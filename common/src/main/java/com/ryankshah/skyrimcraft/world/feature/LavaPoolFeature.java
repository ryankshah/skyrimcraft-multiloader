package com.ryankshah.skyrimcraft.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class LavaPoolFeature extends Feature<NoneFeatureConfiguration> {
    public LavaPoolFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();

        int radius = 2 + rand.nextInt(3);

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x * x + z * z <= radius * radius) {
                    BlockPos poolPos = pos.offset(x, 0, z);
                    world.setBlock(poolPos, Blocks.LAVA.defaultBlockState(), 2);

                    // Create obsidian border
                    if (x == -radius || x == radius || z == -radius || z == radius) {
                        world.setBlock(poolPos.above(), Blocks.OBSIDIAN.defaultBlockState(), 2);
                    }
                }
            }
        }

        return true;
    }
}