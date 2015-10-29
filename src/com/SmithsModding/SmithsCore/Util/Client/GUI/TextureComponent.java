/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Util.Client.GUI;

import com.SmithsModding.SmithsCore.Util.Client.CustomResource;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.Coordinate2D;
import net.minecraft.client.renderer.texture.TextureMap;

public class TextureComponent {

    public int iU = 0;
    public int iV = 0;
    public int iWidth = 0;
    public int iHeight = 0;

    public Coordinate2D iRelativeTranslation = new Coordinate2D(0, 0);

    public UIRotation iRotation = new UIRotation(false, false, false, 0F);

    public String iAddress = TextureMap.locationBlocksTexture.getResourcePath();

    public TextureComponent(String pAddress, int pU, int pV, int pWidth, int pHeight, UIRotation pRotation, Coordinate2D pRelateTranslation) {
        iAddress = pAddress;
        iU = pU;
        iV = pV;
        iWidth = pWidth;
        iHeight = pHeight;
        iRotation = pRotation;
        iRelativeTranslation = pRelateTranslation;
    }

    public TextureComponent(CustomResource pResource, UIRotation pRotation, Coordinate2D pRelativeTranslation) {
        iAddress = pResource.getPrimaryLocation();
        iU = pResource.getU();
        iV = pResource.getV();
        iWidth = pResource.getWidth();
        iHeight = pResource.getHeight();
        iRotation = pRotation;
        iRelativeTranslation = pRelativeTranslation;
    }

    public TextureComponent(CustomResource pResource) {
        this(pResource, new UIRotation(false, false, false, 0F), new Coordinate2D(0, 0));
    }
}
