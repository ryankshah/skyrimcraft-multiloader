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
        // TODO: Add the Hagraven's Claw and Tundra Cotton ingredients
        NonNullList<Ingredient> ingredients = NonNullList.create();
        if (this == ItemRegistry.POTION_OF_HAGGLING.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.BUTTERFLY_WING.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.DRAGONS_TONGUE.get(), 1)));
        } else if(this == ItemRegistry.DRAUGHT_OF_HAGGLING.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.BUTTERFLY_WING.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.DRAGONS_TONGUE.get(), 1)));
        } else if (this == ItemRegistry.PHILTER_OF_HAGGLING.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.BUTTERFLY_WING.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.DRAGONS_TONGUE.get(), 1)));
        } else if (this == ItemRegistry.ELIXIR_OF_HAGGLING.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.BUTTERFLY_WING.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.DRAGONS_TONGUE.get(), 1)));
        }
        return ingredients;
    }

    @Override
    public PotionCategory getCategory() {
        return PotionCategory.FORTIFY_BARTER;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext pContext, List<Component> tooltip, TooltipFlag flagIn) {
        String value = "";

        Item item = stack.getItem();
        if (ItemRegistry.POTION_OF_HAGGLING.get().equals(item)) {
            value = "10%";
        } else if (ItemRegistry.DRAUGHT_OF_HAGGLING.get().equals(item)) {
            value = "15%";
        } else if (ItemRegistry.PHILTER_OF_HAGGLING.get().equals(item)) {
            value = "20%";
        } else if (ItemRegistry.ELIXIR_OF_HAGGLING.get().equals(item)) {
            value = "25%";
        }

        tooltip.add(Component.literal("You haggle for " + value + " better prices for " + duration + " seconds"));
        super.appendHoverText(stack, pContext, tooltip, flagIn);
    }
}