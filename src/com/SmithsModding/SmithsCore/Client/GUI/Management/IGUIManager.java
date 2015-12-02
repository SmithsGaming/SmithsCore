package com.SmithsModding.SmithsCore.Client.GUI.Management;

import java.util.UUID;

/**
 * Created by Orion
 * Created on 01.12.2015
 * 18:09
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IGUIManager
{

    /**
     * Method called when a player closed the linked UI.
     *
     * @param playerId The unique ID of the player that opens the UI.
     */
    void onGuiOpened(UUID playerId);

    /**
     * Method called when a player closes the linked UI.
     *
     * @param playerID The unique ID of the player that closed the UI.
     */
    void onGUIClosed(UUID playerID);
}
