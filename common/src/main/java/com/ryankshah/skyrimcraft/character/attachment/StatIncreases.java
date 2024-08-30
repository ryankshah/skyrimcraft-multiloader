package com.ryankshah.skyrimcraft.character.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.network.character.UpdateStatIncreases;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class StatIncreases
{
    public static Codec<StatIncreases> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("healthIncrease").forGetter(StatIncreases::getHealthIncrease),
            Codec.INT.fieldOf("magickaIncrease").forGetter(StatIncreases::getMagickaIncrease),
            Codec.INT.fieldOf("staminaIncrease").forGetter(StatIncreases::getStaminaIncrease)
    ).apply(instance, StatIncreases::new));

    public static StreamCodec<FriendlyByteBuf, StatIncreases> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            StatIncreases::getHealthIncrease,
            ByteBufCodecs.INT,
            StatIncreases::getMagickaIncrease,
            ByteBufCodecs.INT,
            StatIncreases::getStaminaIncrease,
            StatIncreases::new
    );

    protected int healthIncrease, magickaIncrease, staminaIncrease;

    public StatIncreases() {
        this(0, 0, 0);
    }

    public StatIncreases(int healthIncrease, int magickaIncrease, int staminaIncrease) {
        this.healthIncrease = healthIncrease;
        this.magickaIncrease = magickaIncrease;
        this.staminaIncrease = staminaIncrease;
    }

    public int getHealthIncrease() {
        return healthIncrease;
    }

    public int getMagickaIncrease() {
        return magickaIncrease;
    }

    public int getStaminaIncrease() {
        return staminaIncrease;
    }

    public void increaseHealth() {
        this.healthIncrease++;
    }

    public void increaseMagicka() {
        this.magickaIncrease++;
    }

    public void increaseStamina() {
        this.staminaIncrease++;
    }

    public static StatIncreases get(Player player) {
        return Services.PLATFORM.getStatIncreases(player);
    }

    private void syncToSelf(Player owner) {
        syncTo(owner);
    }

    protected void syncTo(Player player) {
        Dispatcher.sendToClient(new UpdateStatIncreases(this), (ServerPlayer) player);
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

        Services.PLATFORM.setStatIncreases(player, Services.PLATFORM.getStatIncreases(player));
        Dispatcher.sendToClient(new UpdateStatIncreases(newHandler), (ServerPlayer) player); //.sendToPlayer((ServerPlayer) player, new UpdateCharacter(newHandler));
    }

    public static void playerClone(boolean isWasDeath, Player player, Player oldPlayer) {
        if(!isWasDeath)
            return;
//        oldPlayer.revive();
        StatIncreases oldHandler = StatIncreases.get(oldPlayer);
        Services.PLATFORM.setStatIncreases(player, oldHandler);
        StatIncreases newHandler = StatIncreases.get(player);
        Dispatcher.sendToClient(new UpdateStatIncreases(newHandler), (ServerPlayer) player);
    }
}
