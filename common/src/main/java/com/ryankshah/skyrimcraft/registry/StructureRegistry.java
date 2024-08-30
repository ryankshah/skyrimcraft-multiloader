package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.mojang.serialization.MapCodec;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.world.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class StructureRegistry
{
    public static void init() {}

    /**
     * We are using the Deferred Registry system to register our structure as this is the preferred way on Forge.
     * This will handle registering the base structure for us at the correct time so we don't have to handle it ourselves.
     *
     * HOWEVER, do note that Deferred Registries only work for anything that is a Forge Registry. This means that
     * configured structures and configured features need to be registered directly to BuiltinRegistries as there
     * is no Deferred Registry system for them.
     */
    public static final RegistrationProvider<StructureType<?>> STRUCTURES = RegistrationProvider.get(Registries.STRUCTURE_TYPE, Constants.MODID);

    /**
     * Registers the base structure itself and sets what its path is. In this case,
     * this base structure will have the resourcelocation of structure_tutorial:sky_structures.
     */
    public static final RegistryObject<StructureType<?>, StructureType<ShoutWallStructures>> SHOUT_WALL = STRUCTURES.register("shout_wall", () -> explicitStructureTypeTyping(ShoutWallStructures.CODEC));
    public static final RegistryObject<StructureType<?>, StructureType<GiantCampStructures>> GIANT_CAMP = STRUCTURES.register("giant_camp", () -> explicitStructureTypeTyping(GiantCampStructures.CODEC));
    public static final RegistryObject<StructureType<?>, StructureType<DungeonStructures>> DUNGEON = STRUCTURES.register("skyrim_dungeon", () -> explicitStructureTypeTyping(DungeonStructures.CODEC));
    public static final RegistryObject<StructureType<?>, StructureType<WatchtowerStructures>> WATCHTOWER = STRUCTURES.register("watchtower", () -> explicitStructureTypeTyping(WatchtowerStructures.CODEC));
    public static final RegistryObject<StructureType<?>, StructureType<RuinsStructures>> RUINS = STRUCTURES.register("ruins", () -> explicitStructureTypeTyping(RuinsStructures.CODEC));
    public static final RegistryObject<StructureType<?>, StructureType<MonumentStructures>> MONUMENTS = STRUCTURES.register("monuments", () -> explicitStructureTypeTyping(MonumentStructures.CODEC));
    public static final RegistryObject<StructureType<?>, StructureType<AltarStructures>> ALTAR = STRUCTURES.register("altars", () -> explicitStructureTypeTyping(AltarStructures.CODEC));
    public static final RegistryObject<StructureType<?>, StructureType<VillageStructures>> VILLAGE = STRUCTURES.register("village", () -> explicitStructureTypeTyping(VillageStructures.CODEC));

    /**
     * Originally, I had a double lambda ()->()-> for the RegistryObject line above, but it turns out that
     * some IDEs cannot resolve the typing correctly. This method explicitly states what the return type
     * is so that the IDE can put it into the DeferredRegistry properly.
     */
    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(MapCodec<T> structureCodec) {
        return () -> structureCodec;
    }
}