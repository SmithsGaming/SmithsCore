/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.pathfinding;

import com.smithsmodding.smithscore.util.common.positioning.*;

import java.util.*;

public interface IPathComponent {
    Coordinate3D getLocation ();

    ArrayList<IPathComponent> getValidPathableNeighborComponents ();
}
