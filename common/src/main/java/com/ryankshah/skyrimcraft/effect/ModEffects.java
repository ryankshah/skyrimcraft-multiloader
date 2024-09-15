package com.ryankshah.skyrimcraft.effect;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.registry.AttributeRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModEffects
{
    public static void init() {}

    public static final RegistrationProvider<MobEffect> MOB_EFFECTS = RegistrationProvider.get(Registries.MOB_EFFECT, Constants.MODID);

    public static final RegistryObject<MobEffect, EffectEthereal> ETHEREAL = MOB_EFFECTS.register("ethereal", () -> new EffectEthereal(
            MobEffectCategory.BENEFICIAL,
            0x8A416C
    ));
    public static final RegistryObject<MobEffect, EffectFrozen> FROZEN = MOB_EFFECTS.register("frozen", () -> new EffectFrozen(
            MobEffectCategory.HARMFUL,
            0xA5F2F3
    ));
    public static final RegistryObject<MobEffect, EffectMagickaRegen> MAGICKA_REGEN = MOB_EFFECTS.register("magicka_regen", () -> new EffectMagickaRegen(
            MobEffectCategory.BENEFICIAL,
            0xAA3792CB
    ));
    public static final RegistryObject<MobEffect, EffectSpectral> SPECTRAL = MOB_EFFECTS.register("spectral", () -> new EffectSpectral(
            MobEffectCategory.BENEFICIAL,
            0xFDB515
    ));
    public static final RegistryObject<MobEffect, EffectUndeadFlee> UNDEAD_FLEE = MOB_EFFECTS.register("undead_flee", () -> new EffectUndeadFlee(
            MobEffectCategory.BENEFICIAL,
            0xAA222222
    ));
    public static final RegistryObject<MobEffect, EffectDismay> DISMAY = MOB_EFFECTS.register("dismay", () -> new EffectDismay(
            MobEffectCategory.BENEFICIAL,
            0xAA222222
    ));
    public static final RegistryObject<MobEffect, EffectWaterwalking> WATER_WALKING = MOB_EFFECTS.register("water_walking", () -> new EffectWaterwalking(
            MobEffectCategory.NEUTRAL,
            0x0F5E9C
    ));

    public static final RegistryObject<MobEffect, EffectParalysis> PARALYSIS = MOB_EFFECTS.register("paralysis", () -> new EffectParalysis(
            MobEffectCategory.HARMFUL,
            0xA5F2F3
    ));

    public static final RegistryObject<MobEffect, EffectCureDisease> CURE_DISEASE = MOB_EFFECTS.register("cure_disease", () -> new EffectCureDisease(
            MobEffectCategory.BENEFICIAL,
            0xA5F2F3
    ));

    public static final RegistryObject<MobEffect, EffectCurePoison> CURE_POISON = MOB_EFFECTS.register("cure_poison", () -> new EffectCurePoison(
            MobEffectCategory.BENEFICIAL,
            0xA5F2F3
    ));

    public static final RegistryObject<MobEffect, EffectFlameCloak> FLAME_CLOAK = MOB_EFFECTS.register("flame_cloak", () -> new EffectFlameCloak(
            MobEffectCategory.BENEFICIAL,
            0xAA222222
    ));

    public static final RegistryObject<MobEffect, EffectHist> HIST = MOB_EFFECTS.register("hist", () -> new EffectHist(
            MobEffectCategory.BENEFICIAL,
            0xAA222222
    ));

    public static final RegistryObject<MobEffect, EffectAdrenalineRush> ADRENALINE_RUSH = MOB_EFFECTS.register("adrenaline_rush", () -> new EffectAdrenalineRush(
            MobEffectCategory.BENEFICIAL,
            0xAA222222
    ));

    public static final RegistryObject<MobEffect, EffectBattlecry> BATTLE_CRY = MOB_EFFECTS.register("battle_cry", () -> new EffectBattlecry(
            MobEffectCategory.BENEFICIAL,
            0xAA222222
    ));

    public static final RegistryObject<MobEffect, EffectCalm> CALM = MOB_EFFECTS.register("calm", () -> new EffectCalm(
            MobEffectCategory.BENEFICIAL,
            0xAA222222
    ));

    public static final RegistryObject<MobEffect, EffectIncreasedArmorRating> ARMOR_RATING = MOB_EFFECTS.register("armor_rating", () -> (EffectIncreasedArmorRating) (new EffectIncreasedArmorRating(
            MobEffectCategory.BENEFICIAL,
            0xAA222222
    ).addAttributeModifier(Attributes.ARMOR, AttributeRegistry.MODIFIER_ID_ARMOR_RATING, 4.0D, AttributeModifier.Operation.ADD_VALUE)));
}