package com.ryankshah.skyrimcraft.data.recipe;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SkyrimcraftRecipeProvider extends RecipeProvider implements IConditionBuilder
{
    private static final List<ItemLike> CORUNDUM_ORE_SMELTABLE = List.of(BlockRegistry.CORUNDUM_ORE.get(), BlockRegistry.DEEPSLATE_CORUNDUM_ORE.get());
    private static final List<ItemLike> EBONY_ORE_SMELTABLE = List.of(BlockRegistry.EBONY_ORE.get(), BlockRegistry.DEEPSLATE_EBONY_ORE.get());
    private static final List<ItemLike> MALACHITE_ORE_SMELTABLE = List.of(BlockRegistry.MALACHITE_ORE.get(), BlockRegistry.DEEPSLATE_MALACHITE_ORE.get());
    private static final List<ItemLike> MOONSTONE_ORE_SMELTABLE = List.of(BlockRegistry.MOONSTONE_ORE.get(), BlockRegistry.DEEPSLATE_MOONSTONE_ORE.get());
    private static final List<ItemLike> ORICHALCUM_ORE_SMELTABLE = List.of(BlockRegistry.ORICHALCUM_ORE.get(), BlockRegistry.DEEPSLATE_ORICHALCUM_ORE.get());
    private static final List<ItemLike> QUICKSILVER_ORE_SMELTABLE = List.of(BlockRegistry.QUICKSILVER_ORE.get(), BlockRegistry.DEEPSLATE_QUICKSILVER_ORE.get());
    private static final List<ItemLike> SILVER_ORE_SMELTABLE = List.of(BlockRegistry.SILVER_ORE.get(), BlockRegistry.DEEPSLATE_SILVER_ORE.get());


    public SkyrimcraftRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider); //, lookupProvider
    }

    @Override
    protected void buildRecipes(RecipeOutput pWriter) {
        oreSmelting(pWriter, CORUNDUM_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.CORUNDUM_INGOT.get(), 0.25f, 200, "corundum_ingot");
        oreBlasting(pWriter, CORUNDUM_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.CORUNDUM_INGOT.get(), 0.25f, 100, "corundum_ingot");

        oreSmelting(pWriter, EBONY_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.EBONY_INGOT.get(), 0.25f, 200, "ebony_ingot");
        oreBlasting(pWriter, EBONY_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.EBONY_INGOT.get(), 0.25f, 100, "ebony_ingot");

        oreSmelting(pWriter, MALACHITE_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.MALACHITE_INGOT.get(), 0.25f, 200, "malachite_ingot");
        oreBlasting(pWriter, MALACHITE_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.MALACHITE_INGOT.get(), 0.25f, 100, "malachite_ingot");

        oreSmelting(pWriter, MOONSTONE_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.MOONSTONE_INGOT.get(), 0.25f, 200, "moonstone_ingot");
        oreBlasting(pWriter, MOONSTONE_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.MOONSTONE_INGOT.get(), 0.25f, 100, "moonstone_ingot");

        oreSmelting(pWriter, ORICHALCUM_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.ORICHALCUM_INGOT.get(), 0.25f, 200, "orichalcum_ingot");
        oreBlasting(pWriter, ORICHALCUM_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.ORICHALCUM_INGOT.get(), 0.25f, 100, "orichalcum_ingot");

        oreSmelting(pWriter, QUICKSILVER_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.QUICKSILVER_INGOT.get(), 0.25f, 200, "quicksilver_ingot");
        oreBlasting(pWriter, QUICKSILVER_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.QUICKSILVER_INGOT.get(), 0.25f, 100, "quicksilver_ingot");

        oreSmelting(pWriter, SILVER_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.SILVER_INGOT.get(), 0.25f, 200, "silver_ingot");
        oreBlasting(pWriter, SILVER_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.SILVER_INGOT.get(), 0.25f, 100, "silver_ingot");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)).requires(Items.LEATHER).unlockedBy("has_leather", has(Items.LEATHER)).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.FLOUR.get()).requires(Items.WHEAT).requires(Items.BOWL).unlockedBy("has_wheat", has(Items.WHEAT)).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.BUTTER.get()).requires(Items.MILK_BUCKET).requires(Items.MAGMA_CREAM).unlockedBy("has_milk_bucket", has(Items.MILK_BUCKET)).save(pWriter);
        // blocks
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.OVEN.get()).define('s', Blocks.STONE).define('b', Blocks.STONE_BRICKS).pattern(" b ").pattern("b b").pattern("sss").unlockedBy("has_stone_brick", has(Blocks.STONE_BRICKS)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.ALCHEMY_TABLE.get()).define('p', ItemTags.PLANKS).define('g', Items.GLASS_BOTTLE).pattern(" g ").pattern("ppp").pattern("p p").unlockedBy("has_planks", has(Items.OAK_PLANKS)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.BLACKSMITH_FORGE.get()).define('l', Items.LAVA_BUCKET).define('s', Blocks.STONE).define('c', Blocks.COBBLESTONE).pattern("c c").pattern("clc").pattern("sss").unlockedBy("has_cobble", has(Blocks.COBBLESTONE)).save(pWriter);
    }

    protected static void oreSmelting(
            RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup
    ) {
        oreCooking(
                pRecipeOutput,
                RecipeSerializer.SMELTING_RECIPE,
                SmeltingRecipe::new,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTime,
                pGroup,
                "_from_smelting"
        );
    }

    protected static void oreBlasting(
            RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup
    ) {
        oreCooking(
                pRecipeOutput,
                RecipeSerializer.BLASTING_RECIPE,
                BlastingRecipe::new,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTime,
                pGroup,
                "_from_blasting"
        );
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(
            RecipeOutput pRecipeOutput,
            RecipeSerializer<T> pSerializer,
            AbstractCookingRecipe.Factory<T> pRecipeFactory,
            List<ItemLike> pIngredients,
            RecipeCategory pCategory,
            ItemLike pResult,
            float pExperience,
            int pCookingTime,
            String pGroup,
            String pSuffix
    ) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pSerializer, pRecipeFactory)
                    .group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pRecipeOutput, Constants.MODID + ":" + getItemName(pResult) + pSuffix + "_" + getItemName(itemlike));
        }
    }
}