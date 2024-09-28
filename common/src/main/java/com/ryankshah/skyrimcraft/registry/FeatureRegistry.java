package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.world.feature.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class FeatureRegistry
{
    public static void init() {}

    public static final RegistrationProvider<Feature<?>> FEATURES = RegistrationProvider.get(BuiltInRegistries.FEATURE, Constants.MODID);

    public static final RegistryObject<Feature<?>, Feature<NbtFeatureConfig>> NBT_FEATURE = FEATURES.register("nbt_feature", NbtFeature::new);
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> VOLCANO = FEATURES.register("volcano", VolcanoFeature::new);
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> MINI_CRATER = FEATURES.register("mini_crater", MiniCraterFeature::new);
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> LAVA_POOL = FEATURES.register("lava_pool", LavaPoolFeature::new);
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> LAVA_FOUNTAIN = FEATURES.register("lava_fountain", LavaFountainFeature::new);
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> DEAD_TREE = FEATURES.register("dead_tree", DeadTreeFeature::new);
    public static final RegistryObject<Feature<?>, Feature<NoneFeatureConfiguration>> PINE_TREE = FEATURES.register("pine_tree", PineTreeFeature::new);
}
