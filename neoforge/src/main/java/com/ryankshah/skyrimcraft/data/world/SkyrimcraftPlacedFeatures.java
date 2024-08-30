package com.ryankshah.skyrimcraft.data.world;

import com.ryankshah.skyrimcraft.Constants;
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

public class SkyrimcraftPlacedFeatures
{
    public static final ResourceKey<PlacedFeature> CORUNDUM_ORE_PLACED_KEY = registerKey("corundum_ore_placed");
    public static final ResourceKey<PlacedFeature> EBONY_ORE_PLACED_KEY = registerKey("ebony_ore_placed");
    public static final ResourceKey<PlacedFeature> MALACHITE_ORE_PLACED_KEY = registerKey("malachite_ore_placed");
    public static final ResourceKey<PlacedFeature> MOONSTONE_ORE_PLACED_KEY = registerKey("moonstone_ore_placed");
    public static final ResourceKey<PlacedFeature> ORICHALCUM_ORE_PLACED_KEY = registerKey("orichalcum_ore_placed");
    public static final ResourceKey<PlacedFeature> QUICKSILVER_ORE_PLACED_KEY = registerKey("quicksilver_ore_placed");
    public static final ResourceKey<PlacedFeature> SILVER_ORE_PLACED_KEY = registerKey("silver_ore_placed");

    public static final ResourceKey<PlacedFeature> MOUNTAIN_FLOWER_PLACED_KEY = registerKey("mountain_flower_placed");
    public static final ResourceKey<PlacedFeature> MUSHROOMS_PLACED_KEY = registerKey("mushrooms_placed");
    public static final ResourceKey<PlacedFeature> CREEP_CLUSTER_PLACED_KEY = registerKey("creep_cluster_placed");
    public static final ResourceKey<PlacedFeature> DESERT_PLANTS_PLACED_KEY = registerKey("desert_plants_placed");
    public static final ResourceKey<PlacedFeature> OYSTERS_PLACED_KEY = registerKey("oysters_placed");
    public static final ResourceKey<PlacedFeature> BIRDS_NEST_PLACED_KEY = registerKey("birds_nest_placed");


    public static final ResourceKey<PlacedFeature> TREES_PLACED_KEY = registerKey("trees_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, CORUNDUM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.CORUNDUM_ORE_KEY),
                OrePlacement.commonOrePlacement(14,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));
        register(context, EBONY_ORE_PLACED_KEY, configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.EBONY_ORE_KEY),
                OrePlacement.rareOrePlacement(6,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(8))));
        register(context, MALACHITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.MALACHITE_ORE_KEY),
                OrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-11), VerticalAnchor.absolute(46))));
        register(context, MOONSTONE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.MOONSTONE_ORE_KEY),
                OrePlacement.commonOrePlacement(14,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(22))));
        register(context, ORICHALCUM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.ORICHALCUM_ORE_KEY),
                OrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(8), VerticalAnchor.absolute(64))));
        register(context, QUICKSILVER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.QUICKSILVER_ORE_KEY),
                OrePlacement.commonOrePlacement(16,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48))));
        register(context, SILVER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.SILVER_ORE_KEY),
                OrePlacement.commonOrePlacement(24,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64))));

        register(context, MOUNTAIN_FLOWER_PLACED_KEY,
                configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.MOUNTAIN_FLOWER_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(24),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        );

        register(context, MUSHROOMS_PLACED_KEY,
                configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.MUSHROOMS_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(12),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        );
        register(context, CREEP_CLUSTER_PLACED_KEY,
                configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.CREEP_CLUSTER_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(12),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        );

        register(context, OYSTERS_PLACED_KEY,
                configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.OYSTERS_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );

        register(context, DESERT_PLANTS_PLACED_KEY,
                configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.DESERT_PLANTS_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(12),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );

        register(context, BIRDS_NEST_PLACED_KEY,
                configuredFeatures.getOrThrow(SkyrimcraftConfiguredFeatures.BIRDS_NEST_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );
    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        PlacedFeature feature = new PlacedFeature(configuration, List.copyOf(modifiers));
        context.register(key, feature);
    }
}