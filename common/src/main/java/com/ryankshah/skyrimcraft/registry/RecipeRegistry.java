package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.recipe.AlchemyRecipe;
import com.ryankshah.skyrimcraft.data.recipe.ForgeRecipe;
import com.ryankshah.skyrimcraft.data.recipe.OvenRecipe;
import com.ryankshah.skyrimcraft.data.recipe.serial.AlchemyRecipeSerializer;
import com.ryankshah.skyrimcraft.data.recipe.serial.ForgeRecipeSerializer;
import com.ryankshah.skyrimcraft.data.recipe.serial.OvenRecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeRegistry
{
    public static void init() {}

    public static final RegistrationProvider<RecipeType<?>> RECIPE_TYPES = RegistrationProvider.get(BuiltInRegistries.RECIPE_TYPE, Constants.MODID);

    public static final RegistryObject<RecipeType<?>, RecipeType<AlchemyRecipe>> ALCHEMY = RECIPE_TYPES.register("alchemy", AlchemyRecipeType::new);
    public static final RegistryObject<RecipeType<?>, RecipeType<OvenRecipe>> OVEN = RECIPE_TYPES.register("oven", OvenRecipeType::new);
    public static final RegistryObject<RecipeType<?>, RecipeType<ForgeRecipe>> FORGE = RECIPE_TYPES.register("forge", ForgeRecipeType::new);

    static class AlchemyRecipeType implements RecipeType<AlchemyRecipe> {
        @Override
        public String toString() { return Constants.MODID + ":alchemy"; }
    }

    static class OvenRecipeType implements RecipeType<OvenRecipe> {
        @Override
        public String toString() { return Constants.MODID + ":oven"; }
    }

    static class ForgeRecipeType implements RecipeType<ForgeRecipe> {
        @Override
        public String toString() { return Constants.MODID + ":forge"; }
    }

    public static final RegistrationProvider<RecipeSerializer<?>> SERIALIZERS = RegistrationProvider.get(Registries.RECIPE_SERIALIZER, Constants.MODID);

    public static final RegistryObject<RecipeSerializer<?>, RecipeSerializer<AlchemyRecipe>> ALCHEMY_RECIPE_SERIALIZER = SERIALIZERS.register("alchemy", AlchemyRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>, RecipeSerializer<OvenRecipe>> OVEN_RECIPE_SERIALIZER = SERIALIZERS.register("oven", OvenRecipeSerializer::new);
    public static final RegistryObject<RecipeSerializer<?>, RecipeSerializer<ForgeRecipe>> FORGE_RECIPE_SERIALIZER = SERIALIZERS.register("forge", ForgeRecipeSerializer::new);

}