/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.PathFinding;

import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;

import java.util.*;

public interface IPathComponent {
    Coordinate3D getLocation ();

    ArrayList<IPathComponent> getValidPathableNeighborComponents ();
}
