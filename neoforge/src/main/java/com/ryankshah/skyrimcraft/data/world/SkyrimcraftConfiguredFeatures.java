package com.ryankshah.skyrimcraft.data.world;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

import static com.ryankshah.skyrimcraft.world.WorldGenConstants.*;

public class SkyrimcraftConfiguredFeatures
{
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplacables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> corundumOres = List.of(OreConfiguration.target(stoneReplaceable,
                        BlockRegistry.CORUNDUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, BlockRegistry.DEEPSLATE_CORUNDUM_ORE.get().defaultBlockState()));
        register(context, CORUNDUM_ORE_KEY, Feature.ORE, new OreConfiguration(corundumOres, 3));

        List<OreConfiguration.TargetBlockState> ebonyOres = List.of(OreConfiguration.target(stoneReplaceable,
                        BlockRegistry.EBONY_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, BlockRegistry.DEEPSLATE_EBONY_ORE.get().defaultBlockState()));
        register(context, EBONY_ORE_KEY, Feature.ORE, new OreConfiguration(ebonyOres, 2));

        List<OreConfiguration.TargetBlockState> malachiteOres = List.of(OreConfiguration.target(stoneReplaceable,
                        BlockRegistry.MALACHITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, BlockRegistry.DEEPSLATE_MALACHITE_ORE.get().defaultBlockState()));
        register(context, MALACHITE_ORE_KEY, Feature.ORE, new OreConfiguration(malachiteOres, 3));

        List<OreConfiguration.TargetBlockState> moonstoneOres = List.of(OreConfiguration.target(stoneReplaceable,
                        BlockRegistry.MOONSTONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, BlockRegistry.DEEPSLATE_MOONSTONE_ORE.get().defaultBlockState()));
        register(context, MOONSTONE_ORE_KEY, Feature.ORE, new OreConfiguration(moonstoneOres, 3));

        List<OreConfiguration.TargetBlockState> orichalcumOres = List.of(OreConfiguration.target(stoneReplaceable,
                        BlockRegistry.ORICHALCUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, BlockRegistry.DEEPSLATE_ORICHALCUM_ORE.get().defaultBlockState()));
        register(context, ORICHALCUM_ORE_KEY, Feature.ORE, new OreConfiguration(orichalcumOres, 3));

        List<OreConfiguration.TargetBlockState> quicksilverOres = List.of(OreConfiguration.target(stoneReplaceable,
                        BlockRegistry.QUICKSILVER_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, BlockRegistry.DEEPSLATE_QUICKSILVER_ORE.get().defaultBlockState()));
        register(context, QUICKSILVER_ORE_KEY, Feature.ORE, new OreConfiguration(quicksilverOres, 4));

        List<OreConfiguration.TargetBlockState> silverOres = List.of(OreConfiguration.target(stoneReplaceable,
                        BlockRegistry.SILVER_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, BlockRegistry.DEEPSLATE_SILVER_ORE.get().defaultBlockState()));
        register(context, SILVER_ORE_KEY, Feature.ORE, new OreConfiguration(silverOres, 5));

        register(context, LAVENDER_KEY, Feature.NO_BONEMEAL_FLOWER, new RandomPatchConfiguration(32, 3, 3,
                PlacementUtils.onlyWhenEmpty(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                new NoiseProvider(
                                        2345L,
                                        new NormalNoise.NoiseParameters(0, 1.0),
                                        0.020833334F,
                                        List.of(
                                                BlockRegistry.LAVENDER.get().defaultBlockState()
                                        )
                                )
                        ))));

        register(context, MOUNTAIN_FLOWER_KEY, Feature.FLOWER, new RandomPatchConfiguration(32, 7, 3,
                PlacementUtils.onlyWhenEmpty(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                new NoiseProvider(
                                        2345L,
                                        new NormalNoise.NoiseParameters(0, 1.0),
                                        0.020833334F,
                                        List.of(
                                                BlockRegistry.PURPLE_MOUNTAIN_FLOWER.get().defaultBlockState(),
                                                BlockRegistry.RED_MOUNTAIN_FLOWER.get().defaultBlockState(),
                                                BlockRegistry.BLUE_MOUNTAIN_FLOWER.get().defaultBlockState(),
                                                BlockRegistry.YELLOW_MOUNTAIN_FLOWER.get().defaultBlockState(),
                                                BlockRegistry.LAVENDER.get().defaultBlockState()
                                        )
                                )
                        ))));

        register(context, BUSHES_KEY, Feature.FLOWER, new RandomPatchConfiguration(32, 7, 3,
                PlacementUtils.onlyWhenEmpty(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                new NoiseProvider(
                                        2345L,
                                        new NormalNoise.NoiseParameters(0, 1.0),
                                        0.020833334F,
                                        List.of(
                                                BlockRegistry.JAZBAY_GRAPE_BUSH.get().defaultBlockState(),
                                                BlockRegistry.JUNIPER_BERRY_BUSH.get().defaultBlockState(),
                                                BlockRegistry.SNOWBERRY_BUSH.get().defaultBlockState()
                                        )
                                )
                        ))));

        register(context, MUSHROOMS_KEY, Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 7, 3,
                PlacementUtils.onlyWhenEmpty(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                new NoiseProvider(
                                        2345L,
                                        new NormalNoise.NoiseParameters(0, 1.0),
                                        0.020833334F,
                                        List.of(
                                                BlockRegistry.BLEEDING_CROWN_BLOCK.get().defaultBlockState(),
                                                BlockRegistry.WHITE_CAP_BLOCK.get().defaultBlockState(),
                                                BlockRegistry.BLISTERWORT_BLOCK.get().defaultBlockState(),
                                                BlockRegistry.FLY_AMANITA_BLOCK.get().defaultBlockState()
                                        )
                                )
                        ))));

        register(context, CREEP_CLUSTER_KEY, Feature.NO_BONEMEAL_FLOWER, new RandomPatchConfiguration(32, 3, 3,
                PlacementUtils.onlyWhenEmpty(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                new NoiseProvider(
                                        2345L,
                                        new NormalNoise.NoiseParameters(0, 1.0),
                                        0.020833334F,
                                        List.of(
                                                BlockRegistry.CREEP_CLUSTER_BLOCK.get().defaultBlockState()
                                        )
                                )
                        ))));

        register(context, OYSTERS_KEY, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(
                4, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                        new NoiseProvider(
                                2345L,
                                new NormalNoise.NoiseParameters(0, 1.0),
                                0.020833334F,
                                List.of(
                                        BlockRegistry.PEARL_OYSTER_BLOCK.get().defaultBlockState()
                                )
                        ))
                )));

        register(context, DESERT_PLANTS_KEY, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(
                4, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                        new NoiseProvider(
                                2345L,
                                new NormalNoise.NoiseParameters(0, 1.0),
                                0.020833334F,
                                List.of(
                                        BlockRegistry.CANIS_ROOT_BLOCK.get().defaultBlockState()
                                )
                        ))
                )));

        register(context, BIRDS_NEST_KEY, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(
                4, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                        new NoiseProvider(
                                2345L,
                                new NormalNoise.NoiseParameters(0, 1.0),
                                0.020833334F,
                                List.of(
                                        BlockRegistry.BIRDS_NEST.get().defaultBlockState()
                                )
                        ))
                )));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}