package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.util.IngredientEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SkyrimBlockItemIngredient extends BlockItem
{
    private IngredientEffect[] ingredientEffects;

    public SkyrimBlockItemIngredient(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public SkyrimBlockItemIngredient(Block pBlock, Properties properties, IngredientEffect... ingredientEffects) {
        super(pBlock, properties);
        this.ingredientEffects = ingredientEffects;
    }


    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("Effects: ").withStyle(ChatFormatting.DARK_PURPLE));
        for(int i = 0; i < ingredientEffects.length; i++)
            pTooltipComponents.add(Component.translatable(ingredientEffects[i].toString()));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
