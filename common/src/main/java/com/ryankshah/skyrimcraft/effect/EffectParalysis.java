package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class EffectParalysis extends MobEffect
{
    protected EffectParalysis(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if(livingEntity instanceof Mob mob) {
            mob.getMoveControl().setWantedPosition(mob.getX(), mob.getY(), mob.getZ(), mob.getSpeed());
            mob.goalSelector.disableControlFlag(Goal.Flag.MOVE);
            mob.goalSelector.disableControlFlag(Goal.Flag.JUMP);
            mob.goalSelector.disableControlFlag(Goal.Flag.LOOK);
            mob.goalSelector.disableControlFlag(Goal.Flag.TARGET);
        }
        return super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
