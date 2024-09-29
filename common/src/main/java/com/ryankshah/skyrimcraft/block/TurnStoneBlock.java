package com.ryankshah.skyrimcraft.block;

import com.mojang.serialization.MapCodec;
import com.ryankshah.skyrimcraft.block.entity.TurnStoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TurnStoneBlock extends BaseEntityBlock
{
    public static final MapCodec<TurnStoneBlock> CODEC = simpleCodec(TurnStoneBlock::new);

    public TurnStoneBlock() {
        this(Properties.ofFullCopy(Blocks.DEEPSLATE).strength(2.0f).requiresCorrectToolForDrops().noOcclusion());
    }

    public TurnStoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TurnStoneBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof TurnStoneBlockEntity turnStone) {
                turnStone.startAnimation();
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.CONSUME;
    }
}