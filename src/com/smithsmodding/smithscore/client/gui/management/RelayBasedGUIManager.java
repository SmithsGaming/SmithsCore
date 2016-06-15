package com.smithsmodding.smithscore.client.gui.management;

import com.smithsmodding.smithscore.client.events.gui.GuiInputEvent;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Orion
 * Created on 01.12.2015
 * 18:13
 *
 * Copyrighted according to Project specific license
 */
public class RelayBasedGUIManager implements IGUIManager {

    IGUIManagerProvider host;
    ContainerSmithsCore containerSmithsCore;

    public RelayBasedGUIManager(IGUIManagerProvider host, ContainerSmithsCore containerSmithsCore)
    {
        this.host = host;
        this.containerSmithsCore = containerSmithsCore;
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

    /**
     * Method used by components that support overriding tooltips to grab the new tooltip string.
     *
     * @param component The component requesting the override.
     *
     * @return A string displayed as tooltip for the IGUIComponent during rendering.
     */
    @Override
    public String getCustomToolTipDisplayString (IGUIComponent component) {
        return host.getManager().getCustomToolTipDisplayString(component);
    }

    /**
     * Method to get the value for a progressbar. RAnged between 0 and 1.
     *
     * @param component The component to get the value for
     *
     * @return A float between 0 and 1 with 0 meaning no progress on the specific bar and 1 meaning full.
     */
    @Override
    public float getProgressBarValue (IGUIComponent component) {
        return host.getManager().getProgressBarValue(component);
    }

    /**
     * Method used by the rendering system to dynamically update a Label.
     *
     * @param component The component requesting the content.
     *
     * @return THe string that should be displayed.
     */
    @Override
    public String getLabelContents (IGUIComponent component) {
        return host.getManager().getLabelContents(component);
    }

    @Override
    public ArrayList<FluidStack> getTankContents (IGUIComponent component) {
        return host.getManager().getTankContents(component);
    }

    @Override
    public int getTotalTankContents (IGUIComponent component) {
        return host.getManager().getTotalTankContents(component);
    }

    /**
     * Method called by GUI's that are tab based when the active Tab changed.
     *
     * @param newActiveTabId The new active Tabs ID.
     */
    @Override
    public void onTabChanged (String newActiveTabId) {
        containerSmithsCore.onTabChanged(newActiveTabId);
    }

    @Override
    public void onInput(GuiInputEvent.InputTypes types, String componentId, String input) {
        host.getManager().onInput(types, componentId, input);
    }
}
