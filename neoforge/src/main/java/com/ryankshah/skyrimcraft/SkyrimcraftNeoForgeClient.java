package com.ryankshah.skyrimcraft;

import com.ryankshah.skyrimcraft.character.feature.render.RenderRaceLayer;
import com.ryankshah.skyrimcraft.character.feature.render.SpectralLayerRenderer;
import com.ryankshah.skyrimcraft.particle.EmittingLightningParticle;
import com.ryankshah.skyrimcraft.particle.LightningParticle;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import com.ryankshah.skyrimcraft.registry.ParticleRegistry;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = Constants.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class SkyrimcraftNeoForgeClient
{
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(ItemRegistry::registerItemModelProperties);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        SkyrimcraftCommonClient.registerRenderers(event::registerEntityRenderer, event::registerBlockEntityRenderer);
    }

    @SubscribeEvent
    public static void onEntityRenderLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        SkyrimcraftCommonClient.getLayerDefinitions().forEach(
                (layerdef, model) -> event.registerLayerDefinition(layerdef, () -> model)
        );
    }

    @SubscribeEvent
    public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        for(PlayerSkin.Model skin : event.getSkins()) {
            ((PlayerRenderer)event.getSkin(skin)).addLayer(new RenderRaceLayer(event.getSkin(skin)));
            ((PlayerRenderer)event.getSkin(skin)).addLayer(new SpectralLayerRenderer(event.getSkin(skin)));
        }
    }

    @SubscribeEvent
    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.LIGHTNING.get(), LightningParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.EMITTING_LIGHTNING.get(), EmittingLightningParticle.Provider::new);
//        event.registerSpriteSet(ParticleInit.FIRE.get(), FireParticle.Provider::new);
    }
}