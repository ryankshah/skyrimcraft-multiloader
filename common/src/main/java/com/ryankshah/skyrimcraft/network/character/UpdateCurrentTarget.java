package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record UpdateCurrentTarget(int target)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "updatecurrenttarget");

    public static final StreamCodec<FriendlyByteBuf, UpdateCurrentTarget> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            UpdateCurrentTarget::target,
            UpdateCurrentTarget::new
    );

    public UpdateCurrentTarget(final FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public static void handle(PacketContext<UpdateCurrentTarget> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateCurrentTarget> context) {
        ServerPlayer player = (ServerPlayer) context.sender();
        Character character = Character.get(player);

        character.addTarget(context.message().target);

        final UpdateCurrentTarget sendToClient = new UpdateCurrentTarget(context.message().target);
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdateCurrentTarget> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Player player = Minecraft.getInstance().player;
            Character character = Character.get(player);

            character.addTarget(context.message().target);
        });
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}