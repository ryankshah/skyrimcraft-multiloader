package com.ryankshah.skyrimcraft.screen;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.StatIncreases;
import com.ryankshah.skyrimcraft.character.skill.Perk;
import com.ryankshah.skyrimcraft.character.skill.Skill;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.character.skill.SkillWrapper;
import com.ryankshah.skyrimcraft.network.character.UpdateCharacter;
import com.ryankshah.skyrimcraft.network.character.UpdateStatIncreases;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import com.ryankshah.skyrimcraft.util.RenderUtil;
import commonnetwork.api.Dispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;

import java.util.*;
import java.util.stream.Collectors;

public class SkillScreen extends Screen {
    // Skill Nebula Cube Map
    public static final CubeMap SKILL_NEBULA_CUBE_MAP = new CubeMap(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/panorama/magic_end"));
    protected static final ResourceLocation SKILL_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/skill_icons.png");

    private final int PLAYER_BAR_MAX_WIDTH = 78,
            SKILL_BAR_CONTAINER_U = 1,
            SKILL_BAR_CONTAINER_V = 194,
            SKILL_BAR_CONTAINER_WIDTH = 80,
            SKILL_BAR_CONTAINER_HEIGHT = 8,
            SKILL_BAR_U = 7,
            SKILL_BAR_V = 204,
            SKILL_BAR_WIDTH = 67,
            SKILL_BAR_HEIGHT = 2;

    private List<SkillWrapper> skillsList;
    private SkillWrapper selectedSkillObject;
    private Minecraft minecraft;
    private LocalPlayer player;
    private Character character;
    private float cubeMapPosition = 0.0f;
    private int currentSkill = 0;
    private int currentUpdateSelection = -1; // 0 = magicka, 1 = health, 2 = stamina
    private boolean skillSelected;
    private int perkPoints, levelPoints;
    private int levelHeight = 40; // Vertical space between levels
    private boolean shouldFocusLevelUpdates = false;
    private String[] levelUpdateOptions = {"Magicka", "Health", "Stamina"};
    private static final int UNLOCKED_COLOR = 0xFF00FF00; // Green color for unlocked perks
    // Add a field to track current perk selection
    private int currentPerkIndex = 0;
    private static final float DETAILS_SCALE = 0.75f; // Reduced from 0.9f
    private static final float PERK_LIST_SCALE = 0.85f; // New constant for perk list scaling
    private static final int MAX_PERK_NAME_WIDTH = 150; // New constant for max perk name width

    private static final int PERK_ICON_SIZE = 32;
    private static final int LEVEL_HEIGHT = 80;
    private static final int PERK_SPACING = 80; // Increased from 60
    private static final int LEVEL_SPACING = 200; // Increased from 150

    // Add new fields for description scrolling
    private int descriptionScroll = 0;
    private int maxDescriptionScroll = 0;

    private int greenColor = 0xFF4B8C32;

    float characterProgress;
    float characterProgressBarWidth;

    public SkillScreen() {
        super(Component.translatable(Constants.MODID + ".skills.title"));

        this.minecraft = Minecraft.getInstance();
        this.player = minecraft.player;
        this.character = Character.get(player);
        this.skillsList = character.getSkills();
        this.perkPoints = character.getLevelPerkPoints();
        this.selectedSkillObject = null;
        this.characterProgress = character.getCharacterTotalXp() / (float) getXpNeededForNextCharacterLevel(character.getCharacterLevel() + 1);
        this.characterProgressBarWidth = 60 * characterProgress;

        this.levelPoints = Services.PLATFORM.getLevelUpdates(player).getLevelUpdates(); //player.getData(PlayerAttachments.LEVEL_UPDATES);
        if (levelPoints > 0) {
            currentUpdateSelection = 1;
            shouldFocusLevelUpdates = true;
        }
    }

    private double getXpNeededForNextCharacterLevel(int level) {
        return 12.5 * Math.pow(level + 1, 2) + 62.5 * level - 75;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = graphics.pose();
        Minecraft mc = this.minecraft;
        Window window = mc.getWindow();
        int scaledWidth = window.getGuiScaledWidth();
        int scaledHeight = window.getGuiScaledHeight();

        poseStack.pushPose();
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);

        SKILL_NEBULA_CUBE_MAP.render(this.minecraft, Mth.sin(cubeMapPosition * 0.001F) * 5.0F + 25.0F, -cubeMapPosition * 0.1F, 1.0f);
        poseStack.popPose();

        poseStack.pushPose();
        RenderUtil.bind(SKILL_ICONS);

        RenderUtil.blitWithBlend(poseStack, width / 2 - 151, 10, 0, 209, 302, 14, 512, 512, 0, 1);
        if (character.getLevelPerkPoints() > 0)
            RenderUtil.blitWithBlend(poseStack, width / 2 - 56, 32, 0, 264, 112, 11, 512, 512, 0, 1);

        RenderUtil.blitWithBlend(poseStack, width / 2 - 20, 13, 109, 228, 80, 8, 512, 512, 1, 1);
        RenderUtil.blitWithBlend(poseStack, width / 2 - 10, 15, 119 + ((60 - characterProgressBarWidth) / 2.0f), 236, (int) (60 * characterProgress), 6, 512, 512, 1, 1);
        poseStack.popPose();

        drawScaledString(graphics, "NAME", width / 2 - 151 + 22, 15, 0xFF949494, 0.75f);
        drawScaledString(graphics, player.getDisplayName().getString(), width / 2 - 151 + 20 + font.width("NAME"), 13, 0xFFFFFFFF, 1f);
        String level = "LEVEL"; //character.getCharacterLevel();
        drawScaledString(graphics, level, width / 2 - 55, 15, 0xFF949494, 0.75f);
        drawScaledString(graphics, "" + character.getCharacterLevel(), width / 2 - 30, 13, 0xFFFFFFFF, 1f);
        String raceName = character.getRace().getName();
        drawScaledString(graphics, "RACE", width / 2 + 151 - font.width("RACE") - font.width(raceName) - 10, 15, 0xFF949494, 0.75f);
        drawScaledString(graphics, raceName, width / 2 + 151 - font.width(raceName), 13, 0xFFFFFFFF, 1f);

        if (character.getLevelPerkPoints() > 0)
            drawScaledCenteredString(graphics, "Perks to increase: " + perkPoints, width / 2, 35, 0x00FFFFFF, 0.75f);


        // Render bottom bars
        graphics.fillGradient(0, this.height * 3 / 4 + 20, this.width, this.height, 0xAA000000, 0xAA000000);
        graphics.fillGradient(0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xFF6E6B64, 0xFF6E6B64);

        poseStack.pushPose();
        RenderUtil.bind(SKILL_ICONS);
        renderHealth(graphics, poseStack, width, height, currentUpdateSelection == 1);
        renderStamina(graphics, poseStack, width, height, currentUpdateSelection == 2);
        renderMagicka(graphics, poseStack, width, height, currentUpdateSelection == 0);

        poseStack.popPose();

//        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
        // If no skill is selected, show skill icons etc.
        if (!skillSelected) {
            for (SkillWrapper skillEntry : skillsList) {
                int x = this.width / 2 + 128 * (skillEntry.getID() + 1) - (this.currentSkill + 1) * 128; //(width / 2) - ((SKILL_BAR_CONTAINER_WIDTH / 2) + (24 * currentSkill+1))
                drawSkill(skillEntry, graphics, poseStack, x, height / 2 - 20);
            }
        } else {
            // If skill selected, show perks for selection etc.
            drawSkillPerksTree(selectedSkillObject, graphics, poseStack, width, height, mouseX, mouseY);
        }

        // If there is a level update, draw this shit on top
        if (shouldFocusLevelUpdates) {
            drawGradientRect(graphics, poseStack, width / 2 - 140, height / 2 - 30, width / 2 + 140, height / 2 + 6, 0xDD000000, 0xDD000000, 0xFF6E6B64);
            graphics.drawCenteredString(font, "You leveled up! Choose an attribute to advance:", width / 2, height / 2 - 24, 0xFFFFFFFF);

            graphics.drawCenteredString(font, currentUpdateSelection == 0 ? "< " + levelUpdateOptions[0] + " >" : levelUpdateOptions[0], width / 2 - font.width(levelUpdateOptions[0]) - 20, height / 2 - 10, 0xFFFFFFFF);
            graphics.drawCenteredString(font, currentUpdateSelection == 1 ? "< " + levelUpdateOptions[1] + " >" : levelUpdateOptions[1], width / 2, height / 2 - 10, 0xFFFFFFFF);
            graphics.drawCenteredString(font, currentUpdateSelection == 2 ? "< " + levelUpdateOptions[2] + " >" : levelUpdateOptions[2], width / 2 + font.width(levelUpdateOptions[2]) + 20, height / 2 - 10, 0xFFFFFFFF);

//            int i = 1;
//            for(String option : levelUpdateOptions) {
//                if(currentUpdateSelection == i-1)
//                    graphics.drawCenteredString(font, "< " + option + " >", option.equals("health") ? width / 2 : width / 2 - 70 + (i * 42), height / 2 - 10, 0xFFFFFFFF);
//                else
//                    graphics.drawCenteredString(font, option, option.equals("health") ? width / 2 : width / 2 - 70 + (i * 42), height / 2 - 10, 0xFFFFFFFF);
//                i++;
//            }
        }
    }

    public void drawScaledString(GuiGraphics graphics, String str, int x, int y, int color, float scale) {
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);
        graphics.drawString(font, str, (int) (x / scale), (int) (y / scale), color);
        graphics.pose().popPose();
    }

    public void drawScaledCenteredString(GuiGraphics graphics, String str, int x, int y, int color, float scale) {
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);
        float w = this.font.width(str) * scale;
        x = (int) ((x - w / 2) / scale);
        graphics.drawString(font, str, x, (int) (y / scale), color);
        graphics.pose().popPose();
    }

    public void drawScaledCenteredStringWithSplit(GuiGraphics graphics, String str, int x, int y, int color, float scale, int split) {
        if (str.length() >= split) {
            String parts = ClientUtil.wrap(str, split, "\n", true, "-", null);
            String[] lines = parts.split("\n");

            for (int i = 0; i < lines.length; i++)
                drawScaledCenteredString(graphics, lines[i], x, (y + (i * 6)), color, scale);
        } else {
            drawScaledCenteredString(graphics, str, x, y, color, scale);
        }
    }

    @Override
    public void tick() {
        super.tick();
        cubeMapPosition += 1.0f;

//        if(this.perkPoints > 0 && !shouldFocusLevelUpdates)
//            shouldFocusLevelUpdates = true;
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (KeysRegistry.SKYRIM_MENU_EAST.get().matches(keyCode, scanCode)) {
            if (shouldFocusLevelUpdates) {
                if (currentUpdateSelection > 0)
                    currentUpdateSelection--;
            } else if (!skillSelected) {
                if (currentSkill > 0) {
                    currentSkill--;
                } else {
                    currentSkill = skillsList.size() - 1;
                }
            }
        } else if (KeysRegistry.SKYRIM_MENU_WEST.get().matches(keyCode, scanCode)) {
            if (shouldFocusLevelUpdates) {
                if (currentUpdateSelection < 2)
                    currentUpdateSelection++;
            } else if (!skillSelected) {
                if (currentSkill < skillsList.size() - 1) {
                    currentSkill++;
                } else {
                    currentSkill = 0;
                }
            }
        } else if (KeysRegistry.SKYRIM_MENU_NORTH.get().matches(keyCode, scanCode)) {
            if (skillSelected && selectedSkillObject != null) {
                List<Perk> perks = selectedSkillObject.getSkillPerks();
                if (currentPerkIndex > 0) {
                    currentPerkIndex--;
                }
            }
        } else if (KeysRegistry.SKYRIM_MENU_SOUTH.get().matches(keyCode, scanCode)) {
            if (skillSelected && selectedSkillObject != null) {
                List<Perk> perks = selectedSkillObject.getSkillPerks();
                if (currentPerkIndex < perks.size() - 1) {
                    currentPerkIndex++;
                }
            }
        } else if (KeysRegistry.SKYRIM_MENU_ENTER.get().matches(keyCode, scanCode)) {
            if (shouldFocusLevelUpdates) {
                // Level updates logic remains the same
                StatIncreases statIncreases = Services.PLATFORM.getStatIncreases(player);
                if (currentUpdateSelection == 1) {
                    statIncreases.increaseHealth();
                } else if (currentUpdateSelection == 0) {
                    statIncreases.increaseMagicka();
                } else if (currentUpdateSelection == 2) {
                    statIncreases.increaseStamina();
                }
                final UpdateStatIncreases updateStatIncreases = new UpdateStatIncreases(statIncreases);
                Dispatcher.sendToServer(updateStatIncreases);
                levelPoints--;
                if (levelPoints == 0) {
                    shouldFocusLevelUpdates = false;
                    currentUpdateSelection = -1;
                }
            } else if (!skillSelected) {
                skillSelected = true;
                selectedSkillObject = skillsList.get(currentSkill);
                currentPerkIndex = 0; // Reset perk selection when entering skill view
            } else if (skillSelected && selectedSkillObject != null) {
                // Try to unlock the currently selected perk
                List<Perk> perks = selectedSkillObject.getSkillPerks();
                if (currentPerkIndex >= 0 && currentPerkIndex < perks.size()) {
                    Perk selectedPerk = perks.get(currentPerkIndex);
                    handlePerkClick(selectedPerk, perks);
                }
            }
        } else if (keyCode == 256) {
            if (!skillSelected) {
                this.onClose();
                return true;
            } else {
                selectedSkillObject = null;
                skillSelected = false;
                currentPerkIndex = 0;
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (!skillSelected) {
            if (scrollY < 0) {
                if (this.currentSkill < this.skillsList.size() - 1)
                    ++this.currentSkill;
            } else if (scrollY > 0) {
                if (this.currentSkill > 0)
                    --this.currentSkill;
            }
            return true;
        }

        // Calculate areas for scroll handling
        int startX = width / 8;
        int PANEL_PADDING = 25;
        int LIST_WIDTH = (width * 3/4) - (2 * PANEL_PADDING);
        int DIVIDER_X = startX + LIST_WIDTH/3;

        // Handle scrolling in perk list
        if (mouseX >= startX && mouseX <= DIVIDER_X) {
            List<Perk> perks = selectedSkillObject.getSkillPerks();
            int VISIBLE_ITEMS = ((height * 2/3) - (2 * PANEL_PADDING)) / 35;
            int maxScroll = Math.max(0, perks.size() - VISIBLE_ITEMS);

            if (scrollY < 0) {
                if (currentPerkIndex < maxScroll)
                    currentPerkIndex++;
            } else if (scrollY > 0) {
                if (currentPerkIndex > 0)
                    currentPerkIndex--;
            }
            return true;
        }

        // Handle scrolling in description area
        if (mouseX >= DIVIDER_X && mouseX <= startX + LIST_WIDTH && maxDescriptionScroll > 0) {
            if (scrollY < 0) {
                descriptionScroll = Math.min(descriptionScroll + 15, maxDescriptionScroll);
            } else if (scrollY > 0) {
                descriptionScroll = Math.max(0, descriptionScroll - 15);
            }
            return true;
        }

        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (skillSelected && selectedSkillObject != null) {
            List<Perk> perks = selectedSkillObject.getSkillPerks();

            // Calculate areas with new padding
            int startX = width / 8;
            int PANEL_PADDING = 4;
            int LIST_WIDTH = (width * 3/4) - (2 * PANEL_PADDING);
            int DIVIDER_X = startX + LIST_WIDTH/3;
            int PANEL_HEIGHT = height * 2/3 - 40;
            int ITEM_HEIGHT = 30;

            // Check if click is in the perk list area
            if (mouseX >= startX + PANEL_PADDING && mouseX <= DIVIDER_X - PANEL_PADDING) {
                int clickedIndex = currentPerkIndex +
                        (int)((mouseY - (height/6 + PANEL_PADDING)) / ITEM_HEIGHT);

                if (clickedIndex >= 0 && clickedIndex < perks.size()) {
                    Perk clickedPerk = perks.get(clickedIndex);
                    currentPerkIndex = clickedIndex;

                    // Handle perk unlocking
                    if (canUnlockPerk(clickedPerk, perks) && perkPoints > 0 && !clickedPerk.isUnlocked()) {
                        handlePerkClick(clickedPerk, perks);
                        return true;
                    }
                }
            }

            // Unlock button logic
            if (currentPerkIndex >= 0 && currentPerkIndex < perks.size()) {
                Perk selectedPerk = perks.get(currentPerkIndex);
                int buttonWidth = 100;
                int buttonHeight = 20;
                int buttonX = DIVIDER_X + PANEL_PADDING +
                        ((startX + LIST_WIDTH - (DIVIDER_X + PANEL_PADDING) - buttonWidth) / 2);
                int buttonY = height/6 + PANEL_HEIGHT - buttonHeight - 10;

                if (mouseX >= buttonX && mouseX <= buttonX + buttonWidth &&
                        mouseY >= buttonY && mouseY <= buttonY + buttonHeight) {
                    if (canUnlockPerk(selectedPerk, perks) && perkPoints > 0 && !selectedPerk.isUnlocked()) {
                        handlePerkClick(selectedPerk, perks);
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private int getHoveredPerkIndex(double mouseX, double mouseY) {
        if (!skillSelected || selectedSkillObject == null) return -1;

        int startX = width / 4;
        int PANEL_PADDING = 20;
        int LIST_WIDTH = (width / 2) - (2 * PANEL_PADDING);
        int ITEM_HEIGHT = 30;

        if (mouseX >= startX + PANEL_PADDING &&
                mouseX <= startX + LIST_WIDTH/2 - PANEL_PADDING &&
                mouseY >= height/6 + PANEL_PADDING &&
                mouseY <= height/6 + height * 2/3 - PANEL_PADDING) {

            return currentPerkIndex + (int)((mouseY - (height/6 + PANEL_PADDING)) / ITEM_HEIGHT);
        }
        return -1;
    }

    // Helper method to check if click is within the bounds of the perk
    private boolean isWithinBounds(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    // Modified handlePerkClick to work with List instead of Map
    private void handlePerkClick(Perk perk, List<Perk> perks) {
        if (perk.isUnlocked() || perkPoints <= 0) {
            return;
        }

        // Check if all parent perks are unlocked
        boolean canUnlock = true;
        for (String parentName : perk.getParents()) {
            boolean parentUnlocked = false;
            for (Perk parentPerk : perks) {
                if (parentPerk.getName().equals(parentName)) {
                    if (!parentPerk.isUnlocked()) {
                        canUnlock = false;
                        break;
                    }
                    parentUnlocked = true;
                    break;
                }
            }
            if (!parentUnlocked) {
                canUnlock = false;
                break;
            }
        }

        if (canUnlock && character != null) {
            perk.unlock();
            perkPoints--;
            character.removeLevelPerkPoint();
            character.getSkill(selectedSkillObject.getID()).unlockPerk(perk);

            // Send update to server
            UpdateCharacter updateCharacter = new UpdateCharacter(character);
            Dispatcher.sendToServer(updateCharacter);
        }
    }

    public static AbstractMap.SimpleEntry<Integer, Integer> getIconUV(ResourceKey<Skill> skill) {
        return SkillRegistry.SKILLS_REGISTRY.get(skill).getIconUV();
    }

    private void drawSkill(SkillWrapper skill, GuiGraphics graphics, PoseStack poseStack, int x, int y) {
        float skillProgress = skill.getXpProgress();
        //float skillBarWidth = SKILL_BAR_WIDTH * skillProgress;
        poseStack.pushPose();
        RenderUtil.bind(SKILL_ICONS);
        RenderUtil.blitWithBlend(poseStack, x - (SKILL_BAR_CONTAINER_WIDTH / 2), y + 48 + (SKILL_BAR_CONTAINER_HEIGHT / 2), SKILL_BAR_CONTAINER_U, SKILL_BAR_CONTAINER_V, SKILL_BAR_CONTAINER_WIDTH, SKILL_BAR_CONTAINER_HEIGHT, 512, 512, 0, 1);
        RenderUtil.blitWithBlend(poseStack, x - (SKILL_BAR_CONTAINER_WIDTH / 2) + 7, y + 49 + (SKILL_BAR_CONTAINER_HEIGHT / 2) + SKILL_BAR_HEIGHT, SKILL_BAR_U, SKILL_BAR_V, (int)(SKILL_BAR_WIDTH * skillProgress), SKILL_BAR_HEIGHT, 512, 512, 0, 1);

        AbstractMap.SimpleEntry<Integer, Integer> iconUV = getIconUV(SkillRegistry.SKILLS_REGISTRY.getResourceKey(skill.getSkill()).get());
        RenderUtil.blitWithBlend(poseStack, x - 32, y + 18 - 64, iconUV.getKey(), iconUV.getValue(), 64, 64, 512, 512, 0, 1);
        poseStack.popPose();
        String name = skill.getName().toUpperCase();
        drawScaledCenteredString(graphics, skill.getName().toUpperCase(), x - 12, y + 40, 0xFF949494, 0.75f);
        graphics.drawCenteredString(font, "" + skill.getLevel(), x + 20, y + 38, 0x00FFFFFF);

        if(skillsList.get(currentSkill).getID() == skill.getID()) {
            drawScaledCenteredStringWithSplit(graphics, skill.getDescription(), x, y + 70, 0x00FFFFFF, 0.75f, 81);
        }
    }

    private void drawSkillPerksTree(SkillWrapper skill, GuiGraphics graphics, PoseStack poseStack, int width, int height, int mouseX, int mouseY) {
        List<Perk> perks = skill.getSkillPerks();

        // Constants for layout - adjusted for wider left panel and increased height
        final int PANEL_PADDING = 4;
        final int LIST_WIDTH = (width * 3/4) - (2 * PANEL_PADDING);
        final int startX = width / 8;
        final int TOTAL_WIDTH = width * 3/4;
        final int PANEL_HEIGHT = height * 2/3 - 20; // Increased height by 20 pixels (changed from -40)
        final int ITEM_HEIGHT = 30;
        final int VISIBLE_ITEMS = (PANEL_HEIGHT - (2 * PANEL_PADDING)) / ITEM_HEIGHT;
        final int DIVIDER_X = startX + (LIST_WIDTH * 2/5);

        // Calculate scroll position
        int maxScroll = Math.max(0, perks.size() - VISIBLE_ITEMS);
        int currentScroll = Math.min(maxScroll, currentPerkIndex);

        // Draw main container with some transparency
        drawBorderedGradientRect(graphics, poseStack, startX, height/6, startX + TOTAL_WIDTH, height/6 + PANEL_HEIGHT,
                0xDD000000, 0xDD000000, 0xFF6E6B64);

        // Draw divider
        graphics.fill(DIVIDER_X, height/6 + PANEL_PADDING,
                DIVIDER_X + 2, height/6 + PANEL_HEIGHT - PANEL_PADDING, 0xFF6E6B64);

        // Draw scrollbar if needed
        if (maxScroll > 0) {
            int scrollBarHeight = PANEL_HEIGHT - (2 * PANEL_PADDING);
            int scrollThumbHeight = Math.max(20, scrollBarHeight * VISIBLE_ITEMS / perks.size());
            int scrollThumbY = height/6 + PANEL_PADDING +
                    (scrollBarHeight - scrollThumbHeight) * currentScroll / maxScroll;

            graphics.fill(DIVIDER_X - 12, height/6 + PANEL_PADDING,
                    DIVIDER_X - 8, height/6 + PANEL_HEIGHT - PANEL_PADDING, 0x44FFFFFF);
            graphics.fill(DIVIDER_X - 12, scrollThumbY,
                    DIVIDER_X - 8, scrollThumbY + scrollThumbHeight, 0xFFFFFFFF);
        }

        // Draw visible perks with truncation - adjusted to use full height
        int listStartX = startX + PANEL_PADDING;
        int currentY = height/6 + PANEL_PADDING;
        int listWidth = DIVIDER_X - listStartX - PANEL_PADDING - 10;
        int maxY = height/6 + PANEL_HEIGHT - PANEL_PADDING; // Define maximum Y for list items

        Perk hoveredPerk = null;
        if (mouseX >= listStartX && mouseX <= DIVIDER_X - PANEL_PADDING) {
            int hoveredIndex = (mouseY - (height/6 + PANEL_PADDING)) / ITEM_HEIGHT + currentScroll;
            if (hoveredIndex >= currentScroll && hoveredIndex < Math.min(perks.size(), currentScroll + VISIBLE_ITEMS)) {
                hoveredPerk = perks.get(hoveredIndex);
            }
        }

        // Draw the list of perks with truncation - ensure it fills the available space
        for (int i = currentScroll; i < Math.min(perks.size(), currentScroll + VISIBLE_ITEMS); i++) {
            if (currentY + ITEM_HEIGHT > maxY) break; // Prevent overflow

            Perk perk = perks.get(i);
            boolean isHovered = perk == hoveredPerk;
            boolean isSelected = i == currentPerkIndex;

            if (isHovered || isSelected) {
                graphics.fill(listStartX, currentY, DIVIDER_X - PANEL_PADDING,
                        currentY + ITEM_HEIGHT, 0x33FFFFFF);
            }

            int textColor = perk.isUnlocked() ? UNLOCKED_COLOR : 0xFFFFFFFF;
            if (isHovered || isSelected) textColor = 0xFFFFFFFF;

            String perkName = truncateText(perk.getName(), font, listWidth / PERK_LIST_SCALE);

            poseStack.pushPose();
            poseStack.scale(PERK_LIST_SCALE, PERK_LIST_SCALE, PERK_LIST_SCALE);
            graphics.drawString(font, perkName,
                    (int)((listStartX + 5) / PERK_LIST_SCALE),
                    (int)((currentY + (ITEM_HEIGHT - font.lineHeight) / 2) / PERK_LIST_SCALE),
                    textColor);
            poseStack.popPose();

            currentY += ITEM_HEIGHT;
        }

        // Draw perk details for currently selected perk - adjusted to use full height
        if (selectedSkillObject != null && currentPerkIndex >= 0 && currentPerkIndex < perks.size()) {
            Perk selectedPerk = perks.get(currentPerkIndex);
            int detailsX = DIVIDER_X + PANEL_PADDING + 4;
            int detailsY = height/6 + PANEL_PADDING;
            // Use the same height calculation as the list
            drawPerkDetails(graphics, selectedPerk, mouseX, mouseY, detailsX, detailsY,
                    PANEL_HEIGHT - (2 * PANEL_PADDING),
                    startX + TOTAL_WIDTH - PANEL_PADDING - 4);
        }
    }

    private String truncateText(String text, Font font, float maxWidth) {
        if (font.width(text) <= maxWidth) {
            return text;
        }

        String ellipsis = "..";
        int ellipsisWidth = font.width(ellipsis);

        StringBuilder truncated = new StringBuilder();
        float currentWidth = 0;

        for (char c : text.toCharArray()) {
            float charWidth = font.width(String.valueOf(c));
            if (currentWidth + charWidth + ellipsisWidth > maxWidth) {
                return truncated.toString() + ellipsis;
            }
            truncated.append(c);
            currentWidth += charWidth;
        }

        return truncated.toString();
    }

    private void drawPerkDetails(GuiGraphics graphics, Perk perk, int mouseX, int mouseY, int x, int y, int maxHeight, int maxWidth) {
        int currentY = y - descriptionScroll;
        final float DETAILS_SCALE = 0.75f; // Matched with perk list scale

        // Create a scissor box for scrolling
        double scale = minecraft.getWindow().getGuiScale();
        RenderSystem.enableScissor(
                (int)(x * scale),
                (int)(y * scale),
                (int)((maxWidth - x) * scale),
                (int)(maxHeight * scale)
        );

        PoseStack poseStack = graphics.pose();

        // Draw title with consistent styling
        poseStack.pushPose();
        poseStack.scale(DETAILS_SCALE, DETAILS_SCALE, DETAILS_SCALE);
        graphics.drawString(font, perk.getName(), (int)(x/DETAILS_SCALE), (int)(currentY/DETAILS_SCALE), 0xFFFFFFFF);
        currentY += 20;

        // Draw other details with same scaling
        graphics.drawString(font, "Level Required: " + perk.getLevelRequirement(),
                (int)(x/DETAILS_SCALE), (int)(currentY/DETAILS_SCALE), 0xFF949494);
        currentY += 15;

        // Unlock status
        String status = perk.isUnlocked() ? "Status: Unlocked" : "Status: Locked";
        graphics.drawString(font, status, (int)(x/DETAILS_SCALE), (int)(currentY/DETAILS_SCALE),
                perk.isUnlocked() ? UNLOCKED_COLOR : 0xFF949494);
        currentY += 15;

        // Requirements
        if (!perk.getParents().isEmpty()) {
            graphics.drawString(font, "Requirements:", (int)(x/DETAILS_SCALE), (int)(currentY/DETAILS_SCALE), 0xFF949494);
            currentY += 15;
            for (String parent : perk.getParents()) {
                graphics.drawString(font, "- " + parent, (int)((x + 10)/DETAILS_SCALE), (int)(currentY/DETAILS_SCALE), 0xFF949494);
                currentY += 15;
            }
            currentY += 5;
        }

        // Description with word wrap and scaling
        List<String> wrappedDesc = wrapText(perk.getDescription(), (int)((maxWidth - x) / DETAILS_SCALE));
        for (String line : wrappedDesc) {
            graphics.drawString(font, line, (int)(x/DETAILS_SCALE), (int)(currentY/DETAILS_SCALE), 0xFFFFFFFF);
            currentY += 15;
        }

        poseStack.popPose();

        // Calculate max scroll
        int totalHeight = currentY - y + descriptionScroll;
        maxDescriptionScroll = Math.max(0, totalHeight - maxHeight);

        // Draw scrollbar if needed
        if (maxDescriptionScroll > 0) {
            int scrollBarHeight = maxHeight;
            int thumbHeight = Math.max(20, scrollBarHeight * maxHeight / totalHeight);
            int thumbY = y + (scrollBarHeight - thumbHeight) * descriptionScroll / maxDescriptionScroll;

            // Draw scrollbar background
            graphics.fill(maxWidth - 4, y, maxWidth - 2, y + maxHeight, 0x44FFFFFF);
            // Draw scrollbar thumb
            graphics.fill(maxWidth - 4, thumbY, maxWidth - 2, thumbY + thumbHeight, 0xFFFFFFFF);
        }

        RenderSystem.disableScissor();

        // Draw unlock button if available
        if (!perk.isUnlocked() && perkPoints > 0 && canUnlockPerk(perk, selectedSkillObject.getSkillPerks())) {
            int buttonWidth = 100;
            int buttonHeight = 20;
            int buttonX = x + (maxWidth - x - buttonWidth) / 2;
            int buttonY = y + maxHeight - buttonHeight - 10;

            boolean buttonHovered = mouseX >= buttonX && mouseX <= buttonX + buttonWidth &&
                    mouseY >= buttonY && mouseY <= buttonY + buttonHeight;

            drawBorderedGradientRect(graphics, poseStack, buttonX, buttonY,
                    buttonX + buttonWidth, buttonY + buttonHeight,
                    buttonHovered ? 0x66000000 : 0x44000000,
                    buttonHovered ? 0x66000000 : 0x44000000,
                    0xFF6E6B64);

            String unlockText = "Unlock";
            int textWidth = font.width(unlockText);
            graphics.drawString(font, unlockText,
                    buttonX + (buttonWidth - textWidth) / 2,
                    buttonY + (buttonHeight - font.lineHeight) / 2,
                    buttonHovered ? 0xFFFFFFFF : 0xFF949494);
        }
    }

    // Updated text wrapping method to handle scaling
    private List<String> wrapText(String text, int maxWidth) {
        List<String> wrappedLines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine.toString();
            if (!testLine.isEmpty()) testLine += " ";
            testLine += word;

            if (font.width(testLine) > maxWidth) {
                if (!currentLine.isEmpty()) {
                    wrappedLines.add(currentLine.toString());
                    currentLine = new StringBuilder();
                }
                // If a single word is too long, force break it
                if (font.width(word) > maxWidth) {
                    int breakPoint = 0;
                    StringBuilder partial = new StringBuilder();
                    for (char c : word.toCharArray()) {
                        partial.append(c);
                        if (font.width(partial.toString()) > maxWidth) {
                            wrappedLines.add(word.substring(breakPoint, partial.length() - 1) + "-");
                            breakPoint = partial.length() - 1;
                            partial = new StringBuilder(String.valueOf(c));
                        }
                    }
                    if (breakPoint < word.length()) {
                        currentLine.append(word.substring(breakPoint));
                    }
                } else {
                    currentLine.append(word);
                }
            } else {
                if (!currentLine.isEmpty()) currentLine.append(" ");
                currentLine.append(word);
            }
        }

        if (currentLine.length() > 0) {
            wrappedLines.add(currentLine.toString());
        }

        return wrappedLines;
    }

    private boolean canUnlockPerk(Perk perk, List<Perk> allPerks) {
        if (perk.isUnlocked()) return false;

        // Check if all parent perks are unlocked
        for (String parentName : perk.getParents()) {
            boolean parentFound = false;
            for (Perk potential : allPerks) {
                if (potential.getName().equals(parentName)) {
                    if (!potential.isUnlocked()) return false;
                    parentFound = true;
                    break;
                }
            }
            if (!parentFound) return false;
        }

        return true;
    }

    private void renderHealth(GuiGraphics graphics, PoseStack poseStack, int width, int height, boolean selected) {
        poseStack.pushPose();
        float healthPercentage = player.getHealth() / player.getMaxHealth();
        float healthBarWidth = PLAYER_BAR_MAX_WIDTH * healthPercentage;
        float healthBarStartX;

        if(player.level().getDifficulty() == Difficulty.HARD) {
            healthBarStartX = (width / 2 - 40) + (PLAYER_BAR_MAX_WIDTH - healthBarWidth) / 2.0f;
            RenderUtil.blitWithBlend(poseStack, width / 2 - 51, height - 36, 96, 246, 100, 16, 512, 512, 3, 1);
        } else {
            healthBarStartX = (width / 2 - 39) + (PLAYER_BAR_MAX_WIDTH - healthBarWidth) / 2.0f;
            RenderUtil.blitWithBlend(poseStack, width / 2 - 51, height - 30, 0, 226, 102, 10, 512, 512, 3, 1);
        }
        RenderUtil.blitWithBlend(poseStack, (int)healthBarStartX, height - 28, 12 + ((PLAYER_BAR_MAX_WIDTH - healthBarWidth) / 2.0f), 247, healthBarWidth, 6, 512, 512, 3, 1);
        drawScaledString(graphics, "HEALTH", width / 2 - 31, height - 16, 0xFF949494, 0.75F);
        graphics.drawCenteredString(font, "" + (int)player.getHealth() + "/" + (int)player.getMaxHealth(), width / 2 + 15, height - 18, selected ? greenColor : 0xFFFFFFFF);
        poseStack.popPose();
    }

    private void renderStamina(GuiGraphics graphics, PoseStack poseStack, int width, int height, boolean selected) {
        poseStack.pushPose();
        RenderUtil.bind(SKILL_ICONS);
        float staminaPercentage = player.getFoodData().getFoodLevel() / 20.0f; // 20.0f is the max food value (this is apparently hardcoded...)
        float staminaBarWidth = PLAYER_BAR_MAX_WIDTH * staminaPercentage;
        float staminaBarStartX = (float)(width - 108) + (PLAYER_BAR_MAX_WIDTH - staminaBarWidth);

        RenderUtil.blitWithBlend(poseStack, width - 120, height - 30, 0, 226, 102, 10, 512, 512, 3, 1);
        RenderUtil.blitWithBlend(poseStack, (int)staminaBarStartX, height - 28, 12 + ((PLAYER_BAR_MAX_WIDTH - staminaBarWidth) / 2.0f), 255, staminaBarWidth, 6, 512, 512, 3, 1);
        drawScaledString(graphics, "STAMINA", width - 120 + 21, height - 16, 0xFF949494, 0.75F);
        graphics.drawCenteredString(font, "" + (int)player.getFoodData().getFoodLevel() + "/" + 20, width - 120 + 70, height - 18, selected ? greenColor : 0xFFFFFFFF);
        poseStack.popPose();
    }

    private void renderMagicka(GuiGraphics graphics, PoseStack poseStack, int width, int height, boolean selected) {
        poseStack.pushPose();
        RenderUtil.bind(SKILL_ICONS);
        float magickaPercentage = character.getMagicka() / character.getMaxMagicka();
        float magickaBarWidth = PLAYER_BAR_MAX_WIDTH * magickaPercentage;
        RenderUtil.blitWithBlend(poseStack, 20, height - 30, 0, 226, 102, 10, 512, 512, 3, 1);
        RenderUtil.blitWithBlend(poseStack, 32, height - 28, 12 + ((PLAYER_BAR_MAX_WIDTH - magickaBarWidth) / 2.0f), 239, (int)(78 * magickaPercentage), 6, 512, 512, 3, 1);
        drawScaledString(graphics, "MAGICKA", 41, height - 16, 0xFF949494, 0.75F);
        graphics.drawCenteredString(font, "" + (int)character.getMagicka() + "/" + (int)character.getMaxMagicka(), 90, height - 18, selected ? greenColor : 0xFFFFFFFF);
        poseStack.popPose();
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

    private void drawBorderedRect(GuiGraphics graphics, PoseStack matrixStack, int startX, int startY, int endX, int endY, int colorFill, int borderColor) {
        matrixStack.pushPose();
        // Draw background
        graphics.fill(startX, startY, endX, endY, colorFill);
        // Draw borders
        graphics.fill(startX, startY, endX, startY+1, borderColor); // top
        graphics.fill(startX, endY-1, endX, endY, borderColor); // bottom
        graphics.fill(startX, startY+1, startX+1, endY-1, borderColor); // left
        graphics.fill(endX-1, startY+1, endX, endY-1, borderColor); // right
        matrixStack.popPose();
    }

    private void drawBorderedGradientRect(GuiGraphics graphics, PoseStack matrixStack, int startX, int startY, int endX, int endY, int colorStart, int colorEnd, int borderColor) {
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
}
