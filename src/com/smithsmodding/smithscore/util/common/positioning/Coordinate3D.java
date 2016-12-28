/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.common.positioning;

import com.smithsmodding.smithscore.util.CoreReferences;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class Coordinate3D {
    float xCoord;
    float yCoord;
    float zCoord;

    public Coordinate3D(float xCoord, float yCoord, float zCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
    }

    public Coordinate3D(@Nonnull BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    @Nonnull
    public static Coordinate3D fromNBT (@Nonnull NBTTagCompound compound) {
        return new Coordinate3D(compound.getFloat(CoreReferences.NBT.Coordinates.X), compound.getFloat(CoreReferences.NBT.Coordinates.Y), compound.getFloat(CoreReferences.NBT.Coordinates.Z));
    }

    @Nonnull
    public static Coordinate3D fromBytes (@Nonnull ByteBuf pData) {
        return new Coordinate3D(pData.readFloat(), pData.readFloat(), pData.readFloat());
    }

    public void toBytes (@Nonnull ByteBuf pDataOut) {
        pDataOut.writeFloat(getXComponent());
        pDataOut.writeFloat(getYComponent());
        pDataOut.writeFloat(getZComponent());
    }

    @Nonnull
    public BlockPos toBlockPos () {
        return new BlockPos(getXComponent(), getYComponent(), getZComponent());
    }

    @Nonnull
    public NBTTagCompound toCompound () {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setFloat(CoreReferences.NBT.Coordinates.X, xCoord);
        compound.setFloat(CoreReferences.NBT.Coordinates.Y, yCoord);
        compound.setFloat(CoreReferences.NBT.Coordinates.Z, zCoord);

        return compound;
    }

    @Nonnull
    @Override
    public String toString () {
        return "Coordinate{" +
                "xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                ", zCoord=" + zCoord +
                '}';
    }

    @Override
    public boolean equals (@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate3D that = (Coordinate3D) o;

        if (xCoord != that.xCoord) return false;
        if (yCoord != that.yCoord) return false;
        return zCoord == that.zCoord;

    }

    @Override
    public int hashCode () {
        return (int) (getXComponent() + getYComponent() + getZComponent());
    }

    public float getXComponent() {
        return xCoord;
    }

    public float getYComponent() {
        return yCoord;
    }

    public float getZComponent() {
        return zCoord;
    }

    @Nonnull
    public Coordinate3D moveCoordinate(@Nonnull EnumFacing direction, int distance) {
        return new Coordinate3D(toBlockPos().offset(direction, distance));
    }

    public float getDistanceTo (@Nonnull Coordinate3D pCoordinate) {
        return (float) Math.sqrt(Math.pow(getXComponent() - pCoordinate.getXComponent(), 2) + Math.pow(getYComponent() - pCoordinate.getYComponent(), 2) + Math.pow(getZComponent() - pCoordinate.getZComponent(), 2));
    }
}
