package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Block extends Skill
{
//    public Block(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 9;
    }

    @Override
    public String getName() {
        return "Block";
    }

    @Override
    public String getDescription() {
        return "The art of blocking an enemy's blows with a shield or weapon. Blocking reduces the damage and staggering from physical attacks.";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(384,0);
    }

    @Override
    public float getSkillUseMultiplier() {
        return 8.1f;
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
