package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Lockpicking extends Skill
{
//    public Lockpicking(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 14;
    }

    @Override
    public String getName() {
        return "Lockpicking";
    }

    @Override
    public String getDescription() {
        return "The art of lockpicking is used to open locked doors and containers faster and with fewer broken lockpicks.";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(384, 64);
    }

    @Override
    public float getSkillUseMultiplier() {
        return 45f;
    }

    @Override
    public int getSkillUseOffset() {
        return 10;
    }

    @Override
    public float getSkillImproveMultiplier() {
        return 0.25f;
    }

    @Override
    public int getSkillImproveOffset() {
        return 300;
    }

    @Override
    public List<Perk> getSkillPerks() {
        return new ArrayList<>();
    }
}
