package com.ryankshah.skyrimcraft.screen;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.StatIncreases;
import com.ryankshah.skyrimcraft.character.skill.Skill;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.character.skill.SkillWrapper;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;

import java.util.AbstractMap;
import java.util.List;

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
    private boolean shouldFocusLevelUpdates = false;
    private String[] levelUpdateOptions = {"Magicka", "Health", "Stamina"};

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
//                skillSelected = true;
                selectedSkillObject = skillsList.get(currentSkill);
            } else {
                // Skill panel is open, interact with it.
            }
        } else if (keyCode == 256 && this.shouldCloseOnEsc()) {
            if(!skillSelected) {
                this.onClose();
                return true;
            } else {
                // If skill is selected, close the skill's panel and go back to the main skills screen
                selectedSkillObject = null;
                skillSelected = false;
            }
        }
//        else if (keyCode == 258) {
//            boolean flag = !hasShiftDown();
//            if (!this.changeFocus(flag)) {
//                this.changeFocus(flag);
//            }
//
//            return false;
//        }

        return super.keyPressed(keyCode, scanCode, modifiers);
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
}
