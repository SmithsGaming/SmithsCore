package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Host.IGUIBasedTabHost;
import com.SmithsModding.SmithsCore.Client.GUI.Tabs.Core.IGUITab;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by marcf on 1/15/2016.
 */
public interface ITabManager
{

    //SELECTED TAB SIZE: 28*30
    //VERTICAL NOT SELECTED OFFSET: 4

    /**
     * Method to get this TabManagers host.
     *
     * @return The host of this TabManager.
     */
    IGUIBasedTabHost getHost();

    /**
     * Method used to get the currently displayed Tab.
     *
     * @return The currently displayed Tab.
     */
    IGUITab getCurrentTab();

    /**
     * Method used to get the current tabs Index inside the Tab list.
     *
     * @return The index of the currently displayed Index.
     */
    int getCurrentTabIndex();

    /**
     * Method used to retrieve all the possible Tabs for this TabManager.
     *
     * @return A LinkedHashMap<String, IGUITab> that holds all the possible Tabs sorted on registration order with their ID as keys.
     */
    LinkedHashMap<String, IGUITab> getTabs();

    /**
     * Method called when a click outside of the Tab in the TabSelection area occured.
     *
     * @param mouseX The relative X position of the mouse to the top left corner of the first tab selector
     * @param mouseY The relative Y position of the mouse to the top left corner of the first tab selector
     *
     */
    boolean handleClickOutsideTab(int mouseX, int mouseY, int mouseButton);


}
