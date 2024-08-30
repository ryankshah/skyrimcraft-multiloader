package com.ryankshah.skyrimcraft.character.magic.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.entity.LightBallEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class LightBallRenderer extends EntityRenderer<LightBallEntity>
{
    private static final ResourceLocation LIGHTBALL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/effect/light_ball.png");

    public LightBallRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public void render(LightBallEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.scale(1.0F, 1.0F, 1.0F);

        VertexConsumer ivertexbuilder = pBuffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(pEntity)));
        PoseStack.Pose matrixstack$entry = pPoseStack.last();
        Matrix4f matrix4f = matrixstack$entry.pose();

        long t = System.currentTimeMillis() % 6;

        pPoseStack.mulPose(entityRenderDispatcher.cameraOrientation());

        ivertexbuilder.addVertex(matrix4f, -1, -1, 0).setColor(255, 255, 255, 255).setUv(0, 0 +  t * (1.0f / 4.0f)).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(pPackedLight, pPackedLight).setNormal(0, 1, 0);
        ivertexbuilder.addVertex(matrix4f, -1, 1, 0).setColor(255, 255, 255, 255).setUv(0, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(pPackedLight, pPackedLight).setNormal(0, 1, 0);
        ivertexbuilder.addVertex(matrix4f, 1, 1, 0).setColor(255, 255, 255, 255).setUv(1, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(pPackedLight, pPackedLight).setNormal(0, 1, 0);
        ivertexbuilder.addVertex(matrix4f, 1, -1, 0).setColor(255, 255, 255, 255).setUv(1, 0 +  t * (1.0f / 4.0f)).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(pPackedLight, pPackedLight).setNormal(0, 1, 0);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

        @Override
    public ResourceLocation getTextureLocation(LightBallEntity pEntity) {
        return LIGHTBALL_TEXTURE;
    }
}
