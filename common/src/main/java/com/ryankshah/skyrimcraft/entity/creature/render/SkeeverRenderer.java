package com.ryankshah.skyrimcraft.entity.creature.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.Skeever;
import com.ryankshah.skyrimcraft.entity.creature.model.SkeeverModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SkeeverRenderer extends MobRenderer<Skeever, SkeeverModel<Skeever>>
{
    private static final ResourceLocation MODEL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/skeever.png");

    public SkeeverRenderer(EntityRendererProvider.Context p_174428_) {
        super(p_174428_, new SkeeverModel<>(p_174428_.bakeLayer(SkeeverModel.LAYER_LOCATION)), 0.15F);
    }

    public void render(Skeever pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Skeever pEntity) {
        return MODEL_TEXTURE;
    }

    protected void setupRotations(Skeever pEntityLiving, PoseStack pPoseStack, float pBob, float pYBodyRot, float pPartialTick, float pScale) {
        super.setupRotations(pEntityLiving, pPoseStack, pBob, pYBodyRot, pPartialTick, pScale);
    }
}
