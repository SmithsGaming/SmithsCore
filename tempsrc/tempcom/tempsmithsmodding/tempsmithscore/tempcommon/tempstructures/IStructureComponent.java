/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.structures;

import com.smithsmodding.smithscore.common.pathfinding.*;
import com.smithsmodding.smithscore.util.common.positioning.*;

import java.util.*;

public interface IStructureComponent extends IPathComponent {

    String getStructureTypeUniqueID ();

    Cube getStructureBoundingBox ();

    boolean countsAsConnectingComponent ();

    IStructureData getStructureData ();


    void initiateAsMasterEntity ();

    void initiateAsSlaveEntity (Coordinate3D masterLocation);


    ArrayList<Coordinate3D> getSlaveCoordinates ();

    void setSlaveCoordinates (ArrayList<Coordinate3D> newSlaveCoordinates);

    void registerNewSlave (Coordinate3D newSlaveLocation);

    void removeSlave (Coordinate3D slaveLocation);


    boolean isSlaved ();

    float getDistanceToMasterEntity ();

    Coordinate3D getMasterLocation ();

    void setMasterLocation (Coordinate3D newMasterLocation);
}
