package com.ryankshah.skyrimcraft.data.recipe.serial;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.data.recipe.ForgeRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ForgeRecipeSerializer implements RecipeSerializer<ForgeRecipe>
{
    public static final MapCodec<ForgeRecipe> CODEC = RecordCodecBuilder.mapCodec(
            p_311738_ -> p_311738_.group(
                            Codec.STRING.fieldOf("category").forGetter(ForgeRecipe::getCategory),
                            ItemStack.CODEC.fieldOf("result").forGetter(ForgeRecipe::getResult),
                            Codec.INT.fieldOf("level").forGetter(ForgeRecipe::getRequiredLevel),
                            Codec.INT.fieldOf("xp").forGetter(ForgeRecipe::getXpGained),
                            Ingredient.CODEC_NONEMPTY
                                    .listOf()
                                    .fieldOf("ingredients")
                                    .flatXmap(
                                            p_301021_ -> {
                                                Ingredient[] ingredient = p_301021_.toArray(Ingredient[]::new);
                                                if(ingredient.length == 0) {
                                                    return DataResult.error(() -> "No ingredients for Alchemy Recipe");
                                                } else {
                                                    return DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredient));
                                                }
                                            },
                                            DataResult::success
                                    )
                                    .forGetter(ForgeRecipe::getIngredients)
                    )
                    .apply(p_311738_, ForgeRecipe::new)
    );
    private static final StreamCodec<RegistryFriendlyByteBuf, ForgeRecipe> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());


    public ForgeRecipeSerializer() {
    }

    @Override
    public MapCodec<ForgeRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ForgeRecipe> streamCodec() {
        return STREAM_CODEC;
    }

//    @Override
//    public @NotNull Codec<ForgeRecipe> codec() {
//        return CODEC;
//    }
//
//    @Override
//    public @NotNull ForgeRecipe fromNetwork(FriendlyByteBuf buf) {
//        NonNullList<Ingredient> recipeItems = NonNullList.create();
//        String category = buf.readUtf();
//        ItemStack stackToCreate = buf.readItem();
//
//        int length = buf.readInt();
//        for (int i = 0; i < length; i++) {
//            int ingLen = buf.readInt();
//            ItemStack[] stacks = new ItemStack[ingLen];
//            for(int j = 0; j < ingLen; j++)
//                stacks[i] = buf.readItem();
//            recipeItems.add(Ingredient.of(stacks));
//        }
//
//        int level = buf.readInt();
//        int xp = buf.readInt();
//
//        return new ForgeRecipe(category, stackToCreate, xp, level, recipeItems);
//    }
//
//    @Override
//    public void toNetwork(FriendlyByteBuf buf, ForgeRecipe ForgeRecipe) {
//        buf.writeUtf(ForgeRecipe.getCategory());
//
//        if (ForgeRecipe.getResult() != null) {
//            buf.writeItem(ForgeRecipe.getResult());
//        }
//
//        if (ForgeRecipe.getRecipeItems() != null && !ForgeRecipe.getRecipeItems().isEmpty()) {
//            buf.writeInt(ForgeRecipe.getRecipeItems().size());
//            for (Ingredient ing : ForgeRecipe.getRecipeItems()) {
//                buf.writeInt(ing.getItems().length);
//                for(int i = 0; i < ing.getItems().length; i++)
//                    buf.writeItem(ing.getItems()[i]);
//            }
//        }
//
//        buf.writeInt(ForgeRecipe.getRequiredLevel());
//        buf.writeInt(ForgeRecipe.getXpGained());
//    }
}