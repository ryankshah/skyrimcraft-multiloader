package com.ryankshah.skyrimcraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BirdsNestBlock extends Block
{
    private VoxelShape shape = Shapes.or(
            Block.box(0, 0, 0, 16, 4, 16)
    );

    public BirdsNestBlock() {
        super(Properties.ofFullCopy(Blocks.OAK_WOOD).noOcclusion());
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (!pEntity.isSteppingCarefully()) {
            pLevel.destroyBlock(pPos, false);
        }

        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return shape;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_149645_1_) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        if (blockstate.is(this)) {
            return true;
        } else if(blockstate.getBlock() == Blocks.GRASS_BLOCK) {
            return true;
        } else {
            return false;
        }
    }

//    @Override
//    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
//        return PlantType.PLAINS;
//    }
//
//    @Override
//    public BlockState getPlant(BlockGetter level, BlockPos pos) {
//        return defaultBlockState();
//    }
}