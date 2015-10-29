/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Util.Client;
/*
/  TextureAddressHelper
/  Created by : Orion
/  Created on : 27/06/2014
*/

public class TextureAddressHelper {
    public static String getTextureAddress(String pModID, String pAddress) {
        return getTextureAddressWithModID(pModID, pAddress);
    }

    public static String getTextureAddressWithExtension(String pModID, String pAdress, String pExtenstion) {
        return getTextureAddressWithModIDAndExtension(pModID, pAdress, pExtenstion);
    }

    public static String getTextureAddressWithModID(String pModID, String pAdress) {
        return getTextureAddressWithModIDAndExtension(pModID, pAdress, "");
    }

    public static String getTextureAddressWithModIDAndExtension(String pModID, String pAdress, String pExtension) {
        return pModID.toLowerCase() + ":" + pAdress + pExtension;
    }
}
