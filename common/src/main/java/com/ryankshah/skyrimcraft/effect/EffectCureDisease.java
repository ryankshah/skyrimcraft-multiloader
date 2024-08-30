package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectCureDisease extends InstantenousMobEffect
{
    protected EffectCureDisease(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        entity.getActiveEffects().stream()
                .filter(mobEffectInstance -> mobEffectInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL)
                .forEach(
                        mobEffectInstance -> entity.removeEffect(mobEffectInstance.getEffect())
                );
        return super.applyEffectTick(entity, amplifier);
    }
}