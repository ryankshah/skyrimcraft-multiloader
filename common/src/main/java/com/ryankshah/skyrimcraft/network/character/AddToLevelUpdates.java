package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.LevelUpdates;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.util.LevelUpdate;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

public record AddToLevelUpdates(String updateName, int level, int levelUpRenderTime)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "addtolevelupdates");

    public static final StreamCodec<FriendlyByteBuf, AddToLevelUpdates> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            AddToLevelUpdates::updateName,
            ByteBufCodecs.INT,
            AddToLevelUpdates::level,
            ByteBufCodecs.INT,
            AddToLevelUpdates::levelUpRenderTime,
            AddToLevelUpdates::new
    );

    public AddToLevelUpdates(final FriendlyByteBuf buffer) {
        this(buffer.readUtf(), buffer.readInt(), buffer.readInt());
    }

    public AddToLevelUpdates() {
        this("", 0, 0);
    }

    public static void handle(PacketContext<AddToLevelUpdates> context) {
        if(context.side() == Side.CLIENT) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.execute(() -> {
                Player player = minecraft.player;
                player.playSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
                if (context.message().updateName.equals("characterLevel")) {
                    Services.PLATFORM.setLevelUpdates(player, new LevelUpdates(Services.PLATFORM.getLevelUpdates(player).getLevelUpdates() +1));
                    Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastId.NARRATOR_TOGGLE, Component.literal("Level Up"), Component.literal("You have a new attribute point to use!")));
                }
                //TODO: add gui overlay
//                SkyrimGuiOverlay.LEVEL_UPDATES.add(new LevelUpdate(context.message().updateName, context.message().level, context.message().levelUpRenderTime));
            });
        } else {
            ServerPlayer player = context.sender();
            if(context.message().updateName.equals("characterLevel"))
                Services.PLATFORM.setLevelUpdates(player, new LevelUpdates(Services.PLATFORM.getLevelUpdates(player).getLevelUpdates() +1));
            final AddToLevelUpdates updates = new AddToLevelUpdates(context.message().updateName, context.message().level, context.message().levelUpRenderTime);
            Dispatcher.sendToClient(updates, player);
//        PacketDistributor.PLAYER.with((ServerPlayer) context.player().orElseThrow()).send(updates);
        }
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}