package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.screen.CharacterCreationScreen;
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
import net.minecraft.world.entity.player.Player;

public record OpenCharacterCreationScreen(boolean hasSetup)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "opencharactercreationscreen");

    public static final StreamCodec<FriendlyByteBuf, OpenCharacterCreationScreen> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            OpenCharacterCreationScreen::hasSetup,
            OpenCharacterCreationScreen::new
    );

    public OpenCharacterCreationScreen(final FriendlyByteBuf buffer) {
        this(buffer.readBoolean());
    }

    public static void handle(PacketContext<OpenCharacterCreationScreen> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<OpenCharacterCreationScreen> context) {
        ServerPlayer player = context.sender();
        Character character = Character.get(player);

        character.setHasSetup(true);

        final OpenCharacterCreationScreen sendToClient = new OpenCharacterCreationScreen(context.message().hasSetup);
        Dispatcher.sendToClient(sendToClient, player);
//      PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<OpenCharacterCreationScreen> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Player player = Minecraft.getInstance().player;
            Character character = Character.get(player);
            character.setHasSetup(true);
            Minecraft.getInstance().setScreen(new CharacterCreationScreen());
        });
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}