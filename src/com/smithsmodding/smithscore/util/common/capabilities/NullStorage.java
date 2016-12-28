package com.smithsmodding.smithscore.util.common.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 09.10.2016)
 */
public class NullStorage<T extends Object> implements Capability.IStorage<T> {
    @Nonnull
    @Override
    public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
        return new NBTTagCompound();
    }

    @Override
    public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
        return;
    }
}
