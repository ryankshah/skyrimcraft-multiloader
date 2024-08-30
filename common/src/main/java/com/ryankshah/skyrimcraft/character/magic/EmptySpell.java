package com.ryankshah.skyrimcraft.character.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class EmptySpell extends Spell
{
    public EmptySpell() {
        super(-1, "empty_spell");
    }

    @Override
    public String getName() {
        return "Empty Spell";
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return null;
    }

    @Override
    public ResourceLocation getIcon() {
        return null;
    }

    @Override
    public SoundEvent getSound() {
        return null;
    }

    @Override
    public float getCost() {
        return 0f;
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
        return SpellDifficulty.NA;
    }

    @Override
    public int getBaseXp() {
        return 0;
    }

    @Override
    public void onCast() {
        super.onCast();
    }
}
