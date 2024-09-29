package com.ryankshah.skyrimcraft;

import com.google.common.collect.ImmutableMap;
import com.ryankshah.skyrimcraft.block.entity.model.TurnStoneModel;
import com.ryankshah.skyrimcraft.block.entity.renderer.TurnStoneBlockEntityRenderer;
import com.ryankshah.skyrimcraft.character.feature.model.DunmerEarModel;
import com.ryankshah.skyrimcraft.character.feature.model.HighElfEarModel;
import com.ryankshah.skyrimcraft.character.feature.model.KhajiitHeadModel;
import com.ryankshah.skyrimcraft.character.feature.model.KhajiitTailModel;
import com.ryankshah.skyrimcraft.character.magic.entity.render.DisarmRenderer;
import com.ryankshah.skyrimcraft.character.magic.entity.render.FireballRenderer;
import com.ryankshah.skyrimcraft.character.magic.entity.render.LightBallRenderer;
import com.ryankshah.skyrimcraft.character.magic.entity.render.UnrelentingForceRenderer;
import com.ryankshah.skyrimcraft.entity.boss.dragon.render.SkyrimDragonRenderer;
import com.ryankshah.skyrimcraft.entity.creature.model.DraugrModel;
import com.ryankshah.skyrimcraft.entity.creature.model.SkeeverModel;
import com.ryankshah.skyrimcraft.entity.creature.model.VenomfangSkeeverModel;
import com.ryankshah.skyrimcraft.entity.creature.render.*;
import com.ryankshah.skyrimcraft.entity.npc.model.FalmerModel;
import com.ryankshah.skyrimcraft.entity.npc.model.KhajiitModel;
import com.ryankshah.skyrimcraft.entity.npc.render.FalmerRenderer;
import com.ryankshah.skyrimcraft.entity.npc.render.KhajiitRenderer;
import com.ryankshah.skyrimcraft.entity.passive.flying.render.*;
import com.ryankshah.skyrimcraft.registry.BlockEntityRegistry;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.BiConsumer;

public class SkyrimcraftCommonClient
{
    public static void registerRenderers(BiConsumer<EntityType<? extends Entity>, EntityRendererProvider> entityRenderers,
                                         BiConsumer<BlockEntityType<? extends BlockEntity>, BlockEntityRendererProvider> blockEntityRenderers) {
        blockEntityRenderers.accept(BlockEntityRegistry.TURN_STONE.get(), TurnStoneBlockEntityRenderer::new);

        entityRenderers.accept(EntityRegistry.SHOUT_UNRELENTING_FORCE_ENTITY.get(), UnrelentingForceRenderer::new);
        entityRenderers.accept(EntityRegistry.SPELL_FIREBALL_ENTITY.get(), FireballRenderer::new);
        entityRenderers.accept(EntityRegistry.SHOUT_DISARM_ENTITY.get(), DisarmRenderer::new);
        entityRenderers.accept(EntityRegistry.LIGHTBALL_ENTITY.get(), LightBallRenderer::new);

        // Mobs
        entityRenderers.accept(EntityRegistry.SABRE_CAT.get(), SabreCatRenderer::new);
        entityRenderers.accept(EntityRegistry.VALE_SABRE_CAT.get(), ValeSabreCatRenderer::new);
        entityRenderers.accept(EntityRegistry.GIANT.get(), GiantRenderer::new);
        entityRenderers.accept(EntityRegistry.MAMMOTH.get(), MammothRenderer::new);
        entityRenderers.accept(EntityRegistry.DRAGON.get(), SkyrimDragonRenderer::new);
        entityRenderers.accept(EntityRegistry.DRAUGR.get(), DraugrRenderer::new);
        entityRenderers.accept(EntityRegistry.DWARVEN_SPIDER.get(), DwarvenSpiderRenderer::new);
        entityRenderers.accept(EntityRegistry.BLUE_BUTTERFLY.get(), BlueButterflyRenderer::new);
        entityRenderers.accept(EntityRegistry.MONARCH_BUTTERFLY.get(), MonarchButterflyRenderer::new);
        entityRenderers.accept(EntityRegistry.ORANGE_DARTWING.get(), OrangeDartwingRenderer::new);
        entityRenderers.accept(EntityRegistry.BLUE_DARTWING.get(), BlueDartwingRenderer::new);
        entityRenderers.accept(EntityRegistry.LUNAR_MOTH.get(), LunarMothRenderer::new);
        entityRenderers.accept(EntityRegistry.TORCHBUG.get(), TorchBugRenderer::new);


        entityRenderers.accept(EntityRegistry.KHAJIIT.get(), KhajiitRenderer::new);
        entityRenderers.accept(EntityRegistry.FALMER.get(), FalmerRenderer::new);

        entityRenderers.accept(EntityRegistry.SKEEVER.get(), SkeeverRenderer::new);
        entityRenderers.accept(EntityRegistry.VENOMFANG_SKEEVER.get(), VenomfangSkeeverRenderer::new);

        entityRenderers.accept(EntityRegistry.ABECEAN_LONGFIN.get(), AbeceanLongfinRenderer::new);
        entityRenderers.accept(EntityRegistry.CYRODILIC_SPADETAIL.get(), CyrodilicSpadetailRenderer::new);
    }

    public static ImmutableMap<ModelLayerLocation, LayerDefinition> getLayerDefinitions() {
        final CubeDeformation OUTER_ARMOR_DEFORMATION = new CubeDeformation(1.0F);
        final CubeDeformation INNER_ARMOR_DEFORMATION = new CubeDeformation(0.5F);
        LayerDefinition outerArmor = LayerDefinition.create(HumanoidArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION), 64, 32);
        LayerDefinition innerArmor = LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32);

        ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder = ImmutableMap.builder();
        builder.put(DunmerEarModel.LAYER_LOCATION, DunmerEarModel.createBodyLayer());
            builder.put(HighElfEarModel.LAYER_LOCATION, HighElfEarModel.createBodyLayer());
            builder.put(KhajiitHeadModel.LAYER_LOCATION, KhajiitHeadModel.createBodyLayer());
            builder.put(KhajiitTailModel.LAYER_LOCATION, KhajiitTailModel.createBodyLayer());
            builder.put(SkeeverModel.LAYER_LOCATION, SkeeverModel.createBodyLayer());
            builder.put(VenomfangSkeeverModel.LAYER_LOCATION, VenomfangSkeeverModel.createBodyLayer());
            builder.put(KhajiitModel.LAYER_LOCATION, KhajiitModel.createBodyLayer());
            builder.put(KhajiitModel.OUTER_ARMOR_LAYER_LOCATION, outerArmor);
            builder.put(KhajiitModel.INNER_ARMOR_LAYER_LOCATION, innerArmor);
            builder.put(FalmerModel.LAYER_LOCATION, FalmerModel.createBodyLayer());
            builder.put(FalmerModel.OUTER_ARMOR_LAYER_LOCATION, outerArmor);
            builder.put(FalmerModel.INNER_ARMOR_LAYER_LOCATION, innerArmor);
            builder.put(DraugrModel.LAYER_LOCATION, DraugrModel.createBodyLayer());
            builder.put(DraugrModel.OUTER_ARMOR_LAYER_LOCATION, outerArmor);
            builder.put(DraugrModel.INNER_ARMOR_LAYER_LOCATION, innerArmor);
            builder.put(TurnStoneModel.LAYER_LOCATION, TurnStoneModel.createBodyLayer());
        return builder.build();
    }
}