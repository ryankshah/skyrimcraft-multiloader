package com.ryankshah.skyrimcraft.network.recipe;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.data.recipe.AlchemyRecipe;
import com.ryankshah.skyrimcraft.network.skill.AddXpToSkill;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public record FinishAlchemyRecipe(Recipe<?> recipe)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "finishalchemyrecipe");

    public static final StreamCodec<RegistryFriendlyByteBuf, FinishAlchemyRecipe> CODEC = StreamCodec.composite(
            Recipe.STREAM_CODEC,
            FinishAlchemyRecipe::recipe,
            FinishAlchemyRecipe::new
    );

    public FinishAlchemyRecipe(final FriendlyByteBuf buffer) {
        this(buffer.readJsonWithCodec(AlchemyRecipe.CODEC));
    }

    public static void handle(PacketContext<FinishAlchemyRecipe> context) {
        ServerPlayer player = context.sender();
        Character character = Character.get(player);
        if(context.message().recipe instanceof AlchemyRecipe currentRecipeObject) {
            List<Ingredient> recipe = currentRecipeObject.getRecipeItems();
            boolean hasAllItems = recipe.stream().allMatch(ingredient -> hasItem(player, ingredient.getItems()[0]));

            if(!hasAllItems) {
                player.displayClientMessage(Component.translatable("[Skyrimcraft] - You don't have the required items!"), false);
            } else {
                for (Ingredient ing : currentRecipeObject.getRecipeItems()) {
                    hasAndRemoveItem(player, ing.getItems()[0].copy());
                }

                player.getInventory().add(currentRecipeObject.getResult().copy());
                player.playSound(SoundEvents.BREWING_STAND_BREW, 1.0F, 1.0F);
                player.giveExperiencePoints(currentRecipeObject.getXpGained());

                final AddXpToSkill addAlchemyXp = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.getResourceKey(SkillRegistry.ALCHEMY.get()).get(), currentRecipeObject.getXpGained());
                Dispatcher.sendToServer(addAlchemyXp);
//                PacketDistributor.SERVER.noArg().send(addAlchemyXp);
            }
        }
    }

    public static boolean hasItem(Player player, ItemStack is) {
        if (is != null) {
            int count = 0;
            for(int i = 0; i < player.inventoryMenu.slots.size(); ++i) {
                ItemStack stack = player.getInventory().getItem(i);

                if(stack.is(is.getItem())) {
                    count += stack.getCount();
                    if(count >= is.getCount())
                        return true;
                }
            }
        }
        return false;
    }


    public static void hasAndRemoveItem(Player player, ItemStack is) {
        if (is != null) {
            player.getInventory().clearOrCountMatchingItems(
                    stack -> stack.is(is.getItem()),
                    is.getCount(),
                    player.getInventory()
            );
            player.getInventory().setChanged();
        }
    }

    public static void hasAndRemoveItems(Player player, ItemStack is) {
        if (is != null) {
            int count = is.getCount();

            for(int i = 0; i < player.inventoryMenu.slots.size(); ++i) {
                ItemStack stack =  player.getInventory().getItem(i);
                if(is.equals(stack) && ItemStack.matches(is, stack)) {
                    if(stack.getCount() >= count) {
                        player.getInventory().removeItem(i, count);
                        break;
                    } else {
                        count -= stack.getCount();
                        player.getInventory().removeItem(i, stack.getCount());
                    }
                }
            }
        }
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}

