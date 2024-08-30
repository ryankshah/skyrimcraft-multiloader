package com.ryankshah.skyrimcraft.data;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.advancement.SkyrimAdvancementProvider;
import com.ryankshah.skyrimcraft.data.block.SkyrimcraftBlockStateProvider;
import com.ryankshah.skyrimcraft.data.block.SkyrimcraftBlockTagsProvider;
import com.ryankshah.skyrimcraft.data.item.SkyrimcraftItemModelProvider;
import com.ryankshah.skyrimcraft.data.item.SkyrimcraftItemTagProvider;
import com.ryankshah.skyrimcraft.data.lang.SkyrimcraftLanguageProvider;
import com.ryankshah.skyrimcraft.data.loot_table.SkyrimLootModifierProvider;
import com.ryankshah.skyrimcraft.data.loot_table.SkyrimcraftLootTables;
import com.ryankshah.skyrimcraft.data.provider.AlchemyRecipeProvider;
import com.ryankshah.skyrimcraft.data.provider.ForgeRecipeProvider;
import com.ryankshah.skyrimcraft.data.provider.OvenRecipeProvider;
import com.ryankshah.skyrimcraft.data.recipe.SkyrimcraftRecipeProvider;
import com.ryankshah.skyrimcraft.data.sound.SkyrimSoundsProvider;
import com.ryankshah.skyrimcraft.data.tag.SkyrimcraftPoiTypeTagsProvider;
import com.ryankshah.skyrimcraft.data.world.SkyrimBiomeTagsProvider;
import com.ryankshah.skyrimcraft.data.world.SkyrimcraftStructureTagsProvider;
import com.ryankshah.skyrimcraft.data.world.SkyrimcraftWorldGenProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

public class DataGenerators
{
    public static void gatherData(GatherDataEvent event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

            generator.addProvider(true, new SkyrimAdvancementProvider(output, event.getLookupProvider(), existingFileHelper, List.of(new SkyrimAdvancementProvider.SkyrimAdvancements())));
            generator.addProvider(true, new SkyrimcraftLanguageProvider(output, Constants.MODID, "en_us"));
            generator.addProvider(true, new SkyrimBiomeTagsProvider(output, event.getLookupProvider(), Constants.MODID, existingFileHelper));
            generator.addProvider(true, new SkyrimcraftItemModelProvider(output, existingFileHelper));
            generator.addProvider(true, new SkyrimcraftBlockStateProvider(output, Constants.MODID, existingFileHelper));
            generator.addProvider(true, new SkyrimcraftLootTables(output, event.getLookupProvider()));
            generator.addProvider(true, new SkyrimcraftRecipeProvider(output, event.getLookupProvider()));
            generator.addProvider(true, new AlchemyRecipeProvider(output, event.getLookupProvider()));
            generator.addProvider(true, new OvenRecipeProvider(output, event.getLookupProvider()));
            generator.addProvider(true, new ForgeRecipeProvider(output, event.getLookupProvider()));
            SkyrimcraftBlockTagsProvider blockTagsProvider = new SkyrimcraftBlockTagsProvider(output, event.getLookupProvider(), Constants.MODID, existingFileHelper);
            generator.addProvider(true, blockTagsProvider);
            generator.addProvider(true, new SkyrimcraftItemTagProvider(output, event.getLookupProvider(), blockTagsProvider, existingFileHelper));
            generator.addProvider(event.includeServer(), new SkyrimcraftStructureTagsProvider(output, event.getLookupProvider(), Constants.MODID, existingFileHelper));
            generator.addProvider(event.includeServer(), new SkyrimcraftWorldGenProvider(output, event.getLookupProvider()));
//            generator.addProvider(event.includeServer(), new SkyrimCuriosDataProvider(Constants.MODID, output,existingFileHelper, event.getLookupProvider()));
            generator.addProvider(true, new SkyrimLootModifierProvider(output, event.getLookupProvider(), Constants.MODID));
            generator.addProvider(event.includeClient(), new SkyrimSoundsProvider(output, Constants.MODID, existingFileHelper));
            generator.addProvider(event.includeServer(), new SkyrimcraftPoiTypeTagsProvider(output, event.getLookupProvider(), existingFileHelper));
        } catch (RuntimeException e) {
            Constants.LOG.error("Failed to generate data", e);
        }
    }
}