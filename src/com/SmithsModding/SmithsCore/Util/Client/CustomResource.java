/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Util.Client;
/*
/  CustomResource
/  Created by : Orion
/  Created on : 15/06/2014
*/

import com.SmithsModding.SmithsCore.Util.Client.Color.Colors;
import com.SmithsModding.SmithsCore.Util.Client.Color.MinecraftColor;
import net.minecraft.util.IIcon;

import java.util.ArrayList;

public class CustomResource {
    private String iInternalName;

    private ArrayList<String> iRescourceLocations = new ArrayList<String>();
    private IIcon iIcon;
    private MinecraftColor iColor;

    private int iLeft;
    private int iTop;
    private int iWidth;
    private int iHeight;

    public CustomResource(String pInternalName, String pIconLocation) {
        this(pInternalName, pIconLocation, "");
    }

    public CustomResource(String pInternalName, String pIconLocation, String pModelLocation) {
        this(pInternalName, pIconLocation, pModelLocation, 255, 255, 255);
    }

    public CustomResource(String pInternalName, String pIconLocation, String pModelLocation, int pRed, int pGreen, int pBlue) {
        this(pInternalName, pIconLocation, pModelLocation, new MinecraftColor(pRed, pGreen, pBlue));
    }

    public CustomResource(String pInternalName, String pIconLocation, String pModelLocation, MinecraftColor pColor) {
        iInternalName = pInternalName;
        iRescourceLocations.add(pIconLocation);
        iRescourceLocations.add(pModelLocation);
        iColor = pColor;
    }

    public CustomResource(String pInternalName, String pIconLocation, int pRed, int pGreen, int pBlue) {
        this(pInternalName, pIconLocation, new MinecraftColor(pRed, pGreen, pBlue));
    }

    public CustomResource(String pInternalName, String pIconLocation, MinecraftColor pColor) {
        this(pInternalName, pIconLocation, "", pColor);
    }

    public CustomResource(String pInternalName, String pIconLocation, int pLeft, int pTop, int pWidth, int pHeight) {
        this(pInternalName, pIconLocation, Colors.DEFAULT, pLeft, pTop, pWidth, pHeight);
    }

    public CustomResource(String pInternalName, String pIconLocation, MinecraftColor pColor, int pLeft, int pTop, int pWidth, int pHeight) {
        iInternalName = pInternalName;
        iInternalName = pInternalName;
        iRescourceLocations.add(pIconLocation);
        iColor = pColor;
        iLeft = pLeft;
        iTop = pTop;
        iWidth = pWidth;
        iHeight = pHeight;
    }

    public String getInternalName() {
        return this.iInternalName;
    }

    public String getPrimaryLocation() {
        return iRescourceLocations.get(0);
    }

    public void addIcon(IIcon pIcon) {
        iIcon = pIcon;
    }

    public IIcon getIcon() {
        return iIcon;
    }

    public String getSecondaryLocation() {
        return iRescourceLocations.get(1);
    }

    public int getU() {
        return iLeft;
    }

    public int getV() {
        return iTop;
    }

    public int getWidth() {
        return iWidth;
    }

    public int getHeight() {
        return iHeight;
    }

    public int getColorInt(int pIndex) {
        switch (pIndex) {
            case 0:
                return iColor.getRed();
            case 1:
                return iColor.getGreen();
            case 2:
                return iColor.getBlue();
            default:
                return 255;
        }
    }

    public float getColorFloat(int pIndex) {
        switch (pIndex) {
            case 0:
                return iColor.getRed() / 255F;
            case 1:
                return iColor.getGreen() / 255F;
            case 2:
                return iColor.getBlue() / 255F;
            default:
                return 1F;
        }
    }

    public MinecraftColor getColor() {
        return iColor;
    }
}
