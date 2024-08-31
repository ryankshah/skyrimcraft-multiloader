package com.ryankshah.skyrimcraft.block.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BlacksmithForgeInventory implements Container {
    /**
     * Returns the number of slots in the inventory.
     */
    public int getContainerSize() {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }

    @Override
    public ItemStack getItem(int i) {
        return null;
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     *
     * @param index
     * @param count
     */
    public ItemStack removeItem(int index, int count) {
        return ItemStack.EMPTY;
    }

    /**
     * Removes a stack from the given slot and returns it.
     *
     * @param index
     */
    public ItemStack removeItemNoUpdate(int index) {
        return ItemStack.EMPTY;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param index
     * @param stack
     */
    public void setItem(int index, ItemStack stack) {    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    public void setChanged() {   }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     *
     * @param player
     */
    public boolean stillValid(Player player) {
        return false;
    }

    public void clearContent() {    }
}