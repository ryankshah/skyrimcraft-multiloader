package com.ryankshah.skyrimcraft.character.feature.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.feature.model.DunmerEarModel;
import com.ryankshah.skyrimcraft.character.feature.model.HighElfEarModel;
import com.ryankshah.skyrimcraft.character.feature.model.KhajiitHeadModel;
import com.ryankshah.skyrimcraft.character.feature.model.KhajiitTailModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class RenderRaceLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>
{
    private final HighElfEarModel highElfEarModel;
    private final DunmerEarModel dunmerEarModel;
    private final KhajiitHeadModel khajiitHeadModel;
    private final KhajiitTailModel khajiitTailModel;

    public RenderRaceLayer(PlayerRenderer entityRenderer) {
        super(entityRenderer);

        highElfEarModel = new HighElfEarModel(Minecraft.getInstance().getEntityModels().bakeLayer(HighElfEarModel.LAYER_LOCATION));
        dunmerEarModel = new DunmerEarModel(Minecraft.getInstance().getEntityModels().bakeLayer(DunmerEarModel.LAYER_LOCATION));
        khajiitHeadModel = new KhajiitHeadModel(Minecraft.getInstance().getEntityModels().bakeLayer(KhajiitHeadModel.LAYER_LOCATION));
        khajiitTailModel = new KhajiitTailModel(Minecraft.getInstance().getEntityModels().bakeLayer(KhajiitTailModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, AbstractClientPlayer pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        Race race = Character.get(pLivingEntity).getRace();
        if (race.getId() == Race.ALTMER.getId() || race.getId() == Race.BOSMER.getId())
            renderAltmer(pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTick, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        else if (race.getId() == Race.DUNMER.getId())
            renderDunmer(pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTick, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        else if (race.getId() == Race.KHAJIIT.getId()) {
            renderKhajiit(pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTick, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        }
    }

    private void renderAltmer(PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight, AbstractClientPlayer playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer ivertexbuilder = renderBuffer.getBuffer(RenderType.entitySolid(playerEntity.getSkin().texture()));
        int overlayCoords = PlayerRenderer.getOverlayCoords(playerEntity, 0.0F);

        matrixStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(matrixStack);
        //matrixStack.mulPose(YP.rotationDegrees(180F));
        getParentModel().copyPropertiesTo(highElfEarModel);
        highElfEarModel.renderToBuffer(matrixStack, ivertexbuilder, packedLight, overlayCoords, 0xFFFFFFFF);
        matrixStack.popPose();
    }

    private void renderDunmer(PoseStack matrixStack, MultiBufferSource renderBuffer, int packedLight, AbstractClientPlayer playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer ivertexbuilder = renderBuffer.getBuffer(RenderType.entitySolid(playerEntity.getSkin().texture()));
        int overlayCoords = PlayerRenderer.getOverlayCoords(playerEntity, 0.0F);

        matrixStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(matrixStack);
        //matrixStack.mulPose(YP.rotationDegrees(180F));
        getParentModel().copyPropertiesTo(dunmerEarModel);
        dunmerEarModel.renderToBuffer(matrixStack, ivertexbuilder, packedLight, overlayCoords, 0xFFFFFFFF);
        matrixStack.popPose();
    }

    private void renderKhajiit(PoseStack poseStack, MultiBufferSource renderBuffer, int packedLight, AbstractClientPlayer playerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        VertexConsumer ivertexbuilder = renderBuffer.getBuffer(RenderType.entitySolid(playerEntity.getSkin().texture()));
        int overlayCoords = PlayerRenderer.getOverlayCoords(playerEntity, 0.0F);

        poseStack.pushPose();
        this.getParentModel().getHead().translateAndRotate(poseStack);
        getParentModel().copyPropertiesTo(khajiitHeadModel);
        khajiitHeadModel.renderToBuffer(poseStack, ivertexbuilder, packedLight, overlayCoords, 0xFFFFFFFF);
        poseStack.popPose();

        poseStack.pushPose();
        getParentModel().copyPropertiesTo(khajiitTailModel);
        khajiitTailModel.renderToBuffer(poseStack, ivertexbuilder, packedLight, overlayCoords, 0xFFFFFFFF);
        poseStack.popPose();
    }
}