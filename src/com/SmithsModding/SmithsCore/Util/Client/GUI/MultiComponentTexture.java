/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Util.Client.GUI;

import com.SmithsModding.SmithsCore.Util.Client.CustomResource;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.Coordinate2D;

/**
 * A Texture that is made up out of several (9) Components.
 * By repeating the individual components you can create a Texture as big as you want.
 */
public class MultiComponentTexture {
    TextureComponent iCenterComponent;
    TextureComponent[] iCornerComponents = new TextureComponent[4];
    TextureComponent[] iSideComponents = new TextureComponent[4];

    /**
     * Standard constructor that sets the Components based on the given Arrays and the CenterComponent
     *
     * @param pCenterComponent  A TextureComponent that describes the center of this Texture.
     * @param pCornerComponents An Array with the Components for the Corners in this order: TopLeft, TopRight, BottomRight, BottomLeft.
     * @param pSideComponents   An Array with the Components for the Sides in this order: Top, Right, Bottom, Left.
     */
    public MultiComponentTexture(TextureComponent pCenterComponent, TextureComponent[] pCornerComponents, TextureComponent[] pSideComponents) {
        iCenterComponent = pCenterComponent;
        iCornerComponents = pCornerComponents;
        iSideComponents = pSideComponents;
    }

    /**
     * A Special constructor that cuts a given Resource in to nine pieces and stores it as the TextureComponents in the Arrays and the CenterComponent.
     *
     * @param pSource The CustomResource describing the Texture
     * @param pTotalWidth The Total Width of the Texture in the Resource
     * @param pTotalHeight The Total Height of the Texture in the Resource
     * @param pCornerWidth The Corner Width of the MultiTexture Corners
     * @param pCornerHeight The Corner Height of the MultiTexture Corners
     */
    public MultiComponentTexture(CustomResource pSource, int pTotalWidth, int pTotalHeight, int pCornerWidth, int pCornerHeight) {
        iCenterComponent = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU() + pCornerWidth, pSource.getV() + pCornerHeight, pTotalWidth - (pCornerWidth * 2), pTotalHeight - (pCornerHeight * 2), new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));

        iCornerComponents[0] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU(), pSource.getV(), pCornerWidth, pCornerHeight, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
        iCornerComponents[1] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU() + pTotalWidth - pCornerWidth, pSource.getV(), pCornerWidth, pCornerHeight, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
        iCornerComponents[2] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU() + pTotalWidth - pCornerWidth, pSource.getV() + pTotalHeight - pCornerHeight, pCornerWidth, pCornerHeight, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
        iCornerComponents[3] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU(), pSource.getV() + pTotalHeight - pCornerHeight, pCornerWidth, pCornerHeight, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));

        iSideComponents[0] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU() + pCornerWidth, pSource.getV(), pTotalWidth - (pCornerWidth * 2), pCornerHeight, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
        iSideComponents[1] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU() + pTotalWidth - (pCornerWidth), pSource.getV() + pCornerHeight, pCornerWidth, pTotalHeight - (pCornerHeight * 2), new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
        iSideComponents[2] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU() + pCornerWidth, pSource.getV() + pTotalHeight - pCornerHeight, pTotalWidth - (pCornerWidth * 2), pCornerHeight, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
        iSideComponents[3] = new TextureComponent(pSource.getPrimaryLocation(), pSource.getU(), pSource.getV() + pCornerHeight, pCornerWidth, pTotalHeight - (pCornerHeight * 2), new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
    }

}
