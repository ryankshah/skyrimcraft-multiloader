package com.ryankshah.skyrimcraft.character.magic.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class SpellConjureFamiliar extends Spell
{
    public SpellConjureFamiliar(int identifier) {
        super(identifier, "conjure_familiar");
    }

    @Override
    public String getName() {
        return "Conjure Familiar";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Summons a familiar for 60");
        desc.add("seconds wherever the");
        desc.add("caster is pointing");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/conjure_familiar.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/conjure_familiar.png");
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.WOLF_GROWL;
    }

    @Override
    public float getCost() {
        return 12.0f;
    }

    @Override
    public float getCooldown() {
        return 0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.CONJURATION;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NOVICE;
    }

    @Override
    public int getBaseXp() {
        return 4;
    }

    @Override
    public void onCast() {
        if(getCaster() instanceof ServerPlayer && !getCaster().level().isClientSide && canCast() == CastResult.SUCCESS) {
            // TODO: check if there are any other conjured entities
            Wolf wolf = getCaster().level().getNearestEntity(Wolf.class, TargetingConditions.DEFAULT, getCaster(), getCaster().getX(), getCaster().getY(), getCaster().getZ(),
                    new AABB(getCaster().getX() - 10f, getCaster().getY() - 10f, getCaster().getZ() - 10f, getCaster().getX() + 10f, getCaster().getY() + 10f, getCaster().getZ() + 10f));
            if(wolf != null && Services.PLATFORM.doesEntityHavePersistentData(wolf, Constants.MODID+"_conjuredfamiliar_timeToKill")) {
                getCaster().displayClientMessage(Component.translatable(Constants.MODID + ".conjuredfamiliar.exists"), false);
            } else {
                Wolf wolfEntity = new Wolf(EntityType.WOLF, getCaster().level());
                wolfEntity.setPos(getCaster().getX(), getCaster().getY() + 0.2f, getCaster().getZ());
                wolfEntity.setTame(true, false);
                wolfEntity.setHealth(40f);
                wolfEntity.addEffect(new MobEffectInstance(ModEffects.SPECTRAL.asHolder(), 60 * 20, 0, false, true, true));
                wolfEntity.setOwnerUUID(getCaster().getUUID());
                Services.PLATFORM.setEntityPersistentData(wolfEntity, Constants.MODID + "_conjuredfamiliar_timeToKill", getCaster().level().getGameTime() + (60L * 20L));
                getCaster().level().addFreshEntity(wolfEntity);

                super.onCast();
            }
        }
    }
}