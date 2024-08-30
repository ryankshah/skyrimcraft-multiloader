package com.ryankshah.skyrimcraft.character.feature.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;

public class KhajiitHeadModel extends EntityModel<AbstractClientPlayer> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.withDefaultNamespace("player"), "khajiit_head");
    private final ModelPart ears;
    private final ModelPart Whiskers;
    private final ModelPart nose;

    public KhajiitHeadModel(ModelPart root) {
        this.ears = root.getChild("ears");
        this.Whiskers = root.getChild("Whiskers");
        this.nose = root.getChild("nose");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ears = partdefinition.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition left_ear = ears.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(2, 10).mirror().addBox(2.5F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(4, 8).mirror().addBox(2.5F, -5.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -31.0F, 0.0F, 0.0F, 0.2618F, 0.0873F));

        PartDefinition right_ear = ears.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(2, 10).addBox(-4.5F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 8).addBox(-4.5F, -5.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, 0.0F, 0.0F, -0.2618F, -0.0873F));

        PartDefinition Whiskers = partdefinition.addOrReplaceChild("Whiskers", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -1.0F));

        PartDefinition Whisker_Right = Whiskers.addOrReplaceChild("Whisker_Right", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition w2_r1 = Whisker_Right.addOrReplaceChild("w2_r1", CubeListBuilder.create().texOffs(10, 9).addBox(-4.0F, -4.0F, -3.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2182F, 0.0436F));

        PartDefinition w1_r1 = Whisker_Right.addOrReplaceChild("w1_r1", CubeListBuilder.create().texOffs(10, 9).addBox(-4.0F, -3.0F, -3.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2182F, -0.0436F));

        PartDefinition Whisker_Left = Whiskers.addOrReplaceChild("Whisker_Left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition w4_r1 = Whisker_Left.addOrReplaceChild("w4_r1", CubeListBuilder.create().texOffs(10, 9).addBox(2.0F, -3.0F, -3.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2182F, 0.0436F));

        PartDefinition w3_r1 = Whisker_Left.addOrReplaceChild("w3_r1", CubeListBuilder.create().texOffs(10, 9).addBox(2.0F, -4.0F, -3.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2182F, -0.0436F));

        PartDefinition nose = partdefinition.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(9, 13).addBox(-2.0F, 2.0F, -5.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition n2_r1 = nose.addOrReplaceChild("n2_r1", CubeListBuilder.create().texOffs(8, 13).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -5.0F, -0.5236F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(AbstractClientPlayer entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int x) {
        ears.render(poseStack, vertexConsumer, packedLight, packedOverlay, x);
        Whiskers.render(poseStack, vertexConsumer, packedLight, packedOverlay, x);
        nose.render(poseStack, vertexConsumer, packedLight, packedOverlay, x);
    }
}