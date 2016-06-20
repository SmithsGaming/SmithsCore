package com.smithsmodding.smithscore.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorldNameable;

/**
 * Author Orion (Created on: 20.06.2016)
 */
public interface IItemStorage extends IWorldNameable {
    /**
     * Returns the number of slots in the inventory.
     */
    int getSizeInventory();

    /**
     * Returns the stack in the given slot.
     */
    ItemStack getStackInSlot(int index);

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    ItemStack decrStackSize(int index, int count);

    /**
     * Method to clear the complete inventory.
     */
    void clearInventory();

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    void setInventorySlotContents(int index, ItemStack stack);

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    int getInventoryStackLimit();

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    void markDirty();

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    boolean isItemValidForSlot(int index, ItemStack stack);

    class IInventoryWrapper implements IInventory {
        private final IItemStorage storage;

        public IInventoryWrapper(IItemStorage storage) {
            this.storage = storage;
        }


        @Override
        public int getSizeInventory() {
            return storage.getSizeInventory();
        }

        @Override
        public ItemStack getStackInSlot(int index) {
            return storage.getStackInSlot(index);
        }

        @Override
        public ItemStack decrStackSize(int index, int count) {
            return storage.decrStackSize(index, count);
        }

        @Override
        public ItemStack removeStackFromSlot(int index) {
            ItemStack result = getStackInSlot(index);
            setInventorySlotContents(index, null);

            return result;
        }

        @Override
        public void setInventorySlotContents(int index, ItemStack stack) {
            storage.setInventorySlotContents(index, stack);
        }

        @Override
        public int getInventoryStackLimit() {
            return storage.getInventoryStackLimit();
        }

        @Override
        public void markDirty() {
            storage.markDirty();
        }

        @Override
        public boolean isUseableByPlayer(EntityPlayer player) {
            return true;
        }

        @Override
        public void openInventory(EntityPlayer player) {

        }

        @Override
        public void closeInventory(EntityPlayer player) {

        }

        @Override
        public boolean isItemValidForSlot(int index, ItemStack stack) {
            return storage.isItemValidForSlot(index, stack);
        }

        @Override
        public int getField(int id) {
            return 0;
        }

        @Override
        public void setField(int id, int value) {

        }

        @Override
        public int getFieldCount() {
            return 0;
        }

        @Override
        public void clear() {
            storage.clearInventory();
        }

        @Override
        public String getName() {
            return storage.getName();
        }

        @Override
        public boolean hasCustomName() {
            return storage.hasCustomName();
        }

        @Override
        public ITextComponent getDisplayName() {
            return storage.getDisplayName();
        }
    }
}
