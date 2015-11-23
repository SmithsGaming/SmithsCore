package com.SmithsModding.SmithsCore.Util.Common;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

/**
 * Created by Orion
 * Created on 22.11.2015
 * 22:56
 * <p/>
 * Copyrighted according to Project specific license
 */
public class PlayerHelper {

    /**
     * Iterates over al connected players to find one with the given ID, or returns null if none is found.
     *
     * @param pID The ID to search for.
     * @return A Instance of EntityPlayer with that UniqueID or null if none matches.
     */
    public EntityPlayer getPlayerFromID(UUID pID) {
        for (Object tPlayerObject : FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList) {
            EntityPlayer tPlayer = (EntityPlayer) tPlayerObject;

            if (tPlayer.getUniqueID().equals(pID))
                return tPlayer;
        }

        return null;
    }
}
