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

public class SkillScreen extends Screen
{
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

    private static final int PERK_ICON_SIZE = 32;
    private static final int LEVEL_HEIGHT = 80;
    private static final int PERK_SPACING = 120;

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
        this.characterProgress = character.getCharacterTotalXp() / (float)getXpNeededForNextCharacterLevel(character.getCharacterLevel()+1);
        this.characterProgressBarWidth = 60 * characterProgress;

        this.levelPoints = Services.PLATFORM.getLevelUpdates(player).getLevelUpdates(); //player.getData(PlayerAttachments.LEVEL_UPDATES);
        if(levelPoints > 0) {
            currentUpdateSelection = 1;
            shouldFocusLevelUpdates = true;
        }
    }

    private double getXpNeededForNextCharacterLevel(int level) {
        return 12.5*Math.pow(level+1, 2) + 62.5*level - 75;
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
        if(character.getLevelPerkPoints() > 0)
            RenderUtil.blitWithBlend(poseStack, width / 2 - 56, 32, 0, 264, 112, 11, 512, 512, 0, 1);

        RenderUtil.blitWithBlend(poseStack, width / 2 - 20, 13, 109, 228, 80, 8, 512, 512, 1, 1);
        RenderUtil.blitWithBlend(poseStack, width / 2 - 10, 15, 119 + ((60 - characterProgressBarWidth) / 2.0f), 236, (int)(60 * characterProgress), 6, 512, 512, 1, 1);
        poseStack.popPose();

        drawScaledString(graphics, "NAME", width / 2 - 151 + 22, 15, 0xFF949494, 0.75f);
        drawScaledString(graphics, player.getDisplayName().getString(), width / 2 - 151 + 20 + font.width("NAME"), 13, 0xFFFFFFFF, 1f);
        String level = "LEVEL"; //character.getCharacterLevel();
        drawScaledString(graphics, level, width / 2 - 55, 15, 0xFF949494, 0.75f);
        drawScaledString(graphics, ""+character.getCharacterLevel(), width / 2 - 30, 13, 0xFFFFFFFF, 1f);
        String raceName = character.getRace().getName();
        drawScaledString(graphics, "RACE", width / 2 + 151 - 52, 15, 0xFF949494, 0.75f);
        drawScaledString(graphics, raceName, width / 2 + 151 - 42, 13, 0xFFFFFFFF, 1f);
        if(character.getLevelPerkPoints() > 0)
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
        if(!skillSelected) {
            for (SkillWrapper skillEntry : skillsList) {
                int x = this.width / 2 + 128 * (skillEntry.getID() + 1) - (this.currentSkill + 1) * 128; //(width / 2) - ((SKILL_BAR_CONTAINER_WIDTH / 2) + (24 * currentSkill+1))
                drawSkill(skillEntry, graphics, poseStack, x, height / 2 - 20);
            }
        } else {
            // If skill selected, show perks for selection etc.
            drawSkillPerksTree(selectedSkillObject, graphics, poseStack, width, height, mouseX, mouseY);
        }

        // If there is a level update, draw this shit on top
        if(shouldFocusLevelUpdates) {
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
        graphics.drawString(font, str, (int)(x/scale), (int)(y/scale), color);
        graphics.pose().popPose();
    }
    
    public void drawScaledCenteredString(GuiGraphics graphics, String str, int x, int y, int color, float scale) {
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);
        float w = this.font.width(str) * scale;
        x = (int)((x - w / 2) / scale);
        graphics.drawString(font, str, x, (int)(y/scale), color);
        graphics.pose().popPose();
    }

    public void drawScaledCenteredStringWithSplit(GuiGraphics graphics, String str, int x, int y, int color, float scale, int split) {
        if (str.length() >= split) {
            String parts = ClientUtil.wrap(str, split, "\n", true, "-", null);
            String[] lines = parts.split("\n");

            for(int i = 0; i < lines.length; i++)
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
        if(KeysRegistry.SKYRIM_MENU_EAST.get().matches(keyCode, scanCode)) {
            if (shouldFocusLevelUpdates) {
                if(currentUpdateSelection > 0)
                    currentUpdateSelection--;
            } else if(!skillSelected) {
                if (currentSkill > 0) {
                    currentSkill--;
                } else {
                    currentSkill = skillsList.size() - 1;
                }
            }
        } else if(KeysRegistry.SKYRIM_MENU_WEST.get().matches(keyCode, scanCode)) {
            if(shouldFocusLevelUpdates) {
                if(currentUpdateSelection < 2)
                    currentUpdateSelection++;
            } else if(!skillSelected) {
                if (currentSkill < skillsList.size() - 1) {
                    currentSkill++;
                } else {
                    currentSkill = 0;
                }
            }
        } else if(KeysRegistry.SKYRIM_MENU_ENTER.get().matches(keyCode, scanCode)) {
            if(shouldFocusLevelUpdates) {
                // Send packet based on the update selected
                StatIncreases statIncreases = Services.PLATFORM.getStatIncreases(player); //player.getData(PlayerAttachments.STAT_INCREASES);
                System.out.println(statIncreases.getHealthIncrease());
                if(currentUpdateSelection == 1) {
                    statIncreases.increaseHealth();
                } else if(currentUpdateSelection == 0) {
                    statIncreases.increaseMagicka();
                } else if(currentUpdateSelection == 2) {
                    statIncreases.increaseStamina();
                }
                System.out.println(statIncreases.getHealthIncrease());
                final UpdateStatIncreases updateStatIncreases = new UpdateStatIncreases(statIncreases);
                Dispatcher.sendToServer(updateStatIncreases);
//                PacketDistributor.SERVER.noArg().send(updateStatIncreases);
                levelPoints--;
                if(levelPoints == 0) {
                    shouldFocusLevelUpdates = false;
                    currentUpdateSelection = -1;
                }
            } else if(!skillSelected) {
                skillSelected = true;
                selectedSkillObject = skillsList.get(currentSkill);
            } else {
                // Skill panel is open, interact with it.
            }
        } else if (keyCode == 256) {
            if (!skillSelected) {
                this.onClose();
                return true;
            } else {
                // If skill is selected, close the skill's panel and go back to the main skills screen
                selectedSkillObject = null;
                skillSelected = false;
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if(scrollY < 0) {
            if (!this.skillSelected) {
                if (this.currentSkill < this.skillsList.size() - 1)
                    ++this.currentSkill;

//                this.itemList.clear();
//                this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
            } else {
                if (this.currentSkill < this.skillsList.size() - 1)
                    ++this.currentSkill;
            }
        } else if(scrollY > 0) {
            if (!this.skillSelected) {
                if(this.currentSkill > 0)
                    --this.currentSkill;

//                this.itemList.clear();
//                this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
            } else {
                if (this.currentSkill > 0)
                    --this.currentSkill;
            }
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (KeysRegistry.SKYRIM_MENU_MB1_CLICK.get().matchesMouse(button) && skillSelected) {
            SkillWrapper skill = selectedSkillObject;
            Map<Integer, List<Perk>> perksByLevel = organizePerksByLevel(skill.getSkillPerks());

            int baseY = this.height * 3 / 4 - 10;
            int maxPerksInLevel = perksByLevel.values().stream().mapToInt(List::size).max().orElse(1);
            int perkSpacing = Math.min(width / (maxPerksInLevel + 1), 100);

            for (Map.Entry<Integer, List<Perk>> entry : perksByLevel.entrySet()) {
                int level = entry.getKey();
                List<Perk> perksAtLevel = entry.getValue();

                int levelWidth = (perksAtLevel.size() - 1) * perkSpacing;
                int startX = (width - levelWidth) / 2;

                for (int i = 0; i < perksAtLevel.size(); i++) {
                    Perk perk = perksAtLevel.get(i);
                    int x = startX + i * perkSpacing;
                    int y = baseY - (level * levelHeight);

                    if (isWithinBounds(mouseX, mouseY, x - 16, y - 16, 32, 32)) {
                        handlePerkClick(perk, perksByLevel);
                        System.out.println("Perk clicked: " + perk.getName()); // Debug print
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    // Helper method to check if click is within the bounds of the perk
    private boolean isWithinBounds(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    // Handle unlocking the perk if the player has enough points and parent conditions are met
    private void handlePerkClick(Perk perk, Map<Integer, List<Perk>> perksByLevel) {
        if (perk.isUnlocked()) {
            return; // Perk is already unlocked, do nothing
        }

        // Check if the perk is unlockable (all parent perks are unlocked)
        boolean canUnlock = true;
        for (String parentName : perk.getParents()) {
            boolean parentUnlocked = false;
            for (List<Perk> perkList : perksByLevel.values()) {
                for (Perk parentPerk : perkList) {
                    if (parentPerk.getName().equals(parentName) && parentPerk.isUnlocked()) {
                        parentUnlocked = true;
                        break;
                    }
                }
                if (parentUnlocked) break;
            }
            if (!parentUnlocked) {
                canUnlock = false;
                break;
            }
        }

        if (canUnlock && perkPoints >= 1) {
            // Unlock the perk and reduce perk points
            perk.unlock();
            perkPoints--;
            character.removeLevelPerkPoint();
            character.getSkill(selectedSkillObject.getID()).unlockPerk(perk);

            UpdateCharacter updateCharacter = new UpdateCharacter(character);
            Dispatcher.sendToServer(updateCharacter);

            // Optionally: Notify the player that the perk was unlocked (e.g., play a sound, display message)
            System.out.println("Perk unlocked: " + perk.getName());

            // Refresh GUI or trigger necessary updates
            // e.g., this.init() to re-render the GUI
        } else {
            // Optionally: Notify player why the perk can't be unlocked (e.g., missing points or prerequisites)
            System.out.println("Cannot unlock perk: " + perk.getName());
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
        Map<Integer, List<Perk>> perksByLevel = organizePerksByLevel(perks);

        int baseY = height - 100;
        int maxLevel = perksByLevel.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);

        // First, draw the connections
        poseStack.pushPose();
        poseStack.translate(0, 0, 100);
        for (Map.Entry<Integer, List<Perk>> entry : perksByLevel.entrySet()) {
            int level = entry.getKey();
            List<Perk> perksAtLevel = entry.getValue();

            int levelWidth = (perksAtLevel.size() - 1) * PERK_SPACING;
            int startX = (width - levelWidth) / 2;

            for (int i = 0; i < perksAtLevel.size(); i++) {
                Perk perk = perksAtLevel.get(i);
                int x = startX + i * PERK_SPACING;
                int y = baseY - (level * LEVEL_HEIGHT);

                renderConnectionsToParents(graphics, poseStack, perk, x, y, perksByLevel, baseY);
            }
        }
        poseStack.popPose();

        // Then, draw the perks
        for (Map.Entry<Integer, List<Perk>> entry : perksByLevel.entrySet()) {
            int level = entry.getKey();
            List<Perk> perksAtLevel = entry.getValue();

            int levelWidth = (perksAtLevel.size() - 1) * PERK_SPACING;
            int startX = (width - levelWidth) / 2;

            for (int i = 0; i < perksAtLevel.size(); i++) {
                Perk perk = perksAtLevel.get(i);
                int x = startX + i * PERK_SPACING;
                int y = baseY - (level * LEVEL_HEIGHT);

                renderPerk(graphics, poseStack, perk, x, y);

                if (isWithinBounds(mouseX, mouseY, x - PERK_ICON_SIZE/2, y - PERK_ICON_SIZE/2, PERK_ICON_SIZE, PERK_ICON_SIZE)) {
                    renderPerkTooltip(graphics, poseStack, perk, mouseX, mouseY);
                }
            }
        }
    }

    private void renderTooltip(GuiGraphics graphics, PoseStack matrixStack, List<String> tooltipInfo, int mouseX, int mouseY) {
        // Set up Z layer to ensure tooltip renders on top
        RenderSystem.disableDepthTest(); // Disable depth test to ensure the tooltip is on top

        // Determine the width and height of the tooltip based on the longest line of text
        int tooltipWidth = 0;
        for (String line : tooltipInfo) {
            int lineWidth = this.font.width(line);
            if (lineWidth > tooltipWidth) {
                tooltipWidth = lineWidth;
            }
        }
        tooltipWidth += 6; // Add padding

        int tooltipHeight = tooltipInfo.size() * 12 + 6; // Height for tooltip box (12 per line, plus padding)

        // Adjust the tooltip position so it doesn't go off the screen
        int tooltipX = mouseX + 12;
        int tooltipY = mouseY - 12;
        if (tooltipX + tooltipWidth > this.width) {
            tooltipX = this.width - tooltipWidth - 12;
        }
        if (tooltipY + tooltipHeight > this.height) {
            tooltipY = this.height - tooltipHeight - 12;
        }


        // Render the background box
        drawBorderedGradientRect(graphics, matrixStack, tooltipX, tooltipY, tooltipX + tooltipWidth, tooltipY + tooltipHeight, 0xCC000000, 0xCC000000, 0xFF6E6B64);

        // Render each line of the tooltip
        int lineOffsetY = tooltipY + 6;
        for (String line : tooltipInfo) {
            graphics.drawString(font, line, tooltipX + 3, lineOffsetY, 0xFFFFFF);
            lineOffsetY += 12; // Move to the next line
        }

        // Re-enable depth test after rendering the tooltip
        RenderSystem.enableDepthTest();
    }

    private void renderPerkTooltip(GuiGraphics graphics, PoseStack poseStack, Perk perk, int mouseX, int mouseY) {
        List<String> tooltipInfo = new ArrayList<>();
        tooltipInfo.add(perk.getName());
        tooltipInfo.addAll(wrapText(perk.getDescription(), 150));
        tooltipInfo.add("Level Requirement: " + perk.getLevelRequirement());
        if (!perk.getParents().isEmpty()) {
            tooltipInfo.add("Requires: " + String.join(", ", perk.getParents()));
        }
        if (perk.isUnlocked()) {
            tooltipInfo.add("Unlocked");
        } else {
            tooltipInfo.add("Click to Unlock");
        }

        poseStack.pushPose();
        poseStack.translate(0, 0, 300); // Move tooltip to front
        renderTooltip(graphics, poseStack, tooltipInfo, mouseX, mouseY);
        poseStack.popPose();
    }

    private void renderPerk(GuiGraphics graphics, PoseStack poseStack, Perk perk, int x, int y) {
        int color = perk.isUnlocked() ? UNLOCKED_COLOR : 0xFFFFFFFF;

        // Draw perk icon
//        poseStack.pushPose();
//        poseStack.translate(0, 0, 200);
//        RenderSystem.setShaderTexture(0, SKILL_ICONS);
//        graphics.blit(SKILL_ICONS, x - PERK_ICON_SIZE/2, y - PERK_ICON_SIZE/2, 0, 0, PERK_ICON_SIZE, PERK_ICON_SIZE, 512, 512);
//        poseStack.popPose();

        // Draw perk name below the icon
//        graphics.drawCenteredString(font, perk.getName(), x, y + PERK_ICON_SIZE/2 + 5, color);
        graphics.drawCenteredString(font, perk.getName(), x, y, color);
    }


    private void renderConnectionsToParents(GuiGraphics graphics, PoseStack poseStack, Perk perk, int x, int y, Map<Integer, List<Perk>> perksByLevel, int baseY) {
        for (String parentName : perk.getParents()) {
            for (Map.Entry<Integer, List<Perk>> entry : perksByLevel.entrySet()) {
                int parentLevel = entry.getKey();
                List<Perk> parentPerks = entry.getValue();

                for (int i = 0; i < parentPerks.size(); i++) {
                    Perk parentPerk = parentPerks.get(i);
                    if (parentPerk.getName().equals(parentName)) {
                        int parentY = baseY - (parentLevel * LEVEL_HEIGHT);
                        int parentX = (perksByLevel.get(parentLevel).size() - 1) * PERK_SPACING;
                        parentX = (width - parentX) / 2 + i * PERK_SPACING;

                        int color = parentPerk.isUnlocked() && perk.isUnlocked() ? UNLOCKED_COLOR : 0xFFFFFFFF;
//                        drawLine(poseStack, x, y + PERK_ICON_SIZE/2, parentX, parentY - PERK_ICON_SIZE/2, color);
                        drawLine(poseStack, x, y, parentX, parentY, color);
                        break;
                    }
                }
            }
        }
    }

    private void drawLine(PoseStack poseStack, int x1, int y1, int x2, int y2, int color) {
        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        float red = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue = (float)(color & 255) / 255.0F;
        float alpha = 1.0F;

        bufferBuilder.addVertex(poseStack.last().pose(), x1, y1, 0).setColor(red, green, blue, alpha);
        bufferBuilder.addVertex(poseStack.last().pose(), x2, y2, 0).setColor(red, green, blue, alpha);

        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
        RenderSystem.disableBlend();
        poseStack.popPose();
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

    public Map<Integer, List<Perk>> organizePerksByLevel(List<Perk> perks) {
        Map<String, Perk> perkMap = perks.stream()
                .collect(Collectors.toMap(Perk::getName, perk -> perk));

        // Perks organized by level, with the root at level 0.
        Map<Integer, List<Perk>> perksByLevel = new HashMap<>();

        for (Perk perk : perks) {
            int level = getPerkLevel(perk, perkMap);
            perksByLevel.computeIfAbsent(level, k -> new ArrayList<>()).add(perk);
        }

        return perksByLevel;
    }

    private int getPerkLevel(Perk perk, Map<String, Perk> perkMap) {
        if (perk.getParents().isEmpty()) {
            return 0; // Root perk
        }

        // Get the maximum level of all parents
        int maxParentLevel = 0;
        for (String parentName : perk.getParents()) {
            Perk parentPerk = perkMap.get(parentName);
            if (parentPerk != null) {
                maxParentLevel = Math.max(maxParentLevel, getPerkLevel(parentPerk, perkMap) + 1);
            }
        }

        return maxParentLevel;
    }

    // Helper method to wrap text based on a maximum width
    private List<String> wrapText(String text, int maxWidth) {
        List<String> wrappedLines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (this.font.width(currentLine.toString() + word) > maxWidth) {
                wrappedLines.add(currentLine.toString());
                currentLine = new StringBuilder();
            }
            if (currentLine.length() > 0) {
                currentLine.append(" ");
            }
            currentLine.append(word);
        }

        if (currentLine.length() > 0) {
            wrappedLines.add(currentLine.toString());
        }

        return wrappedLines;
    }
}
