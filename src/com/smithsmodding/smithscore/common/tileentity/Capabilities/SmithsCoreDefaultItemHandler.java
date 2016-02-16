package com.smithsmodding.smithscore.common.tileentity.Capabilities;

import net.minecraft.item.*;
import net.minecraftforge.items.*;

/**
 * Created by Marc on 15.02.2016.
 */
public class SmithsCoreDefaultItemHandler implements IItemHandler
{

    ISmithsCoreItemHandler handler;

    public SmithsCoreDefaultItemHandler(ISmithsCoreItemHandler handler)
    {
        this.handler = handler;
    }

    /**
     * Returns the number of slots available
     *
     * @return The number of slots available
     **/
    @Override
    public int getSlots () {
        return handler.getSlotCount();
    }

    /**
     * Returns the ItemStack in a given slot.
     * <p/>
     * The result's stack size may be greater than the itemstacks max size.
     * <p/>
     * If the result is null, then the slot is empty. If the result is not null but the stack size is zero, then it
     * represents an empty slot that will only accept* a specific itemstack.
     * <p/>
     * <p/>
     * IMPORTANT: This ItemStack MUST NOT be modified. This method is not for altering an inventories contents. Any
     * implementers who are able to detect modification through this method should throw an exception.
     * <p/>
     * SERIOUSLY: DO NOT MODIFY THE RETURNED ITEMSTACK
     *
     * @param slot Slot to query
     *
     * @return ItemStack in given slot. May be null.
     **/
    @Override
    public ItemStack getStackInSlot (int slot) {
        return handler.getStackInHandler(slot, 0, true).copy();
    }

    /**
     * Inserts an ItemStack into the given slot and return the remainder. Note: This behaviour is subtly different from
     * IFluidHandlers.fill()
     *
     * @param slot     Slot to insert into.
     * @param stack    ItemStack to insert
     * @param simulate If true, the insertion is only simulated
     *
     * @return The remaining ItemStack that was not inserted (if the entire stack is accepted, then return null)
     **/
    @Override
    public ItemStack insertItem (int slot, ItemStack stack, boolean simulate) {
        if (!handler.isItemValidForSlot(slot, stack))
            return stack;

        if (!simulate)
            handler.markDirty();

        return handler.setHandlerSlotContents(slot, stack, simulate);
    }

    /**
     * Extracts an ItemStack from the given slot. The returned value must be null if nothing is extracted, otherwise
     * it's stack size must not be greater than amount or the itemstacks getMaxStackSize().
     *
     * @param slot     Slot to extract from.
     * @param amount   Amount to extract (may be greater than the current stacks max limit)
     * @param simulate If true, the extraction is only simulated
     *
     * @return ItemStack extracted from the slot, must be null, if nothing can be extracted
     **/
    @Override
    public ItemStack extractItem (int slot, int amount, boolean simulate) {
        if (!simulate)
            handler.markDirty();

        return handler.getStackInHandler(slot, amount, simulate);
    }
}
