package com.ryankshah.skyrimcraft.character.magic.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.ArrayList;
import java.util.List;

public class SpellHealing extends Spell
{
    public SpellHealing(int identifier) {
        super(identifier, "healing");
    }


    @Override
    public String getName() {
        return "Healing";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Heals the caster half a");
        desc.add("heart point per second");
        return desc;
    }

    @Override
    public CastReference getCastReference() {
        return CastReference.HOLD;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/healing.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/healing.png");
    }

    @Override
    public SoundEvent getSound() {
        return null;
    }

    @Override
    public float getCost() {
        return 1.0f;
    }

    @Override
    public float getCooldown() {
        return 0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.RESTORATION;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NOVICE;
    }

    @Override
    public boolean isContinuous() { return true; }

    @Override
    public int getBaseXp() {
        return 4;
    }

    @Override
    public void onCast() {
        getCaster().heal(0.5f);

        super.onCast();
    }
}