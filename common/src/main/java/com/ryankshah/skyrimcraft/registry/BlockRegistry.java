package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.*;
import com.ryankshah.skyrimcraft.item.SkyrimBlockItemIngredient;
import com.ryankshah.skyrimcraft.util.IngredientEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistry
{
    public static void init() {}

    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MODID);
    public static final RegistryObject<Block, Block> CORUNDUM_ORE = registerBlock("corundum_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            )
    );
    public static final RegistryObject<Item, BlockItem> CORUNDUM_ORE_ITEM = ItemRegistry.ITEMS.register("corundum_ore", () -> new BlockItem(CORUNDUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DEEPSLATE_CORUNDUM_ORE = registerBlock("deepslate_corundum_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> DEEPSLATE_CORUNDUM_ORE_ITEM = ItemRegistry.ITEMS.register("deepslate_corundum_ore", () -> new BlockItem(DEEPSLATE_CORUNDUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> EBONY_ORE = registerBlock("ebony_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> EBONY_ORE_ITEM = ItemRegistry.ITEMS.register("ebony_ore", () -> new BlockItem(EBONY_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DEEPSLATE_EBONY_ORE = registerBlock("deepslate_ebony_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> DEEPSLATE_EBONY_ORE_ITEM = ItemRegistry.ITEMS.register("deepslate_ebony_ore", () -> new BlockItem(DEEPSLATE_EBONY_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> MALACHITE_ORE = registerBlock("malachite_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> MALACHITE_ORE_ITEM = ItemRegistry.ITEMS.register("malachite_ore", () -> new BlockItem(MALACHITE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DEEPSLATE_MALACHITE_ORE = registerBlock("deepslate_malachite_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> DEEPSLATE_MALACHITE_ORE_ITEM = ItemRegistry.ITEMS.register("deepslate_malachite_ore", () -> new BlockItem(DEEPSLATE_MALACHITE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> MOONSTONE_ORE = registerBlock("moonstone_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> MOONSTONE_ORE_ITEM = ItemRegistry.ITEMS.register("moonstone_ore", () -> new BlockItem(MOONSTONE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DEEPSLATE_MOONSTONE_ORE = registerBlock("deepslate_moonstone_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> DEEPSLATE_MOONSTONE_ORE_ITEM = ItemRegistry.ITEMS.register("deepslate_moonstone_ore", () -> new BlockItem(DEEPSLATE_MOONSTONE_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> ORICHALCUM_ORE = registerBlock("orichalcum_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> ORICHALCUM_ORE_ITEM = ItemRegistry.ITEMS.register("orichalcum_ore", () -> new BlockItem(ORICHALCUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DEEPSLATE_ORICHALCUM_ORE = registerBlock("deepslate_orichalcum_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> DEEPSLATE_ORICHALCUM_ORE_ITEM = ItemRegistry.ITEMS.register("deepslate_orichalcum_ore", () -> new BlockItem(DEEPSLATE_ORICHALCUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> QUICKSILVER_ORE = registerBlock("quicksilver_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> QUICKSILVER_ORE_ITEM = ItemRegistry.ITEMS.register("quicksilver_ore", () -> new BlockItem(QUICKSILVER_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DEEPSLATE_QUICKSILVER_ORE = registerBlock("deepslate_quicksilver_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> DEEPSLATE_QUICKSILVER_ORE_ITEM = ItemRegistry.ITEMS.register("deepslate_quicksilver_ore", () -> new BlockItem(DEEPSLATE_QUICKSILVER_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> SILVER_ORE = registerBlock("silver_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> SILVER_ORE_ITEM = ItemRegistry.ITEMS.register("silver_ore", () -> new BlockItem(SILVER_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(4, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            ));
    public static final RegistryObject<Item, BlockItem> DEEPSLATE_SILVER_ORE_ITEM = ItemRegistry.ITEMS.register("deepslate_silver_ore", () -> new BlockItem(DEEPSLATE_SILVER_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Block, Block> BIRDS_NEST = registerBlock("birds_nest",
            BirdsNestBlock::new);
    public static final RegistryObject<Item, BlockItem> BIRDS_NEST_ITEM = ItemRegistry.ITEMS.register("birds_nest", () -> new BlockItem(BIRDS_NEST.get(), new Item.Properties()));


    public static final RegistryObject<Block, Block> SHOUT_BLOCK = registerBlock("shout_block",
            ShoutBlock::new);
    public static final RegistryObject<Item, BlockItem> SHOUT_BLOCK_ITEM = ItemRegistry.ITEMS.register("shout_block", () -> new BlockItem(SHOUT_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block, Block> ALCHEMY_TABLE = registerBlock("alchemy_table",
            AlchemyTableBlock::new);
    public static final RegistryObject<Item, BlockItem> ALCHEMY_TABLE_ITEM = ItemRegistry.ITEMS.register("alchemy_table", () -> new BlockItem(ALCHEMY_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> OVEN = registerBlock("oven",
            OvenBlock::new);
    public static final RegistryObject<Item, BlockItem> OVEN_ITEM = ItemRegistry.ITEMS.register("oven", () -> new BlockItem(OVEN.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> BLACKSMITH_FORGE = registerBlock("blacksmith_forge",
            BlacksmithForgeBlock::new);
    public static final RegistryObject<Item, BlockItem> BLACKSMITH_FORGE_ITEM = ItemRegistry.ITEMS.register("blacksmith_forge", () -> new BlockItem(BLACKSMITH_FORGE.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> ARCANE_ENCHANTER = registerBlock("arcane_enchanter",
            ArcaneEnchanterBlock::new);
    public static final RegistryObject<Item, BlockItem> ARCANE_ENCHANTER_ITEM = ItemRegistry.ITEMS.register("arcane_enchanter", () -> new BlockItem(ARCANE_ENCHANTER.get(), new Item.Properties()));

    public static final RegistryObject<Block, Block> WEAPON_RACK = registerBlock("weapon_rack",
            WeaponRackBlock::new);
    public static final RegistryObject<Item, BlockItem> WEAPON_RACK_ITEM = ItemRegistry.ITEMS.register("weapon_rack", () -> new BlockItem(WEAPON_RACK.get(), new Item.Properties()));

    public static final RegistryObject<Block, Block> RED_MOUNTAIN_FLOWER = registerBlock("red_mountain_flower",
            () -> new SkyrimFlower(
                    MobEffects.SATURATION,
                    7,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .noCollission()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .offsetType(BlockBehaviour.OffsetType.XZ)
                            .pushReaction(PushReaction.DESTROY)
            ));
    public static final RegistryObject<Item, BlockItem> RED_MOUNTAIN_FLOWER_ITEM = ItemRegistry.ITEMS.register("red_mountain_flower", () ->
            new SkyrimBlockItemIngredient(RED_MOUNTAIN_FLOWER.get(), new Item.Properties(), IngredientEffect.RESTORE_MAGICKA, IngredientEffect.RAVAGE_MAGICKA, IngredientEffect.FORTIFY_MAGICKA, IngredientEffect.DAMAGE_HEALTH));
    public static final RegistryObject<Block, Block> BLUE_MOUNTAIN_FLOWER = registerBlock("blue_mountain_flower",
            () -> new SkyrimFlower(
                    MobEffects.SATURATION,
                    7,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .noCollission()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .offsetType(BlockBehaviour.OffsetType.XZ)
                            .pushReaction(PushReaction.DESTROY)
            ));
    public static final RegistryObject<Item, BlockItem> BLUE_MOUNTAIN_FLOWER_ITEM = ItemRegistry.ITEMS.register("blue_mountain_flower", () ->
            new SkyrimBlockItemIngredient(BLUE_MOUNTAIN_FLOWER.get(), new Item.Properties(), IngredientEffect.RESTORE_HEALTH, IngredientEffect.FORTIFY_CONJURATION, IngredientEffect.FORTIFY_HEALTH, IngredientEffect.DAMAGE_MAGICKA_REGEN));
    public static final RegistryObject<Block, Block> YELLOW_MOUNTAIN_FLOWER = registerBlock("yellow_mountain_flower",
            () -> new SkyrimFlower(
                    MobEffects.SATURATION,
                    7,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .noCollission()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .offsetType(BlockBehaviour.OffsetType.XZ)
                            .pushReaction(PushReaction.DESTROY)
            ));
    public static final RegistryObject<Item, BlockItem> YELLOW_MOUNTAIN_FLOWER_ITEM = ItemRegistry.ITEMS.register("yellow_mountain_flower", () ->
            new SkyrimBlockItemIngredient(YELLOW_MOUNTAIN_FLOWER.get(), new Item.Properties(), IngredientEffect.RESIST_POISON, IngredientEffect.FORTIFY_RESTORATION, IngredientEffect.FORTIFY_HEALTH, IngredientEffect.DAMAGE_STAMINA_REGEN));
    public static final RegistryObject<Block, Block> PURPLE_MOUNTAIN_FLOWER = registerBlock("purple_mountain_flower",
            () -> new SkyrimFlower(
                    MobEffects.SATURATION,
                    7,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .noCollission()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .offsetType(BlockBehaviour.OffsetType.XZ)
                            .pushReaction(PushReaction.DESTROY)
            ));
    public static final RegistryObject<Item, BlockItem> PURPLE_MOUNTAIN_FLOWER_ITEM = ItemRegistry.ITEMS.register("purple_mountain_flower", () ->
            new SkyrimBlockItemIngredient(PURPLE_MOUNTAIN_FLOWER.get(), new Item.Properties(), IngredientEffect.RESTORE_STAMINA, IngredientEffect.FORTIFY_SNEAK, IngredientEffect.LINGERING_DAMAGE_MAGICKA, IngredientEffect.RESIST_FROST));
    public static final RegistryObject<Block, Block> CANIS_ROOT_BLOCK = registerBlock("canis_root",
            () -> new CanisRoot(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .noCollission()
                            .instabreak()
                            .sound(SoundType.SWEET_BERRY_BUSH)
                            .offsetType(BlockBehaviour.OffsetType.XZ)
                            .pushReaction(PushReaction.DESTROY)
            ));
    public static final RegistryObject<Item, BlockItem> CANIS_ROOT = ItemRegistry.ITEMS.register("canis_root",
            () -> new SkyrimBlockItemIngredient(CANIS_ROOT_BLOCK.get(), new Item.Properties(), IngredientEffect.DAMAGE_STAMINA, IngredientEffect.FORTIFY_ONE_HANDED, IngredientEffect.FORTIFY_MARKSMAN, IngredientEffect.PARALYSIS));

    public static final RegistryObject<Block, Block> BLEEDING_CROWN_BLOCK = registerBlock("bleeding_crown",
            GenericTripleMushroom::new);
    public static final RegistryObject<Item, BlockItem> BLEEDING_CROWN = ItemRegistry.ITEMS.register("bleeding_crown",
            () -> new SkyrimBlockItemIngredient(BLEEDING_CROWN_BLOCK.get(), new Item.Properties(), IngredientEffect.WEAKNESS_TO_FIRE, IngredientEffect.FORTIFY_BLOCK, IngredientEffect.WEAKNESS_TO_POISON, IngredientEffect.RESIST_MAGIC));

    public static final RegistryObject<Block, Block> WHITE_CAP_BLOCK = registerBlock("white_cap",
            GenericTripleMushroom::new);
    public static final RegistryObject<Item, BlockItem> WHITE_CAP = ItemRegistry.ITEMS.register("white_cap",
            () -> new SkyrimBlockItemIngredient(WHITE_CAP_BLOCK.get(), new Item.Properties(), IngredientEffect.WEAKNESS_TO_FIRE, IngredientEffect.FORTIFY_BLOCK, IngredientEffect.WEAKNESS_TO_POISON, IngredientEffect.RESIST_MAGIC));


    public static final RegistryObject<Block, Block> BLISTERWORT_BLOCK = registerBlock("blisterwort",
            GenericTripleMushroom::new);
    public static final RegistryObject<Item, BlockItem> BLISTERWORT = ItemRegistry.ITEMS.register("blisterwort",
            () -> new SkyrimBlockItemIngredient(BLISTERWORT_BLOCK.get(), new Item.Properties(), IngredientEffect.DAMAGE_STAMINA, IngredientEffect.FRENZY, IngredientEffect.RESTORE_HEALTH, IngredientEffect.FORTIFY_SMITHING));

    public static final RegistryObject<Block, Block> FLY_AMANITA_BLOCK = registerBlock("fly_amanita",
            GenericTripleMushroom::new);
    public static final RegistryObject<Item, BlockItem> FLY_AMANITA = ItemRegistry.ITEMS.register("fly_amanita",
            () -> new SkyrimBlockItemIngredient(FLY_AMANITA_BLOCK.get(), new Item.Properties(), IngredientEffect.RESIST_FIRE, IngredientEffect.FORTIFY_TWO_HANDED, IngredientEffect.FRENZY, IngredientEffect.REGENERATE_STAMINA));

    public static final RegistryObject<Block, Block> CREEP_CLUSTER_BLOCK = registerBlock("creep_cluster",
            () -> new CreepClusterBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .noCollission()
                            .noOcclusion()
                            .sound(SoundType.HARD_CROP)
                            .randomTicks()
            ));
    public static final RegistryObject<Item, BlockItem> CREEP_CLUSTER = ItemRegistry.ITEMS.register("creep_cluster",
            () -> new SkyrimBlockItemIngredient(CREEP_CLUSTER_BLOCK.get(), new Item.Properties(), IngredientEffect.RESTORE_MAGICKA, IngredientEffect.DAMAGE_STAMINA_REGEN, IngredientEffect.FORTIFY_CARRY_WEIGHT, IngredientEffect.WEAKNESS_TO_MAGIC));


    public static final RegistryObject<Block, Block> PEARL_OYSTER_BLOCK = registerBlock("pearl_oyster",
            PearlOysterBlock::new);
    public static final RegistryObject<Block, Block> TOMATO_CROP = registerBlock("tomatoes", () -> new TomatoCrop(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)
    ));
    public static final RegistryObject<Block, Block> GARLIC_CROP = registerBlock("garlic", () -> new GarlicCrop(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)
    ));

    /// DECORATIVE BLOCKS
    public static final RegistryObject<Block, Block> STONE_BRICK_1 = registerBlock("stone_brick_1",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Block, Block> STONE_BRICK_2 = registerBlock("stone_brick_2",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Block, Block> STONE_BRICK_3 = registerBlock("stone_brick_3",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Block, Block> STONE_BRICK_4 = registerBlock("stone_brick_4",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Block, Block> STONE_BRICK_5 = registerBlock("stone_brick_5",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Block, Block> COBBLESTONE_1 = registerBlock("cobblestone_1",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Block, Block> COBBLESTONE_2 = registerBlock("cobblestone_2",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Block, Block> SNOWY_PLANKS_1 = registerBlock("snowy_planks_1",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block, Block> PLANKS_1 = registerBlock("planks_1",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block, Block> PLANKS_2 = registerBlock("planks_2",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block, Block> PLANKS_3 = registerBlock("planks_3",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block, Block> PLANKS_4 = registerBlock("planks_4",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    private static <T extends Block> RegistryObject<Block, T> registerBlock(String id, Supplier<T> block) {
        return BLOCKS.register(id, block);
    }

    public static <T extends Block> RegistryObject<Block, T> registerBlockWithItem(String name, Supplier<T> block) {
        return registerBlock(name, block, b -> () -> new BlockItem(b.get(), new Item.Properties()));
    }

    protected static <T extends Block> RegistryObject<Block, T> registerBlock(String name, Supplier<T> block, Function<RegistryObject<Block, T>, Supplier<? extends BlockItem>> item) {
        var reg = registerBlock(name, block);
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(reg.get(), new Item.Properties()));
        return reg;
    }
}