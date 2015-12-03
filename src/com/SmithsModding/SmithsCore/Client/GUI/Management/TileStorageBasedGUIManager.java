package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Components.IGUIComponent;
import com.SmithsModding.SmithsCore.SmithsCore;

import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<String, IGUIComponent> components = new HashMap<String, IGUIComponent>();

    /**
     * Function used to register a new Component when this manager.
     *
     * @param component The new Component.
     */
    @Override
    public void registerComponent(IGUIComponent component) {
        components.put(component.getID(), component);
    }

    /**
     * Function used to get a IGUIComponent from a ID.
     *
     * @param id The ID of the component you are looking for.
     * @return Null if no component is registered to this Manager or the Component that has the given ID;
     */
    @Override
    public IGUIComponent getComponentFromID(String id) {
        return components.get(id);
    }

    /**
     * Function used to clear the list of registered components. Should be called when the GUI gets closed or
     * when the Components get reset.
     */
    @Override
    public void clearAllRegisteredComponents() {
        components.clear();
    }

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
