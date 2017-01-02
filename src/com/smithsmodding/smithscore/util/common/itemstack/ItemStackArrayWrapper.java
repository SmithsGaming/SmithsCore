package com.smithsmodding.smithscore.util.common.itemstack;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/2/2017.
 */
public class ItemStackArrayWrapper implements IItemStackStorageWrapper<ItemStack[]> {

    @Nonnull
    private final ItemStack[] storage;

    public ItemStackArrayWrapper(int size) {
        storage = new ItemStack[size];
    }

    /**
     * Method to get a ItemStack stored on the given Index.
     *
     * @param index The index to get the Strack From.
     * @return The currently stored ItemStack on that Index. Or ItemStack.EMTPY if none is stored there.
     * @throws IndexOutOfBoundsException if index is not in the valid Range. Index < 0 or Index >= size.
     */
    @Override
    @Nonnull
    public ItemStack get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= storage.length)
            throw new IndexOutOfBoundsException("Index is not in the valid Range. Index < 0 or Index >= size.");

        ItemStack result = storage[index];

        if (result == null)
            result = ItemStack.EMPTY;

        return result;
    }

    /**
     * Method to save an ItemStack on a given Index.
     *
     * @param index The index to store the stack on.
     * @param stack The Stack to store.
     * @throws IndexOutOfBoundsException if index is not in the valid Range. Index < 0 or Index >= size.
     */
    @Override
    public void set(int index, @Nonnull ItemStack stack) throws IndexOutOfBoundsException {
        if (index < 0 || index >= storage.length)
            throw new IndexOutOfBoundsException("Index is not in the valid Range. Index < 0 or Index >= size.");

        if (!stack.isEmpty()) {
            storage[index] = stack;
        } else {
            storage[index] = null;
        }
    }

    /**
     * Method to get the Size of the Wrapper.
     *
     * @return The amount of ItemStacks the wrapper can store.
     */
    @Override
    public int getSize() {
        return storage.length;
    }

    /**
     * Method to get the internal object that is used to store the Stacks.
     *
     * @return The interal Object to store the stacks.
     */
    @Nonnull
    @Override
    public ItemStack[] getInterStorage() {
        return storage;
    }

}
