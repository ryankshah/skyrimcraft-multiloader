package com.ryankshah.skyrimcraft.character.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.network.character.UpdateLevelUpdates;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class LevelUpdates
{
    protected int updates;

    public static Codec<LevelUpdates> CODEC = RecordCodecBuilder.create(characterInstance -> characterInstance.group(
            Codec.INT.fieldOf("levelUpdates").forGetter(LevelUpdates::getLevelUpdates)
    ).apply(characterInstance, LevelUpdates::new));

    public static StreamCodec<FriendlyByteBuf, LevelUpdates> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            LevelUpdates::getLevelUpdates,
            LevelUpdates::new
    );

    public LevelUpdates() {
        this(0);
    }

    public LevelUpdates(int updates) {
        this.updates = updates;
    }

    public int getLevelUpdates() {
        return updates;
    }

    public static LevelUpdates get(Player player) {
        return Services.PLATFORM.getLevelUpdates(player);
    }

    private void syncToSelf(Player owner) {
        syncTo(owner);
    }

    protected void syncTo(Player player) {
        Dispatcher.sendToClient(new UpdateLevelUpdates(this), (ServerPlayer) player);
    }

//    protected void syncTo(PacketDistributor.PacketTarget target)
//    {
//        target.send(new UpdateExtraCharacter(this));
//    }

    public static void entityJoinLevel(Player player) {
        if (player.level().isClientSide)
            return;
        get(player).syncToSelf(player);
    }

    public static void playerJoinWorld(Player player) {
        if (player.level().isClientSide)
            return;
        get(player).syncToSelf(player);
    }

    public static void playerChangedDimension(Player player) {
        if (player.level().isClientSide)
            return;
        get(player).syncToSelf(player);
    }

    public static void playerStartTracking(Player player) {
        if (player.level().isClientSide)
            return;
        get(player).syncToSelf(player);
    }

    public static void playerDeath(Player player) {
        var newHandler = get(player);

        Services.PLATFORM.setLevelUpdates(player, Services.PLATFORM.getLevelUpdates(player));
        Dispatcher.sendToClient(new UpdateLevelUpdates(newHandler), (ServerPlayer) player); //.sendToPlayer((ServerPlayer) player, new UpdateCharacter(newHandler));
    }

    public static void playerClone(boolean isWasDeath, Player player, Player oldPlayer) {
        if(!isWasDeath)
            return;
//        oldPlayer.revive();
        LevelUpdates oldHandler = LevelUpdates.get(oldPlayer);
        Services.PLATFORM.setLevelUpdates(player, oldHandler);
        LevelUpdates newHandler = LevelUpdates.get(player);
        Dispatcher.sendToClient(new UpdateLevelUpdates(newHandler), (ServerPlayer) player);
    }
}
