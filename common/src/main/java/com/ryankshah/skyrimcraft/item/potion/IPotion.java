package com.ryankshah.skyrimcraft.item.potion;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;

public interface IPotion
{
    public PotionCategory getCategory();
    public NonNullList<Ingredient> getIngredients();

    public enum PotionCategory {
        ALL(-1, "ALL"),
        CURE_CONDITION(0, "CURE CONDITION"),
        FORTIFY_ALTERATION(1, "FORTIFY ALTERATION"),
        FORTIFY_BARTER(2, "FORTIFY BARTER"),
        FORTIFY_BLOCKING(3, "FORTIFY BLOCKING"),
        FORTIFY_CARRY_WEIGHT(4, "FORTIFY CARRY WEIGHT"),
        FORTIFY_CONJURATION(5, "FORTIFY CONJURATION"),
        FORTIFY_DESTRUCTION(6, "FORTIFY DESTRUCTION"),
        FORTIFY_ENCHANTING(7, "FORTIFY ENCHANTING"),
        FORTIFY_HEALTH(8, "FORTIFY HEALTH"),
        FORTIFY_HEAVY_ARMOR(9, "FORTIFY HEAVY ARMOR"),
        FORTIFY_ILLUSION(10, "FORTIFY ILLUSION"),
        FORTIFY_LIGHT_ARMOR(11, "FORTIFY LIGHT ARMOR"),
        FORTIFY_LOCKPICKING(12, "FORTIFY LOCKPICKING"),
        FORTIFY_MAGICKA(13, "FORTIFY MAGICKA"),
        FORTIFY_MARKSMAN(14, "FORTIFY MARKSMAN"),
        FORTIFY_ONE_HANDED(15, "FORTIFY ONE-HANDED"),
        FORTIFY_PICKPOCKET(16, "FORTIFY PICKPOCKET"),
        FORTIFY_RESTORATION(17, "FORTIFY RESTORATION"),
        FORTIFY_SMITHING(18, "FORTIFY SMITHING"),
        FORTIFY_SNEAK(19, "FORTIFY SNEAK"),
        FORTIFY_SPEECH(20, "FORTIFY SPEECH"),
        FORTIFY_STAMINA(21, "FORTIFY STAMINA"),
        FORTIFY_TWO_HANDED(22, "FORTIFY TWO-HANDED"),
        INVISIBILITY(23, "INVISIBILITY"),
        REGENERATE_HEALTH(24, "REGENERATE HEALTH"),
        REGENERATE_MAGICKA(25, "REGENERATE MAGICKA"),
        REGENERATE_STAMINA(26, "REGENERATE STAMINA"),
        RESIST_FIRE(27, "RESIST FIRE"),
        RESIST_FROST(28, "RESIST FROST"),
        RESIST_MAGIC(29, "RESIST MAGIC"),
        RESIST_SHOCK(30, "RESIST SHOCK"),
        RESTORE_HEALTH(31, "RESTORE HEALTH"),
        RESTORE_MAGICKA(32, "RESTORE MAGICKA"),
        RESTORE_STAMINA(33, "RESTORE STAMINA"),
        WATERBREATHING(34, "WATERBREATHING"),
        WATERWALKING(35, "WATERWALKING"),
        WELL_BEING(36, "WELL-BEING"),
        UNIQUE(37, "UNIQUE", false),
        DRUGS(38, "DRUGS"),
        POISONS(39, "POISONS");

        private int typeID;
        private String displayName;
        private boolean leveledCategory;

        PotionCategory(int typeID, String displayName) {
            this(typeID, displayName, true);
        }

        PotionCategory(int typeID, String displayName, boolean leveledCategory) {
            this.typeID = typeID;
            this.displayName = displayName;
            this.leveledCategory = leveledCategory;
        }

        public int getTypeID() {
            return this.typeID;
        }

        public boolean isLeveledCategory() {
            return leveledCategory;
        }

        @Override
        public String toString() {
            return this.displayName;
        }
    }
}