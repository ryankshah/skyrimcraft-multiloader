package com.ryankshah.skyrimcraft.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.registry.AttributeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity
{
    @Shadow public abstract boolean hasEffect(Holder<MobEffect> pEffect);

    @Shadow public abstract void travel(Vec3 pTravelVector);

    private LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @ModifyReturnValue(method = "canStandOnFluid", at = @At("RETURN"))
    public boolean standOnFluidIfWaterWalking(boolean original) {
        if(hasEffect(ModEffects.WATER_WALKING.asHolder())
                && getBlockStateOn().getFluidState().is(Fluids.WATER)) {
            return true;
        }
        return original;
    }

    @Inject(method = "jumpInLiquid", at = @At("HEAD"), cancellable = true)
    public void canJumpInLiquidIfParalysed(CallbackInfo ci) {
        if (this.hasEffect(ModEffects.PARALYSIS.asHolder())) ci.cancel();
    }

    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;travel(Lnet/minecraft/world/phys/Vec3;)V"))
    public void canTravelIfParalysed(LivingEntity instance, Vec3 v3) {
        if(hasEffect(ModEffects.PARALYSIS.asHolder())) {
            travel(Vec3.ZERO);
            return;
        }
        travel(v3);
    }

    @ModifyReturnValue(method = "createLivingAttributes", at = @At("TAIL"))
    private static AttributeSupplier.Builder createLivingAttributes(AttributeSupplier.Builder original) {
        return original.add(AttributeRegistry.MAX_MAGICKA.asHolder())
            .add(AttributeRegistry.MAGICKA_REGEN.asHolder())
            .add(AttributeRegistry.MAX_STAMINA.asHolder())
            .add(AttributeRegistry.POISON_RESIST.asHolder())
            .add(AttributeRegistry.SHOCK_RESIST.asHolder())
            .add(AttributeRegistry.FIRE_RESIST.asHolder())
            .add(AttributeRegistry.FROST_RESIST.asHolder())
            .add(AttributeRegistry.POISON_POWER.asHolder())
            .add(AttributeRegistry.SHOCK_POWER.asHolder())
            .add(AttributeRegistry.FIRE_POWER.asHolder())
            .add(AttributeRegistry.FROST_POWER.asHolder());
    }
}