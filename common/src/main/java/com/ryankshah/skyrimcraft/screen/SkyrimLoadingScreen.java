package com.ryankshah.skyrimcraft.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import com.ryankshah.skyrimcraft.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkyrimLoadingScreen extends Screen
{
    protected static final ResourceLocation SKILL_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/skill_icons.png");
    protected static final ResourceLocation SMOKE_EFFECT_PARTICLE = ResourceLocation.withDefaultNamespace("textures/particle/generic_6.png");
    private static final Item[] ROTATING_ITEMS = {Items.DIAMOND_SWORD, Items.ELYTRA, Items.ENCHANTED_BOOK, ItemRegistry.SWEET_ROLL.get(), ItemRegistry.DAEDRA_HEART.get(), ItemRegistry.CANDLELIGHT_SPELLBOOK.get(), ItemRegistry.AHZIDALS_RING_OF_ARCANA.get(), ItemRegistry.DAEDRIC_WAR_AXE.get(), ItemRegistry.DAEDRIC_GREATSWORD.get(), ItemRegistry.DAEDRIC_CHESTPLATE.get(), ItemRegistry.ORCISH_CHESTPLATE.get(), ItemRegistry.GLASS_BATTLEAXE.get(), ItemRegistry.GLASS_CHESTPLATE.get()};
    private static final String[] MINECRAFT_FACTS = {
            "Creepers were originally failed pig models.",
            "The Enderman's sound is a reversed, pitch-shifted 'here' sound.",
            "Minecraft's original name was 'Cave Game'.",
            "The world of Minecraft is larger than the surface of the Earth.",
            "The Wither creates explosions that can destroy obsidian. These are the only explosions in the game possible of doing so.",
            "Parrots die if you feed them cookies.",
            "Snow Golems do damage to Blazes, but they take damage while in the Nether, so bring some Fire Resistance potions!",
            "Ocelots used to turn into cats when you tamed them. Now, you only gain their trust and cats are an entirely separate mob, found in villages.",
            "Herobrine is an urban legend in the Minecraft community, depicting a mysterious figure resembling Steve with a blank face.",
            "There is a rare sound file called 'cave sound' that was never implemented, and is a haunting sound for cave atmospheres.",
            "Notch confirmed that the Ender Dragon is the only female mob, who was named Jen.",
            "An Evoker will change the color of a Blue sheep into a red!"
    };
    private static final EntityType<?>[] ROTATING_MOBS = {
            EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER,
            EntityType.VILLAGER, EntityType.IRON_GOLEM, EntityType.BLAZE, EntityType.GHAST,
            EntityType.ENDER_DRAGON
    };
    private boolean renderItem;
    private EntityType<?> selectedMobType;

    private final Random random = new Random();
    private Item rotatingItem;
    private List<String> wrappedFact;
    private float rotation = 0;

    // Fade transition variables
    private static final float FADE_DURATION = 1.0f;
    private float fadeTimer = 0;
    private boolean isFadingIn = true;
    private boolean isFadingOut = false;
    private boolean hasSelectedFact = false;
    private Character character;
    private float characterProgress;
    private float characterProgressBarWidth;
    private List<SmokeParticle> smokeParticles = new ArrayList<>();
    private static final float SCREEN_LIFETIME = 8.0f; // 8 seconds lifetime
    private float screenTimer = 0;
    private boolean isClosing = false;

    public SkyrimLoadingScreen() {
        super(Component.literal("Loading..."));
    }

    private double getXpNeededForNextCharacterLevel(int level) {
        return 12.5*Math.pow(level+1, 2) + 62.5*level - 75;
    }

//    private void selectRandomItemAndFact() {
//        rotatingItem = ROTATING_ITEMS[random.nextInt(ROTATING_ITEMS.length)];
//        String currentFact = MINECRAFT_FACTS[random.nextInt(MINECRAFT_FACTS.length)];
//        wrappedFact = wrapText(currentFact, 100); // Adjust the width as needed
//    }

    private void selectRandomItemAndFact() {
        renderItem = random.nextBoolean();
        if (renderItem) {
            rotatingItem = ROTATING_ITEMS[random.nextInt(ROTATING_ITEMS.length)];
            System.out.println("Selected item: " + rotatingItem);
        } else {
            selectedMobType = ROTATING_MOBS[random.nextInt(ROTATING_MOBS.length)];
            System.out.println("Selected mob: " + selectedMobType.getDescriptionId());
        }
        String currentFact = MINECRAFT_FACTS[random.nextInt(MINECRAFT_FACTS.length)];
        wrappedFact = wrapText(currentFact, 100);
    }

    @Override
    public void tick() {
        super.tick();

        if (!isClosing) {
            screenTimer += 1f / 20; // Assuming 20 ticks per second
            if (screenTimer >= SCREEN_LIFETIME) {
                startFadeOut();
                isClosing = true;
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if(!hasSelectedFact) {
            selectRandomItemAndFact();
            this.character = Services.PLATFORM.getCharacter(minecraft.player);
            this.characterProgress = character.getCharacterTotalXp() / (float)getXpNeededForNextCharacterLevel(character.getCharacterLevel()+1);
            this.characterProgressBarWidth = 60 * characterProgress;
            hasSelectedFact = true;
        }

        renderBackground(graphics);

        // Update smoke particles
        updateSmokeParticles();

        // Make sure smoke is rendered first so it doesn't interfere with item rendering.
        renderSmokeParticles(graphics);

        // Reset any previous OpenGL states, shader color settings, or blending modes that might affect the item rendering.
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();

        // Render the rotating item
//        renderRotatingItem(graphics);

        // Render the rotating item or mob
        renderEntityOrItem(graphics);

        // Continue with other render methods
        renderFact(graphics);
        renderLevel(graphics);

        RenderSystem.disableBlend();

        rotation += 0.5f;

        if (isFadingOut) {
            fadeTimer += partialTick / 20;
            if (fadeTimer >= FADE_DURATION) {
                fadeTimer = FADE_DURATION;
                minecraft.setScreen(null); // Close the screen
            }
        } else if (isFadingIn) {
            fadeTimer += partialTick / 20;
            if (fadeTimer >= FADE_DURATION) {
                fadeTimer = FADE_DURATION;
                isFadingIn = false;
            }
        }
    }

    private float calculateAlpha() {
        if (isFadingIn) {
            return fadeTimer / FADE_DURATION;
        } else if (isFadingOut) {
            return 1 - (fadeTimer / FADE_DURATION);
        }
        return 1.0f;
    }

    private void renderBackground(GuiGraphics graphics) {
        graphics.fill(0, 0, this.width, this.height, 0xFF111111);
    }

    private void renderEntityOrItem(GuiGraphics graphics) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();

        // Translate to the desired position on the screen
        poseStack.translate(this.width / 4f, this.height / 2f, 100);

        // Reduce the scale by half for both items and entities
        float scale = renderItem ? 64f : 128f; // Changed from 128f to 64f
        poseStack.scale(scale, scale, scale);

        // Apply rotation for both items and entities
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

        if (renderItem) {
            System.out.println("Rendering item: " + rotatingItem);
            try {
                // Rotate the item/entity
//                poseStack.mulPose(Axis.XP.rotationDegrees(180));
//                poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

                minecraft.getItemRenderer().renderStatic(
                        rotatingItem.getDefaultInstance(),
                        ItemDisplayContext.GUI,
                        15728880,
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        graphics.bufferSource(),
                        minecraft.level,
                        0
                );
            } catch (Exception e) {
                System.err.println("Error rendering item: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Rotate the entity 180 degrees around the Y-axis to face forward
//            poseStack.mulPose(Axis.YP.rotationDegrees(180));
//            // Apply the spin rotation in the opposite direction
//            poseStack.mulPose(Axis.YP.rotationDegrees(-rotation));

            System.out.println("Rendering entity: " + selectedMobType.getDescriptionId());
            try {
                Entity entity = selectedMobType.create(minecraft.level);
                if (entity instanceof LivingEntity livingEntity) {
                    EntityRenderDispatcher entityRenderer = minecraft.getEntityRenderDispatcher();
                    entityRenderer.setRenderShadow(false);

                    // Calculate the entity's height and adjust its position
                    float entityHeight = livingEntity.getBbHeight();
                    float yOffset = entityHeight / 2;

                    poseStack.translate(0, -0.5f, 0);

                    // Scale down larger entities more
                    float entityScale = 1f / Math.max(1f, entityHeight);
                    poseStack.scale(entityScale, entityScale, entityScale);

                    livingEntity.setYRot(180F);
                    livingEntity.yRotO = 180F;
                    livingEntity.yBodyRot = 180F;
                    livingEntity.yHeadRot = 180F;
                    livingEntity.yHeadRotO = 180F;

                    entityRenderer.render(livingEntity, 0, 0, 0, 0, 1, poseStack, graphics.bufferSource(), 15728880);
                } else {
                    System.err.println("Created entity is not a LivingEntity");
                }
            } catch (Exception e) {
                System.err.println("Error rendering entity: " + e.getMessage());
                e.printStackTrace();
            }
        }

        poseStack.popPose();
    }

    public static void renderEntityOrItem(GuiGraphics graphics, int x, int y, int size, float yaw, float pitch, Object toRender) {
        graphics.pose().pushPose();
        graphics.pose().translate((float)x, (float)y, 300F);
        graphics.pose().scale(1.0F, 1.0F, -1.0F);
        graphics.pose().translate(0.0D, 0.0D, 1000.0D);
        graphics.pose().scale((float)size, (float)size, (float)size);

        Quaternionf rotation = Axis.ZP.rotationDegrees(180.0F)
                .mul(Axis.XP.rotationDegrees(pitch))
                .mul(Axis.YP.rotationDegrees(yaw));
        graphics.pose().mulPose(rotation);

        if (toRender instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) toRender;
            float originalBodyYaw = entity.yBodyRot;
            float originalYaw = entity.getYRot();
            float originalPitch = entity.getXRot();
            float originalHeadYawO = entity.yHeadRotO;
            float originalHeadYaw = entity.yHeadRot;

            entity.yBodyRot = 180.0F + yaw * 20.0F;
            entity.setYRot(180.0F + yaw * 20.0F);
            entity.setXRot(-pitch * 20.0F);
            entity.yHeadRot = entity.getYRot();
            entity.yHeadRotO = entity.getYRot();

            Lighting.setupForFlatItems();
            EntityRenderDispatcher entityRenderer = Minecraft.getInstance().getEntityRenderDispatcher();
            entityRenderer.setRenderShadow(false);
            MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

            RenderSystem.runAsFancy(() -> {
                entityRenderer.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, graphics.pose(), bufferSource, 15728880);
            });

            bufferSource.endBatch();

            // Restore original entity state
            entity.yBodyRot = originalBodyYaw;
            entity.setYRot(originalYaw);
            entity.setXRot(originalPitch);
            entity.yHeadRotO = originalHeadYawO;
            entity.yHeadRot = originalHeadYaw;
        } else if (toRender instanceof Item) {
            Item item = (Item) toRender;
            BakedModel itemModel = Minecraft.getInstance().getItemRenderer().getModel(item.getDefaultInstance(), null, null, 0);
            graphics.pose().translate(-0.5F, -0.5F, -0.5F);
            Minecraft.getInstance().getItemRenderer().render(item.getDefaultInstance(), ItemDisplayContext.FIXED, false, graphics.pose(), Minecraft.getInstance().renderBuffers().bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, itemModel);
        }

        graphics.pose().popPose();
        Lighting.setupFor3DItems();
    }

    private void renderRotatingItem(GuiGraphics graphics) {
        PoseStack poseStack = graphics.pose();

        // Push pose stack to ensure item rendering transforms are isolated
        poseStack.pushPose();

        // Translate to the location where the item should be rendered
        poseStack.translate(this.width / 4f, this.height / 2f, 100);

        // Scale the item for rendering (adjust scale if needed)
        poseStack.scale(128, 128, 128);

        // Rotate the item
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

        // Render the item using the item renderer
        minecraft.getItemRenderer().renderStatic(
                rotatingItem.getDefaultInstance(),
                ItemDisplayContext.GUI,
                15728880,
                15728880,
                poseStack,
                minecraft.renderBuffers().bufferSource(),
                minecraft.level,
                0
        );

        // Pop the pose stack to revert transformations
        poseStack.popPose();
    }


    private void renderLevel(GuiGraphics graphics) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        RenderUtil.bind(SKILL_ICONS);
        RenderUtil.blitWithBlend(poseStack, (width * 2/3) - 20, 13, 109, 228, 80, 8, 512, 512, 1, 1);
        RenderUtil.blitWithBlend(poseStack, (width * 2/3) - 10, 15, 119 + ((60 - characterProgressBarWidth) / 2.0f), 236, (int)(60 * characterProgress), 6, 512, 512, 1, 1);
        poseStack.popPose();
        String level = "LEVEL";
        drawScaledString(graphics, level, (width * 2/3) - 55, 15, 0xFF949494, 0.75f);
        graphics.drawString(font, ""+character.getCharacterLevel(), (width * 2/3) - 30, 13, 0xFFFFFFFF);
    }

    private void renderFact(GuiGraphics graphics) {
        int startX = this.width / 2 + 50;
        int startY = (2 * this.height / 3) - (wrappedFact.size() * font.lineHeight) / 2;

        for (int i = 0; i < wrappedFact.size(); i++) {
            graphics.drawString(this.font, wrappedFact.get(i),
                    startX, startY + i * font.lineHeight, 0xFFFFFFFF);
        }
    }

    private List<String> wrapText(String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split("\\s+");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (font.width(currentLine + " " + word) <= maxWidth) {
                if (currentLine.length() > 0) currentLine.append(" ");
                currentLine.append(word);
            } else {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
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

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void startFadeOut() {
        isFadingOut = true;
        isFadingIn = false;
        fadeTimer = 0;
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    private void updateSmokeParticles() {
        // Add new smoke particles at random positions along the bottom of the screen
        if (smokeParticles.size() < 100) {  // Limit the number of particles
            float randomX = random.nextFloat() * this.width;  // Random X position across the entire width
            smokeParticles.add(new SmokeParticle(randomX, this.height));
        }

        // Update existing smoke particles
        for (int i = smokeParticles.size() - 1; i >= 0; i--) {
            SmokeParticle particle = smokeParticles.get(i);
            particle.update();
            if (particle.isDead()) {
                smokeParticles.remove(i);
            }
        }
    }

    private void renderSmokeParticles(GuiGraphics graphics) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();  // Save the current state

        for (SmokeParticle particle : smokeParticles) {
            poseStack.pushPose();  // Save the state for each particle

            // Translate to the particle's position and apply rotation
            poseStack.translate(particle.x, particle.y, 0);
            poseStack.mulPose(Axis.ZP.rotationDegrees(particle.rotation));

            int halfSize = (int) particle.size / 2;

            // Set the particle color to a grayscale value with opacity
            RenderSystem.setShaderColor(particle.grayValue, particle.grayValue, particle.grayValue, particle.opacity);

            // Render the particle as a rotated square
//            RenderUtil.bind(SMOKE_EFFECT_PARTICLE);
//            RenderUtil.blitWithBlend(poseStack, -halfSize, -halfSize, 0, 0, 8, 8,8,8,0, 1);
            graphics.fill(-halfSize, -halfSize, halfSize, halfSize, 0xFFFFFFFF);

            poseStack.popPose();  // Restore the state after rendering the particle
        }

        poseStack.popPose();  // Restore the original state
    }

    private class SmokeParticle {
        public float x, y;
        public float velocityX, velocityY;
        public float opacity;
        public float lifespan;
        public float size;
        public float rotation;
        public float rotationSpeed;
        public float grayValue;  // Grayscale value for the particle (between 0.3 and 0.9)

        public SmokeParticle(float x, float y) {
            this.x = x;
            this.y = y;
            this.size = random.nextFloat() * 4 + 2; //8
            this.velocityX = random.nextFloat() * 0.2f - 0.1f;  // Horizontal drift
            this.velocityY = random.nextFloat() * 0.3f + 0.2f;  // Rising speed
            this.opacity = 1.0f;
            this.lifespan = random.nextFloat() * 50 + 100;
            this.rotation = random.nextFloat() * 360;
            this.rotationSpeed = random.nextFloat() * 2 - 1;

            // Assign a random grayscale value (0.3 to 0.9 to avoid pure black or white)
            this.grayValue = random.nextFloat() * 0.6f + 0.3f;
        }

        public void update() {
            this.x += velocityX;
            this.y -= velocityY;
            this.lifespan--;
            this.rotation += rotationSpeed;
            this.opacity = Math.max(0, lifespan / 100.0f);  // Fade out
            // Gradually increase the gray value as the particle fades (imitating dissipating smoke)
            this.grayValue = Math.min(0.9f, this.grayValue + 0.005f);
        }

        public boolean isDead() {
            return lifespan <= 0;
        }
    }
}