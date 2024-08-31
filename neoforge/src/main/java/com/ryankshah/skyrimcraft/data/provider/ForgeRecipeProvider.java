package com.ryankshah.skyrimcraft.data.provider;

import com.google.common.collect.Sets;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.recipe.ForgeRecipe;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * TODO: Fix the XP rates for forging!
 */
public class ForgeRecipeProvider implements DataProvider, IConditionBuilder
{
    protected final PackOutput.PathProvider recipePathProvider;
    protected final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public static Map<String, ForgeRecipe> RECIPES;

    public ForgeRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.recipePathProvider = p_248933_.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
        this.lookupProvider = lookupProvider;
        RECIPES = new HashMap<>();
    }

    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        daedricRecipes(pRecipeOutput);
        dwarvenRecipes(pRecipeOutput);
        ebonyRecipes(pRecipeOutput);
        elvenRecipes(pRecipeOutput);
        glassRecipes(pRecipeOutput);
        ironRecipes(pRecipeOutput);
        orcishRecipes(pRecipeOutput);
        steelRecipes(pRecipeOutput);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        return this.lookupProvider.thenCompose((provider) -> {
            final Set<ResourceLocation> set = Sets.newHashSet();
            final List<CompletableFuture<?>> list = new ArrayList<>();
            this.buildRecipes(new RecipeOutput() {
                @Override
                public Advancement.Builder advancement() {
                    return null;
                }

                public void accept(ResourceLocation p_312039_, Recipe<?> p_312254_, @Nullable AdvancementHolder p_311794_, ICondition... conditions) {
                    if (!set.add(p_312039_)) {
                        throw new IllegalStateException("Duplicate recipe " + p_312039_);
                    } else {
                        list.add(DataProvider.saveStable(pOutput, provider, Recipe.CONDITIONAL_CODEC, Optional.of(new WithConditions(p_312254_, conditions)), ForgeRecipeProvider.this.recipePathProvider.json(p_312039_)));
                    }
                }
            });
            return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName() {
        return Constants.MODID + " Forge Recipes";
    }

    private static void daedricRecipes(RecipeOutput consumer) {
//        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.DAEDRIC_ARROW.get(), 24)).level(90).xp(5)
//                .category("daedric")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.DAEDRIC_ARROW.getId().getPath()));
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_DAGGER.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_DAGGER.get(), 1), 90, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_SWORD.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_SWORD.get(), 1), 90, 13,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_BATTLEAXE.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_BATTLEAXE.get(), 1), 90, 27,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_BOW.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_BOW.get(), 1), 90, 25,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_GREATSWORD.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_GREATSWORD.get(), 1), 90, 25,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_MACE.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_MACE.get(), 1), 90, 18,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_WAR_AXE.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_WAR_AXE.get(), 1), 90, 15,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_WARHAMMER.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_WARHAMMER.get(), 1), 90, 40,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_HELMET.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_HELMET.get(), 1), 90, 16,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_CHESTPLATE.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_CHESTPLATE.get(), 1), 90, 32,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_LEGGINGS.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_LEGGINGS.get(), 1), 90, 32,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_BOOTS.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_BOOTS.get(), 1), 90, 16,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DAEDRIC_SHIELD.get()).getPath()),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_SHIELD.get(), 1), 90, 16,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 4)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        )),
                null
        );
    }

    private static void dwarvenRecipes(RecipeOutput consumer) {
//        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.DWARVEN_ARROW.get(), 24)).level(30).xp(5)
//                .category("dwarven")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.DWARVEN_ARROW.getId().getPath()));
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_DAGGER.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_DAGGER.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 4)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_BATTLEAXE.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_BATTLEAXE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_BOW.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_BOW.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_GREATSWORD.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_GREATSWORD.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_MACE.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_MACE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_SWORD.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_SWORD.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_WAR_AXE.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_WAR_AXE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_WARHAMMER.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_WARHAMMER.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_HELMET.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_HELMET.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_CHESTPLATE.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_CHESTPLATE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_LEGGINGS.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_LEGGINGS.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_BOOTS.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_BOOTS.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.DWARVEN_SHIELD.get()).getPath()),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_SHIELD.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        )),
                null
        );
    }

    private static void ebonyRecipes(RecipeOutput consumer) {
//        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.EBONY_ARROW.get(), 24)).level(80).xp(5)
//                .category("ebony")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.EBONY_ARROW.getId().getPath()));
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_DAGGER.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_DAGGER.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_SWORD.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_SWORD.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_GREATSWORD.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_GREATSWORD.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_WAR_AXE.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_WAR_AXE.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_BATTLEAXE.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_BATTLEAXE.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_MACE.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_MACE.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_WARHAMMER.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_WARHAMMER.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_BOW.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_BOW.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_HELMET.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_HELMET.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_CHESTPLATE.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_CHESTPLATE.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_LEGGINGS.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_LEGGINGS.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_BOOTS.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_BOOTS.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.EBONY_SHIELD.get()).getPath()),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_SHIELD.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 4))
                        )),
                null
        );
    }

    private static void elvenRecipes(RecipeOutput consumer) {
//        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.ELVEN_ARROW.get(), 24)).level(30).xp(5)
//                .category("elven")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.ELVEN_ARROW.getId().getPath()));
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_DAGGER.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_DAGGER.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_SWORD.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_SWORD.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_GREATSWORD.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_GREATSWORD.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_WAR_AXE.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_WAR_AXE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_BATTLEAXE.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_BATTLEAXE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_MACE.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_MACE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_WARHAMMER.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_WARHAMMER.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_BOW.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_BOW.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_HELMET.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_HELMET.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_CHESTPLATE.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_CHESTPLATE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_LEGGINGS.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_LEGGINGS.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_BOOTS.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_BOOTS.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ELVEN_SHIELD.get()).getPath()),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_SHIELD.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 4))
                        )),
                null
        );
    }

    private static void glassRecipes(RecipeOutput consumer) {
//        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.GLASS_ARROW.get(), 24)).level(70).xp(5)
//                .category("glass")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.GLASS_ARROW.getId().getPath()));
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_DAGGER.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_DAGGER.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_SWORD.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_SWORD.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_GREATSWORD.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_GREATSWORD.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_WAR_AXE.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_WAR_AXE.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_BATTLEAXE.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_BATTLEAXE.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_MACE.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_MACE.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_WARHAMMER.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_WARHAMMER.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 3))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_BOW.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_BOW.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_HELMET.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_HELMET.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_CHESTPLATE.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_CHESTPLATE.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_LEGGINGS.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_LEGGINGS.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_BOOTS.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_BOOTS.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GLASS_SHIELD.get()).getPath()),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_SHIELD.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 4))
                        )),
                null
        );
    }

    private static void ironRecipes(RecipeOutput consumer) {
//        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.IRON_ARROW.get(), 24)).level(1).xp(5)
//                .category("iron")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.IRON_ARROW.getId().getPath()));
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.IRON_DAGGER.get()).getPath()),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_DAGGER.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.IRON_SWORD.get()).getPath()),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_SWORD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.IRON_GREATSWORD.get()).getPath()),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_GREATSWORD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.IRON_WAR_AXE.get()).getPath()),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_WAR_AXE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.IRON_BATTLEAXE.get()).getPath()),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_BATTLEAXE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.IRON_MACE.get()).getPath()),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_MACE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 3))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.IRON_WARHAMMER.get()).getPath()),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_WARHAMMER.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.IRON_HELMET.get()).getPath()),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_HELMET.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 3))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.IRON_CHESTPLATE.get()).getPath()),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_CHESTPLATE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 5))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.IRON_LEGGINGS.get()).getPath()),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_LEGGINGS.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 5))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.IRON_BOOTS.get()).getPath()),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_BOOTS.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 3))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.IRON_SHIELD.get()).getPath()),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_SHIELD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 4))
                        )),
                null
        );
    }

    private static void orcishRecipes(RecipeOutput consumer) {
//        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.ORCISH_ARROW.get(), 24)).level(50).xp(5)
//                .category("orcish")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.ORCISH_ARROW.getId().getPath()));
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_DAGGER.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_DAGGER.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_SWORD.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_SWORD.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_GREATSWORD.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_GREATSWORD.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_WAR_AXE.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_WAR_AXE.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_BATTLEAXE.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_BATTLEAXE.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_MACE.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_MACE.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 3))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_WARHAMMER.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_WARHAMMER.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_BOW.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_BOW.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_HELMET.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_HELMET.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_CHESTPLATE.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_CHESTPLATE.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_LEGGINGS.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_LEGGINGS.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_BOOTS.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_BOOTS.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 3))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.ORCISH_SHIELD.get()).getPath()),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_SHIELD.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 3))
                        )),
                null
        );
    }

    private static void steelRecipes(RecipeOutput consumer) {
//        RECIPES.put("steel", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.STEEL_ARROW.get(), 24)).level(1).xp(5)
//                .category("steel")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.STEEL_ARROW.getId().getPath()));
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.STEEL_DAGGER.get()).getPath()),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_DAGGER.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.STEEL_SWORD.get()).getPath()),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_SWORD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.STEEL_GREATSWORD.get()).getPath()),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_GREATSWORD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.STEEL_WAR_AXE.get()).getPath()),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_WAR_AXE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 2))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.STEEL_BATTLEAXE.get()).getPath()),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_BATTLEAXE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.STEEL_MACE.get()).getPath()),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_MACE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 3))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.STEEL_WARHAMMER.get()).getPath()),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_WARHAMMER.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 4))
                        )),
                null
        );
        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.STEEL_SHIELD.get()).getPath()),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_SHIELD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 3))
                        )),
                null
        );
    }
}
