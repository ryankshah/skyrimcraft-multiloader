package com.ryankshah.skyrimcraft.entity.ai.goal;

import com.ryankshah.skyrimcraft.entity.creature.DwarvenSpider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class SpiderSprintToNearestAttackableTargetGoal<T extends LivingEntity> extends TargetGoal {
    protected final Class<T> targetType;
    protected final int randomInterval;
    protected LivingEntity target;
    protected TargetingConditions targetConditions;

    public SpiderSprintToNearestAttackableTargetGoal(DwarvenSpider p_i50313_1_, Class<T> p_i50313_2_, boolean p_i50313_3_) {
        this(p_i50313_1_, p_i50313_2_, p_i50313_3_, false);
    }

    public SpiderSprintToNearestAttackableTargetGoal(DwarvenSpider p_i50314_1_, Class<T> p_i50314_2_, boolean p_i50314_3_, boolean p_i50314_4_) {
        this(p_i50314_1_, p_i50314_2_, 10, p_i50314_3_, p_i50314_4_, (Predicate<LivingEntity>)null);
    }

    public SpiderSprintToNearestAttackableTargetGoal(DwarvenSpider p_i50315_1_, Class<T> p_i50315_2_, int p_i50315_3_, boolean p_i50315_4_, boolean p_i50315_5_, @Nullable Predicate<LivingEntity> p_i50315_6_) {
        super(p_i50315_1_, p_i50315_4_, p_i50315_5_);
        this.targetType = p_i50315_2_;
        this.randomInterval = p_i50315_3_;
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(p_i50315_6_);
    }

    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        } else {
            this.findTarget();
            return this.target != null;
        }
    }

    protected AABB getTargetSearchArea(double p_188511_1_) {
        return this.mob.getBoundingBox().inflate(p_188511_1_, 4.0D, p_188511_1_);
    }

    protected void findTarget() {
        if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
            this.target = this.mob.level().getNearestEntity(this.targetType, this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), this.getTargetSearchArea(this.getFollowDistance()));
        } else {
            this.target = this.mob.level().getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }

    }

    public void start() {
        this.mob.setTarget(this.target);
        super.start();
        if(this.target != null && this.mob instanceof DwarvenSpider) // && canReach(this.target)
            ((DwarvenSpider)this.mob).setAnimationState(2);
    }

    public void stop() {
        this.mob.setTarget(null);
        if(this.mob instanceof DwarvenSpider)
            ((DwarvenSpider)this.mob).setAnimationState(0);
    }
}