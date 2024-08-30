package com.ryankshah.skyrimcraft.entity.boss.dragon.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ryankshah.skyrimcraft.entity.boss.dragon.SkyrimDragon;
import com.ryankshah.skyrimcraft.entity.boss.dragon.model.SkyrimDragonModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SkyrimDragonRenderer extends GeoEntityRenderer<SkyrimDragon>
{
    public SkyrimDragonRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SkyrimDragonModel());
        this.shadowRadius = 2.0F; //change 0.7 to the desired shadow size.
    }

    @Override
    public void preRender(PoseStack poseStack, SkyrimDragon animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }
}