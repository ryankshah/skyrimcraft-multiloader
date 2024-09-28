package com.ryankshah.skyrimcraft.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.AlchemyTableBlock;
import com.ryankshah.skyrimcraft.block.ArcaneEnchanterBlock;
import com.ryankshah.skyrimcraft.block.BlacksmithForgeBlock;
import com.ryankshah.skyrimcraft.block.OvenBlock;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.magic.EmptySpell;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.entity.boss.dragon.SkyrimDragon;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import com.ryankshah.skyrimcraft.util.LevelUpdate;
import com.ryankshah.skyrimcraft.util.RenderUtil;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.glfwGetKeyName;

public class SkyrimGuiOverlay
{
    public static final int PLAYER_BAR_MAX_WIDTH = 78;
    public static List<LevelUpdate> LEVEL_UPDATES = new ArrayList<>();

    private static float shoutChargeProgress = 0.0f;
    private static boolean showShoutBar = false; // Flag to control visibility
    private static int spellLocation = 0; // either 0 or 1

    // Method to update the shout charge progress from InputEvents
    public static void setShoutChargeProgress(float progress) {
        shoutChargeProgress = progress;
    }

    public static void setShoutLocation(int loc) { spellLocation = loc; }

    // Method to control when to show the shout charge bar
    public static void setShowShoutBar(boolean show) {
        showShoutBar = show;
    }

    public static class SkyrimLevelUpdates implements LayeredDraw.Layer
    {
        private final ResourceLocation OVERLAY_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/overlay_icons.png");

        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker partialTick) {
            PoseStack poseStack = guiGraphics.pose();
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow();
            int scaledWidth = window.getGuiScaledWidth();
            int scaledHeight = window.getGuiScaledHeight();

            if(!LEVEL_UPDATES.isEmpty()) {
                LevelUpdate levelUpdate = LEVEL_UPDATES.get(0);

                if(levelUpdate.getLevelUpRenderTime() <= 0)
                    LEVEL_UPDATES.remove(levelUpdate);

                if(levelUpdate.getUpdateName().equalsIgnoreCase("characterLevel"))
                    renderCharacterLevelUpdate(guiGraphics, poseStack, mc.font, scaledWidth, scaledHeight, partialTick, levelUpdate.getLevel(), levelUpdate.getLevelUpRenderTime());
                else
                    renderLevelUpdate(guiGraphics, poseStack, mc.font, mc.player, scaledWidth, scaledHeight, partialTick.getGameTimeDeltaTicks(), levelUpdate.getUpdateName(), levelUpdate.getLevel(), levelUpdate.getLevelUpRenderTime());

                levelUpdate.setLevelUpRenderTime(levelUpdate.getLevelUpRenderTime() - 1);
            }
        }

        private void renderCharacterLevelUpdate(GuiGraphics graphics, PoseStack poseStack, Font fontRenderer, int width, int height, DeltaTracker partialTicks, int level, int levelUpRenderTime) {
            String characterLevel = ""+level;
            String levelProgressString = "Progress";

            float hue = (float)levelUpRenderTime - partialTicks.getGameTimeDeltaTicks();
            int opacity = (int)(hue * 255.0F / 20.0F);
            if (opacity > 255) opacity = 255;

            poseStack.pushPose();
            //RenderSystem.translatef((float)(width / 2), (float)(height - 68), 0.0F);
//            RenderSystem.enableAlphaTest();
//            RenderSystem.defaultAlphaFunc();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
//            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.clearColor(1F, 1F, 1f, opacity / 255f);

            RenderUtil.bind(OVERLAY_ICONS);
            RenderUtil.blitWithBlend(poseStack, width / 2 - 51, height / 2 - 30, 0, 51, 102, 10, 256, 256, 10, 1);
            RenderUtil.blitWithBlend(poseStack, width / 2 - 39, height / 2 - 28, 96, 64, 78, 6, 256, 256, 10, 1);

            if (opacity > 8) {
                graphics.drawCenteredString(fontRenderer, "Level Up".toUpperCase(), width / 2, height / 2 - 45, 0x00FFFFFF | (opacity << 24));
                graphics.drawString(fontRenderer, levelProgressString, width / 2 - 60 - fontRenderer.width(levelProgressString), height / 2 - 30, 0x00FFFFFF | (opacity << 24));
                graphics.drawString(fontRenderer, characterLevel, width / 2 + 60 + 8 - fontRenderer.width(characterLevel), height / 2 - 30, 0x00FFFFFF | (opacity << 24));
            }

            RenderSystem.disableDepthTest();
            RenderSystem.disableBlend();
//            RenderSystem.disableAlphaTest();
            poseStack.popPose();
        }

        private double getXpNeededForNextCharacterLevel(int level) {
            return 12.5*Math.pow(level+1, 2) + 62.5*level - 75;
        }

        private void renderLevelUpdate(GuiGraphics graphics, PoseStack poseStack, Font fontRenderer, Player player, int width, int height, float elapsed, String updateName, int level, int levelUpRenderTime) {
            Character character = Character.get(player);
            String nextCharacterLevel = ""+(character.getCharacterLevel()+1);
            float characterProgress = character.getCharacterTotalXp() / (float)getXpNeededForNextCharacterLevel(character.getCharacterLevel()+1);
            float characterProgressBarWidth = PLAYER_BAR_MAX_WIDTH * characterProgress;
            String levelProgressString = "Progress"; //"Level Progress";

            float hue = (float)levelUpRenderTime - elapsed;
            int opacity = (int)(hue * 255.0F / 20.0F);
            if (opacity > 255) opacity = 255;

            poseStack.pushPose();
//            RenderSystem.translatef((float)(width / 2), (float)(height - 68), 0.0F);
//            RenderSystem.enableAlphaTest();
//            RenderSystem.defaultAlphaFunc();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
//            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.clearColor(1F, 1F, 1f, opacity / 255f);

//            mc.getTextureManager().bind(OVERLAY_ICONS);
            RenderUtil.bind(OVERLAY_ICONS);
            RenderUtil.blitWithBlend(poseStack, width / 2 - 51, height / 2 - 30, 0, 51, 102, 10, 256, 256, 10, opacity / 255f);
            RenderUtil.blitWithBlend(poseStack, width / 2 - 39, height / 2 - 28, 96 + ((PLAYER_BAR_MAX_WIDTH - characterProgressBarWidth) / 2.0f), 64, (int)(78 * characterProgress), 6, 256, 256, 11, opacity/255f);

            if (opacity > 8) {
                graphics.drawCenteredString(fontRenderer, (updateName + " Increased To " + level).toUpperCase(), width / 2, height / 2 - 45, 0x00FFFFFF | (opacity << 24));
                graphics.drawString(fontRenderer, levelProgressString, width / 2 - 60 - fontRenderer.width(levelProgressString), height / 2 - 30, 0x00FFFFFF | (opacity << 24));
                graphics.drawString(fontRenderer, nextCharacterLevel, width / 2 + 60 + 8 - fontRenderer.width(nextCharacterLevel), height / 2 - 30, 0x00FFFFFF | (opacity << 24));
            }

            RenderSystem.disableDepthTest();
            RenderSystem.disableBlend();
//            RenderSystem.disableAlphaTest();
            poseStack.popPose();
        }
    }

    public static class SkyrimTargetHealth implements LayeredDraw.Layer
    {
        private final ResourceLocation OVERLAY_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/overlay_icons.png");

        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker partialTick) {
            PoseStack poseStack = guiGraphics.pose();
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow();
            int scaledWidth = window.getGuiScaledWidth();
            int scaledHeight = window.getGuiScaledHeight();
            Character character = Character.get(mc.player);
            Entity currentTarget = mc.player.level().getEntity(character.getCurrentTarget());

            if(currentTarget instanceof LivingEntity && currentTarget.isAlive()) {
//            if(targets.contains(mc.player.getLastHurtMob().getId()) && mc.player.getLastHurtMob() != null && mc.player.getLastHurtMob().isAlive()) {
                LivingEntity target = (LivingEntity)currentTarget;
                String entityName = target instanceof Villager villager ? villager.getVillagerData().getProfession() == VillagerProfession.NONE ? "Villager" : StringUtils.capitalize(villager.getVillagerData().getProfession().toString()) : target.getDisplayName().getString();

                if(target instanceof SkyrimDragon && !mc.player.closerThan(target, 64.0D))
                    return;

                if(!mc.player.closerThan(target, 32.0D))
                    return;

                float healthPercentage = target.getHealth() / target.getMaxHealth();
                float healthBarWidth = 142 * healthPercentage;
                float healthBarStartX = (scaledWidth / 2 - 69) + (142 - healthBarWidth) / 2.0f;

                poseStack.pushPose();
                RenderUtil.bind(OVERLAY_ICONS);
                RenderUtil.blitWithBlend(poseStack, (scaledWidth / 2) - 78, 28, 3, 88, 156, 8, 256, 256, 3, 1);
                RenderUtil.blitWithBlend(poseStack, (int)healthBarStartX, 30, 10 + ((142 - healthBarWidth) / 2.0f), 101, (int)healthBarWidth, 3, 256, 256, 3, 1);

                int entityNameWidth = mc.font.width(entityName);
                // left banner
                RenderUtil.blitWithBlend(poseStack, (scaledWidth / 2) - 2 - (41 + entityNameWidth / 2), 38, 25, 107, 41, 12, 256, 256, 3, 1); // width / 2 - 69
                // right banner
                RenderUtil.blitWithBlend(poseStack, (scaledWidth / 2) + 2 + entityNameWidth / 2, 38, 84, 107, 41, 12, 256, 256, 3, 1); // width / 2 + 28
                guiGraphics.drawCenteredString(mc.font, entityName, scaledWidth / 2, 40, 0x00C0C0C0);
                poseStack.popPose();
            }
        }
    }

    public static class SkyrimCrosshair implements LayeredDraw.Layer
    {
        private final ResourceLocation OVERLAY_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/overlay_icons.png");
//        protected static final ResourceLocation CROSSHAIR_SPRITE = new ResourceLocation("hud/crosshair");
        protected static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_FULL_SPRITE = ResourceLocation.withDefaultNamespace("hud/crosshair_attack_indicator_full");
        protected static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("hud/crosshair_attack_indicator_background");
        protected static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("hud/crosshair_attack_indicator_progress");

        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker partialTick) {
            PoseStack poseStack = guiGraphics.pose();
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow();
            int scaledWidth = window.getGuiScaledWidth();
            int scaledHeight = window.getGuiScaledHeight();

            if(mc.hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockHitResult)mc.hitResult).getBlockPos();
                BlockState blockstate = mc.level.getBlockState(blockpos);
                if (blockstate.isAir()) {
                    return;
                }
                Block block = blockstate.getBlock();
                if(block.builtInRegistryHolder().is(BlockTags.WOODEN_DOORS)
                        || block.builtInRegistryHolder().is(BlockTags.WOODEN_TRAPDOORS)
                        || block.builtInRegistryHolder().is(BlockTags.SHULKER_BOXES)
                        || block.builtInRegistryHolder().is(BlockTags.ANVIL)
                        || block.builtInRegistryHolder().is(BlockTags.BUTTONS)
                        || block.builtInRegistryHolder().is(BlockTags.BEDS)
                        || block.builtInRegistryHolder().is(BlockTags.FENCE_GATES)
                        || block instanceof AbstractFurnaceBlock
                        || block instanceof AbstractChestBlock<?>
                        || block instanceof CartographyTableBlock
                        || block instanceof CraftingTableBlock
                        || block instanceof EnchantingTableBlock
                        || block instanceof GrindstoneBlock
                        || block instanceof LoomBlock
                        || block instanceof BrewingStandBlock
                        || block instanceof LeverBlock
                        || block instanceof RepeaterBlock
                        || block instanceof HopperBlock
                        || block instanceof BlacksmithForgeBlock
                        || block instanceof OvenBlock
                        || block instanceof AlchemyTableBlock
                        || block instanceof ArcaneEnchanterBlock
                ) {
                    int blockNameWidth = mc.font.width(block.getName());
                    poseStack.pushPose();
                    RenderUtil.bind(OVERLAY_ICONS);
                    RenderUtil.blitWithBlend(poseStack, (scaledWidth / 2) - (blockNameWidth / 2) - 10, scaledHeight / 2 + 4, 185, 103, 8, 16, 256, 256, 3, 1);
                    poseStack.popPose();
                    guiGraphics.drawString(mc.font, block.getName(), (scaledWidth / 2) - (blockNameWidth / 2) + 8, scaledHeight / 2 + 10, 0x00FFFFFF);
                }
            }

            if(mc.crosshairPickEntity instanceof LivingEntity && mc.player.isCrouching()) {
                LivingEntity entity = (LivingEntity) mc.crosshairPickEntity;
                if(entity.getTags().contains(EntityRegistry.PICKPOCKET_TAG)) {
                    guiGraphics.drawCenteredString(mc.font, "(" + glfwGetKeyName(KeysRegistry.PICKPOCKET_KEY.get().getDefaultKey().getValue(), 0).toUpperCase() + ") Pickpocket", scaledWidth / 2, scaledHeight / 2 + 8, 0x00FFFFFF);
                }
            }

            if(mc.player.getMainHandItem().getItem() instanceof ProjectileWeaponItem
                    && !mc.player.isCreative() && !mc.player.isSpectator()
                    && !mc.player.isUnderWater() && !(mc.player.getAirSupply() < 300)) {
                int top = scaledHeight - 48;
                if(mc.player.getArmorValue() > 0) top = scaledHeight - 60;
                ItemStack itemstack = mc.player.getProjectile(mc.player.getMainHandItem());
                if(itemstack != ItemStack.EMPTY) {
                    Component nameAndCount = Component.translatable(itemstack.getHoverName().copy().append("(" + itemstack.getCount() + ")").getString());
                    guiGraphics.drawString(mc.font, nameAndCount, scaledWidth - 18 - mc.font.width(nameAndCount), top, 0x00FFFFFF);
                }
            }

            int texX = 166;
            int texY = 88;
            if(!mc.player.isSpectator()) { // mc.player.getMainHandItem().getItem() instanceof ShootableItem && // --> Player does not need bow for this iirc
                if (mc.player.isCrouching()) {
                    texX += 15;

                    if(!mc.player.canBeSeenAsEnemy()) {
                        texX += 15;
                    }
                }
            }
            poseStack.pushPose();
            RenderUtil.bind(OVERLAY_ICONS);
            RenderUtil.blitWithBlend(poseStack, (scaledWidth - 16) / 2, (scaledHeight - 16) / 2, texX, texY, 15, 15, 256, 256, 3, 1);
            poseStack.popPose();

            if (mc.options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
                float f = mc.player.getAttackStrengthScale(0.0F);
                boolean flag = false;
                if (mc.crosshairPickEntity != null && mc.crosshairPickEntity instanceof LivingEntity && f >= 1.0F) {
                    flag = mc.player.getCurrentItemAttackStrengthDelay() > 5.0F;
                    flag = flag & mc.crosshairPickEntity.isAlive();
                }

                int j = scaledHeight / 2 - 7 + 16;
                int k = scaledWidth / 2 - 8;
//                mc.textureManager.bind(AbstractGui.GUI_ICONS_LOCATION);
                poseStack.pushPose();
                if (flag) {
                    guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_FULL_SPRITE, k, j, 16, 16);
                } else if (f < 1.0F) {
                    int l = (int)(f * 17.0F);
                    guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE, k, j, 16, 4);
                    guiGraphics.blitSprite(CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE, 16, 4, 0, 0, k, j, l, 4);
                }
//                if (flag) {
//                    RenderUtil.blitWithBlend(poseStack, k, j, 68, 94, 16, 16, 256, 256, 4, 1);
//                } else if (f < 1.0F) {
//                    int l = (int)(f * 17.0F);
//                    RenderUtil.blitWithBlend(poseStack, k, j, 36, 94, 16, 4, 256, 256, 4, 1);
//                    RenderUtil.blitWithBlend(poseStack, k, j, 52, 94, l, 4, 256, 256, 4, 1);
//                }
                poseStack.popPose();
            }
        }
    }

    public static class SkyrimSpells implements LayeredDraw.Layer
    {
        private final ResourceLocation OVERLAY_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/overlay_icons.png");
        private int SLOT_WIDTH = 22, SLOT_HEIGHT = 22;
        private int DOUBLE_SLOT_WIDTH = 22, DOUBLE_SLOT_HEIGHT = 41;
        private int ICON_WIDTH = 16, ICON_HEIGHT = 16;

        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker partialTick) {
            PoseStack poseStack = guiGraphics.pose();
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow();
            int scaledWidth = window.getGuiScaledWidth();
            int scaledHeight = window.getGuiScaledHeight();

            Character character = Character.get(mc.player);
            
            Spell selectedSpell1 = character.getSelectedSpell1();
            Spell selectedSpell2 = character.getSelectedSpell2();
            Map<Spell, Float> spellCooldowns = character.getSpellsOnCooldown();

            poseStack.pushPose();
            RenderUtil.bind(OVERLAY_ICONS);
            if(!(selectedSpell1 instanceof EmptySpell) && (selectedSpell2 instanceof EmptySpell || selectedSpell2 == null)) {
                RenderUtil.blitWithBlend(poseStack, scaledWidth - SLOT_WIDTH, (scaledHeight / 2) - (DOUBLE_SLOT_HEIGHT / 2), 234, 83, SLOT_WIDTH, SLOT_HEIGHT, 256, 256, 4, 1);
                poseStack.pushPose();
                RenderUtil.bind(selectedSpell1.getIcon());
                RenderUtil.blitWithBlend(poseStack, scaledWidth - SLOT_WIDTH + 3, (scaledHeight / 2) - (DOUBLE_SLOT_HEIGHT / 2) + 3, 0, 0, 16, 16, ICON_WIDTH, ICON_HEIGHT, 5, 1);
                if(spellCooldowns.containsKey(selectedSpell1)) {
                    float cooldown = spellCooldowns.get(selectedSpell1);
                    if (cooldown> 0) {
                        poseStack.pushPose();
                        RenderUtil.bind(OVERLAY_ICONS);
                        float maxCooldown = selectedSpell1.getCooldown();
                        float offset = -1 * Mth.floor(cooldown/maxCooldown * 8);
                        RenderUtil.blitWithBlend(poseStack, scaledWidth - SLOT_WIDTH + 3, (scaledHeight / 2) - (DOUBLE_SLOT_HEIGHT / 2) + 3, 230 + (16 * offset), 148, ICON_WIDTH, ICON_HEIGHT, 256, 256, 6, 1);
                        poseStack.popPose();
                    }
                }
                poseStack.popPose();
            } else if((selectedSpell1 instanceof EmptySpell || selectedSpell1 == null) && !(selectedSpell2 instanceof EmptySpell)) {
                RenderUtil.blitWithBlend(poseStack, scaledWidth - SLOT_WIDTH, (scaledHeight / 2) - (DOUBLE_SLOT_HEIGHT / 2) + 20, 234, 83, SLOT_WIDTH, SLOT_HEIGHT, 256, 256, 4, 1);
                poseStack.pushPose();
                RenderUtil.bind(selectedSpell2.getIcon());
                RenderUtil.blitWithBlend(poseStack, scaledWidth - SLOT_WIDTH + 3, (scaledHeight / 2) - (DOUBLE_SLOT_HEIGHT / 2) + 3 + 20, 0, 0, 16, 16, 16, 16, 5, 1);
                if(spellCooldowns.containsKey(selectedSpell2)) {
                    float cooldown = spellCooldowns.get(selectedSpell2);
                    if (cooldown > 0) {
                        poseStack.pushPose();
                        RenderUtil.bind(OVERLAY_ICONS);
                        float maxCooldown = selectedSpell2.getCooldown();
                        float offset = -1 * Mth.floor(cooldown/maxCooldown * 8);
                        RenderUtil.blitWithBlend(poseStack, scaledWidth - SLOT_WIDTH + 3, (scaledHeight / 2) - (DOUBLE_SLOT_HEIGHT / 2) + 3 + 20, 230 + (16 * offset), 148, ICON_WIDTH, ICON_HEIGHT, 256, 256, 6, 1);
                        poseStack.popPose();
                    }
                }
                poseStack.popPose();
            } else if (!(selectedSpell1 instanceof EmptySpell) && !(selectedSpell2 instanceof EmptySpell)) {
                RenderUtil.blitWithBlend(poseStack, scaledWidth - DOUBLE_SLOT_WIDTH, (scaledHeight / 2) - (DOUBLE_SLOT_HEIGHT / 2), 234, 106, DOUBLE_SLOT_WIDTH, DOUBLE_SLOT_HEIGHT, 256, 256, 4, 1);

                poseStack.pushPose();
                RenderUtil.bind(selectedSpell1.getIcon());
                RenderUtil.blitWithBlend(poseStack, scaledWidth - SLOT_WIDTH + 3, (scaledHeight / 2) - (DOUBLE_SLOT_HEIGHT / 2) + 3, 0, 0, 16, 16, 16, 16, 5, 1);
                if(spellCooldowns.containsKey(selectedSpell1)) {
                    float cooldown = spellCooldowns.get(selectedSpell1);
                    if (cooldown > 0) {
                        poseStack.pushPose();
                        RenderUtil.bind(OVERLAY_ICONS);
                        float maxCooldown = selectedSpell1.getCooldown();
                        float offset = -1 * Mth.floor(cooldown/maxCooldown * 8);
                        RenderUtil.blitWithBlend(poseStack, scaledWidth - SLOT_WIDTH + 3, (scaledHeight / 2) - (DOUBLE_SLOT_HEIGHT / 2) + 3, 230 + (16 * offset), 148, ICON_WIDTH, ICON_HEIGHT, 256, 256, 6, 1);
                        poseStack.popPose();
                    }
                }
                poseStack.popPose();

                poseStack.pushPose();
                RenderUtil.bind(selectedSpell2.getIcon());
                RenderUtil.blitWithBlend(poseStack, scaledWidth - SLOT_WIDTH + 3, (scaledHeight / 2) - (DOUBLE_SLOT_HEIGHT / 2) + 3 + 20, 0, 0, 16, 16, 16, 16, 5, 1);
                if(spellCooldowns.containsKey(selectedSpell2)) {
                    float cooldown = spellCooldowns.get(selectedSpell2);
                    if (cooldown > 0) {
                        poseStack.pushPose();
                        RenderUtil.bind(OVERLAY_ICONS);
                        float maxCooldown = selectedSpell2.getCooldown();
                        float offset = -1 * Mth.floor(cooldown/maxCooldown * 8);
                        RenderUtil.blitWithBlend(poseStack, scaledWidth - SLOT_WIDTH + 3, (scaledHeight / 2) - (DOUBLE_SLOT_HEIGHT / 2) + 3 + 20, 230 + (16 * offset), 148, ICON_WIDTH, ICON_HEIGHT, 256, 256, 6, 1);
                        poseStack.popPose();
                    }
                }
                poseStack.popPose();
            }
            poseStack.popPose();

            if(showShoutBar) {
                int barWidth = 144;
                int barHeight = 10;
                int barX = (scaledWidth / 2) - (barWidth / 2);
                int barY = scaledHeight - 70;

                poseStack.pushPose();
                RenderUtil.bind(OVERLAY_ICONS);
                RenderUtil.blitWithBlend(poseStack, barX, barY, 0, 179, barWidth, barHeight, 256, 256, 6, 1);
                RenderUtil.blitWithBlend(poseStack, barX + 12, barY + 2, 12, 190, (int) (120 * shoutChargeProgress), 6, 256, 256, 6, 1);
                poseStack.popPose();

                // Optional: render text to show the percentage
//                String progressText = String.format("Charging: %.0f%%", shoutChargeProgress * 100);
//                guiGraphics.drawString(mc.font, progressText, barX - (mc.font.width(progressText) / 2), barY - 10, 0xFFFFFF);

                // Render the shout words based on progress
                String shoutName = spellLocation == 1 ? character.getSelectedSpell1().getShoutName() : character.getSelectedSpell2().getShoutName();
                guiGraphics.drawCenteredString(mc.font, shoutName, scaledWidth / 2, barY - 20, 0xFFFFFFFF);
//                String[] shoutWords = shoutName.split(" ");
//                // Calculate percentages for each word's appearance
//                float[] wordThresholds = {0.25f, 0.50f, 0.75f}; // Progress for the first, second, and third word
//                int wordSpacing = 10;  // Space between words
//                int wordX = (scaledWidth / 2) - (shoutName.length() + (3 * wordSpacing));
//                int wordY = barY - 20; // Adjust Y position as needed
//
//                // Iterate over each word in the shout name
//                for (int i = 0; i < shoutWords.length; i++) {
//                    if (i >= wordThresholds.length) break; // Make sure we don't access out of bounds if shoutWords has fewer than 3 elements
//
//                    String word = shoutWords[i];
//                    float threshold = wordThresholds[i];
//
//                    // Only start rendering the word if the charge progress has reached its threshold
//                    if (shoutChargeProgress >= threshold) {
//                        // Calculate the alpha (opacity) based on how close the progress is to the word's threshold
//                        float alpha = 1.0f; // Default to fully visible
//
//                        if (shoutChargeProgress < threshold + 0.25f) {
//                            // Word is fading in; calculate alpha as before
//                            alpha = (shoutChargeProgress - threshold) / 0.25f;
//                            alpha = Math.min(1.0f, alpha); // Cap the alpha at 1
//                            alpha = Math.max(0.0f, alpha); // Ensure alpha is between 0 and 1
//                        }
//                        // Once alpha is 1.0f, no need to recalculate; the word stays fully visible
//
//                        // Calculate the color with alpha (0xAARRGGBB format, where AA is the alpha)
//                        int alphaHex = (int) (alpha * 255) << 24;
//                        int color = 0xFFFFFF | alphaHex; // Apply alpha to the color
//
//                        // Render the word with the calculated alpha
//                        guiGraphics.drawCenteredString(mc.font, word, wordX, wordY, color);
//
//                        // Move X position for the next word (adjust this to fit your layout)
//                        wordX += mc.font.width(word) + wordSpacing;
//                    }
//                }

                // Optional: render text to show the percentage
//                String progressText = String.format("Charging: %.0f%%", shoutChargeProgress * 100);
//                guiGraphics.drawString(mc.font, progressText, barX - (mc.font.width(progressText) / 2), barY - 10, 0xFFFFFF);
            }
        }
    }

    public static class SkyrimStamina implements LayeredDraw.Layer
    {
        private final ResourceLocation OVERLAY_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/overlay_icons.png");

        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker partialTick) {
            PoseStack poseStack = guiGraphics.pose();
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow();
            int scaledWidth = window.getGuiScaledWidth();
            int scaledHeight = window.getGuiScaledHeight();

            float staminaPercentage = mc.player.getFoodData().getFoodLevel() / 20.0f; // 20.0f is the max food value (this is apparently hardcoded...)
            float staminaBarWidth = PLAYER_BAR_MAX_WIDTH * staminaPercentage;
            float staminaBarStartX = (float)(scaledWidth - 108) + (PLAYER_BAR_MAX_WIDTH - staminaBarWidth);

            poseStack.pushPose();
            RenderUtil.bind(OVERLAY_ICONS);
            RenderUtil.blitWithBlend(poseStack, scaledWidth - 120, scaledHeight - 40, 0, 51, 102, 10, 256, 256, 1, 1);
            RenderUtil.blitWithBlend(poseStack, (int)staminaBarStartX, scaledHeight - 38, 12 + ((PLAYER_BAR_MAX_WIDTH - staminaBarWidth) / 2.0f), 80, (int)staminaBarWidth, 6, 256, 256, 1, 1);
            poseStack.popPose();
        }
    }

    public static class SkyrimAir implements LayeredDraw.Layer
    {
        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker partialTick) {
            PoseStack poseStack = guiGraphics.pose();
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow();
            int scaledWidth = window.getGuiScaledWidth();
            int scaledHeight = window.getGuiScaledHeight();

            final ResourceLocation AIR_SPRITE = ResourceLocation.withDefaultNamespace("hud/air");
            final ResourceLocation AIR_BURSTING_SPRITE = ResourceLocation.withDefaultNamespace("hud/air_bursting");

            mc.getProfiler().push("air");
            Player player = (Player)mc.getCameraEntity();
            RenderSystem.enableBlend();
            int left = guiGraphics.guiWidth() - 20;
            int top;
            if(mc.player.getArmorValue() > 0)
                top = guiGraphics.guiHeight() - 65;
            else
                top = guiGraphics.guiHeight() - 53;
            int air = player.getAirSupply();

            if(player.isEyeInFluid(FluidTags.WATER) || air < 300) {
                int full = Mth.ceil((double)(air - 2) * 10.0 / 300.0);
                int partial = Mth.ceil((double)air * 10.0 / 300.0) - full;

                for(int i = 0; i < full + partial; ++i) {
                    guiGraphics.blitSprite(i < full ? AIR_SPRITE : AIR_BURSTING_SPRITE, left - i * 8 - 9, top, 9, 9);
                }
            }
            RenderSystem.disableBlend();
            mc.getProfiler().pop();
        }
    }

    public static class SkyrimXPBar implements LayeredDraw.Layer
    {
        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker partialTick) {
            PoseStack poseStack = guiGraphics.pose();
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow();
            int scaledWidth = window.getGuiScaledWidth();
            int scaledHeight = window.getGuiScaledHeight();
            final ResourceLocation EXPERIENCE_BAR_BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("hud/experience_bar_background");
            final ResourceLocation EXPERIENCE_BAR_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("hud/experience_bar_progress");

            RenderSystem.enableBlend();
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

            if (mc.gameMode.hasExperience()) {
                int pX = guiGraphics.guiWidth() / 2 - 91;

                mc.getProfiler().push("expBar");
                int i = mc.player.getXpNeededForNextLevel();
                if (i > 0) {
                    int j = 182;
                    int k = (int)(mc.player.experienceProgress * 183.0F);
                    int l = guiGraphics.guiHeight() - 32 + 3;
                    guiGraphics.blitSprite(EXPERIENCE_BAR_BACKGROUND_SPRITE, pX, l, 182, 5);
                    if (k > 0) {
                        guiGraphics.blitSprite(EXPERIENCE_BAR_PROGRESS_SPRITE, 182, 5, 0, 0, pX, l, k, 5);
                    }
                }

                mc.getProfiler().pop();
                if (mc.player.experienceLevel > 0) {
                    mc.getProfiler().push("expLevel");
                    String s = mc.player.experienceLevel + "";
                    int i1 = (guiGraphics.guiWidth() - mc.font.width(s)) / 2;
                    int j1 = guiGraphics.guiHeight() - 31 - 4;
                    guiGraphics.drawString(mc.font, s, i1 + 1, j1, 0, false);
                    guiGraphics.drawString(mc.font, s, i1 - 1, j1, 0, false);
                    guiGraphics.drawString(mc.font, s, i1, j1 + 1, 0, false);
                    guiGraphics.drawString(mc.font, s, i1, j1 - 1, 0, false);
                    guiGraphics.drawString(mc.font, s, i1, j1, 8453920, false);
                    mc.getProfiler().pop();
                }
            }

            RenderSystem.disableBlend();
        }
    }

    public static class SkyrimArmorIcons implements LayeredDraw.Layer
    {
        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker partialTick) {
            PoseStack poseStack = guiGraphics.pose();
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow();
            int scaledWidth = window.getGuiScaledWidth();
            int scaledHeight = window.getGuiScaledHeight();

            final ResourceLocation ARMOR_EMPTY_SPRITE = ResourceLocation.withDefaultNamespace("hud/armor_empty");
            final ResourceLocation ARMOR_HALF_SPRITE = ResourceLocation.withDefaultNamespace("hud/armor_half");
            final ResourceLocation ARMOR_FULL_SPRITE = ResourceLocation.withDefaultNamespace("hud/armor_full");

            mc.getProfiler().push("armor");
            RenderSystem.enableBlend();
            int left = guiGraphics.guiWidth() - 20 - 8;
            int top = guiGraphics.guiWidth() - 53;

            int level = mc.player.getArmorValue();
            for (int i = 1; level > 0 && i < 20; i += 2) {
                if (i < level) {
                    guiGraphics.blitSprite(ARMOR_FULL_SPRITE, left, top, 9, 9);
                } else if (i == level) {
                    //TODO: rotate 180 horizontally?
                    guiGraphics.blitSprite(ARMOR_HALF_SPRITE, left, top, 9, 9);
                } else {
                    guiGraphics.blitSprite(ARMOR_EMPTY_SPRITE, left, top, 9, 9);
                }

                left -= 8;
            }
            RenderSystem.disableBlend();
            mc.getProfiler().pop();
        }
    }

    public static class SkyrimMagicka implements LayeredDraw.Layer
    {
        private final ResourceLocation OVERLAY_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/overlay_icons.png");

        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker partialTick) {
            PoseStack poseStack = guiGraphics.pose();
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow();
            int scaledWidth = window.getGuiScaledWidth();
            int scaledHeight = window.getGuiScaledHeight();
            Character character = Character.get(mc.player);
            float magickaPercentage = character.getMagicka() / character.getMaxMagicka();
            float magickaBarWidth = PLAYER_BAR_MAX_WIDTH * magickaPercentage;

            poseStack.pushPose();
            RenderUtil.bind(OVERLAY_ICONS);
            RenderUtil.blitWithBlend(poseStack, 20, scaledHeight - 40, 0, 51, 102, 10, 256, 256, 1, 1);
            RenderUtil.blitWithBlend(poseStack, 32, scaledHeight - 38, 12 + ((PLAYER_BAR_MAX_WIDTH - magickaBarWidth) / 2.0f), 64, (int)(78 * magickaPercentage), 6, 256, 256, 1, 1);
            poseStack.popPose();
        }
    }

    public static class SkyrimHealth implements LayeredDraw.Layer
    {
        private final ResourceLocation OVERLAY_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/overlay_icons.png");

        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker partialTick) {
            PoseStack poseStack = guiGraphics.pose();
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow();
            int scaledWidth = window.getGuiScaledWidth();
            int scaledHeight = window.getGuiScaledHeight();

            float healthPercentage = mc.player.getHealth() / mc.player.getMaxHealth();
            float healthBarWidth = PLAYER_BAR_MAX_WIDTH * healthPercentage;
            float healthBarStartX;

            poseStack.pushPose();
            RenderUtil.bind(OVERLAY_ICONS);
            if(mc.player.level().getDifficulty() == Difficulty.HARD) {
                healthBarStartX = (scaledWidth / 2 - 40) + (PLAYER_BAR_MAX_WIDTH - healthBarWidth) / 2.0f;
                RenderUtil.blitWithBlend(poseStack, scaledWidth / 2 - 51, scaledHeight - 46, 96, 71, 100, 16, 256, 256, 1, 1);
            } else {
                healthBarStartX = (scaledWidth / 2 - 39) + (PLAYER_BAR_MAX_WIDTH - healthBarWidth) / 2.0f;
                RenderUtil.blitWithBlend(poseStack, scaledWidth / 2 - 51, scaledHeight - 40, 0, 51, 102, 10, 256, 256, 1, 1);
            }
            RenderUtil.blitWithBlend(poseStack, (int)healthBarStartX, scaledHeight - 38, 12 + ((PLAYER_BAR_MAX_WIDTH - healthBarWidth) / 2.0f), 72, healthBarWidth, 6, 256, 256, 1, 1);
            poseStack.popPose();
        }
    }

    public static class SkyrimCompass implements LayeredDraw.Layer
    {
        private final ResourceLocation OVERLAY_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/overlay_icons.png");

        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker partialTick) {
            PoseStack poseStack = guiGraphics.pose();
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow();
            int scaledWidth = window.getGuiScaledWidth();
            Character character = Character.get(mc.player);

            poseStack.pushPose();
            RenderUtil.bind(OVERLAY_ICONS);
            RenderUtil.blitWithBlend(poseStack, scaledWidth / 2 - 110, 10, 0, 37, 221, 14, 256, 256, 0, 1.0f);
            poseStack.popPose();
//            guiGraphics.blitSprite(compassBackground, scaledWidth / 2 - 110, 10, 1, 221, 14);
//        guiGraphics.blit(scaledWidth / 2 - 110, 10, 0, 37, 221, compassBackground);

            assert mc.player != null;
            float yaw = Mth.lerp(mc.getTimer().getRealtimeDeltaTicks(), mc.player.yHeadRotO, mc.player.yHeadRot) % 360;
            if (yaw < 0) yaw += 360;

            drawCardinalDirection(guiGraphics, yaw, 0, scaledWidth / 2, "S");
            drawCardinalDirection(guiGraphics, yaw, 90, scaledWidth / 2, "W");
            drawCardinalDirection(guiGraphics, yaw, 180, scaledWidth / 2, "N");
            drawCardinalDirection(guiGraphics, yaw, 270, scaledWidth / 2, "E");

//        double playerPosX = Mth.lerp(mc.getFrameTime(), mc.player.xo, mc.player.getX());
//        double playerPosY = Mth.lerp(mc.getFrameTime(), mc.player.yo, mc.player.getY());
//        double playerPosZ = Mth.lerp(mc.getFrameTime(), mc.player.zo, mc.player.getZ());
            double playerPosX = Mth.lerp(mc.getTimer().getRealtimeDeltaTicks(), mc.player.xo, mc.player.getX());
            double playerPosY = Mth.lerp(mc.getTimer().getRealtimeDeltaTicks(), mc.player.yo, mc.player.getY());
            double playerPosZ = Mth.lerp(mc.getTimer().getRealtimeDeltaTicks(), mc.player.zo, mc.player.getZ());
            final float finalYaw = yaw;

            List<CompassFeature> compassFeatures = character.getCompassFeatures();
            if (compassFeatures.size() > 0) {
                List<CompassFeature> sortedFeatures = Lists.newArrayList(compassFeatures);
                sortedFeatures.sort((a, b) -> {
                    Vec3 positionA = new Vec3(a.getBlockPos().getX(), 0, a.getBlockPos().getZ()); //mc.player.getY()
                    Vec3 positionB = new Vec3(b.getBlockPos().getX(), 0, b.getBlockPos().getZ());
                    float angleA = Mth.abs(angleDistance(finalYaw, angleFromTarget(positionA, positionB).x));
                    float angleB = Mth.abs(angleDistance(finalYaw, angleFromTarget(positionB, positionA).x));
                    return (int) Math.signum(angleB - angleA);
                });

                for (CompassFeature feature : sortedFeatures) {
                    if (mc.player.position().distanceToSqr(feature.getBlockPos().getX(), mc.player.position().y, feature.getBlockPos().getZ()) <= 512 * 16) { // 256 blocks?
                        Vec3 position = new Vec3(feature.getBlockPos().getX(), 0, feature.getBlockPos().getZ());
                        Vec2 angleYd = angleFromTarget(position, new Vec3(playerPosX, playerPosY, playerPosZ));
                        drawStructureIndicator(poseStack, finalYaw, angleYd.x, scaledWidth / 2, feature);
                    }
                }
            }
            List<Integer> targetingEntities = character.getTargets();
            if (targetingEntities != null) {
                for (int entityID : targetingEntities) {
                    if (mc.player.level().getEntity(entityID) == null)
                        return;

                    if(!(mc.player.level().getEntity(entityID) instanceof LivingEntity targetingEntity))
                        return;

                    // Check player is out of target range
                    if (!mc.player.closerThan(targetingEntity, 16.0D))
                        return;

                    Vec3 a = new Vec3(playerPosX, playerPosY, playerPosZ);
                    Vec3 b = targetingEntity.position();

                    Vec2 angleYd = angleFromTarget(b, a);
                    drawTargetIndicator(poseStack, finalYaw, angleYd.x, scaledWidth / 2); //ydelta = angleYd.y
                }
            }
        }

        private static void drawCardinalDirection(GuiGraphics guiGraphics, float yaw, float angle, int xPos, String text) {
            float nDist = angleDistance(yaw, angle);
            if (Mth.abs(nDist) <= 90) {
                float nPos = xPos + nDist;
                //fill(matrixStack, (int)(nPos-0.5f), 10, (int)(nPos+0.5f), 18, 0x7FFFFFFF);
                guiGraphics.drawCenteredString(Minecraft.getInstance().font, text, (int)nPos, 13, 0xFFFFFF);
            }
        }

        private static Vec2 angleFromTarget(Vec3 targetPos, Vec3 playerPos) {
            double xd = targetPos.x - playerPos.x;
            double yd = targetPos.y - playerPos.y;
            double zd = targetPos.z - playerPos.z;
            return new Vec2((float) Math.toDegrees(-Math.atan2(xd, zd)), (float)yd);
        }

        private static float angleDistance(float yaw, float other) {
            float dist = other - yaw;
            if (dist > 0) return dist > 180 ? (dist - 360) : dist;
            else return dist < -180 ? (dist + 360) : dist;
        }

        private void drawTargetIndicator(PoseStack poseStack, float yaw, float angle, int xPos) {
            float nDist = angleDistance(yaw, angle);
            if (Mth.abs(nDist) <= 90) {
                float nPos = xPos + nDist;
                //fill(matrixStack, (int)(nPos-0.5f), 10, (int)(nPos+0.5f), 18, 0x7FFFFFFF);
                poseStack.pushPose();
                RenderUtil.bind(OVERLAY_ICONS);
                RenderUtil.blitWithBlend(poseStack, nPos-2, 15, 106, 53, 4, 4, 256, 256, 3, 1.0f);
                poseStack.popPose();
            }
        }

        private void drawStructureIndicator(PoseStack poseStack, float yaw, float angle, int xPos, CompassFeature feature) {
            if(feature.getIconUV() == null)
                return;

            float nDist = angleDistance(yaw, angle);
            if (Mth.abs(nDist) <= 90)
            {
                float nPos = xPos + nDist;
                int u = feature.getIconUV().getKey(), v = feature.getIconUV().getValue();
                //fill(matrixStack, (int)(nPos-0.5f), 10, (int)(nPos+0.5f), 18, 0x7FFFFFFF);
                poseStack.pushPose();
                RenderUtil.bind(OVERLAY_ICONS);
                RenderUtil.blitWithBlend(poseStack, nPos-2, 17 - (CompassFeature.ICON_HEIGHT / 2), u, v, CompassFeature.ICON_WIDTH, CompassFeature.ICON_HEIGHT, 256, 256, 2, 1.0f);
                poseStack.popPose();
            }
        }
    }
}