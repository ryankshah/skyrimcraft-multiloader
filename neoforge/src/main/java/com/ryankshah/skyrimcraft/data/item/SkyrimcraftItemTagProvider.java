package com.ryankshah.skyrimcraft.data.item;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.registry.TagsRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class SkyrimcraftItemTagProvider extends ItemTagsProvider {
    public SkyrimcraftItemTagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, blockTagsProvider.contentsGetter(), Constants.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // ores
        copy(TagsRegistry.BlockTagsInit.CORUNDUM_ORE_TAG, TagsRegistry.ItemTagsInit.CORUNDUM_ORE_ITEM_TAG);
        copy(TagsRegistry.BlockTagsInit.EBONY_ORE_TAG, TagsRegistry.ItemTagsInit.EBONY_ORE_ITEM_TAG);
        copy(TagsRegistry.BlockTagsInit.MALACHITE_ORE_TAG, TagsRegistry.ItemTagsInit.MALACHITE_ORE_ITEM_TAG);
        copy(TagsRegistry.BlockTagsInit.MOONSTONE_ORE_TAG, TagsRegistry.ItemTagsInit.MOONSTONE_ORE_ITEM_TAG);
        copy(TagsRegistry.BlockTagsInit.ORICHALCUM_ORE_TAG, TagsRegistry.ItemTagsInit.ORICHALCUM_ORE_ITEM_TAG);
        copy(TagsRegistry.BlockTagsInit.QUICKSILVER_ORE_TAG, TagsRegistry.ItemTagsInit.QUICKSILVER_ORE_ITEM_TAG);
        copy(TagsRegistry.BlockTagsInit.SILVER_ORE_TAG, TagsRegistry.ItemTagsInit.SILVER_ORE_ITEM_TAG);

        // deepslate ores
        copy(TagsRegistry.BlockTagsInit.DEEPSLATE_CORUNDUM_ORE_TAG, TagsRegistry.ItemTagsInit.DEEPSLATE_CORUNDUM_ORE_ITEM_TAG);
        copy(TagsRegistry.BlockTagsInit.DEEPSLATE_EBONY_ORE_TAG, TagsRegistry.ItemTagsInit.DEEPSLATE_EBONY_ORE_ITEM_TAG);
        copy(TagsRegistry.BlockTagsInit.DEEPSLATE_MALACHITE_ORE_TAG, TagsRegistry.ItemTagsInit.DEEPSLATE_MALACHITE_ORE_ITEM_TAG);
        copy(TagsRegistry.BlockTagsInit.DEEPSLATE_MOONSTONE_ORE_TAG, TagsRegistry.ItemTagsInit.DEEPSLATE_MOONSTONE_ORE_ITEM_TAG);
        copy(TagsRegistry.BlockTagsInit.DEEPSLATE_ORICHALCUM_ORE_TAG, TagsRegistry.ItemTagsInit.DEEPSLATE_ORICHALCUM_ORE_ITEM_TAG);
        copy(TagsRegistry.BlockTagsInit.DEEPSLATE_QUICKSILVER_ORE_TAG, TagsRegistry.ItemTagsInit.DEEPSLATE_QUICKSILVER_ORE_ITEM_TAG);
        copy(TagsRegistry.BlockTagsInit.DEEPSLATE_SILVER_ORE_TAG, TagsRegistry.ItemTagsInit.DEEPSLATE_SILVER_ORE_ITEM_TAG);

//        // ingots
//        tag(TagsRegistry.ItemTagsRegistry.INGOTS_EXAMPLE).add(ItemInit.EXAMPLE_ITEM.get());
//
//        // scraps
//        tag(TagsRegistry.ItemTagsInit.SCRAPS_EXAMPLE).add(ItemInit.EXAMPLE_ITEM.get());
    }
}