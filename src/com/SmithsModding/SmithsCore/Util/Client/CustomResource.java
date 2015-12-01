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
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.util.ArrayList;

/**
 * Class used to manage resource combinations.
 * It is meanly used for a Item/In world Model combination.
 * You can register two texture sheets and a single IIcon to it.
 */
public class CustomResource {
    private String iInternalName;

    private ArrayList<String> iRescourceLocations = new ArrayList<String>();
    private TextureAtlasSprite iIcon;
    private MinecraftColor iColor;

    private int iLeft;
    private int iTop;
    private int iWidth;
    private int iHeight;

    /**
     * Standard constructor for a single Texture resource
     *
     * @param pInternalName The ID used to identify this Resource
     * @param pIconLocation The Texturelocation for the first resource. Usually this is the location of the IIcon.
     */
    public CustomResource(String pInternalName, String pIconLocation) {
        this(pInternalName, pIconLocation, "");
    }

    /**
     * Standard constructor for a multi Texture resource
     *
     * @param pInternalName  The ID used to identify this Resource.
     * @param pIconLocation  The Texturelocation for the first resource. Usually this is the location of the IIcon.
     * @param pModelLocation The Texturelocation for the second resource. Usually this is the location of the Modeltexture.
     */
    public CustomResource(String pInternalName, String pIconLocation, String pModelLocation) {
        this(pInternalName, pIconLocation, pModelLocation, 255, 255, 255);
    }

    /**
     * Special Constructor for a resource with Color
     *
     * @param pInternalName The ID used to identify this Resource.
     * @param pIconLocation The Texturelocation for the first resource. Usually this is the location of the IIcon.
     * @param pModelLocation The Texturelocation for the second resource. Usually this is the location of the Modeltexture.
     * @param pRed The Red Channel for the Color (0-255)
     * @param pGreen The Green Channel for the Color (0-255)
     * @param pBlue The Blue Channel for the Color (0-255)
     */
    public CustomResource(String pInternalName, String pIconLocation, String pModelLocation, int pRed, int pGreen, int pBlue) {
        this(pInternalName, pIconLocation, pModelLocation, new MinecraftColor(pRed, pGreen, pBlue));
    }

    /**
     * Special Constructor for a resource with Color
     *
     * @param pInternalName The ID used to identify this Resource.
     * @param pIconLocation The Texturelocation for the first resource. Usually this is the location of the IIcon.
     * @param pModelLocation The Texturelocation for the second resource. Usually this is the location of the Modeltexture.
     * @param pColor The MinecraftColor instance used as Color.
     */
    public CustomResource(String pInternalName, String pIconLocation, String pModelLocation, MinecraftColor pColor) {
        iInternalName = pInternalName;
        iRescourceLocations.add(pIconLocation);
        iRescourceLocations.add(pModelLocation);
        iColor = pColor;
    }

    /**
     * Special Constructor for a single texture resource with Color
     *
     * @param pInternalName The ID used to identify this Resource.
     * @param pIconLocation The Texturelocation for the first resource. Usually this is the location of the IIcon.
     * @param pRed The Red Channel for the Color (0-255)
     * @param pGreen The Green Channel for the Color (0-255)
     * @param pBlue The Blue Channel for the Color (0-255)
     */
    public CustomResource(String pInternalName, String pIconLocation, int pRed, int pGreen, int pBlue) {
        this(pInternalName, pIconLocation, new MinecraftColor(pRed, pGreen, pBlue));
    }

    /**
     * Special Constructor for a single texture resource with Color
     *
     * @param pInternalName The ID used to identify this Resource.
     * @param pIconLocation The Texturelocation for the first resource. Usually this is the location of the IIcon.
     * @param pColor The MinecraftColor instance used as Color.
     */
    public CustomResource(String pInternalName, String pIconLocation, MinecraftColor pColor) {
        this(pInternalName, pIconLocation, "", pColor);
    }

    /**
     * Special Contstuctor used for resources that have a location inside there file.
     * Usually used for textures in the GUI
     * @param pInternalName The ID used to identify this Resource.
     * @param pIconLocation The Texturelocation for the first resource. Usually this is the location of the IIcon, but in this case it would be the GUI parts texture location
     * @param pLeft The U Index inside the texture file
     * @param pTop The V Index inside the texture file
     * @param pWidth The width of the Resource
     * @param pHeight The Height of the Resource
     */
    public CustomResource(String pInternalName, String pIconLocation, int pLeft, int pTop, int pWidth, int pHeight) {
        this(pInternalName, pIconLocation, Colors.DEFAULT, pLeft, pTop, pWidth, pHeight);
    }

    /**
     * Special Constructor used for resources that have a location inside there file and a color.
     * Usually used for textures in the GUI
     *
     * @param pInternalName The ID used to identify this Resource.
     * @param pIconLocation The Texturelocation for the first resource. Usually this is the location of the IIcon, but in this case it would be the GUI parts texture location
     * @param pColor The MinecraftColor instance used as Color.
     * @param pLeft The U Index inside the texture file
     * @param pTop The V Index inside the texture file
     * @param pWidth The width of the Resource
     * @param pHeight The Height of the Resource
     */
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

    /**
     * Function to get the ID of the Resource
     *
     * @return The ID of the resources
     */
    public String getInternalName() {
        return this.iInternalName;
    }

    /**
     * Function to get the first (usually the IIcon) texture location of the Resource
     *
     * @return The Texture address location in String
     */
    public String getPrimaryLocation() {
        return iRescourceLocations.get(0);
    }

    /**
     * Function to add a IIcon to the resource.
     *
     * @param pIcon The IIcon to register.
     */
    public void addIcon(TextureAtlasSprite pIcon) {
        iIcon = pIcon;
    }

    /**
     * Function to get the IIcon from the resource.
     *
     * @return The registered IIcon.
     */
    public TextureAtlasSprite getIcon() {
        return iIcon;
    }

    /**
     * Function to get the secondary Texture location.
     * Usually used in models.
     *
     * @return The secondary address location of the resource in String.
     */
    public String getSecondaryLocation() {
        return iRescourceLocations.get(1);
    }

    /**
     * Function to get the U Index of the primary resource that this CustomResource represents.
     *
     * @return A 0-Based X-index of the first pixel in the Resource
     */
    public int getU() {
        return iLeft;
    }

    /**
     * Function to get the V Index of the primary resource that this CustomResource represents.
     *
     * @return A 0-Based Y-index of the first pixel in the Resource
     */
    public int getV() {
        return iTop;
    }

    /**
     * Function to get the width of the primary resource that this CustomResource represents.
     *
     * @return The width of the resource in the source.
     */
    public int getWidth() {
        return iWidth;
    }

    /**
     * Function to get the height of the primary resource that this CustomResource represents.
     *
     * @return The height of the resource in the source.
     */
    public int getHeight() {
        return iHeight;
    }

    /**
     * Holds the Color used to color grayscaled textures.
     *
     * @return The Color for rendering grayscaled textures.
     */
    public MinecraftColor getColor() {
        return iColor;
    }
}
