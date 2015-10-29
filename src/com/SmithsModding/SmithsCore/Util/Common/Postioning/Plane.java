/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Util.Common.Postioning;

public class Plane {

    private int iWidth;
    private int iHeigth;

    private Coordinate2D iTopLeftFrontCoord;
    private Coordinate2D iLowerRightBackCoord;

    public Plane() {
    }

    public Plane(int pTopLeftXCoord, int pYCoord, int pWidth, int pHeigth, int pDepth) {
        iTopLeftFrontCoord = new Coordinate2D(pTopLeftXCoord, pYCoord);
        iLowerRightBackCoord = new Coordinate2D(pTopLeftXCoord + pWidth, pYCoord + pHeigth);

        this.iWidth = pWidth;
        this.iHeigth = pHeigth;
    }

    public Coordinate2D TopLeftCoord() {
        return this.iTopLeftFrontCoord;
    }

    public Coordinate2D LowerRightCoord() {
        return this.iLowerRightBackCoord;
    }

    public int getWidth() {
        return iWidth;
    }

    public int getHeigth() {
        return iHeigth;
    }

    public Plane Move(int pDeltaX, int pDeltaY, int pDeltaZ) {
        this.iTopLeftFrontCoord.iXCoord += pDeltaX;
        this.iTopLeftFrontCoord.iYCoord += pDeltaY;

        this.iLowerRightBackCoord.iXCoord += pDeltaX;
        this.iLowerRightBackCoord.iYCoord += pDeltaY;

        return this;
    }

    public Plane IncludeCoordinate(Cube pCubeToInclude) {
        this.IncludeCoordinate(pCubeToInclude.TopLeftFrontCoord());
        return this.IncludeCoordinate(pCubeToInclude.LowerRightBackCoord());
    }

    public Plane IncludeCoordinate(Coordinate3D pCoordinateToInclude) {
        return this.IncludeCoordinate(pCoordinateToInclude.getXComponent(), pCoordinateToInclude.getYComponent(), pCoordinateToInclude.getZComponent());
    }

    public Plane IncludeCoordinate(int pXCoord, int pYCoord, int pZCoord) {
        if (pXCoord < this.iTopLeftFrontCoord.getXComponent()) {
            this.ExpandToCoordinate(-1 * Math.abs(pXCoord - iTopLeftFrontCoord.getXComponent()), 0);
        }

        if (pXCoord > this.iLowerRightBackCoord.getXComponent()) {
            this.ExpandToCoordinate(Math.abs(pXCoord - iLowerRightBackCoord.getXComponent()), 0);
        }

        if (pYCoord > this.iTopLeftFrontCoord.getYComponent()) {
            this.ExpandToCoordinate(0, Math.abs(pXCoord - iTopLeftFrontCoord.getYComponent()));
        }

        if (pYCoord < this.iLowerRightBackCoord.getYComponent()) {
            this.ExpandToCoordinate(0, -1 * Math.abs(pXCoord - iLowerRightBackCoord.getYComponent()));
        }
        return this;
    }

    public Plane ExpandToCoordinate(int pDeltaX, int pDeltaY) {
        if (pDeltaX < 0) {
            iTopLeftFrontCoord = new Coordinate2D(iTopLeftFrontCoord.getXComponent() + pDeltaX, iTopLeftFrontCoord.getYComponent());
        }

        if (pDeltaX > 0) {
            iLowerRightBackCoord = new Coordinate2D(iLowerRightBackCoord.getXComponent() + pDeltaX, iLowerRightBackCoord.getYComponent());
        }
        iWidth = iTopLeftFrontCoord.getXComponent() - iLowerRightBackCoord.getXComponent();

        if (pDeltaY < 0) {
            iLowerRightBackCoord = new Coordinate2D(iLowerRightBackCoord.getXComponent(), iLowerRightBackCoord.getYComponent() + pDeltaY);
        }

        if (pDeltaY > 0) {
            iTopLeftFrontCoord = new Coordinate2D(iTopLeftFrontCoord.getXComponent(), iTopLeftFrontCoord.getYComponent() + pDeltaY);
        }
        iHeigth = iTopLeftFrontCoord.getYComponent() - iLowerRightBackCoord.getYComponent();

        return this;
    }

    public boolean ContainsCoordinate(Coordinate2D pCoord) {
        if (pCoord.getYComponent() != iTopLeftFrontCoord.getYComponent())
            return false;

        return this.ContainsCoordinate(pCoord.getXComponent(), pCoord.getYComponent());
    }

    public boolean ContainsCoordinate(int pXCoord, int pYCoord) {
        return this.TopLeftCoord().getXComponent() <= pXCoord && pXCoord <= this.LowerRightCoord().getXComponent() && this.LowerRightCoord().getYComponent() <= pYCoord && pYCoord <= this.TopLeftCoord().getYComponent();
    }

    public boolean Intersects(Plane pPlaneToCheck) {
        return pPlaneToCheck.TopLeftCoord().getXComponent() + pPlaneToCheck.iWidth > this.TopLeftCoord().getXComponent() && pPlaneToCheck.TopLeftCoord().getXComponent() < this.TopLeftCoord().getXComponent() + this.iWidth && pPlaneToCheck.TopLeftCoord().getYComponent() + pPlaneToCheck.iHeigth > this.TopLeftCoord().getYComponent() && pPlaneToCheck.TopLeftCoord().getYComponent() < this.TopLeftCoord().getYComponent() + this.iHeigth;
    }

    public int Contents() {
        return this.iWidth * this.iHeigth;
    }
}
