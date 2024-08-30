package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;

public record AddToCompassFeatures(String uuid, ResourceLocation location, BlockPos blockPos)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "addtocompassfeatures");
    public static final StreamCodec<FriendlyByteBuf, AddToCompassFeatures> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            AddToCompassFeatures::uuid,
            ResourceLocation.STREAM_CODEC,
            AddToCompassFeatures::location,
            BlockPos.STREAM_CODEC,
            AddToCompassFeatures::blockPos,
            AddToCompassFeatures::new
    );

    public AddToCompassFeatures(final FriendlyByteBuf buffer) {
        this(buffer.readUtf(), buffer.readResourceLocation(), buffer.readBlockPos());
    }

    public static void handle(PacketContext<AddToCompassFeatures> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }
    public static void handleServer(PacketContext<AddToCompassFeatures> context) {
        ServerPlayer player = (ServerPlayer) context.sender();
        Character character = Character.get(player);
        AddToCompassFeatures data = context.message();
        character.addCompassFeature(new CompassFeature(data.uuid, TagKey.create(Registries.STRUCTURE, data.location), data.blockPos));

        final AddToCompassFeatures sendToClient = new AddToCompassFeatures(data.uuid, data.location, data.blockPos);
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<AddToCompassFeatures> context) {
        Minecraft minecraft = Minecraft.getInstance();
        AddToCompassFeatures data = context.message();
        minecraft.execute(() -> {
            if(Minecraft.getInstance().player != null) {
                Player player = Minecraft.getInstance().player;
                Character character = Character.get(player);
                character.addCompassFeature(new CompassFeature(data.uuid, TagKey.create(Registries.STRUCTURE, data.location), data.blockPos));
            }
        });
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}

