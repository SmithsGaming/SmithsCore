package com.SmithsModding.SmithsCore.Client.GUI.Management;

import java.util.UUID;

/**
 * Created by Orion
 * Created on 01.12.2015
 * 18:13
 * <p/>
 * Copyrighted according to Project specific license
 */
public class RelayBasedGUIManager implements IGUIManager {

    IGUIManagerProvider host;

    public RelayBasedGUIManager(IGUIManagerProvider host)
    {
        this.host = host;
    }


    /**
     * Method called when a player closed the linked UI.
     *
     * @param playerId The unique ID of the player that opens the UI.
     */
    @Override
    public void onGuiOpened(UUID playerId) {
        host.getManager().onGuiOpened(playerId);
    }

    /**
     * Method called when a player closes the linked UI.
     *
     * @param playerID The unique ID of the player that closed the UI.
     */
    @Override
    public void onGUIClosed(UUID playerID) {
        host.getManager().onGUIClosed(playerID);
    }
}
