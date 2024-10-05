package com.ryankshah.skyrimcraft.block.entity;

import com.ryankshah.skyrimcraft.block.RuneStoneBlock;
import com.ryankshah.skyrimcraft.block.TurnStoneBlock;
import com.ryankshah.skyrimcraft.registry.BlockEntityRegistry;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.Random;

public class TurnStoneBlockEntity extends BlockEntity
{
    public static final AnimationDefinition SPIN = AnimationDefinition.Builder.withLength(1.0F)
            .addAnimation("bone", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -90.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    private boolean isSpinning = false;
    private long lastSpinTime;
    private int spinTicks = 0;

    public TurnStoneBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TURN_STONE.get(), pos, state);
    }

    public static void tick() {
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TurnStoneBlockEntity entity) {
        if (entity.isSpinning) {
            entity.spinTicks++;
            if (entity.spinTicks >= 20 * SPIN.lengthInSeconds()) {
                // Animation is complete
                entity.isSpinning = false;
                entity.spinTicks = 0;

                // Get current facing and calculate new facing
                Direction currentFacing = blockState.getValue(TurnStoneBlock.FACING);
                Direction newFacing = currentFacing.getClockWise();

                // Update block state with new facing
                BlockState newState = blockState.setValue(TurnStoneBlock.FACING, newFacing);
                level.setBlock(blockPos, newState, 3);

                // Debug output
                System.out.println("Rotation complete");
                System.out.println("Previous Facing: " + currentFacing);
                System.out.println("New Facing: " + newFacing);
                System.out.println("New Symbol: " + entity.getCurrentSymbol());

                // Update the RuneStone below
                BlockPos belowPos = blockPos.below();
                if (level.getBlockState(belowPos).getBlock() instanceof RuneStoneBlock) {
                    level.neighborChanged(belowPos, newState.getBlock(), blockPos);
                }
            }
        }
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if (t instanceof TurnStoneBlockEntity entity && entity.isSpinning) {
            entity.spinTicks++;
            if (entity.spinTicks >= 20 * SPIN.lengthInSeconds()) {
                // Animation is complete
                entity.isSpinning = false;
                entity.spinTicks = 0;

                // Get current facing and calculate new facing
                Direction currentFacing = blockState.getValue(TurnStoneBlock.FACING);
                Direction newFacing = currentFacing.getClockWise();

                // Update block state with new facing
                BlockState newState = blockState.setValue(TurnStoneBlock.FACING, newFacing);
                level.setBlock(blockPos, newState, 3);

                // Debug output
                System.out.println("Rotation complete");
                System.out.println("Previous Facing: " + currentFacing);
                System.out.println("New Facing: " + newFacing);
                System.out.println("New Symbol: " + entity.getCurrentSymbol());

                // Update the RuneStone below
                BlockPos belowPos = blockPos.below();
                if (level.getBlockState(belowPos).getBlock() instanceof RuneStoneBlock) {
                    level.neighborChanged(belowPos, newState.getBlock(), blockPos);
                }
            }
        }
    }


    public void startAnimation() {
        isSpinning = true;
        spinTicks = 0;
        setChanged();
    }

    public boolean isSpinning() {
        return isSpinning;
    }

    public Direction getFacing() {
        return this.getBlockState().getValue(TurnStoneBlock.FACING);
    }

    public float getSpinProgress() {
        return spinTicks / (20f * SPIN.lengthInSeconds());
    }

    private RuneStoneBlock.RuneSymbol getSymbolForDirection(Direction direction) {
        // When FACING=NORTH, the texture is on the north face (visible when looking south)
        return switch(direction) {
            case NORTH -> RuneStoneBlock.RuneSymbol.SYMBOL3; // Visible when looking south
            case EAST -> RuneStoneBlock.RuneSymbol.SYMBOL4;  // Visible when looking west
            case SOUTH -> RuneStoneBlock.RuneSymbol.SYMBOL1; // Visible when looking north
            case WEST -> RuneStoneBlock.RuneSymbol.SYMBOL2;  // Visible when looking east
            default -> RuneStoneBlock.RuneSymbol.SYMBOL1;
        };
    }

    public RuneStoneBlock.RuneSymbol getCurrentSymbol() {
        Direction facing = this.getBlockState().getValue(TurnStoneBlock.FACING);
        return getSymbolForDirection(facing);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        isSpinning = tag.getBoolean("IsSpinning");
        spinTicks = tag.getInt("SpinTicks");
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("IsSpinning", isSpinning);
        tag.putInt("SpinTicks", spinTicks);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }
}