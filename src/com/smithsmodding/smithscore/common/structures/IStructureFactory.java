package com.smithsmodding.smithscore.common.structures;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public interface IStructureFactory<S extends IStructure, P extends IStructurePart> {

    @Nonnull
    S generateNewStructure(@Nonnull P initialPart);

    @Nonnull
    S loadStructureFromNBT(@Nonnull NBTTagCompound compound);

    @Nonnull
    NBTTagCompound generateNBTFromStructure(@Nonnull S structure);

    @Nonnull
    Class<S> getStructureType();
}
