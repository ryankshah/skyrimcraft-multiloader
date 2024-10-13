package com.ryankshah.skyrimcraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SkyrimBarsBlock extends CrossCollisionBlock {
    public static final MapCodec<SkyrimBarsBlock> CODEC = simpleCodec(SkyrimBarsBlock::new);

    public MapCodec<? extends SkyrimBarsBlock> codec() {
        return CODEC;
    }

    public SkyrimBarsBlock(BlockBehaviour.Properties p_54198_) {
        super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, p_54198_);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(NORTH, false)).setValue(EAST, false)).setValue(SOUTH, false)).setValue(WEST, false)).setValue(WATERLOGGED, false));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter blockgetter = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.south();
        BlockPos blockpos3 = blockpos.west();
        BlockPos blockpos4 = blockpos.east();
        BlockState blockstate = blockgetter.getBlockState(blockpos1);
        BlockState blockstate1 = blockgetter.getBlockState(blockpos2);
        BlockState blockstate2 = blockgetter.getBlockState(blockpos3);
        BlockState blockstate3 = blockgetter.getBlockState(blockpos4);
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(NORTH, this.attachsTo(blockstate, blockstate.isFaceSturdy(blockgetter, blockpos1, Direction.SOUTH)))).setValue(SOUTH, this.attachsTo(blockstate1, blockstate1.isFaceSturdy(blockgetter, blockpos2, Direction.NORTH)))).setValue(WEST, this.attachsTo(blockstate2, blockstate2.isFaceSturdy(blockgetter, blockpos3, Direction.EAST)))).setValue(EAST, this.attachsTo(blockstate3, blockstate3.isFaceSturdy(blockgetter, blockpos4, Direction.WEST)))).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if ((Boolean)state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return facing.getAxis().isHorizontal() ? (BlockState)state.setValue((Property)PROPERTY_BY_DIRECTION.get(facing), this.attachsTo(facingState, facingState.isFaceSturdy(level, facingPos, facing.getOpposite()))) : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    protected VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    protected boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.is(this)) {
            if (!side.getAxis().isHorizontal()) {
                return true;
            }

            if ((Boolean)state.getValue((Property)PROPERTY_BY_DIRECTION.get(side)) && (Boolean)adjacentBlockState.getValue((Property)PROPERTY_BY_DIRECTION.get(side.getOpposite()))) {
                return true;
            }
        }

        return super.skipRendering(state, adjacentBlockState, side);
    }

    public final boolean attachsTo(BlockState state, boolean solidSide) {
        return !isExceptionForConnection(state) && solidSide || state.getBlock() instanceof SkyrimBarsBlock || state.getBlock() instanceof IronBarsBlock || state.is(BlockTags.WALLS);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{NORTH, EAST, WEST, SOUTH, WATERLOGGED});
    }
}
