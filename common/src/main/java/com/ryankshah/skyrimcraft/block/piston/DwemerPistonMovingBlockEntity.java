package com.ryankshah.skyrimcraft.block.piston;

import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.piston.PistonMath;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Iterator;
import java.util.List;

public class DwemerPistonMovingBlockEntity extends BlockEntity {
    private static final int TICKS_TO_EXTEND = 2;
    private static final double PUSH_OFFSET = 0.01;
    public static final double TICK_MOVEMENT = 0.51;
    private BlockState movedState;
    private Direction direction;
    private boolean extending;
    private boolean isSourcePiston;
    private static final ThreadLocal<Direction> NOCLIP = ThreadLocal.withInitial(() -> {
        return null;
    });
    private float progress;
    private float progressO;
    private long lastTicked;
    private int deathTicks;

    public DwemerPistonMovingBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityType.PISTON, pos, blockState);
        this.movedState = Blocks.AIR.defaultBlockState();
    }

    public DwemerPistonMovingBlockEntity(BlockPos pos, BlockState blockState, BlockState movedState, Direction direction, boolean extending, boolean isSourcePiston) {
        this(pos, blockState);
        this.movedState = movedState;
        this.direction = direction;
        this.extending = extending;
        this.isSourcePiston = isSourcePiston;
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveCustomOnly(registries);
    }

    public boolean isExtending() {
        return this.extending;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public boolean isSourcePiston() {
        return this.isSourcePiston;
    }

    public float getProgress(float partialTicks) {
        if (partialTicks > 1.0F) {
            partialTicks = 1.0F;
        }

        return Mth.lerp(partialTicks, this.progressO, this.progress);
    }

    public float getXOff(float partialTicks) {
        return (float)this.direction.getStepX() * this.getExtendedProgress(this.getProgress(partialTicks));
    }

    public float getYOff(float partialTicks) {
        return (float)this.direction.getStepY() * this.getExtendedProgress(this.getProgress(partialTicks));
    }

    public float getZOff(float partialTicks) {
        return (float)this.direction.getStepZ() * this.getExtendedProgress(this.getProgress(partialTicks));
    }

    private float getExtendedProgress(float progress) {
        return this.extending ? progress - 1.0F : 1.0F - progress;
    }

    private BlockState getCollisionRelatedBlockState() {
        return !this.isExtending() && this.isSourcePiston() && this.movedState.getBlock() instanceof DwemerPistonBase ? (BlockState)((BlockState)((BlockState) BlockRegistry.DWEMER_PISTON_HEAD.get().defaultBlockState().setValue(DwemerPistonHead.SHORT, this.progress > 0.25F)).setValue(DwemerPistonHead.TYPE, this.movedState.is(BlockRegistry.DWEMER_STICKY_PISTON.get()) ? PistonType.STICKY : PistonType.DEFAULT)).setValue(DwemerPistonHead.FACING, (Direction)this.movedState.getValue(DwemerPistonBase.FACING)) : this.movedState;
    }

    private static void moveCollidedEntities(Level level, BlockPos pos, float partialTick, DwemerPistonMovingBlockEntity piston) {
        Direction direction = piston.getMovementDirection();
        double d0 = (double)(partialTick - piston.progress);
        VoxelShape voxelshape = piston.getCollisionRelatedBlockState().getCollisionShape(level, pos);
        if (!voxelshape.isEmpty()) {
            AABB aabb = moveByPositionAndProgress(pos, voxelshape.bounds(), piston);
            List<Entity> list = level.getEntities((Entity)null, PistonMath.getMovementArea(aabb, direction, d0).minmax(aabb));
            if (!list.isEmpty()) {
                List<AABB> list1 = voxelshape.toAabbs();
                boolean flag = piston.movedState.is(Blocks.SLIME_BLOCK);
                Iterator iterator = list.iterator();

                while(true) {
                    Entity entity;
                    while(true) {
                        do {
                            if (!iterator.hasNext()) {
                                return;
                            }

                            entity = (Entity)iterator.next();
                        } while(entity.getPistonPushReaction() == PushReaction.IGNORE);

                        if (!flag) {
                            break;
                        }

                        if (!(entity instanceof ServerPlayer)) {
                            Vec3 vec3 = entity.getDeltaMovement();
                            double d1 = vec3.x;
                            double d2 = vec3.y;
                            double d3 = vec3.z;
                            switch (direction.getAxis()) {
                                case X -> d1 = (double)direction.getStepX();
                                case Y -> d2 = (double)direction.getStepY();
                                case Z -> d3 = (double)direction.getStepZ();
                            }

                            entity.setDeltaMovement(d1, d2, d3);
                            break;
                        }
                    }

                    double d4 = 0.0;
                    Iterator var16 = list1.iterator();

                    while(var16.hasNext()) {
                        AABB aabb2 = (AABB)var16.next();
                        AABB aabb1 = PistonMath.getMovementArea(moveByPositionAndProgress(pos, aabb2, piston), direction, d0);
                        AABB aabb3 = entity.getBoundingBox();
                        if (aabb1.intersects(aabb3)) {
                            d4 = Math.max(d4, getMovement(aabb1, direction, aabb3));
                            if (d4 >= d0) {
                                break;
                            }
                        }
                    }

                    if (!(d4 <= 0.0)) {
                        d4 = Math.min(d4, d0) + 0.01;
                        moveEntityByPiston(direction, entity, d4, direction);
                        if (!piston.extending && piston.isSourcePiston) {
                            fixEntityWithinPistonBase(pos, entity, direction, d0);
                        }
                    }
                }
            }
        }

    }

    private static void moveEntityByPiston(Direction noClipDirection, Entity entity, double progress, Direction direction) {
        NOCLIP.set(noClipDirection);
        entity.move(MoverType.PISTON, new Vec3(progress * (double)direction.getStepX(), progress * (double)direction.getStepY(), progress * (double)direction.getStepZ()));
        NOCLIP.set((Direction) null);
    }

    private static void moveStuckEntities(Level level, BlockPos pos, float partialTick, DwemerPistonMovingBlockEntity piston) {
        if (piston.isStickyForEntities()) {
            Direction direction = piston.getMovementDirection();
            if (direction.getAxis().isHorizontal()) {
                double d0 = piston.movedState.getCollisionShape(level, pos).max(Direction.Axis.Y);
                AABB aabb = moveByPositionAndProgress(pos, new AABB(0.0, d0, 0.0, 1.0, 1.5000010000000001, 1.0), piston);
                double d1 = (double)(partialTick - piston.progress);
                Iterator var10 = level.getEntities((Entity)null, aabb, (p_287552_) -> {
                    return matchesStickyCritera(aabb, p_287552_, pos);
                }).iterator();

                while(var10.hasNext()) {
                    Entity entity = (Entity)var10.next();
                    moveEntityByPiston(direction, entity, d1, direction);
                }
            }
        }

    }

    private static boolean matchesStickyCritera(AABB box, Entity entity, BlockPos pos) {
        return entity.getPistonPushReaction() == PushReaction.NORMAL && entity.onGround() && (entity.isSupportedBy(pos) || entity.getX() >= box.minX && entity.getX() <= box.maxX && entity.getZ() >= box.minZ && entity.getZ() <= box.maxZ);
    }

    private boolean isStickyForEntities() {
        return this.movedState.is(Blocks.HONEY_BLOCK);
    }

    public Direction getMovementDirection() {
        return this.extending ? this.direction : this.direction.getOpposite();
    }

    private static double getMovement(AABB headShape, Direction direction, AABB facing) {
        switch (direction) {
            case EAST:
                return headShape.maxX - facing.minX;
            case WEST:
                return facing.maxX - headShape.minX;
            case UP:
            default:
                return headShape.maxY - facing.minY;
            case DOWN:
                return facing.maxY - headShape.minY;
            case SOUTH:
                return headShape.maxZ - facing.minZ;
            case NORTH:
                return facing.maxZ - headShape.minZ;
        }
    }

    private static AABB moveByPositionAndProgress(BlockPos pos, AABB aabb, DwemerPistonMovingBlockEntity pistonMovingBlockEntity) {
        double d0 = (double)pistonMovingBlockEntity.getExtendedProgress(pistonMovingBlockEntity.progress);
        return aabb.move((double)pos.getX() + d0 * (double)pistonMovingBlockEntity.direction.getStepX(), (double)pos.getY() + d0 * (double)pistonMovingBlockEntity.direction.getStepY(), (double)pos.getZ() + d0 * (double)pistonMovingBlockEntity.direction.getStepZ());
    }

    private static void fixEntityWithinPistonBase(BlockPos pos, Entity entity, Direction dir, double progress) {
        AABB aabb = entity.getBoundingBox();
        AABB aabb1 = Shapes.block().bounds().move(pos);
        if (aabb.intersects(aabb1)) {
            Direction direction = dir.getOpposite();
            double d0 = getMovement(aabb1, direction, aabb) + 0.01;
            double d1 = getMovement(aabb1, direction, aabb.intersect(aabb1)) + 0.01;
            if (Math.abs(d0 - d1) < 0.01) {
                d0 = Math.min(d0, progress) + 0.01;
                moveEntityByPiston(dir, entity, d0, direction);
            }
        }

    }

    public BlockState getMovedState() {
        return this.movedState;
    }

    public void finalTick() {
        if (this.level != null && (this.progressO < 1.0F || this.level.isClientSide)) {
            this.progress = 1.0F;
            this.progressO = this.progress;
            this.level.removeBlockEntity(this.worldPosition);
            this.setRemoved();
            if (this.level.getBlockState(this.worldPosition).is(BlockRegistry.DWEMER_MOVING_PISTON.get())) {
                BlockState blockstate;
                if (this.isSourcePiston) {
                    blockstate = Blocks.AIR.defaultBlockState();
                } else {
                    blockstate = Block.updateFromNeighbourShapes(this.movedState, this.level, this.worldPosition);
                }

                this.level.setBlock(this.worldPosition, blockstate, 3);
                this.level.neighborChanged(this.worldPosition, blockstate.getBlock(), this.worldPosition);
            }
        }

    }

    public static void tick(Level level, BlockPos pos, BlockState state, DwemerPistonMovingBlockEntity blockEntity) {
        blockEntity.lastTicked = level.getGameTime();
        blockEntity.progressO = blockEntity.progress;
        if (blockEntity.progressO >= 1.0F) {
            if (level.isClientSide && blockEntity.deathTicks < 5) {
                ++blockEntity.deathTicks;
            } else {
                level.removeBlockEntity(pos);
                blockEntity.setRemoved();
                if (level.getBlockState(pos).is(BlockRegistry.DWEMER_MOVING_PISTON.get())) {
                    BlockState blockstate = Block.updateFromNeighbourShapes(blockEntity.movedState, level, pos);
                    if (blockstate.isAir()) {
                        level.setBlock(pos, blockEntity.movedState, 84);
                        Block.updateOrDestroy(blockEntity.movedState, blockstate, level, pos, 3);
                    } else {
                        if (blockstate.hasProperty(BlockStateProperties.WATERLOGGED) && (Boolean)blockstate.getValue(BlockStateProperties.WATERLOGGED)) {
                            blockstate = (BlockState)blockstate.setValue(BlockStateProperties.WATERLOGGED, false);
                        }

                        level.setBlock(pos, blockstate, 67);
                        level.neighborChanged(pos, blockstate.getBlock(), pos);
                    }
                }
            }
        } else {
            float f = blockEntity.progress + 0.5F;
            moveCollidedEntities(level, pos, f, blockEntity);
            moveStuckEntities(level, pos, f, blockEntity);
            blockEntity.progress = f;
            if (blockEntity.progress >= 1.0F) {
                blockEntity.progress = 1.0F;
            }
        }

    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        HolderGetter<Block> holdergetter = this.level != null ? this.level.holderLookup(Registries.BLOCK) : BuiltInRegistries.BLOCK.asLookup();
        this.movedState = NbtUtils.readBlockState(holdergetter, tag.getCompound("blockState"));
        this.direction = Direction.from3DDataValue(tag.getInt("facing"));
        this.progress = tag.getFloat("progress");
        this.progressO = this.progress;
        this.extending = tag.getBoolean("extending");
        this.isSourcePiston = tag.getBoolean("source");
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("blockState", NbtUtils.writeBlockState(this.movedState));
        tag.putInt("facing", this.direction.get3DDataValue());
        tag.putFloat("progress", this.progressO);
        tag.putBoolean("extending", this.extending);
        tag.putBoolean("source", this.isSourcePiston);
    }

    public VoxelShape getCollisionShape(BlockGetter level, BlockPos pos) {
        VoxelShape voxelshape;
        if (!this.extending && this.isSourcePiston && this.movedState.getBlock() instanceof DwemerPistonBase) {
            voxelshape = ((BlockState)this.movedState.setValue(DwemerPistonBase.EXTENDED, true)).getCollisionShape(level, pos);
        } else {
            voxelshape = Shapes.empty();
        }

        Direction direction = (Direction)NOCLIP.get();
        if ((double)this.progress < 1.0 && direction == this.getMovementDirection()) {
            return voxelshape;
        } else {
            BlockState blockstate;
            if (this.isSourcePiston()) {
                blockstate = (BlockState)((BlockState)BlockRegistry.DWEMER_PISTON_HEAD.get().defaultBlockState().setValue(DwemerPistonHead.FACING, this.direction)).setValue(DwemerPistonHead.SHORT, this.extending != 1.0F - this.progress < 0.25F);
            } else {
                blockstate = this.movedState;
            }

            float f = this.getExtendedProgress(this.progress);
            double d0 = (double)((float)this.direction.getStepX() * f);
            double d1 = (double)((float)this.direction.getStepY() * f);
            double d2 = (double)((float)this.direction.getStepZ() * f);
            return Shapes.or(voxelshape, blockstate.getCollisionShape(level, pos).move(d0, d1, d2));
        }
    }

    public long getLastTicked() {
        return this.lastTicked;
    }

    public void setLevel(Level level) {
        super.setLevel(level);
        if (level.holderLookup(Registries.BLOCK).get(this.movedState.getBlock().builtInRegistryHolder().key()).isEmpty()) {
            this.movedState = Blocks.AIR.defaultBlockState();
        }

    }
}
