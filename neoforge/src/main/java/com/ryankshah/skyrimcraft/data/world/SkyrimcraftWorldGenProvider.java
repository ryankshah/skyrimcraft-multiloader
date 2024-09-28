package com.ryankshah.skyrimcraft.data.world;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.world.biome.SkyrimcraftBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SkyrimcraftWorldGenProvider extends DatapackBuiltinEntriesProvider
{
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, SkyrimcraftConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, SkyrimcraftPlacedFeatures::bootstrap)
            .add(Registries.BIOME, SkyrimcraftBiomes::biomes)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, SkyrimcraftBiomeModifiers::bootstrap);

    public SkyrimcraftWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Constants.MODID));
    }
}