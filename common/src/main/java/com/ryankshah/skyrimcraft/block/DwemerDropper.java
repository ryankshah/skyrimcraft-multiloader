package com.ryankshah.skyrimcraft.block;

import com.ryankshah.skyrimcraft.block.entity.DwemerDropperBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.DropperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DwemerDropper extends DropperBlock {
    public DwemerDropper(Properties p_52942_) {
        super(p_52942_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DwemerDropperBlockEntity(pos, state);
    }
}
