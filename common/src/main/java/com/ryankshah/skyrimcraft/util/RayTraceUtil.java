package com.ryankshah.skyrimcraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Objects;
import java.util.function.Predicate;

public class RayTraceUtil implements BlockGetter
{
    private static final Predicate<Entity> isVisible = entity -> !entity.isSpectator() && entity.isPickable();
    private static final Minecraft minecraft = Minecraft.getInstance();

    @Override
    public BlockEntity getBlockEntity(BlockPos pos) {
        return Objects.requireNonNull(minecraft.level).getBlockEntity(pos);
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        return Objects.requireNonNull(minecraft.level).getBlockState(pos);
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        return Objects.requireNonNull(minecraft.level).getFluidState(pos);
    }

    public LivingEntity getEntityInCrosshair(float partialTicks, double reachDistance) {
        Minecraft client = Minecraft.getInstance();
        Entity viewer = client.getCameraEntity();

        if (viewer == null) return null;

        Vec3 position = viewer.getEyePosition(partialTicks);
        Vec3 look = viewer.getViewVector(1.0F);
        Vec3 max = position.add(look.x * reachDistance, look.y * reachDistance, look.z * reachDistance);
        AABB searchBox = viewer.getBoundingBox().expandTowards(look.scale(reachDistance)).inflate(1.0D, 1.0D, 1.0D);

        EntityHitResult result = ProjectileUtil.getEntityHitResult(viewer, position, max, searchBox, isVisible, reachDistance * reachDistance);

        if (result == null || result.getEntity() == null) return null;
        if (result.getEntity() instanceof LivingEntity target) {

            HitResult blockHit = clip(setupRayTraceContext(Objects.requireNonNull(client.player), reachDistance));

            if (!blockHit.getType().equals(BlockHitResult.Type.MISS)) {
                double blockDistance = blockHit.getLocation().distanceTo(position);
                if (blockDistance > target.distanceTo(client.player)) return target;
            } else {
                return target;
            }
        }
        return null;
    }



    private ClipContext setupRayTraceContext(Player player, double distance) {
        float pitch = player.getXRot();
        float yaw = player.getYRot();
        Vec3 fromPos = player.getEyePosition(1.0F);
        float float_3 = Mth.cos(-yaw * 0.017453292F - 3.1415927F);
        float float_4 = Mth.sin(-yaw * 0.017453292F - 3.1415927F);
        float float_5 = -Mth.cos(-pitch * 0.017453292F);
        float xComponent = float_4 * float_5;
        float yComponent = Mth.sin(-pitch * 0.017453292F);
        float zComponent = float_3 * float_5;
        Vec3 toPos = fromPos.add((double) xComponent * distance, (double) yComponent * distance, (double) zComponent * distance);
        return new ClipContext(fromPos, toPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
    }

    @Override
    public BlockHitResult clip(ClipContext context) {
        return BlockGetter.traverseBlocks(context.getFrom(), context.getTo(), context, (c, pos) -> {
            BlockState block = this.getBlockState(pos);
            if (!block.canOcclude()) return null;
            VoxelShape voxelshape = c.getBlockShape(block, this, pos);
            return this.clipWithInteractionOverride(c.getFrom(), c.getTo(), pos, voxelshape, block);
        }, (c) -> {
            Vec3 vec3 = c.getFrom().subtract(c.getTo());
            return BlockHitResult.miss(c.getTo(), Direction.getNearest(vec3.x, vec3.y, vec3.z), BlockPos.containing(c.getTo()));
        });
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getMinBuildHeight() {
        return 0;
    }

    public static Entity rayTrace(Level world, Player player, double range) {
        Vec3 pos = player.getPosition(0f);
        Vec3 cam1 = player.getLookAngle();
        Vec3 cam2 = cam1.add(cam1.x * range, cam1.y * range, cam1.z * range);
        AABB aabb = player.getBoundingBox().expandTowards(cam1.scale(range)).inflate(1.0F, 1.0F, 1.0F);
        EntityHitResult ray = findEntity(world, player, pos, cam2, aabb, (e) -> {
            return e instanceof LivingEntity && e.isAlive();
        }, range);

        if(ray != null) {
            if(ray.getType() == HitResult.Type.ENTITY) {
                EntityHitResult ray2 = ray;
                Entity entity = ray2.getEntity();
                return entity;
            }
        }
        return null;
    }

    private static EntityHitResult findEntity(Level world, Player player, Vec3 pos, Vec3 look, AABB aabb, Predicate<Entity> filter, double range) {
        for(Entity entity1 : world.getEntities(player, aabb, filter)) {
            AABB mob = entity1.getBoundingBox().inflate((double)1.0F);
            if(intersect(pos, look, mob, range)) {
                return new EntityHitResult(entity1);
            }
        }
        return null;
    }

    private static boolean intersect(Vec3 pos, Vec3 look, AABB mob, double range) {
        Vec3 invDir = new Vec3(1f / look.x, 1f / look.y, 1f / look.z);

        boolean signDirX = invDir.x < 0;
        boolean signDirY = invDir.y < 0;
        boolean signDirZ = invDir.z < 0;

        Vec3 max = new Vec3(mob.maxX, mob.maxY, mob.maxZ);
        Vec3 min = new Vec3(mob.minX, mob.minY, mob.minZ);

        Vec3 bbox = signDirX ? max : min;
        double tmin = (bbox.x - pos.x) * invDir.x;
        bbox = signDirX ? min : max;
        double tmax = (bbox.x - pos.x) * invDir.x;
        bbox = signDirY ? max : min;
        double tymin = (bbox.y - pos.y) * invDir.y;
        bbox = signDirY ? min : max;
        double tymax = (bbox.y - pos.y) * invDir.y;

        if ((tmin > tymax) || (tymin > tmax)) {
            return false;
        }
        if (tymin > tmin) {
            tmin = tymin;
        }
        if (tymax < tmax) {
            tmax = tymax;
        }

        bbox = signDirZ ? max : min;
        double tzmin = (bbox.z - pos.z) * invDir.z;
        bbox = signDirZ ? min : max;
        double tzmax = (bbox.z - pos.z) * invDir.z;

        if ((tmin > tzmax) || (tzmin > tmax)) {
            return false;
        }
        if (tzmin > tmin) {
            tmin = tzmin;
        }
        if (tzmax < tmax) {
            tmax = tzmax;
        }
        if ((tmin < range) && (tmax > 0)) {
            return true;
        }
        return false;
    }
}