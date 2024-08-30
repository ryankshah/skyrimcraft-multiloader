package com.ryankshah.skyrimcraft.entity.creature.render;

import com.ryankshah.skyrimcraft.entity.creature.Mammoth;
import com.ryankshah.skyrimcraft.entity.creature.model.MammothModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MammothRenderer extends GeoEntityRenderer<Mammoth>
{
    public MammothRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new MammothModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }
}
