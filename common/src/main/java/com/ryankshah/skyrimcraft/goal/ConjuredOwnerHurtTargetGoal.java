package com.ryankshah.skyrimcraft.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class ConjuredOwnerHurtTargetGoal extends TargetGoal {
    private final Mob tameAnimal;
    private final LivingEntity owner;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public ConjuredOwnerHurtTargetGoal(Mob pTameAnimal, LivingEntity owner) {
        super(pTameAnimal, false);
        this.tameAnimal = pTameAnimal;
        this.owner = owner;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this method as well.
     */
    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.ownerLastHurt;
        if (livingentity == null) {
            return false;
        } else {
            this.ownerLastHurt = livingentity.getLastHurtMob();
            int i = livingentity.getLastHurtMobTimestamp();
            return i != this.timestamp
                    && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        LivingEntity livingentity = this.owner;
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtMobTimestamp();
        }

        super.start();
    }
}