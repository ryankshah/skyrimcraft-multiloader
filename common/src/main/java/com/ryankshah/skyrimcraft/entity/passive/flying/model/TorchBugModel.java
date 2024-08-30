package com.ryankshah.skyrimcraft.entity.passive.flying.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.passive.flying.TorchBug;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class TorchBugModel extends GeoModel<TorchBug>
{
    @Override
    public ResourceLocation getModelResource(TorchBug object)
    {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "geo/torchbug.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TorchBug object)
    {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/torchbug.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TorchBug object)
    {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "animations/torchbug.animation.json");
    }
}
