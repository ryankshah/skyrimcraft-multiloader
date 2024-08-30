package com.ryankshah.skyrimcraft.effect;

import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class EffectSpectral extends MobEffect
{
    protected EffectSpectral(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    // Whether the effect should apply this tick. Used e.g. by the Regeneration effect that only applies
    // once every x ticks, depending on the tick count and amplifier.
    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier) {
        return true; //tickCount % 2 == 0; // replace this with whatever check you want
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if(livingEntity.level() instanceof ServerLevel) {
            ServerLevel world = (ServerLevel)livingEntity.level();
            float radius = 0.5f;
            // Get origins
            Vec3 origin = Vec3.ZERO;
            Vec3 normal = new Vec3(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());

            Set<Vec3> circlePoints = ClientUtil.circle(origin, normal, radius, 8);
            for(Vec3 point : circlePoints) {
                world.sendParticles(ParticleTypes.ASH, livingEntity.getX() + point.x, livingEntity.getY() + 0.2f, livingEntity.getZ() + point.z, 1, point.x, 0D, point.z, 0); // set amount to 0 so particles don't fly off and stays in place
            }
        }
        return super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
