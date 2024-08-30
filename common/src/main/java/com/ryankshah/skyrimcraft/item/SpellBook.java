package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.network.spell.AddToKnownSpells;
import commonnetwork.api.Dispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class SpellBook extends Item
{
    private final Supplier<Spell> spell;

    public SpellBook(Properties properties, Supplier<Spell> spell) {
        super(properties.stacksTo(1));
        this.spell = spell;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);

        // Dont run on server side
        if (!worldIn.isClientSide) {
            return InteractionResultHolder.pass(itemstack);
        }
        Character character = Character.get(playerIn);
        List<Spell> knownSpells = character.getKnownSpells();
        if (this.spell != null && !knownSpells.contains(spell.get())) {
            final AddToKnownSpells addSpell = new AddToKnownSpells(SpellRegistry.SPELLS_REGISTRY.getResourceKey(spell.get()).get());
            Dispatcher.sendToServer(addSpell);
//            PacketDistributor.SERVER.noArg().send(addSpell);
            playerIn.displayClientMessage(Component.translatable("spellbook.learn", Component.translatable(spell.get().getName()).withStyle(ChatFormatting.RED)), false);
            playerIn.awardStat(Stats.ITEM_USED.get(this));
            itemstack.shrink(1);
        } else {
            playerIn.displayClientMessage(Component.translatable("spellbook.known"), false);
        }


        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(spell != null) {
            pTooltipComponents.add(Component.translatable("spellbook.tooltip.name", Component.translatable(
                            spell.get().getName())
                    .withStyle(ChatFormatting.RED)));
            pTooltipComponents.add(Component.translatable("spellbook.tooltip.difficulty", Component.translatable(
                            spell.get().getDifficulty().toString())
                    .withStyle(ChatFormatting.RED)));
        }
        super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
