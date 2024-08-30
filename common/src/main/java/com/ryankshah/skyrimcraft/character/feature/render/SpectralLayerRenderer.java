package com.ryankshah.skyrimcraft.character.feature.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class SpectralLayerRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>
{

    public SpectralLayerRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack p_225628_1_, MultiBufferSource p_225628_2_, int p_225628_3_, AbstractClientPlayer p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if(p_225628_4_.hasEffect(ModEffects.SPECTRAL.asHolder()) || p_225628_4_.hasEffect(ModEffects.ETHEREAL.asHolder())) {
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            VertexConsumer ivertexbuilder = p_225628_2_.getBuffer(RenderType.entityTranslucentCull(p_225628_4_.getSkin().texture()));
            getParentModel().renderToBuffer(p_225628_1_,
                    ivertexbuilder,
                    p_225628_3_,
                    PlayerRenderer.getOverlayCoords(p_225628_4_, 0.0F),
                    OverlayTexture.NO_OVERLAY);
            RenderSystem.disableBlend();
        }
    }
}
