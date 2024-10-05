package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Perk;
import com.ryankshah.skyrimcraft.character.skill.Perks;
import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Alteration extends Skill
{
//    public Alteration(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public String getName() {
        return "Alteration";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(0,0);
    }

    @Override
    public String getDescription() {
        return "The School of Alteration involves the manipulation of the physical world and its natural properties. This skill makes it easier to cast spells like Waterbreathing, magical protection, and Paralysis.";
    }

    @Override
    public float getSkillUseMultiplier() {
        return 3f;
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
        return List.of(Perks.NOVICE_ALTERATION, Perks.APPRENTICE_ALTERATION, Perks.MAGE_ARMOR, Perks.MAGIC_RESISTANCE, Perks.ADEPT_ALTERATION, Perks.STABILITY, Perks.EXPERT_ALTERATION, Perks.ATRONACH, Perks.MASTER_ALTERATION);
    }
}
