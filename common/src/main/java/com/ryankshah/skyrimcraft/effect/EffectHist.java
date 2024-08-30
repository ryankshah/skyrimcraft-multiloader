package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EffectHist extends MobEffect
{
    protected EffectHist(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier) {
        int i = 50 >> amplifier;
        return i > 0 ? tickCount % i == 0 : true; // every 1 second
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
            livingEntity.heal(4F); // 10x faster (technically 10.0F compared to 1.0F by regen effect, but lets do this)
        }
//        if (livingEntity instanceof Player player) {
//            player.heal();
//            player.getFoodData()..regenHunger(2 * amplifier); // Regen 2 hunger points per amplifier level every tick
//        }
        return super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
