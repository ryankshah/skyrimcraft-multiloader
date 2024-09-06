package com.ryankshah.skyrimcraft.entity.boss.dragon.goal;

import com.ryankshah.skyrimcraft.entity.boss.dragon.SkyrimDragon;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import java.util.EnumSet;

public class DragonMeleeAttackGoal extends MeleeAttackGoal {
    private final SkyrimDragon dragon;

    public DragonMeleeAttackGoal(SkyrimDragon dragon, double speedIn, boolean useLongMemory) {
        super(dragon, speedIn, useLongMemory);
        this.dragon = dragon;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        // Only use melee if the dragon is on the ground and near the target
        return !dragon.isFlying() && dragon.getAnimationState() == 2 && super.canUse();
    }

    @Override
    public void start() {
        super.start();
        dragon.setAggressive(true); // Indicate the dragon is engaging in melee combat
    }

    @Override
    public void stop() {
        super.stop();
        dragon.setAggressive(false);
    }
}