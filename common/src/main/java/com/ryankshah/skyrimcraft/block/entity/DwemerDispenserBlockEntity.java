package com.ryankshah.skyrimcraft.block.entity;

import com.ryankshah.skyrimcraft.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DwemerDispenserBlockEntity extends DispenserBlockEntity {
    public DwemerDispenserBlockEntity(BlockPos p_155498_, BlockState p_155499_) {
        super(BlockEntityRegistry.DWEMER_DISPENSER.get(), p_155498_, p_155499_);
    }

    protected Component getDefaultName() {
        return Component.translatable("container.dispenser");
    }
}