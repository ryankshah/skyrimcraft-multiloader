package com.ryankshah.skyrimcraft.data.block;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.PearlOysterBlock;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
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

        provider.addBlock(BlockRegistry.STONE_BRICK_1, "Stone Bricks");
        provider.addBlock(BlockRegistry.STONE_BRICK_2, "Stone Bricks");
        provider.addBlock(BlockRegistry.STONE_BRICK_3, "Stone Bricks");
        provider.addBlock(BlockRegistry.STONE_BRICK_4, "Stone Bricks");
        provider.addBlock(BlockRegistry.STONE_BRICK_5, "Stone Bricks");
        provider.addBlock(BlockRegistry.COBBLESTONE_1, "Cobblestone");
        provider.addBlock(BlockRegistry.COBBLESTONE_2, "Cobblestone");
        provider.addBlock(BlockRegistry.SNOWY_PLANKS_1, "Snowy Planks");
        provider.addBlock(BlockRegistry.PLANKS_1, "Planks");
        provider.addBlock(BlockRegistry.PLANKS_2, "Planks");
        provider.addBlock(BlockRegistry.PLANKS_3, "Planks");
        provider.addBlock(BlockRegistry.PLANKS_4, "Planks");

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

        provider.addBlock(BlockRegistry.BLEEDING_CROWN_BLOCK, "Bleeding Crown");
        provider.addBlock(BlockRegistry.WHITE_CAP_BLOCK, "White Cap");
        provider.addBlock(BlockRegistry.BLISTERWORT_BLOCK, "Blisterwort");
        provider.addBlock(BlockRegistry.FLY_AMANITA_BLOCK, "Fly Amanita");
        provider.addBlock(BlockRegistry.CANIS_ROOT_BLOCK, "Canis Root");
        provider.addBlock(BlockRegistry.CREEP_CLUSTER_BLOCK, "Creep Cluster");

        provider.addBlock(BlockRegistry.PEARL_OYSTER_BLOCK, "Pearl Oyster");

        provider.addBlock(BlockRegistry.TOMATO_CROP, "Tomatoes");
        provider.addBlock(BlockRegistry.GARLIC_CROP, "Garlic");
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

        normalBlock(provider, BlockRegistry.STONE_BRICK_1.get());
        normalBlock(provider, BlockRegistry.STONE_BRICK_2.get());
        normalBlock(provider, BlockRegistry.STONE_BRICK_3.get());
        normalBlock(provider, BlockRegistry.STONE_BRICK_4.get());
        normalBlock(provider, BlockRegistry.STONE_BRICK_5.get());
        normalBlock(provider, BlockRegistry.COBBLESTONE_1.get());
        normalBlock(provider, BlockRegistry.COBBLESTONE_2.get());
        normalBlock(provider, BlockRegistry.SNOWY_PLANKS_1.get());
        normalBlock(provider, BlockRegistry.PLANKS_1.get());
        normalBlock(provider, BlockRegistry.PLANKS_2.get());
        normalBlock(provider, BlockRegistry.PLANKS_3.get());
        normalBlock(provider, BlockRegistry.PLANKS_4.get());

        normalBlock(provider, BlockRegistry.SHOUT_BLOCK.get());



        flowerBlock(provider, BlockRegistry.RED_MOUNTAIN_FLOWER.get());
        flowerBlock(provider, BlockRegistry.BLUE_MOUNTAIN_FLOWER.get());
        flowerBlock(provider, BlockRegistry.YELLOW_MOUNTAIN_FLOWER.get());
        flowerBlock(provider, BlockRegistry.PURPLE_MOUNTAIN_FLOWER.get());

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

//                .partialState().with(PearlOysterBlock.IS_OPEN, true)
//                .modelForState().modelFile(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster_open"))).addModel()
//                .partialState().with(PearlOysterBlock.IS_EMPTY, true)
//                .modelForState().modelFile(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster_empty"))).addModel()
//                .partialState().with(PearlOysterBlock.IS_OPEN, false)
//                .modelForState().modelFile(provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster"))).addModel();

//        forAllStates(state -> {
//            boolean open = state.getValue(PearlOysterBlock.IS_OPEN);
//            boolean empty = state.getValue(PearlOysterBlock.IS_EMPTY);
//            Direction facing = state.getValue(PearlOysterBlock.FACING);
//
//            return ConfiguredModel.builder()
//                    .modelFile(open ?
//                            (empty ?
//                                    provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster_empty")) :
//                                    provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster_open"))
//                            ) :
//                            provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster"))
//                    )
//                    .rotationY(((int) facing.toYRot() + 180) % 360) // 180 is default angle offset
//                    .build();
//        });
        provider.simpleBlockItem(BlockRegistry.PEARL_OYSTER_BLOCK.get(), provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster")));

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

//        provider.horizontalBlock(SOVNGARDE_PORTAL.get(), state -> provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/sovngarde_portal")));
//        provider.simpleBlockItem(SOVNGARDE_PORTAL.get(), provider.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/sovngarde_portal")));
    }

    public static void addBlockItemModels(ItemModelProvider provider) {
        provider.basicItem(BlockRegistry.CANIS_ROOT.get().asItem());
        provider.basicItem(BlockRegistry.BLEEDING_CROWN.get().asItem());
        provider.basicItem(BlockRegistry.BLISTERWORT.get().asItem());
        provider.basicItem(BlockRegistry.FLY_AMANITA.get().asItem());
        provider.basicItem(BlockRegistry.WHITE_CAP.get().asItem());
        provider.basicItem(BlockRegistry.CREEP_CLUSTER.get().asItem());
    }

    public static void normalBlock(BlockStateProvider provider, Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        provider.simpleBlock(block, provider.models().cubeAll(path, provider.modLoc("block/" + path)));
        provider.simpleBlockItem(block, provider.models().getExistingFile(provider.modLoc("block/" + path)));
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
}