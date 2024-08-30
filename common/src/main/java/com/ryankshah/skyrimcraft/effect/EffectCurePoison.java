package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class EffectCurePoison extends InstantenousMobEffect
{
    protected EffectCurePoison(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if(entity.hasEffect(MobEffects.POISON))
            entity.removeEffect(MobEffects.POISON);
        return super.applyEffectTick(entity, amplifier);
    }
}