package com.ryankshah.skyrimcraft.character.skill;

import java.util.List;

public class Perks
{
    // Alchemy
    public static Perk ALCHEMIST = new Perk("Overdraw", "Potions and poisons you make are 20% stronger", 20, List.of(), false);
    public static Perk PHYSICIAN = new Perk("Physician", "Potions you mix that restore Health, Magicka or Stamina are 25% more powerful", 20, List.of(ALCHEMIST.getName()), false);
    public static Perk BENEFACTOR = new Perk("Benefactor", "Potions you mix with beneficial effects have an additional 25% greater magnitude", 30, List.of(PHYSICIAN.getName()), false);
    public static Perk EXPERIMENTER = new Perk("Experimenter", "Eating an ingredient reveals first two effects", 50, List.of(BENEFACTOR.getName()), false);
    public static Perk POISONER = new Perk("Poisoner", "Poisons you mix are 25% more effective", 30, List.of(PHYSICIAN.getName()), false);
    public static Perk CONCENTRATED_POISON = new Perk("Concentrated Poison", "Poisons applied to weapons last for twice as many hits", 60, List.of(POISONER.getName()), false);
    public static Perk GREEN_THUMB = new Perk("Green Thumb", "Two ingredients are gathered from plants", 70, List.of(CONCENTRATED_POISON.getName()), false);
    public static Perk SNAKEBLOOD = new Perk("Snakeblood", "50% resistance to all poisons", 80, List.of(EXPERIMENTER.getName()), false);
    public static Perk PURITY = new Perk("Purity", "All negative effects are removed from created potions, and all positive effects are removed from created poisons", 100, List.of(SNAKEBLOOD.getName()), false);

    // Alteration
    public static Perk NOVICE_ALTERATION = new Perk("Novice Alteration", "Cast Novice level Alteration spells for half magicka", 20, List.of(), false);
    public static Perk APPRENTICE_ALTERATION = new Perk("Apprentice Alteration", "Cast Apprentice level Alteration spells for half magicka", 25, List.of(NOVICE_ALTERATION.getName()), false);
    public static Perk MAGE_ARMOR = new Perk("Mage Armor", "Protection spells like Stoneflesh are twice as strong if not wearing armor", 30, List.of(APPRENTICE_ALTERATION.getName()), false);
    public static Perk MAGIC_RESISTANCE = new Perk("Magic Resistance", "Blocks 10% of a spells effect", 30, List.of(APPRENTICE_ALTERATION.getName()), false);
    public static Perk ADEPT_ALTERATION = new Perk("Adept Alteration", "Cast Adept level Alteration spells for half magicka", 50, List.of(APPRENTICE_ALTERATION.getName()), false);
    public static Perk STABILITY = new Perk("Stability", "Alteration spells have greater duration (+50%)", 70, List.of(ADEPT_ALTERATION.getName()), false);
    public static Perk EXPERT_ALTERATION = new Perk("Expert Alteration", "Cast Expert level Alteration spells for half magicka", 75, List.of(ADEPT_ALTERATION.getName()), false);
    public static Perk ATRONACH = new Perk("Atronach", "Grants 30% spell absorption and will block friendly spells as well as hostile ones", 100, List.of(EXPERT_ALTERATION.getName()), false);
    public static Perk MASTER_ALTERATION = new Perk("Master Alteration", "Cast Master level Alteration spells for half magicka", 100, List.of(EXPERT_ALTERATION.getName()), false);

    // Archery
    public static Perk OVERDRAW = new Perk("Overdraw", "Bows do 20% more damage", 20, List.of(), false);
    public static Perk EAGLE_EYE = new Perk("Eagle Eye", "Pressing Block while aiming will zoom in your view", 20, List.of(OVERDRAW.getName()), false);
    public static Perk CRITICAL_SHOT = new Perk("Critical Shot", "10% chance of a critical hit that does extra damage", 30, List.of(OVERDRAW.getName()), false);
    public static Perk STEADY_HAND = new Perk("Steady Hand", "Zooming in with a bow slows time by 25%", 40, List.of(EAGLE_EYE.getName()), false);
    public static Perk POWER_SHOT = new Perk("Power Shot", "Arrows stagger all but the largest opponents 50% of the time", 40, List.of(EAGLE_EYE.getName()), false);
    public static Perk HUNTERS_DISCIPLINE = new Perk("Hunter's Discipline", "Recover twice as many arrows from dead bodies", 50, List.of(CRITICAL_SHOT.getName()), false);
    public static Perk RANGER = new Perk("Ranger", "Able to move faster with a drawn bow", 60, List.of(HUNTERS_DISCIPLINE.getName()), false);
    public static Perk QUICK_SHOT = new Perk("Quick Shot", "Can draw a bow 30% faster", 70, List.of(POWER_SHOT.getName()), false);
    public static Perk BULLSEYE = new Perk("Bullseye", "15% chance of paralyzing the target for a few seconds", 100, List.of(QUICK_SHOT.getName()), false);

    // Block
    public static Perk SHIELD_WALL = new Perk("Shield Wall", "Blocking is 20% more effective", 20, List.of(), false);
    public static Perk DEFLECT_ARROWS = new Perk("Deflect Arrows", "Arrows that hit the shield do not damage it", 30, List.of(SHIELD_WALL.getName()), false);
//    public static Perk QUICK_REFLEXES = new Perk("Quick Reflexes", "Time slows down if you are blocking during an enemy's power attack", 30, List.of(SHIELD_WALL.getName()), false);
    public static Perk POWER_BASH = new Perk("Power Bash", "Able to do a power bash", 30, List.of(SHIELD_WALL.getName()), false);
    public static Perk ELEMENTAL_PROTECTION = new Perk("Elemental Protection", "Blocking with a shield reduces incoming fire, frost and shock damage by 50%", 50, List.of(DEFLECT_ARROWS.getName()), false);
    public static Perk DEADLY_BASH = new Perk("Deadly Bash", "Bashing does 5x more damage", 50, List.of(POWER_BASH.getName()), false);
    public static Perk BLOCK_RUNNER = new Perk("Block Runner", "Able to move faster with a shield or weapon raised", 70, List.of(ELEMENTAL_PROTECTION.getName()), false);
    public static Perk DISARMING_BASH = new Perk("Disarming Bash", "Chance to disarm when power bashing (50% chance)", 70, List.of(DEADLY_BASH.getName()), false);
    public static Perk SHIELD_CHARGE = new Perk("Shield Charge", "Able to sprint with shield raised, which can knock down most targets", 100, List.of(BLOCK_RUNNER.getName(), DISARMING_BASH.getName()), false);

    // Conjuration
    public static Perk NOVICE_CONJURATION = new Perk("Novice Conjuration", "Cast Novice level Conjuration spells for half magicka", 20, List.of(), false);
    public static Perk APPRENTICE_CONJURATION = new Perk("Apprentice Conjuration", "Cast Apprentice level Conjuration spells for half magicka", 25, List.of(NOVICE_CONJURATION.getName()), false);
    public static Perk SUMMONER = new Perk("Summoner", "Can summon Atronachs, raise undead or Dremora Lords twice as far away", 30, List.of(NOVICE_CONJURATION.getName()), false);
    public static Perk NECROMANCY = new Perk("Necromancy", "Greater duration for reanimated undead", 40, List.of(NOVICE_CONJURATION.getName()), false);
    public static Perk ATROMANCY = new Perk("Atromancy", "Double duration for conjured Atronachs and Dremora Lords", 40, List.of(SUMMONER.getName()), false);
    public static Perk ADEPT_CONJURATION = new Perk("Adept Conjuration", "Cast Adept level Conjuration spells for half magicka", 50, List.of(APPRENTICE_CONJURATION.getName()), false);
    public static Perk DARK_SOULS = new Perk("Dark Souls", "Reanimated undead have 25 more hearts", 70, List.of(NECROMANCY.getName()), false);
    public static Perk EXPERT_CONJURATION = new Perk("Expert Conjuration", "Cast Expert level Conjuration spells for half magicka", 75, List.of(ADEPT_CONJURATION.getName()), false);
    public static Perk ELEMENTAL_POTENCY = new Perk("Elemental Potency", "Conjured Atronachs are 50% more powerful", 80, List.of(ATROMANCY.getName()), false);
    public static Perk TWIN_SOULS = new Perk("Twin Souls", "The player can have two conjured creatures", 100, List.of(DARK_SOULS.getName()), false);
    public static Perk MASTER_CONJURATION = new Perk("Master Conjuration", "Cast Master level Conjuration spells for half magicka", 100, List.of(EXPERT_CONJURATION.getName()), false);

    // Destruction
    public static Perk NOVICE_DESTRUCTION = new Perk("Novice Destruction", "Cast Novice level Destruction spells for half magicka", 20, List.of(), false);
    public static Perk APPRENTICE_DESTRUCTION = new Perk("Apprentice Destruction", "Cast Apprentice level Destruction spells for half magicka", 25, List.of(NOVICE_DESTRUCTION.getName()), false);
    public static Perk AUGMENTED_FLAMES = new Perk("Augmented Flames", "Fire spells do 25% more damage", 30, List.of(NOVICE_DESTRUCTION.getName()), false);
    public static Perk AUGMENTED_FROST = new Perk("Augmented Frost", "Frost spells do 25% more damage", 30, List.of(NOVICE_DESTRUCTION.getName()), false);
    public static Perk AUGMENTED_SHOCK = new Perk("Augmented Shock", "Shock spells do 25% more damage", 30, List.of(NOVICE_DESTRUCTION.getName()), false);
    public static Perk ADEPT_DESTRUCTION = new Perk("Adept Destruction", "Cast Adept level Destruction spells for half magicka", 50, List.of(APPRENTICE_DESTRUCTION.getName()), false);
    public static Perk INTENSE_FLAMES = new Perk("Intense Flames", "Fire damage causes targets to flee if their health is low", 50, List.of(AUGMENTED_FLAMES.getName()), false);
    public static Perk DEEP_FREEZE = new Perk("Deep Freeze", "Frost damage paralyzes targets if their health is low", 60, List.of(AUGMENTED_FROST.getName()), false);
    public static Perk DISINTEGRATE = new Perk("Disintegrate", "Shock damage disintegrates targets if their health is low", 70, List.of(AUGMENTED_SHOCK.getName()), false);
    public static Perk EXPERT_DESTRUCTION = new Perk("Expert Destruction", "Cast Expert level Destruction spells for half magicka", 75, List.of(ADEPT_DESTRUCTION.getName()), false);
    public static Perk MASTER_DESTRUCTION = new Perk("Master Destruction", "Cast Master level Destruction spells for half magicka", 100, List.of(EXPERT_DESTRUCTION.getName()), false);

    // Enchanting
    public static Perk ENCHANTER = new Perk("Enchanter", "New enchantments on weapons are 20% stronger", 20, List.of(), false);
//    public static Perk SOUL_SQUEEZER = new Perk("Apprentice Destruction", "", 25, List.of(NOVICE_DESTRUCTION.getName()), false);
    public static Perk FIRE_ENCHANTER = new Perk("Fire Enchanter", "Fire enchantments on weapons are 25% stronger", 30, List.of(ENCHANTER.getName()), false);
//    public static Perk SOUL_SIPHON = new Perk("Augmented Frost", "", 30, List.of(NOVICE_DESTRUCTION.getName()), false);
    public static Perk FROST_ENCHANTER = new Perk("Frost Enchanter", "Frost enchantments on weapons are 25% stronger", 40, List.of(FIRE_ENCHANTER.getName()), false);
    public static Perk INSIGHTFUL_ENCHANTER = new Perk("Insightful Enchanter", "Skill enchantments on armor are 25% stronger", 50, List.of(ENCHANTER.getName()), false);
    public static Perk STORM_ENCHANTER = new Perk("Storm Enchanter", "Shock enchantments on weapons and armor are 25% stronger", 50, List.of(FROST_ENCHANTER.getName()), false);
    public static Perk CORPUS_ENCHANTER = new Perk("Corpus Enchanter", "Health, magicka and stamina enchantments on armor are 25% stronger", 70, List.of(INSIGHTFUL_ENCHANTER.getName()), false);
    public static Perk EXTRA_EFFECT = new Perk("Extra Effect", "Allows you to exceed, by one level, the maximum enchants on weapons", 100, List.of(CORPUS_ENCHANTER.getName(), STORM_ENCHANTER.getName()), false);

    // Heavy Armor
    public static Perk JUGGERNAUT = new Perk("Juggernaut", "Increases armor rating for Heavy Armor by 20%", 20, List.of(), false);
    public static Perk FISTS_OF_STEEL = new Perk("Fists of Steel", "Unarmed attacks with Heavy Armor gauntlets do their armor rating in extra damage", 30, List.of(JUGGERNAUT.getName()), false);
    public static Perk WELL_FITTED = new Perk("Well Fitted", "25% Armor bonus if wearing all Heavy Armor", 30, List.of(JUGGERNAUT.getName()), false);
    public static Perk CUSHIONED = new Perk("Cushioned", "Half damage from falling if wearing all Heavy Armor", 50, List.of(FISTS_OF_STEEL.getName()), false);
    public static Perk TOWER_OF_STRENGTH = new Perk("Tower of Strength", "50% less stagger when wearing only Heavy Armor", 50, List.of(WELL_FITTED.getName()), false);
    public static Perk CONDITIONING = new Perk("Conditioning", "Heavy Armor weighs nothing and doesnt slow you down when worn", 70, List.of(CUSHIONED.getName()), false);
    public static Perk MATCHING_SET = new Perk("Matching Set", "Additional 25% Armor bonus if wearing a matched set of Heavy Armor", 70, List.of(TOWER_OF_STRENGTH.getName()), false);
    public static Perk REFLECT_BLOWS = new Perk("Reflect Blows", "10% chance to reflect back melee damage to enemies while wearing all Heavy Armor", 100, List.of(MATCHING_SET.getName()), false);

    // Illusion
    public static Perk NOVICE_ILLUSION = new Perk("Novice Illusion", "Cast Novice level Illusion spells for half magicka", 20, List.of(), false);
    public static Perk APPRENTICE_ILLUSION = new Perk("Apprentice Illusion", "Cast Apprentice level Illusion spells for half magicka", 25, List.of(NOVICE_ILLUSION.getName()), false);
    public static Perk ADEPT_ILLUSION = new Perk("Adept Illusion", "Cast Novice level Illusion spells for half magicka", 50, List.of(APPRENTICE_ILLUSION.getName()), false);
    public static Perk QUIET_CASTING = new Perk("Quiet Casting", "All spells cast from any school of magic are silent to others", 50, List.of(APPRENTICE_ILLUSION.getName()), false);
    public static Perk EXPERT_ILLUSION = new Perk("Expert Illusion", "Cast Expert level Illusion spells for half magicka", 75, List.of(ADEPT_ILLUSION.getName()), false);
    public static Perk MASTER_OF_THE_MIND = new Perk("Master of the Mind", "Cast Adept level Destruction spells for half magicka", 90, List.of(QUIET_CASTING.getName()), false);
    public static Perk MASTER_ILLUSION = new Perk("Master Illusion", "Fire damage causes targets to flee if their health is low", 100, List.of(EXPERT_ILLUSION.getName()), false);

    // Light Armor
    public static Perk AGILE_DEFENDER = new Perk("Agile Defender", "Increase armor rating for Light Armor by 20%", 20, List.of(), false);
    public static Perk CUSTOM_FIT = new Perk("Custom Fit", "25% armor bonus if wearing all Light Armor", 30, List.of(AGILE_DEFENDER.getName()), false);
    public static Perk UNHINDERED = new Perk("Unhindered", "Light armor weighs nothing and doesn't slow you down when worn", 50, List.of(CUSTOM_FIT.getName()), false);
//    public static Perk WIND_WALKER = new Perk("", "", 50, List.of(XYZZZZZ.getName()), false);
    public static Perk MATCHING_SET_LIGHT = new Perk("Matching Set", "Additional 25% Armor bonus if wearing a matched set of Light Armor", 70, List.of(CUSTOM_FIT.getName()), false);
    public static Perk DEFT_MOVEMENT = new Perk("Deft Movement", "10% chance of avoiding all damage from melee attacks while wearing all Light Armor", 100, List.of(MATCHING_SET_LIGHT.getName()), false);

    // Lockpicking
    public static Perk NOVICE_LOCKS = new Perk("Novice Locks", "Novice locks are much easier to pick", 20, List.of(), false);
    public static Perk APPRENTICE_LOCKS = new Perk("Apprentice Locks", "Apprentice locks are much easier to pick", 25, List.of(NOVICE_LOCKS.getName()), false);
    public static Perk ADEPT_LOCKS = new Perk("Adept Locks", "Adept locks are much easier to pick", 50, List.of(APPRENTICE_LOCKS.getName()), false);
    public static Perk EMERALD_TOUCH = new Perk("Emerald Touch", "Find more emeralds in chests", 60, List.of(ADEPT_LOCKS.getName()), false);
    public static Perk TREASURE_HUNTER = new Perk("Treasure Hunter", "50% greater chance of finding special treasure", 70, List.of(EMERALD_TOUCH.getName()), false);
    public static Perk EXPERT_LOCKS = new Perk("Expert Locks", "Expert locks are much easier to pick", 75, List.of(ADEPT_LOCKS.getName()), false);
    public static Perk UNBREAKABLE = new Perk("Unbreakable", "Lockpicks never break", 100, List.of(EXPERT_LOCKS.getName()), false);
    public static Perk MASTER_LOCKS = new Perk("Master Locks", "Master locks are much easier to pick", 100, List.of(EXPERT_LOCKS.getName()), false);

    // One Handed


    // Pickpocket


    // Restoration


    // Smithing


    // Sneak


    // Speech


    // Two Handed
}