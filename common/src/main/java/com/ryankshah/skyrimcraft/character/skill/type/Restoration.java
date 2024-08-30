package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Restoration extends Skill
{
//    public Restoration(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 4;
    }

    @Override
    public String getName() {
        return "Restoration";
    }

    @Override
    public String getDescription() {
        return "The School of Restoration involves control over life forces. This skill makes it easier to cast spells like Healing, Turn Undead, and magical Wards.";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(256,0);
    }

    @Override
    public float getSkillUseMultiplier() {
        return 2.1f;
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
