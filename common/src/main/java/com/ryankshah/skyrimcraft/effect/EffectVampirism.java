package com.ryankshah.skyrimcraft.effect;

import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.network.character.UpdateExtraCharacter;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;

public class EffectVampirism extends MobEffect
{
    protected EffectVampirism(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof Player player) {
            ExtraCharacter extraCharacter = Services.PLATFORM.getExtraCharacter(player);
            int infectionTime = extraCharacter.getInfectionTime();
            extraCharacter.addToInfectionTime(1);

            Dispatcher.sendToServer(new UpdateExtraCharacter(extraCharacter));

            // Check for vampirism progression
            if (infectionTime > 60 * 20 && infectionTime % (20 * 60) == 0) { // Every minute after the first hour
                if (player.level().isNight()) {
                    player.displayClientMessage(Component.literal("You feel a strange thirst as the sun sets."), true);
                } else {
                    player.displayClientMessage(Component.literal("You feel weakened by the sunlight."), true);
                }
                // TODO: Add a red flash effect here (need to implement this on the client side)
            }

            // Check for full vampirism
            if (infectionTime >= 72 * 60 * 20) { // 72 hours
                extraCharacter.setVampire();
                extraCharacter.setVampireAfflicted();
                // Transform the player into a vampire here
                player.displayClientMessage(Component.literal("You have become a vampire!"), false);
                // Remove the effect as the transformation is complete
                player.removeEffect(ModEffects.SANGUINARE_VAMPIRIS.asHolder());
            }
        }
        return super.applyEffectTick(entityLivingBaseIn, amplifier);
    }
}
