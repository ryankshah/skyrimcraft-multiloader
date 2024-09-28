package com.ryankshah.skyrimcraft;

import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.feature.render.RenderRaceLayer;
import com.ryankshah.skyrimcraft.character.feature.render.SpectralLayerRenderer;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.network.skill.HandlePickpocket;
import com.ryankshah.skyrimcraft.network.spell.CastSpell;
import com.ryankshah.skyrimcraft.network.spell.ConsumeMagicka;
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
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.level.block.AbstractFurnaceBlock;

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
    private static final int TICK_INTERVAL = 20; // Magicka consumption and damage every 1 second (20 ticks)
    private static boolean isChargingShout = false;

    private static boolean spell1KeyWasDown = false;
    private static boolean spell2KeyWasDown = false;

    private static long lastCastTime1 = 0;
    private static long lastCastTime2 = 0;

    private static boolean canCastSpell1 = true;
    private static boolean canCastSpell2 = true;

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

        if (mc.screen != null) return;

        handleSpellCasting(character, KeysRegistry.SPELL_SLOT_1_KEY.get(), 1);
        handleSpellCasting(character, KeysRegistry.SPELL_SLOT_2_KEY.get(), 2);
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

    private static void handleSpellCasting(Character character, KeyMapping key, int spellSlot) {
        boolean keyIsDown = key.isDown();
        boolean keyWasDown = spellSlot == 1 ? spell1KeyWasDown : spell2KeyWasDown;
        Spell spell = spellSlot == 1 ? character.getSelectedSpell1() : character.getSelectedSpell2();
        boolean canCastSpell = spellSlot == 1 ? canCastSpell1 : canCastSpell2;

        if (keyIsDown && !keyWasDown) {
            processSpellStart(spell, character, spellSlot);
        } else if (keyWasDown && !keyIsDown) {
            processSpellFinish(spell, character, spellSlot);
        } else if (keyIsDown && canCastSpell) {
            processSpellContinue(spell, character, spellSlot);
        }

        if (spellSlot == 1) {
            spell1KeyWasDown = keyIsDown;
        } else {
            spell2KeyWasDown = keyIsDown;
        }
    }

    private static void processSpellStart(Spell spell, Character character, int spellSlot) {
        if (spell != null && spell.getID() != SpellRegistry.EMPTY_SPELL.get().getID()) {
            if (spell.getType() == Spell.SpellType.SHOUT) {
                startChargingShout(spell, character, spellSlot);
            } else if(spell.getType() == Spell.SpellType.POWERS) {
                castSpell(spell, character, true);
            } else if (hasSufficientMagicka(spell, character)) {
                castSpell(spell, character, true);
                consumeMagicka(spell, character);
            } else {
                displayInsufficientMagickaMessage();
                setCanCastSpell(spellSlot, false);
            }
        } else {
            Minecraft.getInstance().player.displayClientMessage(Component.translatable("skyrimcraft.spell.noselect"), false);
        }
    }

    private static void processSpellContinue(Spell spell, Character character, int spellSlot) {
        int ticksHeld = spellSlot == 1 ? spell1TicksHeld : spell2TicksHeld;
        ticksHeld++;

        if (spell.isContinuous() && hasSufficientMagicka(spell, character)) {
            castSpell(spell, character, false);

            if (ticksHeld % TICK_INTERVAL == 0) {
                consumeMagicka(spell, character);
            }
        } else if (spell.getType() == Spell.SpellType.SHOUT) {
            updateShoutCharge(spell, character, spellSlot);
        } else if (!hasSufficientMagicka(spell, character)) {
            displayInsufficientMagickaMessage();
            setCanCastSpell(spellSlot, false);
        }

        if (spellSlot == 1) {
            spell1TicksHeld = ticksHeld;
        } else {
            spell2TicksHeld = ticksHeld;
        }
    }


    private static void processSpellFinish(Spell spell, Character character, int spellSlot) {
        if (spell.getType() == Spell.SpellType.SHOUT) {
            stopChargingShout(spell, character, spellSlot);
        }
        spell.onSpellCancel();
        resetTicksHeld(spellSlot);
        setCanCastSpell(spellSlot, true);
    }

    private static void castContinuousSpell(Spell spell, Character character, int spellSlot) {
        int ticksHeld = spellSlot == 1 ? spell1TicksHeld : spell2TicksHeld;
        ticksHeld++;

        long currentTime = System.currentTimeMillis();
        long lastCastTime = spellSlot == 1 ? lastCastTime1 : lastCastTime2;

        if (currentTime - lastCastTime >= 1000) { // Check if 1 second has passed
            if (character.getMagicka() >= spell.getCost()) {
                castSpell(spell, character, false);
                if (spellSlot == 1) {
                    lastCastTime1 = currentTime;
                } else {
                    lastCastTime2 = currentTime;
                }
            } else {
                Minecraft.getInstance().player.displayClientMessage(Component.translatable("skyrimcraft.spell.no_magicka"), false);
                processSpellFinish(spell, character, spellSlot); // Stop if out of magicka
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
            SkyrimGuiOverlay.setShoutLocation(spellSlot);
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
            castSpell(spell, character, false);
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

    private static void castSpell(Spell spell, Character character, boolean isInitialCast) {
        final CastSpell castSpell = new CastSpell(SpellRegistry.SPELLS_REGISTRY.getResourceKey(spell).get());
        Dispatcher.sendToServer(castSpell);

        if (isInitialCast || !spell.isContinuous()) {
            consumeMagicka(spell, character);
        }
    }

    private static void consumeMagicka(Spell spell, Character character) {
        float cost = spell.getCost();
        if (character.getMagicka() >= cost) {
            final ConsumeMagicka consumeMagicka = new ConsumeMagicka(cost);
            Dispatcher.sendToServer(consumeMagicka);
        }
    }

    private static boolean hasSufficientMagicka(Spell spell, Character character) {
        return character.getMagicka() >= spell.getCost();
    }

    private static void displayInsufficientMagickaMessage() {
        Minecraft.getInstance().player.displayClientMessage(Component.translatable("skyrimcraft.spell.no_magicka"), false);
    }

    private static void setCanCastSpell(int spellSlot, boolean canCast) {
        if (spellSlot == 1) {
            canCastSpell1 = canCast;
        } else {
            canCastSpell2 = canCast;
        }
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
