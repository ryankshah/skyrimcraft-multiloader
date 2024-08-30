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

public record UpdateMagicka(float magicka, float maxMagicka, float magickaRegenModifier)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "updatemagicka");

    public static final StreamCodec<FriendlyByteBuf, UpdateMagicka> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            UpdateMagicka::magicka,
            ByteBufCodecs.FLOAT,
            UpdateMagicka::maxMagicka,
            ByteBufCodecs.FLOAT,
            UpdateMagicka::magickaRegenModifier,
            UpdateMagicka::new
    );

    public UpdateMagicka(final FriendlyByteBuf buffer) {
        this(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
    }

    public static void handle(PacketContext<UpdateMagicka> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateMagicka> context) {
        ServerPlayer player = context.sender();
        Character character = Character.get(player);
        UpdateMagicka data = context.message();

        character.setMaxMagicka(data.maxMagicka);

        float curMagicka = data.magicka;
        float maxMagicka = character.getMaxMagicka();

        if(curMagicka <= 0.0f)
            curMagicka = 0.0f;
        if(curMagicka >= maxMagicka)
            curMagicka = maxMagicka;

        character.setMagicka(curMagicka);
        character.setMagickaRegenModifier(data.magickaRegenModifier);

        final UpdateMagicka sendToClient = new UpdateMagicka(curMagicka, data.maxMagicka, data.magickaRegenModifier);
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdateMagicka> context) {
        Minecraft minecraft = Minecraft.getInstance();
        UpdateMagicka data = context.message();
        minecraft.execute(() -> {
            Player player = Minecraft.getInstance().player;
            Character character = Character.get(player);

            character.setMagicka(data.magicka);
            character.setMaxMagicka(data.maxMagicka);
            character.setMagickaRegenModifier(data.magickaRegenModifier);
        });
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}