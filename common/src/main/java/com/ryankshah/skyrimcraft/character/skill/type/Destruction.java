package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Destruction extends Skill
{
//    public Destruction(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public String getName() {
        return "Destruction";
    }

    @Override
    public String getDescription() {
        return "The School of Destruction involves the harnessing the energies of fire, frost and shock. This skill makes it easier to cast spells like Fireball, Ice Spike, and Lightning Bolt.";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(128,0);
    }

    @Override
    public float getSkillUseMultiplier() {
        return 1.35f;
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
