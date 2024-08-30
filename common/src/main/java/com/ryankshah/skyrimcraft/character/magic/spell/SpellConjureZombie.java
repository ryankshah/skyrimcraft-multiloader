package com.ryankshah.skyrimcraft.character.magic.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.goal.ConjuredFollowOwnerGoal;
import com.ryankshah.skyrimcraft.goal.ConjuredOwnerHurtByTargetGoal;
import com.ryankshah.skyrimcraft.goal.ConjuredOwnerHurtTargetGoal;
import com.ryankshah.skyrimcraft.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class SpellConjureZombie extends Spell
{
    public SpellConjureZombie(int identifier) {
        super(identifier, "conjure_zombie");
    }

    @Override
    public String getName() {
        return "Conjure Zombie";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Summons a Zombie to");
        desc.add("fight for you");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/conjure_zombie.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/conjure_zombie.png");
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.ZOMBIE_INFECT;
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
        if(getCaster() instanceof ServerPlayer && !getCaster().level().isClientSide) {
            // TODO: check if there are any other conjured entities
            Zombie zombie = getCaster().level().getNearestEntity(Zombie.class, TargetingConditions.DEFAULT, getCaster(), getCaster().getX(), getCaster().getY(), getCaster().getZ(),
                    new AABB(getCaster().getX() - 10f, getCaster().getY() - 10f, getCaster().getZ() - 10f, getCaster().getX() + 10f, getCaster().getY() + 10f, getCaster().getZ() + 10f));
            if(zombie != null && Services.PLATFORM.doesEntityHavePersistentData(zombie, Constants.MODID + "_" + getCaster().getUUID() + "_conjuredzombie_timeToKill")) {
                getCaster().displayClientMessage(Component.translatable(Constants.MODID + ".conjuredzombie.exists"), false);
            } else {
                Zombie zombieEntity = new Zombie(EntityType.ZOMBIE, getCaster().level());
                zombieEntity.setPos(getCaster().getX(), getCaster().getY() + 0.2f, getCaster().getZ());
//                zombieEntity.setSunSensitive(false); // TODO: add a mixin to Zombie to make this method and make the `isSunSensitive` public
                zombieEntity.goalSelector.removeAllGoals(g -> true);
                zombieEntity.targetSelector.removeAllGoals(g -> true);
                zombieEntity.goalSelector.addGoal(5, new MeleeAttackGoal(zombieEntity, 1.0, true));
                zombieEntity.goalSelector.addGoal(6, new ConjuredFollowOwnerGoal(zombieEntity, getCaster(), 1.0f, 10.0f, 2.0f, false));
                zombieEntity.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(zombieEntity, 1.0));
                zombieEntity.goalSelector.addGoal(10, new RandomLookAroundGoal(zombieEntity));
                zombieEntity.targetSelector.addGoal(1, new ConjuredOwnerHurtByTargetGoal(zombieEntity, getCaster()));
                zombieEntity.targetSelector.addGoal(2, new ConjuredOwnerHurtTargetGoal(zombieEntity, getCaster()));
//                zombieEntity.setTame(true);
                zombieEntity.setHealth(40f);
                zombieEntity.addEffect(new MobEffectInstance(ModEffects.SPECTRAL.asHolder(), 60 * 20, 0, false, true, true));
//                zombieEntity.setOwnerUUID(getCaster().getUUID());
                Services.PLATFORM.setEntityPersistentData(zombie, Constants.MODID + "_" + getCaster().getUUID() + "_conjuredzombie_timeToKill", getCaster().level().getGameTime() + (60L * 20L));
                getCaster().level().addFreshEntity(zombieEntity);

                super.onCast();
            }
        }
    }
}