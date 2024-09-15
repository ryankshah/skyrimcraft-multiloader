package com.ryankshah.skyrimcraft.character.magic.shout;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class ShoutDismay extends Spell
{
    public ShoutDismay(int identifier) {
        super(identifier, "dismay");
    }

    @Override
    public String getName() {
        return "Dismay";
    }

    @Override
    public String getShoutName() {
        return "Faas Ru Maar";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("And the weak shall fear the");
        desc.add("Thu'um and flee in terror");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/dismay.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/fireball.png");
    }

    public SoundEvent getSound() {
        return SoundEvents.GHAST_DEATH;
    }

    @Override
    public float getCost() {
        return 6.0f;
    }

    @Override
    public float getCooldown() {
        return 30f;
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
    public int getBaseXp() {
        return 6;
    }

    @Override
    public void onCast() {
        getCaster().addEffect(new MobEffectInstance(ModEffects.DISMAY.asHolder(), 600, 0, false, true, true));
        super.onCast();
    }
}
