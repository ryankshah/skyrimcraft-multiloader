package com.ryankshah.skyrimcraft.world;

import com.ryankshah.skyrimcraft.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WorldGenConstants
{
    public static final ResourceKey<PlacedFeature> CORUNDUM_ORE_PLACED_KEY = registerPlacedFeature("corundum_ore_placed");
    public static final ResourceKey<PlacedFeature> EBONY_ORE_PLACED_KEY = registerPlacedFeature("ebony_ore_placed");
    public static final ResourceKey<PlacedFeature> MALACHITE_ORE_PLACED_KEY = registerPlacedFeature("malachite_ore_placed");
    public static final ResourceKey<PlacedFeature> MOONSTONE_ORE_PLACED_KEY = registerPlacedFeature("moonstone_ore_placed");
    public static final ResourceKey<PlacedFeature> ORICHALCUM_ORE_PLACED_KEY = registerPlacedFeature("orichalcum_ore_placed");
    public static final ResourceKey<PlacedFeature> QUICKSILVER_ORE_PLACED_KEY = registerPlacedFeature("quicksilver_ore_placed");
    public static final ResourceKey<PlacedFeature> SILVER_ORE_PLACED_KEY = registerPlacedFeature("silver_ore_placed");

    public static final ResourceKey<PlacedFeature> MOUNTAIN_FLOWER_PLACED_KEY = registerPlacedFeature("mountain_flower_placed");
    public static final ResourceKey<PlacedFeature> LAVENDER_PLACED_KEY = registerPlacedFeature("lavender_placed");
    public static final ResourceKey<PlacedFeature> MUSHROOMS_PLACED_KEY = registerPlacedFeature("mushrooms_placed");
    public static final ResourceKey<PlacedFeature> CREEP_CLUSTER_PLACED_KEY = registerPlacedFeature("creep_cluster_placed");
    public static final ResourceKey<PlacedFeature> DESERT_PLANTS_PLACED_KEY = registerPlacedFeature("desert_plants_placed");
    public static final ResourceKey<PlacedFeature> OYSTERS_PLACED_KEY = registerPlacedFeature("oysters_placed");
    public static final ResourceKey<PlacedFeature> BIRDS_NEST_PLACED_KEY = registerPlacedFeature("birds_nest_placed");
    public static final ResourceKey<PlacedFeature> BUSHES_PLACED_KEY = registerPlacedFeature("bushes_placed");


    public static final ResourceKey<PlacedFeature> TREES_PLACED_KEY = registerPlacedFeature("trees_placed");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORUNDUM_ORE_KEY = registerConfiguredFeature("corundum_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EBONY_ORE_KEY = registerConfiguredFeature("ebony_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MALACHITE_ORE_KEY = registerConfiguredFeature("malachite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOONSTONE_ORE_KEY = registerConfiguredFeature("moonstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORICHALCUM_ORE_KEY = registerConfiguredFeature("orichalcum_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> QUICKSILVER_ORE_KEY = registerConfiguredFeature("quicksilver_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILVER_ORE_KEY = registerConfiguredFeature("silver_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> MOUNTAIN_FLOWER_KEY = registerConfiguredFeature("mountain_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LAVENDER_KEY = registerConfiguredFeature("lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MUSHROOMS_KEY = registerConfiguredFeature("mushrooms");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CREEP_CLUSTER_KEY = registerConfiguredFeature("creep_clusters");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DESERT_PLANTS_KEY = registerConfiguredFeature("desert_plants");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OYSTERS_KEY = registerConfiguredFeature("oysters");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIRDS_NEST_KEY = registerConfiguredFeature("birds_nest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BUSHES_KEY = registerConfiguredFeature("bushes");

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_KEY = registerConfiguredFeature("trees");

    private static ResourceKey<PlacedFeature> registerPlacedFeature(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, name));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerConfiguredFeature(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, name));
    }
}