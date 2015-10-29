/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Util.Common;

import com.SmithsModding.SmithsCore.Util.Common.Postioning.Coordinate2D;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.Coordinate3D;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {

    public static Coordinate2D readCoordinate2DFromNBT(NBTTagCompound pCoordinateData) {
        return new Coordinate2D(pCoordinateData.getInteger("XCoord"), pCoordinateData.getInteger("YCoord"));
    }

    public static Coordinate3D readCoordinate3DFromNBT(NBTTagCompound pCoordinateData) {
        return new Coordinate3D(pCoordinateData.getInteger("XCoord"), pCoordinateData.getInteger("YCoord"), pCoordinateData.getInteger("ZCoord"));
    }


    public static NBTTagCompound writeCoordinate2DToNBT(Coordinate2D pCoordinate) {
        NBTTagCompound tCoordinateData = new NBTTagCompound();

        tCoordinateData.setInteger("XCoord", pCoordinate.getXComponent());
        tCoordinateData.setInteger("YCoord", pCoordinate.getYComponent());

        return tCoordinateData;
    }

    public static NBTTagCompound writeCoordinate3DToNBT(Coordinate3D pCoordinate) {
        NBTTagCompound tCoordinateData = new NBTTagCompound();

        tCoordinateData.setInteger("XCoord", pCoordinate.getXComponent());
        tCoordinateData.setInteger("YCoord", pCoordinate.getYComponent());
        tCoordinateData.setInteger("ZCoord", pCoordinate.getZComponent());

        return tCoordinateData;
    }
}
