package com.smithsmodding.smithscore.common.structures;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public interface IStructureData<S extends IStructure> {

    @Nonnull
    NBTTagCompound writeToNBT();

    void readFromNBT(@Nonnull NBTTagCompound compound);

    void onDataMergeInto(@Nonnull IStructureData<S> otherData);
}
