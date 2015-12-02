package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.SmithsCore;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Orion
 * Created on 01.12.2015
 * 18:11
 * <p/>
 * Copyrighted according to Project specific license
 */
public class TileStorageBasedGUIManager implements IGUIManager{

    private ArrayList<UUID> watchingPlayers = new ArrayList<UUID>();

    /**
     * Method called when a player closed the linked UI.
     *
     * @param playerId The unique ID of the player that opens the UI.
     */
    @Override
    public void onGuiOpened(UUID playerId) {
        if (watchingPlayers.contains(playerId)) {
            SmithsCore.getLogger().warn("A player is already watching this Container!");
            return;
        }

        watchingPlayers.add(playerId);
    }

    /**
     * Method called when a player closes the linked UI.
     *
     * @param playerID The unique ID of the player that closed the UI.
     */
    @Override
    public void onGUIClosed(UUID playerID) {
        if (!watchingPlayers.contains(playerID)) {
            SmithsCore.getLogger().warn("A player already stopped watching this Container!");
            return;
        }

        watchingPlayers.remove(playerID);
    }
}
