package com.ryankshah.skyrimcraft.character.magic.shout;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.util.RayTraceUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ShoutIceForm extends Spell //implements IForgeRegistryEntry<ISpell>
{
    public ShoutIceForm(int identifier) {
        super(identifier, "ice_form");
    }

    @Override
    public String getName() {
        return "Ice Form";
    }

    @Override
    public String getShoutName() {
        return "Iiz Slen Nus";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Your Thu'um freezes an");
        desc.add("opponent solid for 8 seconds");
        return desc;
    }

    @Override
    public SoundEvent getSound() {
        //return ModSounds.UNRELENTING_FORCE.get();
        return SoundEvents.GLASS_BREAK;
    }

    @Override
    public float getSoundLength() {
        return 0f;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/ice_form.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/ice_spell_icon.png");
    }

    @Override
    public float getCost() {
        return 0f;
    }

    @Override
    public float getCooldown() {
        return 120.0f;
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
        Level world = getCaster().level();
        Entity rayTracedEntity = RayTraceUtil.rayTrace(world, getCaster(), 20D);
        if(rayTracedEntity instanceof LivingEntity) {
            double x = rayTracedEntity.getX();
            double y = rayTracedEntity.getY();
            double z = rayTracedEntity.getZ();
            // freeze player for 8s (160 ticks = 8 * 20)
            ((LivingEntity) rayTracedEntity).addEffect(new MobEffectInstance(ModEffects.FROZEN.asHolder(), 160, 0, false, true, true));
            super.onCast();
        } else {
            getCaster().displayClientMessage(Component.translatable("skyrimcraft.shout.notarget"), false);
        }
    }
}