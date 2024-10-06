package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.entity.*;
import com.ryankshah.skyrimcraft.block.piston.DwemerPistonMovingBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegistry
{
    public static void init() {}

    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Constants.MODID);

    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<TurnStoneBlockEntity>> TURN_STONE = BLOCK_ENTITY_TYPES.register("turn_stone",
            () -> BlockEntityType.Builder.of(TurnStoneBlockEntity::new, BlockRegistry.TURN_STONE.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<RuneStoneBlockEntity>> RUNE_STONE = BLOCK_ENTITY_TYPES.register("rune_stone",
            () -> BlockEntityType.Builder.of(RuneStoneBlockEntity::new, BlockRegistry.RUNE_STONE.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DwemerComparatorBlockEntity>> DWEMER_COMPARATOR = BLOCK_ENTITY_TYPES.register("dwemer_comparator",
            () -> BlockEntityType.Builder.of(DwemerComparatorBlockEntity::new, BlockRegistry.DWEMER_COMPARATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DwemerDropperBlockEntity>> DWEMER_DROPPER = BLOCK_ENTITY_TYPES.register("dwemer_dropper",
            () -> BlockEntityType.Builder.of(DwemerDropperBlockEntity::new, BlockRegistry.DWEMER_DROPPER.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DwemerDispenserBlockEntity>> DWEMER_DISPENSER = BLOCK_ENTITY_TYPES.register("dwemer_dispenser",
            () -> BlockEntityType.Builder.of(DwemerDispenserBlockEntity::new, BlockRegistry.DWEMER_DISPENSER.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DwemerDaylightDetectorBlockEntity>> DWEMER_DAYLIGHT_DETECTOR = BLOCK_ENTITY_TYPES.register("dwemer_daylight_detector",
            () -> BlockEntityType.Builder.of(DwemerDaylightDetectorBlockEntity::new, BlockRegistry.DWEMER_DAYLIGHT_DETECTOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DwemerPistonMovingBlockEntity>> DWEMER_PISTON = BLOCK_ENTITY_TYPES.register("dwemer_moving_piston_entity",
            () -> BlockEntityType.Builder.of(DwemerPistonMovingBlockEntity::new, BlockRegistry.DWEMER_MOVING_PISTON.get()).build(null));
}