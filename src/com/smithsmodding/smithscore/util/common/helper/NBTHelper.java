/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.common.helper;

import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class NBTHelper {

    private static void initNBTTagCompound(@Nonnull ItemStack itemStack) {
        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
    }

    public static boolean hasTag(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        return !itemStack.isEmpty() && itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey(keyName);
    }

    @Nullable
    public static NBTTagCompound getTagCompound (@Nonnull ItemStack itemStack) {
        initNBTTagCompound(itemStack);
        return itemStack.getTagCompound();
    }

    public static void removeTag(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        if (itemStack.getTagCompound() != null) {
            itemStack.getTagCompound().removeTag(keyName);
        }
    }

    public static void setBoolean(@Nonnull ItemStack itemStack, @Nonnull String keyName, boolean keyValue) {
        initNBTTagCompound(itemStack);
        itemStack.getTagCompound().setBoolean(keyName, keyValue);
    }

    public static void setByte(@Nonnull ItemStack itemStack, @Nonnull String keyName, byte keyValue) {
        initNBTTagCompound(itemStack);
        itemStack.getTagCompound().setByte(keyName, keyValue);
    }

    public static void setByteArray(@Nonnull ItemStack itemStack, @Nonnull String keyName, @Nonnull byte[] keyValue) {
        initNBTTagCompound(itemStack);
        itemStack.getTagCompound().setByteArray(keyName, keyValue);
    }

    public static void setDouble(@Nonnull ItemStack itemStack, @Nonnull String keyName, double keyValue) {
        initNBTTagCompound(itemStack);
        itemStack.getTagCompound().setDouble(keyName, keyValue);
    }

    public static void setFloat(@Nonnull ItemStack itemStack, @Nonnull String keyName, float keyValue) {
        initNBTTagCompound(itemStack);
        itemStack.getTagCompound().setFloat(keyName, keyValue);
    }

    public static void setIntArray(@Nonnull ItemStack itemStack, @Nonnull String keyName, @Nonnull int[] keyValue) {
        initNBTTagCompound(itemStack);
        itemStack.getTagCompound().setIntArray(keyName, keyValue);
    }

    public static void setInteger(@Nonnull ItemStack itemStack, @Nonnull String keyName, int keyValue) {
        initNBTTagCompound(itemStack);
        itemStack.getTagCompound().setInteger(keyName, keyValue);
    }

    public static void setLong(@Nonnull ItemStack itemStack, @Nonnull String keyName, long keyValue) {
        initNBTTagCompound(itemStack);
        itemStack.getTagCompound().setLong(keyName, keyValue);
    }

    public static void setShort(@Nonnull ItemStack itemStack, @Nonnull String keyName, short keyValue) {
        initNBTTagCompound(itemStack);
        itemStack.getTagCompound().setShort(keyName, keyValue);
    }

    public static void setString(@Nonnull ItemStack itemStack, @Nonnull String keyName, @Nonnull String keyValue) {
        initNBTTagCompound(itemStack);
        itemStack.getTagCompound().setString(keyName, keyValue);
    }

    public static void setTagCompound(@Nonnull ItemStack itemStack, @Nonnull String keyName, @Nonnull NBTTagCompound keyValue) {
        initNBTTagCompound(itemStack);
        itemStack.getTagCompound().setTag(keyName, keyValue);
    }

    public static void setTagList(@Nonnull ItemStack itemStack, @Nonnull String keyName, @Nonnull NBTTagList keyValue) {
        initNBTTagCompound(itemStack);
        itemStack.getTagCompound().setTag(keyName, keyValue);
    }

    public static void setUUID(@Nonnull ItemStack itemStack) {
        initNBTTagCompound(itemStack);
        if (!hasTag(itemStack, CoreReferences.NBT.UUID_MOST_SIG) && !hasTag(itemStack, CoreReferences.NBT.UUID_LEAST_SIG)) {
            UUID itemUUID = UUID.randomUUID();
            setLong(itemStack, CoreReferences.NBT.UUID_MOST_SIG, itemUUID.getMostSignificantBits());
            setLong(itemStack, CoreReferences.NBT.UUID_LEAST_SIG, itemUUID.getLeastSignificantBits());
        }
    }

    public static boolean getBoolean(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        initNBTTagCompound(itemStack);
        if (!itemStack.getTagCompound().hasKey(keyName)) {
            setBoolean(itemStack, keyName, false);
        }
        return itemStack.getTagCompound().getBoolean(keyName);
    }

    public static byte getByte(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        initNBTTagCompound(itemStack);
        if (!itemStack.getTagCompound().hasKey(keyName)) {
            setByte(itemStack, keyName, (byte) 0);
        }
        return itemStack.getTagCompound().getByte(keyName);
    }

    public static byte[] getByteArray(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        initNBTTagCompound(itemStack);
        if (!itemStack.getTagCompound().hasKey(keyName)) {
            setByteArray(itemStack, keyName, new byte[0]);
        }
        return itemStack.getTagCompound().getByteArray(keyName);
    }

    public static double getDouble(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        initNBTTagCompound(itemStack);
        if (!itemStack.getTagCompound().hasKey(keyName)) {
            setDouble(itemStack, keyName, 0.0D);
        }
        return itemStack.getTagCompound().getDouble(keyName);
    }

    public static float getFloat(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        initNBTTagCompound(itemStack);
        if (!itemStack.getTagCompound().hasKey(keyName)) {
            setFloat(itemStack, keyName, 0.0F);
        }
        return itemStack.getTagCompound().getFloat(keyName);
    }

    public static int[] getIntArray(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        initNBTTagCompound(itemStack);
        if (!itemStack.getTagCompound().hasKey(keyName)) {
            setIntArray(itemStack, keyName, new int[0]);
        }
        return itemStack.getTagCompound().getIntArray(keyName);
    }

    public static int getInteger(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        initNBTTagCompound(itemStack);
        if (!itemStack.getTagCompound().hasKey(keyName)) {
            setInteger(itemStack, keyName, 0);
        }
        return itemStack.getTagCompound().getInteger(keyName);
    }

    public static long getLong(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        initNBTTagCompound(itemStack);
        if (!itemStack.getTagCompound().hasKey(keyName)) {
            setLong(itemStack, keyName, (long) 0);
        }
        return itemStack.getTagCompound().getLong(keyName);
    }

    public static short getShort(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        initNBTTagCompound(itemStack);
        if (!itemStack.getTagCompound().hasKey(keyName)) {
            setShort(itemStack, keyName, (short) 0);
        }
        return itemStack.getTagCompound().getShort(keyName);
    }

    public static String getString(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        initNBTTagCompound(itemStack);
        if (!itemStack.getTagCompound().hasKey(keyName)) {
            setString(itemStack, keyName, "");
        }
        return itemStack.getTagCompound().getString(keyName);
    }

    public static NBTTagCompound getTagCompound(@Nonnull ItemStack itemStack, @Nonnull String keyName) {
        initNBTTagCompound(itemStack);
        if (!itemStack.getTagCompound().hasKey(keyName)) {
            setTagCompound(itemStack, keyName, new NBTTagCompound());
        }
        return itemStack.getTagCompound().getCompoundTag(keyName);
    }

    public static NBTTagList getTagList(@Nonnull ItemStack itemStack, @Nonnull String keyName, int nbtBaseType) {
        initNBTTagCompound(itemStack);
        if (!itemStack.getTagCompound().hasKey(keyName)) {
            setTagList(itemStack, keyName, new NBTTagList());
        }
        return itemStack.getTagCompound().getTagList(keyName, nbtBaseType);
    }

    public static boolean hasUUID(@Nonnull ItemStack itemStack) {
        return hasTag(itemStack, CoreReferences.NBT.UUID_MOST_SIG) && hasTag(itemStack, CoreReferences.NBT.UUID_LEAST_SIG);
    }
}
