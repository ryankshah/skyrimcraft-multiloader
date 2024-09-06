package com.ryankshah.skyrimcraft.block;

import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class CabbageCrop extends CropBlock
{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public CabbageCrop(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxAge() {
        return 5;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ItemRegistry.CABBAGE_SEEDS.get();
    }
}