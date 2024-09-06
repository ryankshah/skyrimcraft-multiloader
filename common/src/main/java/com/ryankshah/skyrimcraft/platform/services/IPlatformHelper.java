package com.ryankshah.skyrimcraft.platform.services;

import com.ryankshah.skyrimcraft.character.attachment.*;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    Character getCharacter(Player player);
    void setCharacterData(Player player, Character characterData);

    ExtraCharacter getExtraCharacter(Player player);
    void setExtraCharacterData(Player player, ExtraCharacter characterData);

    LevelUpdates getLevelUpdates(Player player);
    void setLevelUpdates(Player player, LevelUpdates levelUpdates);

    StatIncreases getStatIncreases(Player player);
    void setStatIncreases(Player player, StatIncreases statIncreases);

    PlayerQuests getQuests(Player player);
    void setQuestData(Player player, PlayerQuests playerQuests);

    boolean doesEntityHavePersistentData(LivingEntity entity, String id);
    void setEntityPersistentData(LivingEntity entity, String id, long value);
}