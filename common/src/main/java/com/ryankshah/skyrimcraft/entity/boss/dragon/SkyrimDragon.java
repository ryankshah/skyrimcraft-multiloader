package com.ryankshah.skyrimcraft.entity.boss.dragon;

import com.ryankshah.skyrimcraft.entity.boss.dragon.goal.DragonLandingGoal;
import com.ryankshah.skyrimcraft.entity.boss.dragon.goal.DragonMeleeAttackGoal;
import com.ryankshah.skyrimcraft.entity.boss.dragon.goal.DragonRangedShoutGoal;
import com.ryankshah.skyrimcraft.entity.boss.dragon.goal.DragonTargetPlayerGoal;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableRangedAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomFlyingTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomHoverTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.UnreachableTargetSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

//TODO: Rework the entire dragon mechanics using SmartBrainLib @See(https://github.com/Tslat/SmartBrainLib/wiki/Making-an-Entity-With-SmartBrainLib)
public class SkyrimDragon extends PathfinderMob implements GeoEntity, Enemy, FlyingAnimal, RangedAttackMob, SmartBrainOwner<SkyrimDragon>
{
    private static final EntityDataAccessor<Integer> PREV_ANIMATION_STATE = SynchedEntityData.defineId(SkyrimDragon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(SkyrimDragon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_FLYING = SynchedEntityData.defineId(SkyrimDragon.class, EntityDataSerializers.BOOLEAN);

    public static final int ALTITUDE_FLYING_THRESHOLD = 3;
    private static final float SITTING_ALLOWED_DAMAGE_PERCENTAGE = 0.25F;

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

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    private boolean attacking, isLanding;

    public SkyrimDragon(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.setHealth(this.getMaxHealth());
        this.noPhysics = true;
        this.noCulling = true;
        this.xpReward = XP_REWARD_BOSS;
        this.setFlying(true); // Ensure the dragon starts flying
        this.setNoGravity(true); // Disable gravity while flying

        this.moveControl = new DragonMoveController(this); //new FlyingMoveControl(this, 20, true);
//        this.navigation = new FlyingPathNavigation(this, worldIn);
        // Add combat AI goals
        this.goalSelector.addGoal(1, new DragonLandingGoal(this));          // Landing goal
        this.goalSelector.addGoal(2, new DragonMeleeAttackGoal(this, 1.0D, true));  // Melee attack on the ground
        this.goalSelector.addGoal(3, new DragonRangedShoutGoal(this));       // Shout/fireball attack in the air

        // Add targeting goals
        this.targetSelector.addGoal(1, new DragonTargetPlayerGoal(this));    // Target only survival players
    }

    // Call this method when you want the dragon to land
    public void tryLanding() {
        if (!isFlying()) return;

        // Logic to determine when the dragon should land
        Player target = (Player) this.getTarget();
        if (target != null && this.distanceTo(target) < 20.0D) {
            this.isLanding = true;
            this.setFlying(false); // Switch to walking/ground movement
        }
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(false);
        return flyingpathnavigation;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

//    @Override
//    protected void customServerAiStep() {
//        super.customServerAiStep();
//    }

    @Nullable
    @Override
    public Component getCustomName() {
        return super.getCustomName();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 200.0f).add(Attributes.MOVEMENT_SPEED, 0.1f).add(Attributes.FLYING_SPEED, 0.1f).add(Attributes.JUMP_STRENGTH, 0.5D).add(Attributes.ATTACK_DAMAGE, 9.0D).add(Attributes.KNOCKBACK_RESISTANCE, 10000);
    }

    @Override
    protected float getBlockJumpFactor() {
        return super.getBlockJumpFactor();
    }

    @Override
    public boolean addEffect(MobEffectInstance pEffectInstance, @Nullable Entity pEntity) {
        return false;
    }

    @Override
    protected boolean canRide(Entity pEntity) {
        return false;
    }

    /**
     * Returns false if this Entity can't move between dimensions. True if it can.
     */
    @Override
    public boolean canChangeDimensions(Level p_352904_, Level p_352909_) {
        return false;
    }

    //    @Override
//    public boolean canAttack(LivingEntity pTarget) {
//        return pTarget.canBeSeenAsEnemy();
//    }

//    @Override
//    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
//        return pLevel.getBlockState(pPos.below()).is(Blocks.GRASS_BLOCK) ? 10.0F : pLevel.getBrightness(LightLayer.BLOCK, pPos) - 0.5F;
//    }

    public boolean shouldDropExperience() {
        return true;
    }

    protected boolean shouldDropLoot() {
        return true;
    }

    @Override
    public void kill() {
        this.remove(RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }

    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(ANIMATION_STATE, 0);
        pBuilder.define(PREV_ANIMATION_STATE, 0);
        pBuilder.define(DATA_FLYING, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        super.onSyncedDataUpdated(pKey);
    }

    public boolean canFly() {
        return true;
    }

    public boolean shouldFly() {
        return canFly() && !isInWater() && isHighEnough(ALTITUDE_FLYING_THRESHOLD);
    }

    /**
     * Returns the int-precision distance to solid ground.
     * Describe an inclusive limit to reduce iterations.
     */
    public double getAltitude(int limit) {
        var pointer = blockPosition().mutable().move(0, -1, 0);
        var min = level().dimensionType().minY();
        var i = 0;

        while(i <= limit && pointer.getY() > min && !level().getBlockState(pointer).isSolid()) //.getMaterial().isSolid())
            pointer.setY(getBlockY() - ++i);

        return i;
    }

    public boolean isReadyToLand() {
        // Detect how close the dragon is to the ground
        return this.getY() - this.level().getHeight(Heightmap.Types.MOTION_BLOCKING, Mth.floor(this.getX()), Mth.floor(this.getZ())) < 5; // Example: 5 blocks above ground
    }

    /**
     * Returns the distance to the ground while the entity is flying.
     */
    public double getAltitude() {
        return getAltitude(level().getMaxBuildHeight());
    }

    public boolean isHighEnough(int height) {
        return getAltitude(height) >= height;
    }

    public void liftOff() {
        if (canFly()) jumpFromGround();
    }

    @Override
    protected float getJumpPower() {
        // stronger jumps for easier lift-offs
        return super.getJumpPower() * (canFly()? 3 : 1);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return !canFly() && super.causeFallDamage(pFallDistance, pMultiplier, pSource);
    }

    /**
     * Returns true if the entity is flying.
     */
    public boolean isFlying() {
        return entityData.get(DATA_FLYING);
    }

    /**
     * Set the flying flag of the entity.
     */
    public void setFlying(boolean flying) {
        entityData.set(DATA_FLYING, flying);
    }

    public void addAdditionalSaveData(CompoundTag p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putInt("AnimationState", this.getAnimationState());
        p_213281_1_.putInt("PrevAnimationState", this.getPrevAnimationState());
    }

    public void readAdditionalSaveData(CompoundTag p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        this.setAnimationState(p_70037_1_.getInt("AnimationState"));
        this.setPrevAnimationState(p_70037_1_.getInt("PrevAnimationState"));
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        return true;
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENDER_DRAGON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.ENDER_DRAGON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDER_DRAGON_DEATH;
    }

//    @Override
//    public MobType getMobType() {
//        return MobType.UNDEFINED;
//    }

    @Override
    public boolean canAttackType(EntityType<?> pType) {
        return true;
    }

    public void setAnimationState(int animationState) {
        setPrevAnimationState(this.getAnimationState());
        this.entityData.set(PREV_ANIMATION_STATE, this.getAnimationState());
        this.entityData.set(ANIMATION_STATE, animationState);
    }
    public int getAnimationState() {
        System.out.println(this.entityData.get(ANIMATION_STATE));
        return this.entityData.get(ANIMATION_STATE);
    }

    public void setPrevAnimationState(int prevAnimationState) {
        this.entityData.set(PREV_ANIMATION_STATE, prevAnimationState);
    }
    public int getPrevAnimationState() { return this.entityData.get(PREV_ANIMATION_STATE); }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
//        this.anchorPoint = this.blockPosition().above(20);
        this.setAnimationState(0);
        this.setPrevAnimationState(0);
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    private <E extends SkyrimDragon> PlayState dragonController(final software.bernie.geckolib.animation.AnimationState<SkyrimDragon> event) {
        AnimationController<SkyrimDragon> controller = event.getController();
        controller.transitionLength(0);

        if(event.isMoving()) {
            event.getController().setAnimation(WALK);
        }

//        if (this.isSitting()) {
//        }

        event.getController().setAnimation(FLY_IDLE);

//        if(this.getAnimationState() == 0) {
//            return event.setAndContinue(FLY_IDLE);
//        } else if(this.getAnimationState() == 1) {
//            event.setAnimation(FLY_SHOUT_BREATHE);
//            return event.setAndContinue(FLY_IDLE);
//        } else if(this.getAnimationState() == 2) {
//            return event.setAndContinue(LAND);
//        } else if(this.getAnimationState() == 3) {
//            return event.setAndContinue(STAND);
//        } else if(this.getAnimationState() == 4) {
//            return event.setAndContinue(WALK);
//        } else if(this.getAnimationState() == 5) {
//            return event.setAndContinue(BITE);
//        } else if(this.getAnimationState() == 6) {
//            return event.setAndContinue(TAKE_OFF);
//        } else if(this.getAnimationState() == 7) {
//            return event.setAndContinue(GLIDE);
//        } else if(this.getAnimationState() == 8) {
//            return event.setAndContinue(DEATH);
//        } else if(this.getAnimationState() == 9) {
//            return event.setAndContinue(DEAD);
//        }

        return PlayState.CONTINUE; //event.setAndContinue(FLY_IDLE);
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "dragon_controller", 0, this::dragonController));
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

//    @Override
//    protected void customServerAiStep() {
//        tickBrain(this);
//    }

    @Override
    public void tick() {
        super.tick();

        if (this.isFlying()) {
            this.setNoGravity(true); // Disable gravity while flying
        } else {
            this.setNoGravity(false); // Enable gravity when on the ground
        }

        // If the dragon is landing, reduce its height gradually
        if (isLanding) {
            if (this.getY() > 2.0D) { // Avoid sinking below ground
                this.setDeltaMovement(0, -0.1D, 0);  // Slowly lower the dragon
            } else {
                this.isLanding = false;  // Once landed, stop
            }
        }
    }

    protected void findTarget() {
        // Iterate through all nearby players
        List<Player> players = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(50)); // 50-block range

        Player closestPlayer = null;
        double closestDistance = Double.MAX_VALUE;

        for (Player player : players) {
            // Only target players in Survival or Adventure mode
            if (!player.isCreative()) {
                double distance = this.distanceTo(player);

                // Find the closest player in range
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestPlayer = player;
                }
            }
        }

        // If we found a valid player, set the target
        if (closestPlayer != null) {
            this.setTarget(closestPlayer);
        }
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();

        tickBrain(this);

        // Check if the dragon should become non-idle
        Player nearestPlayer = this.level().getNearestPlayer(this, 32);
        if (nearestPlayer != null || this.getLastHurtByMob() != null) {
            // Enter fight phase
            this.setAnimationState(5);  // BITE if close
            if (!this.onGround()) {
                this.setAnimationState(6);  // TAKE_OFF if not on ground
            }
        } else {
            // Idle phases
            if (this.onGround()) {
                if (this.getRandom().nextFloat() < 0.5F) {
                    this.setAnimationState(3);  // STAND
                } else {
                    this.setAnimationState(4);  // WALK
                }
            } else {
                this.setAnimationState(0);  // FLY_IDLE
            }
        }
    }

    @Override
    public List<? extends ExtendedSensor<? extends SkyrimDragon>> getSensors() {
        return ObjectArrayList.of(new NearbyPlayersSensor<>(), new HurtBySensor<>(), new UnreachableTargetSensor<>());
    }

    @Override
    public BrainActivityGroup<SkyrimDragon> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),                      // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>() // Walk towards the current walk target
        );
    }

    @Override
    public BrainActivityGroup<SkyrimDragon> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<SkyrimDragon>(
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()
                ),
                new OneRandomBehaviour<>(
                        new SetRandomFlyingTarget<>()
                                .verticalWeight(e -> -4)
                                .whenStarting(a -> {
                                    a.getEntityData().set(PREV_ANIMATION_STATE, a.getEntityData().get(ANIMATION_STATE));
                                    if (a.onGround()) {
                                        a.getEntityData().set(ANIMATION_STATE, 3);  // STAND
                                    } else {
                                        a.getEntityData().set(ANIMATION_STATE, 0);  // FLY_IDLE
                                    }
                                }),
                        new SetRandomHoverTarget<>()
                                .whenStarting(a -> {
                                    a.getEntityData().set(PREV_ANIMATION_STATE, a.getEntityData().get(ANIMATION_STATE));
                                    if (!a.onGround()) {
                                        a.getEntityData().set(ANIMATION_STATE, 0);  // FLY_IDLE
                                    }
                                })
                )
        );
    }


    @Override
    public BrainActivityGroup<SkyrimDragon> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget(),
                new SetWalkTargetToAttackTarget<>(),
                new FirstApplicableBehaviour<SkyrimDragon>(
                        new AnimatableMeleeAttack<>(0)
                                .whenStarting(a -> {
                                    a.getEntityData().set(PREV_ANIMATION_STATE, a.getEntityData().get(ANIMATION_STATE));
                                    a.getEntityData().set(ANIMATION_STATE, 5);  // BITE
                                })
                                .cooldownFor(t -> 20),  // Attempt bite every second if nearby
                        new AnimatableRangedAttack<>(0)
                                .whenStarting(a -> {
                                    if (!a.onGround()) {
                                        a.getEntityData().set(ANIMATION_STATE, 6);  // TAKE_OFF
                                    }
                                })
                                .runFor(t -> 40) // Use shout and breathe in air
                                .whenStopping(a -> a.getEntityData().set(ANIMATION_STATE, 0))  // Reset to FLY_IDLE
                                .cooldownFor(t -> 600)  // 30 seconds cooldown
                ),
                new FirstApplicableBehaviour<SkyrimDragon>(
                        new SetRandomFlyingTarget<>()
                                .whenStarting(a -> {
                                    if (!a.onGround() && a.getRandom().nextBoolean()) {
                                        a.getEntityData().set(ANIMATION_STATE, 7);  // GLIDE
                                    }
                                }),
                        new SetRandomLookTarget<>()
                )
        );
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

//    @Override
//    public void travel(Vec3 pTravelVector) {
//        if (this.isControlledByLocalInstance()) {
//            if (this.isInWater()) {
//                this.moveRelative(0.02F, pTravelVector);
//                this.move(MoverType.SELF, this.getDeltaMovement());
//                this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
//            } else if (this.isInLava()) {
//                this.moveRelative(0.02F, pTravelVector);
//                this.move(MoverType.SELF, this.getDeltaMovement());
//                this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
//            } else {
//                BlockPos ground = getBlockPosBelowThatAffectsMyMovement();
//                float f = 0.91F;
//                if (this.onGround()) {
//                    f = this.level().getBlockState(ground).getBlock().getFriction() * 0.91F;
//                }
//
//                float f1 = 0.16277137F / (f * f * f);
//                f = 0.91F;
//                if (this.onGround()) {
//                    f = this.level().getBlockState(ground).getBlock().getFriction() * 0.91F;
//                }
//
//                this.moveRelative(this.onGround() ? 0.1F * f1 : 0.02F, pTravelVector);
//                this.move(MoverType.SELF, this.getDeltaMovement());
//                this.setDeltaMovement(this.getDeltaMovement().scale((double)f));
//            }
//        }
//
//        this.calculateEntityAnimation(false);
//    }

    public boolean isAttacking() {
        return this.attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.onGround() && pTravelVector.lengthSqr() > 0.1D) {
            // Walking or standing
            if (this.isAttacking()) {
                this.setAnimationState(4);  // WALK if moving on the ground
            } else {
                this.setAnimationState(3);  // STAND if idle on the ground
            }
        } else if (!this.onGround()) {
            // In air, handle TAKE_OFF or GLIDE transitions
            if (this.isAttacking()) {
                this.setAnimationState(7);  // GLIDE
            } else {
                this.setAnimationState(0);  // FLY_IDLE
            }
        }
        super.travel(pTravelVector);
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
    }

//    @Override
//    public boolean isFlying() {
//        return !this.onGround();
//    }

    @Override
    public boolean isFlapping() {
        return !this.onGround() && this.isFlying();
    }
}