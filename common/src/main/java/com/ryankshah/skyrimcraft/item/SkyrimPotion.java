package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.item.potion.IPotion;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class SkyrimPotion extends Item implements IPotion
{
    private static final int DRINK_DURATION = 32;

    public SkyrimPotion(Properties pProperties) {
        super(pProperties);
    }

//    @Override
//    public ItemStack getDefaultInstance() {
//        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.WATER);
//    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using the Item before the action is complete.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        Player player = pEntityLiving instanceof Player ? (Player) pEntityLiving : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, pStack);
        }

//        if (!pLevel.isClientSide) {
//            for (MobEffectInstance mobeffectinstance : PotionUtils.getMobEffects(pStack)) {
//                if (mobeffectinstance.getEffect().isInstantenous()) {
//                    mobeffectinstance.getEffect().applyInstantenousEffect(player, player, pEntityLiving, mobeffectinstance.getAmplifier(), 1.0);
//                } else {
//                    pEntityLiving.addEffect(new MobEffectInstance(mobeffectinstance));
//                }
//            }
//        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                pStack.shrink(1);
            }
        }

        if (player == null || !player.getAbilities().instabuild) {
            if (pStack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        pEntityLiving.gameEvent(GameEvent.DRINK);
        return pStack;
    }

    /**
     * Called when this item is used when targeting a Block
     */
//    @Override
//    public InteractionResult useOn(UseOnContext pContext) {
//        Level level = pContext.getLevel();
//        BlockPos blockpos = pContext.getClickedPos();
//        Player player = pContext.getPlayer();
//        ItemStack itemstack = pContext.getItemInHand();
//        BlockState blockstate = level.getBlockState(blockpos);
//        if (pContext.getClickedFace() != Direction.DOWN && blockstate.is(BlockTags.CONVERTABLE_TO_MUD) && PotionUtils.getPotion(itemstack) == Potions.WATER) {
//            level.playSound(null, blockpos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
//            player.setItemInHand(pContext.getHand(), ItemUtils.createFilledResult(itemstack, player, new ItemStack(Items.GLASS_BOTTLE)));
//            player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
//            if (!level.isClientSide) {
//                ServerLevel serverlevel = (ServerLevel) level;
//
//                for (int i = 0; i < 5; ++i) {
//                    serverlevel.sendParticles(
//                            ParticleTypes.SPLASH,
//                            (double) blockpos.getX() + level.random.nextDouble(),
//                            (double) (blockpos.getY() + 1),
//                            (double) blockpos.getZ() + level.random.nextDouble(),
//                            1,
//                            0.0,
//                            0.0,
//                            0.0,
//                            1.0
//                    );
//                }
//            }
//
//            level.playSound(null, blockpos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
//            level.gameEvent(null, GameEvent.FLUID_PLACE, blockpos);
//            level.setBlockAndUpdate(blockpos, Blocks.MUD.defaultBlockState());
//            return InteractionResult.sidedSuccess(level.isClientSide);
//        } else {
//            return InteractionResult.PASS;
//        }
//    }

    /**
     * How long it takes to use or consume an item
     */

    public int getUseDuration(ItemStack pStack, LivingEntity p_344979_) {
        return DRINK_DURATION;
    }

    /**
     * Returns the action that specifies what animation to play when the item is being used.
     */
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have different names based on their damage or NBT.
     */
//    @Override
//    public String getDescriptionId(ItemStack pStack) {
//        return PotionUtils.getPotion(pStack).getName(this.getDescriptionId() + ".effect.");
//    }

    @Override
    public PotionCategory getCategory() {
        return null;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return null;
    }
}