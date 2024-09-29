package com.ryankshah.skyrimcraft.block.entity;

import com.ryankshah.skyrimcraft.registry.BlockEntityRegistry;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

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

    public void tick() {
        if (isSpinning) {
            spinTicks++;
            if (spinTicks >= 20 * SPIN.lengthInSeconds()) {
                isSpinning = false;
                spinTicks = 0;
            }
            setChanged();
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

    public float getSpinProgress() {
        return spinTicks / (20f * SPIN.lengthInSeconds());
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