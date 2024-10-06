package com.ryankshah.skyrimcraft.block.piston;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;

public class DwemerPistonHeadRenderer implements BlockEntityRenderer<DwemerPistonMovingBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public DwemerPistonHeadRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(DwemerPistonMovingBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        if (level != null) {
            BlockPos blockpos = blockEntity.getBlockPos().relative(blockEntity.getMovementDirection().getOpposite());
            BlockState blockstate = blockEntity.getMovedState();
            if (!blockstate.isAir()) {
                ModelBlockRenderer.enableCaching();
                poseStack.pushPose();
                poseStack.translate(blockEntity.getXOff(partialTick), blockEntity.getYOff(partialTick), blockEntity.getZOff(partialTick));
                if (blockstate.is(BlockRegistry.DWEMER_PISTON_HEAD.get()) && blockEntity.getProgress(partialTick) <= 4.0F) {
                    blockstate = (BlockState)blockstate.setValue(DwemerPistonHead.SHORT, blockEntity.getProgress(partialTick) <= 0.5F);
                    this.renderBlock(blockpos, blockstate, poseStack, bufferSource, level, false, packedOverlay);
                } else if (blockEntity.isSourcePiston() && !blockEntity.isExtending()) {
                    PistonType pistontype = blockstate.is(BlockRegistry.DWEMER_STICKY_PISTON.get()) ? PistonType.STICKY : PistonType.DEFAULT;
                    BlockState blockstate1 = (BlockState)((BlockState)BlockRegistry.DWEMER_PISTON_HEAD.get().defaultBlockState().setValue(DwemerPistonHead.TYPE, pistontype)).setValue(DwemerPistonHead.FACING, (Direction)blockstate.getValue(DwemerPistonBase.FACING));
                    blockstate1 = (BlockState)blockstate1.setValue(DwemerPistonHead.SHORT, blockEntity.getProgress(partialTick) >= 0.5F);
                    this.renderBlock(blockpos, blockstate1, poseStack, bufferSource, level, false, packedOverlay);
                    BlockPos blockpos1 = blockpos.relative(blockEntity.getMovementDirection());
                    poseStack.popPose();
                    poseStack.pushPose();
                    blockstate = (BlockState)blockstate.setValue(DwemerPistonBase.EXTENDED, true);
                    this.renderBlock(blockpos1, blockstate, poseStack, bufferSource, level, true, packedOverlay);
                } else {
                    this.renderBlock(blockpos, blockstate, poseStack, bufferSource, level, false, packedOverlay);
                }

                poseStack.popPose();
                ModelBlockRenderer.clearCache();
            }
        }

    }

    private void renderBlock(BlockPos pos, BlockState state, PoseStack poseStack, MultiBufferSource bufferSource, Level level, boolean extended, int packedOverlay) {
        RenderType rendertype = ItemBlockRenderTypes.getMovingBlockRenderType(state);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(rendertype);
        this.blockRenderer.getModelRenderer().tesselateBlock(level, this.blockRenderer.getBlockModel(state), state, pos, poseStack, vertexconsumer, extended, RandomSource.create(), state.getSeed(pos), packedOverlay);
    }

    @Override
    public int getViewDistance() {
        return 68;
    }
}
