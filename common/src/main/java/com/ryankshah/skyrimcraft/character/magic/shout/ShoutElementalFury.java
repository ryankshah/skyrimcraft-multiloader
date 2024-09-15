package com.ryankshah.skyrimcraft.character.magic.shout;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.List;

public class ShoutElementalFury extends Spell //implements IForgeRegistryEntry<ISpell>
{
    public ShoutElementalFury(int identifier) {
        super(identifier, "elemental_fury");
    }

    @Override
    public String getName() {
        return "Elemental Fury";
    }

    @Override
    public String getShoutName() {
        return "Su Grah Dun";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("The Thu'um imbues your arms");
        desc.add("with the speed of wind, allowing");
        desc.add("for faster weapon strikes");
        return desc;
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.DRAGON_FIREBALL_EXPLODE;
    }

    @Override
    public float getSoundLength() {
        return 0f;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/elemental_fury.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/elemental_fury.png");
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
        getCaster().addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 30*20, 4, false, true, false));
    }
}