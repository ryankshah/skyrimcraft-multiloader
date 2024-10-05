package com.ryankshah.skyrimcraft.util;

import com.ryankshah.skyrimcraft.screen.DialogueScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DialogueManager
{
    private static final Map<String, DialogueNode> dialogueNodes = new HashMap<>();
    private static final Map<String, Integer> playerChoices = new HashMap<>(); // Tracks player choices for quest states

    public static class DialogueNode {
        private final String npcName;
        private final String dialogueText;
        private final List<DialogueOption> options;
        private final Map<String, Predicate<Player>> conditions;

        public DialogueNode(String npcName, String dialogueText) {
            this.npcName = npcName;
            this.dialogueText = dialogueText;
            this.options = new ArrayList<>();
            this.conditions = new HashMap<>();
        }

        public DialogueNode addOption(String text, String nextNodeId, Consumer<Player> action) {
            options.add(new DialogueOption(text, nextNodeId, action));
            return this;
        }

        public DialogueNode addConditionalOption(String text, String nextNodeId,
                                                 Predicate<Player> condition, Consumer<Player> action) {
            DialogueOption option = new DialogueOption(text, nextNodeId, action);
            option.setCondition(condition);
            options.add(option);
            return this;
        }
    }

    private static class DialogueOption {
        private final String text;
        private final String nextNodeId;
        private final Consumer<Player> action;
        private Predicate<Player> condition;

        public DialogueOption(String text, String nextNodeId, Consumer<Player> action) {
            this.text = text;
            this.nextNodeId = nextNodeId;
            this.action = action;
            this.condition = player -> true; // Default condition always true
        }

        public void setCondition(Predicate<Player> condition) {
            this.condition = condition;
        }
    }

    // Example of how to start a dialogue
    public static void showDialogue(Player player) {
        DialogueScreen screen = new DialogueScreen("Village Elder", "What brings you to our village?");

        screen.addDialogueOption("Tell me about this place", () -> {
            // Handle this choice
        });

        screen.addDialogueOption("I'm looking for work", () -> {
            // Handle this choice
        });

        screen.addDialogueOption("Goodbye", () -> {
            // Close dialogue
        });

        Minecraft.getInstance().setScreen(screen);
    }

    // Example helper methods for quest logic
    private static boolean hasCompletedQuestObjectives(Player player) {
        // Check if player has killed required monsters, found items, etc.
        return false; // Replace with actual quest completion logic
    }

    private static void giveQuestReward(Player player) {
        // Give player items, experience, etc.
    }

    public static class VillageElderDialogues {
        private static final String QUEST_STATE_KEY = "village_elder_quest";
        private static final String RUINS_INFO_KEY = "ruins_info_revealed";

        public static void showInitialDialogue() {
            DialogueScreen screen = new DialogueScreen("Village Elder", getGreetingText());

            screen.addDialogueOption("Tell me about this village", () -> {
                showVillageInfoDialogue();
            });

            screen.addDialogueOption("I've heard about some ruins nearby...", () -> {
                showRuinsDialogue();
            });

            screen.addDialogueOption("Goodbye", () -> {
                showFarewellDialogue();
            });

            Minecraft.getInstance().setScreen(screen);
        }

        private static void showVillageInfoDialogue() {
            DialogueScreen screen = new DialogueScreen("Village Elder",
                    "*adjusts goggles thoughtfully* Our village has stood here for generations. " +
                            "We're a simple folk, making our living through farming and trade. " +
                            "Though lately, strange occurrences have been troubling our peaceful existence...");

            screen.addDialogueOption("What kind of strange occurrences?", () -> {
                showStrangeEventsDialogue();
            });

            screen.addDialogueOption("Tell me about the village's history", () -> {
                showVillageHistoryDialogue();
            });

            screen.addDialogueOption("Let's talk about something else", () -> {
                showInitialDialogue();
            });

            Minecraft.getInstance().setScreen(screen);
        }

        private static void showStrangeEventsDialogue() {
            DialogueScreen screen = new DialogueScreen("Village Elder",
                    "*lowers voice* Mysterious lights in the northern woods at night. " +
                            "Strange sounds echoing from the ancient ruins. Some villagers claim " +
                            "they've seen old machines moving on their own...");

            screen.addDialogueOption("Tell me more about these ruins", () -> {
                showRuinsDialogue();
            });

            screen.addDialogueOption("Have you seen these machines yourself?", () -> {
                showMachinesDialogue();
            });

            screen.addDialogueOption("Let's discuss something else", () -> {
                showInitialDialogue();
            });

            Minecraft.getInstance().setScreen(screen);
        }

        private static void showVillageHistoryDialogue() {
            DialogueScreen screen = new DialogueScreen("Village Elder",
                    "*eyes light up* Ah, a student of history! Our village was founded by explorers " +
                            "who discovered these lands centuries ago. They were drawn here by the mysterious " +
                            "ruins to the north, though that's a different tale entirely...");

            screen.addDialogueOption("Tell me about the ruins", () -> {
                showRuinsDialogue();
            });

            screen.addDialogueOption("Who were these explorers?", () -> {
                showExplorersDialogue();
            });

            screen.addDialogueOption("Let's talk about something else", () -> {
                showInitialDialogue();
            });

            Minecraft.getInstance().setScreen(screen);
        }

        private static void showRuinsDialogue() {
            DialogueScreen screen = new DialogueScreen("Village Elder",
                    "*peers at you intently* The ruins... they've stood since before our village was founded. " +
                            "Ancient machinery lies dormant within their walls. Or at least, they used to lie dormant... " +
                            "*adjusts goggles nervously*");

            screen.addDialogueOption("Has anyone explored them recently?", () -> {
                showRecentExplorersDialogue();
            });

            screen.addDialogueOption("What kind of machinery?", () -> {
                showMachinesDialogue();
            });

            screen.addDialogueOption("Let's talk about something else", () -> {
                showInitialDialogue();
            });

            Minecraft.getInstance().setScreen(screen);
        }

        private static void showRecentExplorersDialogue() {
            DialogueScreen screen = new DialogueScreen("Village Elder",
                    "Many have ventured in over the years. Most just find old stone and metal. " +
                            "Though lately, with all these strange occurrences, few dare to enter. " +
                            "*leans in closer* Perhaps that's for the best.");

            screen.addDialogueOption("Tell me about the strange occurrences", () -> {
                showStrangeEventsDialogue();
            });

            screen.addDialogueOption("Back to the ruins", () -> {
                showRuinsDialogue();
            });

            screen.addDialogueOption("Let's discuss something else", () -> {
                showInitialDialogue();
            });

            Minecraft.getInstance().setScreen(screen);
        }

        private static void showMachinesDialogue() {
            DialogueScreen screen = new DialogueScreen("Village Elder",
                    "*rubs chin thoughtfully* The machines are unlike anything we've ever seen. " +
                            "Metal constructs, some small as a spider, others tall as a house. They've got " +
                            "markings that glow with a strange light. Most are broken, but lately...");

            screen.addDialogueOption("What's been happening lately?", () -> {
                showStrangeEventsDialogue();
            });

            screen.addDialogueOption("Tell me more about the ruins", () -> {
                showRuinsDialogue();
            });

            screen.addDialogueOption("Let's talk about something else", () -> {
                showInitialDialogue();
            });

            Minecraft.getInstance().setScreen(screen);
        }

        private static void showExplorersDialogue() {
            DialogueScreen screen = new DialogueScreen("Village Elder",
                    "The founders of our village were a brave group. Scholars and adventurers, " +
                            "drawn here by rumors of ancient wonders. *chuckles* Not unlike yourself, " +
                            "asking all these questions.");

            screen.addDialogueOption("Tell me more about the village's history", () -> {
                showVillageHistoryDialogue();
            });

            screen.addDialogueOption("What did they find in the ruins?", () -> {
                showRuinsDialogue();
            });

            screen.addDialogueOption("Let's discuss something else", () -> {
                showInitialDialogue();
            });

            Minecraft.getInstance().setScreen(screen);
        }

        private static void showFarewellDialogue() {
            DialogueScreen screen = new DialogueScreen("Village Elder", getFarewellText());

            screen.addDialogueOption("Goodbye", () -> {
                Minecraft.getInstance().setScreen(null);
            });

            Minecraft.getInstance().setScreen(screen);
        }


        // Helper methods for dialogue text generation
        private static String getGreetingText() {
            return Minecraft.getInstance().level.isNight() ?
                    "*adjusts goggles in the dim light* Ah, a visitor at this late hour? What brings you to our village?" :
                    "*looks up from ancient tome* Welcome, traveler. What brings you to our village?";
        }

        private static String getFarewellText() {
            return Minecraft.getInstance().level.isNight() ?
                    "*returns to studying* Mind the paths after dark, traveler." :
                    "*nods respectfully* Safe travels, friend.";
        }
    }
}