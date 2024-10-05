package com.ryankshah.skyrimcraft.block.entity;

import com.ryankshah.skyrimcraft.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DwemerDropperBlockEntity extends DispenserBlockEntity {
    public DwemerDropperBlockEntity(BlockPos p_155498_, BlockState p_155499_) {
        super(BlockEntityRegistry.DWEMER_DROPPER.get(), p_155498_, p_155499_);
    }

    protected Component getDefaultName() {
        return Component.translatable("container.dropper");
    }
}
