package com.ryankshah.skyrimcraft.entity.creature.render;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.Draugr;
import com.ryankshah.skyrimcraft.entity.creature.model.DraugrModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class DraugrRenderer extends HumanoidMobRenderer<Draugr, DraugrModel<Draugr>>
{
    public DraugrRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, DraugrModel.LAYER_LOCATION, DraugrModel.INNER_ARMOR_LAYER_LOCATION, DraugrModel.OUTER_ARMOR_LAYER_LOCATION);
    }
    public DraugrRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation pDraugrLayer, ModelLayerLocation pInnerModelLayer, ModelLayerLocation pOuterModelLayer) {
        super(pContext, new DraugrModel<>(pContext.bakeLayer(pDraugrLayer)), 0.5F);
        this.addLayer(
                new HumanoidArmorLayer<>(
                        this, new DraugrModel<>(pContext.bakeLayer(pInnerModelLayer)), new DraugrModel<>(pContext.bakeLayer(pOuterModelLayer)), pContext.getModelManager()
                )
        );
    }

    @Override
    public ResourceLocation getTextureLocation(Draugr pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/draugr.png");
    }
}