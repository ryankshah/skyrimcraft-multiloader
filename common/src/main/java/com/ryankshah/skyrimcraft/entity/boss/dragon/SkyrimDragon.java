package com.ryankshah.skyrimcraft.entity.boss.dragon;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;

public class SkyrimDragon extends PathfinderMob implements GeoEntity {

    private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(SkyrimDragon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_FLYING = SynchedEntityData.defineId(SkyrimDragon.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int attackCounter = 0;
    private int flyCounter = 0;
    private static final int MAX_TARGET_RANGE = 64;
    private int phaseDuration = 0;
    private Vec3 moveTarget;
    private Vec3 orbitCenter;
    private float orbitRadius;
    private float orbitAngle;
    private static final int FLIGHT_DURATION = 300; // 15 seconds (300 ticks)
    private static final int GROUND_DURATION = 300; // 15 seconds (300 ticks)

    private FlyingPathNavigation flyingNavigation;
    private GroundPathNavigation groundNavigation;

    // Animation definitions
    protected static final RawAnimation FLY_IDLE = RawAnimation.begin().thenLoop("animation.dragon.flyidle");
    protected static final RawAnimation GLIDE = RawAnimation.begin().thenLoop("animation.dragon.glide");
    protected static final RawAnimation STAND = RawAnimation.begin().thenLoop("animation.dragon.stand");
    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.dragon.walk");
    protected static final RawAnimation BITE = RawAnimation.begin().thenPlay("animation.dragon.bite");
    protected static final RawAnimation FLY_SHOUT_BREATHE = RawAnimation.begin().thenPlay("animation.dragon.flyshoutbreathe");
    protected static final RawAnimation TAKE_OFF = RawAnimation.begin().thenPlay("animation.dragon.takeoff");
    protected static final RawAnimation LAND = RawAnimation.begin().thenPlay("animation.dragon.land");
    protected static final RawAnimation DEATH = RawAnimation.begin().thenPlay("animation.dragon.death");
    protected static final RawAnimation DEAD = RawAnimation.begin().thenLoop("animation.dragon.dead");
    protected static final RawAnimation STAND_SHOUT_BREATHE = RawAnimation.begin().thenPlay("animation.dragon.standshoutbreathe");

    public enum Phase {
        FLY_IDLE, GLIDE, FLY_SHOUT_BREATH, STAND_SHOUT_BREATH, LAND, STAND, WALK, BITE, TAKEOFF, DEATH, DEAD
    }

    public SkyrimDragon(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SkyrimDragonMoveControl(this);
        this.flyingNavigation = new FlyingPathNavigation(this, level);
        this.groundNavigation = new GroundPathNavigation(this, level);
        this.navigation = this.flyingNavigation;
        this.setPhase(Phase.FLY_IDLE);
        this.setFlying(true);
        this.moveTarget = this.position().add(0, 10, 0);
        this.setNoGravity(true);
        this.noPhysics = true;
        this.orbitCenter = this.position();
        this.orbitRadius = 20.0F;
        this.orbitAngle = 0.0F;
        this.phaseDuration = 0;
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.setPhase(Phase.FLY_IDLE);
        this.setFlying(true);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SkyrimDragonAttackGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new SkyrimDragonWanderGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.6D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(PHASE, 0);
        builder.define(IS_FLYING, true);
        super.defineSynchedData(builder);
    }

    public Phase getPhase() {
        return Phase.values()[this.entityData.get(PHASE)];
    }

    public void setPhase(Phase phase) {
        this.entityData.set(PHASE, phase.ordinal());
        this.phaseDuration = 0;
        System.out.println(entityData.get(PHASE) + " : " + this.getPhase());
    }

    public boolean isFlying() {
        return this.entityData.get(IS_FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(IS_FLYING, flying);
        if (flying) {
            this.setNoGravity(true);
            this.noPhysics = true;
            this.navigation = this.flyingNavigation;
            this.moveControl = new FlyingMoveControl(this, 10, false);
        } else {
            this.setNoGravity(false);
            this.noPhysics = false;
            this.navigation = this.groundNavigation;
            this.moveControl = new MoveControl(this);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Phase", this.getPhase().ordinal());
        compound.putBoolean("IsFlying", this.isFlying());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setPhase(Phase.values()[compound.getInt("Phase")]);
        this.setFlying(compound.getBoolean("IsFlying"));
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            this.phaseDuration++;

            if (this.isFlying()) {
                this.setNoGravity(true);

                if (this.phaseDuration >= FLIGHT_DURATION) {
                    this.setPhase(Phase.LAND);
                    this.phaseDuration = 0;
                } else if (this.getPhase() == Phase.FLY_IDLE && this.random.nextInt(200) == 0) {
                    this.setPhase(Phase.GLIDE);
                } else if (this.getPhase() == Phase.GLIDE && this.random.nextInt(200) == 0) {
                    this.setPhase(Phase.FLY_IDLE);
                }

                this.updateOrbitPosition();
            } else {
                this.setNoGravity(false);

                if (this.phaseDuration >= GROUND_DURATION) {
                    this.setPhase(Phase.TAKEOFF);
                    this.phaseDuration = 0;
                } else if (this.getPhase() == Phase.WALK && this.random.nextInt(200) == 0) {
                    Vec3 randomGroundTarget = this.position().add(
                            this.random.nextInt(20) - 10,
                            0,
                            this.random.nextInt(20) - 10
                    );
                    this.getNavigation().moveTo(randomGroundTarget.x, randomGroundTarget.y, randomGroundTarget.z, 1.0);
                }
            }

            this.handlePhaseTransitions();


//            if (this.getPhase() == Phase.FLY_IDLE && this.random.nextInt(200) == 0) {
//                this.setPhase(Phase.GLIDE);
//            }
//
//            if (this.getPhase() == Phase.GLIDE && this.random.nextInt(200) == 0) {
//                this.setPhase(Phase.FLY_IDLE);
//            }

            if (this.getHealth() <= 0 && this.getPhase() != Phase.DEATH && this.getPhase() != Phase.DEAD) {
                this.setPhase(Phase.DEATH);
            }

            if (this.getPhase() == Phase.DEATH && this.phaseDuration >= 100) {
                this.setPhase(Phase.DEAD);
            }
        }
    }

    private void updateOrbitPosition() {
        this.orbitAngle += 0.05F;
        if (this.orbitAngle > Math.PI * 2) {
            this.orbitAngle -= Math.PI * 2;
        }

        double dx = Math.cos(this.orbitAngle) * this.orbitRadius;
        double dz = Math.sin(this.orbitAngle) * this.orbitRadius;

        this.moveTarget = new Vec3(
                this.orbitCenter.x + dx,
                this.orbitCenter.y + Math.sin(this.orbitAngle * 0.5) * 5, // Add some vertical movement
                this.orbitCenter.z + dz
        );

        this.getMoveControl().setWantedPosition(this.moveTarget.x, this.moveTarget.y, this.moveTarget.z, 1.0);
    }

    private void handlePhaseTransitions() {
        switch (this.getPhase()) {
            case LAND:
                if (this.onGround()) {
                    this.setPhase(Phase.WALK);
                    this.setFlying(false);
                }
                break;
            case TAKEOFF:
                if (this.phaseDuration >= 40) {
                    this.setPhase(Phase.FLY_IDLE);
                    this.setFlying(true);
                    this.orbitCenter = this.position();
                }
                break;
            // Add other phase transitions as needed
        }
    }


    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.getPhase() == Phase.DEAD) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENDER_DRAGON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENDER_DRAGON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDER_DRAGON_DEATH;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends GeoEntity> PlayState predicate(software.bernie.geckolib.animation.AnimationState<SkyrimDragon> event) {
        AnimationController<SkyrimDragon> controller = event.getController();
        switch (getPhase()) {
            case FLY_IDLE:
                controller.setAnimation(FLY_IDLE);
                break;
            case GLIDE:
                controller.setAnimation(GLIDE);
                break;
            case STAND:
                controller.setAnimation(STAND);
                break;
            case WALK:
                controller.setAnimation(WALK);
                break;
            case BITE:
                controller.setAnimation(BITE);
                break;
            case FLY_SHOUT_BREATH:
                controller.setAnimation(FLY_SHOUT_BREATHE);
                break;
            case TAKEOFF:
                controller.setAnimation(TAKE_OFF);
                break;
            case LAND:
                controller.setAnimation(LAND);
                break;
            case DEATH:
                controller.setAnimation(DEATH);
                break;
            case DEAD:
                controller.setAnimation(DEAD);
                break;
            case STAND_SHOUT_BREATH:
                controller.setAnimation(STAND_SHOUT_BREATHE);
                break;
            default:
                controller.setAnimation(FLY_IDLE);
                break;
        }
        return PlayState.CONTINUE;
    }

    static class SkyrimDragonAttackGoal extends Goal {
        private final SkyrimDragon dragon;
        private int cooldown = 0;

        public SkyrimDragonAttackGoal(SkyrimDragon dragon) {
            this.dragon = dragon;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = dragon.getTarget();
            return target != null && target.isAlive() && dragon.distanceToSqr(target) <= MAX_TARGET_RANGE * MAX_TARGET_RANGE;
        }

        @Override
        public void tick() {
            if (cooldown > 0) {
                cooldown--;
                return;
            }

            LivingEntity target = dragon.getTarget();
            if (target == null) return;

            switch (dragon.getPhase()) {
//                case FLY_IDLE:
                case GLIDE:
                if (dragon.random.nextInt(100) == 0) {
                    dragon.setPhase(Phase.LAND);
                    dragon.setFlying(false);
                }
                break;
                case LAND:
                    if (dragon.onGround()) {
                        dragon.setPhase(Phase.WALK);
                    }
                    break;
                case WALK:
                    if (dragon.distanceToSqr(target) <= 4.0) {
                        dragon.setPhase(Phase.BITE);
                        dragon.attackCounter++;
                    }
                    break;
                case BITE:
                    dragon.doHurtTarget(target);
                    if (dragon.attackCounter >= 3) {
                        dragon.setPhase(Phase.TAKEOFF);
                        dragon.setFlying(true);
                        dragon.attackCounter = 0;
                        dragon.flyCounter = 0;
                    } else {
                        dragon.setPhase(Phase.WALK);
                    }
                    break;
                case TAKEOFF:
                    if (dragon.phaseDuration >= 40) {
                        dragon.setPhase(Phase.FLY_IDLE);
                    }
                    break;
                case FLY_SHOUT_BREATH:
                    // Perform breath attack
                    if (target instanceof Player) {
                        ((Player) target).hurt(dragon.damageSources().dragonBreath(), 10.0F);
                    }
                    dragon.flyCounter++;
                    if (dragon.flyCounter >= 3) {
                        dragon.setPhase(Phase.LAND);
                        dragon.setFlying(false);
                        dragon.flyCounter = 0;
                    } else {
                        dragon.setPhase(Phase.FLY_IDLE);
                    }
                    break;
                case FLY_IDLE:
                    if (dragon.random.nextInt(100) == 0) {
                        dragon.setPhase(Phase.FLY_SHOUT_BREATH);
                    }
                    break;
                case STAND_SHOUT_BREATH:
                    // Perform ground breath attack
                    if (target instanceof Player) {
                        ((Player) target).hurt(dragon.damageSources().dragonBreath(), 8.0F);
                    }
                    dragon.setPhase(Phase.STAND);
                    break;
                case STAND:
                    if (dragon.random.nextInt(100) == 0) {
                        if (dragon.distanceToSqr(target) <= 16.0) {
                            dragon.setPhase(Phase.STAND_SHOUT_BREATH);
                        } else {
                            dragon.setPhase(Phase.WALK);
                        }
                    }
                    break;
            }

            cooldown = 20; // Set cooldown to 1 second (20 ticks)
        }
    }

    static class SkyrimDragonWanderGoal extends Goal {
        private final SkyrimDragon dragon;

        public SkyrimDragonWanderGoal(SkyrimDragon dragon) {
            this.dragon = dragon;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return dragon.getTarget() == null && dragon.random.nextInt(100) == 0;
        }

        @Override
        public void start() {
            Vec3 randomPos = findRandomPosition();
            if (randomPos != null) {
                dragon.moveTarget = randomPos;
            }
        }

        private Vec3 findRandomPosition() {
            Vec3 viewVector = dragon.getViewVector(1.0F);
            Vec3 randomDir = new Vec3(dragon.random.nextDouble() - 0.5, (dragon.random.nextDouble() - 0.5) * 0.25, dragon.random.nextDouble() - 0.5).normalize();
            return dragon.position().add(viewVector.add(randomDir).scale(16));
        }
    }

    static class SkyrimDragonMoveControl extends MoveControl {
        public SkyrimDragonMoveControl(SkyrimDragon dragon) {
            super(dragon);
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 movement = new Vec3(this.wantedX - mob.getX(), this.wantedY - mob.getY(), this.wantedZ - mob.getZ());
                double distance = movement.length();

                if (distance < 0.1) {
                    this.operation = MoveControl.Operation.WAIT;
                } else {
                    Vec3 motion = movement.normalize().scale(0.05); // Reduced speed for smoother movement
                    mob.setDeltaMovement(mob.getDeltaMovement().add(motion).scale(0.95)); // Increased drag for smoother turns

                    // Gradual rotation
                    double targetYaw = -Math.atan2(motion.x, motion.z) * (180F / Math.PI);
                    mob.setYRot(rotlerp(mob.getYRot(), (float)targetYaw, 2.0F));
                    mob.yBodyRot = mob.getYRot();
                }
            }
        }

        @Override
        protected float rotlerp(float currentRot, float targetRot, float maxChange) {
            float delta = angleDelta(targetRot - currentRot);
            if (delta > maxChange) delta = maxChange;
            if (delta < -maxChange) delta = -maxChange;
            return currentRot + delta;
        }

        private float angleDelta(float delta) {
            delta = delta % 360.0F;
            if (delta >= 180.0F) delta -= 360.0F;
            if (delta < -180.0F) delta += 360.0F;
            return delta;
        }
    }

}