package com.ryankshah.skyrimcraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class SkyrimObserverBlock extends DirectionalBlock {
    public static final MapCodec<SkyrimObserverBlock> CODEC = simpleCodec(SkyrimObserverBlock::new);
    public static final BooleanProperty POWERED;

    public MapCodec<SkyrimObserverBlock> codec() {
        return CODEC;
    }

    public SkyrimObserverBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.SOUTH)).setValue(POWERED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, POWERED});
    }

    /** @deprecated */
    protected BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
    }

    /** @deprecated */
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
    }

    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if ((Boolean)state.getValue(POWERED)) {
            level.setBlock(pos, (BlockState)state.setValue(POWERED, false), 2);
        } else {
            level.setBlock(pos, (BlockState)state.setValue(POWERED, true), 2);
            level.scheduleTick(pos, this, 2);
        }

        this.updateNeighborsInFront(level, pos, state);
    }

    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(FACING) == facing && !(Boolean)state.getValue(POWERED)) {
            this.startSignal(level, currentPos);
        }

        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    private void startSignal(LevelAccessor level, BlockPos pos) {
        if (!level.isClientSide() && !level.getBlockTicks().hasScheduledTick(pos, this)) {
            level.scheduleTick(pos, this, 2);
        }

    }

    protected void updateNeighborsInFront(Level level, BlockPos pos, BlockState state) {
        Direction direction = (Direction)state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        level.neighborChanged(blockpos, this, pos);
        level.updateNeighborsAtExceptFromFacing(blockpos, this, direction);
    }

    /** @deprecated */
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    /** @deprecated */
    protected int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getSignal(blockAccess, pos, side);
    }

    /** @deprecated */
    protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return (Boolean)blockState.getValue(POWERED) && blockState.getValue(FACING) == side ? 15 : 0;
    }

    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!state.is(oldState.getBlock()) && !level.isClientSide() && (Boolean)state.getValue(POWERED) && !level.getBlockTicks().hasScheduledTick(pos, this)) {
            BlockState blockstate = (BlockState)state.setValue(POWERED, false);
            level.setBlock(pos, blockstate, 18);
            this.updateNeighborsInFront(level, pos, blockstate);
        }

    }

    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock()) && !level.isClientSide && (Boolean)state.getValue(POWERED) && level.getBlockTicks().hasScheduledTick(pos, this)) {
            this.updateNeighborsInFront(level, pos, (BlockState)state.setValue(POWERED, false));
        }

    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite().getOpposite());
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
