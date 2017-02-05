package com.smithsmodding.smithscore.common.structures;

import com.smithsmodding.smithscore.common.pathfinding.IPathComponent;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public interface IStructurePart<S extends IStructure> extends IPathComponent {

    @Nonnull
    Class<S> getStructureType();

    @Nullable
    S getStructure();

    void setStructure(@Nullable S structure);

    @Nonnull
    World getEnvironment();

    @Nonnull
    Coordinate3D getLocation();
}
