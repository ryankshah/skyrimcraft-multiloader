package com.ryankshah.skyrimcraft.block.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.TurnStoneBlock;
import com.ryankshah.skyrimcraft.block.entity.TurnStoneBlockEntity;
import com.ryankshah.skyrimcraft.block.entity.renderer.TurnStoneBlockEntityRenderer;
import com.ryankshah.skyrimcraft.util.AnimationHelper;
import com.ryankshah.skyrimcraft.util.IAnimatedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class TurnStoneModel<T extends Entity> extends EntityModel<T> implements IAnimatedModel<TurnStoneBlockEntityRenderer.AnimContext>
{
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "turn_stone"), "main");
    private final ModelPart root;
    private final ModelPart bone;
    private final ModelPart bb_main;

    public TurnStoneModel(ModelPart root) {
        this.root = root;
        this.bone = root.getChild("bone");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(48, 18).addBox(-6.0F, -31.0F, -6.0F, 12.0F, 11.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, -20.0F, -7.0F, 14.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 30).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnimation(Level level, float partialTicks, TurnStoneBlockEntityRenderer.AnimContext ctx) {
//        root.getAllParts().forEach(ModelPart::resetPose);
        AnimationState state = ctx.state();
        float time = (float)level.getGameTime() + partialTicks;
//        AnimationHelper.animate(this, state, TurnStoneBlockEntity.SPIN, time, 0.5F);
        AnimationHelper.applyStatic(this, TurnStoneBlockEntity.SPIN);
    }

    @Override
    public ModelPart getRoot() {
        return root;
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}