package com.ryankshah.skyrimcraft.character.magic.shout;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ShoutDragonAspect extends Spell //implements IForgeRegistryEntry<ISpell>
{
    public ShoutDragonAspect(int identifier) {
        super(identifier, "dragon_aspect");
    }

    @Override
    public String getName() {
        return "Dragon Aspect";
    }

    @Override
    public String getShoutName() {
        return "Mul Qah Diiv";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Take on the mighty aspect of a");
        desc.add("dragon, delivering colossal");
        desc.add("blows, with an armored hide");
        desc.add("and more powerful shouts");

        return desc;
    }

    @Override
    public SoundEvent getSound() {
        //return ModSounds.UNRELENTING_FORCE.get();
        return SoundEvents.ENDER_DRAGON_GROWL;
    }

    @Override
    public float getSoundLength() {
        return 0f;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/dragon_aspect.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/dragon_aspect.png");
    }

    @Override
    public float getCost() {
        return 0f;
    }

    @Override
    public float getCooldown() {
        return 60.0f;
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
        Level level = getCaster().level();

        int dur = 300;
        int power = 1;

        getCaster().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, dur * power, 3, false, true, true));
        getCaster().addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, dur * power, 2, false, true, true));
        getCaster().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, dur * power, 1, false, true, true));

        if(level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.LAVA, getCaster().position().x, getCaster().position().y, getCaster().position().z, 100, 0 ,0, 0, 1.0f);
        }
    }
}