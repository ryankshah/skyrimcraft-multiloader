package com.ryankshah.skyrimcraft;

import com.ryankshah.skyrimcraft.character.feature.render.RenderRaceLayer;
import com.ryankshah.skyrimcraft.character.feature.render.SpectralLayerRenderer;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;

public class SkyrimcraftFabricClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(KeysRegistry.MENU_KEY.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.SPELL_SLOT_1_KEY.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.SPELL_SLOT_2_KEY.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.PICKPOCKET_KEY.get());

        KeyBindingHelper.registerKeyBinding(KeysRegistry.SKYRIM_MENU_ENTER.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.SKYRIM_MENU_NORTH.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.SKYRIM_MENU_SOUTH.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.SKYRIM_MENU_WEST.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.SKYRIM_MENU_EAST.get());

        //TODO: Add this for blocks where required
        //BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.IRIS_FLOWER.get(), RenderType.cutout());

        SkyrimcraftCommonClient.registerRenderers(EntityRendererRegistry::register, BlockEntityRenderers::register);

        SkyrimcraftCommonClient.getLayerDefinitions().forEach(
                (layerdef, model) -> EntityModelLayerRegistry.registerModelLayer(layerdef, () -> model)
        );

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityType == EntityType.PLAYER) {
                registrationHelper.register(new RenderRaceLayer((PlayerRenderer) entityRenderer));
                registrationHelper.register(new SpectralLayerRenderer((PlayerRenderer) entityRenderer));
            }
        });
    }
}
