package com.ryankshah.skyrimcraft.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LavaFountainFeature extends Feature<NoneFeatureConfiguration> {
    public LavaFountainFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();

        int height = 3 + rand.nextInt(3);

        // Create the fountain base
        for (int y = 0; y < height; y++) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos fountainPos = pos.offset(x, y, z);
                    if (x == 0 && z == 0) {
                        world.setBlock(fountainPos, Blocks.LAVA.defaultBlockState(), 2);
                    } else {
                        world.setBlock(fountainPos, Blocks.OBSIDIAN.defaultBlockState(), 2);
                    }
                }
            }
        }

        // Create the lava spout
        for (int y = height; y < height + 2; y++) {
            world.setBlock(pos.above(y), Blocks.LAVA.defaultBlockState(), 2);
        }

        return true;
    }
}