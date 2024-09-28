package com.ryankshah.skyrimcraft.data.world;

import com.ryankshah.skyrimcraft.registry.StructureRegistry;
import com.ryankshah.skyrimcraft.registry.TagsRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

import java.util.concurrent.CompletableFuture;

public class SkyrimcraftStructureTagsProvider extends StructureTagsProvider
{
    public SkyrimcraftStructureTagsProvider(PackOutput p_256522_, CompletableFuture<HolderLookup.Provider> p_256661_, String modId, @org.jetbrains.annotations.Nullable net.neoforged.neoforge.common.data.ExistingFileHelper existingFileHelper) {
        super(p_256522_, p_256661_, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TagsRegistry.StructureTagsInit.NETHER_FORTRESS)
                .add(BuiltinStructures.FORTRESS);
//        this.tag(TagsRegistry.StructureTagsInit.SHOUT_WALL)
//                .add(StructureRegistry.SHOUT_WALL.getResourceKey());
    }
}
