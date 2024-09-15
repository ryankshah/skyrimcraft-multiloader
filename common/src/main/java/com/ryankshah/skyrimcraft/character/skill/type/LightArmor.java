package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Perk;
import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class LightArmor extends Skill
{
//    public LightArmor(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 12;
    }

    @Override
    public String getName() {
        return "Light Armor";
    }

    @Override
    public String getDescription() {
        return "Those trained to use Light Armor make more effective use of Hide, Leather, Elven, Scale and Glass armors.";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(320, 64);
    }

    @Override
    public float getSkillUseMultiplier() {
        return 4f;
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
