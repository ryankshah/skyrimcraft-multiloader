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

    private static boolean isSpinning = false;
    private long lastSpinTime;
    private static int spinTicks = 0;
    private RuneStoneBlock.RuneSymbol currentSymbol;

    public TurnStoneBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TURN_STONE.get(), pos, state);
        this.currentSymbol = getRandomSymbol();
    }

    private RuneStoneBlock.RuneSymbol getRandomSymbol() {
        RuneStoneBlock.RuneSymbol[] symbols = RuneStoneBlock.RuneSymbol.values();
        return symbols[new Random().nextInt(symbols.length - 1) + 1]; // Exclude EMPTY
    }

    public static void tick() {
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TurnStoneBlockEntity entity) {
        if (entity.isSpinning) {
            entity.spinTicks++;
            if (entity.spinTicks >= 20 * SPIN.lengthInSeconds()) {
                entity.isSpinning = false;
                entity.spinTicks = 0;
                Direction newFacing = blockState.getValue(TurnStoneBlock.FACING).getClockWise();
                level.setBlock(blockPos, blockState.setValue(TurnStoneBlock.FACING, newFacing), 3);
                entity.rotateSymbol();
                entity.setChanged();
                level.sendBlockUpdated(blockPos, blockState, blockState, 3);

                // Update the RuneStone below
                BlockPos belowPos = blockPos.below();
                if (level.getBlockState(belowPos).getBlock() instanceof RuneStoneBlock) {
                    level.neighborChanged(belowPos, blockState.getBlock(), blockPos);
                }
            }
        }
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if (t instanceof TurnStoneBlockEntity entity && entity.isSpinning) {
            entity.spinTicks++;
            if (entity.spinTicks >= 20 * SPIN.lengthInSeconds()) {
                entity.isSpinning = false;
                entity.spinTicks = 0;
                Direction newFacing = blockState.getValue(TurnStoneBlock.FACING).getClockWise();
                level.setBlock(blockPos, blockState.setValue(TurnStoneBlock.FACING, newFacing), 3);
                entity.rotateSymbol();
                entity.setChanged();
                level.sendBlockUpdated(blockPos, blockState, blockState, 3);

                // Update the RuneStone below
                BlockPos belowPos = blockPos.below();
                if (level.getBlockState(belowPos).getBlock() instanceof RuneStoneBlock) {
                    level.neighborChanged(belowPos, blockState.getBlock(), blockPos);
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

    public void rotateSymbol() {
        RuneStoneBlock.RuneSymbol[] symbols = RuneStoneBlock.RuneSymbol.values();
        int nextIndex = (currentSymbol.ordinal() % (symbols.length - 1)) + 1; // Exclude EMPTY
        currentSymbol = symbols[nextIndex];
        setChanged();

        Level level = this.getLevel();
        if (level != null && !level.isClientSide) {
            BlockState state = level.getBlockState(this.getBlockPos());
            level.sendBlockUpdated(this.getBlockPos(), state, state, 3);
            ((TurnStoneBlock)state.getBlock()).updateRuneStoneBelow(level, this.getBlockPos(), state);
        }

        System.out.println("Starting rotation");
        System.out.println("Current Facing: " + this.getBlockState().getValue(TurnStoneBlock.FACING));
        System.out.println("Current Symbol: " + currentSymbol);
    }

    public RuneStoneBlock.RuneSymbol getCurrentSymbol() {
        return currentSymbol;
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        isSpinning = tag.getBoolean("IsSpinning");
        spinTicks = tag.getInt("SpinTicks");
        currentSymbol = RuneStoneBlock.RuneSymbol.valueOf(tag.getString("CurrentSymbol"));
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("IsSpinning", isSpinning);
        tag.putInt("SpinTicks", spinTicks);
        tag.putString("CurrentSymbol", currentSymbol.name());
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