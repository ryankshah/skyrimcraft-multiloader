package com.ryankshah.skyrimcraft.data.loot_table;

import com.google.common.collect.Sets;
import com.ryankshah.skyrimcraft.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SkyrimcraftLootTables extends LootTableProvider
{
    public SkyrimcraftLootTables(PackOutput p_254123_, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(p_254123_, Set.of(), List.of(
                new SubProviderEntry(SkyrimcraftBlockLootTables::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(SkyrimcraftEntityLootProvider::new, LootContextParamSets.ENTITY),
                new SubProviderEntry(SkyrimcraftLootTableProvider::new, LootContextParamSets.CHEST),
                new SubProviderEntry(PickpocketLootTables::new, LootContextParamSets.SELECTOR)
        ), pRegistries);
    }

    @Override
    public void validate(WritableRegistry<LootTable> map, ValidationContext validationContext, ProblemReporter.Collector problemreporter$collector) {
        var skyrimcraftLootTablesId = BuiltInLootTables.all()
                .stream()
                .filter(id -> id.location().getNamespace().equals(Constants.MODID))
                .collect(Collectors.toSet());

        for (var id : Sets.difference(skyrimcraftLootTablesId, map.keySet())) {
            validationContext.reportProblem("Missing mod loot table: " + id);
        }

        map.forEach(id -> id.validate(validationContext));
    }
}