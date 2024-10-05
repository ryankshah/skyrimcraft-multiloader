package com.ryankshah.skyrimcraft.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DeadTreeFeature extends Feature<NoneFeatureConfiguration> {
    public DeadTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();

        int height = 3 + rand.nextInt(3); // Tree height between 3 and 5 blocks

        // Generate trunk
        for (int y = 0; y < height; y++) {
            world.setBlock(pos.above(y), Blocks.ACACIA_LOG.defaultBlockState(), 2);

            // Add random branches
            if (y > height / 2 && rand.nextFloat() < 0.4f) {
                addBranch(world, pos.above(y), rand);
            }
        }

        // Add top branches
        int topBranches = 1 + rand.nextInt(2);
        for (int i = 0; i < topBranches; i++) {
            addBranch(world, pos.above(height - 1), rand);
        }

        return true;
    }

    private void addBranch(WorldGenLevel world, BlockPos pos, RandomSource rand) {
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
        int length = 1 + rand.nextInt(2); // Shorter branches, 1 to 2 blocks long

        for (int i = 0; i < length; i++) {
            pos = pos.relative(direction);
            world.setBlock(pos, Blocks.OAK_LOG.defaultBlockState(), 2);

            // Add some randomness to branch direction
            if (rand.nextFloat() < 0.3f) {
                direction = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
            }

            // Add some vertical variation, but less frequently
            if (rand.nextFloat() < 0.2f) {
                pos = rand.nextBoolean() ? pos.above() : pos.below();
            }
        }
    }
}