package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.ryankshah.skyrimcraft.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegistry
{
    public static void init() {}

    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Constants.MODID);

//    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlchemyTableBlockEntity>> ALCHEMY_TABLE = BLOCK_ENTITY_TYPES.register("alchemy_table",
//            () -> BlockEntityType.Builder.<AlchemyTableBlockEntity>of(AlchemyTableBlockEntity::new, BlockInit.ALCHEMY_TABLE.get()).build();
//
//
//    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerBlockEntityRenderer(ALCHEMY_TABLE.get(), AlchemyTableBlockEntityRenderer:new);
//    }

}