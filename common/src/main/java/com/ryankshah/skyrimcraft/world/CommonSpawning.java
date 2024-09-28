package com.ryankshah.skyrimcraft.world;

import com.ryankshah.skyrimcraft.entity.creature.*;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.List;

public class CommonSpawning
{
    public static List<MobSpawnSettings.SpawnerData> WATER_MOB_SPAWNS = List.of(
            new MobSpawnSettings.SpawnerData(EntityRegistry.ABECEAN_LONGFIN.get(), 4, 2, 4),
            new MobSpawnSettings.SpawnerData(EntityRegistry.CYRODILIC_SPADETAIL.get(), 4, 2, 4)
    );

    public static List<MobSpawnSettings.SpawnerData> CAVE_MOB_SPAWNS = List.of(
            new MobSpawnSettings.SpawnerData(EntityRegistry.DRAUGR.get(), 40, 2, 3),
            new MobSpawnSettings.SpawnerData(EntityRegistry.DWARVEN_SPIDER.get(), 40, 1, 2),
            new MobSpawnSettings.SpawnerData(EntityRegistry.SKEEVER.get(), 40, 2, 3),
            new MobSpawnSettings.SpawnerData(EntityRegistry.VENOMFANG_SKEEVER.get(), 40, 1, 2)
    );

    public static List<MobSpawnSettings.SpawnerData> PLAINS_MOB_SPAWNS = List.of(
            new MobSpawnSettings.SpawnerData(EntityRegistry.SABRE_CAT.get(), 4, 1, 2)
    );

    public static List<MobSpawnSettings.SpawnerData> SNOW_MOB_SPAWNS = List.of(
            new MobSpawnSettings.SpawnerData(EntityRegistry.SABRE_CAT.get(), 4, 1, 2),
            new MobSpawnSettings.SpawnerData(EntityRegistry.VALE_SABRE_CAT.get(), 4, 1, 2),
            new MobSpawnSettings.SpawnerData(EntityRegistry.GIANT.get(), 1, 2, 2),
            new MobSpawnSettings.SpawnerData(EntityRegistry.DRAUGR.get(), 40, 2, 2),
            new MobSpawnSettings.SpawnerData(EntityRegistry.DWARVEN_SPIDER.get(), 40, 1, 2),
            new MobSpawnSettings.SpawnerData(EntityRegistry.SKEEVER.get(), 40, 2, 3),
            new MobSpawnSettings.SpawnerData(EntityRegistry.VENOMFANG_SKEEVER.get(), 40, 1, 2),
            new MobSpawnSettings.SpawnerData(EntityRegistry.DRAGON.get(), 1, 1, 1)
    );
    public static List<MobSpawnSettings.SpawnerData> END_MOB_SPAWNS = List.of(
            new MobSpawnSettings.SpawnerData(EntityRegistry.VALE_SABRE_CAT.get(), 10, 1, 2)
    );

    public static List<MobSpawnSettings.SpawnerData> DRIPSTONE_MOB_SPAWNS = List.of(
            new MobSpawnSettings.SpawnerData(EntityRegistry.TORCHBUG.get(), 40, 4, 6),
            new MobSpawnSettings.SpawnerData(EntityRegistry.SKEEVER.get(), 40, 2, 3),
            new MobSpawnSettings.SpawnerData(EntityRegistry.VENOMFANG_SKEEVER.get(), 40, 1, 2)
    );

    public static List<MobSpawnSettings.SpawnerData> PLAINS_FLYING_MOB_SPAWNS = List.of(
            new MobSpawnSettings.SpawnerData(EntityRegistry.BLUE_BUTTERFLY.get(), 20, 3, 5),
            new MobSpawnSettings.SpawnerData(EntityRegistry.MONARCH_BUTTERFLY.get(), 20, 3, 5),
            new MobSpawnSettings.SpawnerData(EntityRegistry.BLUE_DARTWING.get(), 20, 3, 5),
            new MobSpawnSettings.SpawnerData(EntityRegistry.ORANGE_DARTWING.get(), 20, 3, 5),
            new MobSpawnSettings.SpawnerData(EntityRegistry.LUNAR_MOTH.get(), 20, 3, 5),
            new MobSpawnSettings.SpawnerData(EntityRegistry.DRAGON.get(), 4, 1, 1)
    );

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