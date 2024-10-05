package com.ryankshah.skyrimcraft.character.skill.type;

import com.ryankshah.skyrimcraft.character.skill.Perk;
import com.ryankshah.skyrimcraft.character.skill.Perks;
import com.ryankshah.skyrimcraft.character.skill.Skill;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Conjuration extends Skill
{
//    public Conjuration(int id, String name) {
//        super(id, name);
//    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public String getName() {
        return "Conjuration";
    }

    @Override
    public String getDescription() {
        return "The School of Conjuration governs raising the dead or summoning creations from Oblivion. This skill makes it easier to cast these spells as well as Soul Trap and bindings.";
    }

    @Override
    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        return new AbstractMap.SimpleEntry<>(64,0);
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
        return List.of(Perks.NOVICE_CONJURATION, Perks.APPRENTICE_CONJURATION, Perks.SUMMONER, Perks.NECROMANCY, Perks.ATROMANCY,
                Perks.ADEPT_CONJURATION, Perks.DARK_SOULS, Perks.EXPERT_CONJURATION, Perks.ELEMENTAL_POTENCY, Perks.TWIN_SOULS, Perks.MASTER_CONJURATION);
    }
}
