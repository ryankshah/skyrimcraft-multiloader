package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectIncreasedArmorRating extends InstantenousMobEffect
{
    protected EffectIncreasedArmorRating(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        return super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
