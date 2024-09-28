package com.ryankshah.skyrimcraft.entity.creature.render;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.Vampire;
import com.ryankshah.skyrimcraft.entity.creature.model.VampireModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class VampireRenderer extends HumanoidMobRenderer<Vampire, VampireModel>
{
    private static final ResourceLocation MODEL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/vampire.png");

    public VampireRenderer(EntityRendererProvider.Context context) {
        super(context, new VampireModel(context.bakeLayer(VampireModel.LAYER_LOCATION)), 1.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(Vampire vampire) {
        return MODEL_TEXTURE;
    }
}
