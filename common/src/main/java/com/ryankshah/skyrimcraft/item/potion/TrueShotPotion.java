package com.ryankshah.skyrimcraft.item.potion;

import com.ryankshah.skyrimcraft.item.SkyrimPotion;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
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

public class TrueShotPotion extends SkyrimPotion
{
    private int amplifier;
    private int duration;

    public TrueShotPotion(Properties properties, int amplifier, int duration) {
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
                // TODO: Make effect to increase bow damage by %
//                serverPlayerEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, duration, amplifier, true, true, true));
            }
        }

        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        if (this == ItemRegistry.POTION_OF_TRUE_SHOT.get()) {
            ingredients.add(Ingredient.of(new ItemStack(BlockRegistry.CANIS_ROOT.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.ELVES_EAR.get(), 1)));
        } else if(this == ItemRegistry.DRAUGHT_OF_TRUE_SHOT.get()) {
            ingredients.add(Ingredient.of(new ItemStack(BlockRegistry.CANIS_ROOT.get(), 2)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.JUNIPER_BERRIES.get(), 1)));
        } else if (this == ItemRegistry.PHILTER_OF_TRUE_SHOT.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.JUNIPER_BERRIES.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.SPIDER_EGG.get(), 2)));
        } else if (this == ItemRegistry.ELIXIR_OF_TRUE_SHOT.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.SPIDER_EGG.get(), 2)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.ELVES_EAR.get(), 2)));
        }
        return ingredients;
    }

    @Override
    public PotionCategory getCategory() {
        return PotionCategory.FORTIFY_MARKSMAN;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext pContext, List<Component> tooltip, TooltipFlag flagIn) {
        String value = "";

        Item item = stack.getItem();
        if (ItemRegistry.POTION_OF_TRUE_SHOT.get().equals(item)) {
            value = "10%";
        } else if (ItemRegistry.DRAUGHT_OF_TRUE_SHOT.get().equals(item)) {
            value = "15%";
        } else if (ItemRegistry.PHILTER_OF_TRUE_SHOT.get().equals(item)) {
            value = "20%";
        } else if (ItemRegistry.ELIXIR_OF_TRUE_SHOT.get().equals(item)) {
            value = "25%";
        }

        tooltip.add(Component.literal("Bows do " + value + " more damage for " + duration + " seconds"));
        super.appendHoverText(stack, pContext, tooltip, flagIn);
    }
}