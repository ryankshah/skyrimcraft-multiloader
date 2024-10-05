package com.ryankshah.skyrimcraft.block.entity;

import com.ryankshah.skyrimcraft.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DaylightDetectorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DwemerDaylightDetectorBlockEntity extends BlockEntity {
    public DwemerDaylightDetectorBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.DWEMER_DAYLIGHT_DETECTOR.get(), pos, blockState);
    }
}