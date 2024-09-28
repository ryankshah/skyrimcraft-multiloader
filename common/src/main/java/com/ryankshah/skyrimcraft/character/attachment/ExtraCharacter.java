package com.ryankshah.skyrimcraft.character.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.network.character.UpdateExtraCharacter;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.util.Waypoint;
import commonnetwork.api.Dispatcher;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ExtraCharacter
{
    public static Codec<ExtraCharacter> CODEC = RecordCodecBuilder.create(characterInstance -> characterInstance.group(
            Codec.INT.fieldOf("dragonSouls").forGetter(ExtraCharacter::getDragonSouls),
            Codec.BOOL.fieldOf("isVampireAfflicted").forGetter(ExtraCharacter::isVampireAfflicted),
            Codec.BOOL.fieldOf("isVampire").forGetter(ExtraCharacter::isVampire),
            Codec.INT.fieldOf("vampirismInfectionTime").forGetter(ExtraCharacter::getInfectionTime),
            Waypoint.CODEC.listOf().fieldOf("waypoints").forGetter(ExtraCharacter::getWaypoints)
    ).apply(characterInstance, ExtraCharacter::new));

    public static StreamCodec<FriendlyByteBuf, ExtraCharacter> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ExtraCharacter::getDragonSouls,
            ByteBufCodecs.BOOL,
            ExtraCharacter::isVampireAfflicted,
            ByteBufCodecs.BOOL,
            ExtraCharacter::isVampire,
            ByteBufCodecs.INT,
            ExtraCharacter::getInfectionTime,
            Waypoint.STREAM_CODEC.apply(ByteBufCodecs.list()),
            ExtraCharacter::getWaypoints,
            ExtraCharacter::new
    );

    protected int dragonSouls, infectionTime;
    protected boolean isVampireAfflicted, isVampire;
    protected List<Waypoint> waypoints;

    public ExtraCharacter(int dragonSouls, boolean isVampireAfflicted, boolean isVampire, int infectionTime, List<Waypoint> waypoints) {
        this.dragonSouls = dragonSouls;
        this.isVampireAfflicted = isVampireAfflicted;
        this.isVampire = isVampire;
        this.infectionTime = infectionTime;
        this.waypoints = new ArrayList<>(waypoints);
    }

    public ExtraCharacter() {
        this(
                0,
                false,
                false,
                0,
                new ArrayList<>()
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

    public int getInfectionTime() {
        return this.infectionTime;
    }

    public void setInfectionTime(int infectionTime) {
        this.infectionTime = infectionTime;
    }

    public void addToInfectionTime(int amount) {
        this.infectionTime += amount;
    }

    public List<Waypoint> getWaypoints() {
        return this.waypoints;
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
