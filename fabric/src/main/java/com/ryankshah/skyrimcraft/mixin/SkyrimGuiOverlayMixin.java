package com.ryankshah.skyrimcraft.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class SkyrimGuiOverlayMixin
{
    @Inject(method="renderPlayerHealth", at=@At(value="HEAD"), cancellable = true)
    public void renderPlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        ci.cancel();
    }
}