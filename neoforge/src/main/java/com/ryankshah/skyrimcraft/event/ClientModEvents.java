package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = Constants.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT) // GAME or MOD event bus??
public class ClientModEvents
{
    @SubscribeEvent
    public static void onKeyBinds(RegisterKeyMappingsEvent event) {
        event.register(KeysRegistry.MENU_KEY.get());
        event.register(KeysRegistry.SPELL_SLOT_1_KEY.get());
        event.register(KeysRegistry.SPELL_SLOT_2_KEY.get());
        event.register(KeysRegistry.PICKPOCKET_KEY.get());

        event.register(KeysRegistry.SKYRIM_MENU_ENTER.get());
        event.register(KeysRegistry.SKYRIM_MENU_NORTH.get());
        event.register(KeysRegistry.SKYRIM_MENU_SOUTH.get());
        event.register(KeysRegistry.SKYRIM_MENU_WEST.get());
        event.register(KeysRegistry.SKYRIM_MENU_EAST.get());

        event.register(KeysRegistry.SKYRIM_MENU_MB1_CLICK.get());
    }
}