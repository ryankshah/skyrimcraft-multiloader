package com.ryankshah.skyrimcraft.entity.passive.flying.render;

import com.ryankshah.skyrimcraft.entity.passive.flying.OrangeDartwing;
import com.ryankshah.skyrimcraft.entity.passive.flying.model.OrangeDartwingModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OrangeDartwingRenderer extends GeoEntityRenderer<OrangeDartwing>
{
    public OrangeDartwingRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new OrangeDartwingModel());
        this.shadowRadius = 0.5f;
    }
}