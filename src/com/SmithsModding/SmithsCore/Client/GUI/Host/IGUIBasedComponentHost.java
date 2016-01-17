package com.smithsmodding.smithscore.client.GUI.Host;

import java.util.*;

/**
 * Created by Orion
 * Created on 01.12.2015
 * 18:13
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IGUIBasedComponentHost extends IGUIManagerProvider, IGUIComponent
{

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    void registerComponents(IGUIBasedComponentHost host);

    /**
     * Method used to register a new Component to this host.
     *
     * @param component The new component.
     */
    void registerNewComponent (IGUIComponent component);

    /**
     * Method to get the Root gui Object that this Component is part of.
     *
     * @return The gui that this component is part of.
     */
    GuiContainerSmithsCore getRootGuiObject();

    /**
     * Method to get the gui Roots Manager.
     *
     * @return The Manager that is at the root for the gui Tree.
     */
    IGUIManager getRootManager();

    /**
     * Function to get all the components registered to this host.
     *
     * @return A ID to Component map that holds all the components (but not their SubComponents) of this host.
     */
    LinkedHashMap<String, IGUIComponent> getAllComponents ();

}
