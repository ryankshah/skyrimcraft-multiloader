package com.ryankshah.skyrimcraft.screen;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.recipe.OvenRecipe;
import com.ryankshah.skyrimcraft.network.recipe.FinishOvenRecipe;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import com.ryankshah.skyrimcraft.util.RenderUtil;
import commonnetwork.api.Dispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OvenScreen extends Screen
{
    protected static final ResourceLocation OVERLAY_ICONS = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/gui/overlay_icons.png");

    private Multimap<String, OvenRecipe> items;
    private List<OvenRecipe> itemList;
    private Object[] categories;
    private boolean categoryChosen;
    private int currentCategory;
    private int currentItem;
    private int categoryStartIndex, itemStartIndex;
    private float spin = 0.0F;
    private OvenRecipe currentRecipeObject = null;
    private Player player;

    public OvenScreen(List<OvenRecipe> recipes) {
        super(Component.translatable(Constants.MODID + ".ovenscreen.title"));
        this.player = Minecraft.getInstance().player;
        this.items = ArrayListMultimap.create();
        recipes.stream().forEach(recipe -> items.put(recipe.getCategory(), recipe));
        this.categories = this.items.keySet().toArray();
        this.currentCategory = 0;
        this.currentItem = 0;
        this.itemList = new ArrayList<>();
        this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
        this.itemList.sort(Comparator.comparing(i -> i.getResult().getItem().getDescriptionId()));
        this.categoryChosen = false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack matrixStack = graphics.pose();
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
//        this.renderBackground(graphics, mouseX, mouseY, partialTicks);

//        Minecraft.getInstance().getConnection().registryAccess().registry(QuestRegistry.QUESTS_REGISTRY_KEY).ifPresent(
//                registry -> System.out.println(registry.stream().toList())
//        );
//
//        player.level().registryAccess().registry(QuestRegistry.QUESTS_REGISTRY_KEY).ifPresent(
//                registry -> System.out.println(registry.stream().toList())
//        );

        if(!this.categoryChosen) {
            graphics.fillGradient(10, 0, 80, this.height - 2, 0xAA000000, 0xAA555555);
            graphics.fillGradient(12, 2, 13, this.height - 2, 0xFF6E6B64, 0xFF6E6B64);
            graphics.fillGradient(77, 2, 78, this.height - 2, 0xFF6E6B64, 0xFF6E6B64);
            graphics.fillGradient(90, 0, 200, this.height, 0xAA000000, 0xAA000000);
            graphics.fillGradient(197, 2, 198, this.height - 2, 0xFF5D5A51, 0xFF5D5A51);
            graphics.fillGradient(92, 2, 93, this.height - 2, 0xFF5D5A51, 0xFF5D5A51);
        } else {
            graphics.fillGradient(10, 0, 80, this.height - 2, 0xAA000000, 0xAA000000);
            graphics.fillGradient(12, 2, 13, this.height - 2, 0xFF5D5A51, 0xFF5D5A51);
            graphics.fillGradient(77, 2, 78, this.height - 2, 0xFF5D5A51, 0xFF5D5A51);
            graphics.fillGradient(90, 0, 200, this.height, 0xAA000000, 0xAA555555);
            graphics.fillGradient(197, 2, 198, this.height - 2, 0xFF6E6B64, 0xFF6E6B64);
            graphics.fillGradient(92, 2, 93, this.height - 2, 0xFF6E6B64, 0xFF6E6B64);
        }

        int MIN_Y = 20;
        int MAX_Y = height / 2 + 14 * 6 - 10;

        if (!this.items.isEmpty()) {
            Object[] categories = this.getCategories(this.items);

            for(int i = 0; i < categories.length; i++) {
                int y = this.height / 2 + 14 * i - this.currentCategory * font.lineHeight;
                if(y <= MIN_Y || y >= MAX_Y)
                    continue;

                String categoryName = ((String)categories[i]).toUpperCase();

                if (categoryName.length() >= 10)
                    categoryName = categoryName.substring(0, 8) + "..";

                graphics.drawString(font, categoryName, 18, y, i == currentCategory ? 16777215 : 12632256);
            }

            if (this.itemList != null) {
                for(int i = 0; i < itemList.size(); i++) {
                    OvenRecipe recipe = this.itemList.get(i);

                    if (i == this.currentItem) {
                        this.currentRecipeObject = recipe;
                        this.drawItemImage(matrixStack, recipe.getResult(), width - 100, height / 2 - 70, this.spin);
                        this.drawItemInformation(graphics, recipe);
                    }

                    int y = this.height / 2 + 14 * i - this.currentItem * font.lineHeight;
                    if(y <= MIN_Y || y >= MAX_Y)
                        continue;

                    String name = recipe.getResult().getHoverName().getString();
                    if (name.length() >= 16)
                        name = name.substring(0, 14) + "..";

                    graphics.drawString(font, name, 98, y, i == currentItem ? 16777215 : 12632256);
                }
            }
        }

        graphics.fillGradient(0, this.height * 3 / 4 + 20, this.width, this.height, 0xAA000000, 0xAA000000);
        graphics.fillGradient(0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xFF6E6B64, 0xFF6E6B64);
        drawBorderedGradientRect(graphics, 17, this.height - 29, 32 + font.width("Enter"), this.height - 14, 0xAA000000, 0xAA000000, 0xFF6E6B64);
        graphics.drawString(font, "Enter", 25, this.height - 25, 0x00FFFFFF);
        graphics.drawString(font, "Create", 32 + font.width("Enter") + 6, this.height - 25, 0x00FFFFFF);

        renderHealth(graphics);


//        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.spin >= 360.0f)
            this.spin = 0.0f;
        else
            ++this.spin;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if(scrollY < 0) {
            if (!this.categoryChosen) {
                if (this.currentCategory < this.categories.length - 1)
                    ++this.currentCategory;

                this.itemList.clear();
                this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
            } else {
                if (this.currentItem < this.itemList.size() - 1)
                    ++this.currentItem;
            }
        } else if(scrollY > 0) {
            if (!this.categoryChosen) {
                if(this.currentCategory > 0)
                    --this.currentCategory;

                this.itemList.clear();
                this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
            } else {
                if (this.currentItem > 0)
                    --this.currentItem;
            }
        }
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(KeysRegistry.SKYRIM_MENU_SOUTH.get().matches(keyCode, scanCode)) {
            if (!this.categoryChosen) {
                if (this.currentCategory < this.categories.length - 1) {
                    ++this.currentCategory;
                } else {
                    this.currentCategory = this.categories.length - 1;
                }

                this.itemList.clear();
                this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
            } else if (this.currentItem < this.itemList.size() - 1) {
                ++this.currentItem;
            } else {
                this.currentItem = this.itemList.size() - 1;
            }
        }

        if(KeysRegistry.SKYRIM_MENU_NORTH.get().matches(keyCode, scanCode)) {
            if (!this.categoryChosen) {
                if (this.currentCategory > 0) {
                    --this.currentCategory;
                } else {
                    this.currentCategory = 0;
                }

                this.itemList.clear();
                this.itemList.addAll(this.items.get((String)this.categories[this.currentCategory]));
            } else if (this.currentItem > 0) {
                --this.currentItem;
            } else {
                this.currentItem = 0;
            }
        }

        if(KeysRegistry.SKYRIM_MENU_EAST.get().matches(keyCode, scanCode)) {
            if(this.categoryChosen) {
                this.categoryChosen = false;
                this.currentItem = 0;
            }
        }

        if(KeysRegistry.SKYRIM_MENU_WEST.get().matches(keyCode, scanCode)) {
            if(!this.categoryChosen) {
                this.categoryChosen = true;
                this.currentItem = 0;
            }
        }

        if(KeysRegistry.SKYRIM_MENU_ENTER.get().matches(keyCode, scanCode)) {
            if (!this.categoryChosen) {
                return false;
            }

            final FinishOvenRecipe finishRecipe = new FinishOvenRecipe(currentRecipeObject);
            Dispatcher.sendToServer(finishRecipe);
//            PacketDistributor.SERVER.noArg().send(finishRecipe);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean handleComponentClicked(@Nullable Style style) {
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return true;
    }

    @Override
    public void removed() {
        super.removed();
    }

    private void drawItemImage(PoseStack matrixStack, ItemStack is, int xPos, int yPos, float spin) {
        matrixStack.pushPose();
        RenderUtil.bind(InventoryMenu.BLOCK_ATLAS);
        minecraft.getTextureManager().getTexture(InventoryMenu.BLOCK_ATLAS).setFilter(false, false);
        //RenderSystem.enableRescaleNormal();
        //RenderSystem.enableAlphaTest();
        //RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.translate(xPos, yPos,100F); //300.0Fi
        matrixStack.mulPose(Axis.XP.rotationDegrees(180));
        matrixStack.mulPose(Axis.YP.rotationDegrees(spin++ % 360));
        matrixStack.scale(60F, 60F, 60F);
        MultiBufferSource.BufferSource irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        Minecraft.getInstance().getItemRenderer().render(is, ItemDisplayContext.GUI, false, matrixStack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, Minecraft.getInstance().getItemRenderer().getModel(is, null, null, 0));
        irendertypebuffer$impl.endBatch();
        //RenderSystem.disableAlphaTest();
        //RenderSystem.disableRescaleNormal();
        matrixStack.popPose();
    }

    private void drawItemInformation(GuiGraphics graphics, OvenRecipe recipe) {
        //graphics.fillGradient(this.width - 180, (this.height + 50) / 2 - 20, this.width - 20, (this.height + 50) / 2 + 80, 0xAA000000, 0xAA000000);
        drawBorderedGradientRect(graphics, this.width - 180, this.height / 2 - 20, this.width - 20, this.height / 2 + 20 + (10 * recipe.getRecipeItems().size()), 0xAA000000, 0xAA000000, 0xFF6E6B64);
        graphics.fillGradient(this.width - 160, (this.height) / 2, this.width - 40, (this.height) / 2 + 1, 0xFF6E6B64, 0xFF6E6B64); // Line under recipe item name

        graphics.drawCenteredString(font, recipe.getResult().getHoverName(), width - 100, height / 2 - 10, 0xFFFFFF);
        //this.func_73730_a(this.field_146294_l - 170, this.field_146294_l - 30, (this.field_146295_m + 50) / 2 + 20, -1);
        //drawCenteredString(matrixStack, font, "Required Items: ", width - 100, height / 2 + 10, 0xFFFFFF);

        for(int i = 0; i < recipe.getIngredients().size(); i++) {
            Ingredient ingredient = recipe.getIngredients().get(i);
            ItemStack is = ingredient.getItems()[0];
            boolean hasItem = hasItem(player, is);

            graphics.drawCenteredString(font, is.getHoverName().plainCopy().append(Component.translatable(" (" + is.getCount() + ")")), width - 100, height / 2 + 10 + (10 * i+1), !hasItem ? 0xFF0000 : 0x228B22);
        }
    }

    public static boolean hasItem(Player player, ItemStack is) {
        if (is != null) {
            int count = 0;
            for(int i = 0; i < player.inventoryMenu.slots.size(); ++i) {
                ItemStack stack = player.getInventory().getItem(i);

                if(stack.is(is.getItem())) {
                    count += stack.getCount();
                    if(count >= is.getCount())
                        return true;
                }
            }
        }
        return false;
    }

    private Object[] getCategories(Multimap<String, OvenRecipe> items) {
        return items.keySet().toArray();
    }

    private void renderHealth(GuiGraphics graphics) {
        PoseStack matrixStack = graphics.pose();
        RenderUtil.bind(OVERLAY_ICONS);
        float healthPercentage = this.minecraft.player.getHealth() / minecraft.player.getMaxHealth();
        float healthBarWidth = 80.0f * healthPercentage;
        float healthBarStartX = (float)(width - 109) + (80.0f - healthBarWidth);
        RenderUtil.blitWithBlend(matrixStack, this.width - 120, this.height - 25, 0, 51, 102, 10, 256, 256, 2, 1);
        RenderUtil.blitWithBlend(matrixStack, (int)healthBarStartX, this.height - 23, 12 + ((78 - healthBarWidth) / 2.0f), 72, (int)healthBarWidth, 6, 256, 256, 2, 1);
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