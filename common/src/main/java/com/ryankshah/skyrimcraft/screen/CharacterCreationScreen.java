package com.ryankshah.skyrimcraft.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.network.character.CreateCharacter;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import commonnetwork.api.Dispatcher;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;

import java.util.List;

public class CharacterCreationScreen extends Screen
{
    //protected static final ResourceLocation OVERLAY_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/character_creation_icons.png");

    public static final CubeMap CUBE_MAP = new CubeMap(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/panorama/panorama"));
    private static final ResourceLocation PANORAMA_OVERLAY = ResourceLocation.withDefaultNamespace("textures/gui/title/background/panorama_overlay.png");
    private final PanoramaRenderer panorama = new PanoramaRenderer(CUBE_MAP);
//    private final CUbema panorama = new RenderSkybox(CUBE_MAP);
    private final boolean fading = true;
    private long fadeInStart;
    private float cubeMapPosition = 0.0f;

    private List<Race> races;
    private int currentRace;
    private Race currentRaceObject;

    public CharacterCreationScreen() {
        super(Component.translatable(Constants.MODID + ".charactercreationgui.title"));
        races = Race.getRaces();
        currentRace = 0;
        currentRaceObject = races.get(currentRace);
        final CreateCharacter createCharacter = new CreateCharacter(currentRaceObject.getId(), false);
        Dispatcher.sendToServer(createCharacter);
//        PacketDistributor.SERVER.noArg().send(createCharacter);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.fadeInStart == 0L && this.fading) {
            this.fadeInStart = Util.getMillis();
        }

        float f = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
//        graphics.fill(0, 0, this.width, this.height, -1);
        this.panorama.render(graphics, width, height, Mth.clamp(f, 0.0F, 1.0F), partialTicks); // TODO: Check this!
//        CUBE_MAP.render(this.minecraft, Mth.sin(cubeMapPosition * 0.001F) * 5.0F + 25.0F, -cubeMapPosition * 0.1F, Mth.clamp(f, 0.0F, 1.0F));
        int i = 274;
        int j = this.width / 2 + 40;
        RenderSystem.enableBlend();
        graphics.setColor(1.0F, 1.0F, 1.0F, this.fading ? (float)Mth.ceil(Mth.clamp(f, 0.0F, 1.0F)) : 1.0F);
        graphics.blit(PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        float f1 = this.fading ? Mth.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
        int l = Mth.ceil(f1 * 255.0F) << 24;
        if ((l & -67108864) != 0) {
            RenderSystem.enableDepthTest();
            // Render stuff...
            renderPlayer(graphics, i + 51, j + 75, 120, i + 51, j - 25 - 150, minecraft.player, currentRaceObject);

            RenderSystem.disableDepthTest();
            RenderSystem.disableBlend();

            // Render race selection
            renderSelectionGui(graphics);

            graphics.fillGradient(0, this.height * 3 / 4 + 20, this.width, this.height, 0xAA000000, 0xAA000000);
            graphics.fillGradient(0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xAAFFFFFF, 0xAAFFFFFF);

            drawBorderedGradientRect(graphics, 17, this.height - 29, 32 + font.width("Enter"), this.height - 14, 0xAA000000, 0xAA000000, 0xAAFFFFFF);
            graphics.drawString(font, "Enter", 25, this.height - 25, 0x00FFFFFF);
            graphics.drawString(font, "Confirm Race", 32 + font.width("Enter") + 6, this.height - 25, 0x00FFFFFF);

            graphics.drawString(font, "Name", this.width - 140 + font.width("Name"), this.height - 25, 0x00C0C0C0);
            graphics.drawString(font, minecraft.player.getDisplayName(), this.width - 120 + font.width("Name") + 8, this.height - 25, 0x00FFFFFF);
            //        poseStack.popPose();
            //        poseStack.popPose();
        }
    }

    //TODO: Check this
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if(scrollY > 0) {
            if (this.currentRace > 0)
                --this.currentRace;
            else
                this.currentRace = 0;

            currentRaceObject = races.get(currentRace);
            final CreateCharacter createCharacter = new CreateCharacter(currentRaceObject.getId(), false);
            Dispatcher.sendToServer(createCharacter);
//            PacketDistributor.SERVER.noArg().send(createCharacter);
        } else if(scrollY < 0) {
            if (this.currentRace < this.races.size() - 1)
                ++this.currentRace;
            else
                this.currentRace = this.races.size() - 1;

            currentRaceObject = races.get(currentRace);
            final CreateCharacter createCharacter = new CreateCharacter(currentRaceObject.getId(), false);
            Dispatcher.sendToServer(createCharacter);
//            PacketDistributor.SERVER.noArg().send(createCharacter);
        }
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (KeysRegistry.SKYRIM_MENU_SOUTH.get().matches(keyCode, scanCode)) {
            if (this.currentRace < this.races.size() - 1)
                ++this.currentRace;
            else
                this.currentRace = this.races.size() - 1;

            currentRaceObject = races.get(currentRace);
            final CreateCharacter createCharacter = new CreateCharacter(currentRaceObject.getId(), false);
            Dispatcher.sendToServer(createCharacter);
//            PacketDistributor.SERVER.noArg().send(createCharacter);
        }

        if (KeysRegistry.SKYRIM_MENU_NORTH.get().matches(keyCode, scanCode)) {
            if (this.currentRace > 0)
                --this.currentRace;
            else
                this.currentRace = 0;

            currentRaceObject = races.get(currentRace);
            final CreateCharacter createCharacter = new CreateCharacter(currentRaceObject.getId(), false);
            Dispatcher.sendToServer(createCharacter);
//            PacketDistributor.SERVER.noArg().send(createCharacter);
        }

        if (KeysRegistry.SKYRIM_MENU_ENTER.get().matches(keyCode, scanCode)) {
            final CreateCharacter createCharacter = new CreateCharacter(currentRaceObject.getId(), true);
            Dispatcher.sendToServer(createCharacter);
//            PacketDistributor.SERVER.noArg().send(createCharacter);
//            System.out.println(minecraft.player.getData(PlayerAttachments.SKILLS).getRace().getName() + " " + minecraft.player.getData(PlayerAttachments.SKILLS).getRace().getStartingSkills());
            minecraft.setScreen(null);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public static void renderPlayer(GuiGraphics graphics, int p_228187_0_, int p_228187_1_, int p_228187_2_, float p_228187_3_, float p_228187_4_, LivingEntity p_228187_5_, Race currentRaceObject) {
        float f = (float)Math.atan((double)(p_228187_3_ / 40.0F));
        float f1 = (float)Math.atan((double)(p_228187_4_ / 40.0F));
        graphics.pose().translate((float)p_228187_0_, (float)p_228187_1_, 300F);
        graphics.pose().scale(1.0F, 1.0F, -1.0F);
        graphics.pose().translate(0.0D, 0.0D, 1000.0D);
        graphics.pose().scale((float)p_228187_2_, (float)p_228187_2_, (float)p_228187_2_);
        Quaternionf quaternion = Axis.ZP.rotationDegrees(180.0F);
        Quaternionf quaternion1 = Axis.XP.rotationDegrees(f1); // f1 * 20.0F
        quaternion.mul(quaternion1);
        graphics.pose().mulPose(quaternion);
        float f2 = p_228187_5_.yBodyRot;
        float f3 = p_228187_5_.getYRot();
        float f4 = p_228187_5_.getXRot();
        float f5 = p_228187_5_.yHeadRotO;
        float f6 = p_228187_5_.yHeadRot;
        p_228187_5_.yBodyRot = 180.0F + f * 20.0F;
        p_228187_5_.setYRot(180.0F + f * 20.0F);
        p_228187_5_.setXRot(-f1 * 20.0F);
        p_228187_5_.yHeadRot = 225f + f;
        p_228187_5_.yHeadRotO = 225f + f;
        Lighting.setupForFlatItems();
        EntityRenderDispatcher entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conjugate();
        entityrenderermanager.overrideCameraOrientation(quaternion1);
        entityrenderermanager.setRenderShadow(false);
        MultiBufferSource.BufferSource irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderermanager.render(p_228187_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, graphics.pose(), irendertypebuffer$impl, 15728880);
        });
        irendertypebuffer$impl.endBatch();
        p_228187_5_.yBodyRot = f2;
        p_228187_5_.setYRot(f3);
        p_228187_5_.setXRot(f4);
        p_228187_5_.yHeadRotO = f5;
        p_228187_5_.yHeadRot = f6;
        graphics.pose().popPose();
        Lighting.setupFor3DItems();
    }

    public void renderSelectionGui(GuiGraphics graphics) {
//        graphics.fillGradient(this.width / 4 - 40, this.height / 2 - 60, this.width / 4 + 60, this.height / 2 + 60, 0xAA000000, 0xAA000000);
        drawBorderedGradientRect(graphics, this.width / 4 - 40, this.height / 2 - 60, this.width / 4 + 60, this.height / 2 + 60, 0xAA000000, 0xAA000000, 0xAAFFFFFF);

        int MIN_Y = this.height / 2 - 50;
        int MAX_Y = this.height / 2 + 50;

        for(int i = 0; i < races.size(); i++) {
            int y = this.height / 2 + 14 * i - this.currentRace * 7 - 20;
            if(y <= MIN_Y || y >= MAX_Y)
                continue;

            String raceName = races.get(i).getName();
            if (raceName.length() >= 14) {
                raceName = raceName.substring(0, 8) + "..";
            }
            graphics.drawString(font, raceName, this.width / 4 - 20, y, i == this.currentRace ? 0x00FFFFFF : 0x00C0C0C0);
        }
    }

    private void drawBorderedGradientRect(GuiGraphics graphics, int startX, int startY, int endX, int endY, int colorStart, int colorEnd, int borderColor) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        // Draw background
        graphics.fillGradient(startX, startY, endX, endY, colorStart, colorEnd);
        // Draw borders
        graphics.fill(startX, startY, endX, startY+1, borderColor); // top
        graphics.fill(startX, endY-1, endX, endY, borderColor); // bottom
        graphics.fill(startX, startY+1, startX+1, endY-1, borderColor); // left
        graphics.fill(endX-1, startY+1, endX, endY-1, borderColor); // right
        poseStack.popPose();
    }
}