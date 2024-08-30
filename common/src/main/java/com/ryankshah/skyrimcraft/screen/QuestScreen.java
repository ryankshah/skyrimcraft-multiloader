package com.ryankshah.skyrimcraft.screen;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class QuestScreen extends Screen
{
    protected static final ResourceLocation QUEST_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/quest_icons.png");

    protected QuestScreen() {
        super(Component.translatable(Constants.MODID + ".questscreen.title"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = graphics.pose();
        Minecraft mc = this.minecraft;
        Window window = mc.getWindow();
        int scaledWidth = window.getGuiScaledWidth();
        int scaledHeight = window.getGuiScaledHeight();

//        this.renderBackground(graphics, mouseX, mouseY, partialTick);

        graphics.fill(0, this.height * 3 / 4 + 20, this.width, this.height, 0xAA000000);
        graphics.fill(0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xFF6E6B64);
        //1,14 - 79, 18 || 81,14
        drawBorderedRect(graphics, poseStack, this.width / 2 - 170, this.height / 2 - 80, this.width / 2 + 170, this.height / 2 + 60, 0xAA000000, 0xFF6E6B64);

        graphics.fill(this.width / 2 - 70, this.height / 2 - 60, this.width / 2 - 69, this.height / 2 + 40, 0xFF6E6B64);

        RenderSystem.setShaderTexture(0, QUEST_ICONS);
        RenderUtil.blitWithBlend(poseStack, this.width / 2 - 20, this.height / 2 - 60, 1, 14, 79, 18, 256, 256, 2, 1);

        // time
        graphics.drawString(font, calculateSkyrimTime(minecraft.player.level()),this.width - 230, this.height - 24, 0xFFFFFFFF);

//        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    private String calculateSkyrimTime(Level world) {
        StringBuilder builder = new StringBuilder();
        int dayNum = (int)(this.minecraft.level.getDayTime() / 24000L);
        int monthNum = (int)(this.minecraft.level.getDayTime() / (24000L * 31)) + 1;
        String day = getDayName((dayNum%7) + 1);
        String month = getMonthName(monthNum);
        String year = "4E 201";

        builder.append(day);
        builder.append(", ");
        builder.append(ordinal(dayNum));
        builder.append(" day of ");
        builder.append(month);
        builder.append(", ");
        builder.append(year);

        return builder.toString();
    }

    private String ordinal(int num) {
        String[] suffix = {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        int m = num % 100;
        return String.valueOf(num) + suffix[(m > 3 && m < 21) ? 0 : (m % 10)];
    }

    private String getDayName(int day) {
        String name = "";
        switch(day) {
            case 1:
                name = "Morndas";
                break;
            case 2:
                name = "Tirdas";
                break;
            case 3:
                name = "Middas";
                break;
            case 4:
                name = "Turdas";
                break;
            case 5:
                name = "Fredas";
                break;
            case 6:
                name = "Loredas";
                break;
            case 7:
                name = "Sundas";
                break;
            default:
                name = "Invalid Day!";
                break;
        }
        return name;
    }

    private String getMonthName(int month) {
        String name = "";
        switch(month) {
            case 1:
                name = "Morning Star";
                break;
            case 2:
                name = "Sun's Dawn";
                break;
            case 3:
                name = "First Seed";
                break;
            case 4:
                name = "Rain's Hand";
                break;
            case 5:
                name = "Second Seed";
                break;
            case 6:
                name = "Midyear";
                break;
            case 7:
                name = "Sun's Height";
                break;
            case 8:
                name = "Last Seed";
                break;
            case 9:
                name = "Heart Fire";
                break;
            case 10:
                name = "Frostfall";
                break;
            case 11:
                name = "Sun's Dusk";
                break;
            case 12:
                name = "Evening Star";
                break;
            default:
                name = "Invalid Month!";
                break;
        }
        return name;
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
}