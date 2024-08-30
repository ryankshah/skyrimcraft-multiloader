package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.google.common.collect.ImmutableSet;
import com.ryankshah.skyrimcraft.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;

import java.util.function.Supplier;

public class VillagerRegistry
{
    public static void init() {}

    public static final RegistrationProvider<PoiType> POIS = RegistrationProvider.get(Registries.POINT_OF_INTEREST_TYPE, Constants.MODID);
    public static final RegistrationProvider<VillagerProfession> PROFESSIONS = RegistrationProvider.get(Registries.VILLAGER_PROFESSION, Constants.MODID);

    // TODO: add professions for warrior, cook (oven), alchemist (alchemy table) and skyrim-blacksmith (forge)

    public static final Supplier<PoiType> WARRIOR_POI = POIS.register("warrior_poi", () -> new PoiType(ImmutableSet.copyOf(BlockRegistry.WEAPON_RACK.get().getStateDefinition().getPossibleStates()), 1, 8));
    public static final Supplier<VillagerProfession> WARRIOR = PROFESSIONS.register("warrior", () -> new VillagerProfession("warrior", p -> p.value().equals(WARRIOR_POI.get()), p -> p.value().equals(WARRIOR_POI.get()), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));
}