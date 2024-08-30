package com.ryankshah.skyrimcraft.network.skill;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public record HandlePickpocket(int entity) //(int raceID, String raceName, Map<Integer, IntList> skills)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "handlepickpocket");

    public static final StreamCodec<FriendlyByteBuf, HandlePickpocket> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            HandlePickpocket::entity,
            HandlePickpocket::new
    );

    public HandlePickpocket(final FriendlyByteBuf buf) {
        this(buf.readInt()); //, buf.readUtf(), buf.readMap(FriendlyByteBuf::readInt, FriendlyByteBuf::readIntIdList));
    }

    public static void handle(PacketContext<HandlePickpocket> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<HandlePickpocket> context) {
        ServerPlayer player = context.sender();
        Character character = Character.get(player);
        HandlePickpocket data = context.message();

        LivingEntity livingEntity = (LivingEntity) player.level().getEntity(data.entity);

        LootTable loottable = player.level().getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "pickpocket")));
        LootParams lootparams = new LootParams.Builder(player.serverLevel())
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withParameter(LootContextParams.ORIGIN, player.position())
                .create(LootContextParamSets.SELECTOR);
        List<ItemStack> items = loottable.getRandomItems(lootparams);

        if(items.isEmpty()) {
            player.hurt(player.damageSources().mobAttack(livingEntity), 0.5f);
            player.knockback(0.5f, (double) -Mth.sin(player.yRotO * ((float)Math.PI / 180F)), (double)(Mth.cos(player.yRotO * ((float)Math.PI / 180F))));
            player.displayClientMessage(Component.translatable("skill.pickpocket.fail", livingEntity.getDisplayName()), false);
        } else {
            items.forEach(player::addItem);
            player.displayClientMessage(Component.translatable("skill.pickpocket.success", livingEntity.getDisplayName()), false);
            livingEntity.removeTag(EntityRegistry.PICKPOCKET_TAG);
            final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.getResourceKey(SkillRegistry.PICKPOCKET.get()).get(), SkillRegistry.BASE_PICKPOCKET_XP * (items.size()));
            Dispatcher.sendToServer(xpToSkill);
//            PacketDistributor.SERVER.noArg().send(xpToSkill);
        }

//        final HandlePickpocket sendToClient = new HandlePickpocket(data.entity);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<HandlePickpocket> context) {
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}