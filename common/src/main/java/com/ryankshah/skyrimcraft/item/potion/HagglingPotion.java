package com.ryankshah.skyrimcraft.item.potion;

import com.ryankshah.skyrimcraft.item.SkyrimPotion;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class HagglingPotion extends SkyrimPotion
{
    private int amplifier;
    private int duration;

    public HagglingPotion(Properties properties, int amplifier, int duration) {
        super(properties);
        this.amplifier = amplifier;
        this.duration = duration;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        Player playerEntity = entityLiving instanceof Player ? (Player) entityLiving : null;

        if (!worldIn.isClientSide) {
            if(playerEntity instanceof ServerPlayer) {
                ServerPlayer serverPlayerEntity = (ServerPlayer) playerEntity;
                serverPlayerEntity.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, duration, amplifier, true, true, true));
            }
        }

        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        if (this == ItemRegistry.LASTING_POTENCY_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.SALMON_ROE.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.GARLIC.get(), 1)));
        } else if(this == ItemRegistry.DRAUGHT_LASTING_POTENCY_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.GARLIC.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.SALT_PILE.get(), 1)));
        } else if (this == ItemRegistry.SOLUTION_LASTING_POTENCY_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_OIL.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.SALMON_ROE.get(), 1)));
        } else if (this == ItemRegistry.PHILTER_LASTING_POTENCY_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.SALT_PILE.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_OIL.get(), 1)));
        } else if (this == ItemRegistry.ELIXIR_LASTING_POTENCY_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_OIL.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.FIRE_SALTS.get(), 1)));
        }
        return ingredients;
    }

    @Override
    public PotionCategory getCategory() {
        return PotionCategory.REGENERATE_HEALTH;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext pContext, List<Component> tooltip, TooltipFlag flagIn) {
        String regenValue = "";

        Item item = stack.getItem();
        if (ItemRegistry.LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "50%";
        } else if (ItemRegistry.DRAUGHT_LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "60%";
        } else if (ItemRegistry.SOLUTION_LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "70%";
        } else if (ItemRegistry.PHILTER_LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "80%";
        } else if (ItemRegistry.ELIXIR_LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "100%";
        }

        tooltip.add(Component.literal("Grants " + duration/20 + "s of " + regenValue + " magicka regen"));
        super.appendHoverText(stack, pContext, tooltip, flagIn);
    }
}