package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.character.attachment.LevelUpdates;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record UpdateLevelUpdates(LevelUpdates levelUpdates)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "updatelevelupdates");

    public static final StreamCodec<FriendlyByteBuf, UpdateLevelUpdates> CODEC = StreamCodec.composite(
            LevelUpdates.STREAM_CODEC,
            UpdateLevelUpdates::levelUpdates,
            UpdateLevelUpdates::new
    );

    public UpdateLevelUpdates(final FriendlyByteBuf buffer) {
        this(buffer.readJsonWithCodec(LevelUpdates.CODEC));
    }

    public static void handle(PacketContext<UpdateLevelUpdates> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateLevelUpdates> context) {
        ServerPlayer player = context.sender();
        Services.PLATFORM.setLevelUpdates(player, context.message().levelUpdates);

        final UpdateLevelUpdates sendToClient = new UpdateLevelUpdates(context.message().levelUpdates);
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdateLevelUpdates> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Services.PLATFORM.setLevelUpdates(minecraft.player, context.message().levelUpdates);
        });
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}