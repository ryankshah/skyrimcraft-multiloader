package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record UpdateExtraCharacter(ExtraCharacter character)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "updateextracharacter");

    public static final StreamCodec<FriendlyByteBuf, UpdateExtraCharacter> CODEC = StreamCodec.composite(
            ExtraCharacter.STREAM_CODEC,
            UpdateExtraCharacter::character,
            UpdateExtraCharacter::new
    );

    public UpdateExtraCharacter(final FriendlyByteBuf buffer) {
        this(buffer.readJsonWithCodec(ExtraCharacter.CODEC));
    }

    public static void handle(PacketContext<UpdateExtraCharacter> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateExtraCharacter> context) {
        ServerPlayer player = context.sender();
        Services.PLATFORM.setExtraCharacterData(player, context.message().character);
        final UpdateExtraCharacter sendToClient = new UpdateExtraCharacter(Services.PLATFORM.getExtraCharacter(player));
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdateExtraCharacter> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Services.PLATFORM.setExtraCharacterData(minecraft.player, context.message().character);
        });
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}