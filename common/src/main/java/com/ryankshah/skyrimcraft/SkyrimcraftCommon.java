package com.ryankshah.skyrimcraft;

import com.google.common.collect.ImmutableList;
import com.ryankshah.skyrimcraft.data.loot_table.condition.type.SkyrimcraftLootConditionTypes;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.registry.*;
import net.minecraft.world.entity.EntityType;

/**
 * TODO:
 * - Add accessories support to replace Curios and add the rings/necklaces/etc with powers/abilities/stats/etc.
 * - Fix item tiers (i.e., skyrim swords are not doing damage as they should....) - potentially check armor tiers too.
 * - Add the following foods, textures and oven recipes:
 *   - Boiled Creme Treat
 *   - Braided Bread
 *   - Clam Chowder
 *   - Elsweyr Fondue
 *   - Grilled Leeks
 *   - Honey Nut Treat
 *   - Jazbay grape bush (grape item texture done)
 *   - Juniper Berry bush (berry item texture done)
 *   - Lavender Dumpling
 *   - Mammoth Cheese Bowl (Found in giant camps?)
 *   - Potato Soup
 *   - Rabbit Haunch
 *   - Snowberries bush (berry item texture done)
 *   - Snowberry Crostata -- texture done, make a food item
 */
public class SkyrimcraftCommon
{
    public static void init() {
        ModEffects.init();
        AdvancementTriggersRegistry.init();
        AttributeRegistry.init();
        SkyrimcraftLootConditionTypes.init();
        DamageTypeRegistry.init();
        KeysRegistry.init();
        EntityRegistry.init();

        ItemRegistry.init();
        BlockRegistry.init();
        BlockEntityRegistry.init();
        CreativeTabRegistry.init();
        RecipeRegistry.init();
        TagsRegistry.init();

        ParticleRegistry.init();
        ParticleRenderTypeRegistry.init();
        RenderTypeRegistry.init();

        VillagerRegistry.init();

        FeatureRegistry.init();
        StructureRegistry.init();

        Networking.load();
    }

    public static Iterable<EntityType<?>> getPickpocketableEntities() {
        return ImmutableList.of(EntityType.VILLAGER);//, EntityInit.KHAJIIT.get(), EntityInit.FALMER.get());
    }
}