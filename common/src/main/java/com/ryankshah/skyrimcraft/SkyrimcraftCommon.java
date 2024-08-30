package com.ryankshah.skyrimcraft;

import com.google.common.collect.ImmutableList;
import com.ryankshah.skyrimcraft.data.loot_table.condition.type.SkyrimcraftLootConditionTypes;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.registry.*;
import net.minecraft.world.entity.EntityType;

public class SkyrimcraftCommon
{
    public static void init() {
        AdvancementTriggersRegistry.init();
        AttributeRegistry.init();
        SkyrimcraftLootConditionTypes.init();
        DamageTypeRegistry.init();
        ModEffects.init();
        KeysRegistry.init();

        ItemRegistry.init();
        BlockRegistry.init();
        BlockEntityRegistry.init();
        CreativeTabRegistry.init();
        RecipeRegistry.init();
        TagsRegistry.init();

        EntityRegistry.init();
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