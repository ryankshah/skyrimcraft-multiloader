package com.ryankshah.skyrimcraft.character.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.character.quest.Quest;
import com.ryankshah.skyrimcraft.character.quest.QuestStep;
import com.ryankshah.skyrimcraft.character.quest.QuestType;
import com.ryankshah.skyrimcraft.network.character.UpdatePlayerQuests;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerQuests
{
    protected List<Quest> quests;
    protected List<Quest> completedQuests;

    public static Codec<PlayerQuests> CODEC = RecordCodecBuilder.create(questsInstance -> questsInstance.group(
            Quest.CODEC.listOf().fieldOf("quests").forGetter(PlayerQuests::getQuests),
            Quest.CODEC.listOf().fieldOf("completedQuests").forGetter(PlayerQuests::getCompletedQuests)
//            ProceduralQuest.CODEC.listOf().fieldOf("proceduralQuests").forGetter(PlayerQuests::getProceduralQuests),
//            UniqueQuest.CODEC.listOf().fieldOf("proceduralQuests").forGetter(PlayerQuests::getProceduralQuests),
//            ProceduralQuest.CODEC.listOf().fieldOf("proceduralQuests").forGetter(PlayerQuests::getCompletedQuests)
    ).apply(questsInstance, PlayerQuests::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerQuests> STREAM_CODEC = StreamCodec.composite(
            Quest.STREAM_CODEC.apply(ByteBufCodecs.list()),
            PlayerQuests::getQuests,
            Quest.STREAM_CODEC.apply(ByteBufCodecs.list()),
            PlayerQuests::getCompletedQuests,
            PlayerQuests::new
    );

    public PlayerQuests() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public PlayerQuests(List<Quest> quests, List<Quest> completedQuests) {
        this.quests = quests;
        this.completedQuests = completedQuests;
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
    }

    public void removeQuest(Quest quest) {
        quests.remove(quest);
    }

    public void updateQuests(Player player) {
        List<Quest> completedQuests = new ArrayList<>();
        for (Quest quest : quests) {
            if (quest.isComplete(player)) {
                quest.rewardPlayer(player);
                completedQuests.add(quest);
            }
        }
        quests.removeAll(completedQuests);
    }

    public static void generateQuest(Player player) {
        PlayerQuests questData = get(player);

        Quest newQuest;
        if (shouldAssignUniqueQuest(player)) {
            newQuest = createUniqueQuest(player);
        } else {
            newQuest = createProceduralQuest(player);
        }

        questData.addQuest(newQuest);
//        player.sendMessage(new StringTextComponent("New Quest: " + newQuest.getTitle()), player.getUUID());
    }

    private static boolean shouldAssignUniqueQuest(Player player) {
        // Determine if a unique quest should be assigned based on player progress
        return false;
    }

    private static Quest createUniqueQuest(Player player) {
        String questID = determineNextUniqueQuestID(player);
        List<QuestStep> steps = new ArrayList<>();
        // Define specific quest steps for this unique quest
        return new Quest("Unique Quest Title", QuestType.UNIQUE.name(), steps, questID);
    }

    private static String determineNextUniqueQuestID(Player player) {
        // Determine the next unique quest ID based on player progress or storyline
        return "uniqueQuestID";
    }

    private static Quest createProceduralQuest(Player player) {
        List<QuestStep> steps = new ArrayList<>();
        // Define dynamically generated steps for the procedural quest
        return new Quest("Procedural Quest Title", QuestType.PROCEDURAL, steps);
    }

    public static void updatePlayerQuests(Player player) {
        PlayerQuests questData = get(player);
        questData.updateQuests(player);
    }

    public List<Quest> getQuests() {
        return new ArrayList<>(quests);
    }

    public List<Quest> getCompletedQuests() {
        return new ArrayList<>(completedQuests);
    }

//    // Utility methods for item handling (used in quest steps)
//    private static int countItemsInInventory(Player player, String itemID) {
//        // Count the number of items in the player's inventory that match the given itemID.
//        return (int) player.inventoryMenu.getItems().stream().filter(stack -> stack.getItem().toString().equals(itemID)).mapToInt(ItemStack::getCount).sum();
//    }
//
//    private static void removeItemsFromInventory(Player player, String itemID, int count) {
//        // Remove the specified number of items from the player's inventory.
//        int remaining = count;
//        for (ItemStack stack : player.inventoryMenu.items) {
//            if (stack.getItem().getRegistryName().toString().equals(itemID)) {
//                int removeCount = Math.min(stack.getCount(), remaining);
//                stack.shrink(removeCount);
//                remaining -= removeCount;
//                if (remaining <= 0) break;
//            }
//        }
//    }

    public static PlayerQuests get(Player player) {
        return Services.PLATFORM.getQuests(player);
    }

    private void syncToSelf(Player owner) {
        syncTo(owner);
    }

    protected void syncTo(Player player) {
        Dispatcher.sendToClient(new UpdatePlayerQuests(this), (ServerPlayer) player);
    }

//    protected void syncTo(PacketDistributor.PacketTarget target) {
//        target.send(new UpdateCharacter(this));
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

        Services.PLATFORM.setQuestData(player, Services.PLATFORM.getQuests(player));
        Dispatcher.sendToClient(new UpdatePlayerQuests(newHandler), (ServerPlayer) player); //.sendToPlayer((ServerPlayer) player, new UpdateCharacter(newHandler));
    }

    public static void playerClone(boolean isWasDeath, Player player, Player oldPlayer) {
        if(!isWasDeath)
            return;
//        oldPlayer.revive();
        PlayerQuests oldHandler = PlayerQuests.get(oldPlayer);
        Services.PLATFORM.setQuestData(player, oldHandler);
        PlayerQuests newHandler = PlayerQuests.get(player);
        Dispatcher.sendToClient(new UpdatePlayerQuests(newHandler), (ServerPlayer) player);
    }
}