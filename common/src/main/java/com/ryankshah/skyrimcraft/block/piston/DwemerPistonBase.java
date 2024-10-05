package com.ryankshah.skyrimcraft.block.piston;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DwemerPistonBase extends DirectionalBlock {
    public static final MapCodec<DwemerPistonBase> CODEC = RecordCodecBuilder.mapCodec((p_308861_) -> {
        return p_308861_.group(Codec.BOOL.fieldOf("sticky").forGetter((p_304492_) -> {
            return p_304492_.isSticky;
        }), propertiesCodec()).apply(p_308861_, DwemerPistonBase::new);
    });
    public static final BooleanProperty EXTENDED;
    public static final int TRIGGER_EXTEND = 0;
    public static final int TRIGGER_CONTRACT = 1;
    public static final int TRIGGER_DROP = 2;
    public static final float PLATFORM_THICKNESS = 4.0F;
    protected static final VoxelShape EAST_AABB;
    protected static final VoxelShape WEST_AABB;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape NORTH_AABB;
    protected static final VoxelShape UP_AABB;
    protected static final VoxelShape DOWN_AABB;
    private final boolean isSticky;

    public MapCodec<DwemerPistonBase> codec() {
        return CODEC;
    }

    public DwemerPistonBase(boolean isSticky, BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(EXTENDED, false));
        this.isSticky = isSticky;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if ((Boolean)state.getValue(EXTENDED)) {
            switch ((Direction)state.getValue(FACING)) {
                case DOWN:
                    return DOWN_AABB;
                case UP:
                default:
                    return UP_AABB;
                case NORTH:
                    return NORTH_AABB;
                case SOUTH:
                    return SOUTH_AABB;
                case WEST:
                    return WEST_AABB;
                case EAST:
                    return EAST_AABB;
            }
        } else {
            return Shapes.block();
        }
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide) {
            this.checkIfExtend(level, pos, state);
        }

    }

    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            this.checkIfExtend(level, pos, state);
        }

    }

    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock()) && !level.isClientSide && level.getBlockEntity(pos) == null) {
            this.checkIfExtend(level, pos, state);
        }

    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite())).setValue(EXTENDED, false);
    }

    private void checkIfExtend(Level level, BlockPos pos, BlockState state) {
        Direction direction = (Direction)state.getValue(FACING);
        boolean flag = this.getNeighborSignal(level, pos, direction);
        if (flag && !(Boolean)state.getValue(EXTENDED)) {
            if ((new PistonStructureResolver(level, pos, direction, true)).resolve()) {
                level.blockEvent(pos, this, 0, direction.get3DDataValue());
            }
        } else if (!flag && (Boolean)state.getValue(EXTENDED)) {
            BlockPos blockpos = pos.relative(direction, 2);
            BlockState blockstate = level.getBlockState(blockpos);
            int i = 1;
            if (blockstate.is(BlockRegistry.DWEMER_MOVING_PISTON.get()) && blockstate.getValue(FACING) == direction) {
                BlockEntity var10 = level.getBlockEntity(blockpos);
                if (var10 instanceof DwemerPistonMovingBlockEntity) {
                    DwemerPistonMovingBlockEntity pistonmovingblockentity = (DwemerPistonMovingBlockEntity)var10;
                    if (pistonmovingblockentity.isExtending() && (pistonmovingblockentity.getProgress(0.0F) < 0.5F || level.getGameTime() == pistonmovingblockentity.getLastTicked() || ((ServerLevel)level).isHandlingTick())) {
                        i = 2;
                    }
                }
            }

            level.blockEvent(pos, this, i, direction.get3DDataValue());
        }

    }

    private boolean getNeighborSignal(SignalGetter signalGetter, BlockPos pos, Direction p_direction) {
        Direction[] var4 = Direction.values();
        int var5 = var4.length;

        int var6;
        for(var6 = 0; var6 < var5; ++var6) {
            Direction direction = var4[var6];
            if (direction != p_direction && signalGetter.hasSignal(pos.relative(direction), direction)) {
                return true;
            }
        }

        if (signalGetter.hasSignal(pos, Direction.DOWN)) {
            return true;
        } else {
            BlockPos blockpos = pos.above();
            Direction[] var10 = Direction.values();
            var6 = var10.length;

            for(int var11 = 0; var11 < var6; ++var11) {
                Direction direction1 = var10[var11];
                if (direction1 != Direction.DOWN && signalGetter.hasSignal(blockpos.relative(direction1), direction1)) {
                    return true;
                }
            }

            return false;
        }
    }

    /** @deprecated */
    protected boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
        Direction direction = (Direction)state.getValue(FACING);
        BlockState blockstate = (BlockState)state.setValue(EXTENDED, true);
        if (!level.isClientSide) {
            boolean flag = this.getNeighborSignal(level, pos, direction);
            if (flag && (id == 1 || id == 2)) {
                level.setBlock(pos, blockstate, 2);
                return false;
            }

            if (!flag && id == 0) {
                return false;
            }
        }

        if (id == 0) {
            if (!this.moveBlocks(level, pos, direction, true)) {
                return false;
            }

            level.setBlock(pos, blockstate, 67);
            level.playSound((Player)null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.25F + 0.6F);
            level.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Context.of(blockstate));
        } else if (id == 1 || id == 2) {
            BlockEntity blockentity = level.getBlockEntity(pos.relative(direction));
            if (blockentity instanceof DwemerPistonMovingBlockEntity) {
                ((DwemerPistonMovingBlockEntity)blockentity).finalTick();
            }

            BlockState blockstate1 = (BlockState)((BlockState) BlockRegistry.DWEMER_MOVING_PISTON.get().defaultBlockState().setValue(DwemerMovingPistonBlock.FACING, direction)).setValue(DwemerMovingPistonBlock.TYPE, this.isSticky ? PistonType.STICKY : PistonType.DEFAULT);
            level.setBlock(pos, blockstate1, 20);
            level.setBlockEntity(DwemerMovingPistonBlock.newMovingBlockEntity(pos, blockstate1, (BlockState)this.defaultBlockState().setValue(FACING, Direction.from3DDataValue(param & 7)), direction, false, true));
            level.blockUpdated(pos, blockstate1.getBlock());
            blockstate1.updateNeighbourShapes(level, pos, 2);
            if (this.isSticky) {
                BlockPos blockpos = pos.offset(direction.getStepX() * 2, direction.getStepY() * 2, direction.getStepZ() * 2);
                BlockState blockstate2 = level.getBlockState(blockpos);
                boolean flag1 = false;
                if (blockstate2.is(BlockRegistry.DWEMER_MOVING_PISTON.get())) {
                    BlockEntity var14 = level.getBlockEntity(blockpos);
                    if (var14 instanceof DwemerPistonMovingBlockEntity) {
                        DwemerPistonMovingBlockEntity pistonmovingblockentity = (DwemerPistonMovingBlockEntity)var14;
                        if (pistonmovingblockentity.getDirection() == direction && pistonmovingblockentity.isExtending()) {
                            pistonmovingblockentity.finalTick();
                            flag1 = true;
                        }
                    }
                }

                if (!flag1) {
                    if (id == 1 && !blockstate2.isAir() && isPushable(blockstate2, level, blockpos, direction.getOpposite(), false, direction) && (blockstate2.getPistonPushReaction() == PushReaction.NORMAL || blockstate2.is(BlockRegistry.DWEMER_PISTON.get()) || blockstate2.is(BlockRegistry.DWEMER_STICKY_PISTON.get()))) {
                        this.moveBlocks(level, pos, direction, false);
                    } else {
                        level.removeBlock(pos.relative(direction), false);
                    }
                }
            } else {
                level.removeBlock(pos.relative(direction), false);
            }

            level.playSound((Player)null, pos, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.15F + 0.6F);
            level.gameEvent(GameEvent.BLOCK_DEACTIVATE, pos, GameEvent.Context.of(blockstate1));
        }

        return true;
    }

    public static boolean isPushable(BlockState state, Level level, BlockPos pos, Direction movementDirection, boolean allowDestroy, Direction pistonFacing) {
        if (pos.getY() >= level.getMinBuildHeight() && pos.getY() <= level.getMaxBuildHeight() - 1 && level.getWorldBorder().isWithinBounds(pos)) {
            if (state.isAir()) {
                return true;
            } else if (!state.is(Blocks.OBSIDIAN) && !state.is(Blocks.CRYING_OBSIDIAN) && !state.is(Blocks.RESPAWN_ANCHOR) && !state.is(Blocks.REINFORCED_DEEPSLATE)) {
                if (movementDirection == Direction.DOWN && pos.getY() == level.getMinBuildHeight()) {
                    return false;
                } else if (movementDirection == Direction.UP && pos.getY() == level.getMaxBuildHeight() - 1) {
                    return false;
                } else {
                    if (!state.is(BlockRegistry.DWEMER_PISTON.get()) && !state.is(BlockRegistry.DWEMER_STICKY_PISTON.get())) {
                        if (state.getDestroySpeed(level, pos) == -1.0F) {
                            return false;
                        }

                        switch (state.getPistonPushReaction()) {
                            case BLOCK -> {
                                return false;
                            }
                            case DESTROY -> {
                                return allowDestroy;
                            }
                            case PUSH_ONLY -> {
                                return movementDirection == pistonFacing;
                            }
                        }
                    } else if ((Boolean)state.getValue(EXTENDED)) {
                        return false;
                    }

                    return !state.hasBlockEntity();
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean moveBlocks(Level level, BlockPos pos, Direction facing, boolean extending) {
        BlockPos blockpos = pos.relative(facing);
        if (!extending && level.getBlockState(blockpos).is(BlockRegistry.DWEMER_PISTON_HEAD.get())) {
            level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 20);
        }

        PistonStructureResolver pistonstructureresolver = new PistonStructureResolver(level, pos, facing, extending);
        if (!pistonstructureresolver.resolve()) {
            return false;
        } else {
            Map<BlockPos, BlockState> map = Maps.newHashMap();
            List<BlockPos> list = pistonstructureresolver.getToPush();
            List<BlockState> list1 = Lists.newArrayList();
            Iterator var10 = list.iterator();

            while(var10.hasNext()) {
                BlockPos blockpos1 = (BlockPos)var10.next();
                BlockState blockstate = level.getBlockState(blockpos1);
                list1.add(blockstate);
                map.put(blockpos1, blockstate);
            }

            List<BlockPos> list2 = pistonstructureresolver.getToDestroy();
            BlockState[] ablockstate = new BlockState[list.size() + list2.size()];
            Direction direction = extending ? facing : facing.getOpposite();
            int i = 0;

            int k;
            BlockPos blockpos3;
            BlockState blockstate7;
            for(k = list2.size() - 1; k >= 0; --k) {
                blockpos3 = (BlockPos)list2.get(k);
                blockstate7 = level.getBlockState(blockpos3);
                BlockEntity blockentity = blockstate7.hasBlockEntity() ? level.getBlockEntity(blockpos3) : null;
                dropResources(blockstate7, level, blockpos3, blockentity);
                level.setBlock(blockpos3, Blocks.AIR.defaultBlockState(), 18);
                level.gameEvent(GameEvent.BLOCK_DESTROY, blockpos3, GameEvent.Context.of(blockstate7));
                if (!blockstate7.is(BlockTags.FIRE)) {
                    level.addDestroyBlockEffect(blockpos3, blockstate7);
                }

                ablockstate[i++] = blockstate7;
            }

            for(k = list.size() - 1; k >= 0; --k) {
                blockpos3 = (BlockPos)list.get(k);
                blockstate7 = level.getBlockState(blockpos3);
                blockpos3 = blockpos3.relative(direction);
                map.remove(blockpos3);
                BlockState blockstate8 = (BlockState)BlockRegistry.DWEMER_MOVING_PISTON.get().defaultBlockState().setValue(FACING, facing);
                level.setBlock(blockpos3, blockstate8, 68);
                level.setBlockEntity(DwemerMovingPistonBlock.newMovingBlockEntity(blockpos3, blockstate8, (BlockState)list1.get(k), facing, extending, false));
                ablockstate[i++] = blockstate7;
            }

            if (extending) {
                PistonType pistontype = this.isSticky ? PistonType.STICKY : PistonType.DEFAULT;
                BlockState blockstate4 = (BlockState)((BlockState)BlockRegistry.DWEMER_PISTON_HEAD.get().defaultBlockState().setValue(DwemerPistonHead.FACING, facing)).setValue(DwemerPistonHead.TYPE, pistontype);
                blockstate7 = (BlockState)((BlockState)BlockRegistry.DWEMER_MOVING_PISTON.get().defaultBlockState().setValue(DwemerMovingPistonBlock.FACING, facing)).setValue(DwemerMovingPistonBlock.TYPE, this.isSticky ? PistonType.STICKY : PistonType.DEFAULT);
                map.remove(blockpos);
                level.setBlock(blockpos, blockstate7, 68);
                level.setBlockEntity(DwemerMovingPistonBlock.newMovingBlockEntity(blockpos, blockstate7, blockstate4, facing, true, true));
            }

            BlockState blockstate3 = Blocks.AIR.defaultBlockState();
            Iterator var25 = map.keySet().iterator();

            while(var25.hasNext()) {
                BlockPos blockpos4 = (BlockPos)var25.next();
                level.setBlock(blockpos4, blockstate3, 82);
            }

            var25 = map.entrySet().iterator();

            BlockPos blockpos6;
            while(var25.hasNext()) {
                Map.Entry<BlockPos, BlockState> entry = (Map.Entry)var25.next();
                blockpos6 = (BlockPos)entry.getKey();
                BlockState blockstate2 = (BlockState)entry.getValue();
                blockstate2.updateIndirectNeighbourShapes(level, blockpos6, 2);
                blockstate3.updateNeighbourShapes(level, blockpos6, 2);
                blockstate3.updateIndirectNeighbourShapes(level, blockpos6, 2);
            }

            i = 0;

            int i1;
            for(i1 = list2.size() - 1; i1 >= 0; --i1) {
                blockstate7 = ablockstate[i++];
                blockpos6 = (BlockPos)list2.get(i1);
                blockstate7.updateIndirectNeighbourShapes(level, blockpos6, 2);
                level.updateNeighborsAt(blockpos6, blockstate7.getBlock());
            }

            for(i1 = list.size() - 1; i1 >= 0; --i1) {
                level.updateNeighborsAt((BlockPos)list.get(i1), ablockstate[i++].getBlock());
            }

            if (extending) {
                level.updateNeighborsAt(blockpos, BlockRegistry.DWEMER_PISTON_HEAD.get());
            }

            return true;
        }
    }

    /** @deprecated */
    protected BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
    }

    /** @deprecated */
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, EXTENDED});
    }

    protected boolean useShapeForLightOcclusion(BlockState state) {
        return (Boolean)state.getValue(EXTENDED);
    }

    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    static {
        EXTENDED = BlockStateProperties.EXTENDED;
        EAST_AABB = Block.box(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);
        WEST_AABB = Block.box(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
        SOUTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
        NORTH_AABB = Block.box(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
        UP_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
        DOWN_AABB = Block.box(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
    }
}
