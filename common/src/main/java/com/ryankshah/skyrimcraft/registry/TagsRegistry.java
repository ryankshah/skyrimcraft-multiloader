package com.ryankshah.skyrimcraft.registry;

import com.ryankshah.skyrimcraft.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class TagsRegistry
{
    public static void init() {}

    public static class BlockTagsInit {
        // Ores
        public static TagKey<Block> CORUNDUM_ORE_TAG = create(createResourceLocation("ores/corundum_ore"));
        public static TagKey<Block> EBONY_ORE_TAG = create(createResourceLocation("ores/ebony_ore"));
        public static TagKey<Block> MALACHITE_ORE_TAG = create(createResourceLocation("ores/malachite_ore"));
        public static TagKey<Block> MOONSTONE_ORE_TAG = create(createResourceLocation("ores/moonstone_ore"));
        public static TagKey<Block> ORICHALCUM_ORE_TAG = create(createResourceLocation("ores/orichalcum_ore"));
        public static TagKey<Block> QUICKSILVER_ORE_TAG = create(createResourceLocation("ores/quicksilver_ore"));
        public static TagKey<Block> SILVER_ORE_TAG = create(createResourceLocation("ores/silver_ore"));
        //Deepslate Ores
        public static TagKey<Block> DEEPSLATE_CORUNDUM_ORE_TAG = create(createResourceLocation("ores/deepslate_corundum_ore"));
        public static TagKey<Block> DEEPSLATE_EBONY_ORE_TAG = create(createResourceLocation("ores/deepslate_ebony_ore"));
        public static TagKey<Block> DEEPSLATE_MALACHITE_ORE_TAG = create(createResourceLocation("ores/deepslate_malachite_ore"));
        public static TagKey<Block> DEEPSLATE_MOONSTONE_ORE_TAG = create(createResourceLocation("ores/deepslate_moonstone_ore"));
        public static TagKey<Block> DEEPSLATE_ORICHALCUM_ORE_TAG = create(createResourceLocation("ores/deepslate_orichalcum_ore"));
        public static TagKey<Block> DEEPSLATE_QUICKSILVER_ORE_TAG = create(createResourceLocation("ores/deepslate_quicksilver_ore"));
        public static TagKey<Block> DEEPSLATE_SILVER_ORE_TAG = create(createResourceLocation("ores/deepslate_silver_ore"));


        public static TagKey<Block> NEEDS_ANCIENT_NORD_TOOL = create(createResourceLocation("needs_ancient_nord_tool"));
        public static TagKey<Block> NEEDS_DRAGONBONE_TOOL = create(createResourceLocation("needs_dragonbone_tool"));
        public static TagKey<Block> NEEDS_STEEL_TOOL = create(createResourceLocation("needs_steel_tool"));
        public static TagKey<Block> NEEDS_FALMER_TOOL = create(createResourceLocation("needs_falmer_tool"));
        public static TagKey<Block> NEEDS_GLASS_TOOL = create(createResourceLocation("needs_glass_tool"));
        public static TagKey<Block> NEEDS_ELVEN_TOOL = create(createResourceLocation("needs_elven_tool"));
        public static TagKey<Block> NEEDS_ORCISH_TOOL = create(createResourceLocation("needs_orcish_tool"));
        public static TagKey<Block> NEEDS_DWARVEN_TOOL = create(createResourceLocation("needs_dwarven_tool"));
        public static TagKey<Block> NEEDS_DAEDRIC_TOOL = create(createResourceLocation("needs_daedric_tool"));
        public static TagKey<Block> NEEDS_EBONY_TOOL = create(createResourceLocation("needs_ebony_tool"));

        public static TagKey<Block> create(ResourceLocation location) {
            return TagKey.create(Registries.BLOCK, location);
        }

//        // For normal blocks - storage
//        public static TagKey<Block> STORAGE_BLOCKS_EXAMPLE = create(createResourceLocation("storage_blocks/example_block"));

        private static ResourceLocation createResourceLocation(String name) {
            return ResourceLocation.fromNamespaceAndPath(Constants.MODID,  name);
        }
    }

    public static class ItemTagsInit {

        public static TagKey<Item> bind(ResourceLocation location) {
            return TagKey.create(Registries.ITEM, location);
        }
        // Ores
        public static TagKey<Item> CORUNDUM_ORE_ITEM_TAG = bind(createResourceLocation("ores/corundum_ore"));
        public static TagKey<Item> EBONY_ORE_ITEM_TAG = bind(createResourceLocation("ores/ebony_ore"));
        public static TagKey<Item> MALACHITE_ORE_ITEM_TAG = bind(createResourceLocation("ores/malachite_ore"));
        public static TagKey<Item> MOONSTONE_ORE_ITEM_TAG = bind(createResourceLocation("ores/moonstone_ore"));
        public static TagKey<Item> ORICHALCUM_ORE_ITEM_TAG = bind(createResourceLocation("ores/orichalcum_ore"));
        public static TagKey<Item> QUICKSILVER_ORE_ITEM_TAG = bind(createResourceLocation("ores/quicksilver_ore"));
        public static TagKey<Item> SILVER_ORE_ITEM_TAG = bind(createResourceLocation("ores/silver_ore"));
        //Deepslate Ores
        public static TagKey<Item> DEEPSLATE_CORUNDUM_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_corundum_ore"));
        public static TagKey<Item> DEEPSLATE_EBONY_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_ebony_ore"));
        public static TagKey<Item> DEEPSLATE_MALACHITE_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_malachite_ore"));
        public static TagKey<Item> DEEPSLATE_MOONSTONE_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_moonstone_ore"));
        public static TagKey<Item> DEEPSLATE_ORICHALCUM_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_orichalcum_ore"));
        public static TagKey<Item> DEEPSLATE_QUICKSILVER_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_quicksilver_ore"));
        public static TagKey<Item> DEEPSLATE_SILVER_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_silver_ore"));

        // Items

//        // ore ingot
//        public static TagKey<Item> INGOTS_EXAMPLE = bind(createResourceLocation("ingots/example"));
        public static TagKey<Item> SKYRIM_INGREDIENTS = bind(createResourceLocation("ingredients"));
//
//        // Raw ore scrap
//        public static TagKey<Item> SCRAPS_EXAMPLE = bind(createResourceLocation("scraps/example"));


        private static ResourceLocation createResourceLocation(String name) {
            return ResourceLocation.fromNamespaceAndPath(Constants.MODID,  name);
        }
    }

    public static class StructureTagsInit {
        public static TagKey<Structure> NETHER_FORTRESS = TagKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(Constants.MODID,  "fortress"));
        public static TagKey<Structure> SHOUT_WALL = TagKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(Constants.MODID,  "shout_wall"));
    }

    public static class BiomeTagsInit {
        public static TagKey<Biome> WHITE_SABRE_BIOMES = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Constants.MODID,  "all_snow"));
    }
}