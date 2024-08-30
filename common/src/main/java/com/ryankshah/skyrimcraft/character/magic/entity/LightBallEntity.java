package com.ryankshah.skyrimcraft.character.magic.entity;

import com.ryankshah.skyrimcraft.goal.ConjuredFollowOwnerGoal;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class LightBallEntity extends PathfinderMob implements OwnableEntity
{
    protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(
            LightBallEntity.class, EntityDataSerializers.OPTIONAL_UUID
    );

    private LivingEntity owner;
    private float lifeTime = 1200f; // 60 seconds

    public LightBallEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);


//        this.owner = pLevel.getPlayerByUUID(this.getEntityData().get(DATA_OWNERUUID_ID).get());

//        this.moveTo(owner.getX(), owner.getY(), owner.getZ(), owner.getYRot(), owner.getXRot());
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.setPos(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(Vec3.ZERO);

        this.getNavigation().setCanFloat(true);
        this.setInvulnerable(true);
    }

    public LightBallEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel, LivingEntity owner) {
        this(pEntityType, pLevel);
        this.owner = owner;
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    public LightBallEntity(Level worldIn, LivingEntity shooter) {
        super(EntityRegistry.LIGHTBALL_ENTITY.get(), worldIn);
        this.owner = shooter;
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.getEntityData().set(DATA_OWNERUUID_ID, Optional.of(owner.getUUID()));
//        this.setPos(shooter.getForward().x, shooter.getForward().y, shooter.getForward().z);
//        this.moveTo(shooter.getX(), shooter.getY(), shooter.getZ(), shooter.getYRot(), shooter.getXRot());
        this.setPos(this.getX(), this.getY(), this.getZ());
//        this.setDeltaMovement(Vec3.ZERO);
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
    }

    @Override
    protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState p_184231_4_, BlockPos p_184231_5_) {
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new WaterAvoidingRandomFlyingGoal(this, 0.5f));
        this.goalSelector.addGoal(5, new ConjuredFollowOwnerGoal(this, owner, 2.0D, 10.0f, 2.0f, true));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.1D).add(Attributes.FLYING_SPEED, 0.3f).add(Attributes.FOLLOW_RANGE, 12.0D);
    }

    @Override
    public void tick() {
        super.tick();
        lifeTime++;
        if(lifeTime >= 1200) {
            kill();
        }
//        Iterator<BlockPos> lightPositions = BlockPos.withinManhattan(blockPosition(), 4, 4, 4).iterator();
//        lightPositions.forEachRemaining(
//                blockPos -> level().getLightEngine().
//        );
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_OWNERUUID_ID, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.getOwnerUUID() != null) {
            pCompound.putUUID("Owner", this.getOwnerUUID());
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        UUID uuid;
        if (pCompound.hasUUID("Owner")) {
            uuid = pCompound.getUUID("Owner");
        } else {
            String s = pCompound.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
            try {
                this.setOwnerUUID(uuid);
            } catch (Throwable ignored) {
            }
        }
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNERUUID_ID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID pUuid) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(pUuid));
    }
}
