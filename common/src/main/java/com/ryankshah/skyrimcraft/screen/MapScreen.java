package com.ryankshah.skyrimcraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.network.character.FastTravel;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import com.ryankshah.skyrimcraft.util.RenderUtil;
import com.ryankshah.skyrimcraft.util.Waypoint;
import commonnetwork.api.Dispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import org.joml.Matrix4f;

import java.util.List;

public class MapScreen extends Screen
{
    private static final int INITIAL_RENDER_DISTANCE = 8;
    private static final int CHUNK_SIZE = 16;
    private static final int MIN_BLOCK_SIZE = 1;
    private static final int MAX_BLOCK_SIZE = 8;
    private static final int ICON_WIDTH = 12;
    private static final int ICON_HEIGHT = 16;
    private static final float BASE_MARKER_SIZE = 8.0f;
    private final ResourceLocation OVERLAY_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/overlay_icons.png");

    private final Minecraft mc;
    private final Level level;
    private int centerX, centerZ;
    private int minHeight, maxHeight;
    private int mapOffsetX = 0;
    private int mapOffsetZ = 0;
    private int blockSize = 2;
    private int renderDistance = INITIAL_RENDER_DISTANCE;
    private int dragStartX, dragStartY;
    private boolean isDragging = false;
    private Character character;
    private ExtraCharacter extraCharacter;
    private CompassFeature hoveredFeature = null;
    private CompassFeature selectedFeature;
    private boolean showFastTravelPopup = false;
    private int selectedOption = 0; // 0 for Yes, 1 for No

    public MapScreen() {
        super(Component.empty());
        this.mc = Minecraft.getInstance();
        this.level = mc.level;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        if (this.level == null || this.mc.player == null) {
            return;
        }

        this.character = Services.PLATFORM.getCharacter(mc.player);
        this.extraCharacter = Services.PLATFORM.getExtraCharacter(mc.player);

        List<CompassFeature> mapFeatures = character.getCompassFeatures();
        List<Waypoint> waypoints = extraCharacter.getWaypoints();

        centerX = (int) this.mc.player.getX() + mapOffsetX;
        centerZ = (int) this.mc.player.getZ() + mapOffsetZ;

        calculateHeightRange();
        renderTopDownWorld(guiGraphics);
        renderPlayerMarker(guiGraphics);
        renderWaypoints(guiGraphics, waypoints);
        renderMapFeatures(guiGraphics, mapFeatures, mouseX, mouseY);

        String time = calculateSkyrimTime(minecraft.player.level());
        drawScaledString(guiGraphics, time,this.width - font.width(time) + 30, this.height - 24, 0xFFFFFFFF, 0.75F);

        guiGraphics.fillGradient(0, this.height * 3 / 4 + 20, this.width, this.height, 0xAA000000, 0xAA000000);
        guiGraphics.fillGradient(0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xFF5D5A51, 0xFF5D5A51);

        // Draw buttons for input controls
        drawGradientRect(guiGraphics, guiGraphics.pose(), 17, this.height - 29, 21 + font.width("↑ ↓ → ←") + 4, this.height - 14, 0xAA000000, 0xAA000000, 0xFF5D5A51);
        guiGraphics.drawString(font, "↑ ↓ → ←", 21, this.height - 25, 0x00FFFFFF);
        guiGraphics.drawString(font, "Move", 19 + font.width("↑ ↓ → ←") + 10, this.height - 25, 0x00FFFFFF);

        // Render tooltip for hovered map feature
        if (hoveredFeature != null) {
            renderFeatureTooltip(guiGraphics, hoveredFeature, mouseX, mouseY);
        }

        if (showFastTravelPopup) {
            renderFastTravelPopup(guiGraphics, mouseX, mouseY);
        }
    }

    private void calculateHeightRange() {
        minHeight = Integer.MAX_VALUE;
        maxHeight = Integer.MIN_VALUE;
        int playerChunkX = centerX >> 4;
        int playerChunkZ = centerZ >> 4;

        for (int chunkX = playerChunkX - renderDistance; chunkX <= playerChunkX + renderDistance; chunkX++) {
            for (int chunkZ = playerChunkZ - renderDistance; chunkZ <= playerChunkZ + renderDistance; chunkZ++) {
                LevelChunk chunk = level.getChunk(chunkX, chunkZ);
                for (int x = 0; x < CHUNK_SIZE; x++) {
                    for (int z = 0; z < CHUNK_SIZE; z++) {
                        int height = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
                        minHeight = Math.min(minHeight, height);
                        maxHeight = Math.max(maxHeight, height);
                    }
                }
            }
        }
    }

    private void renderTopDownWorld(GuiGraphics guiGraphics) {
        int screenWidth = this.width;
        int screenHeight = this.height;
        int mapSize = renderDistance * 2 * CHUNK_SIZE * blockSize;
        int startX = (screenWidth - mapSize) / 2;
        int startY = (screenHeight - mapSize) / 2;

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(startX, startY, 0);

        Matrix4f matrix = poseStack.last().pose();
        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        int chunkRadius = renderDistance;
        int centerChunkX = (centerX + mapOffsetX) >> 4;
        int centerChunkZ = (centerZ + mapOffsetZ) >> 4;

        for (int chunkX = centerChunkX - chunkRadius; chunkX <= centerChunkX + chunkRadius; chunkX++) {
            for (int chunkZ = centerChunkZ - chunkRadius; chunkZ <= centerChunkZ + chunkRadius; chunkZ++) {
                renderChunkTopLayer(bufferBuilder, matrix, chunkX, chunkZ);
            }
        }
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());

        // Render chunk grid lines
        renderChunkGridLines(guiGraphics, mapSize);

        poseStack.popPose();

    }

    private void renderPlayerMarker(GuiGraphics guiGraphics) {
        int screenWidth = this.width;
        int screenHeight = this.height;
        int mapSize = renderDistance * 2 * CHUNK_SIZE * blockSize;
        int startX = (screenWidth - mapSize) / 2;
        int startY = (screenHeight - mapSize) / 2;

        // Calculate player's position on the map
        int playerX = (int) ((this.mc.player.getX() - (centerX + mapOffsetX)) * blockSize) + mapSize / 2;
        int playerZ = (int) ((this.mc.player.getZ() - (centerZ + mapOffsetZ)) * blockSize) + mapSize / 2;

        // Calculate marker size based on zoom level
        float markerSize = BASE_MARKER_SIZE * (blockSize / 2.0f);

        // Only render if the player is within the map bounds
        if (playerX >= 0 && playerX < mapSize && playerZ >= 0 && playerZ < mapSize) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(startX + playerX, startY + playerZ, 0);
            poseStack.scale(markerSize/8, markerSize/8, 1);
            RenderUtil.bind(minecraft.player.getSkin().texture());
            RenderUtil.blitWithBlend(guiGraphics.pose(), -4, -4, 8, 8, 8, 8, 64, 64, 1, 1);
            poseStack.popPose();
        }
    }

    private void renderWaypoints(GuiGraphics guiGraphics, List<Waypoint> waypoints) {
        int screenWidth = this.width;
        int screenHeight = this.height;
        int mapSize = renderDistance * 2 * CHUNK_SIZE * blockSize;
        int startX = (screenWidth - mapSize) / 2;
        int startY = (screenHeight - mapSize) / 2;

        for (Waypoint waypoint : waypoints) {
            BlockPos pos = waypoint.getBlockPos();
            int waypointX = (int) ((pos.getX() - (centerX + mapOffsetX)) * blockSize) + mapSize / 2;
            int waypointZ = (int) ((pos.getZ() - (centerZ + mapOffsetZ)) * blockSize) + mapSize / 2;

            if (waypointX >= 0 && waypointX < mapSize && waypointZ >= 0 && waypointZ < mapSize) {
                guiGraphics.pose().pushPose();
                RenderUtil.bind(OVERLAY_ICONS);
                RenderUtil.blitWithBlend(guiGraphics.pose(), startX + waypointX, startY + waypointZ, 0, 158, 8, 16, 256, 256, 1, 1.0f);
                guiGraphics.pose().popPose();
            }
        }
    }

    private void renderMapFeatures(GuiGraphics guiGraphics, List<CompassFeature> mapFeatures, int mouseX, int mouseY) {
        int screenWidth = this.width;
        int screenHeight = this.height;
        int mapSize = renderDistance * 2 * CHUNK_SIZE * blockSize;
        int startX = (screenWidth - mapSize) / 2;
        int startY = (screenHeight - mapSize) / 2;

        hoveredFeature = null;

        for (CompassFeature feature : mapFeatures) {
            BlockPos pos = feature.getBlockPos();
            int featureX = (int) ((pos.getX() - (centerX + mapOffsetX)) * blockSize) + mapSize / 2;
            int featureZ = (int) ((pos.getZ() - (centerZ + mapOffsetZ)) * blockSize) + mapSize / 2;

            if (featureX >= 0 && featureX < mapSize && featureZ >= 0 && featureZ < mapSize) {
                int renderX = startX + featureX - ICON_WIDTH / 2;
                int renderZ = startY + featureZ - ICON_HEIGHT / 2;
                int u = feature.getIconUV().getKey(), v = feature.getIconUV().getValue();

                guiGraphics.pose().pushPose();

                // Draw gradient rect behind the icon
                drawGradientRect(guiGraphics, guiGraphics.pose(), renderX - 2, renderZ - 2,
                        renderX + ICON_WIDTH + 2, renderZ + ICON_HEIGHT + 2,
                        0xAA000000, 0xAA000000, 0xFF5D5A51);

                RenderUtil.bind(OVERLAY_ICONS);
                RenderUtil.blitWithBlend(guiGraphics.pose(), renderX, renderZ, u, v, ICON_WIDTH, ICON_HEIGHT, 256, 256, 1, 1.0f);
                guiGraphics.pose().popPose();

                // Check if the mouse is hovering over this feature
                if (mouseX >= renderX && mouseX < renderX + ICON_WIDTH &&
                        mouseY >= renderZ && mouseY < renderZ + ICON_HEIGHT) {
                    hoveredFeature = feature;
                }
            }
        }
    }

    private void renderCube(GuiGraphics guiGraphics, int x, int y, int color) {
        int size = blockSize;
        guiGraphics.fill(x, y, x + size, y + size, color);
    }

    private void renderFeatureTooltip(GuiGraphics guiGraphics, CompassFeature feature, int mouseX, int mouseY) {
        if(feature.getIconUV() == null)
            return;

        int tooltipWidth = 10 + font.width(feature.getFeatureName());
        int tooltipHeight = 16;
        int padding = 4;

        // Position tooltip above and to the right of the cursor
        int tooltipX = mouseX + 12;
        int tooltipY = mouseY - tooltipHeight - 12;

        // Ensure tooltip stays within screen bounds
        tooltipX = Math.min(tooltipX, this.width - tooltipWidth - padding);
        tooltipY = Math.max(tooltipY, padding);

        guiGraphics.pose().pushPose();
        RenderSystem.enableDepthTest();
        // Render tooltip background
        drawGradientRect(guiGraphics, guiGraphics.pose(), tooltipX, tooltipY, tooltipX + tooltipWidth, tooltipY + tooltipHeight, 0xAA000000, 0xAA000000, 0xFF5D5A51);
        // Render feature name
        guiGraphics.drawString(font, feature.getFeatureName(), tooltipX + padding, tooltipY + padding, 0xFFFFFFFF);
        guiGraphics.pose().popPose();
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDragging) {
            // Reverse the direction of drag
            mapOffsetX -= (int) (dragX / blockSize);
            mapOffsetZ -= (int) (dragY / blockSize);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (showFastTravelPopup) {
            int popupWidth = 200;
            int popupHeight = 80;
            int popupX = (this.width - popupWidth) / 2;
            int popupY = (this.height - popupHeight) / 2;
            int optionY = popupY + popupHeight - 30;

            if (mouseY >= optionY - 10 && mouseY <= optionY + 10) {
                int yesX = popupX + popupWidth / 4;
                int noX = popupX + 3 * popupWidth / 4;

                if (mouseX >= yesX - 20 && mouseX <= yesX + 20) {
                    handleFastTravelChoice(true);
                    return true;
                } else if (mouseX >= noX - 20 && mouseX <= noX + 20) {
                    handleFastTravelChoice(false);
                    return true;
                }
            }
        } else {
            if (hoveredFeature != null) {
                handleFeatureClick(hoveredFeature);
                return true;
            }
            isDragging = true;
            dragStartX = (int) mouseX;
            dragStartY = (int) mouseY;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDragging = false;
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int oldBlockSize = blockSize;
        if (scrollY > 0 && blockSize < MAX_BLOCK_SIZE) {
            blockSize++;
            renderDistance = Math.max(1, renderDistance - 1);
        } else if (scrollY < 0) {
            int minBlockSize = Math.max(MIN_BLOCK_SIZE, this.width / (renderDistance * 2 * CHUNK_SIZE));
            if (blockSize > minBlockSize) {
                blockSize--;
                renderDistance = Math.min(16, renderDistance + 1);
            }
        }

        // If blockSize changed, recenter the map
        if (oldBlockSize != blockSize) {
            mapOffsetX = mapOffsetX * blockSize / oldBlockSize;
            mapOffsetZ = mapOffsetZ * blockSize / oldBlockSize;
        }

        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (showFastTravelPopup) {
            if (KeysRegistry.SKYRIM_MENU_EAST.get().matches(keyCode, scanCode) || KeysRegistry.SKYRIM_MENU_WEST.get().matches(keyCode, scanCode)) {
                selectedOption = 1 - selectedOption; // Toggle between 0 and 1
                return true;
            } else if (KeysRegistry.SKYRIM_MENU_ENTER.get().matches(keyCode, scanCode)) {
                handleFastTravelChoice(selectedOption == 0);
                return true;
            }
        } else {
            int moveAmount = 16; // Move by one chunk
            if (KeysRegistry.SKYRIM_MENU_NORTH.get().matches(keyCode, scanCode)) {
                mapOffsetZ -= moveAmount;
            } else if (KeysRegistry.SKYRIM_MENU_SOUTH.get().matches(keyCode, scanCode)) {
                mapOffsetZ += moveAmount;
            } else if (KeysRegistry.SKYRIM_MENU_EAST.get().matches(keyCode, scanCode)) {
                mapOffsetX -= moveAmount;
            } else if (KeysRegistry.SKYRIM_MENU_WEST.get().matches(keyCode, scanCode)) {
                mapOffsetX += moveAmount;
            } else {
                return super.keyPressed(keyCode, scanCode, modifiers);
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void handleFeatureClick(CompassFeature feature) {
        selectedFeature = feature;
        showFastTravelPopup = true;
        selectedOption = 0; // Default to "Yes"
    }

    private void handleFastTravelChoice(boolean choice) {
        if (choice) {
//            if(character.getCurrentTarget() != -1) // Does player current have a target
//                return;
            mc.setScreen(new SkyrimLoadingScreen());
            FastTravel fastTravelPacket = new FastTravel(selectedFeature.getBlockPos());
            Dispatcher.sendToServer(fastTravelPacket);
            System.out.println("Fast traveling to " + selectedFeature.getFeatureName());
            // Implement fast travel logic here
        } else {
            System.out.println("Fast travel cancelled");
        }
        showFastTravelPopup = false;
    }

    private void renderFastTravelPopup(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int popupWidth = 200;
        int popupHeight = 80;
        int popupX = (this.width - popupWidth) / 2;
        int popupY = (this.height - popupHeight) / 2;

        // Draw popup background
        drawGradientRect(guiGraphics, guiGraphics.pose(), popupX, popupY, popupX + popupWidth, popupY + popupHeight, 0xAA000000, 0xAA000000, 0xFF5D5A51);

        // Draw text
        String message = "Do you wish to fast travel to " + selectedFeature.getFeatureName() + "?";
        drawScaledCenteredString(guiGraphics, message, this.width / 2, popupY + 20, 0xFFFFFFFF, 1.0f);

        // Draw options
        String yesText = selectedOption == 0 ? "< Yes >" : "Yes";
        String noText = selectedOption == 1 ? "< No >" : "No";

        int yesX = popupX + popupWidth / 4;
        int noX = popupX + 3 * popupWidth / 4;
        int optionY = popupY + popupHeight - 30;

        drawScaledCenteredString(guiGraphics, yesText, yesX, optionY, 0xFFFFFFFF, 1.0f);
        drawScaledCenteredString(guiGraphics, noText, noX, optionY, 0xFFFFFFFF, 1.0f);

        // Check for mouse hover
        if (mouseY >= optionY - 10 && mouseY <= optionY + 10) {
            if (mouseX >= yesX - 20 && mouseX <= yesX + 20) {
                selectedOption = 0;
            } else if (mouseX >= noX - 20 && mouseX <= noX + 20) {
                selectedOption = 1;
            }
        }
    }


    private void renderChunkTopLayer(VertexConsumer consumer, Matrix4f matrix, int chunkX, int chunkZ) {
        LevelChunk chunk = level.getChunk(chunkX, chunkZ);
        if (chunk == null) {
            return;
        }

        int startX = (chunkX - ((centerX + mapOffsetX) >> 4) + renderDistance) * CHUNK_SIZE * blockSize;
        int startZ = (chunkZ - ((centerZ + mapOffsetZ) >> 4) + renderDistance) * CHUNK_SIZE * blockSize;

        for (int blockX = 0; blockX < CHUNK_SIZE; blockX++) {
            for (int blockZ = 0; blockZ < CHUNK_SIZE; blockZ++) {
                int worldX = chunkX * CHUNK_SIZE + blockX;
                int worldZ = chunkZ * CHUNK_SIZE + blockZ;
                int y = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, blockX, blockZ);
                BlockPos pos = new BlockPos(worldX, y, worldZ);
                BlockState state = chunk.getBlockState(pos);

                int color = getBlockColor(state, pos, y);
                int x = startX + blockX * blockSize;
                int z = startZ + blockZ * blockSize;

                consumer.addVertex(matrix, x, z + blockSize, 0).setColor(color);
                consumer.addVertex(matrix, x + blockSize, z + blockSize, 0).setColor(color);
                consumer.addVertex(matrix, x + blockSize, z, 0).setColor(color);
                consumer.addVertex(matrix, x, z, 0).setColor(color);
            }
        }
    }

    private int getBlockColor(BlockState state, BlockPos pos, int height) {
        int baseColor = state.getMapColor(level, pos).col;
        float heightFactor = (float) (height - minHeight) / (maxHeight - minHeight);
        return applyShading(baseColor, heightFactor);
    }

    private int applyShading(int color, float factor) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        // Adjust the shading formula to make higher points slightly darker
        float shadingFactor = 1.0f - (factor * 0.3f);  // 0.3f controls the intensity of the shading effect

        r = (int) (r * shadingFactor);
        g = (int) (g * shadingFactor);
        b = (int) (b * shadingFactor);

        return (r << 16) | (g << 8) | b | 0xFF000000;
    }

    private void renderChunkGridLines(GuiGraphics guiGraphics, int mapSize) {
        int lineColor = 0x80FFFFFF; // Semi-transparent white
        for (int i = 0; i <= renderDistance * 2; i++) {
            int linePos = i * CHUNK_SIZE * blockSize;
            // Vertical lines
            guiGraphics.vLine(linePos, 0, mapSize, lineColor);
            // Horizontal lines
            guiGraphics.hLine(0, mapSize, linePos, lineColor);
        }
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

    @Override
    public boolean isPauseScreen() {
        return false;
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
}