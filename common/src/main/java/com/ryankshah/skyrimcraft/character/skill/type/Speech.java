package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;import com.ryankshah.skyrimcraft.character.skill.Perk;

public class Speech extends Skill
{
//    public Speech(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 17;
    }

    @Override
    public String getName() {
        return "Speech";
    }

    @Override
    public String getDescription() {
        return "The skill of persuasion can be used to get better prices from merchants, and persuade others to do as you ask.";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(0, 128);
    }

    @Override
    public float getSkillUseMultiplier() {
        return 0.36f;
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
