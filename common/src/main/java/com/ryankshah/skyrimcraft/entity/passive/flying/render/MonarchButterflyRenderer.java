package com.ryankshah.skyrimcraft.entity.passive.flying.render;

import com.ryankshah.skyrimcraft.entity.passive.flying.MonarchButterfly;
import com.ryankshah.skyrimcraft.entity.passive.flying.model.MonarchButterflyModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MonarchButterflyRenderer extends GeoEntityRenderer<MonarchButterfly>
{
    public MonarchButterflyRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new MonarchButterflyModel());
        this.shadowRadius = 0.5f;
    }
}