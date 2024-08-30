package com.ryankshah.skyrimcraft.character.magic.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.entity.LightBallEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.List;

public class SpellCandlelight extends Spell
{
    public SpellCandlelight(int identifier) {
        super(identifier, "candlelight");
    }

    @Override
    public String getName() {
        return "Candlelight";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Creates a hovering light");
        desc.add("that lasts for 60 seconds");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/candlelight.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/candlelight.png");
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.SHROOMLIGHT_BREAK;
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
        LightBallEntity lightball = new LightBallEntity(getCaster().level(), getCaster());
//        lightball.setPos(lightball.getX() + 1, getCaster().getY() + getCaster().getEyeHeight(), getCaster().getZ() + 1);
        getCaster().getCommandSenderWorld().addFreshEntity(lightball);

        super.onCast();
    }
}