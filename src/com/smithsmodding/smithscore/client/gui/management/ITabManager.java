package com.smithsmodding.smithscore.client.gui.management;

import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.tabs.core.*;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by marcf on 1/15/2016.
 */
public interface ITabManager
{
    /**
     * Method to get this TabManagers host.
     *
     * @return The host of this TabManager.
     */
    IGUIBasedTabHost getHost();

    /**
     * Method called by the Host.
     *
     * To notify the Manager that all tabs have been registered.
     */
    void onTabRegistrationComplete ();

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
     * Method used to get the tab from a Selector index.
     *
     * @param selectorIndex The index you want the tab for.
     *
     * @return The tab for the requested selector index.
     */
    IGUITab getTabFromSelectorIndex (int selectorIndex);

    /**
     * Method used to register a new Tab
     *
     * @param newTab The new tab.
     */
    void registerNewTab (IGUITab newTab);

    /**
     * Method used to retrieve all the possible tabs for this TabManager.
     *
     * @return A LinkedHashMap that holds all the possible tabs sorted on registration order with their ID as keys.
     */
    @Nonnull
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
     * @return The height of a Tab Selector. Vanilla standard is 30. Due to border mechanics default is 33.
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
     * @return The vertical offset of the DisplayArea.
     */
    int getDisplayAreaVerticalOffset();

    /**
     * Method to set the currently displayed tab.
     *
     * @param tab The tab to be displayed.
     */
    void setActiveTab (IGUITab tab);
}
