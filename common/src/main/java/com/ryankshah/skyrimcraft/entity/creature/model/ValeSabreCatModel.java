package com.ryankshah.skyrimcraft.entity.creature.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.ValeSabreCat;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class ValeSabreCatModel extends GeoModel<ValeSabreCat>
{
    @Override
    public ResourceLocation getModelResource(ValeSabreCat animatable) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "geo/sabre_cat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ValeSabreCat animatable) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/vale_sabre_cat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ValeSabreCat animatable) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "animations/sabre_cat.animation.json");
    }
}