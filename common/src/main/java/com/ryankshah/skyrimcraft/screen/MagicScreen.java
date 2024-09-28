package com.ryankshah.skyrimcraft.screen;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.magic.EmptySpell;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.network.spell.UpdateSelectedSpell;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import com.ryankshah.skyrimcraft.util.RenderUtil;
import commonnetwork.api.Dispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.lwjgl.glfw.GLFW.glfwGetKeyName;

public class MagicScreen extends Screen
{
    protected static final ResourceLocation OVERLAY_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/overlay_icons.png");
    private static final int PADDING = 7;

    private Map<Spell.SpellType, ArrayList<Spell>> spellsAndTypes;
    private List<Spell.SpellType> spellTypes;
    private List<Spell> spellsListForCurrentSpellType;
    private boolean spellTypeChosen;
    private int currentSpellType;
    private int currentSpell;
    private Spell currentSpellObject;
    private Spell.SpellType currentSpellTypeObject;
    private Character character;

    private float currentTick, lastTick;
    private int currentSpellFrame;

    public MagicScreen(List<Spell> knownSpells) {
        super(Component.translatable(Constants.MODID + ".magicgui.title"));
        this.spellsAndTypes = new HashMap<>();
        spellsAndTypes.put(Spell.SpellType.ALL, new ArrayList<>());

        for(Spell spell : knownSpells) {
            if(spellsAndTypes.containsKey(spell.getType()))
                spellsAndTypes.get(spell.getType()).add(spell);
            else {
                ArrayList<Spell> temp = new ArrayList<>();
                temp.add(spell);
                spellsAndTypes.put(spell.getType(), temp);
            }
            spellsAndTypes.get(Spell.SpellType.ALL).add(spell);

            this.character = Character.get(Minecraft.getInstance().player);
        }

        spellsAndTypes = spellsAndTypes.entrySet().stream().sorted(Comparator.comparingInt(e -> e.getKey().getTypeID())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        this.currentSpellType = 0;
        this.currentSpell = 0;
//        this.currentSpellObject = null;
        this.spellTypes = new ArrayList<>(spellsAndTypes.keySet().stream().toList());
        this.currentSpellTypeObject = spellTypes.get(currentSpellType); // null;
        this.spellsListForCurrentSpellType = spellsAndTypes.get(currentSpellTypeObject); //new ArrayList<>();
        this.currentSpellObject = spellsListForCurrentSpellType.get(currentSpell);
        this.currentTick = 0;
        this.lastTick = 0;
        this.currentSpellFrame = 0;
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = graphics.pose();
        Minecraft mc = this.minecraft;
        Window window = mc.getWindow();
        int scaledWidth = window.getGuiScaledWidth();
        int scaledHeight = window.getGuiScaledHeight();

        poseStack.pushPose();
//        RenderUtil.bind(MENU_ICONS);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderTransparentBackground(graphics);

        if(this.spellTypeChosen) {
            graphics.fillGradient(this.width - 90, 0, this.width - 10, this.height, 0xAA000000, 0xAA000000);
            graphics.fillGradient(this.width - 88, 0, this.width - 87, this.height, 0xFF5D5A51, 0xFF5D5A51);
            graphics.fillGradient(this.width - 13, 0, this.width - 12, this.height, 0xFF5D5A51, 0xFF5D5A51);
            graphics.fillGradient(this.width - 190, 0, this.width - 110, this.height, 0xAA222222, 0xAA222222);
            graphics.fillGradient(this.width - 188, 0, this.width - 187, this.height, 0xFF5D5A51, 0xFF5D5A51);
            graphics.fillGradient(this.width - 113, 0, this.width - 112, this.height, 0xFF5D5A51, 0xFF5D5A51);
        } else {
            graphics.fillGradient(this.width - 90, 0, this.width - 10, this.height, 0xAA222222, 0xAA222222);
            graphics.fillGradient(this.width - 88, 0, this.width - 87, this.height, 0xFF5D5A51, 0xFF5D5A51);
            graphics.fillGradient(this.width - 13, 0, this.width - 12, this.height, 0xFF5D5A51, 0xFF5D5A51);
            graphics.fillGradient(this.width - 190, 0, this.width - 110, this.height, 0xAA000000, 0xAA000000);
            graphics.fillGradient(this.width - 188, 0, this.width - 187, this.height, 0xFF5D5A51, 0xFF5D5A51);
            graphics.fillGradient(this.width - 113, 0, this.width - 112, this.height, 0xFF5D5A51, 0xFF5D5A51);
        }
        graphics.fillGradient(0, this.height * 3 / 4 + 20, this.width, this.height, 0xAA000000, 0xAA000000);
        graphics.fillGradient(0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xFF5D5A51, 0xFF5D5A51);

        // Draw "buttons" for keys for selecting spells
        drawGradientRect(graphics, poseStack, 17, this.height - 29, 32, this.height - 14, 0xAA000000, 0xAA000000, 0xFF5D5A51);
        drawGradientRect(graphics, poseStack, 37, this.height - 29, 52, this.height - 14, 0xAA000000, 0xAA000000, 0xFF5D5A51);
        graphics.drawCenteredString(font, glfwGetKeyName(KeysRegistry.SPELL_SLOT_1_KEY.get().getDefaultKey().getValue(), 0).toUpperCase(), 25, this.height - 25, 0x0000FF00);
        graphics.drawCenteredString(font, glfwGetKeyName(KeysRegistry.SPELL_SLOT_2_KEY.get().getDefaultKey().getValue(), 0).toUpperCase(), 45, this.height - 25, 0x0000FFFF);
        graphics.drawCenteredString(font, "Equip", 70, this.height - 25, 0x00FFFFFF);

        renderMagicka(graphics, poseStack, this.width, this.height);

        int MIN_Y = 30;
        int MAX_Y = height / 2 + 14 * 6 - 10;

        int i;
        for(i = 0; i < spellTypes.size(); i++) {
            int y = this.height / 2 + 14 * i - this.currentSpellType * font.lineHeight;
            if(y <= MIN_Y || y >= MAX_Y)
                continue;
            String spellTypeName = spellTypes.get(i).toString();
            if (spellTypeName.length() >= 12)
                spellTypeName = spellTypeName.substring(0, 10) + "..";
            graphics.drawString(font, spellTypeName, this.width - 20 - font.width(spellTypeName), y, i == this.currentSpellType ? 0x00FFFFFF : 0x00C0C0C0);
        }

        // Get ISpell.SpellType
        currentSpellTypeObject = (Spell.SpellType) spellTypes.get(currentSpellType);
        // Get player's known spells for chosen spell type
        spellsListForCurrentSpellType = spellsAndTypes.get(currentSpellTypeObject);

        for(int j = 0; j < spellsListForCurrentSpellType.size(); j++) {
            Spell spell = spellsListForCurrentSpellType.get(j);
            String displayName = spell.getName();

            AtomicInteger color = new AtomicInteger(0x00C0C0C0);

            int finalJ = j;
            Spell selectedSpell1 = character.getSelectedSpell1();
            Spell selectedSpell2 = character.getSelectedSpell2();
            if(!(selectedSpell1 instanceof EmptySpell) && selectedSpell1 == spell) color.set(0x0000FF00);
            else if(!(selectedSpell2 instanceof EmptySpell) && selectedSpell2 == spell) color.set(0x0000FFFF);
            else if(finalJ == this.currentSpell) {
                color.set(0x00FFFFFF);
            }

            if (j == this.currentSpell && this.spellTypeChosen) {
                this.currentSpellObject = spell;
                poseStack.pushPose();
                // TODO work on scaling.
//                poseStack.scale(0.5f, 0.5f, 0.5f);
                drawSpellInformation(graphics, poseStack, currentSpellObject, this.width, this.height, partialTick);
                poseStack.popPose();
            }

            int y = this.height / 2 + 14 * j - this.currentSpell * font.lineHeight;
            if(y <= MIN_Y || y >= MAX_Y)
                continue;

            if (displayName.length() >= 12)
                displayName = displayName.substring(0, 10) + "..";

            graphics.drawString(font, displayName, this.width - 183, y, color.get());
        }

        poseStack.popPose();

//        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void drawGradientRect(GuiGraphics graphics, PoseStack matrixStack, int startX, int startY, int endX, int endY, int colorStart, int colorEnd, int borderColor) {
        matrixStack.pushPose();
        // Draw background
        graphics.fillGradient(startX, startY, endX, endY, colorStart, colorEnd);
        // Draw borders
        graphics.fill(startX, startY, endX, startY+1, borderColor); // top
        graphics.fill(startX, endY-1, endX, endY, borderColor); // bottom
        graphics.fill(startX, startY+1, startX+1, endY-1, borderColor); // left
        graphics.fill(endX-1, startY+1, endX, endY-1, borderColor); // right
        matrixStack.popPose();
    }


    private void drawSpellInformation(GuiGraphics graphics, PoseStack matrixStack, Spell spell, int width, int height, float partialTicks) {
        int leftBorder = 0;
        int rightBorder = this.width - 190;
        int centerX = (leftBorder + rightBorder) / 2;
        int infoWidth = 160; // Adjust this value as needed for the width of your spell info box
        int infoLeft = centerX - (infoWidth / 2);
        int infoRight = centerX + (infoWidth / 2);

        // Background
        drawGradientRect(graphics, matrixStack, infoLeft, (height) / 2 - 20, infoRight, (height) / 2 + 60, 0xAA000000, 0xAA000000, 0xFF6E6B64);

        // Line under spell name
        graphics.fillGradient(infoLeft + 10, (this.height) / 2, infoRight - 10, (this.height) / 2 + 1, 0xFF6E6B64, 0xFF6E6B64);

        // Spell name
        graphics.drawCenteredString(font, spell.getName(), centerX, (this.height) / 2 - 10, 0x00FFFFFF);

        // Spell description
        for(int i = 1; i < spell.getDescription().size()+1; i++) {
            graphics.drawCenteredString(font, spell.getDescription().get(i-1), centerX, (this.height) / 2 + (8 * i), 0x00FFFFFF);
        }

        // Spell details
        if(spell.getType() != Spell.SpellType.SHOUT) {
            graphics.drawString(font, "Cost: " + (int) spell.getCost(), infoLeft + 10, (this.height) / 2 + 34, 0x00FFFFFF);
            graphics.drawString(font, "Difficulty: " + StringUtils.capitalize(StringUtils.lowerCase(spell.getDifficulty().toString())), infoLeft + 10, (this.height) / 2 + 44, 0x00FFFFFF);
        } else {
            graphics.drawString(font, "Cooldown: " + (int)spell.getCooldown(), infoLeft + 10, (this.height) / 2 + 40, 0x00FFFFFF);
        }

        matrixStack.pushPose();
        RenderUtil.bind(spell.getDisplayAnimation());
        if(spell.getType() == Spell.SpellType.SHOUT || spell.getType() == Spell.SpellType.POWERS) {
            currentSpellFrame = (int)(lastTick + (currentTick - lastTick) * partialTicks) / 16;
            int uOffset = 0, vOffset = 16 * (currentSpellFrame % 7);
            float scaleFactor = 4.0f;
            float xPos = centerX - 32;
            float yPos = (this.height / 2) - 94;

            matrixStack.pushPose();
            matrixStack.translate(xPos, yPos, 0);
            matrixStack.scale(scaleFactor, scaleFactor, 1);
            RenderUtil.blitWithBlend(matrixStack, 0, 0, uOffset, vOffset, 16, 16, 16, 112, 1, 1);
            matrixStack.popPose();
        } else {
            currentSpellFrame = (int)(lastTick + (currentTick - lastTick) * partialTicks) / 64;
            int uOffset = 64 * (currentSpellFrame % 4), vOffset = 0;
            float xPos = centerX - 32; // Center the 64x64 image
            RenderUtil.blitWithBlend(matrixStack, xPos, (this.height / 2) - 94, uOffset, vOffset, 64, 64, 256, 64, 1, 1);
        }
        matrixStack.popPose();
    }

    private void renderMagicka(GuiGraphics graphics, PoseStack matrixStack, int width, int height) {
//        minecraft.getTextureManager().bind(OVERLAY_ICONS);
        RenderUtil.bind(OVERLAY_ICONS);
        float magicka = character.getMagicka();
        float maxMagicka = character.getMaxMagicka();
        float magickaPercentage = magicka / maxMagicka;
        float magickaBarWidth = 80.0f * magickaPercentage;
        float magickaBarStartX = (float)(width - 109) + (80.0f - magickaBarWidth);
        RenderUtil.blitWithBlend(matrixStack, this.width - 120, this.height - 25, 0, 51, 102, 10, 256, 256, 1, 1);
        RenderUtil.blitWithBlend(matrixStack, this.width - 109, this.height - 23, 11 + ((80 - magickaBarWidth) / 2.0f), 64, 80 * magickaPercentage, 6, 256, 256, 1, 1);
//        minecraft.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
    }

    @Override
    public void tick() {
        if(!spellTypeChosen)
            currentTick = 0;
        else {
            currentTick = lastTick;
            lastTick += 32f; // 32f
//            if(currentSpellFrame > 14)
//                currentSpellFrame = 0;
        }

        super.tick();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if(scrollY < 0) {
            if (!this.spellTypeChosen) {
                if (this.currentSpellType < this.spellTypes.size() - 1)
                    ++this.currentSpellType;
            } else {
                if (this.currentSpell < this.spellsListForCurrentSpellType.size() - 1)
                    ++this.currentSpell;
            }
        } else if(scrollY > 0) {
            if (!this.spellTypeChosen) {
                if(this.currentSpellType > 0)
                    --this.currentSpellType;
            } else {
                if (this.currentSpell > 0)
                    --this.currentSpell;
            }
        }
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(KeysRegistry.SKYRIM_MENU_SOUTH.get().matches(keyCode, scanCode)) {
            if(!this.spellTypeChosen) {
                if(this.currentSpellType < this.spellTypes.size()-1)
                    ++this.currentSpellType;
                else
                    this.currentSpellType = this.spellTypes.size()-1;
            } else {
                if(this.currentSpell >= 0 && this.currentSpell < spellsListForCurrentSpellType.size()-1)
                    this.currentSpell++;
            }
        }

        if(KeysRegistry.SKYRIM_MENU_NORTH.get().matches(keyCode, scanCode)) {
            if(!this.spellTypeChosen) {
                if(this.currentSpellType > 0)
                    --this.currentSpellType;
                else
                    this.currentSpellType = 0;
            } else {
                if(this.currentSpell > 0 && this.currentSpell < spellsListForCurrentSpellType.size())
                    this.currentSpell--;
            }
        }

        if(KeysRegistry.SKYRIM_MENU_WEST.get().matches(keyCode, scanCode)) {
            if(this.spellTypeChosen) {
                this.spellTypeChosen = false;
                this.currentSpell = 0;
            }
        }

        if(KeysRegistry.SKYRIM_MENU_EAST.get().matches(keyCode, scanCode)) {
            if(!this.spellTypeChosen) {
                this.spellTypeChosen = true;
                this.currentSpell = 0;
            }
        }

        if(KeysRegistry.SPELL_SLOT_1_KEY.get().matches(keyCode, scanCode)) {
            Spell selectedSpell1 = character.getSelectedSpell1();
            Spell selectedSpell2 = character.getSelectedSpell2();

            if(!selectedSpell1.equals(currentSpellObject)) {
                if (!(selectedSpell2.equals(currentSpellObject))) {
                    final UpdateSelectedSpell updatedSpells0 = new UpdateSelectedSpell(1, SpellRegistry.SPELLS_REGISTRY.getResourceKey(currentSpellObject).get());
                    Dispatcher.sendToServer(updatedSpells0);
//                    PacketDistributor.SERVER.noArg().send(updatedSpells0);
                } else {
                    final UpdateSelectedSpell updatedSpells0 = new UpdateSelectedSpell(2, SpellRegistry.SPELLS_REGISTRY.getResourceKey(character.getSelectedSpell1()).get());
                    Dispatcher.sendToServer(updatedSpells0);
//                    PacketDistributor.SERVER.noArg().send(updatedSpells0);
                    final UpdateSelectedSpell updatedSpells1 = new UpdateSelectedSpell(1, SpellRegistry.SPELLS_REGISTRY.getResourceKey(currentSpellObject).get());
                    Dispatcher.sendToServer(updatedSpells1);
//                    PacketDistributor.SERVER.noArg().send(updatedSpells1);
                }
            } else {
                final UpdateSelectedSpell updatedSpells0 = new UpdateSelectedSpell(1, SpellRegistry.SPELLS_REGISTRY.getResourceKey(SpellRegistry.EMPTY_SPELL.get()).get());
                Dispatcher.sendToServer(updatedSpells0);
//                PacketDistributor.SERVER.noArg().send(updatedSpells0);
            }

            System.out.println(character.toString());
        }

        if(KeysRegistry.SPELL_SLOT_2_KEY.get().matches(keyCode, scanCode)) {
            Spell selectedSpell1 = character.getSelectedSpell1();
            Spell selectedSpell2 = character.getSelectedSpell2();

            if(!selectedSpell2.equals(currentSpellObject)) {
                if (!selectedSpell1.equals(currentSpellObject)) {
                    final UpdateSelectedSpell updatedSpells0 = new UpdateSelectedSpell(2, SpellRegistry.SPELLS_REGISTRY.getResourceKey(currentSpellObject).get());
                    Dispatcher.sendToServer(updatedSpells0);
//                    PacketDistributor.SERVER.noArg().send(updatedSpells0);
                } else {
                    final UpdateSelectedSpell updatedSpells0 = new UpdateSelectedSpell(1, SpellRegistry.SPELLS_REGISTRY.getResourceKey(character.getSelectedSpell2()).get());
                    Dispatcher.sendToServer(updatedSpells0);
//                    PacketDistributor.SERVER.noArg().send(updatedSpells0);
                    final UpdateSelectedSpell updatedSpells1 = new UpdateSelectedSpell(2, SpellRegistry.SPELLS_REGISTRY.getResourceKey(currentSpellObject).get());
                    Dispatcher.sendToServer(updatedSpells1);
//                    PacketDistributor.SERVER.noArg().send(updatedSpells1);
                }
            } else {
                final UpdateSelectedSpell updatedSpells0 = new UpdateSelectedSpell(2, SpellRegistry.SPELLS_REGISTRY.getResourceKey(SpellRegistry.EMPTY_SPELL.get()).get());
                Dispatcher.sendToServer(updatedSpells0);
//                PacketDistributor.SERVER.noArg().send(updatedSpells0);
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

//    @Override
//    public boolean handleComponentClicked(@Nullable Style style) {
//        return false;
//    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return true;
    }

    @Override
    public void removed() {
        super.removed();
    }
}