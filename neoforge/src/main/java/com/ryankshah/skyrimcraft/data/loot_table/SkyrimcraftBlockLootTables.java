package com.ryankshah.skyrimcraft.data.loot_table;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.PearlOysterBlock;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SkyrimcraftBlockLootTables extends BlockLootSubProvider
{
    protected SkyrimcraftBlockLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {

        // normal ores
//        add(BlockRegistry.EXAMPLE_ORE.get(), createOreDrop(BlockRegistry.EXAMPLE_ORE.get(), ItemRegistry.RAW_EXAMPLE.get()));
//
//        // deepslate ores
//        add(BlockRegistry.DEEPSLATE_EXAMPLE_ORE.get(), createOreDrop(BlockRegistry.DEEPSLATE_EXAMPLE_ORE.get(), ItemRegistry.RAW_EXAMPLE.get()));

        dropSelf(BlockRegistry.CORUNDUM_ORE.get());
        dropSelf(BlockRegistry.EBONY_ORE.get());
        dropSelf(BlockRegistry.MALACHITE_ORE.get());
        dropSelf(BlockRegistry.MOONSTONE_ORE.get());
        dropSelf(BlockRegistry.ORICHALCUM_ORE.get());
        dropSelf(BlockRegistry.QUICKSILVER_ORE.get());
        dropSelf(BlockRegistry.SILVER_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_CORUNDUM_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_EBONY_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_MALACHITE_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_MOONSTONE_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_ORICHALCUM_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_QUICKSILVER_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_SILVER_ORE.get());
        dropSelf(BlockRegistry.SHOUT_BLOCK.get());
        dropSelf(BlockRegistry.ALCHEMY_TABLE.get());
        dropSelf(BlockRegistry.OVEN.get());
        dropSelf(BlockRegistry.BLACKSMITH_FORGE.get());
        dropSelf(BlockRegistry.WEAPON_RACK.get());
        dropSelf(BlockRegistry.ARCANE_ENCHANTER.get());

        dropSelf(BlockRegistry.STONE_BRICK_1.get());
        dropSelf(BlockRegistry.STONE_BRICK_2.get());
        dropSelf(BlockRegistry.STONE_BRICK_3.get());
        dropSelf(BlockRegistry.STONE_BRICK_4.get());
        dropSelf(BlockRegistry.STONE_BRICK_5.get());
        dropSelf(BlockRegistry.COBBLESTONE_1.get());
        dropSelf(BlockRegistry.COBBLESTONE_2.get());
        dropSelf(BlockRegistry.SNOWY_PLANKS_1.get());
        dropSelf(BlockRegistry.PLANKS_1.get());
        dropSelf(BlockRegistry.PLANKS_2.get());
        dropSelf(BlockRegistry.PLANKS_3.get());
        dropSelf(BlockRegistry.PLANKS_4.get());

        dropSelf(BlockRegistry.RED_MOUNTAIN_FLOWER.get());
        dropSelf(BlockRegistry.BLUE_MOUNTAIN_FLOWER.get());
        dropSelf(BlockRegistry.YELLOW_MOUNTAIN_FLOWER.get());
        dropSelf(BlockRegistry.PURPLE_MOUNTAIN_FLOWER.get());
        dropOther(BlockRegistry.LAVENDER.get(), ItemRegistry.LAVENDER.get());

        this.add(
                BlockRegistry.JAZBAY_GRAPE_BUSH.get(),
                p_249159_ -> this.applyExplosionDecay(
                        p_249159_,
                        LootTable.lootTable()
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.JAZBAY_GRAPE_BUSH.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3))
                                                )
                                                .add(LootItem.lootTableItem(ItemRegistry.JAZBAY_GRAPES.get()))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                )
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.JAZBAY_GRAPE_BUSH.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2))
                                                )
                                                .add(LootItem.lootTableItem(ItemRegistry.JAZBAY_GRAPES.get()))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                )
                )
        );
        this.add(
                BlockRegistry.JUNIPER_BERRY_BUSH.get(),
                p_249159_ -> this.applyExplosionDecay(
                        p_249159_,
                        LootTable.lootTable()
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.JUNIPER_BERRY_BUSH.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3))
                                                )
                                                .add(LootItem.lootTableItem(ItemRegistry.JUNIPER_BERRIES.get()))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                )
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.JUNIPER_BERRY_BUSH.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2))
                                                )
                                                .add(LootItem.lootTableItem(ItemRegistry.JUNIPER_BERRIES.get()))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                )
                )
        );
        this.add(
                BlockRegistry.SNOWBERRY_BUSH.get(),
                p_249159_ -> this.applyExplosionDecay(
                        p_249159_,
                        LootTable.lootTable()
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SNOWBERRY_BUSH.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3))
                                                )
                                                .add(LootItem.lootTableItem(ItemRegistry.SNOWBERRIES.get()))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                )
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SNOWBERRY_BUSH.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2))
                                                )
                                                .add(LootItem.lootTableItem(ItemRegistry.SNOWBERRIES.get()))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                )
                )
        );

        dropSelf(BlockRegistry.CANIS_ROOT_BLOCK.get());
        dropSelf(BlockRegistry.BLEEDING_CROWN_BLOCK.get());
        dropSelf(BlockRegistry.WHITE_CAP_BLOCK.get());
        dropSelf(BlockRegistry.BLISTERWORT_BLOCK.get());
        dropSelf(BlockRegistry.FLY_AMANITA_BLOCK.get());
        dropSelf(BlockRegistry.CREEP_CLUSTER_BLOCK.get());

        LootItemCondition.Builder lootitemcondition$pearloyster = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.PEARL_OYSTER_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PearlOysterBlock.IS_OPEN, true))
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PearlOysterBlock.IS_EMPTY, false));
        add(BlockRegistry.PEARL_OYSTER_BLOCK.get(), createCropDrops(BlockRegistry.PEARL_OYSTER_BLOCK.get(), ItemRegistry.PEARL.get(), BlockRegistry.PEARL_OYSTER_BLOCK.get().asItem(), lootitemcondition$pearloyster));

        LootItemCondition.Builder lootitemcondition$builder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.TOMATO_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
        add(BlockRegistry.TOMATO_CROP.get(), createCropDrops(BlockRegistry.TOMATO_CROP.get(), ItemRegistry.TOMATO.get(), ItemRegistry.TOMATO_SEEDS.get(), lootitemcondition$builder1));

        LootItemCondition.Builder ilootcondition$garliccrop = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.GARLIC_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
        add(BlockRegistry.GARLIC_CROP.get(), createCropDrops(BlockRegistry.GARLIC_CROP.get(), ItemRegistry.GARLIC.get(), ItemRegistry.GARLIC.get(), ilootcondition$garliccrop));

        LootItemCondition.Builder ilootcondition$cabbagecrop = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.CABBAGE_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
        add(BlockRegistry.CABBAGE_CROP.get(), createCropDrops(BlockRegistry.CABBAGE_CROP.get(), ItemRegistry.CABBAGE.get(), ItemRegistry.CABBAGE_SEEDS.get(), ilootcondition$cabbagecrop));

        LootItemCondition.Builder ilootcondition$ashyamcrop = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.ASH_YAM_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
        add(BlockRegistry.ASH_YAM_CROP.get(), createCropDrops(BlockRegistry.ASH_YAM_CROP.get(), ItemRegistry.ASH_YAM.get(), ItemRegistry.ASH_YAM_SLIPS.get(), ilootcondition$ashyamcrop));


        LootTable.Builder birdsNestDrops = LootTable.lootTable().withPool(LootPool.lootPool()
                        .name("birdsNestPool")
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ItemRegistry.HAWK_EGG.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                        )
                        .add(LootItem.lootTableItem(ItemRegistry.PINE_THRUSH_EGG.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                        )
                        .add(LootItem.lootTableItem(ItemRegistry.ROCK_WARBLER_EGG.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                        )
                        .add(LootItem.lootTableItem(Items.EGG))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                        );
        add(BlockRegistry.BIRDS_NEST.get(), birdsNestDrops);
    }

    @Override
    public @NotNull Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream()
                .filter(block -> Optional.of(BuiltInRegistries.BLOCK.getKey(block))
                        .filter(key -> key.getNamespace().equals(Constants.MODID))
                        .isPresent())
                .collect(Collectors.toSet());
    }
}