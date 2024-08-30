package com.ryankshah.skyrimcraft.entity.creature.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.CyrodilicSpadetail;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CyrodilicSpadetailRenderer extends MobRenderer<CyrodilicSpadetail, ColorableHierarchicalModel<CyrodilicSpadetail>>
{
    private final ColorableHierarchicalModel<CyrodilicSpadetail> modelA = this.getModel();
    private static final ResourceLocation MODEL_A_TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/cyrodilic_spadetail.png");

    public CyrodilicSpadetailRenderer(EntityRendererProvider.Context p_174428_) {
        super(p_174428_, new TropicalFishModelA<>(p_174428_.bakeLayer(ModelLayers.TROPICAL_FISH_SMALL)), 0.15F);
//        this.addLayer(new TropicalFishPatternLayer(this, p_174428_.getModelSet()));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(CyrodilicSpadetail pEntity) {
        return MODEL_A_TEXTURE;
    }

    public void render(CyrodilicSpadetail pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.model = modelA;
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    protected void setupRotations(CyrodilicSpadetail pEntityLiving, PoseStack pPoseStack, float pBob, float pYBodyRot, float pPartialTick, float pScale) {
        super.setupRotations(pEntityLiving, pPoseStack, pBob, pYBodyRot, pPartialTick, pScale);
        float f = 4.3F * Mth.sin(0.6F * pPartialTick);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(f));
        if (!pEntityLiving.isInWater()) {
            pPoseStack.translate(0.2F, 0.1F, 0.0F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }
}
