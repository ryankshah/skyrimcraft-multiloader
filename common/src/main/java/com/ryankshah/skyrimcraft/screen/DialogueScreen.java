package com.ryankshah.skyrimcraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class DialogueScreen extends Screen
{
    private static final int WINDOW_WIDTH = 320;
    private static final int WINDOW_HEIGHT = 160;
    private final String npcName;
    private final String dialogueText;
    private final List<DialogueOption> dialogueOptions;
//    private final ResourceLocation DIALOGUE_BACKGROUND = new ResourceLocation("modid", "textures/gui/dialogue.png");
    private int selectedOption = 0;
    private long lastNavigationTime = 0;
    private static final long NAVIGATION_COOLDOWN = 200;

    // Colors for different option states
    private static final int COLOR_NORMAL = 0xCCCCCC;
    private static final int COLOR_SELECTED = 0xFFFFFF;
    private static final int COLOR_HOVER = 0xFFFFFF;

    // Store rendered option positions for mouse hover detection
    private final List<OptionButton> optionButtons = new ArrayList<>();

    public DialogueScreen(String npcName, String dialogueText) {
        super(Component.literal(npcName));
        this.npcName = npcName;
        this.dialogueText = dialogueText;
        this.dialogueOptions = new ArrayList<>();
    }

    @Override
    protected void init() {
        optionButtons.clear();

        // Calculate the right-side area for options
        int dialogueAreaWidth = WINDOW_WIDTH / 2;  // Left half for dialogue
        int optionsAreaX = this.width / 2;  // Start options from middle of screen
        int startY = (this.height - WINDOW_HEIGHT) / 2 + 60;  // Move options down below NPC name

        // Create option buttons in right half
        for (int i = 0; i < dialogueOptions.size(); i++) {
            DialogueOption option = dialogueOptions.get(i);
            int buttonX = optionsAreaX + 20;  // Indent options slightly
            int buttonY = startY + (i * 20) + 20;  // Space options evenly
            optionButtons.add(new OptionButton(buttonX, buttonY, 250, 16, option));
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
//        this.renderBackground(graphics, mouseX, mouseY, partialTick);

        // Draw the dialogue window background
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShaderTexture(0, DIALOGUE_BACKGROUND);

        // Calculate base positions
        int dialogueAreaWidth = WINDOW_WIDTH / 2;
        int startX = (this.width - WINDOW_WIDTH) / 2;
        int startY = (this.height - WINDOW_HEIGHT) / 2;

        // Draw background texture
//        graphics.blit(x, y, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Draw NPC name
        graphics.drawString(this.font, npcName, startX + 10, startY + 10, 0xFFFFFFFF);

        // Draw dialogue text with word wrap
        List<FormattedCharSequence> lines = font.split(Component.literal(dialogueText), WINDOW_WIDTH - 20);
        int lineY = startY + 40;
        for (FormattedCharSequence line : lines) {
            graphics.drawString(this.font, line, startX + 10, lineY, 0xFFDDDDDD);
            lineY += 12;
        }

        // Draw dialogue options
        for (int i = 0; i < optionButtons.size(); i++) {
            OptionButton button = optionButtons.get(i);
            boolean isSelected = (i == selectedOption);
            boolean isHovered = button.isHovered(mouseX, mouseY);

            // Update selected option based on mouse hover
            if (isHovered && !isSelected) {
                selectedOption = i;
                playNavigationSound();
            }

            // Determine text color based on state
            int color = isSelected || isHovered ? COLOR_HOVER : COLOR_NORMAL;

            // Draw option text with arrow indicator if selected
            Component optionText = Component.literal(isSelected ? "→ " : "  ")
                    .append(button.option.text);
            graphics.drawString(this.font, optionText,
                    button.x, button.y,
                    color);
        }

//        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastNavigationTime < NAVIGATION_COOLDOWN) {
            return false;
        }

        if(KeysRegistry.SKYRIM_MENU_NORTH.get().matches(keyCode, scanCode)) {
            selectedOption = Math.max(0, selectedOption - 1);
            lastNavigationTime = currentTime;
            playNavigationSound();
            return true;
        } else if(KeysRegistry.SKYRIM_MENU_SOUTH.get().matches(keyCode, scanCode)) {
            selectedOption = Math.min(dialogueOptions.size() - 1, selectedOption + 1);
            lastNavigationTime = currentTime;
            playNavigationSound();
            return true;
        } else if(KeysRegistry.SKYRIM_MENU_ENTER.get().matches(keyCode, scanCode)) {
            if (!dialogueOptions.isEmpty()) {
                selectCurrentOption();
            }
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (KeysRegistry.SKYRIM_MENU_MB1_CLICK.get().matchesMouse(button)) {  // Left click
            for (int i = 0; i < optionButtons.size(); i++) {
                if (optionButtons.get(i).isHovered(mouseX, mouseY)) {
                    selectedOption = i;
                    selectCurrentOption();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    private void playNavigationSound() {
        Minecraft.getInstance().player.playSound(
                SoundEvents.UI_BUTTON_CLICK.value(),
                0.2F,
                1.0F
        );
    }

    private void selectCurrentOption() {
        if (selectedOption >= 0 && selectedOption < dialogueOptions.size()) {
            DialogueOption selected = dialogueOptions.get(selectedOption);
            playSelectionSound();
            selected.action.run();
        }
    }

    private void playSelectionSound() {
        Minecraft.getInstance().player.playSound(
                SoundEvents.UI_BUTTON_CLICK.value(),
                0.3F,
                0.8F
        );
    }

    public void addDialogueOption(String text, Runnable action) {
        dialogueOptions.add(new DialogueOption(text, action));
    }

    private static class DialogueOption {
        private final String text;
        private final Runnable action;

        public DialogueOption(String text, Runnable action) {
            this.text = text;
            this.action = action;
        }
    }

    private static class OptionButton {
        final int x, y, width, height;
        final DialogueOption option;

        OptionButton(int x, int y, int width, int height, DialogueOption option) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.option = option;
        }

        boolean isHovered(double mouseX, double mouseY) {
            return mouseX >= x && mouseX < x + width &&
                    mouseY >= y && mouseY < y + height;
        }
    }


    // Example usage:
    public static void openDialogue(Minecraft minecraft, String npcName, String text) {
        DialogueScreen screen = new DialogueScreen(npcName, text);

        // Add example dialogue options
        screen.addDialogueOption("Tell me more about yourself", () -> {
            // Handle this option
        });

        screen.addDialogueOption("I need to trade", () -> {
            // Handle trading
        });

        screen.addDialogueOption("Goodbye", () -> {
            // Close dialogue
        });

        minecraft.setScreen(screen);
    }
}