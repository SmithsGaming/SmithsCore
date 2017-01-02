/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.common.positioning;

import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nonnull;

public class Cube {

    private float width;
    private float height;
    private float depth;

    private Coordinate3D tlfCoord;
    private Coordinate3D lrbCoord;

    public Cube () {
    }

    public Cube(float tlXCoord, float tlYCoord, float tlZCoord, float width, float height, float depth) {
        tlfCoord = new Coordinate3D(tlXCoord, tlYCoord, tlZCoord);
        lrbCoord = new Coordinate3D(tlXCoord + width, tlYCoord + height, tlZCoord + depth);

        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public float getWidth() {
        return width;
    }

    public float getHeigth() {
        return height;
    }

    public float getDepth() {
        return depth;
    }

    @Nonnull
    public Coordinate3D getTopLeftFrontCoord() {
        return this.tlfCoord;
    }

    @Nonnull
    public Coordinate3D getLowerRightBackCoord() {
        return this.lrbCoord;
    }


    @Nonnull
    public Cube Move(int deltaX, int deltaY, int deltyZ) {
        this.tlfCoord.xCoord += deltaX;
        this.tlfCoord.yCoord += deltaY;
        this.tlfCoord.zCoord += deltyZ;

        this.lrbCoord.xCoord += deltaX;
        this.lrbCoord.yCoord += deltaY;
        this.lrbCoord.zCoord += deltyZ;

        return this;
    }

    @Nonnull
    public Cube IncludeCoordinate(@Nonnull Cube cube) {
        this.IncludeCoordinate(cube.getTopLeftFrontCoord());
        return this.IncludeCoordinate(cube.getLowerRightBackCoord());
    }

    @Nonnull
    public Cube IncludeCoordinate(@Nonnull Coordinate3D coordinate) {
        return this.IncludeCoordinate(coordinate.getXComponent(), coordinate.getYComponent(), coordinate.getZComponent());
    }

    @Nonnull
    public Cube IncludeCoordinate(float x, float y, float z) {
        if (x < this.tlfCoord.getXComponent()) {
            this.ExpandToCoordinate(-1 * Math.abs(x - tlfCoord.getXComponent()), 0, 0);
        }

        if (x > this.lrbCoord.getXComponent()) {
            this.ExpandToCoordinate(Math.abs(x - lrbCoord.getXComponent()), 0, 0);
        }

        if (y > this.lrbCoord.getYComponent()) {
            this.ExpandToCoordinate(0, Math.abs(y - lrbCoord.getYComponent()), 0);
        }

        if (y < this.tlfCoord.getYComponent()) {
            this.ExpandToCoordinate(0, -1 * Math.abs(y - tlfCoord.getYComponent()), 0);
        }

        if (z < this.tlfCoord.getZComponent()) {
            this.ExpandToCoordinate(0, 0, -1 * Math.abs(z - tlfCoord.getZComponent()));
        }

        if (z > lrbCoord.getZComponent()) {
            this.ExpandToCoordinate(0, 0, Math.abs(z - lrbCoord.getZComponent()));
        }

        return this;
    }

    @Nonnull
    public Cube ExpandToCoordinate(float deltaX, float deltaY, float deltaZ) {
        if (deltaX < 0) {
            tlfCoord = new Coordinate3D(tlfCoord.getXComponent() + deltaX, tlfCoord.getYComponent(), tlfCoord.getZComponent());
        }

        if (deltaX > 0) {
            lrbCoord = new Coordinate3D(lrbCoord.getXComponent() + deltaX, lrbCoord.getYComponent(), lrbCoord.getZComponent());
        }
        width = tlfCoord.getXComponent() - lrbCoord.getXComponent();

        if (deltaY < 0) {
            lrbCoord = new Coordinate3D(lrbCoord.getXComponent(), lrbCoord.getYComponent() + deltaY, lrbCoord.getZComponent());
        }

        if (deltaY > 0) {
            tlfCoord = new Coordinate3D(tlfCoord.getXComponent(), tlfCoord.getYComponent() + deltaY, tlfCoord.getZComponent());
        }
        height = tlfCoord.getYComponent() - lrbCoord.getYComponent();

        if (deltaZ < 0) {
            tlfCoord = new Coordinate3D(tlfCoord.getXComponent(), tlfCoord.getYComponent(), tlfCoord.getZComponent() + deltaZ);
        }

        if (deltaZ > 0) {
            lrbCoord = new Coordinate3D(lrbCoord.getXComponent(), lrbCoord.getYComponent(), lrbCoord.getZComponent() + deltaZ);
        }
        depth = tlfCoord.getZComponent() - lrbCoord.getZComponent();

        return this;
    }

    public boolean ContainsCoordinate(@Nonnull Coordinate3D coord) {
        if (coord.getYComponent() != tlfCoord.getYComponent())
            return false;

        return this.ContainsCoordinate(coord.getXComponent(), coord.getYComponent(), coord.getZComponent());
    }

    public boolean ContainsCoordinate(float x, float y, float z) {
        return this.getTopLeftFrontCoord().getXComponent() <= x && x <= this.getLowerRightBackCoord().getXComponent() && this.getLowerRightBackCoord().getYComponent() <= y && y <= this.getTopLeftFrontCoord().getYComponent() && this.getTopLeftFrontCoord().getZComponent() <= z && z < this.getLowerRightBackCoord().getZComponent();
    }

    public boolean Intersects(@Nonnull Cube cube) {
        return cube.getTopLeftFrontCoord().getXComponent() + cube.width > this.getTopLeftFrontCoord().getXComponent() && cube.getTopLeftFrontCoord().getXComponent() < this.getTopLeftFrontCoord().getXComponent() + this.width && cube.getTopLeftFrontCoord().getYComponent() + cube.height > this.getTopLeftFrontCoord().getYComponent() && cube.getTopLeftFrontCoord().getYComponent() < this.getTopLeftFrontCoord().getYComponent() + this.height && cube.getTopLeftFrontCoord().getZComponent() + cube.depth > this.getTopLeftFrontCoord().getZComponent() && cube.getTopLeftFrontCoord().getZComponent() < this.getTopLeftFrontCoord().getZComponent() + this.depth;
    }

    @Nonnull
    public AxisAlignedBB BoundingBox () {
        int tDiffX = 1;
        int tDiffY = 1;
        int tDiffZ = 1;

        if (width == 0)
            tDiffX = 1;

        if (height == 0)
            tDiffY = 1;

        if (depth == 0)
            tDiffZ = 1;


        return new AxisAlignedBB(tlfCoord.getXComponent() - tDiffX, tlfCoord.getYComponent() - tDiffY, tlfCoord.getZComponent() - tDiffZ, lrbCoord.getXComponent(), lrbCoord.getYComponent(), lrbCoord.getZComponent());
    }

    public float Contents() {
        return this.width * this.height * this.depth;
    }

}
