package com.ryankshah.skyrimcraft.block;

import com.mojang.serialization.MapCodec;
import com.ryankshah.skyrimcraft.block.entity.RuneStoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;

public class RuneStoneBlock extends BaseEntityBlock
{
    public static final MapCodec<RuneStoneBlock> CODEC = simpleCodec(RuneStoneBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<RuneSymbol> ACTIVE_SYMBOL = EnumProperty.create("active_symbol", RuneSymbol.class);

    private static final Map<Direction, RuneSymbol> DIRECTION_TO_SYMBOL = Map.of(
            Direction.NORTH, RuneSymbol.SYMBOL1,
            Direction.EAST, RuneSymbol.SYMBOL2,
            Direction.SOUTH, RuneSymbol.SYMBOL3,
            Direction.WEST, RuneSymbol.SYMBOL4
    );


    public RuneStoneBlock() {
        this(Properties.ofFullCopy(Blocks.DEEPSLATE).strength(2.0f).requiresCorrectToolForDrops().noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    public RuneStoneBlock(Properties properties) {
        super(Properties.ofFullCopy(Blocks.DEEPSLATE).strength(2.0f).requiresCorrectToolForDrops().noOcclusion().dynamicShape());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(ACTIVE_SYMBOL, RuneSymbol.EMPTY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, ACTIVE_SYMBOL);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection();
        RuneSymbol symbol = switch(facing) {
            case NORTH -> RuneSymbol.SYMBOL3; // Visible when looking south
            case EAST -> RuneSymbol.SYMBOL4;  // Visible when looking west
            case SOUTH -> RuneSymbol.SYMBOL1; // Visible when looking north
            case WEST -> RuneSymbol.SYMBOL2;  // Visible when looking east
            default -> RuneSymbol.SYMBOL1;
        };

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(POWERED, false)
                .setValue(ACTIVE_SYMBOL, symbol);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);

            System.out.println(aboveState);

            if (aboveState.getBlock() instanceof TurnStoneBlock) {
                RuneSymbol turnStoneSymbol = ((TurnStoneBlock) aboveState.getBlock()).getCurrentSymbol(level, abovePos);
                Direction runeStoneFacing = state.getValue(FACING);
                Direction turnStoneFacing = aboveState.getValue(TurnStoneBlock.FACING);
                RuneSymbol activeSymbol = state.getValue(ACTIVE_SYMBOL);

                System.out.println("RuneStone Facing: " + runeStoneFacing);
                System.out.println("TurnStone Facing: " + turnStoneFacing);
                System.out.println("Required Symbol: " + activeSymbol);
                System.out.println("TurnStone Symbol: " + turnStoneSymbol);

                boolean shouldBePowered = (runeStoneFacing == turnStoneFacing) && (activeSymbol == turnStoneSymbol);
                if (state.getValue(POWERED) != shouldBePowered) {
                    level.setBlock(pos, state.setValue(POWERED, shouldBePowered), 3);
                    level.updateNeighborsAt(pos, this);
                }
            } else if (state.getValue(POWERED)) {
                level.setBlock(pos, state.setValue(POWERED, false), 3);
                level.updateNeighborsAt(pos, this);
            }
        }
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    private RuneSymbol getSymbolForDirection(Direction direction) {
        return DIRECTION_TO_SYMBOL.getOrDefault(direction, RuneSymbol.SYMBOL1);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RuneStoneBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public enum RuneSymbol implements StringRepresentable {
        EMPTY("empty"),
        SYMBOL1("symbol1"),
        SYMBOL2("symbol2"),
        SYMBOL3("symbol3"),
        SYMBOL4("symbol4");

        private final String name;

        RuneSymbol(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}