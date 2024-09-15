package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.platform.Services;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;

public class AttributeRegistry
{
    public static void init() {}

    public static final RegistrationProvider<Attribute> ATTRIBUTES = RegistrationProvider.get(Registries.ATTRIBUTE, Constants.MODID);

    public static final RegistryObject<Attribute, Attribute> MAX_MAGICKA = ATTRIBUTES.register("max_magicka_attribute", () -> new RangedAttribute("skyrimcraft.character.attribute.max_magicka", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> MAX_STAMINA = ATTRIBUTES.register("max_stamina_attribute", () -> new RangedAttribute("skyrimcraft.character.attribute.max_stamina", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> MAGICKA_REGEN = ATTRIBUTES.register("magicka_regen_attribute", () -> new RangedAttribute("skyrimcraft.character.attribute.magicka_regen", 1F, 0F, 20F).setSyncable(true));

    public static final RegistryObject<Attribute, Attribute> POISON_RESIST = ATTRIBUTES.register("poison_resist", () -> new RangedAttribute("skyrimcraft.character.attribute.poison_resistance", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> SHOCK_RESIST = ATTRIBUTES.register("shock_resist", () -> new RangedAttribute("skyrimcraft.character.attribute.shock_resistance", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> FIRE_RESIST = ATTRIBUTES.register("fire_resist", () -> new RangedAttribute("skyrimcraft.character.attribute.fire_resistance", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> FROST_RESIST = ATTRIBUTES.register("frost_resist", () -> new RangedAttribute("skyrimcraft.character.attribute.frost_resistance", 1F, 0F, 100F).setSyncable(true));

    public static final RegistryObject<Attribute, Attribute> POISON_POWER = ATTRIBUTES.register("poison_power", () -> new RangedAttribute("skyrimcraft.character.attribute.poison_power", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> SHOCK_POWER = ATTRIBUTES.register("shock_power", () -> new RangedAttribute("skyrimcraft.character.attribute.shock_power", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> FIRE_POWER = ATTRIBUTES.register("fire_power", () -> new RangedAttribute("skyrimcraft.character.attribute.fire_power", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> FROST_POWER = ATTRIBUTES.register("frost_power", () -> new RangedAttribute("skyrimcraft.character.attribute.frost_power", 1F, 0F, 100F).setSyncable(true));

    public static final RegistryObject<Attribute, Attribute> ALTERATION_POWER = ATTRIBUTES.register("alteration_power", () -> new RangedAttribute("skyrimcraft.character.attribute.alteration_power", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> DESTRUCTION_POWER = ATTRIBUTES.register("destruction_power", () -> new RangedAttribute("skyrimcraft.character.attribute.destruction_power", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> RESTORATION_POWER = ATTRIBUTES.register("restoration_power", () -> new RangedAttribute("skyrimcraft.character.attribute.restoration_power", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> SHOUT_POWER = ATTRIBUTES.register("shout_power", () -> new RangedAttribute("skyrimcraft.character.attribute.shout_power", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> POWERS_POWER = ATTRIBUTES.register("powers_power", () -> new RangedAttribute("skyrimcraft.character.attribute.powers_power", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> ILLUSION_POWER = ATTRIBUTES.register("illusion_power", () -> new RangedAttribute("skyrimcraft.character.attribute.illusion_power", 1F, 0F, 100F).setSyncable(true));
    public static final RegistryObject<Attribute, Attribute> CONJURATION_POWER = ATTRIBUTES.register("conjuration_power", () -> new RangedAttribute("skyrimcraft.character.attribute.conjuration_power", 1F, 0F, 100F).setSyncable(true));

    public static final ResourceLocation MODIFIER_ID_MAGICKA_REGEN = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "26fcb349-bc96-4593-9b29-5ace7bdee19f");
    public static final ResourceLocation MODIFIER_ID_PLAYER_HEALTH = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "671fbcca-aac7-4de7-9399-d951d58adc12");
    public static final ResourceLocation MODIFIER_ID_PLAYER_MAGICKA = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "496fe98b-9c36-41b9-a04b-5e06e6a13bb5");
    public static final ResourceLocation MODIFIER_ID_PLAYER_STAMINA = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "8745453b-a3d7-47c6-a986-a01f043da892");
    public static final ResourceLocation MODIFIER_ID_ARMOR_RATING = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "65df5e1c-fd02-45b7-80ae-40de04d9315d");

    private static final String MODIFIER_NAME_HEALTH = Constants.MODID + ".healthModifier";
    private static final String MODIFIER_NAME_MAGICKA = Constants.MODID + ".magickaModifier";
    private static final String MODIFIER_NAME_STAMINA = Constants.MODID + ".staminaModifier";

    public static final ResourceLocation ELEMENTAL_FURY_SHOUT_ID = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "92837cbf-7285-41f5-9b12-811421af56fb");

    public static void setModifier(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id, double amount, AttributeModifier.Operation op) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance == null) return;
        AttributeModifier mod = instance.getModifier(id);
        if (mod != null) instance.removeModifier(mod.id());
        instance.addPermanentModifier(new AttributeModifier(id, amount, op));
    }

    public static void setMaxHealth(LivingEntity entity, double amount, AttributeModifier.Operation op) {
        double oldMax = entity.getMaxHealth();
        setModifier(entity, Attributes.MAX_HEALTH, MODIFIER_ID_PLAYER_HEALTH, amount, op);
        double newMax = entity.getMaxHealth();

        // Heal entity when increasing max health
        if (newMax > oldMax) {
            float healAmount = (float) (newMax - oldMax);
            entity.heal(healAmount);
        } else if (entity.getHealth() > newMax) {
            entity.setHealth((float) newMax);
        }
    }

    public static void setMaxMagicka(LivingEntity entity, double amount, AttributeModifier.Operation op) {
        if(entity instanceof Player player) {
            Character character = Services.PLATFORM.getCharacter(player); // entity.getData(PlayerAttachments.CHARACTER);
            double oldMax = character.getMaxMagicka();
            setModifier(entity, AttributeRegistry.MAX_MAGICKA.asHolder(), MODIFIER_ID_PLAYER_MAGICKA, amount, op);
            double newMax = character.getMaxMagicka();
        }

//        // Heal entity when increasing max health
//        if (newMax > oldMax) {
//            float healAmount = (float) (newMax - oldMax);
//            entity.heal(healAmount);
//        } else if (entity.getHealth() > newMax) {
//            entity.setHealth((float) newMax);
//        }
    }
}