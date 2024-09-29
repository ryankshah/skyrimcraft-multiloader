package com.ryankshah.skyrimcraft.util;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.level.Level;

import java.util.Optional;

public interface IAnimatedModel<T>
{
    /**
     * Called before any rendering to set up the animations of this model
     * @param level The {@link Level} in which this model is being rendered
     * @param partialTicks The partial tick time this is being rendered at
     * @param ctx The custom animation context object
     */
    void setupAnimation(Level level, float partialTicks, T ctx);

    /**
     * {@return the root part of this model}
     */
    ModelPart getRoot();

    /**
     * {@return the child or child of child with the given name or empty}
     */
    default Optional<ModelPart> findChildPart(String name)
    {
        if (name.equals("root"))
        {
            return Optional.of(getRoot());
        }

        return getRoot().getAllParts()
                .filter(part -> part.hasChild(name))
                .findFirst()
                .map(part -> part.getChild(name));
    }
}
