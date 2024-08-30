package com.ryankshah.skyrimcraft.item.potion;

import com.ryankshah.skyrimcraft.item.SkyrimPotion;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class SkyrimAlcohol extends SkyrimPotion
{
    public SkyrimAlcohol(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        Player playerEntity = entityLiving instanceof Player ? (Player) entityLiving : null;

        if (!worldIn.isClientSide) {
            if(playerEntity instanceof ServerPlayer) {
                ServerPlayer serverPlayerEntity = (ServerPlayer) playerEntity;
                serverPlayerEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, worldIn.random.nextIntBetweenInclusive(4 * 20, 10 * 20), worldIn.random.nextIntBetweenInclusive(2, 20), true, true, true));
                serverPlayerEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, worldIn.random.nextIntBetweenInclusive(6 * 20, 20 * 20), worldIn.random.nextIntBetweenInclusive(2, 20), true, true, true));
            }
        }

        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        return ingredients;
    }

    @Override
    public PotionCategory getCategory() {
        return PotionCategory.DRUGS;
    }
}