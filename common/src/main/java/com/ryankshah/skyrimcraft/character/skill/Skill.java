package com.ryankshah.skyrimcraft.character.skill;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.util.CodecUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;

import java.util.AbstractMap;
import java.util.List;

public abstract class Skill
{
    private Player player;
    private int identifier;
    private String name;
    private String description;
    private int level;
    private int totalXp;
    private float xpProgress;

    private float skillUseMultiplier;
    public int skillUseOffset;
    public float skillImproveMultiplier;
    public int skillImproveOffset;

    protected List<Perk> skillPerks;

    public Skill() {
        this.identifier = getID();
        this.name = getName();
        this.description = getDescription();
        this.level = getDefaultLevel();
        this.totalXp = getTotalXp();
        this.xpProgress = getXpProgress();
        this.skillUseMultiplier = getSkillUseMultiplier();
        this.skillUseOffset = getSkillUseOffset();
        this.skillImproveMultiplier = getSkillImproveMultiplier();
        this.skillImproveOffset = getSkillImproveOffset();
        this.skillPerks = getSkillPerks();
    }

    public abstract AbstractMap.SimpleEntry<Integer, Integer> getIconUV();

    public Skill(int identifier, String name, String description, int baseLevel, float skillUseMultiplier, int skillUseOffset, float skillImproveMultiplier, int skillImproveOffset, List<Perk> perks) {
        this(identifier, name, description, baseLevel, 0, 0, skillUseMultiplier, skillUseOffset, skillImproveMultiplier, skillImproveOffset, perks);
    }

    public Skill(int identifier, String name, String description, int level, int totalXp, float xpProgress, float skillUseMultiplier, int skillUseOffset, float skillImproveMultiplier, int skillImproveOffset, List<Perk> perks) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.level = level;
        this.totalXp = totalXp;
        this.xpProgress = xpProgress;
        this.skillUseMultiplier = skillUseMultiplier;
        this.skillUseOffset = skillUseOffset;
        this.skillImproveMultiplier = skillImproveMultiplier;
        this.skillImproveOffset = skillImproveOffset;
        this.skillPerks = perks;
    }

    // Dummy constructor
    public Skill(Skill skill) {
        this(skill.identifier, skill.name, skill.description, skill.level, skill.totalXp, skill.xpProgress, skill.skillUseMultiplier, skill.skillUseOffset, skill.skillImproveMultiplier, skill.skillImproveOffset, skill.skillPerks);
    }

    public int getDefaultLevel() {
        return 15;
    }

    /**
     * Get the ID of the skill
     *
     * @return skill ID
     */
    public int getID() {
        return identifier;
    }

    /**
     * Get the skill instance
     *
     * @return ISkill instance
     */
    public Skill getSkill() {
        return this;
    }

    /**
     * Get the name of the skill
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the description of the skill
     *
     * @return name
     */
    public abstract String getDescription();

    /**
     * Set the player who the skill instance belongs to
     *
     * @param playerEntity
     */
    public void setPlayer(Player playerEntity) {
        this.player = playerEntity;
    }

    /**
     * Get the player entity who has the skill
     * @return {@link Player}
     */
    public Player getPlayer() {
        return this.player;
    }

    public abstract float getSkillImproveMultiplier();

    public abstract float getSkillUseMultiplier();

    public int getIdentifier() {
        return identifier;
    }

    public abstract int getSkillImproveOffset();

    public abstract int getSkillUseOffset();

    ///
    /// EXPERIENCE-RELATED FIELDS
    ///

    public int getLevel() { return this.level; }

    public int getTotalXp() {
        return this.totalXp;
    }

    public float getXpProgress() { return this.xpProgress; }

    public abstract List<Perk> getSkillPerks();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("id: ").append(identifier).append(", ");
        sb.append("name: ").append(name).append(", ");
        sb.append("description: ").append(description).append(", ");
        sb.append("level: ").append(level).append(", ");
        sb.append("totalXp: ").append(totalXp).append(", ");
        sb.append("xpProgress: ").append(xpProgress).append(", ");
        sb.append("skillUseMultiplier: ").append(skillUseMultiplier).append(", ");
        sb.append("skillUseOffset: ").append(skillUseOffset).append(", ");
        sb.append("skillImproveMultiplier: ").append(skillImproveMultiplier).append(", ");
        sb.append("skillImproveOffset: ").append(skillImproveOffset);
        sb.append("]");
        return sb.toString();
    }
}