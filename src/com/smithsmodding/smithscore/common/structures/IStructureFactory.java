package com.smithsmodding.smithscore.common.structures;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public interface IStructureFactory<S extends IStructure, P extends IStructurePart> {

    S generateNewStructure(P initialPart);

    S loadStructureFromNBT(NBTTagCompound compound);

    NBTTagCompound generateNBTFromStructure(S structure);

    Class<S> getStructureType();
}
