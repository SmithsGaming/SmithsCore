package com.smithsmodding.smithscore.util.common;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.player.management.PlayerManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

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
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            if (PlayerManager.getInstance().getServerSidedJoinedMap().containsKey(pID)) {
                return PlayerManager.getInstance().getServerSidedJoinedMap().get(pID);
            }

            SmithsCore.getLogger().info("[PlayerHelper] Falling back on ServerConfiguration Manager - No player found in PlayerManager!");
        }

        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(pID);
    }
}
