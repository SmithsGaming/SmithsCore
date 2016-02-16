package com.smithsmodding.smithscore.common.tileentity.Capabilities;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

/**
 * Interface that sets the requirements for the Default SmithsCore IItemHandler.
 *
 * Created by Marc on 15.02.2016.
 */
public interface ISmithsCoreItemHandler
{

    /**
     * Method to get the slot count in this inventory.
     *
     * @return The slot count.
     */
    int getSlotCount();

    /**
     * Method to get the Stack in a given Handler
     *
     * @param slot The Index of the Slot that the IItemHandler requests.
     * @param amount The maximal amount of Items that the requester requires. Maybe more then the stack limit.
     * @param simulate True when no actual changes to the Handler should be made. False when the caller actually changes the handler.
     *
     * @return The constructed result stack. A ItemStack with stacksize 0 will be treated as a Filter.
     */
    ItemStack getStackInHandler (int slot, int amount, boolean simulate);

    /**
     * Method to set the Stack in a given Handler.
     *
     * @param slot The Index of the Slot that the IItemHandler modifies.
     * @param itemStack The stack the handler tries to put into the Handler.
     * @param simulate True when no actual changes to the Handler should be made. False when the caller actually changes the handler.
     *
     * @return The rest of the Stack. Null should be returned when the complete stack should be accepted.
     */
    ItemStack setHandlerSlotContents (int slot, ItemStack itemStack, boolean simulate);

    /**
     * Method used by the ItemHandler to check if a Stack is allowed to enter the inventory.
     *
     * @param slot The Index of the Slot being tested.
     * @param itemStack The stack that the Handler requests the test for.
     *
     * @return True when the stack is allowed, false when not. This function should NOT check for StackSize.
     */
    boolean isItemValidForSlot (int slot, ItemStack itemStack);

    /**
     * Method used by vanilla Minecraft to mark the block for update.
     */
    void markDirty();

    /**
     * Method used as a compromise as long as the transition from IInventory to IItemHandler is still ongoing.
     *
     * @return The IInventory that handles this handler.
     */
    default IInventory getIInventoryWrapper()
    {
        return new StandardInventoryWrapper(this);
    }

    /**
     * Standard wrapper for this ItemHandler.
     */
    class StandardInventoryWrapper implements IInventory
    {

        ISmithsCoreItemHandler handler;

        public StandardInventoryWrapper (ISmithsCoreItemHandler handler) {
            this.handler = handler;
        }

        /**
         * Returns the number of slots in the inventory.
         */
        @Override
        public int getSizeInventory () {
            return handler.getSlotCount();
        }

        /**
         * Returns the stack in the given slot.
         *
         * @param index
         */
        @Override
        public ItemStack getStackInSlot (int index) {
            return handler.getStackInHandler(index, 64, false);
        }

        /**
         * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
         *
         * @param index
         * @param count
         */
        @Override
        public ItemStack decrStackSize (int index, int count) {
            return handler.getStackInHandler(index, count, false);
        }

        /**
         * Removes a stack from the given slot and returns it.
         *
         * @param index
         */
        @Override
        public ItemStack removeStackFromSlot (int index) {
            return handler.getStackInHandler(index, 64, false);
        }

        /**
         * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
         *
         * @param index
         * @param stack
         */
        @Override
        public void setInventorySlotContents (int index, ItemStack stack) {
            handler.setHandlerSlotContents(index, stack, false);
        }

        /**
         * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
         */
        @Override
        public int getInventoryStackLimit () {
            return 64;
        }

        /**
         * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
         * hasn't changed and skip it.
         */
        @Override
        public void markDirty () {
            handler.markDirty();
        }

        /**
         * Do not make give this method the name canInteractWith because it clashes with Container
         *
         * @param player
         */
        @Override
        public boolean isUseableByPlayer (EntityPlayer player) {
            return true;
        }

        @Override
        public void openInventory (EntityPlayer player) {

        }

        @Override
        public void closeInventory (EntityPlayer player) {

        }

        /**
         * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
         *
         * @param index
         * @param stack
         */
        @Override
        public boolean isItemValidForSlot (int index, ItemStack stack) {
            return handler.isItemValidForSlot(index, stack);
        }

        @Override
        public int getField (int id) {
            return 0;
        }

        @Override
        public void setField (int id, int value) {

        }

        @Override
        public int getFieldCount () {
            return 0;
        }

        @Override
        public void clear () {

        }

        /**
         * Gets the name of this command sender (usually username, but possibly "Rcon")
         */
        @Override
        public String getName () {
            return "";
        }

        /**
         * Returns true if this thing is named
         */
        @Override
        public boolean hasCustomName () {
            return false;
        }

        /**
         * Get the formatted ChatComponent that will be used for the sender's username in chat
         */
        @Override
        public IChatComponent getDisplayName () {
            return new ChatComponentText("");
        }
    }
}
