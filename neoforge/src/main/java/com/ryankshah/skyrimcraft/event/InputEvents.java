package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.network.skill.HandlePickpocket;
import com.ryankshah.skyrimcraft.network.spell.CastSpell;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import com.ryankshah.skyrimcraft.screen.MenuScreen;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import commonnetwork.api.Dispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = Constants.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class InputEvents
{
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {

//        if (event.phase == TickEvent.Phase.END) { // Only call code once as the tick event is called twice every tick
        Minecraft mc = Minecraft.getInstance();

        while (KeysRegistry.MENU_KEY.get().consumeClick()) {
            mc.setScreen(new MenuScreen());
            return;
        }
        while (KeysRegistry.SPELL_SLOT_1_KEY.get().consumeClick()) { // TODO: Check if `isDown` for continuous cast?
            Character character = Character.get(mc.player);
            Spell spell = character.getSelectedSpell1();
            if (spell != null && spell.getID() != SpellRegistry.EMPTY_SPELL.get().getID()) {
                final CastSpell castSpell = new CastSpell(SpellRegistry.SPELLS_REGISTRY.getResourceKey(spell).get());
                Dispatcher.sendToServer(castSpell);
//                    PacketDistributor.SERVER.noArg().send(castSpell);
            } else
                mc.player.displayClientMessage(Component.translatable("skyrimcraft.spell.noselect"), false);
            return;
        }
        while (KeysRegistry.SPELL_SLOT_2_KEY.get().consumeClick()) {
            Character character = Character.get(mc.player);
            Spell spell = character.getSelectedSpell2();
            if (spell != null && spell.getID() != SpellRegistry.EMPTY_SPELL.get().getID()) {
                final CastSpell castSpell = new CastSpell(SpellRegistry.SPELLS_REGISTRY.getResourceKey(spell).get());
                Dispatcher.sendToServer(castSpell);
//                    PacketDistributor.SERVER.noArg().send(castSpell);
            } else
                mc.player.displayClientMessage(Component.translatable("skyrimcraft.spell.noselect"), false);
            return;
        }
        while (KeysRegistry.PICKPOCKET_KEY.get().consumeClick()) {
            if (mc.crosshairPickEntity instanceof LivingEntity && mc.player.isCrouching()) {
                LivingEntity entity = (LivingEntity) mc.crosshairPickEntity;

                if(entity.getTags().contains(EntityRegistry.PICKPOCKET_TAG)) {
                    if(ClientUtil.canEntitySee(entity, mc.player)) {
                        mc.player.hurt(mc.player.damageSources().mobAttack(entity), 0.5f);
                        mc.player.knockback(0.5f, (double) -Mth.sin(mc.player.yRotO * ((float)Math.PI / 180F)), (double)(Mth.cos(mc.player.yRotO * ((float)Math.PI / 180F))));
                    } else {
                        final HandlePickpocket handlePickpocket = new HandlePickpocket(entity.getId());
                        Dispatcher.sendToServer(handlePickpocket);
//                            PacketDistributor.SERVER.noArg().send(handlePickpocket);
                    }
                }
            }
        }
//        }
    }
}