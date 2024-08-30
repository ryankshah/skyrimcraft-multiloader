package com.ryankshah.skyrimcraft.entity.creature.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.DwarvenSpider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DwarvenSpiderModel extends GeoModel<DwarvenSpider>
{
    @Override
    public ResourceLocation getModelResource(DwarvenSpider object)
    {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "geo/dwarven_spider.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DwarvenSpider object) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/dwarven_spider.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DwarvenSpider object)
    {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "animations/dwarven_spider.animation.json");
    }
}