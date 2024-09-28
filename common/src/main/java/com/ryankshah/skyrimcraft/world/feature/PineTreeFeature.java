package com.ryankshah.skyrimcraft.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class PineTreeFeature extends Feature<NoneFeatureConfiguration> {
    public PineTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();

        int height = 8 + rand.nextInt(5);

        // Generate trunk
        for (int y = 0; y < height; y++) {
            world.setBlock(pos.above(y), Blocks.SPRUCE_LOG.defaultBlockState(), 2);
        }

        // Generate branches and leaves
        for (int y = 3; y < height; y++) {
            int radius = Math.max(1, (height - y) / 2);
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + z * z <= radius * radius) {
                        BlockPos leafPos = pos.offset(x, y, z);
                        if (world.isEmptyBlock(leafPos)) {
                            world.setBlock(leafPos, Blocks.SPRUCE_LEAVES.defaultBlockState(), 2);
                        }
                    }
                }
            }
        }

        return true;
    }
}

