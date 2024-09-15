package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Perk;
import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Illusion extends Skill
{
//    public Illusion(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public String getName() {
        return "Illusion";
    }

    @Override
    public String getDescription() {
        return "The School of Illusion involves manipulating the mind of the enemy. This skill makes it easier to cast spells like Fear, Calm, and Invisibility.";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(192,0);
    }

    @Override
    public float getSkillUseMultiplier() {
        return 4.6f;
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
