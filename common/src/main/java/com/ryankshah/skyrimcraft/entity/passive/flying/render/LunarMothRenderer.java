package com.ryankshah.skyrimcraft.entity.passive.flying.render;

import com.ryankshah.skyrimcraft.entity.passive.flying.LunarMoth;
import com.ryankshah.skyrimcraft.entity.passive.flying.model.LunarMothModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LunarMothRenderer extends GeoEntityRenderer<LunarMoth>
{
    public LunarMothRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx, new LunarMothModel());
        this.shadowRadius = 0.5f;
    }
}