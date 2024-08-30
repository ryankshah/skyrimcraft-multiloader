package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record UpdateCharacter(Character character)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "updatecharacter");

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateCharacter> CODEC = StreamCodec.composite(
            Character.STREAM_CODEC,
            UpdateCharacter::character,
            UpdateCharacter::new
    );

    public UpdateCharacter(final FriendlyByteBuf buffer) {
        this(buffer.readJsonWithCodec(Character.CODEC));
    }

    public static void handle(PacketContext<UpdateCharacter> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateCharacter> context) {
        ServerPlayer player = context.sender();
        Services.PLATFORM.setCharacterData(player, context.message().character);
        final UpdateCharacter sendToClient = new UpdateCharacter(Services.PLATFORM.getCharacter(player));
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdateCharacter> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Services.PLATFORM.setCharacterData(minecraft.player, context.message().character);
        });
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}