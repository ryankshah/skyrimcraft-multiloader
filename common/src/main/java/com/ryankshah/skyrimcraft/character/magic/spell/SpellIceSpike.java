package com.ryankshah.skyrimcraft.character.magic.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.entity.FireballEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.List;

public class SpellIceSpike extends Spell
{
    public SpellIceSpike(int identifier) {
        super(identifier, "ice_spike");
    }

    @Override
    public String getName() {
        return "Ice Spike";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Throw a spike of ice that");
        desc.add("does frost damage to health");
        desc.add("and stamina");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/ice_spike.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/ice_spell_icon.png");
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.GLASS_BREAK;
    }

    @Override
    public float getCost() {
        return 4.8f;
    }

    @Override
    public float getCooldown() {
        return 0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.DESTRUCTION;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.APPRENTICE;
    }

    @Override
    public int getBaseXp() {
        return 4;
    }

    @Override
    public void onCast() {
        //TODO: Make this an ice shard that shatters when it hits something
        FireballEntity fireball = new FireballEntity(getCaster().level(), getCaster(), getCaster().getLookAngle().x * 1, getCaster().getLookAngle().y * 1, getCaster().getLookAngle().z * 1);
        fireball.setPos(fireball.getX(), getCaster().getY() + getCaster().getEyeHeight(), getCaster().getZ());
        getCaster().getCommandSenderWorld().addFreshEntity(fireball);

        super.onCast();
    }
}