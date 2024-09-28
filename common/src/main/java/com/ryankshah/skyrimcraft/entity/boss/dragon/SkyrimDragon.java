package com.ryankshah.skyrimcraft.entity.boss.dragon;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;

public class SkyrimDragon extends PathfinderMob implements GeoEntity {

    private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(SkyrimDragon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_FLYING = SynchedEntityData.defineId(SkyrimDragon.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private FlyingPathNavigation flyingNavigation;
    private GroundPathNavigation groundNavigation;

    private static final double MIN_FLYING_HEIGHT = 40.0; // Increased from 30.0
    private static final double MAX_FLYING_HEIGHT = 80.0; // Increased from 80.0
    private static final double VERTICAL_ADJUST_SPEED = 0.05; // Reduced from 0.1
    private static final int GROUND_PHASE_DURATION = 600; // Reduced from 1200 (30 seconds)
    private static final double DESCENT_SPEED = 0.5; // Increased from 0.2
    private static final double LANDING_THRESHOLD = 5.0; // Increased from 2.0
    private static final int DESCENT_TIMEOUT = 200; // Reduced from 400
    private static final double DESCENT_ANGLE = 20.0; // Reduced from 30.0
    private static final double ELLIPSE_SPEED = 0.02;
    private static final double TAKEOFF_VERTICAL_SPEED = 0.2;
    private static final int TRANSITION_DURATION = 60; // 3 seconds
    private static final double IDEAL_FLIGHT_HEIGHT = 40.0;
    private static final double TAKEOFF_HORIZONTAL_SPEED = 0.2;
    private static final double MAX_VERTICAL_SPEED = 0.4;
    private static final int TAKE_OFF_DURATION = 60; // 3 seconds
    private static final double TAKE_OFF_HEIGHT = 10.0;
    private static final int MIN_GROUND_DURATION = 200; // 10 seconds
    private static final int MAX_GROUND_DURATION = 600; // 30 seconds
    private static final int MIN_FLY_DURATION = 200; // 10 seconds
    private static final int MAX_FLY_DURATION = 600; // 30 seconds
    private static final double MAX_SPEED = 0.3; // New constant to limit speed
//    private static final double LANDING_THRESHOLD = 2.0; // Reduced from 5.0
    private static final int LANDING_DURATION = 80; // Increased from 40
    private int takeoffTicks = 0;
    private int groundPhaseTicks = 0;
    private BlockPos landingTarget;
    private int phaseDuration = 0;
    private Vec3 moveTarget;
    private int currentPhaseDuration;
    private int descentTicks = 0;
    private int transitionTicks = 0;
    private int landingTicks = 0;
    private Vec3 ellipseCenter;
    private double ellipseA; // Semi-major axis
    private double ellipseB; // Semi-minor axis
    private double ellipseAngle;
    private boolean isLanding = false;

    private static final boolean DEBUG = false;  // Set this to true for detailed logging/testing

    private void debugLog(String message) {
        if (DEBUG) {
            System.out.println("[SkyrimDragon] " + message);
        }
    }

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
        FLY_IDLE, GLIDE, DESCEND, LAND, STAND, WALK, BITE, TAKEOFF, ASCEND, TRANSITION_TO_FLY, DEATH, DEAD, FLY_SHOUT_BREATH, STAND_SHOUT_BREATH
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
        this.noPhysics = false;
        this.phaseDuration = 0;
        this.setPersistenceRequired(); // Add this line to make the dragon persist
//        this.setPathfindingMalus(PathType.WATER, -1.0F);
//        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
    }

    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.setPhase(Phase.FLY_IDLE);
        this.setFlying(true);
        this.setPos(this.getX(), this.getY() + 5, this.getZ()); // Spawn slightly above ground
        this.moveTarget = this.position().add(0, 10, 0);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    protected void registerGoals() {
//        this.goalSelector.addGoal(1, new WaterAvoidingRandomFlyingGoal(this, 1.0F));
        this.goalSelector.addGoal(1, new SkyrimDragonWanderGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.6D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.STEP_HEIGHT, 1.5f);
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
        Phase oldPhase = this.getPhase();
        this.entityData.set(PHASE, phase.ordinal());
        this.currentPhaseDuration = getNewPhaseDuration(phase);
        debugLog("Phase set from " + oldPhase + " to " + phase + " with duration " + this.currentPhaseDuration);

        // Additional phase-specific setup
        switch (phase) {
            case DESCEND:
                this.descentTicks = 0;
                break;
            case LAND:
                this.landingTicks = 0;
                this.isLanding = true;
                break;
            case TAKEOFF:
                this.takeoffTicks = 0;
                break;
            case TRANSITION_TO_FLY:
                this.transitionTicks = 0;
                break;
        }
    }


    private void checkEntityRemoval() {
        if (this.isRemoved()) {
            System.out.println("Dragon entity has been removed! Last known position: " + this.position());
        }
    }

    public boolean isFlying() {
        return this.entityData.get(IS_FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(IS_FLYING, flying);
        if (flying) {
            this.setNoGravity(true);
            this.navigation = this.flyingNavigation;
            this.moveControl = new SkyrimDragonMoveControl(this);
        } else {
            this.setNoGravity(false);
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

    private void ensureWithinLoadedChunks() {
        Level world = this.level();
        BlockPos currentPos = this.blockPosition();
        if (!world.hasChunkAt(currentPos)) {
            // Move the dragon back to the last known loaded position
            BlockPos safePos = findNearestLoadedChunk(currentPos);
            this.setPos(safePos.getX(), safePos.getY(), safePos.getZ());
            this.initializeEllipse(); // Reinitialize the flight path
            debugLog("Dragon moved to safe position: " + safePos);
        }
    }

    private BlockPos findNearestLoadedChunk(BlockPos currentPos) {
        Level world = this.level();
        int searchRadius = 5;
        for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int z = -searchRadius; z <= searchRadius; z++) {
                BlockPos checkPos = currentPos.offset(x * 16, 0, z * 16);
                if (world.hasChunkAt(checkPos)) {
                    return checkPos;
                }
            }
        }
        // If no loaded chunks found, return spawn point as a fallback
        return world.getSharedSpawnPos();
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            // Safety check to prevent falling through the world
            if (this.getY() < this.level().getMinBuildHeight()) {
                BlockPos safePos = this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, this.blockPosition());
                this.setPos(safePos.getX(), safePos.getY() + 5, safePos.getZ());
                this.setDeltaMovement(Vec3.ZERO);
                this.setPhase(Phase.FLY_IDLE);
                System.out.println("Dragon reset to safe position: " + this.position());
            }

            this.ensureWithinLoadedChunks();
            this.phaseDuration++;
            this.currentPhaseDuration--;

            debugLog("Current phase: " + this.getPhase() + ", Duration left: " + this.currentPhaseDuration);

            switch (this.getPhase()) {
                case FLY_IDLE:
                case GLIDE:
                    this.handleFlying();
                    break;
                case DESCEND:
                    this.handleDescent();
                    break;
                case LAND:
                    this.handleLanding();
                    break;
                case WALK:
                case STAND:
                    this.handleGroundMovement();
                    break;
                case TAKEOFF:
                    this.handleTakeoff();
                    break;
                case TRANSITION_TO_FLY:
                    this.handleTransitionToFly();
                    break;
            }

            // Force phase transition check if duration is up
            if (this.currentPhaseDuration <= 0) {
                debugLog("Force checking phase transition due to duration expiry");
                this.handlePhaseTransitions();
            }
            this.checkEntityRemoval();
        }
    }

    private BlockPos findSuitableLandingSpot() {
        int searchRadius = 20;
        BlockPos dragonPos = this.blockPosition();

        for (int i = 0; i < 10; i++) { // Try up to 10 times
            int x = dragonPos.getX() + this.random.nextInt(searchRadius * 2) - searchRadius;
            int z = dragonPos.getZ() + this.random.nextInt(searchRadius * 2) - searchRadius;
            BlockPos potentialSpot = this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x, 0, z));

            if (isSuitableLandingSpot(potentialSpot)) {
                return potentialSpot;
            }
        }
        return null; // No suitable spot found
    }

    private boolean isSuitableLandingSpot(BlockPos pos) {
        // Check if the spot and the block above it are air
        if (!this.level().getBlockState(pos.above()).isAir() || !this.level().getBlockState(pos.above(2)).isAir()) {
            return false;
        }

        // Check for water in a 3x3 area around the landing spot
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos checkPos = pos.offset(dx, -1, dz);
//                if (this.level().getBlockState(checkPos).getFluidState().is(FluidTags.WATER)) {
                if(GoalUtils.isWater(this, checkPos)) {
                    return false;
                }
            }
        }

        return true;
    }


    private void handleFlying() {
        this.flyInEllipse();
        this.adjustFlightHeight();
    }

    private double getGroundHeight(BlockPos pos) {
        return this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos).getY();
    }

    private double getHighestSurroundingHeight() {
        double highestY = Double.NEGATIVE_INFINITY;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int dx = -5; dx <= 5; dx++) {
            for (int dz = -5; dz <= 5; dz++) {
                mutablePos.set(this.getX() + dx, this.getY(), this.getZ() + dz);
                double height = this.level().getHeight(Heightmap.Types.MOTION_BLOCKING, mutablePos.getX(), mutablePos.getZ());
                highestY = Math.max(highestY, height);
            }
        }

        return highestY;
    }

    private void adjustFlightHeight() {
        double groundHeight = this.getHighestSurroundingHeight();
        double currentHeight = this.getY() - groundHeight;

        if (currentHeight < MIN_FLYING_HEIGHT) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, VERTICAL_ADJUST_SPEED, 0));
        } else if (currentHeight > MAX_FLYING_HEIGHT) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, -VERTICAL_ADJUST_SPEED, 0));
        }
    }

    private void initializeEllipse() {
        double currentHeight = this.getY();
        this.ellipseCenter = new Vec3(this.getX(), currentHeight, this.getZ());
        this.ellipseA = 40 + this.random.nextDouble() * 20; // Increased from 20 + random * 10
        this.ellipseB = 30 + this.random.nextDouble() * 15; // Increased from 15 + random * 10
        this.ellipseAngle = this.random.nextDouble() * Math.PI * 2;
    }

    private void flyInEllipse() {
        if (this.ellipseCenter == null) {
            this.initializeEllipse();
        }

        this.ellipseAngle += ELLIPSE_SPEED;
        if (this.ellipseAngle > Math.PI * 2) {
            this.ellipseAngle -= Math.PI * 2;
        }

        double x = this.ellipseCenter.x + this.ellipseA * Math.cos(this.ellipseAngle);
        double z = this.ellipseCenter.z + this.ellipseB * Math.sin(this.ellipseAngle);

        double heightVariation = (Math.sin(this.ellipseAngle * 2) + 1) / 2;
        double targetY = this.getGroundHeight(new BlockPos((int)x, (int)this.getY(), (int)z))
                + Mth.lerp(heightVariation, MIN_FLYING_HEIGHT, MAX_FLYING_HEIGHT);

        double currentY = this.getY();
        double smoothY = Mth.lerp(0.1, currentY, targetY);

        Vec3 targetPos = new Vec3(x, smoothY, z);
        Vec3 movement = targetPos.subtract(this.position());

        double speed = movement.length();
        double targetSpeed = MAX_SPEED;
        if (speed > targetSpeed) {
            movement = movement.scale(targetSpeed / speed);
        }

        this.setDeltaMovement(movement);
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.lookAt(EntityAnchorArgument.Anchor.EYES, targetPos);
    }

    private void handleDescent() {
        if (landingTarget == null) {
            landingTarget = findSuitableLandingSpot();
            if (landingTarget == null) {
                debugLog("No suitable landing spot found. Returning to FLY_IDLE.");
                this.setPhase(Phase.FLY_IDLE);
                return;
            }
        }

        double targetY = landingTarget.getY() + LANDING_THRESHOLD;
        double currentHeight = this.getY();

        if (currentHeight > targetY) {
            Vec3 toTarget = Vec3.atCenterOf(landingTarget).subtract(this.position()).normalize();
            Vec3 descentVector = new Vec3(toTarget.x, -DESCENT_SPEED, toTarget.z).normalize();

            this.setDeltaMovement(descentVector.scale(DESCENT_SPEED));
            this.move(MoverType.SELF, this.getDeltaMovement());

            this.getLookControl().setLookAt(
                    landingTarget.getX(),
                    landingTarget.getY(),
                    landingTarget.getZ()
            );

            debugLog("Descending. Current height: " + currentHeight + ", Target height: " + targetY);
        } else {
            debugLog("Reached landing threshold. Transitioning to LAND phase.");
            this.setPhase(Phase.LAND);
            this.landingTicks = 0;
        }
    }


    private void handleLanding() {
        double groundHeight = landingTarget.getY();
        double currentHeight = this.getY();

        if (landingTicks < LANDING_DURATION) {
            double progress = (double) landingTicks / LANDING_DURATION;
            double easedProgress = 1 - Math.pow(1 - progress, 3); // Ease out cubic
            double targetY = Mth.lerp(easedProgress, currentHeight, groundHeight);

            this.setPos(this.getX(), targetY, this.getZ());

            Vec3 motion = this.getDeltaMovement();
            double speedReduction = 1 - easedProgress;
            this.setDeltaMovement(motion.x * speedReduction, motion.y * speedReduction, motion.z * speedReduction);

            landingTicks++;

            debugLog("Landing. Progress: " + progress + ", Current height: " + this.getY());
        } else {
            debugLog("Landed. Transitioning to STAND phase.");
            this.setOnGround(true);
            this.setDeltaMovement(Vec3.ZERO);
            this.setPhase(Phase.STAND);
            this.setFlying(false);
            this.groundPhaseTicks = 0;
            this.landingTarget = null;
        }
    }

    private void handleTakeoff() {
        if (takeoffTicks < TAKE_OFF_DURATION) {
            double progress = (double) takeoffTicks / TAKE_OFF_DURATION;
            double easedProgress = progress * progress * (3 - 2 * progress); // Ease in-out

            double startY = this.getGroundHeight(this.blockPosition());
            double targetY = startY + TAKE_OFF_HEIGHT;
            double newY = Mth.lerp(easedProgress, startY, targetY);

            this.setPos(this.getX(), newY, this.getZ());

            // Gradually increase upward momentum
            double verticalSpeed = 0.2 * easedProgress;
            this.setDeltaMovement(this.getDeltaMovement().add(0, verticalSpeed, 0));

            takeoffTicks++;
        } else {
            // Takeoff complete, transition directly to FLY_IDLE
            this.setPhase(Phase.FLY_IDLE);
            this.setFlying(true);
            this.initializeEllipse();
        }
    }

    private void handleTransitionToFly() {
        if (transitionTicks < TRANSITION_DURATION) {
            double progress = (double) transitionTicks / TRANSITION_DURATION;
            double easedProgress = progress * progress * (3 - 2 * progress); // Ease in-out

            // Smoothly adjust height if needed
            double currentHeight = this.getY() - this.getGroundHeight(this.blockPosition());
            double heightDifference = IDEAL_FLIGHT_HEIGHT - currentHeight;
            double verticalAdjustment = heightDifference * 0.05 * easedProgress;

            // Limit vertical speed
            verticalAdjustment = Mth.clamp(verticalAdjustment, -MAX_VERTICAL_SPEED, MAX_VERTICAL_SPEED);

            this.setDeltaMovement(this.getDeltaMovement().add(0, verticalAdjustment, 0));

            // Gradually increase horizontal speed
            Vec3 horizontalMotion = this.getLookAngle().multiply(1, 0, 1).normalize().scale(0.3 * easedProgress);
            this.setDeltaMovement(this.getDeltaMovement().add(horizontalMotion));

            transitionTicks++;
        } else {
            this.setPhase(Phase.FLY_IDLE);
            this.initializeEllipse();
            this.currentPhaseDuration = this.random.nextInt(MAX_FLY_DURATION - MIN_FLY_DURATION + 1) + MIN_FLY_DURATION;
        }
    }

    private void handleGroundMovement() {
        groundPhaseTicks++;
        if (groundPhaseTicks >= GROUND_PHASE_DURATION) {
            debugLog("Ground phase complete. Preparing for takeoff.");
            this.setPhase(Phase.TAKEOFF);
            this.takeoffTicks = 0;
            return;
        }

        if (this.getPhase() == Phase.WALK) {
            if (!this.getNavigation().isInProgress()) {
                debugLog("Walk completed. Transitioning to STAND.");
                this.setPhase(Phase.STAND);
            }
        } else if (this.random.nextInt(100) == 0) {
            debugLog("Initiating random walk.");
            this.setPhase(Phase.WALK);
            Vec3 randomPos = this.position().add(
                    this.random.nextInt(20) - 10,
                    0,
                    this.random.nextInt(20) - 10
            );
            this.getNavigation().moveTo(randomPos.x, randomPos.y, randomPos.z, 1.0);
        }

        debugLog("Ground movement. Phase: " + this.getPhase() + ", Ticks: " + groundPhaseTicks);
    }

    private void handlePhaseTransitions() {
        Phase currentPhase = this.getPhase();
        Phase newPhase = currentPhase;

        debugLog("Checking phase transition. Current phase: " + currentPhase);

        if (this.currentPhaseDuration <= 0) {
            switch (currentPhase) {
                case FLY_IDLE:
                case GLIDE:
                    if (this.random.nextInt(100) < 30 && this.currentPhaseDuration <= 0) {
                        newPhase = Phase.DESCEND;
                        debugLog("Initiating descent.");
                    }
                    break;
                case DESCEND:
                    // No transition here, handleDescent will manage the transition to LAND
                    break;
                case LAND:
                    // No transition here, handleLanding will manage the transition to STAND
                    break;
//                case STAND:
//                case WALK:
//                    if (groundPhaseTicks >= GROUND_PHASE_DURATION) {
//                        newPhase = Phase.TAKEOFF;
//                        this.takeoffTicks = 0;
//                        groundPhaseTicks = 0;
//                        debugLog("Ground phase complete. Taking off.");
//                    } else {
//                        newPhase = this.random.nextBoolean() ? Phase.STAND : Phase.WALK;
//                        debugLog("Continuing ground movement. Ground phase ticks: " + groundPhaseTicks);
//                    }
//                    break;
                case TAKEOFF:
                    if (this.takeoffTicks >= TAKE_OFF_DURATION) {
                        newPhase = Phase.FLY_IDLE;
                        debugLog("Takeoff complete. Entering FLY_IDLE.");
                    }
                    break;
                case TRANSITION_TO_FLY:
                    if (this.transitionTicks >= TRANSITION_DURATION) {
                        newPhase = Phase.FLY_IDLE;
                        debugLog("Transition complete. Entering FLY_IDLE.");
                    }
                    break;
//                case DESCEND:
//                    if (this.isNearGround()) {
//                        newPhase = Phase.LAND;
//                    } else {
//                        newPhase = Phase.FLY_IDLE;
//                    }
//                    break;
//                case LAND:
//                    if (this.isOnGround()) {
//                        newPhase = Phase.STAND;
//                        this.currentPhaseDuration = this.random.nextInt(MAX_GROUND_DURATION - MIN_GROUND_DURATION + 1) + MIN_GROUND_DURATION;
//                    } else {
//                        newPhase = Phase.DESCEND;
//                    }
//                    break;
            }
        }

        if (newPhase != currentPhase) {
            debugLog("Transitioning from " + currentPhase + " to " + newPhase);
            setPhase(newPhase);
        }
    }

    private int getNewPhaseDuration(Phase phase) {
        switch (phase) {
            case FLY_IDLE:
                return this.random.nextInt(MAX_FLY_DURATION - MIN_FLY_DURATION + 1) + MIN_FLY_DURATION;
            case STAND:
            case WALK:
                return this.random.nextInt(MAX_GROUND_DURATION - MIN_GROUND_DURATION + 1) + MIN_GROUND_DURATION;
            default:
                return 100; // Default duration for other phases
        }
    }

    private boolean isNearGround() {
        double groundHeight = this.getGroundHeight(this.blockPosition());
        return this.getY() - groundHeight < LANDING_THRESHOLD;
    }

    private boolean isOnGround() {
        return this.onGround() || this.getY() - this.getGroundHeight(this.blockPosition()) < 0.5;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isLanding || this.getPhase() == Phase.LAND) {
            return false;
        }
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

    static class SkyrimDragonWanderGoal extends Goal {
        private final SkyrimDragon dragon;

        public SkyrimDragonWanderGoal(SkyrimDragon dragon) {
            this.dragon = dragon;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return dragon.random.nextInt(100) == 0;
        }

        @Override
        public void start() {
            if (dragon.isFlying()) {
                Vec3 randomPos = findRandomPosition();
                if (randomPos != null) {
                    dragon.moveTarget = randomPos;
                }
            } else {
                Vec3 randomPos = dragon.position().add(
                        dragon.random.nextInt(20) - 10,
                        0,
                        dragon.random.nextInt(20) - 10
                );
                dragon.getNavigation().moveTo(randomPos.x, randomPos.y, randomPos.z, 1.0);
                dragon.setPhase(Phase.WALK);
            }
        }

        private Vec3 findRandomPosition() {
            Vec3 viewVector = dragon.getViewVector(1.0F);
            Vec3 randomDir = new Vec3(dragon.random.nextDouble() - 0.5, (dragon.random.nextDouble() - 0.5) * 0.25, dragon.random.nextDouble() - 0.5).normalize();
            return dragon.position().add(viewVector.add(randomDir).scale(16));
        }
    }

    static class SkyrimDragonMoveControl extends MoveControl {
        private SkyrimDragon dragon;
        private static final double MAX_ROTATION_SPEED = 3.0;

        public SkyrimDragonMoveControl(SkyrimDragon dragon) {
            super(dragon);
            this.dragon = dragon;
        }

        @Override
        public void tick() {
            if (this.dragon.isFlying()) {
                if (this.operation == MoveControl.Operation.MOVE_TO) {
                    Vec3 movement = new Vec3(this.wantedX - dragon.getX(), this.wantedY - dragon.getY(), this.wantedZ - dragon.getZ());
                    double distance = movement.length();

                    if (distance < 0.1) {
                        this.operation = MoveControl.Operation.WAIT;
                    } else {
                        // Smooth movement with some randomness
                        Vec3 motion = movement.normalize().scale(0.05);
                        motion = motion.add(
                                dragon.random.nextGaussian() * 0.01,
                                dragon.random.nextGaussian() * 0.01,
                                dragon.random.nextGaussian() * 0.01
                        );
                        dragon.setDeltaMovement(dragon.getDeltaMovement().add(motion).scale(0.95));

                        // Smooth rotation
                        double targetYaw = -Math.atan2(motion.x, motion.z) * (180F / Math.PI);
                        double yawDifference = angleDelta((float)targetYaw - dragon.getYRot());
                        double yawChange = Mth.clamp(yawDifference, -MAX_ROTATION_SPEED, MAX_ROTATION_SPEED);
                        dragon.setYRot((float) (dragon.getYRot() + yawChange));
                        dragon.yBodyRot = dragon.getYRot();
                    }
                }
            } else {
                // Improved ground movement
                if (this.operation == MoveControl.Operation.MOVE_TO) {
                    this.operation = MoveControl.Operation.WAIT;
                    double dx = this.wantedX - dragon.getX();
                    double dy = this.wantedY - dragon.getY();
                    double dz = this.wantedZ - dragon.getZ();
                    double distanceSq = dx * dx + dy * dy + dz * dz;

                    if (distanceSq < 2.5000003E-7) {
                        this.mob.setZza(0.0F);
                        return;
                    }

                    float targetYaw = (float)(Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
                    this.dragon.setYRot(this.rotlerp(this.dragon.getYRot(), targetYaw, 30.0F));

                    float speed = (float)(this.speedModifier * dragon.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    dragon.setSpeed(speed);

                    BlockPos currentPos = dragon.blockPosition();
                    BlockPos targetPos = new BlockPos((int)this.wantedX, (int)this.wantedY, (int)this.wantedZ);

                    // Check if there's a clear path
                    if (!this.isDirectPathBetweenPoints(currentPos, targetPos)) {
                        // If not, try to move up one block
                        targetPos = targetPos.above();
                    }

                    dragon.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), speed);
                    dragon.setPhase(Phase.WALK);
                } else {
                    dragon.setSpeed(0.0F);
                    dragon.setPhase(Phase.STAND);
                }
            }
        }

        private boolean isDirectPathBetweenPoints(BlockPos start, BlockPos end) {
            Vec3 startVec = Vec3.atCenterOf(start);
            Vec3 endVec = Vec3.atCenterOf(end);
            Vec3 direction = endVec.subtract(startVec).normalize();
            double distance = startVec.distanceTo(endVec);

            for (double d = 0; d <= distance; d += 1.0) {
                Vec3 pos = startVec.add(direction.scale(d));
                BlockPos blockPos = new BlockPos((int)pos.x, (int)pos.y, (int)pos.z);
                if (!dragon.level().getBlockState(blockPos).isAir() && !dragon.level().getBlockState(blockPos.above()).isAir()) {
                    return false;
                }
            }
            return true;
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