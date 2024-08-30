package com.ryankshah.skyrimcraft.entity.creature;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class Giant extends PathfinderMob implements GeoEntity
{
    private static final EntityDataAccessor<Integer> PREV_ANIMATION_STATE = SynchedEntityData.defineId(Giant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(Giant.class, EntityDataSerializers.INT);

    private MeleeAttackGoal meleeGoal;
    private WaterAvoidingRandomStrollGoal walkingGoal;
    private NearestAttackableTargetGoal<? extends LivingEntity> sprintToNearestPlayerGoal;
    private NearestAttackableTargetGoal<? extends LivingEntity> sprintToNearestAnimalGoal;

    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.giant.idle");
    protected static final RawAnimation IDLE_AGGRESSIVE = RawAnimation.begin().thenLoop("animation.giant.idle.aggresive");
    protected static final RawAnimation LIFT_CLUB = RawAnimation.begin().thenLoop("animation.giant.lift_club");
    protected static final RawAnimation LOWER_CLUB = RawAnimation.begin().thenLoop("animation.giant.lower_club");
    protected static final RawAnimation WALK_CLUB = RawAnimation.begin().thenLoop("animation.giant.walk_club");
    protected static final RawAnimation GUARD = RawAnimation.begin().thenLoop("animation.giant.guard");
    protected static final RawAnimation RUN = RawAnimation.begin().thenLoop("animation.giant.run");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public Giant(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.noCulling = true;
        this.xpReward = 5;
//        this.setMaxUpStep(1.25f); // 1.5 works.. but does 1.25f? if so then this comment may still be here xox

        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
//        this.setBiomeType(Biomes.PLAINS);
    }

    protected void registerGoals() {
        meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
            @Override
            public void stop() {
                super.stop();
                Giant.this.setAnimationState(0);
            }

            @Override
            protected void checkAndPerformAttack(LivingEntity pTarget) {
                if (this.canPerformAttack(pTarget)) {
                    if (getTicksUntilNextAttack() <= 0) {
                        this.resetAttackCooldown();
                        this.mob.swing(InteractionHand.MAIN_HAND);
                        if (Giant.this.getAnimationState() != 3)
                            Giant.this.setAnimationState(3);
                        this.mob.doHurtTarget(pTarget);
                    }
                }
            }
        };

        walkingGoal = new WaterAvoidingRandomStrollGoal(this, 1.0D) {
            @Override
            public void stop() {
                super.stop();
                Giant.this.setAnimationState(Giant.this.getPrevAnimationState());
            }

            @Override
            public void tick() {
                super.tick();
                if(Giant.this.getAnimationState() != 1)
                    Giant.this.setAnimationState(3);
            }
        };

        sprintToNearestPlayerGoal = new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public void stop() {
                super.stop();
                Giant.this.setAnimationState(Giant.this.getPrevAnimationState());
            }

            @Override
            public void tick() {
                super.tick();
                if(Giant.this.getAnimationState() != 2)
                    Giant.this.setAnimationState(2);
            }
        };

        this.goalSelector.addGoal(1, new FloatGoal(this));
        //this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, meleeGoal); //new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, walkingGoal);
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
//        this.targetSelector.addGoal(2, new SprintToNearestAttackableTargetGoal<>(this, Player.class, true));
//        this.targetSelector.addGoal(2, new SprintToNearestAttackableTargetGoal<>(this, Animal.class, true));
    }

    @Nullable
    @Override
    public Component getCustomName() {
//        if(SNOWY_BIOMES.contains(this.getBiomeType()))
//            return Component.translatable("entity.skyrimcraft.snowy_sabre_cat");
        return super.getCustomName();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.JUMP_STRENGTH, 0.5D).add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.ATTACK_DAMAGE, 10.0D).add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    @Override
    protected float getBlockJumpFactor() {
        return super.getBlockJumpFactor();
    }

    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return pLevel.getBlockState(pPos.below()).is(Blocks.GRASS_BLOCK) ? 10.0F : pLevel.getBrightness(LightLayer.BLOCK, pPos) - 0.5F;
    }

    public boolean shouldDropExperience() {
        return true;
    }

    protected boolean shouldDropLoot() {
        return true;
    }

    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(ANIMATION_STATE, 0);
        pBuilder.define(PREV_ANIMATION_STATE, 0);
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

    public void setAnimationState(int animationState) {
        setPrevAnimationState(this.getAnimationState());
        this.entityData.set(PREV_ANIMATION_STATE, this.getAnimationState());
        this.entityData.set(ANIMATION_STATE, animationState);
    }
    public int getAnimationState() {
        return this.entityData.get(ANIMATION_STATE);
    }

    public void setPrevAnimationState(int prevAnimationState) {
        this.entityData.set(PREV_ANIMATION_STATE, prevAnimationState);
    }
    public int getPrevAnimationState() { return this.entityData.get(PREV_ANIMATION_STATE); }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
        this.setAnimationState(0);
        this.setPrevAnimationState(0);
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    private <E extends Giant> PlayState giantController(final software.bernie.geckolib.animation.AnimationState<Giant> event) {
        AnimationController<Giant> controller = event.getController();
        controller.transitionLength(0);

        if (this.getAnimationState() == 0) {
            return event.setAndContinue(IDLE);
        } else if (this.getAnimationState() == 1) {
            return event.setAndContinue(LIFT_CLUB);
        } else if (this.getAnimationState() == 2) {
            return event.setAndContinue(LOWER_CLUB);
        } else if (this.getAnimationState() == 3 && event.isMoving()) {
            return event.setAndContinue(WALK_CLUB);
        } else if (this.getAnimationState() == 4) {
            return event.setAndContinue(GUARD);
        } else if (this.getAnimationState() == 5 && event.isMoving()) {
            return event.setAndContinue(RUN);
        } else if (this.getAnimationState() == 6) {
            return event.setAndContinue(IDLE_AGGRESSIVE);
        } else {
            return event.setAndContinue(IDLE);
        }
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "giant_controller", 0, this::giantController));
    }
}