package com.ryankshah.skyrimcraft.character.skill;

import java.util.ArrayList;
import java.util.List;

public class Perks
{
    //Alchemy
    public static Perk ALCHEMIST = new Perk("Overdraw", "Potions and poisons you make are 20% stronger", 20, List.of(), false);
    public static Perk PHYSICIAN = new Perk("Physician", "Potions you mix that restore Health, Magicka or Stamina are 25% more powerful", 20, List.of(ALCHEMIST.getName()), false);
    public static Perk BENEFACTOR = new Perk("Benefactor", "Potions you mix with beneficial effects have an additional 25% greater magnitude", 30, List.of(PHYSICIAN.getName()), false);
    public static Perk EXPERIMENTER = new Perk("Experimenter", "Eating an ingredient reveals first two effects", 50, List.of(BENEFACTOR.getName()), false);
    public static Perk POISONER = new Perk("Poisoner", "Poisons you mix are 25% more effective", 30, List.of(PHYSICIAN.getName()), false);
    public static Perk CONCENTRATED_POISON = new Perk("Concentrated Poison", "Poisons applied to weapons last for twice as many hits", 60, List.of(POISONER.getName()), false);
    public static Perk GREEN_THUMB = new Perk("Green Thumb", "Two ingredients are gathered from plants", 70, List.of(CONCENTRATED_POISON.getName()), false);
    public static Perk SNAKEBLOOD = new Perk("Snakeblood", "50% resistance to all poisons", 80, List.of(EXPERIMENTER.getName()), false);
    public static Perk PURITY = new Perk("Purity", "All negative effects are removed from created potions, and all positive effects are removed from created poisons", 100, List.of(SNAKEBLOOD.getName()), false);

    //Alteration


    //Archery
    public static Perk OVERDRAW = new Perk("Overdraw", "Bows do 20% more damage", 20, List.of(), false);
    public static Perk EAGLE_EYE = new Perk("Eagle Eye", "Pressing Block while aiming will zoom in your view", 20, List.of(OVERDRAW.getName()), false);
    public static Perk CRITICAL_SHOT = new Perk("Critical Shot", "10% chance of a critical hit that does extra damage", 30, List.of(OVERDRAW.getName()), false);
    public static Perk STEADY_HAND = new Perk("Steady Hand", "Zooming in with a bow slows time by 25%", 40, List.of(EAGLE_EYE.getName()), false);
    public static Perk POWER_SHOT = new Perk("Power Shot", "Arrows stagger all but the largest opponents 50% of the time", 40, List.of(EAGLE_EYE.getName()), false);
    public static Perk HUNTERS_DISCIPLINE = new Perk("Hunter's Discipline", "Recover twice as many arrows from dead bodies", 50, List.of(CRITICAL_SHOT.getName()), false);
    public static Perk RANGER = new Perk("Ranger", "Able to move faster with a drawn bow", 60, List.of(HUNTERS_DISCIPLINE.getName()), false);
    public static Perk QUICK_SHOT = new Perk("Quick Shot", "Can draw a bow 30% faster", 70, List.of(POWER_SHOT.getName()), false);
    public static Perk BULLSEYE = new Perk("Bullseye", "15% chance of paralyzing the target for a few seconds", 100, List.of(QUICK_SHOT.getName()), false);


}