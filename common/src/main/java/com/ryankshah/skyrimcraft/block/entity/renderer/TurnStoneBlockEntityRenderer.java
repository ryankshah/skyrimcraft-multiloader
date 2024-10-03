package com.ryankshah.skyrimcraft.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.TurnStoneBlock;
import com.ryankshah.skyrimcraft.block.entity.TurnStoneBlockEntity;
import com.ryankshah.skyrimcraft.block.entity.model.TurnStoneModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;

public class TurnStoneBlockEntityRenderer implements BlockEntityRenderer<TurnStoneBlockEntity>
{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/block/turn_stone.png");
    private final TurnStoneModel<Entity> model;
    private final AnimContext animCtx = new AnimContext(new AnimationState());

    public TurnStoneBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        model = new TurnStoneModel<>(context.bakeLayer(TurnStoneModel.LAYER_LOCATION));
        animCtx.state.start(0);
    }

    @Override
    public void render(TurnStoneBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        ModelPart toRotate = model.findChildPart("bone").orElseThrow();

        Direction facing = blockEntity.getFacing();
        float initialRotation = getInitialRotation(facing);

        // Apply initial rotation to the entire model
        poseStack.mulPose(Axis.YP.rotationDegrees(initialRotation));

        if (blockEntity.isSpinning()) {
            model.setupAnimation(blockEntity.getLevel(), partialTick, animCtx);
            float spinProgress = blockEntity.getSpinProgress();
            float spinRotation = 90 * spinProgress; // Positive for counterclockwise rotation

            // Apply spin rotation only to the "bone" part
            toRotate.yRot = (float) Math.toRadians(spinRotation);
        } else {
            // Reset rotation when not spinning
            toRotate.yRot = 0;
            // Update the facing when the spin animation completes
//            Direction newFacing = blockEntity.getBlockState().getValue(TurnStoneBlock.FACING).getClockWise();
//            blockEntity.getBlockState().setValue(TurnStoneBlock.FACING, newFacing);
//            blockEntity.setChanged();
        }


        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);

        poseStack.popPose();
    }

    private float getInitialRotation(Direction facing) {
        return switch (facing) {
            case EAST -> 270;
            case SOUTH -> 180;
            case WEST -> 90;
            default -> 0; // NORTH
        };
    }

    public record AnimContext(AnimationState state) { }
}