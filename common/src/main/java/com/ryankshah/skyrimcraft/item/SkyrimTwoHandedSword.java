package com.ryankshah.skyrimcraft.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

//TODO: FiX THIS!!! (the prevent use if has shield etc.)
public class SkyrimTwoHandedSword extends SwordItem
{
    public SkyrimTwoHandedSword(Tier pTier, Properties pProperties) {
        super(pTier, pProperties);
    }

//    @Override
//    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
//        return !entity.getOffhandItem().equals(ItemStack.EMPTY);
//    }
//
//    @Override
//    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
//        return !player.getOffhandItem().equals(ItemStack.EMPTY);
//    }

    public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        if (p_77659_2_.getOffhandItem().equals(ItemStack.EMPTY)) {
            p_77659_2_.startUsingItem(p_77659_3_);
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

//    @Override
//    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
//        return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
//    }
//
//    @Override
//    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
//        if(armorType == EquipmentSlot.OFFHAND)
//            return false;
//        else return super.canEquip(stack, armorType, entity);
//    }

    public UseAnim getUseAnimation(ItemStack p_77661_1_) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }
}
