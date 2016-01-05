/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Util.Common.Postioning;

import com.SmithsModding.SmithsCore.Util.*;
import io.netty.buffer.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;


public class Coordinate3D {
    int xCoord;
    int yCoord;
    int zCoord;

    public Coordinate3D(int pXCoord, int pYCoord, int pZCoord) {
        xCoord = pXCoord;
        yCoord = pYCoord;
        zCoord = pZCoord;
    }

    public Coordinate3D(BlockPos pPos)
    {
        this(pPos.getX(), pPos.getY(), pPos.getZ());
    }

    public static Coordinate3D fromNBT (NBTTagCompound compound) {
        return new Coordinate3D(compound.getInteger(CoreReferences.NBT.Coordinates.X), compound.getInteger(CoreReferences.NBT.Coordinates.Y), compound.getInteger(CoreReferences.NBT.Coordinates.Z));
    }

    public static Coordinate3D fromBytes(ByteBuf pData) {
        return new Coordinate3D(pData.readInt(), pData.readInt(), pData.readInt());
    }

    public void toBytes(ByteBuf pDataOut) {
        pDataOut.writeInt(getXComponent());
        pDataOut.writeInt(getYComponent());
        pDataOut.writeInt(getZComponent());
    }

    public BlockPos toBlockPos () {
        return new BlockPos(getXComponent(), getYComponent(), getZComponent());
    }

    public NBTTagCompound toCompound () {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setInteger(CoreReferences.NBT.Coordinates.X, xCoord);
        compound.setInteger(CoreReferences.NBT.Coordinates.Y, yCoord);
        compound.setInteger(CoreReferences.NBT.Coordinates.Z, zCoord);

        return compound;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                ", zCoord=" + zCoord +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate3D that = (Coordinate3D) o;

        if (xCoord != that.xCoord) return false;
        if (yCoord != that.yCoord) return false;
        return zCoord == that.zCoord;

    }

    @Override
    public int hashCode() {
        return getXComponent() + getYComponent() + getZComponent();
    }

    public int getXComponent() {
        return xCoord;
    }

    public int getYComponent() {
        return yCoord;
    }

    public int getZComponent() {
        return zCoord;
    }

    public Coordinate3D moveCoordinate (EnumFacing pDirection, int pDistance) {
        return new Coordinate3D(getXComponent() + ( pDistance * pDirection.getDirectionVec().getX() ), getYComponent() + ( pDistance * pDirection.getDirectionVec().getY() ), getZComponent() + ( pDistance * pDirection.getFrontOffsetZ() ));
    }

    public float getDistanceTo(Coordinate3D pCoordinate) {
        return (float) Math.sqrt(Math.pow(getXComponent() - pCoordinate.getXComponent(), 2) + Math.pow(getYComponent() - pCoordinate.getYComponent(), 2) + Math.pow(getZComponent() - pCoordinate.getZComponent(), 2));
    }
}
