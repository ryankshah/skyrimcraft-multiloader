package com.ryankshah.skyrimcraft.character.magic.shout;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.util.RayTraceUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ShoutDragonrend extends Spell //implements IForgeRegistryEntry<ISpell>
{
    public ShoutDragonrend(int identifier) {
        super(identifier, "dragonrend");
    }

    @Override
    public String getName() {
        return "Dragonrend";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Your voice lashes out at a");
        desc.add("dragon's very soul, forcing");
        desc.add("the Ender Dragon to land");
        return desc;
    }

    @Override
    public SoundEvent getSound() {
        //return ModSounds.UNRELENTING_FORCE.get();
        return SoundEvents.ENDER_DRAGON_GROWL;
    }

    @Override
    public float getSoundLength() {
        return 0f;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/dragonrend.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/dragonrend.png");
    }

    @Override
    public float getCost() {
        return 0f;
    }

    @Override
    public float getCooldown() {
        return 60.0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.SHOUT;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NA;
    }

    @Override
    public void onCast() {
        Level world = getCaster().level();
        Entity rayTracedEntity = RayTraceUtil.rayTrace(world, getCaster(), 200D);
        if(rayTracedEntity instanceof EnderDragon) {
            EnderDragon dragon = (EnderDragon) rayTracedEntity;

            dragon.getPhaseManager().setPhase(EnderDragonPhase.LANDING);

            super.onCast();
        } else {
            getCaster().displayClientMessage(Component.translatable("skyrimcraft.shout.notarget"), false);
        }
    }
}