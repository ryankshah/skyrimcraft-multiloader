package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class EffectWaterwalking extends MobEffect
{
    private final Vec3 vec = new Vec3(1.02d, 0d, 1.02d);

    protected EffectWaterwalking(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int p_76394_2_) {
        // we use the mixin from LivingEntityMixin now
//        if (!entity.level().getBlockState(entity.blockPosition()).getFluidState().isEmpty()) {
//            Vec3 movement = entity.getDeltaMovement();
//
//            if (movement.y < 0.0D) {
//                entity.setDeltaMovement(movement.x, 0.01D, movement.z);
//            }
//            entity.setOnGround(true);
//        }
//
//        if (entity instanceof Player player) {
//            float f;
//
//            if (entity.onGround() && !entity.isDeadOrDying() && !entity.isSwimming()) {
//                f = Math.min(0.1F, (float) entity.getDeltaMovement().horizontalDistance());
//            } else {
//                f = 0.0F;
//            }
//            player.bob += (f - player.bob) * 0.4F;
//        }


        return super.applyEffectTick(entity, p_76394_2_);
    }
}
