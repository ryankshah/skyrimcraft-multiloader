package com.ryankshah.skyrimcraft.entity.creature.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.ValeSabreCat;
import com.ryankshah.skyrimcraft.entity.creature.model.ValeSabreCatModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ValeSabreCatRenderer extends GeoEntityRenderer<ValeSabreCat>
{
    public ValeSabreCatRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new ValeSabreCatModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
        this.addRenderLayer(new SabreCatEyesLayer(this));
    }

    class SabreCatEyesLayer extends GeoRenderLayer<ValeSabreCat>
    {
        private GeoRenderer<ValeSabreCat> entityIGeoRenderer;

        public SabreCatEyesLayer(GeoRenderer<ValeSabreCat> entityRendererIn) {
            super(entityRendererIn);
            this.entityIGeoRenderer = entityRendererIn;
        }

        @Override
        public void render(PoseStack poseStack, ValeSabreCat animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
//            ResourceLocation eyes = ResourceLocation.fromNamespaceAndPath(Skyrimcraft.MODID, "textures/entity/sabre_cat_e.png");
            ResourceLocation eyes = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/vale_sabre_cat_e.png");
            if(animatable.level().getDayTime() > 12542) {
                RenderType type = RenderType.eyes(eyes);
                getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, type,
                        bufferSource.getBuffer(type), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                        1);
            }
        }
    }
}