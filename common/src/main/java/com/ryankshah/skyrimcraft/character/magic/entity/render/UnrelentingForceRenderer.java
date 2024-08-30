package com.ryankshah.skyrimcraft.character.magic.entity.render;

import com.ryankshah.skyrimcraft.character.magic.entity.UnrelentingForceEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class UnrelentingForceRenderer extends EntityRenderer<UnrelentingForceEntity> {
    public UnrelentingForceRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(UnrelentingForceEntity pEntity) {
        return null;
    }
}
