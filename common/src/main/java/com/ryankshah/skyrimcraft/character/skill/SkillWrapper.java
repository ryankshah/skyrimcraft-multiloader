package com.ryankshah.skyrimcraft.character.skill;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.util.CodecUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SkillWrapper
{
    public static Codec<SkillWrapper> CODEC = RecordCodecBuilder.create(skill -> skill.group(
        SkillRegistry.SKILLS_REGISTRY.byNameCodec().fieldOf("skill").forGetter(SkillWrapper::getSkill),
        Codec.INT.fieldOf("identifier").forGetter(SkillWrapper::getID),
        Codec.INT.fieldOf("level").forGetter(SkillWrapper::getLevel),
        Codec.INT.fieldOf("totalXp").forGetter(SkillWrapper::getTotalXp),
        Codec.FLOAT.fieldOf("xpProgress").forGetter(SkillWrapper::getXpProgress),
        Codec.FLOAT.fieldOf("skillUseMultiplier").forGetter(SkillWrapper::getSkillUseMultiplier),
        Codec.INT.fieldOf("skillUseOffset").forGetter(SkillWrapper::getSkillUseOffset),
        Codec.FLOAT.fieldOf("skillImproveMultiplier").forGetter(SkillWrapper::getSkillImproveMultiplier),
        Codec.INT.fieldOf("skillImproveOffset").forGetter(SkillWrapper::getSkillImproveOffset),
            Perk.CODEC.listOf().fieldOf("perks").forGetter(SkillWrapper::getSkillPerks)
    ).apply(skill, SkillWrapper::new));

    public static StreamCodec<RegistryFriendlyByteBuf, SkillWrapper> STREAM_CODEC = CodecUtils.composite10(
            ByteBufCodecs.fromCodec(SkillRegistry.SKILLS_REGISTRY.byNameCodec()),
            SkillWrapper::getSkill,
            ByteBufCodecs.INT,
            SkillWrapper::getID,
            ByteBufCodecs.INT,
            SkillWrapper::getLevel,
            ByteBufCodecs.INT,
            SkillWrapper::getTotalXp,
            ByteBufCodecs.FLOAT,
            SkillWrapper::getXpProgress,
            ByteBufCodecs.FLOAT,
            SkillWrapper::getSkillUseMultiplier,
            ByteBufCodecs.INT,
            SkillWrapper::getSkillUseOffset,
            ByteBufCodecs.FLOAT,
            SkillWrapper::getSkillImproveMultiplier,
            ByteBufCodecs.INT,
            SkillWrapper::getSkillImproveOffset,
            Perk.STREAM_CODEC.apply(ByteBufCodecs.list()),
            SkillWrapper::getSkillPerks,
            SkillWrapper::new
    );

    protected Skill skill;
    protected List<Perk> perks;
    private int identifier;
    protected int level, totalXp, skillUseOffset, skillImproveOffset;
    protected float xpProgress, skillUseMultiplier, skillImproveMultiplier;

    public SkillWrapper(Skill skill, int ID, int level, int totalXp, float xpProgress, float useMultiplier, int useOffset, float improveMultiplier, int improveOffset, List<Perk> perks) {
        this.skill = skill;
        this.identifier = ID;
        this.level = level;
        this.totalXp = totalXp;
        this.xpProgress = xpProgress;
        this.skillUseMultiplier = useMultiplier;
        this.skillUseOffset = useOffset;
        this.skillImproveMultiplier = improveMultiplier;
        this.skillImproveOffset = improveOffset;
        this.perks = new ArrayList<>(perks);
    }

    public SkillWrapper(Skill skill) {
        this.skill = skill;
    }

    public SkillWrapper() {
    }

    public Skill getSkill() {
        return skill;
    }

    public boolean isPerkUnlocked(Perk perk) {
        return perks.stream().filter( p -> p.getName().equals(perk.getName())).anyMatch(Perk::isUnlocked);
    }

    public void unlockPerk(Perk perk) {
        if(!isPerkUnlocked(perk)) {
            perks.stream().filter(p -> p.getName().equals(perk.getName())).findFirst().get().unlock();
        }
    }

    public int getID() {
        return skill.getID();
    }

    public String getName() {
        return skill.getName();
    }

    public String getDescription() {
        return skill.getDescription();
    }

    public int getLevel() {
        return level;
    }

    public int getSkillUseOffset() {
        return skillUseOffset;
    }

    public int getSkillImproveOffset() {
        return skillImproveOffset;
    }

    public float getSkillUseMultiplier() {
        return skillUseMultiplier;
    }

    public float getSkillImproveMultiplier() {
        return skillImproveMultiplier;
    }

    public void setLevel(int level) { this.level = level; }

    public int getTotalXp() {
        return this.totalXp;
    }

    public float getXpProgress() { return this.xpProgress; }

    private void giveXpLevels(int levels) {
        this.level += levels;
        if (this.level < 0) {
            this.level = 0;
            this.totalXp = 0;
        }
    }

    // xp progress calculation taken from https://en.uesp.net/wiki/Skyrim:Leveling
    public SkillWrapper giveExperiencePoints(int baseXp) {
        // full calculation: `Skill Use Mult * (base XP * skill specific multipliers) + Skill Use Offset` -- TODO: add in skill specific multipliers
        // minecraft progress calc : (float)amount / (float)this.getXpNeededForNextLevel();
        float xpToAdd = skillUseMultiplier * (baseXp) + skillUseOffset;
        this.xpProgress += xpToAdd / (float)this.getXpNeededForNextLevel();
        this.totalXp = (int)clamp(this.totalXp + xpToAdd, 0, Integer.MAX_VALUE);

        if(xpProgress < 0.0F) {
            float f = xpProgress * (float)this.getXpNeededForNextLevel();
            if (level > 0) {
                this.giveXpLevels(-1);
                xpProgress = 1.0F + f / (float)this.getXpNeededForNextLevel();
            } else {
                giveXpLevels(-1);
                xpProgress = 0.0F;
            }
        }

        if(xpProgress >= 1.0F) {
            xpProgress = (xpProgress - 1.0F) * (float)this.getXpNeededForNextLevel();
            this.giveXpLevels(1);
            xpProgress /= (float)this.getXpNeededForNextLevel();
        }
        return this;
    }

    // Taken from https://en.uesp.net/wiki/Skyrim:Leveling
    public double getXpNeededForNextLevel() {
        return level == 0 ? 0 : skillImproveMultiplier * Math.pow((level), 1.95) + skillImproveOffset;
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public List<Perk> getSkillPerks() {
        return new ArrayList<>(skill.getSkillPerks());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("name: ").append(getName()).append(", ");
        sb.append("description: ").append(getDescription()).append(", ");
        sb.append("level: ").append(level).append(", ");
        sb.append("totalXp: ").append(totalXp).append(", ");
        sb.append("xpProgress: ").append(xpProgress).append(", ");
        sb.append("skillUseMultiplier: ").append(skillUseMultiplier).append(", ");
        sb.append("skillUseOffset: ").append(skillUseOffset).append(", ");
        sb.append("skillImproveMultiplier: ").append(skillImproveMultiplier).append(", ");
        sb.append("skillImproveOffset: ").append(skillImproveOffset).append(", ");
        sb.append("perks: ").append(perks.toString());
        sb.append("]");
        return sb.toString();
    }
}
