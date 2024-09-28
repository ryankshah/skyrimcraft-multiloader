package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.Heightmap;

public record FastTravel(BlockPos blockPos)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "fasttravel");

    public static final StreamCodec<FriendlyByteBuf, FastTravel> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            FastTravel::blockPos,
            FastTravel::new
    );

    public FastTravel(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public static void handle(PacketContext<FastTravel> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<FastTravel> context) {
        ServerPlayer player = (ServerPlayer) context.sender();
        BlockPos pos = context.message().blockPos();
        int y = player.level().getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());
        player.teleportTo(pos.getX(), y, pos.getZ());
    }

    public static void handleClient(PacketContext<FastTravel> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
        });
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}

