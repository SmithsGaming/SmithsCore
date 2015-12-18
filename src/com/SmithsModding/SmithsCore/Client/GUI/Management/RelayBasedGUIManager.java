package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;

import java.util.*;

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
     * Function used to register a new Component when this manager.
     *
     * @param component The new Component.
     */
    @Override
    public void registerComponent(IGUIComponent component) {
        host.getManager().registerComponent(component);
    }

    /**
     * Function used to get a IGUIComponent from a ID.
     *
     * @param id The ID of the component you are looking for.
     * @return Null if no component is registered to this Manager or the Component that has the given ID;
     */
    @Override
    public IGUIComponent getComponentFromID(String id) {
        return host.getManager().getComponentFromID(id);
    }

    /**
     * Function used to clear the list of registered components. Should be called when the GUI gets closed or
     * when the Components get reset.
     */
    @Override
    public void clearAllRegisteredComponents() {
        host.getManager().clearAllRegisteredComponents();
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
