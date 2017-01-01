/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.pathfinding;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class Path {

    ArrayList<IPathComponent> iComponents;
    @Nullable
    IPathComponent iStartNode = null;
    @Nullable
    IPathComponent iEndNode = null;

    public Path(@Nonnull IPathComponent pStartNode, @Nonnull IPathComponent pEndNode) {
        this(pStartNode, pEndNode, new ArrayList<IPathComponent>());
    }

    public Path(@Nonnull IPathComponent pStartNode, @Nonnull IPathComponent pEndNode, @Nonnull ArrayList<IPathComponent> pComponents) {
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

    @Nonnull
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
