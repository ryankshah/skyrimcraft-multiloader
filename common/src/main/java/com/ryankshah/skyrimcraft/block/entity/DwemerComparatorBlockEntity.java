package com.ryankshah.skyrimcraft.block.entity;

import com.ryankshah.skyrimcraft.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DwemerComparatorBlockEntity extends BlockEntity {
    private int output;

    public DwemerComparatorBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.DWEMER_COMPARATOR.get(), pos, blockState);
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("OutputSignal", this.output);
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.output = tag.getInt("OutputSignal");
    }

    public int getOutputSignal() {
        return this.output;
    }

    public void setOutputSignal(int output) {
        this.output = output;
    }
}
