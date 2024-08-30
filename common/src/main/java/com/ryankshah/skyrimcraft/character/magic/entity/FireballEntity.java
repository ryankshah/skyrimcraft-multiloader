package com.ryankshah.skyrimcraft.character.magic.entity;

import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FireballEntity extends AbstractHurtingProjectile
{

    private LivingEntity shootingEntity;
    private int ticksAlive;
    private int ticksInAir;
    private double accelerationX;
    private double accelerationY;
    private double accelerationZ;

    public FireballEntity(Level worldIn) {
        super(EntityRegistry.SPELL_FIREBALL_ENTITY.get(), worldIn);
    }

    public FireballEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(EntityRegistry.SPELL_FIREBALL_ENTITY.get(), worldIn);
        this.shootingEntity = shooter;
        this.moveTo(shooter.getX(), shooter.getY(), shooter.getZ(), shooter.getYRot(), shooter.getXRot());
        this.setPos(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(Vec3.ZERO);

        double u = Mth.sqrt((float)(accelX * accelX + accelY * accelY + accelZ * accelZ));

        this.accelerationX = accelX / u * 0.1D;
        this.accelerationY = accelY / u * 0.1D;
        this.accelerationZ = accelZ / u * 0.1D;
    }

    public FireballEntity(EntityType<? extends AbstractHurtingProjectile> entityEntityType, Level world) {
        super(entityEntityType, world);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double u = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(u)) {
            u = 4.0D;
        }

        u = u * 64.0D;
        return distance < u * u;
    }

    @Override
    public void tick() {
        if (this.level().isClientSide || (this.shootingEntity == null || !this.shootingEntity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
            super.tick();
            ++this.ticksInAir;

            HitResult raytraceresult = ProjectileUtil.getHitResultOnMoveVector(this, entity -> entity.isAlive() && entity != this.shootingEntity);
            if (raytraceresult.getType() != HitResult.Type.MISS) {
                this.onImpact(raytraceresult);
            }

            Vec3 vec3d = this.getDeltaMovement();
            this.setPos(getX() + vec3d.x, getY() + vec3d.y, getZ() + vec3d.z);
            ProjectileUtil.rotateTowardsMovement(this, 0.2f);
            //ProjectileHelper.rotateTowardsMovement(this, 0.2f);

            float f = this.getMotionFactor();

            float radius = 2f;
            // Get origins
            Vec3 origin = new Vec3(0, 0, 0);
            Vec3 facing = getForward();

            for(int i = 0; i < 6; i++) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX() - vec3d.x * 0.25D, this.getY() - vec3d.y * 0.25D, this.getZ() - vec3d.z * 0.25D, vec3d.x, vec3d.y, vec3d.z);
            }

            this.setDeltaMovement(vec3d.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale(f));
            this.setPos(this.getX(), this.getY(), this.getZ());
        } else {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    protected float getMotionFactor() {
        return 1f;
    }


    public void onImpact(HitResult result) {
        if (!this.level().isClientSide) {
            if (result.getType() == HitResult.Type.ENTITY) {
                Entity entity = ((EntityHitResult)result).getEntity();
                if(entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity)entity;
                    ServerLevel world = (ServerLevel) entity.level();
                    livingEntity.hurt(this.damageSources().magic(), 5.5f);
                    livingEntity.setRemainingFireTicks(4*20);
//                    livingEntity.setSecondsOnFire(4); // 4 seconds too long/short?
                    //igniteBlocks(4); // needed ?
                }
            } else if (result.getType() == HitResult.Type.BLOCK) {
                this.level().playSound(null, new BlockPos((int)result.getLocation().x, (int)result.getLocation().y, (int)result.getLocation().z), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 1.0F, 1.0F);
            }
            this.remove(RemovalReason.DISCARDED);
        }
    }



    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        Vec3 vec3d = this.getDeltaMovement();
        compound.put("direction", this.newDoubleList(vec3d.x, vec3d.y, vec3d.z));
        compound.put("power", this.newDoubleList(this.accelerationX, this.accelerationY, this.accelerationZ));
        compound.putInt("life", this.ticksAlive);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("power", 9)) {
            ListTag listnbt = compound.getList("power", 6);
            if (listnbt.size() == 3) {
                this.accelerationX = listnbt.getDouble(0);
                this.accelerationY = listnbt.getDouble(1);
                this.accelerationZ = listnbt.getDouble(2);
            }
        }

        this.ticksAlive = compound.getInt("life");
        if (compound.contains("direction", 9) && compound.getList("direction", 6).size() == 3) {
            ListTag listnbt1 = compound.getList("direction", 6);
            this.setDeltaMovement(listnbt1.getDouble(0), listnbt1.getDouble(1), listnbt1.getDouble(2));
        } else {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 1.0F;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.markHurt();
            if (source.getEntity() != null) {
                Vec3 vec3d = source.getEntity().getLookAngle();
                this.setDeltaMovement(vec3d);
                this.accelerationX = vec3d.x * 0.1D;
                this.accelerationY = vec3d.y * 0.1D;
                this.accelerationZ = vec3d.z * 0.1D;
                if (source.getEntity() instanceof LivingEntity) {
                    this.shootingEntity = (LivingEntity)source.getEntity();
                }

                return true;
            } else {
                return false;
            }
        }
    }

    private void igniteBlocks(int extraIgnitions) {
        if (!this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            BlockPos blockpos = this.blockPosition();
            BlockState blockstate = BaseFireBlock.getState(this.level(), blockpos);
            if (this.level().getBlockState(blockpos).isAir() && blockstate.canSurvive(this.level(), blockpos)) {
                this.level().setBlockAndUpdate(blockpos, blockstate);
            }

            for(int i = 0; i < extraIgnitions; ++i) {
                BlockPos blockpos1 = blockpos.offset(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
                blockstate = BaseFireBlock.getState(this.level(), blockpos1);
                if (this.level().getBlockState(blockpos1).isAir() && blockstate.canSurvive(this.level(), blockpos1)) {
                    this.level().setBlockAndUpdate(blockpos1, blockstate);
                }
            }

        }
    }

    private Vec3 rotate(Vec3 origin, Vec3 facing, double angle) {
        double c = (origin.x * facing.x) + (origin.y * facing.y) + (origin.z * facing.z); // constant
        double vx = origin.x * c * (1d - Mth.cos((float)angle)) + facing.x * Mth.cos((float)angle) + (-origin.z * facing.y + origin.y*facing.z) * Mth.sin((float)angle);
        double vy = origin.y * c * (1d - Mth.cos((float)angle)) + facing.y * Mth.cos((float)angle) + (origin.z * facing.x - origin.x*facing.z) * Mth.sin((float)angle);
        double vz = origin.z * c * (1d - Mth.cos((float)angle)) + facing.z * Mth.cos((float)angle) + (-origin.y * facing.x + origin.x*facing.y) * Mth.sin((float)angle);

        return new Vec3(vx, vy, vz);
    }
}