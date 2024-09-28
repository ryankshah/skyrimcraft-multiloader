package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.Constants;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public record UpdateTelekinesisItem(int entityId, Vector3f position)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "update_telekinesis_item");

    public static final StreamCodec<FriendlyByteBuf, UpdateTelekinesisItem> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            UpdateTelekinesisItem::entityId,
            ByteBufCodecs.VECTOR3F,
            UpdateTelekinesisItem::position,
            UpdateTelekinesisItem::new
    );

    public UpdateTelekinesisItem(final FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readVector3f());
    }

    public static void handle(PacketContext<UpdateTelekinesisItem> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateTelekinesisItem> context) {
        ServerPlayer player = context.sender();
        UpdateTelekinesisItem data = context.message();
        Entity entity = player.level().getEntity(data.entityId);
        if (entity instanceof ItemEntity) {
            entity.setPos(data.position.x, data.position.y, data.position.z);
            Dispatcher.sendToAllClients(data, player.level().getServer());
        }
    }

    public static void handleClient(PacketContext<UpdateTelekinesisItem> context) {
        Minecraft minecraft = Minecraft.getInstance();
        UpdateTelekinesisItem data = context.message();
        minecraft.execute(() -> {
            Entity entity = minecraft.level.getEntity(data.entityId);
            if (entity instanceof ItemEntity) {
                entity.setPos(data.position.x, data.position.y, data.position.z);
            }
        });
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}