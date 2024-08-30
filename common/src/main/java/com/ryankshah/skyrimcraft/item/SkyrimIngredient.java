package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.util.IngredientEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SkyrimIngredient extends Item
{
    private IngredientEffect[] ingredientEffects;

    public SkyrimIngredient(Properties pProperties) {
        super(pProperties);
    }

    public SkyrimIngredient(Properties properties, IngredientEffect... ingredientEffects) {
        super(properties);
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
