/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Util.Common.Postioning;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;


public class Coordinate3D {
    int iXCoord;
    int iYCoord;
    int iZCoord;

    public Coordinate3D(int pXCoord, int pYCoord, int pZCoord) {
        iXCoord = pXCoord;
        iYCoord = pYCoord;
        iZCoord = pZCoord;
    }

    public Coordinate3D(BlockPos pPos)
    {
        this(pPos.getX(), pPos.getY(), pPos.getZ());
    }

    public static Coordinate3D fromBytes(ByteBuf pData) {
        return new Coordinate3D(pData.readInt(), pData.readInt(), pData.readInt());
    }

    public void toBytes(ByteBuf pDataOut) {
        pDataOut.writeInt(getXComponent());
        pDataOut.writeInt(getYComponent());
        pDataOut.writeInt(getZComponent());
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "iXCoord=" + iXCoord +
                ", iYCoord=" + iYCoord +
                ", iZCoord=" + iZCoord +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate3D that = (Coordinate3D) o;

        if (iXCoord != that.iXCoord) return false;
        if (iYCoord != that.iYCoord) return false;
        return iZCoord == that.iZCoord;

    }

    @Override
    public int hashCode() {
        return getXComponent() + getYComponent() + getZComponent();
    }

    public int getXComponent() {
        return iXCoord;
    }

    public int getYComponent() {
        return iYCoord;
    }

    public int getZComponent() {
        return iZCoord;
    }

    public Coordinate3D moveCoordiante(EnumFacing pDirection, int pDistance) {
        return new Coordinate3D(getXComponent() + (pDistance * pDirection.getFrontOffsetX()), getYComponent() + (pDistance * pDirection.getFrontOffsetY()), getZComponent() + (pDistance * pDirection.getFrontOffsetZ()));
    }

    public float getDistanceTo(Coordinate3D pCoordinate) {
        return (float) Math.sqrt(Math.pow(getXComponent() - pCoordinate.getXComponent(), 2) + Math.pow(getYComponent() - pCoordinate.getYComponent(), 2) + Math.pow(getZComponent() - pCoordinate.getZComponent(), 2));
    }

    public BlockPos toBlockPos()
    {
        return new BlockPos(getXComponent(), getYComponent(), getZComponent());
    }
}
