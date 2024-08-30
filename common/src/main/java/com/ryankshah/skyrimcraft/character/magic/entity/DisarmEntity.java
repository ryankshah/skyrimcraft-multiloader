package com.ryankshah.skyrimcraft.character.magic.entity;

import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class DisarmEntity extends AbstractHurtingProjectile
{
    private LivingEntity shootingEntity;
    private int ticksAlive;
    private int ticksInAir;
    private double accelerationX;
    private double accelerationY;
    private double accelerationZ;
    private Vec3 startingPosition;

    public DisarmEntity(Level worldIn) {
        super(EntityRegistry.SHOUT_DISARM_ENTITY.get(), worldIn);
    }

    public DisarmEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(EntityRegistry.SHOUT_DISARM_ENTITY.get(), worldIn);
        this.shootingEntity = shooter;
        this.setPos(shooter.getForward().x, shooter.getForward().y, shooter.getForward().z);
        this.moveTo(shooter.getX(), shooter.getY(), shooter.getZ(), shooter.getYRot(), shooter.getXRot());
        this.setPos(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(Vec3.ZERO);

        startingPosition = new Vec3(getX(), getY(), getZ());

        double u = Mth.sqrt((float)(accelX * accelX + accelY * accelY + accelZ * accelZ));

        this.accelerationX = accelX / u * 0.1D;
        this.accelerationY = accelY / u * 0.1D;
        this.accelerationZ = accelZ / u * 0.1D;
    }

    public DisarmEntity(EntityType<? extends AbstractHurtingProjectile> entityEntityType, Level world) {
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
        if(!this.level().isClientSide)
            if(startingPosition.distanceToSqr(getX(), getY(), getZ()) >= 64D)
                this.remove(RemovalReason.DISCARDED);

        if (this.level().isClientSide || (this.shootingEntity == null || !this.shootingEntity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
            super.tick();
            ++this.ticksInAir;

            HitResult raytraceresult = ProjectileUtil.getHitResultOnMoveVector(this, entity -> entity.isAlive() && entity != this.shootingEntity);
            if (raytraceresult.getType() != HitResult.Type.MISS) {
                this.onImpact(raytraceresult);
            }

            Vec3 vec3d = this.getDeltaMovement();
            this.setPos(getX() + vec3d.x, getY() + vec3d.y, getZ() + vec3d.z);

            float f = this.getMotionFactor();

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
                Entity entity = ((EntityHitResult) result).getEntity();
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    ItemStack mainHand = livingEntity.getMainHandItem();

                    // Check its not vanishing curse and there is indeed something in the entity's main hand
                    if (!livingEntity.getMainHandItem().isEmpty()) { // && !EnchantmentHelper.hasVanishingCurse(mainHand)) {
                        ItemEntity mainHandItemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), mainHand);
                        this.level().addFreshEntity(mainHandItemEntity);
                        livingEntity.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    }

                    // Stagger entity
                    if (!entity.isInWater() || !entity.isInLava())
                        ((LivingEntity) entity).knockback(1F, (double) Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), (double) (-Mth.cos(this.getYRot() * ((float) Math.PI / 180F))));
                }
//                if(!entity.isInWater() || !entity.isInLava())
//                    ((LivingEntity)entity).knockback(2F, (double)MathHelper.sin(this.yRot * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(this.yRot * ((float)Math.PI / 180F))));
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
}