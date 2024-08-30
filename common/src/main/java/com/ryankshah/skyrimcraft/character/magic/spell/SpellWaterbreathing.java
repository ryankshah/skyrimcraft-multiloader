package com.ryankshah.skyrimcraft.character.magic.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.List;

public class SpellWaterbreathing extends Spell
{
    public SpellWaterbreathing(int identifier) {
        super(identifier, "waterbreathing");
    }

    @Override
    public String getName() {
        return "Waterbreathing";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Can breathe under water for");
        desc.add("60 seconds");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/waterbreathing.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/waterbreathing.png");
    }

    public SoundEvent getSound() {
        return SoundEvents.AMBIENT_UNDERWATER_EXIT;
    }

    @Override
    public float getCost() {
        return 18.2f;
    } //TODO: 222 cost..???

    @Override
    public float getCooldown() {
        return 0.0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.ALTERATION;
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
        getCaster().addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 1200, 0, false, true, true));
        super.onCast();
    }
}