package com.ryankshah.skyrimcraft.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VolcanoFeature extends Feature<NoneFeatureConfiguration> {
    public VolcanoFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();

        int height = 10 + rand.nextInt(10);
        int radius = 5 + rand.nextInt(3);

        // Create the volcano cone
        for (int y = 0; y < height; y++) {
            int layerRadius = radius * (height - y) / height;
            for (int x = -layerRadius; x <= layerRadius; x++) {
                for (int z = -layerRadius; z <= layerRadius; z++) {
                    if (x * x + z * z <= layerRadius * layerRadius) {
                        BlockPos volcanoPos = pos.offset(x, y, z);
                        world.setBlock(volcanoPos, Blocks.BASALT.defaultBlockState(), 2);
                    }
                }
            }
        }

        // Create lava core
        int coreRadius = radius / 2;
        for (int y = height / 2; y < height; y++) {
            for (int x = -coreRadius; x <= coreRadius; x++) {
                for (int z = -coreRadius; z <= coreRadius; z++) {
                    if (x * x + z * z <= coreRadius * coreRadius) {
                        BlockPos corePos = pos.offset(x, y, z);
                        world.setBlock(corePos, Blocks.LAVA.defaultBlockState(), 2);
                    }
                }
            }
        }

        return true;
    }
}