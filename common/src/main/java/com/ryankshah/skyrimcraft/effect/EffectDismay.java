package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectDismay extends MobEffect
{
    protected EffectDismay(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        return super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
