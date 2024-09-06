package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.item.*;
import com.ryankshah.skyrimcraft.item.potion.*;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import com.ryankshah.skyrimcraft.util.IngredientEffect;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

public class ItemRegistry
{
    public static void init() {}

    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MODID);

    public static final RegistryObject<Item, Item> SWEET_ROLL = ITEMS.register("sweet_roll",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 2), 1f)
                            .build())
                    .rarity(Rarity.EPIC)
            ));
    public static final RegistryObject<Item, Item> GARLIC_BREAD = ITEMS.register("garlic_bread",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .effect(new MobEffectInstance(ModEffects.CURE_DISEASE.asHolder(), 200, 2), 1f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> POTATO_BREAD = ITEMS.register("potato_bread",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> TOMATO = ITEMS.register("tomato",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> APPLE_PIE = ITEMS.register("apple_pie",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> MAMMOTH_STEAK = ITEMS.register("mammoth_steak",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> VENISON = ITEMS.register("venison",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> CABBAGE = ITEMS.register("cabbage",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> TOMATO_SOUP = ITEMS.register("tomato_soup",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> APPLE_CABBAGE_STEW = ITEMS.register("apple_cabbage_stew",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> APPLE_DUMPLING = ITEMS.register("apple_dumpling",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .build())
            ));
    public static final RegistryObject<Item, Item> BEEF_STEW = ITEMS.register("beef_stew",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> CABBAGE_SOUP = ITEMS.register("cabbage_soup",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> CABBAGE_POTATO_SOUP = ITEMS.register("cabbage_potato_soup",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> CHICKEN_DUMPLING = ITEMS.register("chicken_dumpling",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> CLAM_MEAT = ITEMS.register("clam_meat",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> SLICED_GOAT_CHEESE = ITEMS.register("sliced_goat_cheese",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> SLICED_EIDAR_CHEESE = ITEMS.register("sliced_eidar_cheese",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> GOURD = ITEMS.register("gourd",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> LEEK = ITEMS.register("leek",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> LEG_OF_GOAT = ITEMS.register("leg_of_goat",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .effect(new MobEffectInstance(MobEffects.HUNGER, 200, 2), 1f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> LEG_OF_GOAT_ROAST = ITEMS.register("leg_of_goat_roast",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> HORSE_MEAT = ITEMS.register("horse_meat",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .effect(new MobEffectInstance(MobEffects.HUNGER, 200, 2), 1f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> HORSE_HAUNCH = ITEMS.register("horse_haunch",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> VEGETABLE_SOUP = ITEMS.register("vegetable_soup",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> ASH_YAM = ITEMS.register("ash_yam",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> ASH_YAM_SLIPS = ITEMS.register("ash_yam_slips",
            () -> new ItemNameBlockItem(BlockRegistry.ASH_YAM_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> BOILED_CREME_TREAT = ITEMS.register("boiled_creme_treat",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> BRAIDED_BREAD = ITEMS.register("braided_bread",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> CLAM_CHOWDER = ITEMS.register("clam_chowder",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> ELSWEYR_FONDUE = ITEMS.register("elsweyr_fondue",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .effect(new MobEffectInstance(ModEffects.MAGICKA_REGEN.asHolder(), 500, 2), 1f)
//                                    .effect(new MobEffectInstance(ModEffects.FORTIFY_MAGICKA.asHolder(), 500, 2), 1f)
        .build())
            ));
    public static final RegistryObject<Item, Item> GREEN_APPLE = ITEMS.register("green_apple",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> GRILLED_LEEKS = ITEMS.register("grilled_leeks",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> HONEY_NUT_TREAT = ITEMS.register("honey_nut_treat",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> JAZBAY_GRAPES = ITEMS.register("jazbay_grapes",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> JAZBAY_CROSTATA = ITEMS.register("jazbay_crostata",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> JUNIPER_BERRIES = ITEMS.register("juniper_berries",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> JUNIPER_BERRY_CROSTATA = ITEMS.register("juniper_berry_crostata",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> LAVENDER = ITEMS.register("lavender",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> LAVENDER_DUMPLING = ITEMS.register("lavender_dumpling",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> MAMMOTH_CHEESE_BOWL = ITEMS.register("mammoth_cheese_bowl",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> POTATO_SOUP = ITEMS.register("potato_soup",
            () -> new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> RABBIT_HAUNCH = ITEMS.register("rabbit_haunch",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> SNOWBERRIES = ITEMS.register("snowberries",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));
    public static final RegistryObject<Item, Item> SNOWBERRY_CROSTATA = ITEMS.register("snowberry_crostata",
            () -> new Item(new Item.Properties()
                    .stacksTo(64)
                    .food(new FoodProperties.Builder()
                            .nutrition(5)
                            .saturationModifier(0.2f)
                            .build())
            ));

    public static final RegistryObject<Item, Item> ALE = ITEMS.register("ale", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1))); // new PotionItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> ALTO_WINE = ITEMS.register("alto_wine", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> ARGONIAN_ALE = ITEMS.register("argonian_ale", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> NORD_MEAD = ITEMS.register("nord_mead", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> BLACK_BRIAR_MEAD = ITEMS.register("black_briar_mead", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> BLACK_BRIAR_RESERVE = ITEMS.register("black_briar_reserve", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> DRAGONS_BREATH_MEAD = ITEMS.register("dragons_breath_mead", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> FIREBRAND_WINE = ITEMS.register("firebrand_wine", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> HONNINGBREW_MEAD = ITEMS.register("honningbrew_mead", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> MEAD_WITH_JUNIPER_BERRY = ITEMS.register("mead_with_juniper_berry", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> SKOOMA = ITEMS.register("skooma", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> SPICED_WINE = ITEMS.register("spiced_wine", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item, Item> WINE = ITEMS.register("wine", () -> new SkyrimAlcohol(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item, Item> MAMMOTH_SNOUT = ITEMS.register("mammoth_snout", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> FLOUR = ITEMS.register("flour", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> BUTTER = ITEMS.register("butter", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item, Item> GARLIC = ITEMS.register("garlic", () -> new ItemNameBlockItem(BlockRegistry.GARLIC_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds", () -> new ItemNameBlockItem(BlockRegistry.TOMATO_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item, Item> CABBAGE_SEEDS = ITEMS.register("cabbage_seeds", () -> new ItemNameBlockItem(BlockRegistry.CABBAGE_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item, Item> CORUNDUM_INGOT = ITEMS.register("corundum_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> DWARVEN_METAL_INGOT = ITEMS.register("dwarven_metal_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> EBONY_INGOT = ITEMS.register("ebony_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> MALACHITE_INGOT = ITEMS.register("malachite_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> MOONSTONE_INGOT = ITEMS.register("moonstone_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> ORICHALCUM_INGOT = ITEMS.register("orichalcum_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> QUICKSILVER_INGOT = ITEMS.register("quicksilver_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> LEATHER_STRIPS = ITEMS.register("leather_strips", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> DAEDRA_HEART = ITEMS.register("daedra_heart", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_HEALTH, IngredientEffect.DAMAGE_STAMINA_REGEN, IngredientEffect.DAMAGE_MAGICKA, IngredientEffect.FEAR));

    // TODO: Make these a new roll in ore drop loot tables.
    public static final RegistryObject<Item, Item> FLAWED_AMETHYST = ITEMS.register("flawed_amethyst", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> FLAWED_DIAMOND = ITEMS.register("flawed_diamond", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> FLAWED_EMERALD = ITEMS.register("flawed_emerald", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> FLAWED_RUBY = ITEMS.register("flawed_ruby", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> FLAWLESS_RUBY = ITEMS.register("flawless_ruby", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> FLAWED_GARNET = ITEMS.register("flawed_garnet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item, Item> FLAWLESS_GARNET = ITEMS.register("flawless_garnet", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item, Item> SALT_PILE = ITEMS.register("salt_pile", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.WEAKNESS_TO_MAGIC, IngredientEffect.FORTIFY_RESTORATION, IngredientEffect.SLOW, IngredientEffect.REGENERATE_MAGICKA));
    //    public static final RegistryObject<Item, Item> CREEP_CLUSTER = ITEMS.register("creep_cluster", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_MAGICKA, IngredientEffect.DAMAGE_STAMINA_REGEN, IngredientEffect.FORTIFY_CARRY_WEIGHT, IngredientEffect.WEAKNESS_TO_MAGIC));
    public static final RegistryObject<Item, Item> GRASS_POD = ITEMS.register("grass_pod", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESIST_POISON, IngredientEffect.RAVAGE_MAGICKA, IngredientEffect.FORTIFY_ALTERATION, IngredientEffect.RESTORE_MAGICKA));
    public static final RegistryObject<Item, Item> VAMPIRE_DUST = ITEMS.register("vampire_dust", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.INVISIBILITY, IngredientEffect.RESTORE_MAGICKA, IngredientEffect.REGENERATE_HEALTH, IngredientEffect.CURE_DISEASE));
    public static final RegistryObject<Item, Item> MORA_TAPINELLA = ITEMS.register("mora_tapinella", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_MAGICKA, IngredientEffect.LINGERING_DAMAGE_HEALTH, IngredientEffect.REGENERATE_STAMINA, IngredientEffect.FORTIFY_ILLUSION));
    public static final RegistryObject<Item, Item> BRIAR_HEART = ITEMS.register("briar_heart", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_MAGICKA, IngredientEffect.FORTIFY_BLOCK, IngredientEffect.PARALYSIS, IngredientEffect.FORTIFY_MAGICKA));
    public static final RegistryObject<Item, Item> GIANTS_TOE = ITEMS.register("giants_toe", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.DAMAGE_STAMINA, IngredientEffect.FORTIFY_HEALTH, IngredientEffect.FORTIFY_CARRY_WEIGHT, IngredientEffect.DAMAGE_STAMINA_REGEN));
    public static final RegistryObject<Item, Item> SALMON_ROE = ITEMS.register("salmon_roe", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_STAMINA, IngredientEffect.WATERBREATHING, IngredientEffect.FORTIFY_MAGICKA, IngredientEffect.REGENERATE_MAGICKA));
    public static final RegistryObject<Item, Item> DWARVEN_OIL = ITEMS.register("dwarven_oil", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.WEAKNESS_TO_MAGIC, IngredientEffect.FORTIFY_ILLUSION, IngredientEffect.REGENERATE_MAGICKA, IngredientEffect.RESTORE_MAGICKA));
    public static final RegistryObject<Item, Item> FIRE_SALTS = ITEMS.register("fire_salts", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.WEAKNESS_TO_FROST, IngredientEffect.RESIST_FIRE, IngredientEffect.RESTORE_MAGICKA, IngredientEffect.REGENERATE_MAGICKA));
    public static final RegistryObject<Item, Item> ABECEAN_LONGFIN = ITEMS.register("abecean_longfin", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.WEAKNESS_TO_FROST, IngredientEffect.FORTIFY_SNEAK, IngredientEffect.WEAKNESS_TO_POISON, IngredientEffect.FORTIFY_RESTORATION));
    public static final RegistryObject<Item, Item> CYRODILIC_SPADETAIL = ITEMS.register("cyrodilic_spadetail", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.DAMAGE_STAMINA, IngredientEffect.FORTIFY_RESTORATION, IngredientEffect.FEAR, IngredientEffect.RAVAGE_HEALTH));
    public static final RegistryObject<Item, Item> SABRE_CAT_TOOTH = ITEMS.register("sabre_cat_tooth", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_STAMINA, IngredientEffect.FORTIFY_HEAVY_ARMOR, IngredientEffect.FORTIFY_SMITHING, IngredientEffect.WEAKNESS_TO_POISON));
    public static final RegistryObject<Item, Item> BLUE_DARTWING = ITEMS.register("blue_dartwing", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESIST_SHOCK, IngredientEffect.FORTIFY_PICKPOCKET, IngredientEffect.RESTORE_HEALTH, IngredientEffect.FEAR));
    public static final RegistryObject<Item, Item> ORANGE_DARTWING = ITEMS.register("orange_dartwing", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_STAMINA, IngredientEffect.RAVAGE_MAGICKA, IngredientEffect.FORTIFY_PICKPOCKET, IngredientEffect.LINGERING_DAMAGE_HEALTH));
    public static final RegistryObject<Item, Item> PEARL = ITEMS.register("pearl", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_STAMINA, IngredientEffect.FORTIFY_BLOCK, IngredientEffect.RESTORE_MAGICKA, IngredientEffect.RESIST_SHOCK));
    public static final RegistryObject<Item, Item> SMALL_PEARL = ITEMS.register("small_pearl", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_STAMINA, IngredientEffect.FORTIFY_ONE_HANDED, IngredientEffect.FORTIFY_RESTORATION, IngredientEffect.RESIST_FROST));
    public static final RegistryObject<Item, Item> PINE_THRUSH_EGG = ITEMS.register("pine_thrush_egg", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_STAMINA, IngredientEffect.FORTIFY_LOCKPICKING, IngredientEffect.WEAKNESS_TO_POISON, IngredientEffect.RESIST_SHOCK));
    public static final RegistryObject<Item, Item> ROCK_WARBLER_EGG = ITEMS.register("rock_warbler_egg", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_HEALTH, IngredientEffect.FORTIFY_ONE_HANDED, IngredientEffect.DAMAGE_STAMINA, IngredientEffect.WEAKNESS_TO_MAGIC));
    public static final RegistryObject<Item, Item> SLAUGHTERFISH_EGG = ITEMS.register("slaughterfish_egg", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESIST_POISON, IngredientEffect.FORTIFY_PICKPOCKET, IngredientEffect.LINGERING_DAMAGE_HEALTH, IngredientEffect.FORTIFY_STAMINA));
    public static final RegistryObject<Item, Item> SLAUGHTERFISH_SCALES = ITEMS.register("slaughterfish_scales", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESIST_FROST, IngredientEffect.LINGERING_DAMAGE_HEALTH, IngredientEffect.FORTIFY_HEAVY_ARMOR, IngredientEffect.FORTIFY_BLOCK));
    public static final RegistryObject<Item, Item> SPIDER_EGG = ITEMS.register("spider_egg", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.DAMAGE_STAMINA, IngredientEffect.DAMAGE_MAGICKA_REGEN, IngredientEffect.FORTIFY_LOCKPICKING, IngredientEffect.FORTIFY_MARKSMAN));
    public static final RegistryObject<Item, Item> HAWK_EGG = ITEMS.register("hawk_egg", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESIST_MAGIC, IngredientEffect.DAMAGE_MAGICKA_REGEN, IngredientEffect.WATERBREATHING, IngredientEffect.LINGERING_DAMAGE_STAMINA));
    public static final RegistryObject<Item, Item> TROLL_FAT = ITEMS.register("troll_fat", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESIST_POISON, IngredientEffect.FORTIFY_TWO_HANDED, IngredientEffect.FRENZY, IngredientEffect.DAMAGE_HEALTH));
    public static final RegistryObject<Item, Item> CHAURUS_EGGS = ITEMS.register("chaurus_eggs", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.WEAKNESS_TO_POISON, IngredientEffect.FORTIFY_STAMINA, IngredientEffect.DAMAGE_MAGICKA, IngredientEffect.INVISIBILITY));
    //    public static final RegistryObject<Item, Item> FLY_AMANITA = ITEMS.register("fly_amanita", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESIST_FIRE, IngredientEffect.FORTIFY_TWO_HANDED, IngredientEffect.FRENZY, IngredientEffect.REGENERATE_STAMINA));
    public static final RegistryObject<Item, Item> ELVES_EAR = ITEMS.register("elves_ear", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_MAGICKA, IngredientEffect.FORTIFY_MARKSMAN, IngredientEffect.WEAKNESS_TO_FROST, IngredientEffect.RESIST_FIRE));
    public static final RegistryObject<Item, Item> TAPROOT = ITEMS.register("taproot", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.WEAKNESS_TO_MAGIC, IngredientEffect.FORTIFY_ILLUSION, IngredientEffect.REGENERATE_MAGICKA, IngredientEffect.RESTORE_MAGICKA));
    public static final RegistryObject<Item, Item> BEE = ITEMS.register("bee", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_STAMINA, IngredientEffect.RAVAGE_STAMINA, IngredientEffect.REGENERATE_STAMINA, IngredientEffect.WEAKNESS_TO_SHOCK));
    public static final RegistryObject<Item, Item> EYE_OF_SABRE_CAT = ITEMS.register("eye_of_sabre_cat", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_STAMINA, IngredientEffect.RAVAGE_HEALTH, IngredientEffect.DAMAGE_MAGICKA, IngredientEffect.RESTORE_HEALTH));
    public static final RegistryObject<Item, Item> BEAR_CLAWS = ITEMS.register("bear_claws", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_STAMINA, IngredientEffect.FORTIFY_HEALTH, IngredientEffect.FORTIFY_ONE_HANDED, IngredientEffect.DAMAGE_MAGICKA_REGEN));
    public static final RegistryObject<Item, Item> BEEHIVE_HUSK = ITEMS.register("beehive_husk", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESIST_POISON, IngredientEffect.FORTIFY_LIGHT_ARMOR, IngredientEffect.FORTIFY_SNEAK, IngredientEffect.FORTIFY_DESTRUCTION));
    public static final RegistryObject<Item, Item> BERITS_ASHES = ITEMS.register("berits_ashes", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.DAMAGE_STAMINA, IngredientEffect.RESIST_FIRE, IngredientEffect.FORTIFY_CONJURATION, IngredientEffect.RAVAGE_STAMINA));
    //    public static final RegistryObject<Item, Item> BLEEDING_CROWN = ITEMS.register("bleeding_crown", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.WEAKNESS_TO_FIRE, IngredientEffect.FORTIFY_BLOCK, IngredientEffect.WEAKNESS_TO_POISON, IngredientEffect.RESIST_MAGIC));
//    public static final RegistryObject<Item, Item> BLISTERWORT = ITEMS.register("blisterwort", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.DAMAGE_STAMINA, IngredientEffect.FRENZY, IngredientEffect.RESTORE_HEALTH, IngredientEffect.FORTIFY_SMITHING));
    public static final RegistryObject<Item, Item> BLUE_BUTTERFLY_WING = ITEMS.register("blue_butterfly_wing", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.DAMAGE_STAMINA, IngredientEffect.FORTIFY_CONJURATION, IngredientEffect.DAMAGE_MAGICKA_REGEN, IngredientEffect.FORTIFY_ENCHANTING));
    public static final RegistryObject<Item, Item> BUTTERFLY_WING = ITEMS.register("butterfly_wing", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_HEALTH, IngredientEffect.FORTIFY_BARTER, IngredientEffect.LINGERING_DAMAGE_STAMINA, IngredientEffect.DAMAGE_MAGICKA));
    //    public static final RegistryObject<Item, Item> CANIS_ROOT = ITEMS.register("canis_root", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.DAMAGE_STAMINA, IngredientEffect.FORTIFY_ONE_HANDED, IngredientEffect.FORTIFY_MARKSMAN, IngredientEffect.PARALYSIS));
    public static final RegistryObject<Item, Item> CHARRED_SKEEVER_HIDE = ITEMS.register("charred_skeever_hide", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_STAMINA, IngredientEffect.CURE_DISEASE, IngredientEffect.RESIST_POISON, IngredientEffect.RESTORE_HEALTH));
    public static final RegistryObject<Item, Item> CRIMSON_NIRNROOT = ITEMS.register("crimson_nirnroot", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.DAMAGE_HEALTH, IngredientEffect.DAMAGE_STAMINA, IngredientEffect.INVISIBILITY, IngredientEffect.RESIST_MAGIC));
    public static final RegistryObject<Item, Item> DEATHBELL = ITEMS.register("deathbell", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.DAMAGE_HEALTH, IngredientEffect.RAVAGE_STAMINA, IngredientEffect.SLOW, IngredientEffect.WEAKNESS_TO_POISON));
    public static final RegistryObject<Item, Item> DRAGONS_TONGUE = ITEMS.register("dragons_tongue", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESIST_FIRE, IngredientEffect.FORTIFY_BARTER, IngredientEffect.FORTIFY_ILLUSION, IngredientEffect.FORTIFY_TWO_HANDED));
    public static final RegistryObject<Item, Item> ECTOPLASM = ITEMS.register("ectoplasm", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.RESTORE_MAGICKA, IngredientEffect.FORTIFY_DESTRUCTION, IngredientEffect.FORTIFY_MAGICKA, IngredientEffect.DAMAGE_HEALTH));
    public static final RegistryObject<Item, Item> FALMER_EAR = ITEMS.register("falmer_ear", () -> new SkyrimIngredient(new Item.Properties(), IngredientEffect.DAMAGE_HEALTH, IngredientEffect.FRENZY, IngredientEffect.RESIST_POISON, IngredientEffect.FORTIFY_LOCKPICKING));

    //// COMBAT ////
    // Ancient Nord
    public static final Supplier<SkyrimArmor> ANCIENT_NORD_HELMET = ITEMS.register("ancient_nord_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.ANCIENT_NORD, SkyrimArmor.Type.HELMET, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> ANCIENT_NORD_CHESTPLATE = ITEMS.register("ancient_nord_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.ANCIENT_NORD, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> ANCIENT_NORD_LEGGINGS = ITEMS.register("ancient_nord_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.ANCIENT_NORD, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> ANCIENT_NORD_BOOTS = ITEMS.register("ancient_nord_boots", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.ANCIENT_NORD, SkyrimArmor.Type.BOOTS, new Item.Properties(), true));
    //    public static final Supplier<Item> ANCIENT_NORD_ARROW = ITEMS.register("ancient_nord_arrow", () -> new SkyrimArrow.AncientNordArrow(new Item.Properties(), "Ancient Nord Arrow"));
    public static final Supplier<SwordItem> ANCIENT_NORD_SWORD = ITEMS.register("ancient_nord_sword", () -> new SwordItem(ItemTier.ANCIENT_NORD, new Item.Properties()));
    public static final Supplier<SkyrimTwoHandedSword> ANCIENT_NORD_BATTLEAXE = ITEMS.register("ancient_nord_battleaxe", () -> new SkyrimTwoHandedSword(ItemTier.ANCIENT_NORD, new Item.Properties()));
    public static final Supplier<SkyrimTwoHandedSword> ANCIENT_NORD_GREATSWORD = ITEMS.register("ancient_nord_greatsword", () -> new SkyrimTwoHandedSword(ItemTier.ANCIENT_NORD, new Item.Properties()));
    public static final Supplier<SwordItem> ANCIENT_NORD_WAR_AXE = ITEMS.register("ancient_nord_war_axe", () -> new SwordItem(ItemTier.ANCIENT_NORD, new Item.Properties()));
    public static final Supplier<BowItem> ANCIENT_NORD_BOW = ITEMS.register("ancient_nord_bow", () -> new BowItem(new BowItem.Properties()));
    // Daedric
    public static final Supplier<SkyrimArmor> DAEDRIC_HELMET = ITEMS.register("daedric_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.DAEDRIC, SkyrimArmor.Type.HELMET, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> DAEDRIC_CHESTPLATE = ITEMS.register("daedric_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.DAEDRIC, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> DAEDRIC_LEGGINGS = ITEMS.register("daedric_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.DAEDRIC, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> DAEDRIC_BOOTS = ITEMS.register("daedric_boots", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.DAEDRIC, SkyrimArmor.Type.BOOTS, new Item.Properties(), true));
    public static final Supplier<ShieldItem> DAEDRIC_SHIELD = ITEMS.register("daedric_shield", () -> new ShieldItem(new Item.Properties().stacksTo(1)));
    //    public static final Supplier<Item> DAEDRIC_ARROW = ITEMS.register("daedric_arrow", () -> new SkyrimArrow.DaedricArrow(new Item.Properties().fireResistant(), "Daedric Arrow"));
    public static final Supplier<SwordItem> DAEDRIC_DAGGER = ITEMS.register("daedric_dagger", () -> new SwordItem(ItemTier.DAEDRIC, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> DAEDRIC_SWORD = ITEMS.register("daedric_sword", () -> new SwordItem(ItemTier.DAEDRIC, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> DAEDRIC_BATTLEAXE = ITEMS.register("daedric_battleaxe", () -> new SkyrimTwoHandedSword(ItemTier.DAEDRIC, (new Item.Properties()).fireResistant()));
    public static final Supplier<BowItem> DAEDRIC_BOW = ITEMS.register("daedric_bow", () -> new BowItem(new BowItem.Properties()));
    public static final Supplier<SkyrimTwoHandedSword> DAEDRIC_GREATSWORD = ITEMS.register("daedric_greatsword", () -> new SkyrimTwoHandedSword(ItemTier.DAEDRIC, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> DAEDRIC_MACE = ITEMS.register("daedric_mace", () -> new SwordItem(ItemTier.DAEDRIC, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> DAEDRIC_WAR_AXE = ITEMS.register("daedric_war_axe", () -> new SwordItem(ItemTier.DAEDRIC, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> DAEDRIC_WARHAMMER = ITEMS.register("daedric_warhammer", () -> new SkyrimTwoHandedSword(ItemTier.DAEDRIC, (new Item.Properties()).fireResistant()));
    // Dragonbone
//    public static final Supplier<SwordItem> DRAGONBONE_ARROW = ITEMS.register("dragonbone_arrow", () -> new SkyrimArrow.DragonboneArrow(new Item.Properties().fireResistant(), "Dragonbone Arrow"));
    public static final Supplier<SwordItem> DRAGONBONE_DAGGER = ITEMS.register("dragonbone_dagger", () -> new SwordItem(ItemTier.DRAGONBONE, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> DRAGONBONE_SWORD = ITEMS.register("dragonbone_sword", () -> new SwordItem(ItemTier.DRAGONBONE, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> DRAGONBONE_BATTLEAXE = ITEMS.register("dragonbone_battleaxe", () -> new SwordItem(ItemTier.DRAGONBONE, (new Item.Properties()).fireResistant()));
    public static final Supplier<BowItem> DRAGONBONE_BOW = ITEMS.register("dragonbone_bow", () -> new BowItem(new BowItem.Properties()));
    public static final Supplier<SkyrimTwoHandedSword> DRAGONBONE_GREATSWORD = ITEMS.register("dragonbone_greatsword", () -> new SkyrimTwoHandedSword(ItemTier.DRAGONBONE, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> DRAGONBONE_MACE = ITEMS.register("dragonbone_mace", () -> new SwordItem(ItemTier.DRAGONBONE, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> DRAGONBONE_WAR_AXE = ITEMS.register("dragonbone_war_axe", () -> new SwordItem(ItemTier.DRAGONBONE, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> DRAGONBONE_WARHAMMER = ITEMS.register("dragonbone_warhammer", () -> new SkyrimTwoHandedSword(ItemTier.DRAGONBONE, (new Item.Properties()).fireResistant()));
    // Dwarven
    public static final Supplier<SkyrimArmor> DWARVEN_HELMET = ITEMS.register("dwarven_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.DWARVEN, SkyrimArmor.Type.HELMET, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> DWARVEN_CHESTPLATE = ITEMS.register("dwarven_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.DWARVEN, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> DWARVEN_LEGGINGS = ITEMS.register("dwarven_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.DWARVEN, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> DWARVEN_BOOTS = ITEMS.register("dwarven_boots", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.DWARVEN, SkyrimArmor.Type.BOOTS, new Item.Properties(), true));
    public static final Supplier<ShieldItem> DWARVEN_SHIELD = ITEMS.register("dwarven_shield", () -> new ShieldItem(new Item.Properties().stacksTo(1)));
    //    public static final Supplier<SwordItem> DWARVEN_ARROW = ITEMS.register("dwarven_arrow", () -> new SkyrimArrow.DwarvenArrow(new Item.Properties().fireResistant(), "Dwarven Arrow"));
    public static final Supplier<SwordItem> DWARVEN_DAGGER = ITEMS.register("dwarven_dagger", () -> new SwordItem(ItemTier.DWARVEN, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> DWARVEN_SWORD = ITEMS.register("dwarven_sword", () -> new SwordItem(ItemTier.DWARVEN, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> DWARVEN_BATTLEAXE = ITEMS.register("dwarven_battleaxe", () -> new SkyrimTwoHandedSword(ItemTier.DWARVEN, (new Item.Properties()).fireResistant()));
    public static final Supplier<BowItem> DWARVEN_BOW = ITEMS.register("dwarven_bow", () -> new BowItem(new BowItem.Properties()));
    public static final Supplier<SkyrimTwoHandedSword> DWARVEN_GREATSWORD = ITEMS.register("dwarven_greatsword", () -> new SkyrimTwoHandedSword(ItemTier.DWARVEN, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> DWARVEN_MACE = ITEMS.register("dwarven_mace", () -> new SwordItem(ItemTier.DWARVEN, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> DWARVEN_WAR_AXE = ITEMS.register("dwarven_war_axe", () -> new SwordItem(ItemTier.DWARVEN, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> DWARVEN_WARHAMMER = ITEMS.register("dwarven_warhammer", () -> new SkyrimTwoHandedSword(ItemTier.DWARVEN, (new Item.Properties()).fireResistant()));
    // Ebony
    public static final Supplier<SkyrimArmor> EBONY_HELMET = ITEMS.register("ebony_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.EBONY, SkyrimArmor.Type.HELMET, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> EBONY_CHESTPLATE = ITEMS.register("ebony_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.EBONY, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> EBONY_LEGGINGS = ITEMS.register("ebony_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.EBONY, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> EBONY_BOOTS = ITEMS.register("ebony_boots", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.EBONY, SkyrimArmor.Type.BOOTS, new Item.Properties(), true));
    public static final Supplier<ShieldItem> EBONY_SHIELD = ITEMS.register("ebony_shield", () -> new ShieldItem(new Item.Properties().stacksTo(1)));
    //    public static final Supplier<SwordItem> EBONY_ARROW = ITEMS.register("ebony_arrow", () -> new SkyrimArrow.EbonyArrow(new Item.Properties().fireResistant(), "Ebony Arrow"));
    public static final Supplier<SwordItem> EBONY_DAGGER = ITEMS.register("ebony_dagger", () -> new SwordItem(ItemTier.EBONY, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> EBONY_SWORD = ITEMS.register("ebony_sword", () -> new SwordItem(ItemTier.EBONY, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> EBONY_BATTLEAXE = ITEMS.register("ebony_battleaxe", () -> new SkyrimTwoHandedSword(ItemTier.EBONY, (new Item.Properties()).fireResistant()));
    public static final Supplier<BowItem> EBONY_BOW = ITEMS.register("ebony_bow", () -> new BowItem(new BowItem.Properties()));
    public static final Supplier<SkyrimTwoHandedSword> EBONY_GREATSWORD = ITEMS.register("ebony_greatsword", () -> new SkyrimTwoHandedSword(ItemTier.EBONY, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> EBONY_MACE = ITEMS.register("ebony_mace", () -> new SwordItem(ItemTier.EBONY, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> EBONY_WAR_AXE = ITEMS.register("ebony_war_axe", () -> new SwordItem(ItemTier.EBONY, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> EBONY_WARHAMMER = ITEMS.register("ebony_warhammer", () -> new SkyrimTwoHandedSword(ItemTier.EBONY, (new Item.Properties()).fireResistant()));
    // Elven
    public static final Supplier<SkyrimArmor> ELVEN_HELMET = ITEMS.register("elven_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.ELVEN, SkyrimArmor.Type.HELMET, new Item.Properties(), false));
    public static final Supplier<SkyrimArmor> ELVEN_CHESTPLATE = ITEMS.register("elven_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.ELVEN, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), false));
    public static final Supplier<SkyrimArmor> ELVEN_LEGGINGS = ITEMS.register("elven_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.ELVEN, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), false));
    public static final Supplier<SkyrimArmor> ELVEN_BOOTS = ITEMS.register("elven_boots", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.ELVEN, SkyrimArmor.Type.BOOTS, new Item.Properties(), false));
    public static final Supplier<ShieldItem> ELVEN_SHIELD = ITEMS.register("elven_shield", () -> new ShieldItem(new Item.Properties().stacksTo(1)));
    //    public static final Supplier<SwordItem> ELVEN_ARROW = ITEMS.register("elven_arrow", () -> new SkyrimArrow.ElvenArrow(new Item.Properties().fireResistant(), "Elven Arrow"));
    public static final Supplier<SwordItem> ELVEN_DAGGER = ITEMS.register("elven_dagger", () -> new SwordItem(ItemTier.ELVEN, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> ELVEN_SWORD = ITEMS.register("elven_sword", () -> new SwordItem(ItemTier.ELVEN, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> ELVEN_BATTLEAXE = ITEMS.register("elven_battleaxe", () -> new SkyrimTwoHandedSword(ItemTier.ELVEN, (new Item.Properties()).fireResistant()));
    public static final Supplier<BowItem> ELVEN_BOW = ITEMS.register("elven_bow", () -> new BowItem(new BowItem.Properties()));
    public static final Supplier<SkyrimTwoHandedSword> ELVEN_GREATSWORD = ITEMS.register("elven_greatsword", () -> new SkyrimTwoHandedSword(ItemTier.ELVEN, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> ELVEN_MACE = ITEMS.register("elven_mace", () -> new SwordItem(ItemTier.ELVEN, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> ELVEN_WAR_AXE = ITEMS.register("elven_war_axe", () -> new SwordItem(ItemTier.ELVEN, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> ELVEN_WARHAMMER = ITEMS.register("elven_warhammer", () -> new SkyrimTwoHandedSword(ItemTier.ELVEN, (new Item.Properties()).fireResistant()));
    // Falmer
    public static final Supplier<SkyrimArmor> FALMER_HELMET = ITEMS.register("falmer_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.FALMER, SkyrimArmor.Type.HELMET, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> FALMER_CHESTPLATE = ITEMS.register("falmer_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.FALMER, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> FALMER_LEGGINGS = ITEMS.register("falmer_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.FALMER, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> FALMER_BOOTS = ITEMS.register("falmer_boots", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.FALMER, SkyrimArmor.Type.BOOTS, new Item.Properties(), true));
    //    public static final Supplier<SwordItem> FALMER_ARROW = ITEMS.register("falmer_arrow", () -> new SkyrimArrow.FalmerArrow(new Item.Properties().fireResistant(), "Falmer Arrow"));
    public static final Supplier<SwordItem> FALMER_SWORD = ITEMS.register("falmer_sword", () -> new SwordItem(ItemTier.FALMER, (new Item.Properties()).fireResistant()));
    public static final Supplier<BowItem> FALMER_BOW = ITEMS.register("falmer_bow", () -> new BowItem(new BowItem.Properties()));
    public static final Supplier<SwordItem> FALMER_WAR_AXE = ITEMS.register("falmer_war_axe", () -> new SwordItem(ItemTier.FALMER, (new Item.Properties()).fireResistant()));
    // Glass
    public static final Supplier<SkyrimArmor> GLASS_HELMET = ITEMS.register("glass_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.GLASS, SkyrimArmor.Type.HELMET, new Item.Properties(), false));
    public static final Supplier<SkyrimArmor> GLASS_CHESTPLATE = ITEMS.register("glass_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.GLASS, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), false));
    public static final Supplier<SkyrimArmor> GLASS_LEGGINGS = ITEMS.register("glass_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.GLASS, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), false));
    public static final Supplier<SkyrimArmor> GLASS_BOOTS = ITEMS.register("glass_boots", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.GLASS, SkyrimArmor.Type.BOOTS, new Item.Properties(), false));
    public static final Supplier<ShieldItem> GLASS_SHIELD = ITEMS.register("glass_shield", () -> new ShieldItem(new Item.Properties().stacksTo(1)));
    //    public static final Supplier<SwordItem> GLASS_ARROW = ITEMS.register("glass_arrow", () -> new SkyrimArrow.GlassArrow(new Item.Properties(), "Glass Arrow"));
    public static final Supplier<SwordItem> GLASS_DAGGER = ITEMS.register("glass_dagger", () -> new SwordItem(ItemTier.GLASS, (new Item.Properties())));
    public static final Supplier<SwordItem> GLASS_SWORD = ITEMS.register("glass_sword", () -> new SwordItem(ItemTier.GLASS, (new Item.Properties())));
    public static final Supplier<SkyrimTwoHandedSword> GLASS_BATTLEAXE = ITEMS.register("glass_battleaxe", () -> new SkyrimTwoHandedSword(ItemTier.GLASS, (new Item.Properties())));
    public static final Supplier<BowItem> GLASS_BOW = ITEMS.register("glass_bow", () -> new BowItem(new BowItem.Properties()));
    public static final Supplier<SkyrimTwoHandedSword> GLASS_GREATSWORD = ITEMS.register("glass_greatsword", () -> new SkyrimTwoHandedSword(ItemTier.GLASS, (new Item.Properties())));
    public static final Supplier<SwordItem> GLASS_MACE = ITEMS.register("glass_mace", () -> new SwordItem(ItemTier.GLASS, (new Item.Properties())));
    public static final Supplier<SwordItem> GLASS_WAR_AXE = ITEMS.register("glass_war_axe", () -> new SwordItem(ItemTier.GLASS, (new Item.Properties())));
    public static final Supplier<SkyrimTwoHandedSword> GLASS_WARHAMMER = ITEMS.register("glass_warhammer", () -> new SkyrimTwoHandedSword(ItemTier.GLASS, (new Item.Properties())));
    // Imperial
    public static final Supplier<SkyrimArmor> IMPERIAL_HELMET = ITEMS.register("imperial_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.IMPERIAL, SkyrimArmor.Type.HELMET, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> IMPERIAL_CHESTPLATE = ITEMS.register("imperial_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.IMPERIAL, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> IMPERIAL_LEGGINGS = ITEMS.register("imperial_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.IMPERIAL, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> IMPERIAL_BOOTS = ITEMS.register("imperial_boots", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.IMPERIAL, SkyrimArmor.Type.BOOTS, new Item.Properties(), true));
    public static final Supplier<SwordItem> IMPERIAL_SWORD = ITEMS.register("imperial_sword", () -> new SwordItem(Tiers.IRON, (new Item.Properties()).fireResistant()));
    // Iron (Skyrim)
    public static final Supplier<SkyrimArmor> IRON_HELMET = ITEMS.register("iron_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.IRON, SkyrimArmor.Type.HELMET, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> IRON_CHESTPLATE = ITEMS.register("iron_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.IRON, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> IRON_LEGGINGS = ITEMS.register("iron_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.IRON, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> IRON_BOOTS = ITEMS.register("iron_boots", () -> new SkyrimArmor(net.minecraft.world.item.ArmorMaterials.IRON, SkyrimArmor.Type.BOOTS, new Item.Properties(),true));
    public static final Supplier<ShieldItem> IRON_SHIELD = ITEMS.register("iron_shield", () -> new ShieldItem(new Item.Properties().stacksTo(1)));
    //    public static final Supplier<SwordItem> IRON_ARROW = ITEMS.register("iron_arrow", () -> new SkyrimArrow.IronArrow(new Item.Properties().fireResistant(), "Iron Arrow"));
    public static final Supplier<SwordItem> IRON_DAGGER = ITEMS.register("iron_dagger", () -> new SwordItem(Tiers.IRON, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> IRON_SWORD = ITEMS.register("iron_sword", () -> new SwordItem(Tiers.IRON, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> IRON_BATTLEAXE = ITEMS.register("iron_battleaxe", () -> new SkyrimTwoHandedSword(Tiers.IRON, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> IRON_GREATSWORD = ITEMS.register("iron_greatsword", () -> new SkyrimTwoHandedSword(Tiers.IRON, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> IRON_MACE = ITEMS.register("iron_mace", () -> new SwordItem(Tiers.IRON, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> IRON_WAR_AXE = ITEMS.register("iron_war_axe", () -> new SwordItem(Tiers.IRON, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> IRON_WARHAMMER = ITEMS.register("iron_warhammer", () -> new SkyrimTwoHandedSword(Tiers.IRON, (new Item.Properties()).fireResistant()));
    // Orcish
    public static final Supplier<SkyrimArmor> ORCISH_HELMET = ITEMS.register("orcish_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.ORCISH, SkyrimArmor.Type.HELMET, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> ORCISH_CHESTPLATE = ITEMS.register("orcish_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.ORCISH, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> ORCISH_LEGGINGS = ITEMS.register("orcish_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.ORCISH, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> ORCISH_BOOTS = ITEMS.register("orcish_boots", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.ORCISH, SkyrimArmor.Type.BOOTS, new Item.Properties(), true));
    public static final Supplier<ShieldItem> ORCISH_SHIELD = ITEMS.register("orcish_shield", () -> new ShieldItem(new Item.Properties().stacksTo(1)));
    //    public static final Supplier<SwordItem> ORCISH_ARROW = ITEMS.register("orcish_arrow", () -> new SkyrimArrow.OrcishArrow(new Item.Properties().fireResistant(), "Orcish Arrow"));
    public static final Supplier<SwordItem> ORCISH_DAGGER = ITEMS.register("orcish_dagger", () -> new SwordItem(ItemTier.ORCISH, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> ORCISH_SWORD = ITEMS.register("orcish_sword", () -> new SwordItem(ItemTier.ORCISH, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> ORCISH_BATTLEAXE = ITEMS.register("orcish_battleaxe", () -> new SkyrimTwoHandedSword(ItemTier.ORCISH, (new Item.Properties()).fireResistant()));
    public static final Supplier<BowItem> ORCISH_BOW = ITEMS.register("orcish_bow", () -> new BowItem(new BowItem.Properties()));
    public static final Supplier<SkyrimTwoHandedSword> ORCISH_GREATSWORD = ITEMS.register("orcish_greatsword", () -> new SkyrimTwoHandedSword(ItemTier.ORCISH, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> ORCISH_MACE = ITEMS.register("orcish_mace", () -> new SwordItem(ItemTier.ORCISH, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> ORCISH_WAR_AXE = ITEMS.register("orcish_war_axe", () -> new SwordItem(ItemTier.ORCISH, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> ORCISH_WARHAMMER = ITEMS.register("orcish_warhammer", () -> new SkyrimTwoHandedSword(ItemTier.ORCISH, (new Item.Properties()).fireResistant()));
    // Steel
//    public static final Supplier<SkyrimArmor> STEEL_HELMET = ITEMS.register("steel_helmet", () -> new SkyrimArmor(ArmorMaterials.STEEL, ArmorItem.Type.HELMET, new Item.Properties(), true));
//    public static final Supplier<SkyrimArmor> STEEL_CHESTPLATE = ITEMS.register("steel_chestplate", () -> new SkyrimArmor(ArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties(), true));
//    public static final Supplier<SkyrimArmor> STEEL_LEGGINGS = ITEMS.register("steel_leggings", () -> new SkyrimArmor(ArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS, new Item.Properties(), true));
//    public static final Supplier<SkyrimArmor> STEEL_BOOTS = ITEMS.register("steel_boots", () -> new SkyrimArmor(ArmorMaterials.STEEL, ArmorItem.Type.BOOTS, new Item.Properties(), true));
    public static final Supplier<ShieldItem> STEEL_SHIELD = ITEMS.register("steel_shield", () -> new ShieldItem(new Item.Properties().stacksTo(1)));
    //    public static final Supplier<SwordItem> STEEL_ARROW = ITEMS.register("steel_arrow", () -> new SkyrimArrow.SteelArrow(new Item.Properties().fireResistant(), "Steel Arrow"));
    public static final Supplier<SwordItem> STEEL_DAGGER = ITEMS.register("steel_dagger", () -> new SwordItem(ItemTier.STEEL, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> STEEL_SWORD = ITEMS.register("steel_sword", () -> new SwordItem(ItemTier.STEEL, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> STEEL_BATTLEAXE = ITEMS.register("steel_battleaxe", () -> new SkyrimTwoHandedSword(ItemTier.STEEL, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> STEEL_GREATSWORD = ITEMS.register("steel_greatsword", () -> new SkyrimTwoHandedSword(ItemTier.STEEL, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> STEEL_MACE = ITEMS.register("steel_mace", () -> new SwordItem(ItemTier.STEEL, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> STEEL_WAR_AXE = ITEMS.register("steel_war_axe", () -> new SwordItem(ItemTier.STEEL, (new Item.Properties()).fireResistant()));
    public static final Supplier<SkyrimTwoHandedSword> STEEL_WARHAMMER = ITEMS.register("steel_warhammer", () -> new SkyrimTwoHandedSword(ItemTier.STEEL, (new Item.Properties()).fireResistant()));
    // Stormcloak + Stormcloak Officer Armor
    public static final Supplier<SkyrimArmor> STORMCLOAK_OFFICER_HELMET = ITEMS.register("stormcloak_officer_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.STORMCLOAK, SkyrimArmor.Type.HELMET, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> STORMCLOAK_OFFICER_CHESTPLATE = ITEMS.register("stormcloak_officer_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.STORMCLOAK, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> STORMCLOAK_OFFICER_LEGGINGS = ITEMS.register("stormcloak_officer_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.STORMCLOAK, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), true));
    public static final Supplier<SkyrimArmor> STORMCLOAK_OFFICER_BOOTS = ITEMS.register("stormcloak_officer_boots", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.STORMCLOAK, SkyrimArmor.Type.BOOTS, new Item.Properties(), true));

    //// MISC ////
    // Hunting bow
    public static final Supplier<BowItem> HUNTING_BOW = ITEMS.register("hunting_bow", () -> new BowItem(new BowItem.Properties()));
    // Longbow
    public static final Supplier<BowItem> LONGBOW = ITEMS.register("longbow", () -> new BowItem(new BowItem.Properties()));
    // Scaled armor
    public static final Supplier<SkyrimArmor> SCALED_HELMET = ITEMS.register("scaled_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.SCALED, SkyrimArmor.Type.HELMET, new Item.Properties(), false));
    public static final Supplier<SkyrimArmor> SCALED_CHESTPLATE = ITEMS.register("scaled_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.SCALED, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), false));
    public static final Supplier<SkyrimArmor> SCALED_LEGGINGS = ITEMS.register("scaled_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.SCALED, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), false));
    public static final Supplier<SkyrimArmor> SCALED_BOOTS = ITEMS.register("scaled_boots", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.SCALED, SkyrimArmor.Type.BOOTS, new Item.Properties(), false));
    // Hide armor
    public static final Supplier<SkyrimArmor> HIDE_HELMET = ITEMS.register("hide_helmet", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.HIDE, SkyrimArmor.Type.HELMET, new Item.Properties(), false));
    public static final Supplier<SkyrimArmor> HIDE_CHESTPLATE = ITEMS.register("hide_chestplate", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.HIDE, SkyrimArmor.Type.CHESTPLATE, new Item.Properties(), false));
    public static final Supplier<SkyrimArmor> HIDE_LEGGINGS = ITEMS.register("hide_leggings", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.HIDE, SkyrimArmor.Type.LEGGINGS, new Item.Properties(), false));
    public static final Supplier<SkyrimArmor> HIDE_BOOTS = ITEMS.register("hide_boots", () -> new SkyrimArmor(com.ryankshah.skyrimcraft.item.ArmorMaterials.HIDE, SkyrimArmor.Type.BOOTS, new Item.Properties(), false));

    // Swords
    public static final Supplier<SwordItem> CHILLREND = ITEMS.register("chillrend", () -> new SwordItem(ItemTier.STEEL, (new Item.Properties()).fireResistant()));
    public static final Supplier<SwordItem> DAWNBREAKER = ITEMS.register("dawnbreaker", () -> new SwordItem(ItemTier.STEEL, (new Item.Properties()).fireResistant()));


    //// MAGIC ////
    // Staff
    public static final Supplier<Item> STAFF = ITEMS.register("staff", () -> new Item(new Item.Properties()));
    // Spell books
    public static final Supplier<Item> FIREBALL_SPELLBOOK = ITEMS.register("fireball_spellbook", () -> new SpellBook(new Item.Properties().rarity(Rarity.EPIC), SpellRegistry.FIREBALL));
    public static final Supplier<Item> TURN_UNDEAD_SPELLBOOK = ITEMS.register("turn_undead_spellbook", () -> new SpellBook(new Item.Properties().rarity(Rarity.EPIC), SpellRegistry.TURN_UNDEAD));
    public static final Supplier<Item> CONJURE_FAMILIAR_SPELLBOOK = ITEMS.register("conjure_familiar_spellbook", () -> new SpellBook(new Item.Properties().rarity(Rarity.EPIC), SpellRegistry.CONJURE_FAMILIAR));
    public static final Supplier<Item> HEALING_SPELLBOOK = ITEMS.register("healing_spellbook", () -> new SpellBook(new Item.Properties().rarity(Rarity.EPIC), SpellRegistry.HEALING));
    public static final Supplier<Item> LIGHTNING_SPELLBOOK = ITEMS.register("lightning_spellbook", () -> new SpellBook(new Item.Properties().rarity(Rarity.EPIC), SpellRegistry.LIGHTNING));
    public static final Supplier<Item> FLAME_CLOAK_SPELLBOOK = ITEMS.register("flame_cloak_spellbook", () -> new SpellBook(new Item.Properties().rarity(Rarity.EPIC), SpellRegistry.FLAME_CLOAK));
    public static final Supplier<Item> ICE_SPIKE_SPELLBOOK = ITEMS.register("ice_spike_spellbook", () -> new SpellBook(new Item.Properties().rarity(Rarity.EPIC), SpellRegistry.ICE_SPIKE));
    public static final Supplier<Item> CONJURE_ZOMBIE_SPELLBOOK = ITEMS.register("conjure_zombie_spellbook", () -> new SpellBook(new Item.Properties().rarity(Rarity.EPIC), SpellRegistry.CONJURE_ZOMBIE));
    public static final Supplier<Item> DETECT_LIFE_SPELLBOOK = ITEMS.register("detect_life_spellbook", () -> new SpellBook(new Item.Properties().rarity(Rarity.EPIC), SpellRegistry.DETECT_LIFE));
    public static final Supplier<Item> CANDLELIGHT_SPELLBOOK = ITEMS.register("candlelight_spellbook", () -> new SpellBook(new Item.Properties().rarity(Rarity.EPIC), SpellRegistry.CANDLELIGHT));

    // Skill books
    public static final Supplier<Item> ALTERATION_SKILLBOOK = ITEMS.register("alteration_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.ALTERATION));
    public static final Supplier<Item> CONJURATION_SKILLBOOK = ITEMS.register("conjuration_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.CONJURATION));
    public static final Supplier<Item> DESTRUCTION_SKILLBOOK = ITEMS.register("destruction_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.DESTRUCTION));
    public static final Supplier<Item> ILLUSION_SKILLBOOK = ITEMS.register("illusion_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.ILLUSION));
    public static final Supplier<Item> RESTORATION_SKILLBOOK = ITEMS.register("restoration_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.RESTORATION));
    public static final Supplier<Item> ENCHANTING_SKILLBOOK = ITEMS.register("enchanting_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.ENCHANTING));
    public static final Supplier<Item> ONE_HANDED_SKILLBOOK = ITEMS.register("one_handed_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.ONE_HANDED));
    public static final Supplier<Item> TWO_HANDED_SKILLBOOK = ITEMS.register("two_handed_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.TWO_HANDED));
    public static final Supplier<Item> ARCHERY_SKILLBOOK = ITEMS.register("archery_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.ARCHERY));
    public static final Supplier<Item> BLOCK_SKILLBOOK = ITEMS.register("block_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.BLOCK));
    public static final Supplier<Item> SMITHING_SKILLBOOK = ITEMS.register("smithing_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.SMITHING));
    public static final Supplier<Item> HEAVY_ARMOR_SKILLBOOK = ITEMS.register("heavy_armor_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.HEAVY_ARMOR));
    public static final Supplier<Item> LIGHT_ARMOR_SKILLBOOK = ITEMS.register("light_armor_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.LIGHT_ARMOR));
    public static final Supplier<Item> PICKPOCKET_SKILLBOOK = ITEMS.register("pickpocket_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.PICKPOCKET));
    public static final Supplier<Item> LOCKPICKING_SKILLBOOK = ITEMS.register("lockpicking_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.LOCKPICKING));
    public static final Supplier<Item> SNEAK_SKILLBOOK = ITEMS.register("sneak_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.SNEAK));
    public static final Supplier<Item> ALCHEMY_SKILLBOOK = ITEMS.register("alchemy_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.ALCHEMY));
    public static final Supplier<Item> SPEECH_SKILLBOOK = ITEMS.register("speech_skillbook", () -> new SkillBook(new Item.Properties().rarity(Rarity.EPIC), SkillRegistry.SPEECH));

    //// Jewellery
    public static final Supplier<Item> GOLD_RING = ITEMS.register("gold_ring", () -> new SkyrimRing());
    public static final Supplier<Item> GOLD_DIAMOND_RING = ITEMS.register("gold_diamond_ring", () -> new SkyrimRing());
    public static final Supplier<Item> GOLD_EMERALD_RING = ITEMS.register("gold_emerald_ring", () -> new SkyrimRing());
    public static final Supplier<Item> GOLD_SAPPHIRE_RING = ITEMS.register("gold_sapphire_ring", () -> new SkyrimRing());
    public static final Supplier<Item> SILVER_RING = ITEMS.register("silver_ring", () -> new SkyrimRing());
    public static final Supplier<Item> SILVER_AMETHYST_RING = ITEMS.register("silver_amethyst_ring", () -> new SkyrimRing());
    public static final Supplier<Item> SILVER_GARNET_RING = ITEMS.register("silver_garnet_ring", () -> new SkyrimRing());
    public static final Supplier<Item> SILVER_RUBY_RING = ITEMS.register("silver_ruby_ring", () -> new SkyrimRing());
    public static final Supplier<Item> ASGEIRS_WEDDING_BAND = ITEMS.register("asgeirs_wedding_band", () -> new SkyrimRing());
    public static final Supplier<Item> AHZIDALS_RING_OF_ARCANA = ITEMS.register("ahzidals_ring_of_arcana", () -> new SkyrimRing());
    public static final Supplier<Item> BALWENS_ORNAMENTAL_RING = ITEMS.register("balwens_ornamental_ring", () -> new SkyrimRing());
    public static final Supplier<Item> BONE_HAWK_RING = ITEMS.register("bone_hawk_ring", () -> new SkyrimRing());
    public static final Supplier<Item> CALCELMOS_RING = ITEMS.register("calcelmos_ring", () -> new SkyrimRing());
    public static final Supplier<Item> ENCHANTED_RING = ITEMS.register("enchanted_ring", () -> new SkyrimRing());
    public static final Supplier<Item> FJOLAS_WEDDING_BAND = ITEMS.register("fjolas_wedding_band", () -> new SkyrimRing());
    public static final Supplier<Item> ILAS_TEIS_RING = ITEMS.register("ilas_teis_ring", () -> new SkyrimRing());
    public static final Supplier<Item> KATARINAS_ORNAMENTAL_RING = ITEMS.register("katarinas_ornamental_ring", () -> new SkyrimRing());
    public static final Supplier<Item> MADESIS_SILVER_RING = ITEMS.register("madesis_silver_ring", () -> new SkyrimRing());
    public static final Supplier<Item> MUIRIS_RING = ITEMS.register("muiris_ring", () -> new SkyrimRing());
    public static final Supplier<Item> NIGHTWEAVERS_BAND = ITEMS.register("nightweavers_band", () -> new SkyrimRing());
    public static final Supplier<Item> PITHIS_ORNAMENTAL_RING = ITEMS.register("pithis_ornamental_ring", () -> new SkyrimRing());
    public static final Supplier<Item> RING_OF_NAMIRA = ITEMS.register("ring_of_namira", () -> new SkyrimRing());
    public static final Supplier<Item> TREOYS_ORNAMENTAL_RING = ITEMS.register("treoys_ornamental_ring", () -> new SkyrimRing());

    public static final Supplier<Item> GOLD_NECKLACE = ITEMS.register("gold_necklace", () -> new SkyrimNecklace());
    public static final Supplier<Item> GOLD_DIAMOND_NECKLACE = ITEMS.register("gold_diamond_necklace", () -> new SkyrimNecklace());
    public static final Supplier<Item> GOLD_JEWELLED_NECKLACE = ITEMS.register("gold_jewelled_necklace", () -> new SkyrimNecklace());
    public static final Supplier<Item> GOLD_RUBY_NECKLACE = ITEMS.register("gold_ruby_necklace", () -> new SkyrimNecklace());
    public static final Supplier<Item> SILVER_NECKLACE = ITEMS.register("silver_necklace", () -> new SkyrimNecklace());
    public static final Supplier<Item> SILVER_EMERALD_NECKLACE = ITEMS.register("silver_emerald_necklace", () -> new SkyrimNecklace());
    public static final Supplier<Item> SILVER_JEWELLED_NECKLACE = ITEMS.register("silver_jewelled_necklace", () -> new SkyrimNecklace());
    public static final Supplier<Item> SILVER_SAPPHIRE_NECKLACE = ITEMS.register("silver_sapphire_necklace", () -> new SkyrimNecklace());

    public static final RegistryObject<Item, Item> MINOR_MAGICKA_POTION = ITEMS.register("minor_magicka_potion", () -> new MagickaPotion(new Item.Properties(), 2.0f));
    public static final RegistryObject<Item, Item> MAGICKA_POTION = ITEMS.register("magicka_potion", () -> new MagickaPotion(new Item.Properties(), 4.0f));
    public static final RegistryObject<Item, Item> PLENTIFUL_MAGICKA_POTION = ITEMS.register("plentiful_magicka_potion", () -> new MagickaPotion(new Item.Properties(), 6.0f));
    public static final RegistryObject<Item, Item> VIGOROUS_MAGICKA_POTION = ITEMS.register("vigorous_magicka_potion", () -> new MagickaPotion(new Item.Properties(), 8.0f));
    public static final RegistryObject<Item, Item> EXTREME_MAGICKA_POTION = ITEMS.register("extreme_magicka_potion", () -> new MagickaPotion(new Item.Properties(), 12.0f));
    public static final RegistryObject<Item, Item> ULTIMATE_MAGICKA_POTION = ITEMS.register("ultimate_magicka_potion", () -> new MagickaPotion(new Item.Properties(), 20.0f));

    public static final RegistryObject<Item, Item> LASTING_POTENCY_POTION = ITEMS.register("lasting_potency_potion", () -> new RegenMagickaPotion(new Item.Properties(),1.5f, 600));
    public static final RegistryObject<Item, Item> DRAUGHT_LASTING_POTENCY_POTION = ITEMS.register("draught_lasting_potency_potion", () -> new RegenMagickaPotion(new Item.Properties(), 1.6f, 600));
    public static final RegistryObject<Item, Item> SOLUTION_LASTING_POTENCY_POTION = ITEMS.register("solution_lasting_potency_potion", () -> new RegenMagickaPotion(new Item.Properties(), 1.7f, 600));
    public static final RegistryObject<Item, Item> PHILTER_LASTING_POTENCY_POTION = ITEMS.register("philter_lasting_potency_potion", () -> new RegenMagickaPotion(new Item.Properties(), 1.8f, 600));
    public static final RegistryObject<Item, Item> ELIXIR_LASTING_POTENCY_POTION = ITEMS.register("elixir_lasting_potency_potion", () -> new RegenMagickaPotion(new Item.Properties(), 2f, 600));

    public static final RegistryObject<Item, Item> POTION_OF_HAGGLING = ITEMS.register("potion_of_haggling", () -> new HagglingPotion(new Item.Properties(),1, 30));
    public static final RegistryObject<Item, Item> DRAUGHT_OF_HAGGLING = ITEMS.register("draught_of_haggling", () -> new HagglingPotion(new Item.Properties(), 1, 60));
    public static final RegistryObject<Item, Item> PHILTER_OF_HAGGLING = ITEMS.register("philter_of_haggling", () -> new HagglingPotion(new Item.Properties(), 2, 30));
    public static final RegistryObject<Item, Item> ELIXIR_OF_HAGGLING = ITEMS.register("elixir_of_haggling", () -> new HagglingPotion(new Item.Properties(), 3, 60));

    // Unique and non-levelled potions
    public static final RegistryObject<Item, Item> PHILTER_OF_THE_PHANTOM_POTION = ITEMS.register("philter_of_the_phantom_potion", () -> new SpectralPotion(new Item.Properties(), 600));
    public static final RegistryObject<Item, Item> POTION_OF_WATERWALKING = ITEMS.register("potion_of_waterwalking", () -> new WaterWalkingPotion(new Item.Properties(), 1, 1200));

    public static final RegistryObject<Item, Item> SABRE_CAT_SPAWN_EGG = ITEMS.register("sabre_cat_spawn_egg", () -> new SpawnEggItem(EntityRegistry.SABRE_CAT.get(), 0x505050, 0x606060, new Item.Properties()));
    public static final RegistryObject<Item, Item> GIANT_SPAWN_EGG = ITEMS.register("giant_spawn_egg", () -> new SpawnEggItem(EntityRegistry.GIANT.get(), 0x505050, 0x606060, new Item.Properties()));
    public static final RegistryObject<Item, Item> DRAGON_SPAWN_EGG = ITEMS.register("dragon_spawn_egg", () -> new SpawnEggItem(EntityRegistry.DRAGON.get(), 0x505050, 0x606060, new Item.Properties()));
    public static final RegistryObject<Item, Item> BLUE_BUTTERFLY_SPAWN_EGG = ITEMS.register("blue_butterfly_spawn_egg", () -> new SpawnEggItem(EntityRegistry.BLUE_BUTTERFLY.get(), 0x505050, 0x606060, new Item.Properties()));
    public static final RegistryObject<Item, Item> MONARCH_BUTTERFLY_SPAWN_EGG = ITEMS.register("monarch_butterfly_spawn_egg", () -> new SpawnEggItem(EntityRegistry.MONARCH_BUTTERFLY.get(), 0x505050, 0x606060, new Item.Properties()));
    public static final RegistryObject<Item, Item> ORANGE_DARTWING_SPAWN_EGG = ITEMS.register("orange_dartwing_spawn_egg", () -> new SpawnEggItem(EntityRegistry.ORANGE_DARTWING.get(), 0x505050, 0x606060, new Item.Properties()));
    public static final RegistryObject<Item, Item> BLUE_DARTWING_SPAWN_EGG = ITEMS.register("blue_dartwing_spawn_egg", () -> new SpawnEggItem(EntityRegistry.BLUE_DARTWING.get(), 0x505050, 0x606060, new Item.Properties()));
    public static final RegistryObject<Item, Item> LUNAR_MOTH_SPAWN_EGG = ITEMS.register("lunar_moth_spawn_egg", () -> new SpawnEggItem(EntityRegistry.LUNAR_MOTH.get(), 0x505050, 0x606060, new Item.Properties()));
    public static final RegistryObject<Item, Item> TORCHBUG_SPAWN_EGG = ITEMS.register("torchbug_spawn_egg", () -> new SpawnEggItem(EntityRegistry.TORCHBUG.get(), 0x505050, 0x606060, new Item.Properties()));
    public static final RegistryObject<Item, Item> DRAUGR_SPAWN_EGG = ITEMS.register("draugr_spawn_egg", () -> new SpawnEggItem(EntityRegistry.DRAUGR.get(), 0x505050, 0x606060, new Item.Properties()));
    public static final RegistryObject<Item, Item> KHAJIIT_SPAWN_EGG = ITEMS.register("khajiit_spawn_egg", () -> new SpawnEggItem(EntityRegistry.KHAJIIT.get(), 0x505050, 0x606060, new Item.Properties()));
    public static final RegistryObject<Item, Item> FALMER_SPAWN_EGG = ITEMS.register("falmer_spawn_egg", () -> new SpawnEggItem(EntityRegistry.FALMER.get(), 0x505050, 0x606060, new Item.Properties()));

    public static void registerItemModelProperties() {
        registerTwoHandedProperties(ANCIENT_NORD_BATTLEAXE.get());
        registerTwoHandedProperties(ANCIENT_NORD_GREATSWORD.get());
        registerBowProperties(ANCIENT_NORD_BOW.get());

        registerTwoHandedProperties(DAEDRIC_BATTLEAXE.get());
        registerTwoHandedProperties(DAEDRIC_GREATSWORD.get());
        registerTwoHandedProperties(DAEDRIC_WARHAMMER.get());
        registerBowProperties(DAEDRIC_BOW.get());
        registerShield(DAEDRIC_SHIELD.get());

        registerTwoHandedProperties(DRAGONBONE_BATTLEAXE.get());
        registerTwoHandedProperties(DRAGONBONE_GREATSWORD.get());
        registerTwoHandedProperties(DRAGONBONE_WARHAMMER.get());
        registerBowProperties(DRAGONBONE_BOW.get());

        registerTwoHandedProperties(DWARVEN_BATTLEAXE.get());
        registerTwoHandedProperties(DWARVEN_GREATSWORD.get());
        registerTwoHandedProperties(DWARVEN_WARHAMMER.get());
        registerBowProperties(DWARVEN_BOW.get());
        registerShield(DWARVEN_SHIELD.get());

        registerTwoHandedProperties(EBONY_BATTLEAXE.get());
        registerTwoHandedProperties(EBONY_GREATSWORD.get());
        registerTwoHandedProperties(EBONY_WARHAMMER.get());
        registerBowProperties(EBONY_BOW.get());
        registerShield(EBONY_SHIELD.get());

        registerTwoHandedProperties(ELVEN_BATTLEAXE.get());
        registerTwoHandedProperties(ELVEN_GREATSWORD.get());
        registerTwoHandedProperties(ELVEN_WARHAMMER.get());
        registerBowProperties(ELVEN_BOW.get());
        registerShield(ELVEN_SHIELD.get());

        registerTwoHandedProperties(GLASS_BATTLEAXE.get());
        registerTwoHandedProperties(GLASS_GREATSWORD.get());
        registerTwoHandedProperties(GLASS_WARHAMMER.get());
        registerBowProperties(GLASS_BOW.get());
        registerShield(GLASS_SHIELD.get());

        registerTwoHandedProperties(IRON_BATTLEAXE.get());
        registerTwoHandedProperties(IRON_GREATSWORD.get());
        registerTwoHandedProperties(IRON_WARHAMMER.get());
        registerShield(IRON_SHIELD.get());

        registerTwoHandedProperties(ORCISH_BATTLEAXE.get());
        registerTwoHandedProperties(ORCISH_GREATSWORD.get());
        registerTwoHandedProperties(ORCISH_WARHAMMER.get());
        registerBowProperties(ORCISH_BOW.get());
        registerShield(ORCISH_SHIELD.get());

        registerTwoHandedProperties(STEEL_BATTLEAXE.get());
        registerTwoHandedProperties(STEEL_GREATSWORD.get());
        registerTwoHandedProperties(STEEL_WARHAMMER.get());
        registerShield(STEEL_SHIELD.get());

        // misc
        registerBowProperties(HUNTING_BOW.get());
        registerBowProperties(LONGBOW.get());
    }

    private static void registerTwoHandedProperties(Item item) {
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(Constants.MODID,  "no_use"), ClientUtil::noUse);
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(Constants.MODID,  "blocking"), ClientUtil::blocking);
    }

    private static void registerBowProperties(Item item) {
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(Constants.MODID,  "pulling"), ClientUtil::pulling);
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(Constants.MODID,  "pull"), ClientUtil::pull);
    }

    private static void registerShield(Item item) {
        ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(Constants.MODID,  "blocking"), ClientUtil::blocking);
    }
}