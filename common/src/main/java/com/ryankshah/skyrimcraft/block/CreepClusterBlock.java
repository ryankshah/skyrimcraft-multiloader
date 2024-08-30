package com.ryankshah.skyrimcraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CreepClusterBlock extends BushBlock
{
    public static final MapCodec<CreepClusterBlock> CODEC = simpleCodec(CreepClusterBlock::new);

    private VoxelShape shape = Shapes.or(
            Block.box(0, 0, 0, 16, 1.5, 16)
    );

    public CreepClusterBlock(Properties p_58162_) {
        super(p_58162_);
    }

    @Override
    public MapCodec<CreepClusterBlock> codec() {
        return CODEC;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);
        if (pLevel instanceof ServerLevel && pEntity instanceof Boat) {
            pLevel.destroyBlock(new BlockPos(pPos), true, pEntity);
        }
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return shape;
    }

//    @Override
//    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
//        BlockState blockstate = pLevel.getBlockState(pPos.below());
//        if (blockstate.is(this)) {
//            return true;
//        } else if(blockstate.canSustainPlant(pLevel, pPos.below(), Direction.UP, pState).isTrue()) {
//            return true;
//        } else {
//            return false;
//        }
//    }

//    @Override
//    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
//        return PlantType.CAVE;
//    }
//
//    @Override
//    public BlockState getPlant(BlockGetter level, BlockPos pos) {
//        return defaultBlockState();
//    }
}