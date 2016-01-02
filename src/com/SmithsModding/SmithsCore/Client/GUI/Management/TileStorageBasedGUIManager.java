package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;

import java.util.*;

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
            return;
        }

        watchingPlayers.remove(playerID);
    }

    /**
     * Method to get the value for a progressbar. RAnged between 0 and 1.
     *
     * @param component The component to get the value for
     *
     * @return A float between 0 and 1 with 0 meaning no progress on the specific bar and 1 meaning full.
     */
    @Override
    public float getProgressBarValue (IGUIComponent component) {
        return 0F;
    }
}
