package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Perk;
import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class HeavyArmor extends Skill
{
//    public HeavyArmor(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 11;
    }

    @Override
    public String getName() {
        return "Heavy Armor";
    }

    @Override
    public String getDescription() {
        return "Those trained to use Heavy Armor make more efficient use of Iron, Steel, Dwarven, Orcish, Ebony, and Daedric armors.";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(128, 64);
    }

    @Override
    public float getSkillUseMultiplier() {
        return 3.8f;
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
