package com.ryankshah.skyrimcraft.entity.boss.dragon.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.boss.dragon.SkyrimDragon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SkyrimDragonModel extends GeoModel<SkyrimDragon>
{
    @Override
    public ResourceLocation getModelResource(SkyrimDragon object)
    {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "geo/dragon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SkyrimDragon object) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/normal_dragon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SkyrimDragon object)
    {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "animations/dragon.animation.json");
    }
}