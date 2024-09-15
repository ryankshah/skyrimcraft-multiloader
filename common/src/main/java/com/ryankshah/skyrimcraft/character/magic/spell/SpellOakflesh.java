package com.ryankshah.skyrimcraft.character.magic.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.entity.LightBallEntity;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class SpellOakflesh extends Spell
{
    public SpellOakflesh(int identifier) {
        super(identifier, "oakflesh");
    }

    @Override
    public String getName() {
        return "Oakflesh";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Improves the caster's armor");
        desc.add("rating by 40 points for 60");
        desc.add("seconds");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/oakflesh.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/oakflesh.png");
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.ARMOR_EQUIP_NETHERITE.value();
    }

    @Override
    public float getCost() {
        return 11f;
    }

    @Override
    public float getCooldown() {
        return 0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.ALTERATION;
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
        getCaster().addEffect(new MobEffectInstance(ModEffects.ARMOR_RATING.asHolder(), 1200, 0, false, true, true));
        super.onCast();
    }
}