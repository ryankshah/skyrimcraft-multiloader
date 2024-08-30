package com.ryankshah.skyrimcraft;

import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.feature.render.RenderRaceLayer;
import com.ryankshah.skyrimcraft.character.feature.render.SpectralLayerRenderer;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.network.skill.HandlePickpocket;
import com.ryankshah.skyrimcraft.network.spell.CastSpell;
import com.ryankshah.skyrimcraft.network.spell.UpdateShoutCooldown;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import com.ryankshah.skyrimcraft.screen.MenuScreen;
import com.ryankshah.skyrimcraft.screen.SkyrimGuiOverlay;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import commonnetwork.api.Dispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.fabricmc.fabric.mixin.client.rendering.InGameHudMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

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

//        HudRenderCallback.EVENT.register((guiGraphics, deltaTime) -> {
//            SkyrimGuiOverlay.SkyrimCompass compass = new SkyrimGuiOverlay.SkyrimCompass();
//            compass.render(guiGraphics, deltaTime);
//        });

        ClientTickEvents.END_CLIENT_TICK.register(this::handleClientTicks);
    }

    public void handleClientTicks(Minecraft client) {
        if (client.player == null || !client.player.isAlive())
            return;

        Character character = Character.get(client.player);
        if(client.player.level().isClientSide) {
            if (!character.getSpellsOnCooldown().isEmpty()) {
                for (Map.Entry<Spell, Float> entry : character.getSpellsOnCooldown().entrySet()) {
                    if (entry.getValue() <= 0f) {
                        final UpdateShoutCooldown updateShoutCooldown = new UpdateShoutCooldown(SpellRegistry.SPELLS_REGISTRY.getResourceKey(entry.getKey()).get(), 0f);
                        Dispatcher.sendToServer(updateShoutCooldown);
//                            PacketDistributor.SERVER.noArg().send(updateShoutCooldown);
                    }
                    if (entry.getValue() > 0f) {
                        float cooldown = character.getSpellCooldown(entry.getKey());
                        final UpdateShoutCooldown updateShoutCooldown = new UpdateShoutCooldown(SpellRegistry.SPELLS_REGISTRY.getResourceKey(entry.getKey()).get(), cooldown - 0.05f);
                        Dispatcher.sendToServer(updateShoutCooldown);
//                            PacketDistributor.SERVER.noArg().send(updateShoutCooldown);
                    }
                }
            }
        }

        while (KeysRegistry.MENU_KEY.get().consumeClick()) {
            client.setScreen(new MenuScreen());
            return;
        }
        while (KeysRegistry.SPELL_SLOT_1_KEY.get().consumeClick()) { // TODO: Check if `isDown` for continuous cast?
            Spell spell = character.getSelectedSpell1();
            if (spell != null && spell.getID() != SpellRegistry.EMPTY_SPELL.get().getID()) {
                final CastSpell castSpell = new CastSpell(SpellRegistry.SPELLS_REGISTRY.getResourceKey(spell).get());
                Dispatcher.sendToServer(castSpell);
//                    PacketDistributor.SERVER.noArg().send(castSpell);
            } else
                client.player.displayClientMessage(Component.translatable("skyriclientraft.spell.noselect"), false);
            return;
        }
        while (KeysRegistry.SPELL_SLOT_2_KEY.get().consumeClick()) {
            Spell spell = character.getSelectedSpell2();
            if (spell != null && spell.getID() != SpellRegistry.EMPTY_SPELL.get().getID()) {
                final CastSpell castSpell = new CastSpell(SpellRegistry.SPELLS_REGISTRY.getResourceKey(spell).get());
                Dispatcher.sendToServer(castSpell);
//                    PacketDistributor.SERVER.noArg().send(castSpell);
            } else
                client.player.displayClientMessage(Component.translatable("skyriclientraft.spell.noselect"), false);
            return;
        }
        while (KeysRegistry.PICKPOCKET_KEY.get().consumeClick()) {
            if (client.crosshairPickEntity instanceof LivingEntity && client.player.isCrouching()) {
                LivingEntity entity = (LivingEntity) client.crosshairPickEntity;

                if(entity.getTags().contains(EntityRegistry.PICKPOCKET_TAG)) {
                    if(ClientUtil.canEntitySee(entity, client.player)) {
                        client.player.hurt(client.player.damageSources().mobAttack(entity), 0.5f);
                        client.player.knockback(0.5f, (double) -Mth.sin(client.player.yRotO * ((float)Math.PI / 180F)), (double)(Mth.cos(client.player.yRotO * ((float)Math.PI / 180F))));
                    } else {
                        final HandlePickpocket handlePickpocket = new HandlePickpocket(entity.getId());
                        Dispatcher.sendToServer(handlePickpocket);
//                            PacketDistributor.SERVER.noArg().send(handlePickpocket);
                    }
                }
            }
        }
    }
}
