package com.ryankshah.skyrimcraft.entity.creature.render;

import com.ryankshah.skyrimcraft.entity.creature.Giant;
import com.ryankshah.skyrimcraft.entity.creature.model.GiantModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GiantRenderer extends GeoEntityRenderer<Giant>
{
    public GiantRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GiantModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }
}