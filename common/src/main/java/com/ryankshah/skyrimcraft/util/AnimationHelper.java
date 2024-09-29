package com.ryankshah.skyrimcraft.util;

import net.minecraft.client.animation.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import org.joml.Vector3f;

import java.util.*;

public final class AnimationHelper
{
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    /**
     * Animate the given {@link IAnimatedModel} based on the given {@link AnimationState} of the given
     * {@link AnimationDefinition} at the given time with the given speed
     * @param model The model being animated
     * @param state The current state of the animation
     * @param definition The animation being played
     * @param time The absolute time at which the animation is being played
     */
    public static void animate(IAnimatedModel<?> model, AnimationState state, AnimationDefinition definition, float time)
    {
        animate(model, state, definition, time, 1F);
    }

    /**
     * Animate the given {@link IAnimatedModel} based on the given {@link AnimationState} of the given
     * {@link AnimationDefinition} at the given time with the given speed
     * @param model The model being animated
     * @param state The current state of the animation
     * @param definition The animation being played
     * @param time The absolute time at which the animation is being played
     * @param speed The speed at which the animation should be played
     */
    public static void animate(
            IAnimatedModel<?> model, AnimationState state, AnimationDefinition definition, float time, float speed
    )
    {
        state.updateTime(time, speed);
        state.ifStarted(animState -> animate(
                model, definition, animState.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE
        ));
    }

    /**
     * Apply the initial state (time = 0) of the given {@link AnimationDefinition} to the given {@link IAnimatedModel}
     * @param model The model being animated
     * @param definition The animation to be applied
     */
    public static void applyStatic(IAnimatedModel<?> model, AnimationDefinition definition)
    {
        animate(model, definition, 0, 1F, ANIMATION_VECTOR_CACHE);
    }

    /**
     * Animate the given {@link IAnimatedModel} based on the given {@link AnimationState} of the given
     * {@link AnimationDefinition} at the given time with the given speed
     * @param model The model being animated
     * @param definition The animation being played
     * @param accumulatedTime The accumulated time of the animation being played
     * @param scale The scale to be applied to the outputs of the animation
     * @param animVecCache A cache vector to avoid allocation new vector instances for the animation outputs
     */
    public static void animate(
            IAnimatedModel<?> model,
            AnimationDefinition definition,
            long accumulatedTime,
            float scale,
            Vector3f animVecCache
    )
    {
        float elapsed = getElapsedSeconds(definition, accumulatedTime);

        for (Map.Entry<String, List<AnimationChannel>> anims : definition.boneAnimations().entrySet())
        {
            model.findChildPart(anims.getKey()).ifPresent(part -> anims.getValue().forEach((channel) ->
            {
                Keyframe[] frames = channel.keyframes();
                int frameIdx = Math.max(
                        0,
                        Mth.binarySearch(0, frames.length, idx -> elapsed <= frames[idx].timestamp()) - 1
                );
                int nextFrameIdx = Math.min(frames.length - 1, frameIdx + 1);
                Keyframe frame = frames[frameIdx];
                Keyframe nextFrame = frames[nextFrameIdx];

                float remaining = elapsed - frame.timestamp();
                float delta = 0.0F;
                if (nextFrameIdx != frameIdx)
                {
                    float dist = nextFrame.timestamp() - frame.timestamp();
                    delta = Mth.clamp(remaining / dist, 0.0F, 1.0F);
                }

                nextFrame.interpolation().apply(animVecCache, delta, frames, frameIdx, nextFrameIdx, scale);
                channel.target().apply(part, animVecCache);
            }));
        }
    }

    public static float getElapsedSeconds(AnimationDefinition definition, long accumulatedTime)
    {
        float timeInSecs = (float) accumulatedTime / 1000.0F;
        return definition.looping() ? (timeInSecs % definition.lengthInSeconds()) : timeInSecs;
    }



    private AnimationHelper() { }
}