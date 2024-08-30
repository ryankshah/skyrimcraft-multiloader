package com.ryankshah.skyrimcraft.character.magic.entity.render;

import com.ryankshah.skyrimcraft.character.magic.entity.DisarmEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DisarmRenderer extends EntityRenderer<DisarmEntity> {
    public DisarmRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(DisarmEntity pEntity) {
        return null;
    }
}
