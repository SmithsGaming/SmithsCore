/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Util.Common;
/*
 *   ItemStackHelper
 *   Created by: Orion
 *   Created on: 16-1-2015
 */

import com.SmithsModding.SmithsCore.Util.CoreReferences;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Comparator;
import java.util.UUID;

public class ItemStackHelper


{
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
                                    return (pItemStack1.stackSize - pItemStack2.stackSize);
                                } else {
                                    return (pItemStack1.getTagCompound().hashCode() - pItemStack2.getTagCompound().hashCode());
                                }
                            } else if (!(pItemStack1.hasTagCompound()) && pItemStack2.hasTagCompound()) {
                                return -1;
                            } else if (pItemStack1.hasTagCompound() && !(pItemStack2.hasTagCompound())) {
                                return 1;
                            } else {
                                return (pItemStack1.stackSize - pItemStack2.stackSize);
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
        tClonedItemStack.stackSize = pStackSize;
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

    public static int compare(ItemStack pItemStack1, ItemStack pItemStack2) {
        return COMPARATOR.compare(pItemStack1, pItemStack2);
    }

    public static String toString(ItemStack pItemStack) {
        if (pItemStack != null) {
            return String.format("%sxitemStack[%s@%s]", pItemStack.stackSize, pItemStack.getUnlocalizedName(), pItemStack.getItemDamage());
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
