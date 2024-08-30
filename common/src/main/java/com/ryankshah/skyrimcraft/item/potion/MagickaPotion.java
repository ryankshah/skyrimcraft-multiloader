package com.ryankshah.skyrimcraft.item.potion;

import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.item.SkyrimPotion;
import com.ryankshah.skyrimcraft.network.spell.ReplenishMagicka;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import commonnetwork.api.Dispatcher;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

;

public class MagickaPotion extends SkyrimPotion
{
    private float replenishValue;

    public MagickaPotion(Properties properties, float replenishValue) {
        super(properties);
        this.replenishValue = replenishValue;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        Player playerEntity = entityLiving instanceof Player ? (Player) entityLiving : null;

        if(!worldIn.isClientSide) {
            ServerPlayer player = (ServerPlayer) playerEntity;
            Character character = Character.get(player);
            if(character.getMagicka() != character.getMaxMagicka()) {
                final ReplenishMagicka replenishMagicka = new ReplenishMagicka((int) replenishValue);
                Dispatcher.sendToServer(replenishMagicka);
//                PacketDistributor.SERVER.noArg().send(replenishMagicka);
            }
        }

        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        if (this == ItemRegistry.MINOR_MAGICKA_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.GRASS_POD.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(BlockRegistry.RED_MOUNTAIN_FLOWER.get(), 1)));
        } else if(this == ItemRegistry.MAGICKA_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.GRASS_POD.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.BRIAR_HEART.get(), 1)));
        } else if (this == ItemRegistry.PLENTIFUL_MAGICKA_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.VAMPIRE_DUST.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.BRIAR_HEART.get(), 1)));
        } else if (this == ItemRegistry.VIGOROUS_MAGICKA_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(BlockRegistry.CREEP_CLUSTER.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(BlockRegistry.RED_MOUNTAIN_FLOWER.get(), 1)));
        } else if (this == ItemRegistry.EXTREME_MAGICKA_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(BlockRegistry.CREEP_CLUSTER.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.MORA_TAPINELLA.get(), 1)));
        } else if (this == ItemRegistry.ULTIMATE_MAGICKA_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.MORA_TAPINELLA.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(BlockRegistry.RED_MOUNTAIN_FLOWER.get(), 1)));
        }
        return ingredients;
    }

    @Override
    public PotionCategory getCategory() {
        return PotionCategory.RESTORE_MAGICKA;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal((int)replenishValue == 20 ? "Completely replenishes your magicka" : "Replenishes " + (int)replenishValue + " magicka"));
        super.appendHoverText(pStack, pContext, pTooltip, pFlag);
    }
}