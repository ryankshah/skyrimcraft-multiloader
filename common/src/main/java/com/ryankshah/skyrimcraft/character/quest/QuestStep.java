package com.ryankshah.skyrimcraft.character.quest;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;

public record QuestStep(String description) {
    public static Codec<QuestStep> CODEC = RecordCodecBuilder.create(pc -> pc.group(
            Codec.STRING.fieldOf("description").forGetter(QuestStep::description)
    ).apply(pc, QuestStep::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, QuestStep> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            QuestStep::description,
            QuestStep::new
    );

    public boolean isComplete(Player player) {
        return false;
    }

    public void rewardPlayer(Player player) {
    }
}