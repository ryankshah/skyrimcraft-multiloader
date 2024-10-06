package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.mojang.serialization.MapCodec;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.*;
import com.ryankshah.skyrimcraft.block.entity.TurnStoneBlockEntity;
import com.ryankshah.skyrimcraft.block.piston.DwemerMovingPistonBlock;
import com.ryankshah.skyrimcraft.block.piston.DwemerPistonBase;
import com.ryankshah.skyrimcraft.block.piston.DwemerPistonHead;
import com.ryankshah.skyrimcraft.item.SkyrimBlockItemIngredient;
import com.ryankshah.skyrimcraft.util.IngredientEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistry
{
    public static void init() {}

    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MODID);
    public static final RegistrationProvider<MapCodec<? extends Block>> REGISTRAR = RegistrationProvider.get(Registries.BLOCK_TYPE, Constants.MODID);

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
    public static final RegistryObject<Block, TurnStoneBlock> TURN_STONE = registerBlock("turn_stone",
            TurnStoneBlock::new);
    public static final RegistryObject<Item, BlockItem> TURN_STONE_ITEM = ItemRegistry.ITEMS.register("turn_stone", () -> new BlockItem(TURN_STONE.get(), new Item.Properties()));

    public static final RegistryObject<Block, RuneStoneBlock> RUNE_STONE = registerBlock("rune_stone",
            RuneStoneBlock::new);
    public static final RegistryObject<Item, BlockItem> RUNE_STONE_ITEM = ItemRegistry.ITEMS.register("rune_stone", () -> new BlockItem(RUNE_STONE.get(), new Item.Properties()));


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
    public static final RegistryObject<Block, Block> LAVENDER = registerBlock("lavender_block",
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
    public static final RegistryObject<Item, BlockItem> LAVENDER_ITEM = ItemRegistry.ITEMS.register("lavender_block", () ->
            new SkyrimBlockItemIngredient(LAVENDER.get(), new Item.Properties(), IngredientEffect.RESTORE_STAMINA, IngredientEffect.FORTIFY_SNEAK, IngredientEffect.LINGERING_DAMAGE_MAGICKA, IngredientEffect.RESIST_FROST));

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
    public static final RegistryObject<Block, Block> CABBAGE_CROP = registerBlock("cabbage", () -> new CabbageCrop(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)
    ));
    public static final RegistryObject<Block, Block> ASH_YAM_CROP = registerBlock("ash_yam", () -> new AshYamCrop(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)
    ));

    public static final RegistryObject<Block, Block> JAZBAY_GRAPE_BUSH = registerBlock("jazbay_grape_bush", () -> new JazbayGrapeBushBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.SWEET_BERRY_BUSH)
                    .pushReaction(PushReaction.DESTROY)
    ));
    public static final RegistryObject<Block, Block> JUNIPER_BERRY_BUSH = registerBlock("juniper_berry_bush", () -> new JuniperBerryBushBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.SWEET_BERRY_BUSH)
                    .pushReaction(PushReaction.DESTROY)
    ));
    public static final RegistryObject<Block, Block> SNOWBERRY_BUSH = registerBlock("snowberry_bush", () -> new SnowberryBushBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.SWEET_BERRY_BUSH)
                    .pushReaction(PushReaction.DESTROY)
    ));


    /// DECORATIVE BLOCKS
    public static final RegistryObject<Block, Block> STONE_BRICK_1 = registerBlock("stone_brick_1",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_BRICK_1_ITEM = ItemRegistry.ITEMS.register("stone_brick_1",
            () -> new BlockItem(STONE_BRICK_1.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_BRICK_2 = registerBlock("stone_brick_2",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_BRICK_2_ITEM = ItemRegistry.ITEMS.register("stone_brick_2",
            () -> new BlockItem(STONE_BRICK_2.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_BRICK_3 = registerBlock("stone_brick_3",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_BRICK_3_ITEM = ItemRegistry.ITEMS.register("stone_brick_3",
            () -> new BlockItem(STONE_BRICK_3.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_BRICK_4 = registerBlock("stone_brick_4",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_BRICK_4_ITEM = ItemRegistry.ITEMS.register("stone_brick_4",
            () -> new BlockItem(STONE_BRICK_4.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_BRICK_5 = registerBlock("stone_brick_5",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_BRICK_5_ITEM = ItemRegistry.ITEMS.register("stone_brick_5",
            () -> new BlockItem(STONE_BRICK_5.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_BRICK_6 = registerBlock("stone_brick_6",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_BRICK_6_ITEM = ItemRegistry.ITEMS.register("stone_brick_6",
            () -> new BlockItem(STONE_BRICK_6.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> COBBLESTONE_1 = registerBlock("cobblestone_1",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> COBBLESTONE_1_ITEM = ItemRegistry.ITEMS.register("cobblestone_1",
            () -> new BlockItem(COBBLESTONE_1.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> COBBLESTONE_2 = registerBlock("cobblestone_2",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> COBBLESTONE_2_ITEM = ItemRegistry.ITEMS.register("cobblestone_2",
            () -> new BlockItem(COBBLESTONE_2.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> SNOWY_PLANKS_1 = registerBlock("snowy_planks_1",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Item, BlockItem> SNOWY_PLANKS_1_ITEM = ItemRegistry.ITEMS.register("snowy_planks_1",
            () -> new BlockItem(SNOWY_PLANKS_1.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> PLANKS_1 = registerBlock("planks_1",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Item, BlockItem> PLANKS_1_ITEM = ItemRegistry.ITEMS.register("planks_1",
            () -> new BlockItem(PLANKS_1.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> PLANKS_2 = registerBlock("planks_2",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Item, BlockItem> PLANKS_2_ITEM = ItemRegistry.ITEMS.register("planks_2",
            () -> new BlockItem(PLANKS_2.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> PLANKS_3 = registerBlock("planks_3",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Item, BlockItem> PLANKS_3_ITEM = ItemRegistry.ITEMS.register("planks_3",
            () -> new BlockItem(PLANKS_3.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> PLANKS_4 = registerBlock("planks_4",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Item, BlockItem> PLANKS_4_ITEM = ItemRegistry.ITEMS.register("planks_4",
            () -> new BlockItem(PLANKS_4.get(), new Item.Properties()));

    public static final RegistryObject<Block, Block> STONE_1 = registerBlock("stone_1",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_1_ITEM = ItemRegistry.ITEMS.register("stone_1",
            () -> new BlockItem(STONE_1.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_2 = registerBlock("stone_2",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_2_ITEM = ItemRegistry.ITEMS.register("stone_2",
            () -> new BlockItem(STONE_2.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_3 = registerBlock("stone_3",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_3_ITEM = ItemRegistry.ITEMS.register("stone_3",
            () -> new BlockItem(STONE_3.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_4 = registerBlock("stone_4",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_4_ITEM = ItemRegistry.ITEMS.register("stone_4",
            () -> new BlockItem(STONE_4.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_5 = registerBlock("stone_5",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_5_ITEM = ItemRegistry.ITEMS.register("stone_5",
            () -> new BlockItem(STONE_5.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_6 = registerBlock("stone_6",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_6_ITEM = ItemRegistry.ITEMS.register("stone_6",
            () -> new BlockItem(STONE_6.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_7 = registerBlock("stone_7",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_7_ITEM = ItemRegistry.ITEMS.register("stone_7",
            () -> new BlockItem(STONE_7.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_8 = registerBlock("stone_8",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_8_ITEM = ItemRegistry.ITEMS.register("stone_8",
            () -> new BlockItem(STONE_8.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_9 = registerBlock("stone_9",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_9_ITEM = ItemRegistry.ITEMS.register("stone_9",
            () -> new BlockItem(STONE_9.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> STONE_10 = registerBlock("stone_10",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> STONE_10_ITEM = ItemRegistry.ITEMS.register("stone_10",
            () -> new BlockItem(STONE_10.get(), new Item.Properties()));

    public static final RegistryObject<Block, Block> DIRT_1 = registerBlock("dirt_1",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final RegistryObject<Item, BlockItem> DIRT_1_ITEM = ItemRegistry.ITEMS.register("dirt_1",
            () -> new BlockItem(DIRT_1.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DIRT_2 = registerBlock("dirt_2",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final RegistryObject<Item, BlockItem> DIRT_2_ITEM = ItemRegistry.ITEMS.register("dirt_2",
            () -> new BlockItem(DIRT_2.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DIRT_3 = registerBlock("dirt_3",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final RegistryObject<Item, BlockItem> DIRT_3_ITEM = ItemRegistry.ITEMS.register("dirt_3",
            () -> new BlockItem(DIRT_3.get(), new Item.Properties()));

    public static final RegistryObject<Block, Block> GRASS_1 = registerBlock("grass_1",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Item, BlockItem> GRASS_1_ITEM = ItemRegistry.ITEMS.register("grass_1",
            () -> new BlockItem(GRASS_1.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> GRASS_2 = registerBlock("grass_2",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Item, BlockItem> GRASS_2_ITEM = ItemRegistry.ITEMS.register("grass_2",
            () -> new BlockItem(GRASS_2.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> GRASS_3 = registerBlock("grass_3",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Item, BlockItem> GRASS_3_ITEM = ItemRegistry.ITEMS.register("grass_3",
            () -> new BlockItem(GRASS_3.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> GRASS_4 = registerBlock("grass_4",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Item, BlockItem> GRASS_4_ITEM = ItemRegistry.ITEMS.register("grass_4",
            () -> new BlockItem(GRASS_4.get(), new Item.Properties()));

    public static final BlockSetType STEEL = BlockSetType.register(new BlockSetType(Constants.MODID + ":steel"));
    public static final BlockSetType DWEMER_METAL = BlockSetType.register(new BlockSetType(Constants.MODID + ":dwemer"));

    public static final RegistryObject<Block, TrapDoorBlock> STEEL_TRAPDOOR = registerBlock("steel_trapdoor",
            () -> new TrapDoorBlock(STEEL, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_TRAPDOOR)));
    public static final RegistryObject<Item, BlockItem> STEEL_TRAPDOOR_ITEM = ItemRegistry.ITEMS.register("steel_trapdoor",
            () -> new BlockItem(STEEL_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Block, SkyrimcraftDoorBlock> STEEL_CELL_DOOR = registerBlock("steel_cell_door",
            () -> new SkyrimcraftDoorBlock(STEEL, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR)));
    public static final RegistryObject<Item, BlockItem> STEEL_CELL_DOOR_ITEM = ItemRegistry.ITEMS.register("steel_cell_door",
            () -> new BlockItem(STEEL_CELL_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Block, SkyrimcraftDoorBlock> STEEL_GATE_DOOR = registerBlock("steel_gate_door",
            () -> new SkyrimcraftDoorBlock(STEEL, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR)));
    public static final RegistryObject<Item, BlockItem> STEEL_GATE_DOOR_ITEM = ItemRegistry.ITEMS.register("steel_gate_door",
            () -> new BlockItem(STEEL_GATE_DOOR.get(), new Item.Properties()));

    public static final RegistryObject<Block, TrapDoorBlock> DWEMER_METAL_TRAPDOOR = registerBlock("dwemer_metal_trapdoor",
            () -> new TrapDoorBlock(DWEMER_METAL, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_TRAPDOOR)));
    public static final RegistryObject<Item, BlockItem> DWEMER_METAL_TRAPDOOR_ITEM = ItemRegistry.ITEMS.register("dwemer_metal_trapdoor",
            () -> new BlockItem(DWEMER_METAL_TRAPDOOR.get(), new Item.Properties()));

    public static final RegistryObject<Block, SkyrimcraftDoorBlock> DWEMER_METAL_DOOR = registerBlock("dwemer_metal_door",
            () -> new SkyrimcraftDoorBlock(DWEMER_METAL, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR)));
    public static final RegistryObject<Item, BlockItem> DWEMER_METAL_DOOR_ITEM = ItemRegistry.ITEMS.register("dwemer_metal_door",
            () -> new BlockItem(DWEMER_METAL_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Block, SkyrimcraftDoorBlock> DWEMER_METAL_GATE = registerBlock("dwemer_metal_gate",
            () -> new SkyrimcraftDoorBlock(DWEMER_METAL, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR)));
    public static final RegistryObject<Item, BlockItem> DWEMER_METAL_GATE_ITEM = ItemRegistry.ITEMS.register("dwemer_metal_gate",
            () -> new BlockItem(DWEMER_METAL_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Block, TorchBlock> DWEMER_METAL_TORCH = registerBlock("dwemer_metal_torch",
            () -> new TorchBlock(ParticleTypes.SMALL_FLAME, BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH)));
    public static final RegistryObject<Block, WallTorchBlock> DWEMER_METAL_WALL_TORCH = registerBlock("dwemer_metal_wall_torch",
            () -> new WallTorchBlock(ParticleTypes.SMALL_FLAME, BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH)));
    public static final RegistryObject<Item, BlockItem> DWEMER_METAL_TORCH_ITEM = ItemRegistry.ITEMS.register("dwemer_metal_torch",
            () -> new BlockItem(DWEMER_METAL_TORCH.get(), new Item.Properties()));
    public static final RegistryObject<Block, TorchBlock> DWEMER_SOUL_TORCH = registerBlock("dwemer_soul_torch",
            () -> new TorchBlock(ParticleTypes.SOUL_FIRE_FLAME, BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_TORCH)));
    public static final RegistryObject<Block, WallTorchBlock> DWEMER_SOUL_WALL_TORCH = registerBlock("dwemer_soul_wall_torch",
            () -> new WallTorchBlock(ParticleTypes.SMALL_FLAME, BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH)));
    public static final RegistryObject<Item, BlockItem> DDWEMER_SOUL_TORCH_ITEM = ItemRegistry.ITEMS.register("dwemer_soul_torch",
            () -> new BlockItem(DWEMER_SOUL_TORCH.get(), new Item.Properties()));
    public static final RegistryObject<Block, RedstoneTorchBlock> DWEMER_REDSTONE_TORCH = registerBlock("dwemer_redstone_torch", //RedstoneTorchBlock
            () -> new RedstoneTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_TORCH)));
    public static final RegistryObject<Block, RedstoneWallTorchBlock> DWEMER_REDSTONE_WALL_TORCH = registerBlock("dwemer_redstone_wall_torch", //RedstoneTorchBlock
            () -> new RedstoneWallTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_TORCH)));
    public static final RegistryObject<Item, BlockItem> DDWEMER_REDSTONE_TORCH_ITEM = ItemRegistry.ITEMS.register("dwemer_redstone_torch",
            () -> new BlockItem(DWEMER_REDSTONE_TORCH.get(), new Item.Properties()));

    public static final RegistryObject<Block, RepeaterBlock> DWEMER_REPEATER = registerBlock("dwemer_repeater",
            () -> new RepeaterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REPEATER)));
    public static final RegistryObject<Item, BlockItem> DWEMER_REPEATER_ITEM = ItemRegistry.ITEMS.register("dwemer_repeater",
            () -> new BlockItem(DWEMER_REPEATER.get(), new Item.Properties()));

    public static final RegistryObject<Block, DwemerComparator> DWEMER_COMPARATOR = registerBlock("dwemer_comparator",
            () -> new DwemerComparator(BlockBehaviour.Properties.ofFullCopy(Blocks.COMPARATOR)));
    public static final RegistryObject<Item, BlockItem> DWEMER_COMPARATOR_ITEM = ItemRegistry.ITEMS.register("dwemer_comparator",
            () -> new BlockItem(DWEMER_COMPARATOR.get(), new Item.Properties()));

    public static final RegistryObject<Block, SkyrimObserverBlock> DWEMER_OBSERVER = registerBlock("dwemer_observer",
            () -> new SkyrimObserverBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OBSERVER)));
    public static final RegistryObject<Item, BlockItem> DWEMER_OBSERVER_ITEM = ItemRegistry.ITEMS.register("dwemer_observer",
            () -> new BlockItem(DWEMER_OBSERVER.get(), new Item.Properties()));

    public static final RegistryObject<Block, DwemerDropper> DWEMER_DROPPER = registerBlock("dwemer_dropper",
            () -> new DwemerDropper(BlockBehaviour.Properties.ofFullCopy(Blocks.DROPPER)));
    public static final RegistryObject<Item, BlockItem> DWEMER_DROPPER_ITEM = ItemRegistry.ITEMS.register("dwemer_dropper",
            () -> new BlockItem(DWEMER_DROPPER.get(), new Item.Properties()));

    public static final RegistryObject<Block, DwemerDispenser> DWEMER_DISPENSER = registerBlock("dwemer_dispenser",
            () -> new DwemerDispenser(BlockBehaviour.Properties.ofFullCopy(Blocks.DISPENSER)));
    public static final RegistryObject<Item, BlockItem> DWEMER_DISPENSER_ITEM = ItemRegistry.ITEMS.register("dwemer_dispenser",
            () -> new BlockItem(DWEMER_DISPENSER.get(), new Item.Properties()));

    public static final RegistryObject<Block, RotatedPillarBlock> DWEMER_METAL_PILLAR = registerBlock("dwemer_metal_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Item, BlockItem> DWEMER_METAL_PILLAR_ITEM = ItemRegistry.ITEMS.register("dwemer_metal_pillar",
            () -> new BlockItem(DWEMER_METAL_PILLAR.get(), new Item.Properties()));
    public static final RegistryObject<Block, RotatedPillarBlock> DWEMER_STONE_PILLAR = registerBlock("dwemer_stone_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> DWEMER_STONE_PILLAR_ITEM = ItemRegistry.ITEMS.register("dwemer_stone_pillar",
            () -> new BlockItem(DWEMER_STONE_PILLAR.get(), new Item.Properties()));

    public static final RegistryObject<Block, Block> DWEMER_METAL_TILES = registerBlock("dwemer_metal_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Item, BlockItem> DWEMER_METAL_TILES_ITEM = ItemRegistry.ITEMS.register("dwemer_metal_tiles",
            () -> new BlockItem(DWEMER_METAL_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> ORNATE_DWEMER_METAL_TILES = registerBlock("ornate_dwemer_metal_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Item, BlockItem> ORNATE_DWEMER_METAL_TILES_ITEM = ItemRegistry.ITEMS.register("ornate_dwemer_metal_tiles",
            () -> new BlockItem(ORNATE_DWEMER_METAL_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DWEMER_METAL_BLOCK = registerBlock("dwemer_metal_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Item, BlockItem> DWEMER_METAL_BLOCK_ITEM = ItemRegistry.ITEMS.register("dwemer_metal_block",
            () -> new BlockItem(DWEMER_METAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DWEMER_METAL_BRICKS = registerBlock("dwemer_metal_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Item, BlockItem> DWEMER_METAL_BRICKS_ITEM = ItemRegistry.ITEMS.register("dwemer_metal_bricks",
            () -> new BlockItem(DWEMER_METAL_BRICKS.get(), new Item.Properties()));

    public static final RegistryObject<Block, Block> DWEMER_STONE_BLOCK = registerBlock("dwemer_stone_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> DWEMER_STONE_BLOCK_ITEM = ItemRegistry.ITEMS.register("dwemer_stone_block",
            () -> new BlockItem(DWEMER_STONE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DWEMER_STONE_BRICKS = registerBlock("dwemer_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> DWEMER_STONE_BRICKS_ITEM = ItemRegistry.ITEMS.register("dwemer_stone_bricks",
            () -> new BlockItem(DWEMER_STONE_BRICKS.get(), new Item.Properties()));

    public static final RegistryObject<Block, SkyrimcraftPressurePlateBlock> DWEMER_STONE_PRESSURE_PLATE = registerBlock("dwemer_stone_pressure_plate",
            () -> new SkyrimcraftPressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final RegistryObject<Item, BlockItem> DWEMER_STONE_PRESSURE_PLATE_ITEM = ItemRegistry.ITEMS.register("dwemer_stone_pressure_plate",
            () -> new BlockItem(DWEMER_STONE_PRESSURE_PLATE.get(), new Item.Properties()));

    public static final RegistryObject<Block, TransparentBlock> DWEMER_GLASS = registerBlock("dwemer_glass",
            () -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));
    public static final RegistryObject<Item, BlockItem> DWEMER_GLASS_ITEM = ItemRegistry.ITEMS.register("dwemer_glass",
            () -> new BlockItem(DWEMER_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Block, TransparentBlock> DWEMER_WINDOWED_GLASS = registerBlock("dwemer_windowed_glass",
            () -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));
    public static final RegistryObject<Item, BlockItem> DWEMER_WINDOWED_GLASS_ITEM = ItemRegistry.ITEMS.register("dwemer_windowed_glass",
            () -> new BlockItem(DWEMER_WINDOWED_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Block, TransparentBlock> DWEMER_FRAMED_GLASS = registerBlock("dwemer_framed_glass",
            () -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));
    public static final RegistryObject<Item, BlockItem> DWEMER_FRAMED_GLASS_ITEM = ItemRegistry.ITEMS.register("dwemer_framed_glass",
            () -> new BlockItem(DWEMER_FRAMED_GLASS.get(), new Item.Properties()));

    public static final RegistryObject<Block, RedstoneLampBlock> DWEMER_REDSTONE_LAMP = registerBlock("dwemer_redstone_lamp",
            () -> new RedstoneLampBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_LAMP)));
    public static final RegistryObject<Item, BlockItem> DWEMER_REDSTONE_LAMP_ITEM = ItemRegistry.ITEMS.register("dwemer_redstone_lamp",
            () -> new BlockItem(DWEMER_REDSTONE_LAMP.get(), new Item.Properties()));

    public static final RegistryObject<Block, RedstoneLampBlock> DWEMER_REDSTONE_SOUL_LAMP = registerBlock("dwemer_redstone_soul_lamp",
            () -> new RedstoneLampBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_LAMP)));
    public static final RegistryObject<Item, BlockItem> DWEMER_REDSTONE_SOUL_LAMP_ITEM = ItemRegistry.ITEMS.register("dwemer_redstone_soul_lamp",
            () -> new BlockItem(DWEMER_REDSTONE_SOUL_LAMP.get(), new Item.Properties()));

    public static final RegistryObject<Block, DwemerDaylightDetector> DWEMER_DAYLIGHT_DETECTOR = registerBlock("dwemer_daylight_detector",
            () -> new DwemerDaylightDetector(BlockBehaviour.Properties.ofFullCopy(Blocks.DAYLIGHT_DETECTOR)));
    public static final RegistryObject<Item, BlockItem> DWEMER_DAYLIGHT_DETECTOR_ITEM = ItemRegistry.ITEMS.register("dwemer_daylight_detector",
            () -> new BlockItem(DWEMER_DAYLIGHT_DETECTOR.get(), new Item.Properties()));


    public static final RegistryObject<Block, DwemerLanternBlock> DWEMER_METAL_LANTERN = registerBlock("dwemer_metal_lantern",
            () -> new DwemerLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)));
    public static final RegistryObject<Item, BlockItem> DWEMER_METAL_LANTERN_ITEM = ItemRegistry.ITEMS.register("dwemer_metal_lantern",
            () -> new BlockItem(DWEMER_METAL_LANTERN.get(), new Item.Properties()));

    public static final RegistryObject<Block, DwemerLanternBlock> DWEMER_SOUL_LANTERN = registerBlock("dwemer_soul_lantern",
            () -> new DwemerLanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_LANTERN)));
    public static final RegistryObject<Item, BlockItem> DWEMER_SOUL_LANTERN_ITEM = ItemRegistry.ITEMS.register("dwemer_soul_lantern",
            () -> new BlockItem(DWEMER_SOUL_LANTERN.get(), new Item.Properties()));



    public static final RegistryObject<Block, DwemerMovingPistonBlock> DWEMER_MOVING_PISTON = registerBlock("dwemer_moving_piston",
            () -> new DwemerMovingPistonBlock(
                    BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .forceSolidOn()
                    .strength(-1.0F)
                    .dynamicShape()
                    .noLootTable()
                    .noOcclusion()
                    .isRedstoneConductor(BlockRegistry::never)
                    .isSuffocating(BlockRegistry::never)
                    .isViewBlocking(BlockRegistry::never)
                    .pushReaction(PushReaction.BLOCK)));
    public static final RegistryObject<Block, DwemerPistonHead> DWEMER_PISTON_HEAD = registerBlock("dwemer_piston_head",
            () -> new DwemerPistonHead(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(1.5F).noLootTable().pushReaction(PushReaction.BLOCK)));
    public static final RegistryObject<Block, Block> DWEMER_PISTON = registerBlock("dwemer_piston",
            () -> pistonBase(false));
    public static final RegistryObject<Item, BlockItem> DWEMER_PISTON_ITEM = ItemRegistry.ITEMS.register("dwemer_piston",
            () -> new BlockItem(DWEMER_PISTON.get(), new Item.Properties()));
    public static final RegistryObject<Block, Block> DWEMER_STICKY_PISTON = registerBlock("dwemer_sticky_piston",
            () -> pistonBase(true));
    public static final RegistryObject<Item, BlockItem> DWEMER_STICKY_PISTON_ITEM = ItemRegistry.ITEMS.register("dwemer_sticky_piston",
            () -> new BlockItem(DWEMER_STICKY_PISTON.get(), new Item.Properties()));

    public static final RegistryObject<MapCodec<? extends Block>, MapCodec<DwemerMovingPistonBlock>> DWEMER_MOVING_PISTON_TYPE = REGISTRAR.register("dwemer_moving_piston",
            () -> DwemerMovingPistonBlock.CODEC);
    public static final RegistryObject<MapCodec<? extends Block>, MapCodec<DwemerPistonHead>> DWEMER_PISTON_HEAD_TYPE = REGISTRAR.register("dwemer_piston_head",
            () -> DwemerPistonHead.CODEC);
    public static final RegistryObject<MapCodec<? extends Block>, MapCodec<DwemerPistonBase>> DWEMER_PISTON_BASE_TYPE = REGISTRAR.register("dwemer_piston_base",
            () -> DwemerPistonBase.CODEC);
    public static final RegistryObject<MapCodec<? extends Block>, MapCodec<TurnStoneBlock>> TURN_STONE_TYPE = REGISTRAR.register("turn_stone",
            () -> TurnStoneBlock.CODEC);


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

    private static Block pistonBase(boolean isSticky) {
        BlockBehaviour.StatePredicate blockbehaviour$statepredicate = (p_152641_, p_152642_, p_152643_) -> {
            return !(Boolean)p_152641_.getValue(DwemerPistonBase.EXTENDED);
        };
        return new DwemerPistonBase(isSticky, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(1.5F).isRedstoneConductor(BlockRegistry::never).isSuffocating(blockbehaviour$statepredicate).isViewBlocking(blockbehaviour$statepredicate).pushReaction(PushReaction.BLOCK));
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }
}