package com.ryankshah.skyrimcraft;

import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.feature.render.RenderRaceLayer;
import com.ryankshah.skyrimcraft.character.feature.render.SpectralLayerRenderer;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.network.skill.HandlePickpocket;
import com.ryankshah.skyrimcraft.network.spell.CastSpell;
import com.ryankshah.skyrimcraft.network.spell.UpdateShoutCooldown;
import com.ryankshah.skyrimcraft.particle.EmittingLightningParticle;
import com.ryankshah.skyrimcraft.particle.LightningParticle;
import com.ryankshah.skyrimcraft.registry.*;
import com.ryankshah.skyrimcraft.screen.MenuScreen;
import com.ryankshah.skyrimcraft.screen.SkyrimGuiOverlay;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import commonnetwork.api.Dispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

public class SkyrimcraftFabricClient implements ClientModInitializer
{
    protected SkyrimGuiOverlay.SkyrimCompass compass = new SkyrimGuiOverlay.SkyrimCompass();
    protected SkyrimGuiOverlay.SkyrimMagicka magicka = new SkyrimGuiOverlay.SkyrimMagicka();
    protected SkyrimGuiOverlay.SkyrimSpells spells = new SkyrimGuiOverlay.SkyrimSpells();
    protected SkyrimGuiOverlay.SkyrimTargetHealth targetHealth = new SkyrimGuiOverlay.SkyrimTargetHealth();
    protected SkyrimGuiOverlay.SkyrimHealth health = new SkyrimGuiOverlay.SkyrimHealth();
    protected SkyrimGuiOverlay.SkyrimStamina stamina = new SkyrimGuiOverlay.SkyrimStamina();
    protected SkyrimGuiOverlay.SkyrimArmorIcons armorIcons = new SkyrimGuiOverlay.SkyrimArmorIcons();
    protected SkyrimGuiOverlay.SkyrimAir air = new SkyrimGuiOverlay.SkyrimAir();
    protected SkyrimGuiOverlay.SkyrimCrosshair crosshair = new SkyrimGuiOverlay.SkyrimCrosshair();
    protected SkyrimGuiOverlay.SkyrimXPBar xpBar = new SkyrimGuiOverlay.SkyrimXPBar();

    private static int spell1TicksHeld = 0;
    private static int spell2TicksHeld = 0;
    private static final int TICK_INTERVAL = 10; // Continuous spells cast every 0.5 seconds
    private static boolean isChargingShout = false;

    private static boolean spell1KeyWasDown = false;
    private static boolean spell2KeyWasDown = false;

    private Minecraft mc = Minecraft.getInstance();

    @Override
    public void onInitializeClient() {
        // TODO: fix all of this shit
        KeyBindingHelper.registerKeyBinding(KeysRegistry.MENU_KEY.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.SPELL_SLOT_1_KEY.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.SPELL_SLOT_2_KEY.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.PICKPOCKET_KEY.get());

        KeyBindingHelper.registerKeyBinding(KeysRegistry.SKYRIM_MENU_ENTER.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.SKYRIM_MENU_NORTH.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.SKYRIM_MENU_SOUTH.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.SKYRIM_MENU_WEST.get());
        KeyBindingHelper.registerKeyBinding(KeysRegistry.SKYRIM_MENU_EAST.get());

        KeyBindingHelper.registerKeyBinding(KeysRegistry.SKYRIM_MENU_MB1_CLICK.get());

        addBlockRenders();

        SkyrimcraftCommonClient.registerRenderers(EntityRendererRegistry::register, BlockEntityRenderers::register);
        SkyrimcraftCommonClient.getLayerDefinitions().forEach(
                (layerdef, model) -> EntityModelLayerRegistry.registerModelLayer(layerdef, () -> model)
        );

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityType == EntityType.PLAYER) {
                registrationHelper.register(new SpectralLayerRenderer((PlayerRenderer) entityRenderer));
                registrationHelper.register(new RenderRaceLayer((PlayerRenderer) entityRenderer));
            }
        });

        ItemRegistry.registerItemModelProperties();

        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.LIGHTNING.get(), LightningParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.EMITTING_LIGHTNING.get(), EmittingLightningParticle.Provider::new);

        HudRenderCallback.EVENT.register((guiGraphics, deltaTime) -> {
            compass.render(guiGraphics, deltaTime);
            magicka.render(guiGraphics, deltaTime);
            spells.render(guiGraphics, deltaTime);
            targetHealth.render(guiGraphics, deltaTime);
            health.render(guiGraphics, deltaTime);
            stamina.render(guiGraphics, deltaTime);
            armorIcons.render(guiGraphics, deltaTime);
            air.render(guiGraphics, deltaTime);
            crosshair.render(guiGraphics, deltaTime);
            xpBar.render(guiGraphics, deltaTime);
        });

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
            mc.setScreen(new MenuScreen());
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
            return;
        }

        if (mc.screen != null) return;

        // Handle spell slot 1 (left hand)
        boolean spell1KeyIsDown = KeysRegistry.SPELL_SLOT_1_KEY.get().isDown();
        if (spell1KeyIsDown && !spell1KeyWasDown) {
            processSpellStart(character.getSelectedSpell1(), character, 1);
            return;
        } else if (spell1KeyWasDown && !spell1KeyIsDown) {
            processSpellFinish(character.getSelectedSpell1(), character, 1);
            return;
        } else if (spell1KeyIsDown) {
            processSpellContinue(character.getSelectedSpell1(), character, 1);
            return;
        }
        spell1KeyWasDown = spell1KeyIsDown;

        // Handle spell slot 2 (right hand)
        boolean spell2KeyIsDown = KeysRegistry.SPELL_SLOT_2_KEY.get().isDown();
        if (spell2KeyIsDown && !spell2KeyWasDown) {
            processSpellStart(character.getSelectedSpell2(), character, 2);
            return;
        } else if (spell2KeyWasDown && !spell2KeyIsDown) {
            processSpellFinish(character.getSelectedSpell2(), character, 2);
            return;
        } else if (spell2KeyIsDown) {
            processSpellContinue(character.getSelectedSpell2(), character, 2);
            return;
        }
        spell2KeyWasDown = spell2KeyIsDown;
    }

    public void addBlockRenders() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.RED_MOUNTAIN_FLOWER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.YELLOW_MOUNTAIN_FLOWER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BLUE_MOUNTAIN_FLOWER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.PURPLE_MOUNTAIN_FLOWER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.LAVENDER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CABBAGE_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.TOMATO_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.GARLIC_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.OVEN.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ALCHEMY_TABLE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BLACKSMITH_FORGE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ARCANE_ENCHANTER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BIRDS_NEST.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.PEARL_OYSTER_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ASH_YAM_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CANIS_ROOT_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BLISTERWORT_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BLEEDING_CROWN_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CREEP_CLUSTER_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.FLY_AMANITA_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.WHITE_CAP_BLOCK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.WEAPON_RACK.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.JAZBAY_GRAPE_BUSH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.JUNIPER_BERRY_BUSH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SNOWBERRY_BUSH.get(), RenderType.cutout());
    }

    private static void processSpellStart(Spell spell, Character character, int spellSlot) {
        if (spell != null && spell.getID() != SpellRegistry.EMPTY_SPELL.get().getID()) {
            if (!spell.isContinuous() && spell.getType() != Spell.SpellType.SHOUT && spell.getType() != Spell.SpellType.POWERS) {
                // Cast instantly for non-continuous, non-shout spells
                castSpell(spell);
            } else if (spell.isContinuous() && spell.getType() != Spell.SpellType.SHOUT && spell.getType() != Spell.SpellType.POWERS) {
                // Handle continuous spells
                castContinuousSpell(spell, character, spellSlot);
            } else if (spell.getType() == Spell.SpellType.SHOUT || spell.getType() == Spell.SpellType.POWERS) {
                // Handle shouts or powers (charge-up logic)
                startChargingShout(spell, character, spellSlot);
            }
        } else {
            Minecraft.getInstance().player.displayClientMessage(Component.translatable("skyrimcraft.spell.noselect"), false);
        }
    }

    private static void processSpellContinue(Spell spell, Character character, int spellSlot) {
        if (spell.isContinuous()) {
            // Continuous spell logic (repeat cast every interval)
            castContinuousSpell(spell, character, spellSlot);
        } else if (spell.getType() == Spell.SpellType.SHOUT || spell.getType() == Spell.SpellType.POWERS) {
            // Handle shout charging logic
            updateShoutCharge(spell, character, spellSlot);
        }
    }

    private static void processSpellFinish(Spell spell, Character character, int spellSlot) {
        if (spell.isContinuous()) {
            // Reset ticks for continuous spells
            resetTicksHeld(spellSlot);
        } else if (spell.getType() == Spell.SpellType.SHOUT || spell.getType() == Spell.SpellType.POWERS) {
            // If shout charge was interrupted, cancel shout charge
            stopChargingShout(spell, character, spellSlot);
            resetTicksHeld(spellSlot);
        }

        if(spellSlot == 1) {
            KeysRegistry.SPELL_SLOT_1_KEY.get().setDown(false);
//            spell1KeyWasDown = true;
        }  else if(spellSlot == 2) {
            KeysRegistry.SPELL_SLOT_2_KEY.get().setDown(false);
//            spell2KeyWasDown = true;
        }
    }

    private static void castContinuousSpell(Spell spell, Character character, int spellSlot) {
        int ticksHeld = spellSlot == 1 ? spell1TicksHeld : spell2TicksHeld;
        ticksHeld++;

        if (ticksHeld % TICK_INTERVAL == 0) {
            if (character.getMagicka() >= spell.getCost()) {
                castSpell(spell);
            } else {
                Minecraft.getInstance().player.displayClientMessage(Component.translatable("skyrimcraft.spell.nomana"), false);
                processSpellFinish(spell, character, spellSlot); // Stop if out of mana
                return;
            }
        }

        // Store updated ticksHeld
        if (spellSlot == 1) {
            spell1TicksHeld = ticksHeld;
        } else {
            spell2TicksHeld = ticksHeld;
        }
    }

    private static void startChargingShout(Spell spell, Character character, int spellSlot) {
        if (character.getSpellCooldown(spell) > 0f) {
            Minecraft.getInstance().player.displayClientMessage(Component.translatable("skyrimcraft.shout.cooldown"), false);
            return;
        }

        if (!isChargingShout) {
            isChargingShout = true;
            SkyrimGuiOverlay.setShowShoutBar(true);
        }

        resetTicksHeld(spellSlot); // Reset charge time at the start
    }

    private static void updateShoutCharge(Spell spell, Character character, int spellSlot) {
        int ticksHeld = spellSlot == 1 ? spell1TicksHeld : spell2TicksHeld;
        ticksHeld++;

        // Calculate charge-up progress (0.0 - 1.0)
        float chargeupProgress = Math.min(ticksHeld / (spell.getChargeTime() * 20.0f), 1.0f);
        SkyrimGuiOverlay.setShoutChargeProgress(chargeupProgress);

        if (chargeupProgress >= 1.0f) {
            // Cast shout once charge is complete
            hideShoutBar();
            castSpell(spell);
            processSpellFinish(spell, character, spellSlot); // Reset after casting
        }

        // Store updated ticksHeld
        if (spellSlot == 1) {
            spell1TicksHeld = ticksHeld;
        } else {
            spell2TicksHeld = ticksHeld;
        }
    }

    private static void stopChargingShout(Spell spell, Character character, int spellSlot) {
        hideShoutBar();
        resetTicksHeld(spellSlot);
    }

    private static void castSpell(Spell spell) {
        final CastSpell castSpell = new CastSpell(SpellRegistry.SPELLS_REGISTRY.getResourceKey(spell).get());
        Dispatcher.sendToServer(castSpell);
    }

    private static void hideShoutBar() {
        SkyrimGuiOverlay.setShoutChargeProgress(0.0f);
        SkyrimGuiOverlay.setShowShoutBar(false);
        isChargingShout = false;
    }

    private static void resetTicksHeld(int spellSlot) {
        if (spellSlot == 1) {
            spell1TicksHeld = 0;
        } else {
            spell2TicksHeld = 0;
        }
    }
}
