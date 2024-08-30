package com.ryankshah.skyrimcraft.entity.creature.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.Skeever;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Monster;

public class SkeeverModel<S extends Monster> extends QuadrupedModel<Skeever> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "skeever"), "main");

    public SkeeverModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = QuadrupedModel.createBodyMesh(7, CubeDeformation.NONE);
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition skeever = partdefinition.addOrReplaceChild("skeever", CubeListBuilder.create().texOffs(24, 0).addBox(-3.5F, -11.0F, -8.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.5F, 0.0F));

        PartDefinition body_back = skeever.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(0, 19).addBox(-3.0F, -4.25F, 0.25F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.25F))
                .texOffs(24, 0).addBox(-3.5F, -5.0F, -7.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -1.0F));

        PartDefinition tail = body_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(34, 15).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 9.5F));

        PartDefinition tail_second = tail.addOrReplaceChild("tail_second", CubeListBuilder.create().texOffs(42, 17).addBox(-1.0F, -1.0F, -0.25F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.25F));

        PartDefinition tail_third = tail_second.addOrReplaceChild("tail_third", CubeListBuilder.create().texOffs(45, 8).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 8.5F));

        PartDefinition leg_back_left = body_back.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-1.0F, -1.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 2.0F, 7.5F));

        PartDefinition leg_back_right = body_back.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 2.0F, 7.5F));

        PartDefinition top_back = body_back.addOrReplaceChild("top_back", CubeListBuilder.create().texOffs(24, 14).addBox(-2.0F, -4.0F, 0.0F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.0F, 4.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition head = skeever.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -13.0F));

        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

        PartDefinition jaw = mouth.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(21, 22).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, -1.0F, 0.5F));

        PartDefinition nose = mouth.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -3.5F));

        PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition ear_left = ears.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(16, 14).mirror().addBox(-1.0F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -4.0F, 2.0F, -0.2618F, 0.0F, 0.2618F));

        PartDefinition ear_right = ears.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(16, 14).addBox(-2.0F, -4.0F, 0.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -4.0F, 2.0F, -0.2618F, 0.0F, -0.2618F));

        PartDefinition top_head = head.addOrReplaceChild("top_head", CubeListBuilder.create().texOffs(24, 14).addBox(-2.0F, -4.0F, 0.0F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -5.0F, 2.0F, -0.9599F, 0.0F, 0.0F));

        PartDefinition leg_front_left = skeever.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(56, 8).mirror().addBox(-1.0F, -1.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, -4.0F, -6.0F));

        PartDefinition leg_front_right = skeever.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(56, 8).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -4.0F, -6.0F));

        PartDefinition top_front = skeever.addOrReplaceChild("top_front", CubeListBuilder.create().texOffs(24, 18).addBox(-2.0F, -4.0F, 0.0F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -11.0F, -5.0F, -1.2217F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

//    @Override
//    public void setupAnim(Skeever entity, float pLimbSwing, float pLimbSwingAmount, float ageInTicks, float pNetHeadYaw, float pHeadPitch) {
//        this.head.xRot = pHeadPitch * (float) (Math.PI / 180.0);
//        this.head.yRot = pNetHeadYaw * (float) (Math.PI / 180.0);
//        this.rightHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
//        this.leftHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount;
//        this.rightFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount;
//        this.leftFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
//    }

//    @Override
//    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//        skeever.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//    }
}