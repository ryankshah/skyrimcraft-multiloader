package com.ryankshah.skyrimcraft.entity.creature.render;

import com.ryankshah.skyrimcraft.entity.creature.DwarvenSpider;
import com.ryankshah.skyrimcraft.entity.creature.model.DwarvenSpiderModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DwarvenSpiderRenderer extends GeoEntityRenderer<DwarvenSpider>
{
    public DwarvenSpiderRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DwarvenSpiderModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }
}