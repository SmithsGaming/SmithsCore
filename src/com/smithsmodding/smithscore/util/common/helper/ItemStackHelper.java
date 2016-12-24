/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.common.helper;
/*
 *   ItemStackHelper
 *   Created by: Orion
 *   Created on: 16-1-2015
 */

import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;

public class ItemStackHelper {
    public static Comparator<ItemStack> COMPARATOR = new Comparator<ItemStack>() {
        public int compare(ItemStack pItemStack1, ItemStack pItemStack2) {
            if (pItemStack1 != null && pItemStack2 != null) {
                // Sort on itemID
                if (Item.getIdFromItem(pItemStack1.getItem()) - Item.getIdFromItem(pItemStack2.getItem()) == 0) {
                    // Sort on item
                    if (pItemStack1.getItem() == pItemStack2.getItem()) {
                        // Then sort on meta
                        if (pItemStack1.getItemDamage() == pItemStack2.getItemDamage()) {
                            // Then sort on NBT
                            if (pItemStack1.hasTagCompound() && pItemStack2.hasTagCompound()) {
                                // Then sort on stack size
                                if (ItemStack.areItemStackTagsEqual(pItemStack1, pItemStack2)) {
                                    return (pItemStack1.getCount() - pItemStack2.getCount());
                                } else {
                                    return (pItemStack1.getTagCompound().hashCode() - pItemStack2.getTagCompound().hashCode());
                                }
                            } else if (!(pItemStack1.hasTagCompound()) && pItemStack2.hasTagCompound()) {
                                return -1;
                            } else if (pItemStack1.hasTagCompound() && !(pItemStack2.hasTagCompound())) {
                                return 1;
                            } else {
                                return (pItemStack1.getCount() - pItemStack2.getCount());
                            }
                        } else {
                            return (pItemStack1.getItemDamage() - pItemStack2.getItemDamage());
                        }
                    } else {
                        return pItemStack1.getItem().getUnlocalizedName(pItemStack1).compareToIgnoreCase(pItemStack2.getItem().getUnlocalizedName(pItemStack2));
                    }
                } else {
                    return Item.getIdFromItem(pItemStack1.getItem()) - Item.getIdFromItem(pItemStack2.getItem());
                }
            } else if (pItemStack1 != null) {
                return -1;
            } else if (pItemStack2 != null) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    public static ItemStack cloneItemStack(ItemStack pItemStack, int pStackSize) {
        ItemStack tClonedItemStack = pItemStack.copy();
        tClonedItemStack.setCount(pStackSize);
        return tClonedItemStack;
    }

    public static boolean equals(ItemStack pItemStack1, ItemStack pItemStack2) {
        return (COMPARATOR.compare(pItemStack1, pItemStack2) == 0);
    }

    public static boolean equalsIgnoreStackSize(ItemStack itemStack1, ItemStack itemStack2) {
        if (itemStack1 != null && itemStack2 != null) {
            // Sort on itemID
            if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == 0) {
                // Sort on item
                if (itemStack1.getItem() == itemStack2.getItem()) {
                    // Then sort on meta
                    if (itemStack1.getItemDamage() == itemStack2.getItemDamage()) {
                        // Then sort on NBT
                        if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound()) {
                            // Then sort on stack size
                            if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2)) {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if two ItemStacks are identical enough to be merged
     *
     * @param stack1 - The first stack
     * @param stack2 - The second stack
     * @return true if stacks can be merged, false otherwise
     */
    public static boolean canStacksMerge(ItemStack stack1, ItemStack stack2) {
        if (stack1 == null || stack2 == null) {
            return false;
        }
        if (!stack1.isItemEqual(stack2)) {
            return false;
        }
        return ItemStack.areItemStackTagsEqual(stack1, stack2);

    }

    /**
     * Merges mergeSource into mergeTarget
     *
     * @param mergeSource - The stack to merge into mergeTarget, this stack is not
     *                    modified
     * @param mergeTarget - The target merge, this stack is modified if doMerge is set
     * @param doMerge     - To actually do the merge
     * @return The number of items that was successfully merged.
     */
    public static int mergeStacks(ItemStack mergeSource, ItemStack mergeTarget, boolean doMerge) {
        if (!canStacksMerge(mergeSource, mergeTarget)) {
            return 0;
        }
        int mergeCount = Math.min(mergeTarget.getMaxStackSize() - mergeTarget.getCount(), mergeSource.getAnimationsToGo());
        if (mergeCount < 1) {
            return 0;
        }
        if (doMerge) {
            mergeTarget.grow(mergeCount);
        }
        return mergeCount;
    }

    /**
     * Determines whether the given ItemStack should be considered equivalent
     * for crafting purposes.
     *
     * @param base          The stack to compare to.
     * @param comparison    The stack to compare.
     * @param oreDictionary true to take the Forge OreDictionary into account.
     * @return true if comparison should be considered a crafting equivalent for
     * base.
     */
    public static boolean isCraftingEquivalent(ItemStack base, ItemStack comparison, boolean oreDictionary) {
        if (isMatchingItem(base, comparison, true, false)) {
            return true;
        }
        if (oreDictionary) {
            int[] idBase = OreDictionary.getOreIDs(base);
            return isCraftingEquivalent(idBase, comparison);
        }

        return false;
    }

    public static boolean isCraftingEquivalent(int[] oreIDs, ItemStack comparison) {
        if (oreIDs.length > 0) {
            for (int id : oreIDs) {
                for (ItemStack itemstack : OreDictionary.getOres(OreDictionary.getOreName(id))) {
                    if (comparison.getItem() == itemstack.getItem() && (itemstack.getItemDamage() == OreDictionary.WILDCARD_VALUE || comparison.getItemDamage() == itemstack.getItemDamage())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Compares item id, damage and NBT. Accepts wildcard damage. Ignores damage
     * entirely if the item doesn't have subtypes.
     *
     * @param base       The stack to compare to.
     * @param comparison The stack to compare.
     * @return true if id, damage and NBT match.
     */
    public static boolean isMatchingItem(ItemStack base, ItemStack comparison) {
        return isMatchingItem(base, comparison, true, true);
    }

    /**
     * Compares item id, and optionally damage and NBT. Accepts wildcard damage.
     * Ignores damage entirely if the item doesn't have subtypes.
     *
     * @param a           ItemStack
     * @param b           ItemStack
     * @param matchDamage Whether to check the damage value of the items
     * @param matchNBT    Whether to check the NBT tags on the items
     * @return Whether the items match
     */
    public static boolean isMatchingItem(final ItemStack a, final ItemStack b, final boolean matchDamage, final boolean matchNBT) {
        if (a == null || b == null) {
            return false;
        }
        if (a.getItem() != b.getItem()) {
            return false;
        }
        if (matchDamage && a.getHasSubtypes()) {
            if (!isWildcard(a) && !isWildcard(b)) {
                if (a.getItemDamage() != b.getItemDamage()) {
                    return false;
                }
            }
        }
        if (matchNBT) {
            if (a.getTagCompound() != null && !a.getTagCompound().equals(b.getTagCompound())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWildcard(ItemStack stack) {
        return isWildcard(stack.getItemDamage());
    }

    public static boolean isWildcard(int damage) {
        return damage == -1 || damage == OreDictionary.WILDCARD_VALUE;
    }

    public static boolean hasOreDictEntry(final ItemStack a) {
        int[] oreIDs = OreDictionary.getOreIDs(a);
        return oreIDs != null;
    }

    public static boolean isMatchingOreDict(final ItemStack a, final ItemStack b) {
        if (hasOreDictEntry(a) && hasOreDictEntry(b)) {
            int[] idA = OreDictionary.getOreIDs(a);
            int[] idB = OreDictionary.getOreIDs(b);
            return Arrays.equals(idA, idB);
        }
        return false;
    }

    public static int compare(ItemStack pItemStack1, ItemStack pItemStack2) {
        return COMPARATOR.compare(pItemStack1, pItemStack2);
    }

    public static String toString(ItemStack pItemStack) {
        if (pItemStack != null) {
            return String.format("%sxitemStack[%s@%s]", pItemStack.getCount(), pItemStack.getUnlocalizedName(), pItemStack.getItemDamage());
        }

        return "null";
    }

    public static boolean hasOwner(ItemStack itemStack) {
        return (NBTHelper.hasTag(itemStack, CoreReferences.NBT.OWNER_UUID_MOST_SIG) && NBTHelper.hasTag(itemStack, CoreReferences.NBT.OWNER_UUID_LEAST_SIG)) || NBTHelper.hasTag(itemStack, CoreReferences.NBT.OWNER);
    }

    public static boolean hasOwnerUUID(ItemStack itemStack) {
        return NBTHelper.hasTag(itemStack, CoreReferences.NBT.OWNER_UUID_MOST_SIG) && NBTHelper.hasTag(itemStack, CoreReferences.NBT.OWNER_UUID_LEAST_SIG);
    }

    public static boolean hasOwnerName(ItemStack itemStack) {
        return NBTHelper.hasTag(itemStack, CoreReferences.NBT.OWNER);
    }

    public static String getOwnerName(ItemStack itemStack) {
        if (NBTHelper.hasTag(itemStack, CoreReferences.NBT.OWNER)) {
            return NBTHelper.getString(itemStack, CoreReferences.NBT.OWNER);
        }

        return null;
    }

    public static UUID getOwnerUUID(ItemStack itemStack) {
        if (NBTHelper.hasTag(itemStack, CoreReferences.NBT.OWNER_UUID_MOST_SIG) && NBTHelper.hasTag(itemStack, CoreReferences.NBT.OWNER_UUID_LEAST_SIG)) {
            return new UUID(NBTHelper.getLong(itemStack, CoreReferences.NBT.OWNER_UUID_MOST_SIG), NBTHelper.getLong(itemStack, CoreReferences.NBT.OWNER_UUID_LEAST_SIG));
        }

        return null;
    }

    public static void setOwner(ItemStack itemStack, EntityPlayer entityPlayer) {
        setOwnerName(itemStack, entityPlayer);
        setOwnerUUID(itemStack, entityPlayer);
    }

    public static void setOwnerUUID(ItemStack itemStack, EntityPlayer entityPlayer) {
        NBTHelper.setLong(itemStack, CoreReferences.NBT.OWNER_UUID_MOST_SIG, entityPlayer.getGameProfile().getId().getMostSignificantBits());
        NBTHelper.setLong(itemStack, CoreReferences.NBT.OWNER_UUID_LEAST_SIG, entityPlayer.getGameProfile().getId().getLeastSignificantBits());
    }

    public static void setOwnerName(ItemStack itemStack, EntityPlayer entityPlayer) {
        NBTHelper.setString(itemStack, CoreReferences.NBT.OWNER, entityPlayer.getDisplayNameString());
    }
}
