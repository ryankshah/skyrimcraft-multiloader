package com.ryankshah.skyrimcraft.data.loot_table.condition.type;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.loot_table.condition.MatchSkillLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class SkyrimcraftLootConditionTypes
{
    public static void init() {}

    public static final RegistrationProvider<LootItemConditionType> LOOT_CONDITION_TYPE = RegistrationProvider.get(BuiltInRegistries.LOOT_CONDITION_TYPE, Constants.MODID);

    public static final RegistryObject<LootItemConditionType, LootItemConditionType> MATCH_SKILL = LOOT_CONDITION_TYPE.register("match_skill", () -> new LootItemConditionType(MatchSkillLevel.CODEC));
}