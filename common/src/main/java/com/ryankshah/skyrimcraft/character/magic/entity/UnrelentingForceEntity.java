package com.ryankshah.skyrimcraft.character.magic.entity;

import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Set;

/**
 * TODO: See what we can maybe do with a screenshake..?
 */
public class UnrelentingForceEntity extends AbstractHurtingProjectile
{
    private LivingEntity shootingEntity;
    private int ticksAlive;
    private int ticksInAir;
    private double accelerationX;
    private double accelerationY;
    private double accelerationZ;
    private Vec3 startingPosition;

    public UnrelentingForceEntity(Level worldIn) {
        super(EntityRegistry.SHOUT_UNRELENTING_FORCE_ENTITY.get(), worldIn);
    }

    public UnrelentingForceEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(EntityRegistry.SHOUT_UNRELENTING_FORCE_ENTITY.get(), worldIn);
        this.shootingEntity = shooter;
        this.setPos(shooter.getForward().x, shooter.getForward().y, shooter.getForward().z);
        this.moveTo(shooter.getX(), shooter.getY(), shooter.getZ(), shooter.getYRot(), shooter.getXRot());
        this.setPos(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(Vec3.ZERO);

        startingPosition = new Vec3(getX(), getY(), getZ());

        double u = Mth.sqrt((float)(accelX * accelX + accelY * accelY + accelZ * accelZ));

        this.accelerationX = accelX / u * 0.1D;
        this.accelerationY = accelY / u * 0.1D;
        this.accelerationZ = accelZ / u * 0.1D;
    }

    public UnrelentingForceEntity(EntityType<? extends AbstractHurtingProjectile> entityEntityType, Level world) {
        super(entityEntityType, world);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double u = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(u)) {
            u = 4.0D;
        }

        u = u * 64.0D;
        return distance < u * u;
    }

    @Override
    public void tick() {
        if(!this.level().isClientSide && this.startingPosition != null) {
            if (startingPosition.distanceToSqr(getX(), getY(), getZ()) >= 64D)
                this.remove(RemovalReason.DISCARDED);

            ServerLevel level = (ServerLevel) this.level();

            // Get origins
//            Vec3 origin = new Vec3(getX(), getY(), getZ());
//            float radius = 1.375f * (1f + ((float)(startingPosition.distanceToSqr(origin)/64f)));
//            Vec3 normal = getDeltaMovement().normalize(); //getLookAngle();
//            Set<Vec3> circlePoints = ClientUtil.circle(origin, normal, radius, 8);
//            for(Vec3 point : circlePoints) {
//                level.sendParticles(ParticleTypes.CLOUD, getForward().x + point.x, getForward().y + point.y, getForward().z + point.z, 4, 0, 0, 0, 0);
//            }
            Vec3 currentPosition = new Vec3(getX(), getY(), getZ());
            float distanceFromStart = (float) startingPosition.distanceTo(currentPosition);
            float radius = 1.375f * (1f + (distanceFromStart / 64f));
            Vec3 normal = getDeltaMovement().normalize();
            Set<Vec3> circlePoints = ClientUtil.circle(currentPosition, normal, radius, 8);

            for (Vec3 point : circlePoints) {
                level.sendParticles(ParticleTypes.CLOUD, point.x, point.y, point.z, 4, 0, 0, 0, 0);
            }
        }

        if (this.level().isClientSide || (this.shootingEntity == null || !this.shootingEntity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
            super.tick();
            ++this.ticksInAir;

//            ServerLevel level = (ServerLevel)this.level();

            HitResult raytraceresult = ProjectileUtil.getHitResultOnMoveVector(this, entity -> entity.isAlive() && entity != this.shootingEntity);
            if (raytraceresult.getType() != HitResult.Type.MISS) {
                this.onImpact(raytraceresult);
            }

            Vec3 vec3d = this.getDeltaMovement();
            this.setPos(getX() + vec3d.x, getY() + vec3d.y, getZ() + vec3d.z);

            float f = this.getMotionFactor();

            this.setDeltaMovement(vec3d.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale(f));
            this.setPos(this.getX(), this.getY(), this.getZ());
        } else {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    protected float getMotionFactor() {
        return 1f;
    }


    public void onImpact(HitResult result) {
        if (!this.level().isClientSide) {
            if(result.getType() != HitResult.Type.MISS) {
//                AABB aabb = getBoundingBox();
                AABB aabb = getBoundingBox().inflate(2.0);
                List<Entity> entitiesInBoundingBox = this.level().getEntities(this.shootingEntity, aabb);

                Vec3 motionVec = this.getDeltaMovement().normalize();
                for (Entity entity : entitiesInBoundingBox) {
                    if (entity instanceof LivingEntity livingEntity && livingEntity != shootingEntity) {
                        if ((!livingEntity.isInWater() || !livingEntity.isInLava())) {
                            livingEntity.knockback(2F, -motionVec.x, -motionVec.z); // Apply knockback away from the entity's motion
                        }
                    }
                }
//                for(Entity entity : entitiesInBoundingBox) {
//                    if(entity instanceof LivingEntity livingEntity && livingEntity != shootingEntity) {
//                        if((!livingEntity.isInWater() || !livingEntity.isInLava())) {
//                            Vec3 motionVec = this.getDeltaMovement().normalize(); // Use normalized motion vector for knockback direction
//                            livingEntity.knockback(2F, motionVec.x, motionVec.z); // Apply knockback in the direction of the entity's motion
//                            //livingEntity.knockback(2F, (double) Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), (double) (-Mth.cos(this.getYRot() * ((float) Math.PI / 180F))));
//                        }
//                    }
//                }
            }
//            if (result.getType() == HitResult.Type.ENTITY) {
//                Entity entity = ((EntityHitResult)result).getEntity();
//                if((!entity.isInWater() || !entity.isInLava()) && entity instanceof LivingEntity) {
//                    System.out.println("knocking back " + entity.getCustomName());
//                    ((LivingEntity) entity).knockback(2F, (double) Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), (double) (-Mth.cos(this.getYRot() * ((float) Math.PI / 180F))));
//                }
//            }
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        Vec3 vec3d = this.getDeltaMovement();
        compound.put("direction", this.newDoubleList(vec3d.x, vec3d.y, vec3d.z));
        compound.put("power", this.newDoubleList(this.accelerationX, this.accelerationY, this.accelerationZ));
        compound.putInt("life", this.ticksAlive);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("power", 9)) {
            ListTag listnbt = compound.getList("power", 6);
            if (listnbt.size() == 3) {
                this.accelerationX = listnbt.getDouble(0);
                this.accelerationY = listnbt.getDouble(1);
                this.accelerationZ = listnbt.getDouble(2);
            }
        }

        this.ticksAlive = compound.getInt("life");
        if (compound.contains("direction", 9) && compound.getList("direction", 6).size() == 3) {
            ListTag listnbt1 = compound.getList("direction", 6);
            this.setDeltaMovement(listnbt1.getDouble(0), listnbt1.getDouble(1), listnbt1.getDouble(2));
        } else {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 1.0F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.markHurt();
            if (source.getEntity() != null) {
                Vec3 vec3d = source.getEntity().getLookAngle();
                this.setDeltaMovement(vec3d);
                this.accelerationX = vec3d.x * 0.1D;
                this.accelerationY = vec3d.y * 0.1D;
                this.accelerationZ = vec3d.z * 0.1D;
                if (source.getEntity() instanceof LivingEntity) {
                    this.shootingEntity = (LivingEntity)source.getEntity();
                }

                return true;
            } else {
                return false;
            }
        }
    }
}