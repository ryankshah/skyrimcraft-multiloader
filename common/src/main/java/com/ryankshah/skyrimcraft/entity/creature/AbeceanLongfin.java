package com.ryankshah.skyrimcraft.entity.creature;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class AbeceanLongfin extends TropicalFish
{
    public AbeceanLongfin(EntityType<? extends TropicalFish> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static boolean checkSpawnRules(
            EntityType<AbeceanLongfin> pTropicalFish, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom
    ) {
        return pLevel.getFluidState(pPos.below()).is(FluidTags.WATER)
                && pLevel.getBlockState(pPos.above()).is(Blocks.WATER)
                && (
                pLevel.getBiome(pPos).is(BiomeTags.ALLOWS_TROPICAL_FISH_SPAWNS_AT_ANY_HEIGHT)
                        || WaterAnimal.checkSurfaceWaterAnimalSpawnRules(pTropicalFish, pLevel, pSpawnType, pPos, pRandom)
        );
    }
}
