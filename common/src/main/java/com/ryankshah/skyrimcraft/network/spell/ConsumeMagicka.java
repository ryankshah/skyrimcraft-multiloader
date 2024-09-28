package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
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

public record ConsumeMagicka(float amount)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "consumemagicka");

    public static final StreamCodec<FriendlyByteBuf, ConsumeMagicka> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            ConsumeMagicka::amount,
            ConsumeMagicka::new
    );

    public ConsumeMagicka(final FriendlyByteBuf buffer) {
        this(buffer.readFloat());
    }

    public static void handle(PacketContext<ConsumeMagicka> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<ConsumeMagicka> context) {
        ServerPlayer player = context.sender();
        Character character = Character.get(player);
        ConsumeMagicka data = context.message();

        float currentMagicka = character.getMagicka();
        float newMagicka = Math.max(0, currentMagicka - data.amount);
        character.setMagicka(newMagicka);

        final ConsumeMagicka sendToClient = new ConsumeMagicka(newMagicka);
        Dispatcher.sendToClient(sendToClient, player);
    }

    public static void handleClient(PacketContext<ConsumeMagicka> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Player player = Minecraft.getInstance().player;
            Character character = Character.get(player);
            character.setMagicka(context.message().amount);
        });
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}