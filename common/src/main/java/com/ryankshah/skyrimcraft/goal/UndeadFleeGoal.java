package com.ryankshah.skyrimcraft.goal;

import com.ryankshah.skyrimcraft.effect.ModEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.function.Predicate;

public class UndeadFleeGoal extends Goal
{
    private PathfinderMob fleeingEntity;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    protected LivingEntity toAvoid;
    protected final float maxDist;
    protected Path path;
    protected final PathNavigation pathNav;
    protected final Predicate<LivingEntity> avoidPredicate;
    protected final Predicate<LivingEntity> predicateOnAvoidEntity;
    private final TargetingConditions avoidEntityTargeting;

    private boolean shouldFlee;

    public UndeadFleeGoal(PathfinderMob p_i50037_1_, float p_i50037_3_, double p_i50037_4_, double p_i50037_6_) {
        this(p_i50037_1_, (p_200828_0_) -> {
            return true;
        }, p_i50037_3_, p_i50037_4_, p_i50037_6_, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
        this.fleeingEntity = p_i50037_1_;
        shouldFlee = false;
    }

    public UndeadFleeGoal(PathfinderMob p_i48859_1_, Predicate<LivingEntity> p_i48859_3_, float p_i48859_4_, double p_i48859_5_, double p_i48859_7_, Predicate<LivingEntity> p_i48859_9_) {
        this.fleeingEntity = p_i48859_1_;
        this.avoidPredicate = p_i48859_3_;
        this.maxDist = p_i48859_4_;
        this.walkSpeedModifier = p_i48859_5_;
        this.sprintSpeedModifier = p_i48859_7_;
        this.predicateOnAvoidEntity = p_i48859_9_;
        this.pathNav = p_i48859_1_.getNavigation();
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.avoidEntityTargeting = TargetingConditions.forCombat(); // (new TargetingConditions(true)).range((double)p_i48859_4_).selector(p_i48859_9_.and(p_i48859_3_));
    }

    public UndeadFleeGoal(PathfinderMob p_i48860_1_, Player p_i48860_2_, float p_i48860_3_, double p_i48860_4_, double p_i48860_6_, Predicate<LivingEntity> p_i48860_8_) {
        this(p_i48860_1_, (p_203782_0_) -> {
            return true;
        }, p_i48860_3_, p_i48860_4_, p_i48860_6_, p_i48860_8_);
    }

    public boolean isShouldFlee() {
        return true;
    }

    public void setShouldFlee(boolean shouldFlee) {
        this.shouldFlee = shouldFlee;
    }

    @Override
    public boolean canUse() {
        this.toAvoid = this.fleeingEntity.level().getNearestPlayer(fleeingEntity, 12D); // 12D == radius?
        this.fleeingEntity.goalSelector.disableControlFlag(Flag.TARGET);
        if (this.toAvoid == null || !this.toAvoid.hasEffect(ModEffects.UNDEAD_FLEE.asHolder())) {
            return false;
        } else {
            Vec3 vector3d = DefaultRandomPos.getPosAway(this.fleeingEntity, 16, 7, this.toAvoid.position());
            if (vector3d == null) {
                return false;
            } else if (this.toAvoid.distanceToSqr(vector3d.x, vector3d.y, vector3d.z) < this.toAvoid.distanceToSqr(this.fleeingEntity)) {
                return false;
            } else {
                this.path = this.pathNav.createPath(vector3d.x, vector3d.y, vector3d.z, 0);
                return this.path != null;
            }
        }
    }

    public boolean canContinueToUse() {
        return !this.pathNav.isDone();
    }

    public void start() {
        this.pathNav.moveTo(this.path, this.walkSpeedModifier);
    }

    public void stop() {
        this.fleeingEntity.goalSelector.setControlFlag(Flag.TARGET, true);
        this.toAvoid = null;
    }

    @Override
    public void tick() {
        // Add helix of particles around the entity
        // fleeingEntity.level.addParticle ...
        if(isShouldFlee() && canContinueToUse()) {
            for (double phi = 0; phi <= Math.PI * 2; phi += Math.PI * 2 / 6)
            {
                double z = Math.cos(phi) * 0.8;
                double x = Math.sin(phi) * 0.8;
                if(fleeingEntity.level() instanceof ServerLevel)
                    ((ServerLevel) fleeingEntity.level()).sendParticles(ParticleTypes.SMOKE, fleeingEntity.getX() + x, fleeingEntity.getY() + fleeingEntity.getEyeHeight(), fleeingEntity.getZ() + z, 1, 0D, 0D, 0D, 0); // set amount to 0 so particles don't fly off and stays in place
            }

//            for(double y = 0; y <= fleeingEntity.getEyeHeight(); y+=0.05) {
//                double x = 1.125 * Math.cos(y);
//                double z = 1.125 * Math.sin(y);
//                if (fleeingEntity.level instanceof ServerWorld) {
//                    ((ServerWorld) fleeingEntity.level).sendParticles(ParticleTypes.TOTEM_OF_UNDYING, fleeingEntity.getX() + x, fleeingEntity.getY() + y, fleeingEntity.getZ() + z, 1, 0D, 0D, 0D, 0.0D);
//                }
//            }
        }
        if (this.fleeingEntity.distanceToSqr(this.toAvoid) < 49.0D) {
            this.fleeingEntity.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
        } else {
            this.fleeingEntity.getNavigation().setSpeedModifier(this.walkSpeedModifier);
        }
        super.tick();
    }
}