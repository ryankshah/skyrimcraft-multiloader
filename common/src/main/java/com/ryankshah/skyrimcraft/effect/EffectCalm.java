package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.raid.Raider;

public class EffectCalm extends MobEffect
{
    protected EffectCalm(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if(livingEntity instanceof Raider raider) {
            raider.targetSelector.setControlFlag(Goal.Flag.TARGET, false);
        }

        return super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
