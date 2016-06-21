/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.common.positioning;

import java.awt.*;

public class Plane {

    private int iWidth;
    private int iHeigth;

    private Coordinate2D iTopLeftFrontCoord;
    private Coordinate2D iLowerRightBackCoord;

    public Plane () {
    }

    public Plane (Coordinate2D coordinate2D, int width, int heigth) {
        this(coordinate2D.iXCoord, coordinate2D.iYCoord, width, heigth);
    }

    public Plane (int pTopLeftXCoord, int pYCoord, int pWidth, int pHeigth) {
        iTopLeftFrontCoord = new Coordinate2D(pTopLeftXCoord, pYCoord);
        iLowerRightBackCoord = new Coordinate2D(pTopLeftXCoord + pWidth, pYCoord + pHeigth);

        this.iWidth = pWidth;
        this.iHeigth = pHeigth;
    }

    public Plane (Coordinate2D topLeft, Coordinate2D lowerRight)
    {
        iTopLeftFrontCoord = topLeft;
        iLowerRightBackCoord = lowerRight;

        if (iTopLeftFrontCoord.getXComponent() < 0)
            this.iWidth = Math.abs(iTopLeftFrontCoord.getXComponent()) + iLowerRightBackCoord.getXComponent();
        else
            this.iWidth = iLowerRightBackCoord.getXComponent() - iTopLeftFrontCoord.getXComponent();

        if (iTopLeftFrontCoord.getYComponent() < 0)
            this.iHeigth = Math.abs(iTopLeftFrontCoord.getYComponent()) + iLowerRightBackCoord.getYComponent();
        else
            this.iHeigth = iLowerRightBackCoord.getYComponent() - iTopLeftFrontCoord.getYComponent();
    }

    public Coordinate2D TopLeftCoord () {
        return this.iTopLeftFrontCoord;
    }

    public Coordinate2D LowerRightCoord () {
        return this.iLowerRightBackCoord;
    }

    public int getWidth () {
        return iWidth;
    }

    public int getHeigth () {
        return iHeigth;
    }

    public Plane Move (int pDeltaX, int pDeltaY) {
        this.iTopLeftFrontCoord.iXCoord += pDeltaX;
        this.iTopLeftFrontCoord.iYCoord += pDeltaY;

        this.iLowerRightBackCoord.iXCoord += pDeltaX;
        this.iLowerRightBackCoord.iYCoord += pDeltaY;

        return this;
    }

    public Plane IncludeCoordinate (Plane planeToInclude) {
        this.IncludeCoordinate(planeToInclude.TopLeftCoord());
        return this.IncludeCoordinate(planeToInclude.LowerRightCoord());
    }

    public Plane IncludeCoordinate (Coordinate2D pCoordinateToInclude) {
        return this.IncludeCoordinate(pCoordinateToInclude.getXComponent(), pCoordinateToInclude.getYComponent());
    }

    public Plane IncludeCoordinate (int pXCoord, int pYCoord) {
        if (pXCoord < this.iTopLeftFrontCoord.getXComponent()) {
            this.ExpandToCoordinate(-1 * Math.abs(pXCoord - iTopLeftFrontCoord.getXComponent()), 0);
        }

        if (pXCoord > this.iLowerRightBackCoord.getXComponent()) {
            this.ExpandToCoordinate(Math.abs(pXCoord - iLowerRightBackCoord.getXComponent()), 0);
        }

        if (pYCoord > this.iLowerRightBackCoord.getYComponent()) {
            this.ExpandToCoordinate(0, Math.abs(pYCoord - iLowerRightBackCoord.getYComponent()));
        }

        if (pYCoord < this.iTopLeftFrontCoord.getYComponent()) {
            this.ExpandToCoordinate(0, -1 * Math.abs(pYCoord - iTopLeftFrontCoord.getYComponent()));
        }
        return this;
    }

    public Plane ExpandToCoordinate (int pDeltaX, int pDeltaY) {
        if (pDeltaX < 0) {
            iTopLeftFrontCoord = new Coordinate2D(iTopLeftFrontCoord.getXComponent() + pDeltaX, iTopLeftFrontCoord.getYComponent());
        }

        if (pDeltaX > 0) {
            iLowerRightBackCoord = new Coordinate2D(iLowerRightBackCoord.getXComponent() + pDeltaX, iLowerRightBackCoord.getYComponent());
        }
        iWidth = iLowerRightBackCoord.getXComponent() - iTopLeftFrontCoord.getXComponent();

        if (pDeltaY < 0) {
            iTopLeftFrontCoord = new Coordinate2D(iTopLeftFrontCoord.getXComponent(), iTopLeftFrontCoord.getYComponent() + pDeltaY);
        }

        if (pDeltaY > 0) {
            iLowerRightBackCoord = new Coordinate2D(iLowerRightBackCoord.getXComponent(), iLowerRightBackCoord.getYComponent() + pDeltaY);
        }
        iHeigth = iLowerRightBackCoord.getYComponent() - iTopLeftFrontCoord.getYComponent();

        return this;
    }

    public boolean ContainsCoordinate (Coordinate2D pCoord) {
        if (pCoord.getYComponent() != iTopLeftFrontCoord.getYComponent())
            return false;

        return this.ContainsCoordinate(pCoord.getXComponent(), pCoord.getYComponent());
    }

    public boolean ContainsCoordinate (int pXCoord, int pYCoord) {
        return this.TopLeftCoord().getXComponent() <= pXCoord && pXCoord <= this.LowerRightCoord().getXComponent() && this.TopLeftCoord().getYComponent() <= pYCoord && pYCoord <= this.LowerRightCoord().getYComponent();
    }

    public boolean Intersects (Plane pPlaneToCheck) {
        return pPlaneToCheck.TopLeftCoord().getXComponent() + pPlaneToCheck.iWidth > this.TopLeftCoord().getXComponent() && pPlaneToCheck.TopLeftCoord().getXComponent() < this.TopLeftCoord().getXComponent() + this.iWidth && pPlaneToCheck.TopLeftCoord().getYComponent() + pPlaneToCheck.iHeigth > this.TopLeftCoord().getYComponent() && pPlaneToCheck.TopLeftCoord().getYComponent() < this.TopLeftCoord().getYComponent() + this.iHeigth;
    }

    public Plane getInstersection (Plane toIntersect)
    {
        if (!Intersects(toIntersect))
            return null;
        
        int x1, y1, x2, y2;
        
        if (toIntersect.TopLeftCoord().getXComponent() <= TopLeftCoord().getXComponent())
            x1 = TopLeftCoord().getXComponent();
        else
            x1 = toIntersect.TopLeftCoord().getXComponent();
        
        if (toIntersect.LowerRightCoord().getXComponent() <= LowerRightCoord().getXComponent())
            x2 = toIntersect.LowerRightCoord().getXComponent();
        else
            x2 = LowerRightCoord().getXComponent();


        if (toIntersect.TopLeftCoord().getYComponent() <= TopLeftCoord().getYComponent())
            y1 = TopLeftCoord().getYComponent();
        else
            y1 = toIntersect.TopLeftCoord().getYComponent();

        if (toIntersect.LowerRightCoord().getYComponent() <= LowerRightCoord().getYComponent())
            y2 = toIntersect.LowerRightCoord().getYComponent();
        else
            y2 = LowerRightCoord().getYComponent();
        
        return new Plane(new Coordinate2D(x1, y1), new Coordinate2D(x2, y2));
    }
    
    public int Contents () {
        return this.iWidth * this.iHeigth;
    }

    public Rectangle toRectangle() {
        return new Rectangle(TopLeftCoord().getXComponent(), TopLeftCoord().getYComponent(), getWidth(), getHeigth());
    }
}
