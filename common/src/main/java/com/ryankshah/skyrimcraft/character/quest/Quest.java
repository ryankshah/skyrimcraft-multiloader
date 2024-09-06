package com.ryankshah.skyrimcraft.character.quest;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Quest
{
    public static Codec<Quest> CODEC = RecordCodecBuilder.create(questInstance -> questInstance.group(
            Codec.STRING.fieldOf("title").forGetter(Quest::getTitle),
            Codec.STRING.fieldOf("type").forGetter(Quest::getTypeString),
            QuestStep.CODEC.listOf().fieldOf("steps").forGetter(Quest::getSteps),
            Codec.STRING.fieldOf("questID").forGetter(Quest::getQuestID)
    ).apply(questInstance, Quest::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Quest> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            Quest::getTitle,
            ByteBufCodecs.STRING_UTF8,
            Quest::getTypeString,
            QuestStep.STREAM_CODEC.apply(ByteBufCodecs.list()),
            Quest::getSteps,
            ByteBufCodecs.STRING_UTF8,
            Quest::getQuestID,
            Quest::new
    );

    private final String title;
    private final QuestType type;
    private final List<QuestStep> steps;
    private int currentStep = 0;

    // Optional, only used for unique quests
    private final String questID;

    public Quest(String title, QuestType type, List<QuestStep> steps) {
        this(title, type.name(), steps, UUID.randomUUID().toString());
    }

    public Quest(String title, String type, List<QuestStep> steps, String questID) {
        this.title = title;
        this.type = QuestType.valueOf(type);
        this.steps = steps;
        this.questID = questID;
    }

    public String getTitle() {
        return title;
    }

    public QuestType getType() {
        return type;
    }

    public String getTypeString() {
        return type.name();
    }

    public String getQuestID() {
        return questID;
    }

    public List<QuestStep> getSteps() {
        return steps;
    }

    public boolean isComplete(Player player) {
        if (currentStep >= steps.size()) {
            return true;
        }

        QuestStep step = steps.get(currentStep);
        if (step.isComplete(player)) {
            step.rewardPlayer(player);
            currentStep++;
        }

        return currentStep >= steps.size();
    }

    public void rewardPlayer(Player player) {
        if (isComplete(player)) {
//            player.sendMessage(new StringTextComponent("Quest Complete: " + getTitle()), player.getUUID());
            // Final reward for completing the quest
            player.giveExperiencePoints(1000);
        }
    }
}
