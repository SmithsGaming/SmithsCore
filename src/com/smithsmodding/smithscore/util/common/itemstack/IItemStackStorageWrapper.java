package com.smithsmodding.smithscore.util.common.itemstack;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/2/2017.
 */
public interface IItemStackStorageWrapper<S> {
    /**
     * Method to get a ItemStack stored on the given Index.
     *
     * @param index The index to get the Strack From.
     * @return The currently stored ItemStack on that Index. Or ItemStack.EMTPY if none is stored there.
     * @throws IndexOutOfBoundsException if index is not in the valid Range. Index < 0 or Index >= size.
     */
    @Nonnull
    ItemStack get(int index) throws IndexOutOfBoundsException;

    /**
     * Method to save an ItemStack on a given Index.
     *
     * @param index The index to store the stack on.
     * @param stack The Stack to store.
     * @throws IndexOutOfBoundsException if index is not in the valid Range. Index < 0 or Index >= size.
     */
    void set(int index, @Nonnull ItemStack stack) throws IndexOutOfBoundsException;

    /**
     * Method to get the Size of the Wrapper.
     *
     * @return The amount of ItemStacks the wrapper can store.
     */
    int getSize();

    /**
     * Method to get the internal object that is used to store the Stacks.
     *
     * @return The interal Object to store the stacks.
     */
    @Nonnull
    S getInterStorage();
}
