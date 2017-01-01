package com.smithsmodding.smithscore.common.structures;

import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public interface IStructure<D extends IStructureData, C extends IStructureController, P extends IStructurePart> {

    @Nonnull
    C getController();

    @Nonnull
    D getData();

    boolean canPartJoin(@Nonnull IStructurePart part);

    @Nonnull
    LinkedHashSet<Coordinate3D> getPartLocations();

    void registerPart(@Nonnull P part);

    void removePart(@Nonnull P part);


    @Nonnull
    Coordinate3D getMasterLocation();

    void setMasterLocation(@Nonnull Coordinate3D masterLocation);
}
