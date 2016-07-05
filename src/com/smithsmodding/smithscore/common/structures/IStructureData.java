package com.smithsmodding.smithscore.common.structures;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public interface IStructureData<S extends IStructure> {

    NBTTagCompound writeToNBT();

    void readFromNBT(NBTTagCompound compound);

    void onDataMergeInto(IStructureData<S> otherData);
}
