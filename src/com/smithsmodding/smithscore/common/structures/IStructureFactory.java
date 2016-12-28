package com.smithsmodding.smithscore.common.structures;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public interface IStructureFactory<S extends IStructure, P extends IStructurePart> {

    @Nonnull
    S generateNewStructure(P initialPart);

    @Nonnull
    S loadStructureFromNBT(NBTTagCompound compound);

    @Nonnull
    NBTTagCompound generateNBTFromStructure(S structure);

    @Nonnull
    Class<S> getStructureType();
}
