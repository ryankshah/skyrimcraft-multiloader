package com.ryankshah.skyrimcraft.entity.passive.flying.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.passive.flying.BlueButterfly;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class BlueButterflyModel extends GeoModel<BlueButterfly>
{
    @Override
    public ResourceLocation getModelResource(BlueButterfly object)
    {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "geo/bluebutterfly.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlueButterfly object)
    {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/bluebutterfly.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlueButterfly object)
    {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "animations/bluebutterfly.animation.json");
    }
}
