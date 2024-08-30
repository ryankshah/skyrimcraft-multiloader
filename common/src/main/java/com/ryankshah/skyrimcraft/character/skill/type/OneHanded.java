package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class OneHanded extends Skill
{
//    public OneHanded(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 6;
    }

    @Override
    public String getName() {
        return "One-Handed";
    }

    @Override
    public String getDescription() {
        return "The art of combat using one-handed weapons such as daggers, swords, maces, and war axes. Those trained in this skill deliver deadlier blows.";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(64, 64);
    }

    @Override
    public float getSkillUseMultiplier() {
        return 6.3f;
    }

    @Override
    public int getSkillUseOffset() {
        return 0;
    }

    @Override
    public float getSkillImproveMultiplier() {
        return 2f;
    }

    @Override
    public int getSkillImproveOffset() {
        return 0;
    }

    @Override
    public List<Perk> getSkillPerks() {
        return new ArrayList<>();
    }
}
