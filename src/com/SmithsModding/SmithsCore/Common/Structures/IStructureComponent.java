/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.Structures;

import com.SmithsModding.SmithsCore.Common.PathFinding.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;

import java.util.concurrent.*;

public interface IStructureComponent extends IPathComponent {
    String getStructureType ();

    ConcurrentHashMap<Coordinate3D, IStructureComponent> getSlaveEntities ();

    void registerNewSlave (TileEntity pNewSlaveEntity);

    void removeSlave (Coordinate3D pSlaveCoordinate);

    Cube getStructureSpace ();

    void initiateAsMasterEntity ();

    IStructureData getStructureRelevantData ();

    void setStructureData (IStructureData pNewData);


    float getDistanceToMasterEntity ();

    boolean isSlaved ();

    IStructureComponent getMasterEntity ();

    void initiateAsSlaveEntity (IStructureComponent pMasterEntity);

    boolean countsAsConnectingComponent ();


    void writeStructureToNBT (NBTTagCompound pTileEntityCompound);

    void readStructureFromNBT (NBTTagCompound pTileEntityCompound);
}
