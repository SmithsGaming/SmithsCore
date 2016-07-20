package com.smithsmodding.smithscore.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;

/**
 * Author Orion (Created on: 20.06.2016)
 */
public class ItemStorageItemHandler implements IItemHandlerModifiable {
    private final IItemStorage inv;

    public ItemStorageItemHandler(IItemStorage inv) {
        this.inv = inv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        InvWrapper that = (InvWrapper) o;

        return getInv().equals(that.getInv());

    }

    @Override
    public int hashCode() {
        return getInv().hashCode();
    }

    @Override
    public int getSlots() {
        return getInv().getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return getInv().getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack == null)
            return null;

        if (!getInv().isItemValidForSlot(slot, stack))
            return stack;

        ItemStack stackInSlot = getInv().getStackInSlot(slot);

        int m;
        if (stackInSlot != null) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, stackInSlot))
                return stack;

            m = Math.min(stack.getMaxStackSize(), getInv().getInventoryStackLimit()) - stackInSlot.stackSize;

            if (stack.stackSize <= m) {
                if (!simulate) {
                    ItemStack copy = stack.copy();
                    copy.stackSize += stackInSlot.stackSize;
                    getInv().setInventorySlotContents(slot, copy);
                    getInv().markInventoryDirty();
                }

                return null;
            } else {
                // copy the stack to not modify the original one
                stack = stack.copy();
                if (!simulate) {
                    ItemStack copy = stack.splitStack(m);
                    copy.stackSize += stackInSlot.stackSize;
                    getInv().setInventorySlotContents(slot, copy);
                    getInv().markInventoryDirty();
                    return stack;
                } else {
                    stack.stackSize -= m;
                    return stack;
                }
            }
        } else {
            m = Math.min(stack.getMaxStackSize(), getInv().getInventoryStackLimit());
            if (m < stack.stackSize) {
                // copy the stack to not modify the original one
                stack = stack.copy();
                if (!simulate) {
                    getInv().setInventorySlotContents(slot, stack.splitStack(m));
                    getInv().markInventoryDirty();
                    return stack;
                } else {
                    stack.stackSize -= m;
                    return stack;
                }
            } else {
                if (!simulate) {
                    getInv().setInventorySlotContents(slot, stack);
                    getInv().markInventoryDirty();
                }
                return null;
            }
        }

    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return null;

        ItemStack stackInSlot = getInv().getStackInSlot(slot);

        if (stackInSlot == null)
            return null;

        if (simulate) {
            if (stackInSlot.stackSize < amount) {
                return stackInSlot.copy();
            } else {
                ItemStack copy = stackInSlot.copy();
                copy.stackSize = amount;
                return copy;
            }
        } else {
            int m = Math.min(stackInSlot.stackSize, amount);

            ItemStack decrStackSize = getInv().decrStackSize(slot, m);
            getInv().markInventoryDirty();
            return decrStackSize;
        }
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        getInv().setInventorySlotContents(slot, stack);
    }

    public IItemStorage getInv() {
        return inv;
    }
}
