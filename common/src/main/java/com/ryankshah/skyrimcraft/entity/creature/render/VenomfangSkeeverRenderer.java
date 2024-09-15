package com.ryankshah.skyrimcraft.entity.creature.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.Skeever;
import com.ryankshah.skyrimcraft.entity.creature.VenomfangSkeever;
import com.ryankshah.skyrimcraft.entity.creature.model.SkeeverModel;
import com.ryankshah.skyrimcraft.entity.creature.model.VenomfangSkeeverModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class VenomfangSkeeverRenderer extends MobRenderer<VenomfangSkeever, VenomfangSkeeverModel<VenomfangSkeever>>
{
    private static final ResourceLocation MODEL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/venomfang_skeever.png");

    public VenomfangSkeeverRenderer(EntityRendererProvider.Context p_174428_) {
        super(p_174428_, new VenomfangSkeeverModel<>(p_174428_.bakeLayer(VenomfangSkeeverModel.LAYER_LOCATION)), 0.15F);
    }

    public void render(VenomfangSkeever pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(VenomfangSkeever pEntity) {
        return MODEL_TEXTURE;
    }

    protected void setupRotations(VenomfangSkeever pEntityLiving, PoseStack pPoseStack, float pBob, float pYBodyRot, float pPartialTick, float pScale) {
        super.setupRotations(pEntityLiving, pPoseStack, pBob, pYBodyRot, pPartialTick, pScale);
    }
}
