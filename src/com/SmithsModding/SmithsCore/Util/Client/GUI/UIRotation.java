/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.Client.GUI;

import org.lwjgl.opengl.GL11;

/**
 * Class used to store Rotation in the UI in Memory
 */
public class UIRotation {

    public float iRotateX = 0F;
    public float iRotateY = 0F;
    public float iRotateZ = 0F;

    public float iAngle = 0F;

    /**
     * Standard constructor. Sets the needed Axis and there Angles.
     *
     * @param pRotateX Whether or not the X Axis should be rotated
     * @param pRotateY Whether or not the Y Axis should be rotated
     * @param pRotateZ Whether or not the Z Axis should be rotated
     * @param pAngle   The Angle to rotate
     */
    public UIRotation(boolean pRotateX, boolean pRotateY, boolean pRotateZ, float pAngle) {
        if (pRotateX)
            iRotateX = 1F;

        if (pRotateY)
            iRotateY = 1F;

        if (pRotateZ)
            iRotateZ = 1F;

        iAngle = pAngle;

    }

    /**
     * Convenient Function to perform this rotation on the GL Buffer Matrix
     */
    public void performGLRotation() {
        GL11.glRotatef(iAngle, iRotateX, iRotateY, iRotateZ);
    }

    /**
     * Convenient Function to undo this rotation on the GL Buffer Matrix
     */
    public void performGLRotationReset() {
        GL11.glRotatef(-iAngle, iRotateX, iRotateY, iRotateZ);
    }

}
