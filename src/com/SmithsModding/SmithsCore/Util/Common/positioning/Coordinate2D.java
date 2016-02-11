/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.common.positioning;

import io.netty.buffer.*;
import net.minecraft.util.*;

public class Coordinate2D {

    int iXCoord;
    int iYCoord;

    public Coordinate2D (int pXCoord, int pYCoord) {
        iXCoord = pXCoord;
        iYCoord = pYCoord;
    }

    public static Coordinate2D fromBytes (ByteBuf pData) {
        return new Coordinate2D(pData.readInt(), pData.readInt());
    }

    @Override
    public String toString () {
        return "Coordinate{" +
                "X=" + iXCoord +
                ", Y=" + iYCoord +
                '}';
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate2D that = (Coordinate2D) o;

        if (iXCoord != that.iXCoord) return false;
        return iYCoord == that.iYCoord;

    }

    @Override
    public int hashCode () {
        return getXComponent() + getYComponent();
    }

    public int getXComponent () {
        return iXCoord;
    }

    public int getYComponent () {
        return iYCoord;
    }

    public void setXComponent (int newX) { iXCoord = newX; }

    public void setYComponent (int newY) { iYCoord = newY; }

    public Coordinate2D moveCoordiante (EnumFacing pDirection, int pDistance) {
        return new Coordinate2D(getXComponent() + ( pDistance * pDirection.getFrontOffsetX() ), getYComponent() + ( pDistance * pDirection.getFrontOffsetY() ));
    }

    public float getDistanceTo (Coordinate2D pCoordinate) {
        return (float) Math.sqrt(Math.pow(getXComponent() - pCoordinate.getXComponent(), 2) + Math.pow(getYComponent() - pCoordinate.getYComponent(), 2));
    }

    public void toBytes (ByteBuf pDataOut) {
        pDataOut.writeInt(getXComponent());
        pDataOut.writeInt(getYComponent());
    }

    public Coordinate2D getTranslatedCoordinate (Coordinate2D distance) {
        return new Coordinate2D(this.iXCoord + distance.iXCoord, this.iYCoord + distance.getYComponent());
    }
}
