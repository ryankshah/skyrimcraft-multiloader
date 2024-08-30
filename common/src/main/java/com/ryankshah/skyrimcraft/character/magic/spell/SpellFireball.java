package com.ryankshah.skyrimcraft.character.magic.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.entity.FireballEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.List;

public class SpellFireball extends Spell
{
    public SpellFireball(int identifier) {
        super(identifier, "fireball");
    }

    @Override
    public String getName() {
        return "Fireball";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("A fiery explosion for 4");
        desc.add("points of damage in a 15");
        desc.add("block radius");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/fireball.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/fireball_icon.png");
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.ENDER_DRAGON_SHOOT;
    }

    @Override
    public float getCost() {
        return 6.0f;
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
        return SpellDifficulty.NA;
    }

    @Override
    public int getBaseXp() {
        return 4;
    }

    @Override
    public void onCast() {
        FireballEntity fireball = new FireballEntity(getCaster().level(), getCaster(), getCaster().getLookAngle().x * 1, getCaster().getLookAngle().y * 1, getCaster().getLookAngle().z * 1);
        fireball.setPos(fireball.getX(), getCaster().getY() + getCaster().getEyeHeight(), getCaster().getZ());
        getCaster().getCommandSenderWorld().addFreshEntity(fireball);

        super.onCast();
    }
}