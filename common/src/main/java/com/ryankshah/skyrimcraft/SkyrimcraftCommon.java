package com.ryankshah.skyrimcraft;

import com.google.common.collect.ImmutableList;
import com.ryankshah.skyrimcraft.data.loot_table.condition.type.SkyrimcraftLootConditionTypes;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.network.Networking;
import com.ryankshah.skyrimcraft.registry.*;
import com.ryankshah.skyrimcraft.world.WorldGenConstants;
import com.ryankshah.skyrimcraft.world.region.SkyrimcraftOverworldRegion;
import com.ryankshah.skyrimcraft.world.surface_rule.AshWastesSurfaceRule;
import net.minecraft.world.entity.EntityType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

/**
 * TODO:
 * - Add accessories support to replace Curios and add the rings/necklaces/etc with powers/abilities/stats/etc.
 * - Add daedric artefacts - https://elderscrolls.fandom.com/wiki/Daedric_Artifacts_(Skyrim)
 * - Add black books (also artefacts?? - https://elderscrolls.fandom.com/wiki/Black_Books)
 * - Add more spells!!
 * - Add factions
 * - Add Lycanthropy (werewolf)
 * - Add Vampirism
 * - Add racial abilities (see bottom of https://elderscrolls.fandom.com/wiki/Abilities_(Skyrim))
 * - Add shrines and structures for the shrines - also add blessings for using shrines (1 only active at a time)
 * - Add lore books (but minecraft them using ChatGPT or something)
 * - Add traps - https://elderscrolls.fandom.com/wiki/Traps_(Skyrim)
 *
 * - Add more creatures:
 *   - Ash Hopper
 *   - Albino Spider
 *   - Bear
 *   - Cave Bear
 *   - Chaurus
 *   - Chaurus Hunter
 *   - Chaurus Hunter Fledgling
 *   - Death Hound (spawns with vampires)
 *   - DAEDRA (and all varieties of it)
 *   - DRAGON (more types of dragons not just normal)
 *   - DRAUGR (and more types)
 *   - DWARVEN AUTOMATONS (and all varieties)
 *   - Snow Bear
 *   - Wolf
 *   - Ice Wolf
 *   - Pit Wolf
 *   - Mudcrab
 *   - Slaughterfish
 *   - Horker
 *   - Ice Wraiths
 *   - Werewolf (Lycanthropy addon)
 *   - Spriggans (four different types)
 *   - Troll
 *   - Frost Troll
 *   - Vampires (Vampirism addon)
 *   - Werebear
 *
 *   - Clam
 *   - Deer
 *   - Vale Deer
 *   - Elk
 *   - Snow Fox
 *   - Hawk
 *   - Bone Hawk
 *   - Bristleback
 *   - Dusty
 *   - Felsaad Tern
 *   - Betty Netch
 *   - Bull Netch
 *   - Netch Calf
 *
 *   - Histcarp
 *   - River Betty
 *   - Silverside Perch
 */
public class SkyrimcraftCommon
{
    public static void init() {
        ModEffects.init();
        AttributeRegistry.init();
        SkyrimcraftLootConditionTypes.init();
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

        WorldGenConstants.init();
        FeatureRegistry.init();
        StructureRegistry.init();

        AdvancementTriggersRegistry.init();

        Networking.load();
    }

    public static void setupTerraBlender() {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Constants.MODID, AshWastesSurfaceRule.makeRules());
        Regions.register(new SkyrimcraftOverworldRegion(5));
    }

    public static Iterable<EntityType<?>> getPickpocketableEntities() {
        return ImmutableList.of(EntityType.VILLAGER);//, EntityInit.KHAJIIT.get(), EntityInit.FALMER.get());
    }
}