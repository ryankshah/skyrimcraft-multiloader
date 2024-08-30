package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = Constants.MODID, bus = EventBusSubscriber.Bus.GAME, value= Dist.CLIENT)
public class GuiGameEvents
{
    @SubscribeEvent
    public static void renderOverlays(RenderGuiLayerEvent.Pre event) {
        if (event.getName() == VanillaGuiLayers.BOSS_OVERLAY && !ClientUtil.getMinecraft().options.hideGui && !event.isCanceled()) {
            event.getGuiGraphics().pose().pushPose();
            event.getGuiGraphics().pose().translate(0, 28, 0);
            event.getGuiGraphics().pose().popPose();
        }

        if(event.getName() == VanillaGuiLayers.PLAYER_HEALTH
                || event.getName() == VanillaGuiLayers.CROSSHAIR
                || event.getName() == VanillaGuiLayers.FOOD_LEVEL
                || event.getName() == VanillaGuiLayers.ARMOR_LEVEL
                || event.getName() == VanillaGuiLayers.AIR_LEVEL
                || event.getName() == VanillaGuiLayers.EXPERIENCE_BAR
        ) {
            event.setCanceled(true);
        }
    }
}