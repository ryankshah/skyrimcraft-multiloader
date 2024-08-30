package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.screen.SkyrimGuiOverlay;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = Constants.MODID, bus = EventBusSubscriber.Bus.MOD, value= Dist.CLIENT)
public class GuiModEvents
{
    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event){
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Constants.MODID,"skyrim_compass"), new SkyrimGuiOverlay.SkyrimCompass());
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "skyrim_magicka"), new SkyrimGuiOverlay.SkyrimMagicka());
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "current_spells"), new SkyrimGuiOverlay.SkyrimSpells());
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "target_health"), new SkyrimGuiOverlay.SkyrimTargetHealth());
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "level_updates"), new SkyrimGuiOverlay.SkyrimLevelUpdates());
        event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "skyrim_health"), new SkyrimGuiOverlay.SkyrimHealth());
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "skyrim_stamina"), new SkyrimGuiOverlay.SkyrimStamina());
        event.registerAbove(VanillaGuiLayers.ARMOR_LEVEL, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "skyrim_armor"), new SkyrimGuiOverlay.SkyrimArmorIcons());
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "skyrim_air"), new SkyrimGuiOverlay.SkyrimAir());
        event.registerAbove(VanillaGuiLayers.CROSSHAIR, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "skyrim_crosshair"), new SkyrimGuiOverlay.SkyrimCrosshair());
        event.registerAbove(VanillaGuiLayers.EXPERIENCE_BAR, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "skyrim_xpbar"), new SkyrimGuiOverlay.SkyrimXPBar());
    }
}