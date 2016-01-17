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
     * Method used to get to total tab selector count.
     *
     * @return The total amount of tab selectors that could fit on the host.
     */
    int getTabSelectorCount();

    /**
     * Method to get the width of a Tab Selector
     *
     * @return The width of a Tab Selector. Vanilla standard is 28.
     */
    int getTabSelectorWidth();

    /**
     * Method to get the height of a Tab Selector.
     *
     * @return The height of a Tab Selector. Vanilla standard is 30. Due to border mechanics defailt is 33.
     */
    int getTabSelectorHeight();

    /**
     * Method used to grab the vertical offset for a Tab Selector when he is InActive.
     *
     * @return The amount of pixels a InActive Tab Selector is placed lower then a Active one.
     */
    int getInActiveSelectorVerticalOffset();

    /**
     * Method used to get the horizontal offset of the Tab Selectors from the top left corner of the UI.
     *
     * @return The horizontal offset of the Selectors.
     */
    int getSelectorsHorizontalOffset();

    /**
     * Method used to get the vertical offset of the Display area of a Ta
     * @return
     */
    int getDisplayAreaVerticalOffset();

    /**
     * Method called when a click outside of the Tab in the TabSelection area occured.
     *
     * @param mouseX The relative X position of the mouse to the top left corner of the first tab selector
     * @param mouseY The relative Y position of the mouse to the top left corner of the first tab selector
     *
     */
    boolean handleClickOutsideTab(int mouseX, int mouseY, int mouseButton);


}
