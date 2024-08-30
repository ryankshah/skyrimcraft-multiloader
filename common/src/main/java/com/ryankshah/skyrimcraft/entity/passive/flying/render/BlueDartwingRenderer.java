package com.ryankshah.skyrimcraft.entity.passive.flying.render;

import com.ryankshah.skyrimcraft.entity.passive.flying.BlueDartwing;
import com.ryankshah.skyrimcraft.entity.passive.flying.model.BlueDartwingModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlueDartwingRenderer extends GeoEntityRenderer<BlueDartwing>
{
    public BlueDartwingRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new BlueDartwingModel());
        this.shadowRadius = 0.5f;
    }
}