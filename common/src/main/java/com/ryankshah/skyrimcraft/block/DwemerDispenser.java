package com.ryankshah.skyrimcraft.block;

import com.ryankshah.skyrimcraft.block.entity.DwemerDispenserBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DwemerDispenser extends DispenserBlock
{
    public DwemerDispenser(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DwemerDispenserBlockEntity(pos, state);
    }
}