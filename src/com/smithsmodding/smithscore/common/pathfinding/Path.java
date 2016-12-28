/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.pathfinding;

import javax.annotation.Nullable;
import java.util.*;

public class Path {

    ArrayList<IPathComponent> iComponents;
    @Nullable
    IPathComponent iStartNode = null;
    @Nullable
    IPathComponent iEndNode = null;

    public Path (IPathComponent pStartNode, IPathComponent pEndNode) {
        this(pStartNode, pEndNode, new ArrayList<IPathComponent>());
    }

    public Path (IPathComponent pStartNode, IPathComponent pEndNode, ArrayList<IPathComponent> pComponents) {
        iComponents = pComponents;
        iStartNode = pStartNode;
        iEndNode = pEndNode;
    }

    public void startConstructingReversePath () {
        iComponents.add(iEndNode);
    }

    public void endConstructingReversePath () {
        if (!iComponents.contains(iStartNode))
            iComponents.add(iStartNode);
    }

    public ArrayList<IPathComponent> getComponents () {
        return iComponents;
    }

    @Nullable
    public IPathComponent getStartNode () {
        return iStartNode;
    }

    @Nullable
    public IPathComponent getEndNode () {
        return iEndNode;
    }
}
