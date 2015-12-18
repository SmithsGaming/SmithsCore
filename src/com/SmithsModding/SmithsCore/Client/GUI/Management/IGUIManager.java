package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;

import java.util.*;

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
     * Function used to register a new Component when this manager.
     *
     * @param component The new Component.
     */
    void registerComponent(IGUIComponent component);

    /**
     * Function used to get a IGUIComponent from a ID.
     *
     * @param id The ID of the component you are looking for.
     * @return Null if no component is registered to this Manager or the Component that has the given ID;
     */
    IGUIComponent getComponentFromID(String id);

    /**
     * Function used to clear the list of registered components. Should be called when the GUI gets closed or
     * when the Components get reset.
     */
    void clearAllRegisteredComponents();

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
