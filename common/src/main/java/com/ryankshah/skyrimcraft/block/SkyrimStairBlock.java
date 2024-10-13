package com.ryankshah.skyrimcraft.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.IntStream;

public class SkyrimStairBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<SkyrimStairBlock> CODEC = RecordCodecBuilder.mapCodec((p_308839_) -> {
        return p_308839_.group(BlockState.CODEC.fieldOf("base_state").forGetter((p_304378_) -> {
            return p_304378_.baseState;
        }), propertiesCodec()).apply(p_308839_, SkyrimStairBlock::new);
    });
    public static final DirectionProperty FACING;
    public static final EnumProperty<Half> HALF;
    public static final EnumProperty<StairsShape> SHAPE;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape TOP_AABB;
    protected static final VoxelShape BOTTOM_AABB;
    protected static final VoxelShape OCTET_NNN;
    protected static final VoxelShape OCTET_NNP;
    protected static final VoxelShape OCTET_NPN;
    protected static final VoxelShape OCTET_NPP;
    protected static final VoxelShape OCTET_PNN;
    protected static final VoxelShape OCTET_PNP;
    protected static final VoxelShape OCTET_PPN;
    protected static final VoxelShape OCTET_PPP;
    protected static final VoxelShape[] TOP_SHAPES;
    protected static final VoxelShape[] BOTTOM_SHAPES;
    private static final int[] SHAPE_BY_STATE;
    private final Block base;
    protected final BlockState baseState;

    public MapCodec<? extends SkyrimStairBlock> codec() {
        return CODEC;
    }

    private static VoxelShape[] makeShapes(VoxelShape slabShape, VoxelShape nwCorner, VoxelShape neCorner, VoxelShape swCorner, VoxelShape seCorner) {
        return (VoxelShape[])IntStream.range(0, 16).mapToObj((p_56945_) -> {
            return makeStairShape(p_56945_, slabShape, nwCorner, neCorner, swCorner, seCorner);
        }).toArray((x$0) -> {
            return new VoxelShape[x$0];
        });
    }

    private static VoxelShape makeStairShape(int bitfield, VoxelShape slabShape, VoxelShape nwCorner, VoxelShape neCorner, VoxelShape swCorner, VoxelShape seCorner) {
        VoxelShape voxelshape = slabShape;
        if ((bitfield & 1) != 0) {
            voxelshape = Shapes.or(slabShape, nwCorner);
        }

        if ((bitfield & 2) != 0) {
            voxelshape = Shapes.or(voxelshape, neCorner);
        }

        if ((bitfield & 4) != 0) {
            voxelshape = Shapes.or(voxelshape, swCorner);
        }

        if ((bitfield & 8) != 0) {
            voxelshape = Shapes.or(voxelshape, seCorner);
        }

        return voxelshape;
    }

    public SkyrimStairBlock(BlockState baseState, BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(HALF, Half.BOTTOM)).setValue(SHAPE, StairsShape.STRAIGHT)).setValue(WATERLOGGED, false));
        this.base = baseState.getBlock();
        this.baseState = baseState;
    }

    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (state.getValue(HALF) == Half.TOP ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_BY_STATE[this.getShapeIndex(state)]];
    }

    private int getShapeIndex(BlockState state) {
        return ((StairsShape)state.getValue(SHAPE)).ordinal() * 4 + ((Direction)state.getValue(FACING)).get2DDataValue();
    }

    public float getExplosionResistance() {
        return this.base.getExplosionResistance();
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        BlockState blockstate = (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection())).setValue(HALF, direction == Direction.DOWN || direction != Direction.UP && context.getClickLocation().y - (double)blockpos.getY() > 0.5 ? Half.TOP : Half.BOTTOM)).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        return (BlockState)blockstate.setValue(SHAPE, getStairsShape(blockstate, context.getLevel(), blockpos));
    }

    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if ((Boolean)state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return facing.getAxis().isHorizontal() ? (BlockState)state.setValue(SHAPE, getStairsShape(state, level, currentPos)) : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    private static StairsShape getStairsShape(BlockState state, BlockGetter level, BlockPos pos) {
        Direction direction = (Direction)state.getValue(FACING);
        BlockState blockstate = level.getBlockState(pos.relative(direction));
        if (isStairs(blockstate) && state.getValue(HALF) == blockstate.getValue(HALF)) {
            Direction direction1 = (Direction)blockstate.getValue(FACING);
            if (direction1.getAxis() != ((Direction)state.getValue(FACING)).getAxis() && canTakeShape(state, level, pos, direction1.getOpposite())) {
                if (direction1 == direction.getCounterClockWise()) {
                    return StairsShape.OUTER_LEFT;
                }

                return StairsShape.OUTER_RIGHT;
            }
        }

        BlockState blockstate1 = level.getBlockState(pos.relative(direction.getOpposite()));
        if (isStairs(blockstate1) && state.getValue(HALF) == blockstate1.getValue(HALF)) {
            Direction direction2 = (Direction)blockstate1.getValue(FACING);
            if (direction2.getAxis() != ((Direction)state.getValue(FACING)).getAxis() && canTakeShape(state, level, pos, direction2)) {
                if (direction2 == direction.getCounterClockWise()) {
                    return StairsShape.INNER_LEFT;
                }

                return StairsShape.INNER_RIGHT;
            }
        }

        return StairsShape.STRAIGHT;
    }

    private static boolean canTakeShape(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        BlockState blockstate = level.getBlockState(pos.relative(face));
        return !isStairs(blockstate) || blockstate.getValue(FACING) != state.getValue(FACING) || blockstate.getValue(HALF) != state.getValue(HALF);
    }

    public static boolean isStairs(BlockState state) {
        return state.getBlock() instanceof SkyrimStairBlock || state.getBlock() instanceof StairBlock;
    }

    /** @deprecated */
    protected BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
    }

    /** @deprecated */
    protected BlockState mirror(BlockState state, Mirror mirror) {
        Direction direction = (Direction)state.getValue(FACING);
        StairsShape stairsshape = (StairsShape)state.getValue(SHAPE);
        switch (mirror) {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z) {
                    switch (stairsshape) {
                        case INNER_LEFT -> {
                            return (BlockState)state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_RIGHT);
                        }
                        case INNER_RIGHT -> {
                            return (BlockState)state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_LEFT);
                        }
                        case OUTER_LEFT -> {
                            return (BlockState)state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_RIGHT);
                        }
                        case OUTER_RIGHT -> {
                            return (BlockState)state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_LEFT);
                        }
                        default -> {
                            return state.rotate(Rotation.CLOCKWISE_180);
                        }
                    }
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X) {
                    switch (stairsshape) {
                        case INNER_LEFT -> {
                            return (BlockState)state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_LEFT);
                        }
                        case INNER_RIGHT -> {
                            return (BlockState)state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_RIGHT);
                        }
                        case OUTER_LEFT -> {
                            return (BlockState)state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_RIGHT);
                        }
                        case OUTER_RIGHT -> {
                            return (BlockState)state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_LEFT);
                        }
                        case STRAIGHT -> {
                            return state.rotate(Rotation.CLOCKWISE_180);
                        }
                    }
                }
        }

        return super.mirror(state, mirror);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, HALF, SHAPE, WATERLOGGED});
    }

    protected FluidState getFluidState(BlockState state) {
        return (Boolean)state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        HALF = BlockStateProperties.HALF;
        SHAPE = BlockStateProperties.STAIRS_SHAPE;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        TOP_AABB = Block.box(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);
        BOTTOM_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
        OCTET_NNN = Block.box(0.0, 0.0, 0.0, 8.0, 8.0, 8.0);
        OCTET_NNP = Block.box(0.0, 0.0, 8.0, 8.0, 8.0, 16.0);
        OCTET_NPN = Block.box(0.0, 8.0, 0.0, 8.0, 16.0, 8.0);
        OCTET_NPP = Block.box(0.0, 8.0, 8.0, 8.0, 16.0, 16.0);
        OCTET_PNN = Block.box(8.0, 0.0, 0.0, 16.0, 8.0, 8.0);
        OCTET_PNP = Block.box(8.0, 0.0, 8.0, 16.0, 8.0, 16.0);
        OCTET_PPN = Block.box(8.0, 8.0, 0.0, 16.0, 16.0, 8.0);
        OCTET_PPP = Block.box(8.0, 8.0, 8.0, 16.0, 16.0, 16.0);
        TOP_SHAPES = makeShapes(TOP_AABB, OCTET_NNN, OCTET_PNN, OCTET_NNP, OCTET_PNP);
        BOTTOM_SHAPES = makeShapes(BOTTOM_AABB, OCTET_NPN, OCTET_PPN, OCTET_NPP, OCTET_PPP);
        SHAPE_BY_STATE = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
    }
}
