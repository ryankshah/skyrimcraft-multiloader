package com.ryankshah.skyrimcraft.data.block;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.*;
import com.ryankshah.skyrimcraft.block.util.TripleBlockPart;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class BlockData
{
    public static void addBlockTranslations(LanguageProvider provider) {
        provider.addBlock(BlockRegistry.CORUNDUM_ORE, "Corundum Ore");
        provider.addBlock(BlockRegistry.DEEPSLATE_CORUNDUM_ORE, "Corundum Ore");
        provider.addBlock(BlockRegistry.EBONY_ORE, "Ebony Ore");
        provider.addBlock(BlockRegistry.DEEPSLATE_EBONY_ORE, "Ebony Ore");
        provider.addBlock(BlockRegistry.MALACHITE_ORE, "Malachite Ore");
        provider.addBlock(BlockRegistry.DEEPSLATE_MALACHITE_ORE, "Malachite Ore");
        provider.addBlock(BlockRegistry.MOONSTONE_ORE, "Moonstone Ore");
        provider.addBlock(BlockRegistry.DEEPSLATE_MOONSTONE_ORE, "Moonstone Ore");
        provider.addBlock(BlockRegistry.ORICHALCUM_ORE, "Orichalcum Ore");
        provider.addBlock(BlockRegistry.DEEPSLATE_ORICHALCUM_ORE, "Orichalcum Ore");
        provider.addBlock(BlockRegistry.QUICKSILVER_ORE, "Quicksilver Ore");
        provider.addBlock(BlockRegistry.DEEPSLATE_QUICKSILVER_ORE, "Quicksilver Ore");
        provider.addBlock(BlockRegistry.SILVER_ORE, "Silver Ore");
        provider.addBlock(BlockRegistry.DEEPSLATE_SILVER_ORE, "Silver Ore");

        provider.addBlock(BlockRegistry.STONE_4, "Stone");
        provider.addBlock(BlockRegistry.STONE_5, "Stone");

        provider.addBlock(BlockRegistry.TURN_STONE, "Turn Stone");
        provider.addBlock(BlockRegistry.RUNE_STONE, "Rune Stone");

        provider.addBlock(BlockRegistry.SHOUT_BLOCK, "Shout Block");

        provider.addBlock(BlockRegistry.BIRDS_NEST, "Bird's Nest");

        provider.addBlock(BlockRegistry.ALCHEMY_TABLE, "Alchemy Table");
        provider.addBlock(BlockRegistry.OVEN, "Oven");
        provider.addBlock(BlockRegistry.BLACKSMITH_FORGE, "Blacksmith Forge");
        provider.addBlock(BlockRegistry.WEAPON_RACK, "Weapon Rack");
        provider.addBlock(BlockRegistry.ARCANE_ENCHANTER, "Arcane Enchanter");

        provider.addBlock(BlockRegistry.RED_MOUNTAIN_FLOWER, "Red Mountain Flower");
        provider.addBlock(BlockRegistry.BLUE_MOUNTAIN_FLOWER, "Blue Mountain Flower");
        provider.addBlock(BlockRegistry.YELLOW_MOUNTAIN_FLOWER, "Yellow Mountain Flower");
        provider.addBlock(BlockRegistry.PURPLE_MOUNTAIN_FLOWER, "Purple Mountain Flower");
        provider.addBlock(BlockRegistry.LAVENDER, "Lavender");

        provider.addBlock(BlockRegistry.JAZBAY_GRAPE_BUSH, "Jazbay Grape Bush");
        provider.addBlock(BlockRegistry.JUNIPER_BERRY_BUSH, "Juniper Berry Bush");
        provider.addBlock(BlockRegistry.SNOWBERRY_BUSH, "Snowberry Bush");

        provider.addBlock(BlockRegistry.BLEEDING_CROWN_BLOCK, "Bleeding Crown");
        provider.addBlock(BlockRegistry.WHITE_CAP_BLOCK, "White Cap");
        provider.addBlock(BlockRegistry.BLISTERWORT_BLOCK, "Blisterwort");
        provider.addBlock(BlockRegistry.FLY_AMANITA_BLOCK, "Fly Amanita");
        provider.addBlock(BlockRegistry.CANIS_ROOT_BLOCK, "Canis Root");
        provider.addBlock(BlockRegistry.CREEP_CLUSTER_BLOCK, "Creep Cluster");

        provider.addBlock(BlockRegistry.PEARL_OYSTER_BLOCK, "Pearl Oyster");

        provider.addBlock(BlockRegistry.TOMATO_CROP, "Tomatoes");
        provider.addBlock(BlockRegistry.GARLIC_CROP, "Garlic");


        provider.addBlock(BlockRegistry.STEEL_TRAPDOOR, "Steel Trapdoor");
        provider.addBlock(BlockRegistry.DWEMER_METAL_TRAPDOOR, "Dwemer Metal Trapdoor");
        provider.addBlock(BlockRegistry.DWEMER_METAL_TORCH, "Dwemer Metal Torch");
        provider.addBlock(BlockRegistry.DWEMER_REDSTONE_TORCH, "Dwemer Redstone Torch");
        provider.addBlock(BlockRegistry.DWEMER_SOUL_TORCH, "Dwemer Soul Torch");

        provider.addBlock(BlockRegistry.DWEMER_METAL_PILLAR, "Dwemer Metal Pillar");
        provider.addBlock(BlockRegistry.DWEMER_STONE_PILLAR, "Dwemer Stone Pillar");
        provider.addBlock(BlockRegistry.DWEMER_METAL_TILES, "Dwemer Metal Tiles");
        provider.addBlock(BlockRegistry.DWEMER_METAL_TILE_SLAB, "Dwemer Metal Tile Slab");
        provider.addBlock(BlockRegistry.DWEMER_METAL_TILE_STAIRS, "Dwemer Metal Tile Stairs");
        provider.addBlock(BlockRegistry.ORNATE_DWEMER_METAL_TILES, "Ornate Dwemer Metal Tiles");
        provider.addBlock(BlockRegistry.ORNATE_DWEMER_METAL_TILE_SLAB, "Ornate Dwemer Metal Tile Slab");
        provider.addBlock(BlockRegistry.ORNATE_DWEMER_METAL_TILE_STAIRS, "Ornate Dwemer Metal Tile Stairs");
        provider.addBlock(BlockRegistry.DWEMER_METAL_BLOCK, "Dwemer Metal Block");
        provider.addBlock(BlockRegistry.DWEMER_METAL_BRICKS, "Dwemer Metal Bricks");
        provider.addBlock(BlockRegistry.DWEMER_METAL_BRICK_WALL, "Dwemer Metal Brick Wall");
        provider.addBlock(BlockRegistry.DWEMER_METAL_BRICK_SLAB, "Dwemer Metal Brick Slab");
        provider.addBlock(BlockRegistry.DWEMER_METAL_BRICK_STAIRS, "Dwemer Metal Brick Stairs");
        provider.addBlock(BlockRegistry.DWEMER_COMPARATOR, "Dwemer Comparator");
        provider.addBlock(BlockRegistry.DWEMER_DROPPER, "Dwemer Dropper");
        provider.addBlock(BlockRegistry.DWEMER_DISPENSER, "Dwemer Dispenser");
        provider.addBlock(BlockRegistry.DWEMER_STONE_BLOCK, "Dwemer Stone Block");
        provider.addBlock(BlockRegistry.DWEMER_STONE_BRICKS, "Dwemer Stone Bricks");
        provider.addBlock(BlockRegistry.DWEMER_STONE_BRICK_WALL, "Dwemer Stone Brick Wall");
        provider.addBlock(BlockRegistry.DWEMER_STONE_BRICK_SLAB, "Dwemer Stone Brick Slab");
        provider.addBlock(BlockRegistry.DWEMER_STONE_BRICK_STAIRS, "Dwemer Stone Brick Stairs");

        provider.addBlock(BlockRegistry.STEEL_CELL_DOOR, "Steel Cell Door");
        provider.addBlock(BlockRegistry.STEEL_GATE_DOOR, "Steel Gate Door");
        provider.addBlock(BlockRegistry.DWEMER_METAL_GATE, "Dwemer Metal Door");
        provider.addBlock(BlockRegistry.DWEMER_METAL_DOOR, "Dwemer Metal Gate");
        provider.addBlock(BlockRegistry.DWEMER_STONE_PRESSURE_PLATE, "Dwemer Stone Pressure Plate");

        provider.addBlock(BlockRegistry.DWEMER_GLASS, "Dwemer Glass");
        provider.addBlock(BlockRegistry.DWEMER_WINDOWED_GLASS, "Dwemer Windowed Glass");
        provider.addBlock(BlockRegistry.DWEMER_FRAMED_GLASS, "Dwemer Framed Glass");
        provider.addBlock(BlockRegistry.DWEMER_OBSERVER, "Dwemer Observer");
        provider.addBlock(BlockRegistry.DWEMER_REPEATER, "Dwemer Repeater");

        provider.addBlock(BlockRegistry.DWEMER_REDSTONE_LAMP, "Dwemer Redstone Lamp");
        provider.addBlock(BlockRegistry.DWEMER_REDSTONE_SOUL_LAMP, "Dwemer Redstone Soul Lamp");
        provider.addBlock(BlockRegistry.DWEMER_METAL_LANTERN, "Dwemer Metal Lantern");
        provider.addBlock(BlockRegistry.DWEMER_SOUL_LANTERN, "Dwemer Metal Soul Lantern");

        provider.addBlock(BlockRegistry.DWEMER_DAYLIGHT_DETECTOR, "Dwemer Daylight Detector");
        provider.addBlock(BlockRegistry.DWEMER_PISTON, "Dwemer Piston");
        provider.addBlock(BlockRegistry.DWEMER_STICKY_PISTON, "Dwemer Sticky Piston");

        provider.addBlock(BlockRegistry.STEEL_TALL_GATE, "Steel Tall Gate");
        provider.addBlock(BlockRegistry.DWEMER_METAL_TALL_GATE, "Dwemer Metal Tall Gate");
        provider.addBlock(BlockRegistry.DWEMER_METAL_TALL_DOOR, "Dwemer Metal Tall Door");

        provider.addBlock(BlockRegistry.STEEL_BARS, "Steel Bars");
        provider.addBlock(BlockRegistry.DWEMER_METAL_BARS, "Dwemer Metal Bars");
    }

    public static void addBlockStateModels(BlockStateProvider provider) {
        normalBlock(provider, BlockRegistry.CORUNDUM_ORE.get());
        normalBlock(provider, BlockRegistry.DEEPSLATE_CORUNDUM_ORE.get());
        normalBlock(provider, BlockRegistry.EBONY_ORE.get());
        normalBlock(provider, BlockRegistry.DEEPSLATE_EBONY_ORE.get());
        normalBlock(provider, BlockRegistry.MALACHITE_ORE.get());
        normalBlock(provider, BlockRegistry.DEEPSLATE_MALACHITE_ORE.get());
        normalBlock(provider, BlockRegistry.MOONSTONE_ORE.get());
        normalBlock(provider, BlockRegistry.DEEPSLATE_MOONSTONE_ORE.get());
        normalBlock(provider, BlockRegistry.ORICHALCUM_ORE.get());
        normalBlock(provider, BlockRegistry.DEEPSLATE_ORICHALCUM_ORE.get());
        normalBlock(provider, BlockRegistry.QUICKSILVER_ORE.get());
        normalBlock(provider, BlockRegistry.DEEPSLATE_QUICKSILVER_ORE.get());
        normalBlock(provider, BlockRegistry.SILVER_ORE.get());
        normalBlock(provider, BlockRegistry.DEEPSLATE_SILVER_ORE.get());

        normalBlock(provider, BlockRegistry.STONE_4.get());
        normalBlock(provider, BlockRegistry.STONE_5.get());

        normalBlock(provider, BlockRegistry.SHOUT_BLOCK.get());

        flowerBlock(provider, BlockRegistry.RED_MOUNTAIN_FLOWER.get());
        flowerBlock(provider, BlockRegistry.BLUE_MOUNTAIN_FLOWER.get());
        flowerBlock(provider, BlockRegistry.YELLOW_MOUNTAIN_FLOWER.get());
        flowerBlock(provider, BlockRegistry.PURPLE_MOUNTAIN_FLOWER.get());

        flowerBlock(provider, BlockRegistry.LAVENDER.get());

        crossBlock(provider, BlockRegistry.CANIS_ROOT_BLOCK.get());

        mushroomBlock(provider, BlockRegistry.BLEEDING_CROWN_BLOCK.get());
        mushroomBlock(provider, BlockRegistry.FLY_AMANITA_BLOCK.get());
        mushroomBlock(provider, BlockRegistry.WHITE_CAP_BLOCK.get());
        mushroomBlock(provider, BlockRegistry.BLISTERWORT_BLOCK.get());
        lilyPadBlock(provider, BlockRegistry.CREEP_CLUSTER_BLOCK.get());

        provider.getVariantBuilder(BlockRegistry.PEARL_OYSTER_BLOCK.get())
                .forAllStates(state -> {
                    boolean open = state.getValue(PearlOysterBlock.IS_OPEN);
                    boolean empty = state.getValue(PearlOysterBlock.IS_EMPTY);
                    Direction facing = state.getValue(PearlOysterBlock.FACING);

                    return ConfiguredModel.builder()
                            .modelFile(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster")))
                            .rotationY(((int) facing.toYRot() + 180) % 360)
                            .nextModel()
                            .modelFile(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster_open")))
                            .rotationY(((int) facing.toYRot() + 180) % 360)
                            .nextModel()
                            .modelFile(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster_empty")))
                            .rotationY(((int) facing.toYRot() + 180) % 360)
                            .build();
                });

        provider.simpleBlockItem(BlockRegistry.PEARL_OYSTER_BLOCK.get(), provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster")));

//        provider.simpleBlockItem(BlockRegistry.TURN_STONE.get(), provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/turn_stone")));

        provider.horizontalBlock(BlockRegistry.ALCHEMY_TABLE.get(), state -> provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/alchemy_table")));
        provider.simpleBlockItem(BlockRegistry.ALCHEMY_TABLE.get(), provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/alchemy_table")));

        provider.horizontalBlock(BlockRegistry.WEAPON_RACK.get(), state -> provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/weapon_rack")));
        provider.simpleBlockItem(BlockRegistry.WEAPON_RACK.get(), provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/weapon_rack")));

        provider.simpleBlock(BlockRegistry.BIRDS_NEST.get(), provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/birds_nest")));
        provider.simpleBlockItem(BlockRegistry.BIRDS_NEST.get(), provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/birds_nest")));

        provider.horizontalBlock(BlockRegistry.OVEN.get(), state -> provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/oven")));
        provider.simpleBlockItem(BlockRegistry.OVEN.get(), provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/oven")));

        provider.horizontalBlock(BlockRegistry.BLACKSMITH_FORGE.get(), state -> provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/blacksmith_forge")));
        provider.simpleBlockItem(BlockRegistry.BLACKSMITH_FORGE.get(), provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/blacksmith_forge")));

        provider.horizontalBlock(BlockRegistry.ARCANE_ENCHANTER.get(), state -> provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/arcane_enchanter")));
        provider.simpleBlockItem(BlockRegistry.ARCANE_ENCHANTER.get(), provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/arcane_enchanter")));

        threeStageCrop(provider, BlockRegistry.ASH_YAM_CROP.get());

        threeStageBush(provider, BlockRegistry.JAZBAY_GRAPE_BUSH.get());
        threeStageBush(provider, BlockRegistry.JUNIPER_BERRY_BUSH.get());
        threeStageBush(provider, BlockRegistry.SNOWBERRY_BUSH.get());

//        provider.horizontalBlock(BlockRegistry.TURN_STONE.get(), null);
        turnStoneBlock(provider, BlockRegistry.TURN_STONE.get());
        runeStoneBlock(provider, BlockRegistry.RUNE_STONE.get());
        provider.simpleBlockItem(BlockRegistry.RUNE_STONE.get(), provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/rune_stone")));


        provider.trapdoorBlockWithRenderType(BlockRegistry.STEEL_TRAPDOOR.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/steel_trapdoor"), true, ResourceLocation.tryParse("cutout"));
        provider.simpleBlockItem(BlockRegistry.STEEL_TRAPDOOR.get(), provider.models().getExistingFile(provider.modLoc("steel_trapdoor_bottom")));
        provider.doorBlockWithRenderType(BlockRegistry.STEEL_CELL_DOOR.get(),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/steel_cell_door_bottom"),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/steel_cell_door_top"), ResourceLocation.tryParse("cutout"));
        provider.itemModels().basicItem(BlockRegistry.STEEL_CELL_DOOR.get().asItem());
        provider.doorBlockWithRenderType(BlockRegistry.STEEL_GATE_DOOR.get(),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/steel_gate_door_bottom"),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/steel_gate_door_top"), ResourceLocation.tryParse("cutout"));
        provider.itemModels().basicItem(BlockRegistry.STEEL_GATE_DOOR.get().asItem());
        tallDoorBlock(provider, BlockRegistry.STEEL_TALL_GATE.get(), "block/steel_tall_gate");


        provider.trapdoorBlockWithRenderType(BlockRegistry.DWEMER_METAL_TRAPDOOR.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_trapdoor"), true, ResourceLocation.tryParse("cutout"));
        provider.simpleBlockItem(BlockRegistry.DWEMER_METAL_TRAPDOOR.get(), provider.models().getExistingFile(provider.modLoc("dwemer_metal_trapdoor_bottom")));

        torchBlock(provider, BlockRegistry.DWEMER_METAL_TORCH.get());
        wallTorchBlock(provider, BlockRegistry.DWEMER_METAL_TORCH.get(), BlockRegistry.DWEMER_METAL_WALL_TORCH.get());
        torchBlock(provider, BlockRegistry.DWEMER_SOUL_TORCH.get());
        wallTorchBlock(provider, BlockRegistry.DWEMER_SOUL_TORCH.get(), BlockRegistry.DWEMER_SOUL_WALL_TORCH.get());
        poweredTorchBlock(provider, BlockRegistry.DWEMER_REDSTONE_TORCH.get());
        poweredWallTorchBlock(provider, BlockRegistry.DWEMER_REDSTONE_TORCH.get(), BlockRegistry.DWEMER_REDSTONE_WALL_TORCH.get());

        provider.doorBlockWithRenderType(BlockRegistry.DWEMER_METAL_DOOR.get(),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_door_bottom"),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_door_top"), ResourceLocation.tryParse("cutout"));
        provider.itemModels().basicItem(BlockRegistry.DWEMER_METAL_DOOR.get().asItem());
        tallDoorBlock(provider, BlockRegistry.DWEMER_METAL_TALL_DOOR.get(), "block/dwemer_metal_tall_door");
        provider.doorBlockWithRenderType(BlockRegistry.DWEMER_METAL_GATE.get(),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_gate_bottom"),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_gate_top"), ResourceLocation.tryParse("cutout"));
        provider.itemModels().basicItem(BlockRegistry.DWEMER_METAL_GATE.get().asItem());
        tallDoorBlock(provider, BlockRegistry.DWEMER_METAL_TALL_GATE.get(), "block/dwemer_metal_tall_gate");
        provider.axisBlock(BlockRegistry.DWEMER_METAL_PILLAR.get());
        provider.simpleBlockItem(BlockRegistry.DWEMER_METAL_PILLAR.get(), provider.models().getExistingFile(provider.modLoc("dwemer_metal_pillar")));
        provider.axisBlock(BlockRegistry.DWEMER_STONE_PILLAR.get());
        provider.simpleBlockItem(BlockRegistry.DWEMER_STONE_PILLAR.get(), provider.models().getExistingFile(provider.modLoc("dwemer_stone_pillar")));

        pressurePlateBlock(provider, BlockRegistry.DWEMER_STONE_PRESSURE_PLATE.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_stone_pressure_plate"));
        provider.simpleBlockItem(BlockRegistry.DWEMER_STONE_PRESSURE_PLATE.get(), provider.models().getExistingFile(provider.modLoc("dwemer_stone_pressure_plate")));

        normalBlock(provider, BlockRegistry.DWEMER_METAL_TILES.get());
        normalBlock(provider, BlockRegistry.ORNATE_DWEMER_METAL_TILES.get());
        normalBlock(provider, BlockRegistry.DWEMER_METAL_BLOCK.get());
        normalBlock(provider, BlockRegistry.DWEMER_METAL_BRICKS.get());
        normalBlock(provider, BlockRegistry.DWEMER_STONE_BLOCK.get());
        normalBlock(provider, BlockRegistry.DWEMER_STONE_BRICKS.get());

        glassBlock(provider, BlockRegistry.DWEMER_GLASS.get());
        glassBlock(provider, BlockRegistry.DWEMER_FRAMED_GLASS.get());
        glassBlock(provider, BlockRegistry.DWEMER_WINDOWED_GLASS.get());

        provider.simpleBlockItem(BlockRegistry.DWEMER_OBSERVER.get(), provider.models().getExistingFile(provider.modLoc("dwemer_observer")));
        provider.simpleBlockItem(BlockRegistry.DWEMER_COMPARATOR.get(), provider.models().getExistingFile(provider.modLoc("dwemer_comparator")));
        provider.simpleBlockItem(BlockRegistry.DWEMER_DROPPER.get(), provider.models().getExistingFile(provider.modLoc("dwemer_dropper")));
        provider.simpleBlockItem(BlockRegistry.DWEMER_DISPENSER.get(), provider.models().getExistingFile(provider.modLoc("dwemer_dispenser")));
        provider.simpleBlockItem(BlockRegistry.DWEMER_PISTON.get(), provider.models().getExistingFile(provider.modLoc("dwemer_piston_inventory")));
        provider.simpleBlockItem(BlockRegistry.DWEMER_STICKY_PISTON.get(), provider.models().getExistingFile(provider.modLoc("dwemer_sticky_piston_inventory")));
//        provider.simpleBlockItem(BlockRegistry.DWEMER_REPEATER.get(), provider.models().getExistingFile(provider.modLoc("dwemer_repeater_1tick")));
        daylightDetector(provider, BlockRegistry.DWEMER_DAYLIGHT_DETECTOR.get());

        lampBlock(provider, BlockRegistry.DWEMER_REDSTONE_LAMP.get());
        lampBlock(provider, BlockRegistry.DWEMER_REDSTONE_SOUL_LAMP.get());
        lantern(provider, BlockRegistry.DWEMER_METAL_LANTERN.get());
        lantern(provider, BlockRegistry.DWEMER_SOUL_LANTERN.get());

        stairsBlock(provider, BlockRegistry.DWEMER_METAL_BRICK_STAIRS.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_bricks"));
        provider.slabBlock(BlockRegistry.DWEMER_METAL_BRICK_SLAB.get(),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_bricks"),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_bricks"));
        provider.wallBlockWithRenderType(BlockRegistry.DWEMER_METAL_BRICK_WALL.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_bricks"), "cutout");
        provider.simpleBlockItem(BlockRegistry.DWEMER_METAL_BRICK_SLAB.get(), provider.models().getExistingFile(provider.modLoc("dwemer_metal_brick_slab")));
//        provider.simpleBlockItem(BlockRegistry.DWEMER_METAL_BRICK_WALL.get(), provider.models().getExistingFile(provider.modLoc("dwemer_metal_brick_wall_side")));
        provider.simpleBlockItem(BlockRegistry.DWEMER_METAL_BRICK_STAIRS.get(), provider.models().getExistingFile(provider.modLoc("dwemer_metal_brick_stairs")));

        stairsBlock(provider, BlockRegistry.DWEMER_STONE_BRICK_STAIRS.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_stone_bricks"));
        provider.slabBlock(BlockRegistry.DWEMER_STONE_BRICK_SLAB.get(),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_stone_bricks"),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_stone_bricks"));
        provider.wallBlockWithRenderType(BlockRegistry.DWEMER_STONE_BRICK_WALL.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_stone_bricks"), "cutout");
        provider.simpleBlockItem(BlockRegistry.DWEMER_STONE_BRICK_SLAB.get(), provider.models().getExistingFile(provider.modLoc("dwemer_stone_brick_slab")));
//        provider.simpleBlockItem(BlockRegistry.DWEMER_METAL_BRICK_WALL.get(), provider.models().getExistingFile(provider.modLoc("dwemer_stone_brick_wall_side")));
        provider.simpleBlockItem(BlockRegistry.DWEMER_STONE_BRICK_STAIRS.get(), provider.models().getExistingFile(provider.modLoc("dwemer_stone_brick_stairs")));

        stairsBlock(provider, BlockRegistry.DWEMER_METAL_TILE_STAIRS.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_tiles"));
        provider.slabBlock(BlockRegistry.DWEMER_METAL_TILE_SLAB.get(),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_tiles"),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_tiles"));
        provider.simpleBlockItem(BlockRegistry.DWEMER_METAL_TILE_SLAB.get(), provider.models().getExistingFile(provider.modLoc("dwemer_metal_tile_slab")));
        provider.simpleBlockItem(BlockRegistry.DWEMER_METAL_TILE_STAIRS.get(), provider.models().getExistingFile(provider.modLoc("dwemer_metal_tile_stairs")));

        stairsBlock(provider, BlockRegistry.ORNATE_DWEMER_METAL_TILE_STAIRS.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/ornate_dwemer_metal_tiles"));
        provider.slabBlock(BlockRegistry.ORNATE_DWEMER_METAL_TILE_SLAB.get(),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/ornate_dwemer_metal_tiles"),
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/ornate_dwemer_metal_tiles"));
        provider.simpleBlockItem(BlockRegistry.ORNATE_DWEMER_METAL_TILE_SLAB.get(), provider.models().getExistingFile(provider.modLoc("ornate_dwemer_metal_tile_slab")));
        provider.simpleBlockItem(BlockRegistry.ORNATE_DWEMER_METAL_TILE_STAIRS.get(), provider.models().getExistingFile(provider.modLoc("ornate_dwemer_metal_tile_stairs")));
    }

    public static void addBlockItemModels(ItemModelProvider provider) {
        provider.basicItem(BlockRegistry.CANIS_ROOT.get().asItem());
        provider.basicItem(BlockRegistry.BLEEDING_CROWN.get().asItem());
        provider.basicItem(BlockRegistry.BLISTERWORT.get().asItem());
        provider.basicItem(BlockRegistry.FLY_AMANITA.get().asItem());
        provider.basicItem(BlockRegistry.WHITE_CAP.get().asItem());
        provider.basicItem(BlockRegistry.CREEP_CLUSTER.get().asItem());
    }

    public static void tallDoorBlock(BlockStateProvider provider, Block block, String baseName) {
        internalTallDoorBlock(provider, (TallDoorBlock)block, baseName, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "" + baseName + "_bottom"), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "" + baseName + "_middle"), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "" + baseName + "_top"));
    }

    private static void internalTallDoorBlock(BlockStateProvider provider, TallDoorBlock block, String baseName, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        ModelFile bottomLeft = provider.models().withExistingParent(baseName + "_bottom_left", ":" + ModelProvider.BLOCK_FOLDER + "/door_bottom_left").texture("bottom", bottom).texture("middle", middle).texture("top", top).renderType("cutout");
        ModelFile bottomLeftOpen = provider.models().withExistingParent(baseName + "_bottom_left_open", "" + ModelProvider.BLOCK_FOLDER + "/door_bottom_left_open").texture("bottom", bottom).texture("middle", middle).texture("top", top).renderType("cutout");
        ModelFile bottomRight = provider.models().withExistingParent(baseName + "_bottom_right", "" + ModelProvider.BLOCK_FOLDER + "/door_bottom_right").texture("bottom", bottom).texture("middle", middle).texture("top", top).renderType("cutout");
        ModelFile bottomRightOpen = provider.models().withExistingParent(baseName + "_bottom_right_open", "" + ModelProvider.BLOCK_FOLDER + "/door_bottom_right_open").texture("bottom", bottom).texture("middle", middle).texture("top",top).renderType("cutout");
        ModelFile middleLeft = provider.models().withExistingParent(baseName + "_middle_left", Constants.MODID + ":" + ModelProvider.BLOCK_FOLDER + "/door_middle_left").texture("bottom", middle).texture("middle", middle).texture("top", middle).renderType("cutout");
        ModelFile middleLeftOpen = provider.models().withExistingParent(baseName + "_middle_left_open", Constants.MODID + ":" + ModelProvider.BLOCK_FOLDER + "/door_middle_left_open").texture("bottom", middle).texture("middle", middle).texture("top", middle).renderType("cutout");
        ModelFile middleRight = provider.models().withExistingParent(baseName + "_middle_right", Constants.MODID + ":" + ModelProvider.BLOCK_FOLDER + "/door_middle_right").texture("bottom", middle).texture("middle", middle).texture("top", middle).renderType("cutout");
        ModelFile middleRightOpen = provider.models().withExistingParent(baseName + "_middle_right_open", Constants.MODID + ":" + ModelProvider.BLOCK_FOLDER + "/door_middle_right_open").texture("bottom", middle).texture("middle", middle).texture("top", middle).renderType("cutout");
        ModelFile topLeft = provider.models().withExistingParent(baseName + "_top_left", "" + ModelProvider.BLOCK_FOLDER + "/door_top_left").texture("bottom", bottom).texture("middle", middle).texture("top", top).renderType("cutout");
        ModelFile topLeftOpen = provider.models().withExistingParent(baseName + "_top_left_open", "" + ModelProvider.BLOCK_FOLDER + "/door_top_left_open").texture("bottom", bottom).texture("middle", middle).texture("top", top).renderType("cutout");
        ModelFile topRight = provider.models().withExistingParent(baseName + "_top_right", "" + ModelProvider.BLOCK_FOLDER + "/door_top_right").texture("bottom", bottom).texture("middle", middle).texture("top", top).renderType("cutout");
        ModelFile topRightOpen = provider.models().withExistingParent(baseName + "_top_right_open", "" + ModelProvider.BLOCK_FOLDER + "/door_top_right_open").texture("bottom", bottom).texture("middle", middle).texture("top",top).renderType("cutout");
        tallDoorBlock(provider, block, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, middleLeft, middleLeftOpen, middleRight, middleRightOpen, topLeft, topLeftOpen, topRight, topRightOpen);
    }

    public static void tallDoorBlock(BlockStateProvider provider, TallDoorBlock block, ModelFile bottomLeft, ModelFile bottomLeftOpen, ModelFile bottomRight, ModelFile bottomRightOpen, ModelFile middleLeft, ModelFile middleLeftOpen, ModelFile middleRight, ModelFile middleRightOpen, ModelFile topLeft, ModelFile topLeftOpen, ModelFile topRight, ModelFile topRightOpen) {
        provider.getVariantBuilder(block).forAllStatesExcept(state -> {
            int yRot = ((int) state.getValue(TallDoorBlock.FACING).toYRot()) + 90;
            TripleBlockPart third = state.getValue(TallDoorBlock.THIRD);
            boolean right = state.getValue(TallDoorBlock.HINGE) == DoorHingeSide.RIGHT;
            boolean open = state.getValue(TallDoorBlock.OPEN);
            if (open) {
                yRot += 90;
            }
            if (right && open) {
                yRot += 180;
            }
            yRot %= 360;

            ModelFile model = null;
            switch(third) {
                case LOWER:
                default:
                    if (right && open) {
                        model = bottomRightOpen;
                    } else if (!right && open) {
                        model = bottomLeftOpen;
                    }
                    if (right && !open) {
                        model = bottomRight;
                    } else if (!right && !open) {
                        model = bottomLeft;
                    }
                    break;
                case MIDDLE:
                    if (right && open) {
                        model = middleRightOpen;
                    } else if (!right && open) {
                        model = middleLeftOpen;
                    }
                    if (right && !open) {
                        model = middleRight;
                    } else if (!right && !open) {
                        model = middleLeft;
                    }
                    break;
                case UPPER:
                    if (right && open) {
                        model = topRightOpen;
                    } else if (!right && open) {
                        model = topLeftOpen;
                    }
                    if (right && !open) {
                        model = topRight;
                    } else if (!right && !open) {
                        model = topLeft;
                    }
                    break;
            }
            return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
        }, TallDoorBlock.POWERED, TallDoorBlock.WATERLOGGED);
    }

    public static void lampBlock(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = key(block);
        String path = blockKey.getPath();

        ModelFile unlit = provider.models().getBuilder(key(block) + "")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/cube_all")))
                .texture("all", provider.modLoc("block/" + name(block)))
                .texture("particle", provider.modLoc("block/" + name(block)));
        ModelFile lit = provider.models().getBuilder(key(block).toString() + "_on")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/cube_all")))
                .texture("all", provider.modLoc("block/" + name(block) + "_on"))
                .texture("particle", provider.modLoc("block/" + name(block) + "_on"));

        provider.getVariantBuilder(block).forAllStatesExcept(state ->
        {
            ModelFile mf = state.getValue(BlockStateProperties.LIT) ? lit : unlit;
            return ConfiguredModel.builder()
                    .modelFile(mf)
                    .build();
        });

        provider.simpleBlockItem(block, provider.models().getExistingFile(provider.modLoc("block/" + path)));
    }

    public static void daylightDetector(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = key(block);
        String path = blockKey.getPath();

        ModelFile normal = provider.models().getBuilder(key(block) + "")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/template_daylight_detector")))
                .texture("side", provider.modLoc("block/" + name(block) + "_side"))
                .texture("top", provider.modLoc("block/" + name(block) + "_top"))
                .texture("particle", provider.modLoc("block/" + name(block)) + "_top")
                .renderType("cutout");
        ModelFile inverted = provider.models().getBuilder(key(block).toString() + "_inverted")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/template_daylight_detector")))
                .texture("side", provider.modLoc("block/" + name(block) + "_side"))
                .texture("top", provider.modLoc("block/" + name(block) + "_inverted_top"))
                .texture("particle", provider.modLoc("block/" + name(block) + "_inverted_top"))
                .renderType("cutout");

        provider.getVariantBuilder(block).forAllStatesExcept(state ->
        {
            ModelFile mf = state.getValue(BlockStateProperties.INVERTED) ? inverted : normal;
            return ConfiguredModel.builder()
                    .modelFile(mf)
                    .build();
        });

        provider.simpleBlockItem(block, provider.models().getExistingFile(provider.modLoc("block/" + path)));
    }

    public static void lantern(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = key(block);
        String path = blockKey.getPath();

        ModelFile normal = provider.models().getBuilder(key(block) + "")
                .parent(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/template_tall_lantern")))
                .texture("lantern", provider.modLoc("block/" + name(block)))
                .renderType("cutout");
        ModelFile hanging = provider.models().getBuilder(key(block).toString() + "_hanging")
                .parent(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/template_tall_hanging_lantern")))
                .texture("lantern", provider.modLoc("block/" + name(block)))
                .renderType("cutout");

        provider.getVariantBuilder(block).forAllStatesExcept(state ->
        {
            ModelFile mf = state.getValue(BlockStateProperties.HANGING) ? hanging : normal;
            return ConfiguredModel.builder()
                    .modelFile(mf)
                    .build();
        });

        provider.simpleBlockItem(block, provider.models().getExistingFile(provider.modLoc("block/" + path)));
    }

    public static void normalBlock(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        provider.simpleBlock(block, provider.models().cubeAll(path, provider.modLoc("block/" + path)));
        provider.simpleBlockItem(block, provider.models().getExistingFile(provider.modLoc("block/" + path)));
    }

    public static void glassBlock(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        provider.simpleBlock(block, provider.models().cubeAll(path, provider.modLoc("block/" + path)).renderType("translucent"));
        provider.simpleBlockItem(block, provider.models().getExistingFile(provider.modLoc("block/" + path)));
    }

    public static void pressurePlateBlock(BlockStateProvider provider, SkyrimcraftPressurePlateBlock block, ResourceLocation texture) {
        ModelFile pressurePlate = provider.models().pressurePlate(name(block), texture);
        ModelFile pressurePlateDown = provider.models().pressurePlateDown(name(block) + "_down", texture);
        pressurePlateBlock(provider, block, pressurePlate, pressurePlateDown);
    }
    public static void pressurePlateBlock(BlockStateProvider provider, SkyrimcraftPressurePlateBlock block, ModelFile pressurePlate, ModelFile pressurePlateDown) {
        provider.getVariantBuilder(block).partialState().with(PressurePlateBlock.POWERED, true).addModels(new ConfiguredModel[]{new ConfiguredModel(pressurePlateDown)}).partialState().with(PressurePlateBlock.POWERED, false).addModels(new ConfiguredModel[]{new ConfiguredModel(pressurePlate)});
    }

    public static void torchBlock(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = key(block);
        String path = blockKey.getPath();
        provider.simpleBlock(block, provider.models().getBuilder(blockKey.toString()).parent(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ResourceLocation.DEFAULT_NAMESPACE, "block/template_torch")))
                .texture("torch", provider.modLoc("block/"+path))
                .renderType("cutout"));
        provider.simpleBlockItem(block, provider.models().getExistingFile(provider.modLoc("block/" + path)));
    }

    protected static ModelFile wallTorchModel(BlockStateProvider provider, Block tblock, Block block) {
        ResourceLocation blockKey = key(block);
        ResourceLocation tblockKey = key(tblock);
        String tpath = tblockKey.getPath();
        String path = blockKey.getPath();
        return provider.models().getBuilder(blockKey.toString()).parent(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ResourceLocation.DEFAULT_NAMESPACE, "block/template_torch_wall")))
                .texture("torch", provider.modLoc("block/"+tpath))
                .renderType("cutout");
    }

    public static void wallTorchBlock(BlockStateProvider provider, TorchBlock tblock, WallTorchBlock block) {
        ResourceLocation blockKey = key(block);
        String path = blockKey.getPath();

        provider.horizontalBlock(block, wallTorchModel(provider, tblock, block));
        provider.simpleBlockItem(block, provider.models().getExistingFile(provider.modLoc("block/" + path)));
    }

    public static void poweredWallTorchBlock(BlockStateProvider provider, RedstoneTorchBlock tblock, RedstoneWallTorchBlock block) {
        ResourceLocation blockKey = key(block);
        ResourceLocation tblockKey = key(tblock);
        String tpath = tblockKey.getPath();
        String path = blockKey.getPath();

        ModelFile unlit = provider.models().getBuilder(key(block) + "_off")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/template_torch_wall")))
                .texture("torch", provider.modLoc("block/" + name(tblock) + "_off"))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/" + name(tblock) + "_off"));
        ModelFile lit = provider.models().getBuilder(key(block).toString())
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/template_torch_wall")))
                .texture("torch", provider.modLoc("block/" + name(tblock)))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/" + name(tblock)));

        provider.horizontalBlock(block, (state) -> {
            return state.getValue(BlockStateProperties.LIT) ? lit : unlit;
        }, 180);

        provider.simpleBlockItem(block, provider.models().getExistingFile(provider.modLoc("block/" + path)));
    }

    public static void poweredTorchBlock(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = key(block);
        String path = blockKey.getPath();

        ModelFile unlit = provider.models().getBuilder(key(block) + "_off")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/template_torch")))
                .texture("torch", provider.modLoc("block/" + name(block) + "_off"))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/" + name(block) + "_off"));
        ModelFile lit = provider.models().getBuilder(key(block).toString())
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/template_torch")))
                .texture("torch", provider.modLoc("block/" + name(block)))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/" + name(block)));

        provider.getVariantBuilder(block).forAllStatesExcept(state ->
        {
            ModelFile mf = state.getValue(BlockStateProperties.LIT) ? lit : unlit;
            return ConfiguredModel.builder()
                    .modelFile(mf)
                    .build();
        });

        provider.simpleBlockItem(block, provider.models().getExistingFile(provider.modLoc("block/" + path)));
    }

    public static void turnStoneBlock(BlockStateProvider provider, TurnStoneBlock block) {
        ResourceLocation blockKey = key(block);
        String path = blockKey.getPath();

        ModelFile model = provider.models().getBuilder(provider.modLoc("block/" + path).getPath())
                .texture("particle", provider.modLoc("block/"+path));
        provider.horizontalBlock(block, model);
    }

    public static void runeStoneBlock(BlockStateProvider provider, RuneStoneBlock block) {
        ResourceLocation blockKey = key(block);
        String path = blockKey.getPath();

        ModelFile powered = provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/rune_stone_powered"));
        ModelFile not_powered = provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/rune_stone"));

        provider.horizontalBlock(block, (state) -> {
            return state.getValue(RuneStoneBlock.POWERED) ? powered : not_powered;
        }, 180);
    }

    public static void mushroomBlock(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();

        provider.simpleBlock(block, provider.models().getBuilder(blockKey.toString())
                .parent(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/triple_mushroom_generic")))
                .texture("0", provider.modLoc("block/"+path))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/"+path))
        );
    }

    public static void crossBlock(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();

        provider.simpleBlock(block, provider.models().getBuilder(blockKey.toString()).parent(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ResourceLocation.DEFAULT_NAMESPACE, "block/cross")))
                .texture("cross", provider.modLoc("block/"+path))
                .renderType("cutout"));
    }

    public static void lilyPadBlock(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();

        provider.simpleBlock(block, provider.models().getBuilder(blockKey.toString())
                .parent(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ResourceLocation.DEFAULT_NAMESPACE, "block/lily_pad")))
                .texture("texture", provider.modLoc("block/"+path))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/"+path)));
    }

    public static void flowerBlock(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();

        provider.simpleBlock(block, provider.models().getBuilder(blockKey.toString()).parent(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(ResourceLocation.DEFAULT_NAMESPACE, "block/cross"))).texture("cross", provider.modLoc("block/"+path)).renderType("cutout"));
        provider.simpleBlockItem(block, provider.models().getExistingFile(provider.modLoc("block/" + path)));
    }

    public static void crop(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();

        provider.simpleBlock(block, provider.models().crop(path, provider.modLoc("block/" + path)).renderType("cutout"));
    }

    public static void threeStageCrop(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = key(block);
        String path = blockKey.getPath();

        ModelFile stage0 = provider.models().getBuilder(key(block) + "_stage0")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/crop")))
                .texture("crop", provider.modLoc("block/" + name(block) + "_stage0"))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/" + name(block) + "_stage0"));
        ModelFile stage1 = provider.models().getBuilder(name(block) + "_stage1")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/crop")))
                .texture("crop", provider.modLoc("block/" + name(block) + "_stage1"))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/" + name(block) + "_stage1"));
        ModelFile stage2 = provider.models().getBuilder(key(block) + "_stage2")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/crop")))
                .texture("crop", provider.modLoc("block/" + name(block) + "_stage2"))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/" + name(block) + "_stage2"));
        ModelFile stage3 = provider.models().getBuilder(key(block) + "_stage3")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/crop")))
                .texture("crop", provider.modLoc("block/" + name(block) + "_stage3"))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/" + name(block) + "_stage3"));

        provider.getVariantBuilder(block).forAllStatesExcept(state ->
        {
            ModelFile mf = switch (state.getValue(CropBlock.AGE)) {
                case 0, 1 -> stage0;
                case 2, 3 -> stage1;
                case 4, 5, 6 -> stage2;
                case 7 -> stage3;
                default -> stage0;
            };
            return ConfiguredModel.builder()
                    .modelFile(mf)
                    .build();
        });
    }

    public static void threeStageBush(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = key(block);
        String path = blockKey.getPath();

        ModelFile stage0 = provider.models().getBuilder(key(block) + "_stage0")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/cross")))
                .texture("cross", provider.modLoc("block/" + name(block) + "_stage0"))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/" + name(block) + "_stage0"));
        ModelFile stage1 = provider.models().getBuilder(name(block) + "_stage1")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/cross")))
                .texture("cross", provider.modLoc("block/" + name(block) + "_stage1"))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/" + name(block) + "_stage1"));
        ModelFile stage2 = provider.models().getBuilder(key(block) + "_stage2")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/cross")))
                .texture("cross", provider.modLoc("block/" + name(block) + "_stage2"))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/" + name(block) + "_stage2"));
        ModelFile stage3 = provider.models().getBuilder(key(block) + "_stage3")
                .parent(provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/cross")))
                .texture("cross", provider.modLoc("block/" + name(block) + "_stage3"))
                .renderType("cutout")
                .texture("particle", provider.modLoc("block/" + name(block) + "_stage3"));

        provider.getVariantBuilder(block).forAllStatesExcept(state ->
        {
            ModelFile mf = switch (state.getValue(BlockStateProperties.AGE_3)) {
                case 0 -> stage0;
                case 1 -> stage1;
                case 2 -> stage2;
                case 3 -> stage3;
                default -> stage0;
            };
            return ConfiguredModel.builder()
                    .modelFile(mf)
                    .build();
        });
    }

    public static void stairsBlock(BlockStateProvider provider, SkyrimStairBlock block, ResourceLocation texture) {
        stairsBlock(provider, block, texture, texture, texture);
    }

    public static void stairsBlock(BlockStateProvider provider, SkyrimStairBlock block, String name, ResourceLocation texture) {
        stairsBlock(provider, block, name, texture, texture, texture);
    }

    public static void stairsBlock(BlockStateProvider provider, SkyrimStairBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        stairsBlockInternal(provider, block, key(block).toString(), side, bottom, top);
    }

    public static void stairsBlock(BlockStateProvider provider, SkyrimStairBlock block, String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        stairsBlockInternal(provider, block, name + "_stairs", side, bottom, top);
    }

    public static void stairsBlockWithRenderType(BlockStateProvider provider, SkyrimStairBlock block, ResourceLocation texture, String renderType) {
        stairsBlockWithRenderType(provider, block, texture, texture, texture, renderType);
    }

    public static void stairsBlockWithRenderType(BlockStateProvider provider, SkyrimStairBlock block, String name, ResourceLocation texture, String renderType) {
        stairsBlockWithRenderType(provider, block, name, texture, texture, texture, renderType);
    }

    public static void stairsBlockWithRenderType(BlockStateProvider provider, SkyrimStairBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, String renderType) {
        stairsBlockInternalWithRenderType(provider, block, key(block).toString(), side, bottom, top, ResourceLocation.tryParse(renderType));
    }

    public static void stairsBlockWithRenderType(BlockStateProvider provider, SkyrimStairBlock block, String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, String renderType) {
        stairsBlockInternalWithRenderType(provider, block, name + "_stairs", side, bottom, top, ResourceLocation.tryParse(renderType));
    }

    public static void stairsBlockWithRenderType(BlockStateProvider provider, SkyrimStairBlock block, ResourceLocation texture, ResourceLocation renderType) {
        stairsBlockWithRenderType(provider, block, texture, texture, texture, renderType);
    }

    public static void stairsBlockWithRenderType(BlockStateProvider provider, SkyrimStairBlock block, String name, ResourceLocation texture, ResourceLocation renderType) {
        stairsBlockWithRenderType(provider, block, name, texture, texture, texture, renderType);
    }

    public static void stairsBlockWithRenderType(BlockStateProvider provider, SkyrimStairBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation renderType) {
        stairsBlockInternalWithRenderType(provider, block, key(block).toString(), side, bottom, top, renderType);
    }

    public static void stairsBlockWithRenderType(BlockStateProvider provider, SkyrimStairBlock block, String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation renderType) {
        stairsBlockInternalWithRenderType(provider, block, name + "_stairs", side, bottom, top, renderType);
    }

    private static void stairsBlockInternal(BlockStateProvider provider, SkyrimStairBlock block, String baseName, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        ModelFile stairs = provider.models().stairs(baseName, side, bottom, top);
        ModelFile stairsInner = provider.models().stairsInner(baseName + "_inner", side, bottom, top);
        ModelFile stairsOuter = provider.models().stairsOuter(baseName + "_outer", side, bottom, top);
        stairsBlock(provider, block, (ModelFile)stairs, (ModelFile)stairsInner, (ModelFile)stairsOuter);
    }

    private static void stairsBlockInternalWithRenderType(BlockStateProvider provider, SkyrimStairBlock block, String baseName, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, ResourceLocation renderType) {
        ModelFile stairs = ((BlockModelBuilder)provider.models().stairs(baseName, side, bottom, top)).renderType(renderType);
        ModelFile stairsInner = ((BlockModelBuilder)provider.models().stairsInner(baseName + "_inner", side, bottom, top)).renderType(renderType);
        ModelFile stairsOuter = ((BlockModelBuilder)provider.models().stairsOuter(baseName + "_outer", side, bottom, top)).renderType(renderType);
        stairsBlock(provider, block, (ModelFile)stairs, (ModelFile)stairsInner, (ModelFile)stairsOuter);
    }

    public static void stairsBlock(BlockStateProvider provider, SkyrimStairBlock block, ModelFile stairs, ModelFile stairsInner, ModelFile stairsOuter) {
        provider.getVariantBuilder(block).forAllStatesExcept((state) -> {
            Direction facing = (Direction)state.getValue(SkyrimStairBlock.FACING);
            Half half = (Half)state.getValue(SkyrimStairBlock.HALF);
            StairsShape shape = (StairsShape)state.getValue(SkyrimStairBlock.SHAPE);
            int yRot = (int)facing.getClockWise().toYRot();
            if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
                yRot += 270;
            }

            if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
                yRot += 90;
            }

            yRot %= 360;
            boolean uvlock = yRot != 0 || half == Half.TOP;
            return ConfiguredModel.builder().modelFile(shape == StairsShape.STRAIGHT ? stairs : (shape != StairsShape.INNER_LEFT && shape != StairsShape.INNER_RIGHT ? stairsOuter : stairsInner)).rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock).build();
        }, new Property[]{SkyrimStairBlock.WATERLOGGED});
    }

    private static String name(Block block) {
        return key(block).getPath();
    }

    private static ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}