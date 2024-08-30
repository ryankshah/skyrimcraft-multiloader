package com.ryankshah.skyrimcraft.character.skill;

import com.example.examplemod.registration.RegistrationProvider;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.skill.type.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class SkillRegistry
{
    public static final ResourceKey<Registry<Skill>> SKILLS_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "skills_key"));
    public static final RegistrationProvider<Skill> SKILLS = RegistrationProvider.get(SKILLS_KEY, Constants.MODID);
    public static final Registry<Skill> SKILLS_REGISTRY = SKILLS.registryBuilder().build(); //(builder -> builder.sync(true).defaultKey(ResourceLocation.fromNamespaceAndPath(Skyrimcraft.MODID, "empty")));

    public static final int BASE_ARCHERY_XP = 4;
    public static final int BASE_BLOCK_XP = 4;
    public static final int BASE_PICKPOCKET_XP = 4;
    public static final int BASE_ALCHEMY_XP = 4;
    public static final int BASE_SMITHING_XP = 6;
    public static final int BASE_SPEECH_XP = 4;
    public static final int BASE_ENCHANT_XP = 4;

    public static Supplier<Skill> ALTERATION = SKILLS.register("alteration", Alteration::new);
    public static Supplier<Skill> CONJURATION = SKILLS.register("conjuration", Conjuration::new);
    public static Supplier<Skill> DESTRUCTION = SKILLS.register("destruction", Destruction::new);
    public static Supplier<Skill> ILLUSION = SKILLS.register("illusion", Illusion::new);
    public static Supplier<Skill> RESTORATION = SKILLS.register("restoration", Restoration::new);
    public static Supplier<Skill> ENCHANTING = SKILLS.register("enchanting", Enchanting::new);
    public static Supplier<Skill> ONE_HANDED = SKILLS.register("one_handed", OneHanded::new);
    public static Supplier<Skill> TWO_HANDED = SKILLS.register("two_handed", TwoHanded::new);
    public static Supplier<Skill> ARCHERY = SKILLS.register("archery", Archery::new);
    public static Supplier<Skill> BLOCK = SKILLS.register("block", Block::new);
    public static Supplier<Skill> SMITHING = SKILLS.register("smithing", Smithing::new);
    public static Supplier<Skill> HEAVY_ARMOR = SKILLS.register("heavy_armor", HeavyArmor::new);
    public static Supplier<Skill> LIGHT_ARMOR = SKILLS.register("light_armor", LightArmor::new);
    public static Supplier<Skill> PICKPOCKET = SKILLS.register("pickpocket", Pickpocket::new);
    public static Supplier<Skill> LOCKPICKING = SKILLS.register("lockpicking", Lockpicking::new);
    public static Supplier<Skill> SNEAK = SKILLS.register("sneak", Sneak::new);
    public static Supplier<Skill> ALCHEMY = SKILLS.register("alchemy", Alchemy::new);
    public static Supplier<Skill> SPEECH = SKILLS.register("speech", Speech::new);
}