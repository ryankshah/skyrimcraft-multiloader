package com.ryankshah.skyrimcraft.data.world;

import com.ryankshah.skyrimcraft.registry.TagsRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SkyrimBiomeTagsProvider extends BiomeTagsProvider
{
    public SkyrimBiomeTagsProvider(PackOutput p_255800_, CompletableFuture<HolderLookup.Provider> p_256205_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_255800_, p_256205_, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(TagsRegistry.BiomeTagsInit.WHITE_SABRE_BIOMES)
                .add(Biomes.SNOWY_BEACH).add(Biomes.SNOWY_TAIGA)
                .add(Biomes.SNOWY_SLOPES).add(Biomes.SNOWY_PLAINS)
                .add(Biomes.FROZEN_RIVER).add(Biomes.FROZEN_PEAKS)
                .add(Biomes.ICE_SPIKES).add(Biomes.GROVE)
                .add(Biomes.JAGGED_PEAKS);
    }
}
