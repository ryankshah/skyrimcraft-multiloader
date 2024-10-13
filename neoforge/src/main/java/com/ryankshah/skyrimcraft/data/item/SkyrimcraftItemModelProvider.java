package com.ryankshah.skyrimcraft.data.item;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.block.BlockData;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import com.ryankshah.skyrimcraft.util.NameUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SkyrimcraftItemModelProvider extends ItemModelProvider
{
    public SkyrimcraftItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(BlockRegistry.DWEMER_METAL_TALL_DOOR_ITEM.get());
        basicItem(BlockRegistry.DWEMER_METAL_TALL_GATE_ITEM.get());
        basicItem(BlockRegistry.STEEL_TALL_GATE_ITEM.get());

        item(ItemRegistry.SWEET_ROLL.get());
        item(ItemRegistry.GARLIC_BREAD.get());
        item(ItemRegistry.POTATO_BREAD.get());
        item(ItemRegistry.TOMATO.get());
        item(ItemRegistry.GARLIC.get());
        item(ItemRegistry.APPLE_PIE.get());
        item(ItemRegistry.MAMMOTH_SNOUT.get());
        item(ItemRegistry.MAMMOTH_STEAK.get());
        item(ItemRegistry.VENISON.get());
        item(ItemRegistry.FLOUR.get());
        item(ItemRegistry.BUTTER.get());
        item(ItemRegistry.CABBAGE.get());
        item(ItemRegistry.CABBAGE_SEEDS.get());
        item(ItemRegistry.ASH_YAM.get());
        item(ItemRegistry.ASH_YAM_SLIPS.get());
        item(ItemRegistry.JAZBAY_GRAPES.get());
        item(ItemRegistry.GREEN_APPLE.get());
        item(ItemRegistry.SNOWBERRIES.get());
        item(ItemRegistry.JUNIPER_BERRIES.get());
        item(ItemRegistry.SNOWBERRY_CROSTATA.get());
        item(ItemRegistry.JUNIPER_BERRY_CROSTATA.get());
        item(ItemRegistry.JAZBAY_CROSTATA.get());
        item(ItemRegistry.LAVENDER.get());
        item(ItemRegistry.LAVENDER_DUMPLING.get());
        item(ItemRegistry.CHICKEN_DUMPLING.get());
        item(ItemRegistry.POTATO_SOUP.get());
        item(ItemRegistry.TOMATO_SOUP.get());
        item(ItemRegistry.APPLE_CABBAGE_STEW.get());
        item(ItemRegistry.APPLE_DUMPLING.get());
        item(ItemRegistry.BEEF_STEW.get());
        item(ItemRegistry.CABBAGE_SOUP.get());
        item(ItemRegistry.CABBAGE_POTATO_SOUP.get());
        item(ItemRegistry.CLAM_MEAT.get());
        item(ItemRegistry.SLICED_GOAT_CHEESE.get());
        item(ItemRegistry.SLICED_EIDAR_CHEESE.get());
        item(ItemRegistry.GOURD.get());
        item(ItemRegistry.LEEK.get());
        item(ItemRegistry.LEG_OF_GOAT.get());
        item(ItemRegistry.LEG_OF_GOAT_ROAST.get());
        item(ItemRegistry.HORSE_MEAT.get());
        item(ItemRegistry.HORSE_HAUNCH.get());
        item(ItemRegistry.VEGETABLE_SOUP.get());
        item(ItemRegistry.MAMMOTH_CHEESE_BOWL.get());
        item(ItemRegistry.HONEY_NUT_TREAT.get());
        item(ItemRegistry.GRILLED_LEEKS.get());
        item(ItemRegistry.ELSWEYR_FONDUE.get());
        item(ItemRegistry.CLAM_CHOWDER.get());
        item(ItemRegistry.BRAIDED_BREAD.get());

        item(ItemRegistry.TOMATO_SEEDS.get());

        item(ItemRegistry.ALE.get());
        item(ItemRegistry.ALTO_WINE.get());
        item(ItemRegistry.ARGONIAN_ALE.get());
        item(ItemRegistry.NORD_MEAD.get());
        item(ItemRegistry.BLACK_BRIAR_MEAD.get());
        item(ItemRegistry.BLACK_BRIAR_RESERVE.get());
        item(ItemRegistry.DRAGONS_BREATH_MEAD.get());
        item(ItemRegistry.FIREBRAND_WINE.get());
        item(ItemRegistry.HONNINGBREW_MEAD.get());
        item(ItemRegistry.MEAD_WITH_JUNIPER_BERRY.get());
        item(ItemRegistry.SKOOMA.get());
        item(ItemRegistry.SPICED_WINE.get());
        item(ItemRegistry.WINE.get());
        item(ItemRegistry.BOILED_CREME_TREAT.get());

        item(ItemRegistry.CORUNDUM_INGOT.get());
        item(ItemRegistry.DWARVEN_METAL_INGOT.get());
        item(ItemRegistry.EBONY_INGOT.get());
        item(ItemRegistry.MALACHITE_INGOT.get());
        item(ItemRegistry.MOONSTONE_INGOT.get());
        item(ItemRegistry.ORICHALCUM_INGOT.get());
        item(ItemRegistry.QUICKSILVER_INGOT.get());
        item(ItemRegistry.SILVER_INGOT.get());
        item(ItemRegistry.STEEL_INGOT.get());
        item(ItemRegistry.LEATHER_STRIPS.get());
        item(ItemRegistry.DAEDRA_HEART.get());

        item(ItemRegistry.FLAWED_AMETHYST.get());
        item(ItemRegistry.FLAWED_DIAMOND.get());
        item(ItemRegistry.FLAWED_EMERALD.get());
        item(ItemRegistry.FLAWED_RUBY.get());
        item(ItemRegistry.FLAWLESS_RUBY.get());
        item(ItemRegistry.FLAWED_GARNET.get());
        item(ItemRegistry.FLAWLESS_GARNET.get());

        item(ItemRegistry.SALT_PILE.get());
//        item(ItemRegistry.CREEP_CLUSTER.get());
        item(ItemRegistry.GRASS_POD.get());
        item(ItemRegistry.VAMPIRE_DUST.get());
        item(ItemRegistry.MORA_TAPINELLA.get());
        item(ItemRegistry.BRIAR_HEART.get());
        item(ItemRegistry.GIANTS_TOE.get());
        item(ItemRegistry.SALMON_ROE.get());
        item(ItemRegistry.DWARVEN_OIL.get());
        item(ItemRegistry.FIRE_SALTS.get());
        item(ItemRegistry.ABECEAN_LONGFIN.get());
        item(ItemRegistry.CYRODILIC_SPADETAIL.get());
        item(ItemRegistry.SABRE_CAT_TOOTH.get());
        item(ItemRegistry.BLUE_DARTWING.get());
        item(ItemRegistry.ORANGE_DARTWING.get());
        item(ItemRegistry.PEARL.get());
        item(ItemRegistry.SMALL_PEARL.get());
        item(ItemRegistry.PINE_THRUSH_EGG.get());
        item(ItemRegistry.ROCK_WARBLER_EGG.get());
        item(ItemRegistry.SLAUGHTERFISH_EGG.get());
        item(ItemRegistry.SLAUGHTERFISH_SCALES.get());
        item(ItemRegistry.SPIDER_EGG.get());
        item(ItemRegistry.HAWK_EGG.get());
        item(ItemRegistry.TROLL_FAT.get());
        item(ItemRegistry.CHAURUS_EGGS.get());
//        item(ItemRegistry.FLY_AMANITA.get());
        item(ItemRegistry.ELVES_EAR.get());
        item(ItemRegistry.TAPROOT.get());
        item(ItemRegistry.BEE.get());
        item(ItemRegistry.EYE_OF_SABRE_CAT.get());
        item(ItemRegistry.BEAR_CLAWS.get());
        item(ItemRegistry.BEEHIVE_HUSK.get());
        item(ItemRegistry.BERITS_ASHES.get());
//        item(ItemRegistry.BLISTERWORT.get());
        item(ItemRegistry.BLUE_BUTTERFLY_WING.get());
        item(ItemRegistry.BUTTERFLY_WING.get());
        item(ItemRegistry.CHARRED_SKEEVER_HIDE.get());
        item(ItemRegistry.CRIMSON_NIRNROOT.get());
        item(ItemRegistry.DEATHBELL.get());
        item(ItemRegistry.DRAGONS_TONGUE.get());
        item(ItemRegistry.ECTOPLASM.get());
        item(ItemRegistry.FALMER_EAR.get());

        egg(ItemRegistry.BLUE_BUTTERFLY_SPAWN_EGG.get());
        egg(ItemRegistry.MONARCH_BUTTERFLY_SPAWN_EGG.get());
        egg(ItemRegistry.LUNAR_MOTH_SPAWN_EGG.get());
        egg(ItemRegistry.ORANGE_DARTWING_SPAWN_EGG.get());
        egg(ItemRegistry.BLUE_DARTWING_SPAWN_EGG.get());
        egg(ItemRegistry.TORCHBUG_SPAWN_EGG.get());
        egg(ItemRegistry.DRAGON_SPAWN_EGG.get());
        egg(ItemRegistry.GIANT_SPAWN_EGG.get());
        egg(ItemRegistry.SABRE_CAT_SPAWN_EGG.get());
        egg(ItemRegistry.DRAUGR_SPAWN_EGG.get());
        egg(ItemRegistry.KHAJIIT_SPAWN_EGG.get());
        egg(ItemRegistry.FALMER_SPAWN_EGG.get());

        item(ItemRegistry.ANCIENT_NORD_HELMET.get());
        item(ItemRegistry.ANCIENT_NORD_CHESTPLATE.get());
        item(ItemRegistry.ANCIENT_NORD_LEGGINGS.get());
        item(ItemRegistry.ANCIENT_NORD_BOOTS.get());
        sword(ItemRegistry.ANCIENT_NORD_SWORD.get());
        greatsword(ItemRegistry.ANCIENT_NORD_GREATSWORD.get());
        sword(ItemRegistry.ANCIENT_NORD_WAR_AXE.get());
        sword(ItemRegistry.ANCIENT_NORD_BATTLEAXE.get());
        bow(ItemRegistry.ANCIENT_NORD_BOW.get());

        item(ItemRegistry.DAEDRIC_HELMET.get());
        item(ItemRegistry.DAEDRIC_CHESTPLATE.get());
        item(ItemRegistry.DAEDRIC_LEGGINGS.get());
        item(ItemRegistry.DAEDRIC_BOOTS.get());
        sword(ItemRegistry.DAEDRIC_DAGGER.get());
        sword(ItemRegistry.DAEDRIC_SWORD.get());
        sword(ItemRegistry.DAEDRIC_BATTLEAXE.get());
        bow(ItemRegistry.DAEDRIC_BOW.get());
        greatsword(ItemRegistry.DAEDRIC_GREATSWORD.get());
        sword(ItemRegistry.DAEDRIC_MACE.get());
        sword(ItemRegistry.DAEDRIC_WAR_AXE.get());
        sword(ItemRegistry.DAEDRIC_WARHAMMER.get());

        sword(ItemRegistry.DRAGONBONE_DAGGER.get());
        sword(ItemRegistry.DRAGONBONE_SWORD.get());
        sword(ItemRegistry.DRAGONBONE_BATTLEAXE.get());
        bow(ItemRegistry.DRAGONBONE_BOW.get());
        greatsword(ItemRegistry.DRAGONBONE_GREATSWORD.get());
        sword(ItemRegistry.DRAGONBONE_MACE.get());
        sword(ItemRegistry.DRAGONBONE_WAR_AXE.get());
        sword(ItemRegistry.DRAGONBONE_WARHAMMER.get());

        item(ItemRegistry.DWARVEN_HELMET.get());
        item(ItemRegistry.DWARVEN_CHESTPLATE.get());
        item(ItemRegistry.DWARVEN_LEGGINGS.get());
        item(ItemRegistry.DWARVEN_BOOTS.get());
        sword(ItemRegistry.DWARVEN_DAGGER.get());
        sword(ItemRegistry.DWARVEN_SWORD.get());
        sword(ItemRegistry.DWARVEN_BATTLEAXE.get());
        bow(ItemRegistry.DWARVEN_BOW.get());
        greatsword(ItemRegistry.DWARVEN_GREATSWORD.get());
        sword(ItemRegistry.DWARVEN_MACE.get());
        sword(ItemRegistry.DWARVEN_WAR_AXE.get());
        sword(ItemRegistry.DWARVEN_WARHAMMER.get());

        item(ItemRegistry.EBONY_HELMET.get());
        item(ItemRegistry.EBONY_CHESTPLATE.get());
        item(ItemRegistry.EBONY_LEGGINGS.get());
        item(ItemRegistry.EBONY_BOOTS.get());
        sword(ItemRegistry.EBONY_DAGGER.get());
        sword(ItemRegistry.EBONY_SWORD.get());
        sword(ItemRegistry.EBONY_BATTLEAXE.get());
        bow(ItemRegistry.EBONY_BOW.get());
        greatsword(ItemRegistry.EBONY_GREATSWORD.get());
        sword(ItemRegistry.EBONY_MACE.get());
        sword(ItemRegistry.EBONY_WAR_AXE.get());
        sword(ItemRegistry.EBONY_WARHAMMER.get());

        item(ItemRegistry.ELVEN_HELMET.get());
        item(ItemRegistry.ELVEN_CHESTPLATE.get());
        item(ItemRegistry.ELVEN_LEGGINGS.get());
        item(ItemRegistry.ELVEN_BOOTS.get());
        sword(ItemRegistry.ELVEN_DAGGER.get());
        sword(ItemRegistry.ELVEN_SWORD.get());
        sword(ItemRegistry.ELVEN_BATTLEAXE.get());
        bow(ItemRegistry.ELVEN_BOW.get());
        greatsword(ItemRegistry.ELVEN_GREATSWORD.get());
        sword(ItemRegistry.ELVEN_MACE.get());
        sword(ItemRegistry.ELVEN_WAR_AXE.get());
        sword(ItemRegistry.ELVEN_WARHAMMER.get());

        item(ItemRegistry.FALMER_HELMET.get());
        item(ItemRegistry.FALMER_CHESTPLATE.get());
        item(ItemRegistry.FALMER_LEGGINGS.get());
        item(ItemRegistry.FALMER_BOOTS.get());
        sword(ItemRegistry.FALMER_SWORD.get());
        bow(ItemRegistry.FALMER_BOW.get());
        sword(ItemRegistry.FALMER_WAR_AXE.get());

        item(ItemRegistry.GLASS_HELMET.get());
        item(ItemRegistry.GLASS_CHESTPLATE.get());
        item(ItemRegistry.GLASS_LEGGINGS.get());
        item(ItemRegistry.GLASS_BOOTS.get());
        sword(ItemRegistry.GLASS_DAGGER.get());
        sword(ItemRegistry.GLASS_SWORD.get());
        sword(ItemRegistry.GLASS_BATTLEAXE.get());
        bow(ItemRegistry.GLASS_BOW.get());
        greatsword(ItemRegistry.GLASS_GREATSWORD.get());
        sword(ItemRegistry.GLASS_MACE.get());
        sword(ItemRegistry.GLASS_WAR_AXE.get());
        sword(ItemRegistry.GLASS_WARHAMMER.get());

        item(ItemRegistry.IMPERIAL_HELMET.get());
        item(ItemRegistry.IMPERIAL_CHESTPLATE.get());
        item(ItemRegistry.IMPERIAL_LEGGINGS.get());
        item(ItemRegistry.IMPERIAL_BOOTS.get());
        sword(ItemRegistry.IMPERIAL_SWORD.get());

        item(ItemRegistry.IRON_HELMET.get());
        item(ItemRegistry.IRON_CHESTPLATE.get());
        item(ItemRegistry.IRON_LEGGINGS.get());
        item(ItemRegistry.IRON_BOOTS.get());
        sword(ItemRegistry.IRON_DAGGER.get());
        sword(ItemRegistry.IRON_SWORD.get());
        sword(ItemRegistry.IRON_BATTLEAXE.get());
        greatsword(ItemRegistry.IRON_GREATSWORD.get());
        sword(ItemRegistry.IRON_MACE.get());
        sword(ItemRegistry.IRON_WAR_AXE.get());
        sword(ItemRegistry.IRON_WARHAMMER.get());

        item(ItemRegistry.ORCISH_HELMET.get());
        item(ItemRegistry.ORCISH_CHESTPLATE.get());
        item(ItemRegistry.ORCISH_LEGGINGS.get());
        item(ItemRegistry.ORCISH_BOOTS.get());
        sword(ItemRegistry.ORCISH_DAGGER.get());
        sword(ItemRegistry.ORCISH_SWORD.get());
        sword(ItemRegistry.ORCISH_BATTLEAXE.get());
        bow(ItemRegistry.ORCISH_BOW.get());
        greatsword(ItemRegistry.ORCISH_GREATSWORD.get());
        sword(ItemRegistry.ORCISH_MACE.get());
        sword(ItemRegistry.ORCISH_WAR_AXE.get());
        sword(ItemRegistry.ORCISH_WARHAMMER.get());

//        item(ItemRegistry.STEEL_HELMET.get());
//        item(ItemRegistry.STEEL_CHESTPLATE.get());
//        item(ItemRegistry.STEEL_LEGGINGS.get());
//        item(ItemRegistry.STEEL_BOOTS.get());
        sword(ItemRegistry.STEEL_DAGGER.get());
        sword(ItemRegistry.STEEL_SWORD.get());
        sword(ItemRegistry.STEEL_BATTLEAXE.get());
        greatsword(ItemRegistry.STEEL_GREATSWORD.get());
        sword(ItemRegistry.STEEL_MACE.get());
        sword(ItemRegistry.STEEL_WAR_AXE.get());
        sword(ItemRegistry.STEEL_WARHAMMER.get());

        item(ItemRegistry.STORMCLOAK_OFFICER_HELMET.get());
        item(ItemRegistry.STORMCLOAK_OFFICER_CHESTPLATE.get());
        item(ItemRegistry.STORMCLOAK_OFFICER_LEGGINGS.get());
        item(ItemRegistry.STORMCLOAK_OFFICER_BOOTS.get());

        bow(ItemRegistry.HUNTING_BOW.get());
        bow(ItemRegistry.LONGBOW.get());

        item(ItemRegistry.SCALED_HELMET.get());
        item(ItemRegistry.SCALED_CHESTPLATE.get());
        item(ItemRegistry.SCALED_LEGGINGS.get());
        item(ItemRegistry.SCALED_BOOTS.get());

        item(ItemRegistry.HIDE_HELMET.get());
        item(ItemRegistry.HIDE_CHESTPLATE.get());
        item(ItemRegistry.HIDE_LEGGINGS.get());
        item(ItemRegistry.HIDE_BOOTS.get());

        item(ItemRegistry.CHILLREND.get());
        item(ItemRegistry.DAWNBREAKER.get());

        item(ItemRegistry.GOLD_RING.get());
        item(ItemRegistry.GOLD_SAPPHIRE_RING.get());
        item(ItemRegistry.GOLD_EMERALD_RING.get());
        item(ItemRegistry.GOLD_DIAMOND_RING.get());
        item(ItemRegistry.SILVER_RING.get());
        item(ItemRegistry.SILVER_AMETHYST_RING.get());
        item(ItemRegistry.SILVER_GARNET_RING.get());
        item(ItemRegistry.SILVER_RUBY_RING.get());
        item(ItemRegistry.ASGEIRS_WEDDING_BAND.get());
        item(ItemRegistry.AHZIDALS_RING_OF_ARCANA.get());
        item(ItemRegistry.BALWENS_ORNAMENTAL_RING.get());
        item(ItemRegistry.BONE_HAWK_RING.get());
        item(ItemRegistry.CALCELMOS_RING.get());
        item(ItemRegistry.ENCHANTED_RING.get());
        item(ItemRegistry.FJOLAS_WEDDING_BAND.get());
        item(ItemRegistry.ILAS_TEIS_RING.get());
        item(ItemRegistry.KATARINAS_ORNAMENTAL_RING.get());
        item(ItemRegistry.MADESIS_SILVER_RING.get());
        item(ItemRegistry.MUIRIS_RING.get());
        item(ItemRegistry.NIGHTWEAVERS_BAND.get());
        item(ItemRegistry.PITHIS_ORNAMENTAL_RING.get());
        item(ItemRegistry.RING_OF_NAMIRA.get());
        item(ItemRegistry.TREOYS_ORNAMENTAL_RING.get());

        item(ItemRegistry.GOLD_NECKLACE.get());
        item(ItemRegistry.GOLD_DIAMOND_NECKLACE.get());
        item(ItemRegistry.GOLD_JEWELLED_NECKLACE.get());
        item(ItemRegistry.GOLD_RUBY_NECKLACE.get());
        item(ItemRegistry.SILVER_NECKLACE.get());
        item(ItemRegistry.SILVER_EMERALD_NECKLACE.get());
        item(ItemRegistry.SILVER_JEWELLED_NECKLACE.get());
        item(ItemRegistry.SILVER_SAPPHIRE_NECKLACE.get());

        item(ItemRegistry.STAFF.get());
        spellbook(ItemRegistry.CONJURE_FAMILIAR_SPELLBOOK.get());
        spellbook(ItemRegistry.FIREBALL_SPELLBOOK.get());
        spellbook(ItemRegistry.HEALING_SPELLBOOK.get());
        spellbook(ItemRegistry.TURN_UNDEAD_SPELLBOOK.get());
        spellbook(ItemRegistry.LIGHTNING_SPELLBOOK.get());
        spellbook(ItemRegistry.FLAME_CLOAK_SPELLBOOK.get());
        spellbook(ItemRegistry.ICE_SPIKE_SPELLBOOK.get());
        spellbook(ItemRegistry.CONJURE_ZOMBIE_SPELLBOOK.get());
        spellbook(ItemRegistry.DETECT_LIFE_SPELLBOOK.get());
        spellbook(ItemRegistry.CANDLELIGHT_SPELLBOOK.get());
        spellbook(ItemRegistry.OAKFLESH_SPELLBOOK.get());
        spellbook(ItemRegistry.TELEKINESIS_SPELLBOOK.get());
        spellbook(ItemRegistry.WATERBREATHING_SPELLBOOK.get());

        skillbook(ItemRegistry.ALTERATION_SKILLBOOK.get());
        skillbook(ItemRegistry.CONJURATION_SKILLBOOK.get());
        skillbook(ItemRegistry.DESTRUCTION_SKILLBOOK.get());
        skillbook(ItemRegistry.ILLUSION_SKILLBOOK.get());
        skillbook(ItemRegistry.RESTORATION_SKILLBOOK.get());
        skillbook(ItemRegistry.ENCHANTING_SKILLBOOK.get());
        skillbook(ItemRegistry.ONE_HANDED_SKILLBOOK.get());
        skillbook(ItemRegistry.TWO_HANDED_SKILLBOOK.get());
        skillbook(ItemRegistry.ARCHERY_SKILLBOOK.get());
        skillbook(ItemRegistry.BLOCK_SKILLBOOK.get());
        skillbook(ItemRegistry.SMITHING_SKILLBOOK.get());
        skillbook(ItemRegistry.HEAVY_ARMOR_SKILLBOOK.get());
        skillbook(ItemRegistry.LIGHT_ARMOR_SKILLBOOK.get());
        skillbook(ItemRegistry.PICKPOCKET_SKILLBOOK.get());
        skillbook(ItemRegistry.LOCKPICKING_SKILLBOOK.get());
        skillbook(ItemRegistry.SNEAK_SKILLBOOK.get());
        skillbook(ItemRegistry.ALCHEMY_SKILLBOOK.get());
        skillbook(ItemRegistry.SPEECH_SKILLBOOK.get());

        item(ItemRegistry.MINOR_MAGICKA_POTION.get());
        item(ItemRegistry.MAGICKA_POTION.get());
        item(ItemRegistry.PLENTIFUL_MAGICKA_POTION.get());
        item(ItemRegistry.VIGOROUS_MAGICKA_POTION.get());
        item(ItemRegistry.EXTREME_MAGICKA_POTION.get());
        item(ItemRegistry.ULTIMATE_MAGICKA_POTION.get());

        item(ItemRegistry.PHILTER_OF_THE_PHANTOM_POTION.get());
        item(ItemRegistry.POTION_OF_WATERWALKING.get());
        item(ItemRegistry.POTION_OF_CURE_DISEASES.get());
        item(ItemRegistry.POTION_OF_CURE_POISON.get());

        item(ItemRegistry.LASTING_POTENCY_POTION.get());
        item(ItemRegistry.DRAUGHT_LASTING_POTENCY_POTION.get());
        item(ItemRegistry.SOLUTION_LASTING_POTENCY_POTION.get());
        item(ItemRegistry.PHILTER_LASTING_POTENCY_POTION.get());
        item(ItemRegistry.ELIXIR_LASTING_POTENCY_POTION.get());

        item(ItemRegistry.POTION_OF_HAGGLING.get());
        item(ItemRegistry.DRAUGHT_OF_HAGGLING.get());
        item(ItemRegistry.PHILTER_OF_HAGGLING.get());
        item(ItemRegistry.ELIXIR_OF_HAGGLING.get());

        item(ItemRegistry.POTION_OF_TRUE_SHOT.get());
        item(ItemRegistry.DRAUGHT_OF_TRUE_SHOT.get());
        item(ItemRegistry.PHILTER_OF_TRUE_SHOT.get());
        item(ItemRegistry.ELIXIR_OF_TRUE_SHOT.get());

        wallInventory("dwemer_metal_brick_wall", ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_bricks"));
        wallInventory("dwemer_stone_brick_wall", ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_stone_bricks"));

        BlockData.addBlockItemModels(this);
    }

    private void item(Item item) {
        String name = NameUtils.getItemName(item);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", "item/" + name);
    }

    private void spellbook(Item item) {
        String name = NameUtils.getItemName(item);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", "item/spellbook");
    }

    private void skillbook(Item item) {
        String name = NameUtils.getItemName(item);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", "item/skillbook");
    }

    private ItemModelBuilder item(String name) {
        return getBuilder(name)
                .parent(getExistingFile(mcLoc("item/bow")))
                .texture("layer0", "item/" + name);
    }

    private void egg(Item item) {
        String name = NameUtils.getItemName(item);
        getBuilder(name).parent(getExistingFile(mcLoc("item/template_spawn_egg")));
    }

    private void sword(Item item) {
        String name = NameUtils.getItemName(item);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", "item/" + name);
    }

    private void greatsword(Item item) {
        String name = NameUtils.getItemName(item);
        getBuilder(name+"_no_use")
                .parent(getExistingFile(modLoc("item/greatsword_no_use")))
                .texture("layer0", "item/" + name)
                .texture("layer1", mcLoc("item/barrier"));
        getBuilder(name+"_blocking")
                .parent(getExistingFile(modLoc("item/greatsword_blocking")))
                .texture("layer0", "item/" + name);
        getBuilder(name)
                .parent(getExistingFile(modLoc("item/greatsword")))
                .texture("layer0", "item/" + name)
                .override().predicate(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "no_use"), 1.0f)
                .model(getExistingFile(modLoc("item/" + name + "_no_use"))).end()
                .override().predicate(modLoc("blocking"), 1.0f)
                .model(getExistingFile(modLoc("item/" + name + "_blocking"))).end();
    }

    private void bow(Item item) {
        String name = NameUtils.getItemName(item);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/bow")))
                .texture("layer0", "item/" + name)
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).model(item(name + "_pulling_0")).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), 0.65f).model(item(name + "_pulling_1")).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), 0.9f).model(item(name + "_pulling_2")).end();
    }
}