package com.ryankshah.skyrimcraft.data.world;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.world.WorldGenConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.ryankshah.skyrimcraft.world.WorldGenConstants.*;

public class SkyrimcraftPlacedFeatures
{
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, CORUNDUM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(WorldGenConstants.CORUNDUM_ORE_KEY),
                OrePlacement.commonOrePlacement(14,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));
        register(context, EBONY_ORE_PLACED_KEY, configuredFeatures.getOrThrow(WorldGenConstants.EBONY_ORE_KEY),
                OrePlacement.rareOrePlacement(6,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(8))));
        register(context, MALACHITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(WorldGenConstants.MALACHITE_ORE_KEY),
                OrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-11), VerticalAnchor.absolute(46))));
        register(context, MOONSTONE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(WorldGenConstants.MOONSTONE_ORE_KEY),
                OrePlacement.commonOrePlacement(14,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(22))));
        register(context, ORICHALCUM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(WorldGenConstants.ORICHALCUM_ORE_KEY),
                OrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(8), VerticalAnchor.absolute(64))));
        register(context, QUICKSILVER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(WorldGenConstants.QUICKSILVER_ORE_KEY),
                OrePlacement.commonOrePlacement(16,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48))));
        register(context, SILVER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(WorldGenConstants.SILVER_ORE_KEY),
                OrePlacement.commonOrePlacement(24,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64))));

        register(context, LAVENDER_PLACED_KEY,
                configuredFeatures.getOrThrow(WorldGenConstants.LAVENDER_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(24),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        );

        register(context, MOUNTAIN_FLOWER_PLACED_KEY,
                configuredFeatures.getOrThrow(WorldGenConstants.MOUNTAIN_FLOWER_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(24),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        );

        register(context, BUSHES_PLACED_KEY,
                configuredFeatures.getOrThrow(WorldGenConstants.BUSHES_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(24),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        );

        register(context, MUSHROOMS_PLACED_KEY,
                configuredFeatures.getOrThrow(WorldGenConstants.MUSHROOMS_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(12),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        );
        register(context, CREEP_CLUSTER_PLACED_KEY,
                configuredFeatures.getOrThrow(WorldGenConstants.CREEP_CLUSTER_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(12),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        );

        register(context, OYSTERS_PLACED_KEY,
                configuredFeatures.getOrThrow(WorldGenConstants.OYSTERS_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(12),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );

        register(context, DESERT_PLANTS_PLACED_KEY,
                configuredFeatures.getOrThrow(WorldGenConstants.DESERT_PLANTS_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(12),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );

        register(context, BIRDS_NEST_PLACED_KEY,
                configuredFeatures.getOrThrow(WorldGenConstants.BIRDS_NEST_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        PlacedFeature feature = new PlacedFeature(configuration, List.copyOf(modifiers));
        context.register(key, feature);
    }
}