package com.ryankshah.skyrimcraft.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * to prevent attacking for players i cancel the InteractionKeyMappingTriggered event and for mobs
 *  i have a mixin in MeleeAttackGoal::canUse and MeleeAttackGoal::canContinueToUse
 *
 * to prevent moving i have a redirect mixin in the LivingEntity::travel that calls the original function with Vec3.ZERO
 * i also cancel the LivingEntity::jumpInLiquid function
 */

@Mixin(MeleeAttackGoal.class)
public abstract class MeleeAttackGoalMixin extends Goal
{
    @Shadow
    private PathfinderMob mob;

    @ModifyReturnValue(method = "canUse", at = @At("RETURN"))
    private boolean canUseIfParalysed(boolean original) {
        if(mob.hasEffect(ModEffects.PARALYSIS.asHolder())) {
            return false;
        }
        return original;
    }

    @ModifyReturnValue(method = "canContinueToUse", at = @At("RETURN"))
    private boolean canContinueToUseIfParalysed(boolean original) {
        if(mob.hasEffect(ModEffects.PARALYSIS.asHolder())) {
            return false;
        }
        return original;
    }
}
