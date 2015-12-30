package com.SmithsModding.SmithsCore.Util.Common;

import gnu.trove.strategy.*;
import net.minecraft.item.*;

/**
 * Created by Marc on 29.12.2015.
 */
public class ItemStackHashingStrategy implements HashingStrategy {

    /**
     * Computes a hash code for the specified object.  Implementers can use the object's own <tt>hashCode</tt> method,
     * the Java runtime's <tt>identityHashCode</tt>, or a custom scheme.
     *
     * @param object for which the hashcode is to be computed
     *
     * @return the hashCode
     */
    @Override
    public int computeHashCode (Object object) {
        if (!( object instanceof ItemStack ))
            return object.hashCode();

        ItemStack stack = (ItemStack) object;

        int hash = stack.getItem().hashCode() ^ stack.getMetadata();

        if (stack.getTagCompound() != null)
            hash ^= stack.getTagCompound().hashCode();

        return hash;
    }

    /**
     * Compares o1 and o2 for equality.  Strategy implementers may use the objects' own equals() methods, compare object
     * references, or implement some custom scheme.
     *
     * @param o1 an <code>Object</code> value
     * @param o2 an <code>Object</code> value
     *
     * @return true if the objects are equal according to this strategy.
     */
    @Override
    public boolean equals (Object o1, Object o2) {
        if (!( o1 instanceof ItemStack ))
            return false;

        if (!( o2 instanceof ItemStack ))
            return false;

        return ItemStackHelper.equalsIgnoreStackSize((ItemStack) o1, (ItemStack) o2);
    }
}
