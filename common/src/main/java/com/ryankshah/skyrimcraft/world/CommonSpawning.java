package com.ryankshah.skyrimcraft.world;

import com.ryankshah.skyrimcraft.entity.creature.AbeceanLongfin;
import com.ryankshah.skyrimcraft.entity.creature.CyrodilicSpadetail;
import com.ryankshah.skyrimcraft.entity.creature.Draugr;
import com.ryankshah.skyrimcraft.entity.creature.DwarvenSpider;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.List;

public class CommonSpawning
{
    public static void placements() {
        SpawnPlacements.register(EntityRegistry.DWARVEN_SPIDER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DwarvenSpider::checkSpawnRules);
        SpawnPlacements.register(EntityRegistry.DRAUGR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Draugr::checkSpawnRules);
        SpawnPlacements.register(EntityRegistry.ABECEAN_LONGFIN.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbeceanLongfin::checkSpawnRules);
        SpawnPlacements.register(EntityRegistry.CYRODILIC_SPADETAIL.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CyrodilicSpadetail::checkSpawnRules);
        SpawnPlacements.register(EntityRegistry.BLUE_BUTTERFLY.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(EntityRegistry.BLUE_DARTWING.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(EntityRegistry.MONARCH_BUTTERFLY.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(EntityRegistry.ORANGE_DARTWING.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(EntityRegistry.LUNAR_MOTH.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);

    }
}