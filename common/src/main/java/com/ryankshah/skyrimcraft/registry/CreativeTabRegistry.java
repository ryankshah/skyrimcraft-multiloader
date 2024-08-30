package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.item.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

public class CreativeTabRegistry
{
    public static void init() {}

    public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, Constants.MODID);

    // tab title
    public static String SKYRIMCRAFT_BLOCK_TAB_TITLE = "creativetab.skyrimcraft.blocks";
    public static String SKYRIMCRAFT_INGREDIENT_TAB_TITLE = "creativetab.skyrimcraft.ingredients";
    public static String SKYRIMCRAFT_FOOD_TAB_TITLE = "creativetab.skyrimcraft.food";
    public static String SKYRIMCRAFT_COMBAT_TAB_TITLE = "creativetab.skyrimcraft.combat";
    public static String SKYRIMCRAFT_MAGIC_TITLE = "creativetab.skyrimcraft.magic";
    public static String SKYRIMCRAFT_JEWELLERY_TAB_TITLE = "creativetab.skyrimcraft.jewellery";
    public static String SKYRIMCRAFT_ALL_TITLE = "creativetab.skyrimcraft.all";

    public static final RegistryObject<CreativeModeTab, CreativeModeTab> SKYRIMCRAFT_BLOCKS_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_blocks_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {
            ItemRegistry.ITEMS.getEntries()
                    .stream()
                    .filter(i -> i.get() instanceof BlockItem && !(i.get() instanceof SkyrimBlockItemIngredient))
                    .map(RegistryObject::get)
                    .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(BlockRegistry.SHOUT_BLOCK.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_BLOCK_TAB_TITLE));

        return builder.build();
    });

    public static final RegistryObject<CreativeModeTab, CreativeModeTab> SKYRIMCRAFT_COMBAT_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_combat_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {
            ItemRegistry.ITEMS.getEntries()
                    .stream()
                    .filter(item -> item.get() instanceof SwordItem || item.get() instanceof ShieldItem || item.get() instanceof BowItem || item.get() instanceof ArmorItem)
                    .map(RegistryObject::get)
                    .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ItemRegistry.DAEDRIC_SWORD.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_COMBAT_TAB_TITLE));

        return builder.build();
    });

    public static final RegistryObject<CreativeModeTab, CreativeModeTab> SKYRIMCRAFT_ALL_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_all_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {
            ItemRegistry.ITEMS.getEntries()
                    .stream()
                    .map(RegistryObject::get)
                    .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ItemRegistry.DAEDRA_HEART.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_ALL_TITLE));

        return builder.build();
    });

    public static final RegistryObject<CreativeModeTab, CreativeModeTab> SKYRIMCRAFT_MAGIC_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_magic_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {
            ItemRegistry.ITEMS.getEntries()
                    .stream()
                    .filter(item -> item.get() instanceof SpellBook)
                    .map(RegistryObject::get)
                    .forEach(output::accept);
            output.accept(ItemRegistry.STAFF.get());
        });

        builder.icon(() -> new ItemStack(ItemRegistry.TURN_UNDEAD_SPELLBOOK.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_MAGIC_TITLE));

        return builder.build();
    });

    public static final RegistryObject<CreativeModeTab, CreativeModeTab> SKYRIMCRAFT_INGREDIENTS_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_ingredients_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {
            ItemRegistry.ITEMS.getEntries()
                    .stream()
                    .filter(item -> item.get() instanceof SkyrimIngredient || item.get() instanceof SkyrimBlockItemIngredient)
                    .map(RegistryObject::get)
                    .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ItemRegistry.ORANGE_DARTWING.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_INGREDIENT_TAB_TITLE));

        return builder.build();
    });

    public static final RegistryObject<CreativeModeTab, CreativeModeTab> SKYRIMCRAFT_JEWELLERY_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_jewellery_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {ItemRegistry.ITEMS.getEntries()
                .stream()
                .filter(item -> item.get() instanceof SkyrimRing || item.get() instanceof SkyrimNecklace)
                .map(RegistryObject::get)
                .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ItemRegistry.RING_OF_NAMIRA.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_JEWELLERY_TAB_TITLE));

        return builder.build();
    });

    public static final RegistryObject<CreativeModeTab, CreativeModeTab> SKYRIMCRAFT_FOOD_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_food_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {
            output.accept(ItemRegistry.BUTTER.get());
            output.accept(ItemRegistry.FLOUR.get());
            ItemRegistry.ITEMS.getEntries()
                    .stream()
//                    .filter(item -> item.get().getFoodProperties(new ItemStack(item), null) != null || item.get() instanceof PotionItem) //TODO: item -> item.get().isEdible() ||
                    .map(RegistryObject::get)
                    .map(Item::getDefaultInstance)
                    .filter(stack -> stack.getComponents().has(DataComponents.FOOD)|| stack.getItem() instanceof PotionItem)
                    .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ItemRegistry.SWEET_ROLL.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_FOOD_TAB_TITLE));

        return builder.build();
    });
}