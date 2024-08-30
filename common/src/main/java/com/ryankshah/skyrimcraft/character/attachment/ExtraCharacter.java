package com.ryankshah.skyrimcraft.character.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.network.character.UpdateExtraCharacter;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ExtraCharacter
{
    public static Codec<ExtraCharacter> CODEC = RecordCodecBuilder.create(characterInstance -> characterInstance.group(
            Codec.INT.fieldOf("dragonSouls").forGetter(ExtraCharacter::getDragonSouls),
            Codec.BOOL.fieldOf("isVampireAfflicted").forGetter(ExtraCharacter::isVampireAfflicted),
            Codec.BOOL.fieldOf("isVampire").forGetter(ExtraCharacter::isVampire)
    ).apply(characterInstance, ExtraCharacter::new));

    public static StreamCodec<FriendlyByteBuf, ExtraCharacter> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ExtraCharacter::getDragonSouls,
            ByteBufCodecs.BOOL,
            ExtraCharacter::isVampireAfflicted,
            ByteBufCodecs.BOOL,
            ExtraCharacter::isVampire,
            ExtraCharacter::new
    );

    protected int dragonSouls;
    protected boolean isVampireAfflicted, isVampire;

    public ExtraCharacter(int dragonSouls, boolean isVampireAfflicted, boolean isVampire) {
        this.dragonSouls = dragonSouls;
        this.isVampireAfflicted = isVampireAfflicted;
        this.isVampire = isVampire;
    }

    public ExtraCharacter() {
        this(
                0,
                false,
                false
        );
    }

    public int getDragonSouls() {
        return dragonSouls;
    }
    public void setDragonSouls(int amount) {
        this.dragonSouls = amount;
    }
    public void addDragonSoul() {
        this.dragonSouls++;
    }
    public void removeDragonSoul() {
        this.dragonSouls--;
    }

    public boolean isVampireAfflicted() {
        return this.isVampireAfflicted;
    }

    public void setVampireAfflicted() {
        this.isVampireAfflicted = true;
    }

    public boolean isVampire() {
        return this.isVampire;
    }

    public void setVampire() {
        this.isVampire = true;
    }

    public static ExtraCharacter get(Player player) {
        return Services.PLATFORM.getExtraCharacter(player);
    }

    private void syncToSelf(Player owner) {
        syncTo(owner);
    }

    protected void syncTo(Player player) {
        Dispatcher.sendToClient(new UpdateExtraCharacter(this), (ServerPlayer) player);
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

        Services.PLATFORM.setExtraCharacterData(player, Services.PLATFORM.getExtraCharacter(player));
        Dispatcher.sendToClient(new UpdateExtraCharacter(newHandler), (ServerPlayer) player); //.sendToPlayer((ServerPlayer) player, new UpdateCharacter(newHandler));
    }

    public static void playerClone(boolean isWasDeath, Player player, Player oldPlayer) {
        if(!isWasDeath)
            return;
//        oldPlayer.revive();
        ExtraCharacter oldHandler = ExtraCharacter.get(oldPlayer);
        Services.PLATFORM.setExtraCharacterData(player, oldHandler);
        ExtraCharacter newHandler = ExtraCharacter.get(player);
        Dispatcher.sendToClient(new UpdateExtraCharacter(newHandler), (ServerPlayer) player);
    }
}
