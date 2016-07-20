package com.smithsmodding.smithscore.common.structures;

import com.smithsmodding.smithscore.common.pathfinding.IPathComponent;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.world.World;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public interface IStructurePart<S extends IStructure> extends IPathComponent {

    Class<S> getStructureType();

    S getStructure();

    void setStructure(S structure);

    World getEnvironment();

    Coordinate3D getLocation();
}
