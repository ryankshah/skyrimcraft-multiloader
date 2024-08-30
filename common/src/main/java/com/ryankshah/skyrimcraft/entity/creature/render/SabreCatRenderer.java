package com.ryankshah.skyrimcraft.entity.creature.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.SabreCat;
import com.ryankshah.skyrimcraft.entity.creature.model.SabreCatModel;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.Arrays;
import java.util.List;

public class SabreCatRenderer extends GeoEntityRenderer<SabreCat>
{
    public SabreCatRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new SabreCatModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
        this.addRenderLayer(new SabreCatEyesLayer(this));
    }

    class SabreCatEyesLayer extends GeoRenderLayer<SabreCat>
    {
        private final Int2ObjectMap<ResourceLocation> EYES_MAP = Util.make(new Int2ObjectOpenHashMap<>(), (p_215348_0_) -> {
            p_215348_0_.put(1, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/sabre_cat_e.png"));
            p_215348_0_.put(2, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/snowy_sabre_cat_e.png"));
        });
        private final List<ResourceKey<Biome>> SNOWY_BIOMES = Arrays.asList(
                Biomes.SNOWY_BEACH, Biomes.SNOWY_TAIGA,
                        Biomes.SNOWY_SLOPES, Biomes.SNOWY_PLAINS,
                        Biomes.FROZEN_RIVER, Biomes.FROZEN_PEAKS,
                        Biomes.ICE_SPIKES, Biomes.GROVE,
                        Biomes.JAGGED_PEAKS
        );
        private GeoRenderer<SabreCat> entityIGeoRenderer;

        public SabreCatEyesLayer(GeoRenderer<SabreCat> entityRendererIn) {
            super(entityRendererIn);
            this.entityIGeoRenderer = entityRendererIn;
        }

        protected ResourceLocation getTextureResource(SabreCat animatable) {
            return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/sabre_cat_e.png");
        }

        @Override
        public void render(PoseStack poseStack, SabreCat animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
//            ResourceLocation eyes = ResourceLocation.fromNamespaceAndPath(Skyrimcraft.MODID, "textures/entity/sabre_cat_e.png");
            ResourceLocation eyes = EYES_MAP.getOrDefault(SNOWY_BIOMES.contains(animatable.getBiomeType()) ? 2 : 1, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/sabre_cat_e.png"));
            if(animatable.level().getDayTime() > 12542) {
                RenderType type = RenderType.eyes(eyes);
                getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, type,
                        bufferSource.getBuffer(type), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                        1);
            }
        }
    }
}