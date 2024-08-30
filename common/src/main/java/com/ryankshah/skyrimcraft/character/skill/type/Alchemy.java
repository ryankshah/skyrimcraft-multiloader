package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Alchemy extends Skill
{
//    public Alchemy(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 16;
    }

    @Override
    public String getName() {
        return "Alchemy";
    }

    @Override
    public String getDescription() {
        return "An alchemist can create magical potions and deadly poisons.";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(64, 128);
    }

    @Override
    public float getSkillUseMultiplier() {
        return 0.75f;
    }

    @Override
    public int getSkillUseOffset() {
        return 0;
    }

    @Override
    public float getSkillImproveMultiplier() {
        return 1.6f;
    }

    @Override
    public int getSkillImproveOffset() {
        return 65;
    }

    @Override
    public List<Perk> getSkillPerks() {
        return new ArrayList<>();
    }
}
