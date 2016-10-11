package com.smithsmodding.smithscore.client.gui.management;

import com.smithsmodding.smithscore.client.events.gui.GuiInputEvent;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Orion
 * Created on 01.12.2015
 * 18:09
 *
 * Copyrighted according to Project specific license
 */
public interface IGUIManager
{
    //TODO: Create a OnManagerAttachedMethod to link a GuiManager to a specific ContainerClass

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

    /**
     * Method used by components that support overriding tooltips to grab the new tooltip string.
     *
     * @param component The component requesting the override.
     *
     * @return A string displayed as tooltip for the IGUIComponent during rendering.
     */
    String getCustomToolTipDisplayString(IGUIComponent component);

    /**
     * Method to get the value for a progressbar. RAnged between 0 and 1.
     *
     * @param component The component to get the value for
     *
     * @return A float between 0 and 1 with 0 meaning no progress on the specific bar and 1 meaning full.
     */
    float getProgressBarValue (IGUIComponent component);

    /**
     * Method used by the rendering system to dynamically update a Label.
     *
     * @param component The component requesting the content.
     *
     * @return THe string that should be displayed.
     */
    String getLabelContents (IGUIComponent component);

    /**
     * Method used by components to get the Fluids to display
     *
     * @param component The component that is requesting the Fluids
     *
     * @return The Fluids to display.
     */
    ArrayList<FluidStack> getTankContents (IGUIComponent component);

    /**
     * Method used by components to get the total fluid amount they can display, used for scaling.
     *
     * @param component The component requesting the total fluid amount
     *
     * @return Total fluid amount to display. Used for scaling.
     */
    int getTotalTankContents (IGUIComponent component);

    /**
     * Method called by GUI's that are tab based when the active Tab changed.
     *
     * @param newActiveTabId The new active Tabs ID.
     */
    void onTabChanged (String newActiveTabId);

    void onInput(GuiInputEvent.InputTypes types, String componentId, String input);
}
