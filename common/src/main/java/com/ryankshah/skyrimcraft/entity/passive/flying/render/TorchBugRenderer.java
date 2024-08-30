package com.ryankshah.skyrimcraft.entity.passive.flying.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.passive.flying.TorchBug;
import com.ryankshah.skyrimcraft.entity.passive.flying.model.TorchBugModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class TorchBugRenderer extends GeoEntityRenderer<TorchBug>
{
    public TorchBugRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new TorchBugModel());
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new TorchBugButtLayer(this));
    }

    class TorchBugButtLayer extends GeoRenderLayer<TorchBug>
    {
        private GeoRenderer<TorchBug> entityIGeoRenderer;

        public TorchBugButtLayer(GeoRenderer<TorchBug> entityRendererIn) {
            super(entityRendererIn);
            this.entityIGeoRenderer = entityRendererIn;
        }

        @Override
        public void render(PoseStack poseStack, TorchBug animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            ResourceLocation eyes = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/torchbug_e.png");

            RenderType type = RenderType.eyes(eyes);
            getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, type,
                    bufferSource.getBuffer(type), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    1);
        }
    }
}
