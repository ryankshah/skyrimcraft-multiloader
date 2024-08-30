package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EffectAdrenalineRush extends MobEffect
{
    protected EffectAdrenalineRush(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier) {
        int i = 50 >> amplifier;
        return i > 0 ? tickCount % i == 0 : true; // every 1 second
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        super.applyEffectTick(livingEntity, p_76394_2_);

        if (livingEntity instanceof Player player && livingEntity.getHealth() < livingEntity.getMaxHealth()) {
            if (player.getFoodData().needsFood()) {
                player.getFoodData().eat(4, 20); // 10x faster (technically 10.0F compared to 1.0F by regen effect, but lets do this)
                return true;
            }
        }
        return false;
    }
}
