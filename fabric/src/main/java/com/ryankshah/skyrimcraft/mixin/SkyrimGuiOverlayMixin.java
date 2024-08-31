package com.ryankshah.skyrimcraft.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class SkyrimGuiOverlayMixin
{
    @Inject(method="renderExperienceBar", at=@At(value="HEAD"), cancellable = true)
    public void renderExperienceBar(GuiGraphics guiGraphics, int x, CallbackInfo ci) {
        ci.cancel();
    }

    // This is called within renderPlayerHealth so no need to cancel separately
//    @Inject(method="renderArmor", at=@At(value="HEAD"), cancellable = true)
//    public void renderArmor(GuiGraphics guiGraphics, Player player, int y, int heartRows, int height, int x, CallbackInfo ci) {
//        ci.cancel();
//    }

    @Inject(method="renderCrosshair", at=@At(value="HEAD"), cancellable = true)
    public void renderCrosshair(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method="renderPlayerHealth", at=@At(value="HEAD"), cancellable = true)
    public void renderPlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        ci.cancel();
    }
}