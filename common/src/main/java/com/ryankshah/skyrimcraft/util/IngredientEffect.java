package com.ryankshah.skyrimcraft.util;

public enum IngredientEffect
{
    REGENERATE_MAGICKA("Regenerate Magicka"),
    REGENERATE_HEALTH("Regenerate Health"),
    REGENERATE_STAMINA("Regenerate Stamina"),
    RESTORE_MAGICKA("Restore Magicka"),
    RESTORE_HEALTH("Restore Health"),
    RESTORE_STAMINA("Restore Stamina"),
    DAMAGE_STAMINA("Damage Stamina"),
    DAMAGE_MAGICKA("Damage Magicka"),
    DAMAGE_HEALTH("Damage Health"),
    DAMAGE_MAGICKA_REGEN("Damage Magicka Regen"),
    DAMAGE_HEALTH_REGEN("Damage Health Regen"),
    DAMAGE_STAMINA_REGEN("Damage Stamina Regen"),
    RAVAGE_HEALTH("Ravage Health"),
    RAVAGE_MAGICKA("Ravage Magicka"),
    RAVAGE_STAMINA("Ravage Stamina"),
    WEAKNESS_TO_MAGIC("Weakness to Magic"),
    WEAKNESS_TO_FROST("Weakness to Frost"),
    WEAKNESS_TO_FIRE("Weakness to Fire"),
    WEAKNESS_TO_SHOCK("Weakness to Shock"),
    WEAKNESS_TO_POISON("Weakness to Poison"),
    RESIST_FIRE("Resist Fire"),
    RESIST_FROST("Resist Frost"),
    RESIST_POISON("Resist Poison"),
    RESIST_SHOCK("Resist Shock"),
    RESIST_MAGIC("Resist Magic"),
    FORTIFY_ILLUSION("Fortify Illusion"),
    FORTIFY_CONJURATION("Fortify Conjuration"),
    FORTIFY_ENCHANTING("Fortify Enchanting"),
    FORTIFY_ALTERATION("Fortify Alteration"),
    FORTIFY_DESTRUCTION("Fortify Destruction"),
    FORTIFY_RESTORATION("Fortify Restoration"),
    FORTIFY_HEAVY_ARMOR("Fortify Heavy Armor"),
    FORTIFY_LIGHT_ARMOR("Fortify Light Armor"),
    FORTIFY_MAGICKA("Fortify Magicka"),
    FORTIFY_HEALTH("Fortify Health"),
    FORTIFY_STAMINA("Fortify Stamina"),
    FORTIFY_BARTER("Fortify Barter"),
    FORTIFY_BLOCK("Fortify Block"),
    FORTIFY_CARRY_WEIGHT("Fortify Carry Weight"),
    FORTIFY_SNEAK("Fortify Sneak"),
    FORTIFY_SMITHING("Fortify Smithing"),
    FORTIFY_PICKPOCKET("Fortify Pickpocket"),
    FORTIFY_LOCKPICKING("Fortify Lockpicking"),
    FORTIFY_ONE_HANDED("Fortify One-Handed"),
    FORTIFY_TWO_HANDED("Fortify Two-Handed"),
    FORTIFY_MARKSMAN("Fortify Marksman"),
    LINGERING_DAMAGE_HEALTH("Lingering Damage Health"),
    LINGERING_DAMAGE_MAGICKA("Lingering Damage Magicka"),
    LINGERING_DAMAGE_STAMINA("Lingering Damage Stamina"),
    SLOW("Slow"),
    FEAR("Fear"),
    FRENZY("Frenzy"),
    PARALYSIS("Paralysis"),
    WATERBREATHING("Waterbreathing"),
    CURE_DISEASE("Cure Disease"),
    INVISIBILITY("Invisibility");

    private String displayName;

    IngredientEffect(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}