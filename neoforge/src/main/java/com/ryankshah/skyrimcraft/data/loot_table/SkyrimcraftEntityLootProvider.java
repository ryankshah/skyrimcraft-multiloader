package com.ryankshah.skyrimcraft.data.loot_table;

import com.example.examplemod.registration.RegistryObject;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.stream.Stream;

public class SkyrimcraftEntityLootProvider extends EntityLootSubProvider
{
    public SkyrimcraftEntityLootProvider(HolderLookup.Provider provider) {
        super(FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    public void generate() {
        this.add(EntityRegistry.BLUE_DARTWING.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ItemRegistry.BLUE_DARTWING.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F))))));
        this.add(EntityRegistry.BLUE_BUTTERFLY.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ItemRegistry.BLUE_BUTTERFLY_WING.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F))))));
        this.add(EntityRegistry.ORANGE_DARTWING.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ItemRegistry.ORANGE_DARTWING.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F))))));
        this.add(EntityRegistry.LUNAR_MOTH.get(), LootTable.lootTable());
        this.add(EntityRegistry.MONARCH_BUTTERFLY.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ItemRegistry.BUTTERFLY_WING.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F))))));
        this.add(EntityRegistry.TORCHBUG.get(), LootTable.lootTable());//.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ModItems.TORCHBUG_THORAX.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F))))));
        this.add(EntityRegistry.SABRE_CAT.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ItemRegistry.SABRE_CAT_TOOTH.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F))))).withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F)).add(LootItem.lootTableItem(Items.BONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ItemRegistry.EYE_OF_SABRE_CAT.get())).when(LootItemKilledByPlayerCondition.killedByPlayer())));
        this.add(EntityRegistry.GIANT.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ItemRegistry.GIANTS_TOE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F))))));
        this.add(EntityRegistry.MAMMOTH.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ItemRegistry.MAMMOTH_SNOUT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F))))));
        this.add(EntityRegistry.DRAGON.get(), LootTable.lootTable());
        this.add(EntityRegistry.DWARVEN_SPIDER.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ItemRegistry.DWARVEN_METAL_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F))))));

        this.add(EntityRegistry.SKEEVER.get(), LootTable.lootTable()); //LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ItemRegistry.CHARRED_SKEEVER_HIDE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F))))));

        this.add(EntityRegistry.KHAJIIT.get(), LootTable.lootTable());

        LootPool.Builder falmerPool = LootPool.lootPool()
                .name("falmerPool")
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(ItemRegistry.FALMER_EAR.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FALMER_BOOTS.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FALMER_BOW.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FALMER_HELMET.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FALMER_CHESTPLATE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FALMER_LEGGINGS.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FALMER_SWORD.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FALMER_WAR_AXE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.VAMPIRE_DUST.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.MORA_TAPINELLA.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.BRIAR_HEART.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FIRE_SALTS.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.TROLL_FAT.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.CHAURUS_EGGS.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.ELVES_EAR.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.TAPROOT.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.BEAR_CLAWS.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.BERITS_ASHES.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.CRIMSON_NIRNROOT.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.DEATHBELL.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.DRAGONS_TONGUE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.ECTOPLASM.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                );
        this.add(EntityRegistry.FALMER.get(), LootTable.lootTable().withPool(falmerPool));

        this.add(EntityRegistry.ABECEAN_LONGFIN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ItemRegistry.ABECEAN_LONGFIN.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F))))));
        this.add(EntityRegistry.CYRODILIC_SPADETAIL.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ItemRegistry.CYRODILIC_SPADETAIL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F))))));

        LootPool.Builder draugrPool = LootPool.lootPool()
                .name("draugrPool")
                .setRolls(UniformGenerator.between(1.0F, 3.0F))
                .add(LootItem.lootTableItem(ItemRegistry.FLAWED_RUBY.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FLAWED_EMERALD.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FLAWED_DIAMOND.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FLAWLESS_RUBY.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FLAWED_GARNET.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FLAWLESS_GARNET.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                )
                .add(LootItem.lootTableItem(ItemRegistry.FLAWED_AMETHYST.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(1.0F, 2.0F)))
                );
        this.add(EntityRegistry.DRAUGR.get(), LootTable.lootTable().withPool(draugrPool));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return EntityRegistry.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
    }
}
