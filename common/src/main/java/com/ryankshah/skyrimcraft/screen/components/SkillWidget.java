package com.ryankshah.skyrimcraft.screen.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.skill.Skill;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.CommonInputs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.AbstractMap;
import java.util.List;

public class SkillWidget extends AbstractWidget implements Renderable
{
    protected static final ResourceLocation SKILL_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/skill_icons.png");

    public static final int DEFAULT_WIDTH = 150;
    public static final int DEFAULT_HEIGHT = 20;
    public static final int DEFAULT_SPACING = 8;
    protected final OnPress onPress;
    protected final Font font;
    protected final Skill skill;
    protected final int currentSkill;
    private List<Skill> skillsList;

    private final int SKILL_BAR_CONTAINER_U = 1,
            SKILL_BAR_CONTAINER_V = 194,
            SKILL_BAR_CONTAINER_WIDTH = 80,
            SKILL_BAR_CONTAINER_HEIGHT = 8,
            SKILL_BAR_U = 7,
            SKILL_BAR_V = 204,
            SKILL_BAR_WIDTH = 67,
            SKILL_BAR_HEIGHT = 2;

    public SkillWidget(
            Font pFont, int pX, int pY, int pWidth, int pHeight, Component pMessage, List<Skill> skillsList, Skill skill, int currentSkill, OnPress pOnPress
    ) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.font = pFont;
        this.skillsList = skillsList;
        this.skill = skill;
        this.currentSkill = currentSkill;
        this.onPress = pOnPress;
    }

    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        this.onPress();
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (!this.active || !this.visible) {
            return false;
        } else if (CommonInputs.selected(pKeyCode)) {
            this.playDownSound(Minecraft.getInstance().getSoundManager());
            this.onPress();
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        PoseStack poseStack = pGuiGraphics.pose();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        float skillProgress = skill.getXpProgress();
        //float skillBarWidth = SKILL_BAR_WIDTH * skillProgress;
        poseStack.pushPose();
        RenderUtil.bind(SKILL_ICONS);
        RenderUtil.blitWithBlend(poseStack, this.getX() - (SKILL_BAR_CONTAINER_WIDTH / 2), this.getY() + 48 + (SKILL_BAR_CONTAINER_HEIGHT / 2), SKILL_BAR_CONTAINER_U, SKILL_BAR_CONTAINER_V, SKILL_BAR_CONTAINER_WIDTH, SKILL_BAR_CONTAINER_HEIGHT, 512, 512, 2, 1);
        RenderUtil.blitWithBlend(poseStack, this.getX() - (SKILL_BAR_CONTAINER_WIDTH / 2) + 7, this.getY() + 49 + (SKILL_BAR_CONTAINER_HEIGHT / 2) + SKILL_BAR_HEIGHT, SKILL_BAR_U, SKILL_BAR_V, (int)(SKILL_BAR_WIDTH * skillProgress), SKILL_BAR_HEIGHT, 512, 512, 2, 1);

        AbstractMap.SimpleEntry<Integer, Integer> iconUV = getIconUV(SkillRegistry.SKILLS_REGISTRY.getResourceKey(skill).get());
        RenderUtil.blitWithBlend(poseStack, this.getX() - 32, this.getY() + 18 - 64, iconUV.getKey(), iconUV.getValue(), 64, 64, 512, 512, 2, 1);
        poseStack.popPose();
        pGuiGraphics.drawCenteredString(font, skill.getName() + " " + skill.getLevel(), this.getX(), this.getY() + 38, 0x00FFFFFF);

        if(skillsList.get(currentSkill).getID() == skill.getID()) {
            drawScaledCenteredStringWithSplit(pGuiGraphics, skill.getDescription(), this.getX(), this.getY() + 65, 0x00FFFFFF, 0.5f, 108);
        }
    }

    public static AbstractMap.SimpleEntry<Integer, Integer> getIconUV(ResourceKey<Skill> skill) {
        return SkillRegistry.SKILLS_REGISTRY.get(skill).getIconUV();
    }

    public void drawScaledCenteredString(GuiGraphics graphics, String str, int x, int y, int color, float scale) {
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);
        graphics.drawCenteredString(font, str, x * (int)(1 / scale), y * (int)(1 / scale), color);
        graphics.pose().popPose();
    }
    public void drawScaledCenteredStringWithSplit(GuiGraphics graphics, String str, int x, int y, int color, float scale, int split) {
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);
        if (str.length() >= split) {
            String[] parts = str.split("\\.");
//            String[] parts = {str.substring(0, split),str.substring(split)};
            for(int i = 0; i < parts.length; i++)
                graphics.drawCenteredString(font, parts[i], x * (int)(1 / scale), (y + (i * 6)) * (int)(1 / scale), color);
//            graphics.drawCenteredString(font, parts[1], x * (int)(1 / scale), (y+6) * (int)(1 / scale), color);
        } else {
            graphics.drawCenteredString(font, str, x * (int) (1 / scale), y * (int) (1 / scale), color);
        }
        graphics.pose().popPose();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
    }

    public interface OnPress {
        void onPress(SkillWidget pButton);
    }
}
