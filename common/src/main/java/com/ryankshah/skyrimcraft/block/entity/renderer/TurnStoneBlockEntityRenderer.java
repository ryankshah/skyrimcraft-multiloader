package com.ryankshah.skyrimcraft.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.TurnStoneBlock;
import com.ryankshah.skyrimcraft.block.entity.TurnStoneBlockEntity;
import com.ryankshah.skyrimcraft.block.entity.model.TurnStoneModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;

public class TurnStoneBlockEntityRenderer implements BlockEntityRenderer<TurnStoneBlockEntity>
{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/block/turn_stone.png");
    private final TurnStoneModel<Entity> model;
    private final AnimContext animCtx = new AnimContext(new AnimationState());

    public TurnStoneBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        model = new TurnStoneModel<>(context.bakeLayer(TurnStoneModel.LAYER_LOCATION));
        animCtx.state.start(0);
    }

    @Override
    public void render(TurnStoneBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        if (blockEntity.isSpinning()) {
            model.setupAnimation(blockEntity.getLevel(), partialTick, animCtx);
            float spinProgress = blockEntity.getSpinProgress();
            poseStack.mulPose(Axis.YP.rotationDegrees(spinProgress * 360));
        }

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);

        poseStack.popPose();
    }

    public record AnimContext(AnimationState state) { }
}