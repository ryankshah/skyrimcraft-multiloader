package com.ryankshah.skyrimcraft.character.magic.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class SpellFlameCloak extends Spell
{
    public SpellFlameCloak(int identifier) {
        super(identifier, "flame_cloak");
    }

    @Override
    public String getName() {
        return "Flame Cloak";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Opponents in melee range");
        desc.add("take 1 heart of fire damage");
        desc.add("per second");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/flame_cloak.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/flame_cloak.png");
    }

    public SoundEvent getSound() {
        return SoundEvents.FIRE_AMBIENT;
    }

    @Override
    public float getCost() {
        return 18.9f;
    } //TODO: 289 cost..???

    @Override
    public float getCooldown() {
        return 60.0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.DESTRUCTION;
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
    public CastReference getCastReference() {
        return CastReference.INSTANT;
    }

    @Override
    public void onCast() {
        getCaster().addEffect(new MobEffectInstance(ModEffects.FLAME_CLOAK.asHolder(), 600, 0, false, true, true));
        super.onCast();
    }
}