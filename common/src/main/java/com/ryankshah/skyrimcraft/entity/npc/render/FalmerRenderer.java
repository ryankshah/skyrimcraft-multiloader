package com.ryankshah.skyrimcraft.entity.npc.render;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.npc.Falmer;
import com.ryankshah.skyrimcraft.entity.npc.layer.FalmerProfessionLayer;
import com.ryankshah.skyrimcraft.entity.npc.model.FalmerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class FalmerRenderer extends HumanoidMobRenderer<Falmer, FalmerModel<Falmer>>
{
    public FalmerRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, FalmerModel.LAYER_LOCATION, FalmerModel.INNER_ARMOR_LAYER_LOCATION, FalmerModel.OUTER_ARMOR_LAYER_LOCATION);
    }
    public FalmerRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation pFalmerLayer, ModelLayerLocation pInnerModelLayer, ModelLayerLocation pOuterModelLayer) {
        super(pContext, new FalmerModel<>(pContext.bakeLayer(pFalmerLayer)), 0.5F);
        this.addLayer(
                new HumanoidArmorLayer<>(
                        this, new FalmerModel<>(pContext.bakeLayer(pInnerModelLayer)), new FalmerModel<>(pContext.bakeLayer(pOuterModelLayer)), pContext.getModelManager()
                )
        );
        this.addLayer(new FalmerProfessionLayer<>(this, pContext.getResourceManager(), "falmer"));
    }

    @Override
    public ResourceLocation getTextureLocation(Falmer pEntity) {
        return pEntity.getRandom().nextInt(1,2) == 1 ? ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/falmer_1.png") : ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/falmer_2.png");
    }
}