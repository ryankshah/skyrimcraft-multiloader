package com.ryankshah.skyrimcraft.data.loot_table;

import com.google.common.collect.Maps;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.skill.Skill;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.data.loot_table.condition.MatchSkillLevel;
import com.ryankshah.skyrimcraft.data.loot_table.predicate.SkillPredicate;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PickpocketLootTables implements LootTableSubProvider
{
    private final Map<ResourceLocation, LootTable.Builder> map = Maps.newHashMap();
    private HolderLookup.Provider provider;

    public PickpocketLootTables(HolderLookup.Provider provider) {
        this.provider = provider;
    }

    protected static LootItemCondition.Builder getSkillLevelCondition(Skill skill, int level) {
        return MatchSkillLevel.skillMatches(SkillPredicate.Builder.skill().of(skill, level, 1F));
    }

    protected static LootItemCondition.Builder getSkillLevelConditionWithChance(Skill skill, int level, float successChance) {
        return MatchSkillLevel.skillMatches(SkillPredicate.Builder.skill().of(skill, level, successChance));
    }

    protected static LootTable.Builder createSingleItemTable(ItemLike itemProvider) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(itemProvider)));
    }

    protected static LootTable.Builder createSingleItemTable(ItemLike itemProvider, LootItemCondition.Builder lootConditionBuilder, LootPoolEntryContainer.Builder<?> lootEntryBuilder) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(itemProvider).when(lootConditionBuilder).otherwise(lootEntryBuilder)));
    }

    protected static LootTable.Builder createSingleItemTableWithRange(ItemLike itemProvider, UniformGenerator rolls) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(rolls).add(LootItem.lootTableItem(itemProvider)));
    }

    protected static LootTable.Builder createSingleItemTableWithRange(ItemLike itemProvider, UniformGenerator rolls, LootItemCondition.Builder lootConditionBuilder) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(rolls).add(LootItem.lootTableItem(itemProvider).when(lootConditionBuilder)));//.otherwise(ItemLootEntry.lootTableItem(itemProvider))));
    }

    protected static LootTable.Builder multiplePools(LootItemCondition.Builder lootConditionBuilder, LootPool.Builder... lootPools) {
        LootTable.Builder lootTable = LootTable.lootTable();

        for(LootPool.Builder pool : lootPools) {
            lootTable.withPool(pool.when(lootConditionBuilder));
        }

        return lootTable;
    }

    public static LootTable.Builder noLoot() {
        return LootTable.lootTable();
    }

    protected void add(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pOutput, EntityType<?> entityType, LootTable.Builder builder) {
        pOutput.accept(
                ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "pickpocket")),
                builder);
    }
    protected void add(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pOutput, EntityType<?> entityType, Function<EntityType<?>, LootTable.Builder> builder) {
        this.add(pOutput, entityType, builder.apply(entityType));
    }

    public void addTables(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pOutput) {
        LootTable.Builder pickpocketGemPool = LootTable.lootTable().withPool(LootPool.lootPool()
                .name("pickpocketGemPool")
                .setRolls(UniformGenerator.between(1.0F, 3.0F))
                .add(LootItem.lootTableItem(ItemRegistry.FLAWED_RUBY.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.provider, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FLAWED_EMERALD.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.provider, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FLAWED_DIAMOND.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.provider, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FLAWLESS_RUBY.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.provider, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FLAWED_GARNET.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.provider, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FLAWLESS_GARNET.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.provider, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FLAWED_AMETHYST.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.provider, UniformGenerator.between(1.0F, 2.0F)))
                )
                .when(getSkillLevelConditionWithChance(SkillRegistry.PICKPOCKET.get(), 15, 0.4f))
        );

        add(pOutput, EntityType.VILLAGER, pickpocketGemPool); //createSingleItemTableWithRange(Items.EMERALD, UniformGenerator.between(1F, 3F), getSkillLevelConditionWithChance(SkillRegistry.PICKPOCKET.get(), 15, 0.4f)));
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> p_249643_) {
        addTables(p_249643_);
    }
}