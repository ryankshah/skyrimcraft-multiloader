package com.ryankshah.skyrimcraft.block.piston;

import com.mojang.serialization.MapCodec;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Arrays;

public class DwemerPistonHead extends DirectionalBlock {
    public static final MapCodec<DwemerPistonHead> CODEC = simpleCodec(DwemerPistonHead::new);
    public static final EnumProperty<PistonType> TYPE;
    public static final BooleanProperty SHORT;
    public static final float PLATFORM = 4.0F;
    protected static final VoxelShape EAST_AABB;
    protected static final VoxelShape WEST_AABB;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape NORTH_AABB;
    protected static final VoxelShape UP_AABB;
    protected static final VoxelShape DOWN_AABB;
    protected static final float AABB_OFFSET = 2.0F;
    protected static final float EDGE_MIN = 6.0F;
    protected static final float EDGE_MAX = 10.0F;
    protected static final VoxelShape UP_ARM_AABB;
    protected static final VoxelShape DOWN_ARM_AABB;
    protected static final VoxelShape SOUTH_ARM_AABB;
    protected static final VoxelShape NORTH_ARM_AABB;
    protected static final VoxelShape EAST_ARM_AABB;
    protected static final VoxelShape WEST_ARM_AABB;
    protected static final VoxelShape SHORT_UP_ARM_AABB;
    protected static final VoxelShape SHORT_DOWN_ARM_AABB;
    protected static final VoxelShape SHORT_SOUTH_ARM_AABB;
    protected static final VoxelShape SHORT_NORTH_ARM_AABB;
    protected static final VoxelShape SHORT_EAST_ARM_AABB;
    protected static final VoxelShape SHORT_WEST_ARM_AABB;
    private static final VoxelShape[] SHAPES_SHORT;
    private static final VoxelShape[] SHAPES_LONG;

    protected MapCodec<DwemerPistonHead> codec() {
        return CODEC;
    }

    private static VoxelShape[] makeShapes(boolean extended) {
        return (VoxelShape[]) Arrays.stream(Direction.values()).map((p_60316_) -> {
            return calculateShape(p_60316_, extended);
        }).toArray((x$0) -> {
            return new VoxelShape[x$0];
        });
    }

    private static VoxelShape calculateShape(Direction direction, boolean shortArm) {
        switch (direction) {
            case DOWN:
            default:
                return Shapes.or(DOWN_AABB, shortArm ? SHORT_DOWN_ARM_AABB : DOWN_ARM_AABB);
            case UP:
                return Shapes.or(UP_AABB, shortArm ? SHORT_UP_ARM_AABB : UP_ARM_AABB);
            case NORTH:
                return Shapes.or(NORTH_AABB, shortArm ? SHORT_NORTH_ARM_AABB : NORTH_ARM_AABB);
            case SOUTH:
                return Shapes.or(SOUTH_AABB, shortArm ? SHORT_SOUTH_ARM_AABB : SOUTH_ARM_AABB);
            case WEST:
                return Shapes.or(WEST_AABB, shortArm ? SHORT_WEST_ARM_AABB : WEST_ARM_AABB);
            case EAST:
                return Shapes.or(EAST_AABB, shortArm ? SHORT_EAST_ARM_AABB : EAST_ARM_AABB);
        }
    }

    public DwemerPistonHead(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(TYPE, PistonType.DEFAULT)).setValue(SHORT, false));
    }

    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return ((Boolean)state.getValue(SHORT) ? SHAPES_SHORT : SHAPES_LONG)[((Direction)state.getValue(FACING)).ordinal()];
    }

    private boolean isFittingBase(BlockState baseState, BlockState extendedState) {
        Block block = baseState.getValue(TYPE) == PistonType.DEFAULT ? BlockRegistry.DWEMER_PISTON.get() : BlockRegistry.DWEMER_STICKY_PISTON.get();
        return extendedState.is(block) && (Boolean)extendedState.getValue(DwemerPistonBase.EXTENDED) && extendedState.getValue(FACING) == baseState.getValue(FACING);
    }

    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.getAbilities().instabuild) {
            BlockPos blockpos = pos.relative(((Direction)state.getValue(FACING)).getOpposite());
            if (this.isFittingBase(state, level.getBlockState(blockpos))) {
                level.destroyBlock(blockpos, false);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            super.onRemove(state, level, pos, newState, isMoving);
            BlockPos blockpos = pos.relative(((Direction)state.getValue(FACING)).getOpposite());
            if (this.isFittingBase(state, level.getBlockState(blockpos))) {
                level.destroyBlock(blockpos, true);
            }
        }

    }

    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return facing.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos.relative(((Direction)state.getValue(FACING)).getOpposite()));
        return this.isFittingBase(state, blockstate) || blockstate.is(BlockRegistry.DWEMER_MOVING_PISTON.get()) && blockstate.getValue(FACING) == state.getValue(FACING);
    }

    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (state.canSurvive(level, pos)) {
            level.neighborChanged(pos.relative(((Direction)state.getValue(FACING)).getOpposite()), block, fromPos);
        }

    }

    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(state.getValue(TYPE) == PistonType.STICKY ? BlockRegistry.DWEMER_STICKY_PISTON.get() : BlockRegistry.DWEMER_PISTON.get());
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
        builder.add(new Property[]{FACING, TYPE, SHORT});
    }

    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    static {
        TYPE = BlockStateProperties.PISTON_TYPE;
        SHORT = BlockStateProperties.SHORT;
        EAST_AABB = Block.box(12.0, 0.0, 0.0, 16.0, 16.0, 16.0);
        WEST_AABB = Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0);
        SOUTH_AABB = Block.box(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
        NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 4.0);
        UP_AABB = Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
        DOWN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
        UP_ARM_AABB = Block.box(6.0, -4.0, 6.0, 10.0, 12.0, 10.0);
        DOWN_ARM_AABB = Block.box(6.0, 4.0, 6.0, 10.0, 20.0, 10.0);
        SOUTH_ARM_AABB = Block.box(6.0, 6.0, -4.0, 10.0, 10.0, 12.0);
        NORTH_ARM_AABB = Block.box(6.0, 6.0, 4.0, 10.0, 10.0, 20.0);
        EAST_ARM_AABB = Block.box(-4.0, 6.0, 6.0, 12.0, 10.0, 10.0);
        WEST_ARM_AABB = Block.box(4.0, 6.0, 6.0, 20.0, 10.0, 10.0);
        SHORT_UP_ARM_AABB = Block.box(6.0, 0.0, 6.0, 10.0, 12.0, 10.0);
        SHORT_DOWN_ARM_AABB = Block.box(6.0, 4.0, 6.0, 10.0, 16.0, 10.0);
        SHORT_SOUTH_ARM_AABB = Block.box(6.0, 6.0, 0.0, 10.0, 10.0, 12.0);
        SHORT_NORTH_ARM_AABB = Block.box(6.0, 6.0, 4.0, 10.0, 10.0, 16.0);
        SHORT_EAST_ARM_AABB = Block.box(0.0, 6.0, 6.0, 12.0, 10.0, 10.0);
        SHORT_WEST_ARM_AABB = Block.box(4.0, 6.0, 6.0, 16.0, 10.0, 10.0);
        SHAPES_SHORT = makeShapes(true);
        SHAPES_LONG = makeShapes(false);
    }
}