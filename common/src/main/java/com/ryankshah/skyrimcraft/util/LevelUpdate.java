package com.ryankshah.skyrimcraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class LevelUpdate
{
    private String updateName;
    private int level;
    private int levelUpRenderTime;

    public static Codec<LevelUpdate> CODEC = RecordCodecBuilder.create(updateInstance -> updateInstance.group(
            Codec.STRING.fieldOf("updateName").forGetter(LevelUpdate::getUpdateName),
            Codec.INT.fieldOf("level").forGetter(LevelUpdate::getLevel),
            Codec.INT.fieldOf("levelUpRenderTime").forGetter(LevelUpdate::getLevelUpRenderTime)
    ).apply(updateInstance, LevelUpdate::new));

    public LevelUpdate(String updateName, int level, int levelUpRenderTime) {
        this.updateName = updateName;
        this.level = level;
        this.levelUpRenderTime = levelUpRenderTime;
    }

    public String getUpdateName() {
        return updateName;
    }

    public int getLevel() {
        return level;
    }

    public int getLevelUpRenderTime() {
        return levelUpRenderTime;
    }

    public void setLevelUpRenderTime(int levelUpRenderTime) {
        this.levelUpRenderTime = levelUpRenderTime;
    }
}