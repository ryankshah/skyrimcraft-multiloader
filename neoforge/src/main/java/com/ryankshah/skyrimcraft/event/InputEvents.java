package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.network.skill.HandlePickpocket;
import com.ryankshah.skyrimcraft.network.spell.CastSpell;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import com.ryankshah.skyrimcraft.screen.MenuScreen;
import com.ryankshah.skyrimcraft.screen.SkyrimGuiOverlay;
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

//TODO: Update fabric client input with new updates from here
@EventBusSubscriber(modid = Constants.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class InputEvents {
    private static int spell1TicksHeld = 0;
    private static int spell2TicksHeld = 0;
    private static final int TICK_INTERVAL = 10; // Continuous spells cast every 0.5 seconds
    private static boolean isChargingShout = false;

    private static boolean spell1KeyWasDown = false;
    private static boolean spell2KeyWasDown = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        Character character = Services.PLATFORM.getCharacter(mc.player);

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
        } else if (spell1KeyWasDown && !spell1KeyIsDown) {
            processSpellFinish(character.getSelectedSpell1(), character, 1);
        } else if (spell1KeyIsDown) {
            processSpellContinue(character.getSelectedSpell1(), character, 1);
        }
        spell1KeyWasDown = spell1KeyIsDown;

        // Handle spell slot 2 (right hand)
        boolean spell2KeyIsDown = KeysRegistry.SPELL_SLOT_2_KEY.get().isDown();
        if (spell2KeyIsDown && !spell2KeyWasDown) {
            processSpellStart(character.getSelectedSpell2(), character, 2);
        } else if (spell2KeyWasDown && !spell2KeyIsDown) {
            processSpellFinish(character.getSelectedSpell2(), character, 2);
        } else if (spell2KeyIsDown) {
            processSpellContinue(character.getSelectedSpell2(), character, 2);
        }
        spell2KeyWasDown = spell2KeyIsDown;
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
//                return;
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
