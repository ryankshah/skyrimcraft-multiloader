package com.ryankshah.skyrimcraft.block.piston;

import com.mojang.serialization.MapCodec;
import com.ryankshah.skyrimcraft.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class DwemerMovingPistonBlock extends BaseEntityBlock {
    public static final MapCodec<DwemerMovingPistonBlock> CODEC = simpleCodec(DwemerMovingPistonBlock::new);
    public static final DirectionProperty FACING;
    public static final EnumProperty<PistonType> TYPE;

    public MapCodec<DwemerMovingPistonBlock> codec() {
        return CODEC;
    }

    public DwemerMovingPistonBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(TYPE, PistonType.DEFAULT));
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    public static BlockEntity newMovingBlockEntity(BlockPos pos, BlockState blockState, BlockState movedState, Direction direction, boolean extending, boolean isSourcePiston) {
        return new DwemerPistonMovingBlockEntity(pos, blockState, movedState, direction, extending, isSourcePiston);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, BlockEntityRegistry.DWEMER_PISTON.get(), DwemerPistonMovingBlockEntity::tick);
    }

    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof DwemerPistonMovingBlockEntity) {
                ((DwemerPistonMovingBlockEntity)blockentity).finalTick();
            }
        }

    }

    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.relative(((Direction)state.getValue(FACING)).getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.getBlock() instanceof DwemerPistonBase && (Boolean)blockstate.getValue(DwemerPistonBase.EXTENDED)) {
            level.removeBlock(blockpos, false);
        }

    }

    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide && level.getBlockEntity(pos) == null) {
            level.removeBlock(pos, false);
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.PASS;
        }
    }

    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        DwemerPistonMovingBlockEntity pistonmovingblockentity = this.getBlockEntity(params.getLevel(), BlockPos.containing((Position)params.getParameter(LootContextParams.ORIGIN)));
        return pistonmovingblockentity == null ? Collections.emptyList() : pistonmovingblockentity.getMovedState().getDrops(params);
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        DwemerPistonMovingBlockEntity pistonmovingblockentity = this.getBlockEntity(level, pos);
        return pistonmovingblockentity != null ? pistonmovingblockentity.getCollisionShape(level, pos) : Shapes.empty();
    }

    @Nullable
    private DwemerPistonMovingBlockEntity getBlockEntity(BlockGetter blockReader, BlockPos pos) {
        BlockEntity blockentity = blockReader.getBlockEntity(pos);
        return blockentity instanceof DwemerPistonMovingBlockEntity ? (DwemerPistonMovingBlockEntity)blockentity : null;
    }

    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
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
        builder.add(new Property[]{FACING, TYPE});
    }

    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    static {
        FACING = DwemerPistonHead.FACING;
        TYPE = DwemerPistonHead.TYPE;
    }
}
