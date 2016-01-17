/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.Common.Postioning;

import net.minecraft.util.*;

public class Cube {

    private int iWidth;
    private int iHeigth;
    private int iDepth;

    private Coordinate3D iTopLeftFrontCoord;
    private Coordinate3D iLowerRightBackCoord;

    public Cube() {
    }

    public Cube(int pTopLeftXCoord, int pYCoord, int pTopLeftZCoord, int pWidth, int pHeigth, int pDepth) {
        iTopLeftFrontCoord = new Coordinate3D(pTopLeftXCoord, pYCoord, pTopLeftZCoord);
        iLowerRightBackCoord = new Coordinate3D(pTopLeftXCoord + pWidth, pYCoord + pHeigth, pTopLeftZCoord + pDepth);

        this.iWidth = pWidth;
        this.iHeigth = pHeigth;
        this.iDepth = pDepth;
    }

    public int getWidth() {
        return iWidth;
    }

    public int getHeigth() {
        return iHeigth;
    }

    public int getDepth() {
        return iDepth;
    }

    public Coordinate3D TopLeftFrontCoord() {
        return this.iTopLeftFrontCoord;
    }

    public Coordinate3D LowerRightBackCoord() {
        return this.iLowerRightBackCoord;
    }


    public Cube Move(int pDeltaX, int pDeltaY, int pDeltaZ) {
        this.iTopLeftFrontCoord.xCoord += pDeltaX;
        this.iTopLeftFrontCoord.yCoord += pDeltaY;
        this.iTopLeftFrontCoord.zCoord += pDeltaZ;

        this.iLowerRightBackCoord.xCoord += pDeltaX;
        this.iLowerRightBackCoord.yCoord += pDeltaY;
        this.iLowerRightBackCoord.zCoord += pDeltaZ;

        return this;
    }

    public Cube IncludeCoordinate(Cube pCubeToInclude) {
        this.IncludeCoordinate(pCubeToInclude.TopLeftFrontCoord());
        return this.IncludeCoordinate(pCubeToInclude.LowerRightBackCoord());
    }

    public Cube IncludeCoordinate(Coordinate3D pCoordinateToInclude) {
        return this.IncludeCoordinate(pCoordinateToInclude.getXComponent(), pCoordinateToInclude.getYComponent(), pCoordinateToInclude.getZComponent());
    }

    public Cube IncludeCoordinate(int pXCoord, int pYCoord, int pZCoord) {
        if (pXCoord < this.iTopLeftFrontCoord.getXComponent()) {
            this.ExpandToCoordinate(-1 * Math.abs(pXCoord - iTopLeftFrontCoord.getXComponent()), 0, 0);
        }

        if (pXCoord > this.iLowerRightBackCoord.getXComponent()) {
            this.ExpandToCoordinate(Math.abs(pXCoord - iLowerRightBackCoord.getXComponent()), 0, 0);
        }

        if (pYCoord > this.iLowerRightBackCoord.getYComponent()) {
            this.ExpandToCoordinate(0, Math.abs(pYCoord - iLowerRightBackCoord.getYComponent()), 0);
        }

        if (pYCoord < this.iTopLeftFrontCoord.getYComponent()) {
            this.ExpandToCoordinate(0, -1 * Math.abs(pYCoord - iTopLeftFrontCoord.getYComponent()), 0);
        }

        if (pZCoord < this.iTopLeftFrontCoord.getZComponent()) {
            this.ExpandToCoordinate(0, 0, -1 * Math.abs(pZCoord - iTopLeftFrontCoord.getZComponent()));
        }

        if (pZCoord > iLowerRightBackCoord.getZComponent()) {
            this.ExpandToCoordinate(0, 0, Math.abs(pZCoord - iLowerRightBackCoord.getZComponent()));
        }

        return this;
    }

    public Cube ExpandToCoordinate(int pDeltaX, int pDeltaY, int pDeltaZ) {
        if (pDeltaX < 0) {
            iTopLeftFrontCoord = new Coordinate3D(iTopLeftFrontCoord.getXComponent() + pDeltaX, iTopLeftFrontCoord.getYComponent(), iTopLeftFrontCoord.getZComponent());
        }

        if (pDeltaX > 0) {
            iLowerRightBackCoord = new Coordinate3D(iLowerRightBackCoord.getXComponent() + pDeltaX, iLowerRightBackCoord.getYComponent(), iLowerRightBackCoord.getZComponent());
        }
        iWidth = iTopLeftFrontCoord.getXComponent() - iLowerRightBackCoord.getXComponent();

        if (pDeltaY < 0) {
            iLowerRightBackCoord = new Coordinate3D(iLowerRightBackCoord.getXComponent(), iLowerRightBackCoord.getYComponent() + pDeltaY, iLowerRightBackCoord.getZComponent());
        }

        if (pDeltaY > 0) {
            iTopLeftFrontCoord = new Coordinate3D(iTopLeftFrontCoord.getXComponent(), iTopLeftFrontCoord.getYComponent() + pDeltaY, iTopLeftFrontCoord.getZComponent());
        }
        iHeigth = iTopLeftFrontCoord.getYComponent() - iLowerRightBackCoord.getYComponent();

        if (pDeltaZ < 0) {
            iTopLeftFrontCoord = new Coordinate3D(iTopLeftFrontCoord.getXComponent(), iTopLeftFrontCoord.getYComponent(), iTopLeftFrontCoord.getZComponent() + pDeltaZ);
        }

        if (pDeltaZ > 0) {
            iLowerRightBackCoord = new Coordinate3D(iLowerRightBackCoord.getXComponent(), iLowerRightBackCoord.getYComponent(), iLowerRightBackCoord.getZComponent() + pDeltaZ);
        }
        iDepth = iTopLeftFrontCoord.getZComponent() - iLowerRightBackCoord.getZComponent();

        return this;
    }

    public boolean ContainsCoordinate(Coordinate3D pCoord) {
        if (pCoord.getYComponent() != iTopLeftFrontCoord.getYComponent())
            return false;

        return this.ContainsCoordinate(pCoord.getXComponent(), pCoord.getYComponent(), pCoord.getZComponent());
    }

    public boolean ContainsCoordinate(int pXCoord, int pYCoord, int pZCoord) {
        return this.TopLeftFrontCoord().getXComponent() <= pXCoord && pXCoord <= this.LowerRightBackCoord().getXComponent() && this.LowerRightBackCoord().getYComponent() <= pYCoord && pYCoord <= this.TopLeftFrontCoord().getYComponent() && this.TopLeftFrontCoord().getZComponent() <= pZCoord && pZCoord < this.LowerRightBackCoord().getZComponent();
    }

    public boolean Intersects(Cube pCubeToCheck) {
        return pCubeToCheck.TopLeftFrontCoord().getXComponent() + pCubeToCheck.iWidth > this.TopLeftFrontCoord().getXComponent() && pCubeToCheck.TopLeftFrontCoord().getXComponent() < this.TopLeftFrontCoord().getXComponent() + this.iWidth && pCubeToCheck.TopLeftFrontCoord().getYComponent() + pCubeToCheck.iHeigth > this.TopLeftFrontCoord().getYComponent() && pCubeToCheck.TopLeftFrontCoord().getYComponent() < this.TopLeftFrontCoord().getYComponent() + this.iHeigth && pCubeToCheck.TopLeftFrontCoord().getZComponent() + pCubeToCheck.iDepth > this.TopLeftFrontCoord().getZComponent() && pCubeToCheck.TopLeftFrontCoord().getZComponent() < this.TopLeftFrontCoord().getZComponent() + this.iDepth;
    }

    public AxisAlignedBB BoundingBox() {
        int tDiffX = 1;
        int tDiffY = 1;
        int tDiffZ = 1;

        if (iWidth == 0)
            tDiffX = 1;

        if (iHeigth == 0)
            tDiffY = 1;

        if (iDepth == 0)
            tDiffZ = 1;


        return new AxisAlignedBB(iTopLeftFrontCoord.getXComponent() - tDiffX, iTopLeftFrontCoord.getYComponent() - tDiffY, iTopLeftFrontCoord.getZComponent() - tDiffZ, iLowerRightBackCoord.getXComponent(), iLowerRightBackCoord.getYComponent(), iLowerRightBackCoord.getZComponent());
    }

    public int Contents() {
        return this.iWidth * this.iHeigth * this.iDepth;
    }

}
