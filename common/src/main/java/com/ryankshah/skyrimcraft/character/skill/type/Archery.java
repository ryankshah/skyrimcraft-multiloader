package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Perk;
import com.ryankshah.skyrimcraft.character.skill.Perks;
import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Archery extends Skill
{
//    public Archery(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 8;
    }

    @Override
    public String getName() {
        return "Archery";
    }

    @Override
    public String getDescription() {
        return "An archer is trained in the use of bows and arrows. The greater the skill, the more deadly the shot.";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(192, 64);
    }

    @Override
    public float getSkillUseMultiplier() {
        return 9.3f;
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
        return List.of(Perks.OVERDRAW, Perks.EAGLE_EYE, Perks.CRITICAL_SHOT, Perks.STEADY_HAND, Perks.POWER_SHOT, Perks.HUNTERS_DISCIPLINE, Perks.RANGER, Perks.QUICK_SHOT, Perks.BULLSEYE);
    }
}
