package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.entity.TurnStoneBlockEntity;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegistry
{
    public static void init() {}

    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Constants.MODID);

    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<TurnStoneBlockEntity>> TURN_STONE = BLOCK_ENTITY_TYPES.register("turn_stone",
            () -> BlockEntityType.Builder.of(TurnStoneBlockEntity::new, BlockRegistry.TURN_STONE.get()).build(null));

//    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerBlockEntityRenderer(ALCHEMY_TABLE.get(), AlchemyTableBlockEntityRenderer:new);
//    }
}