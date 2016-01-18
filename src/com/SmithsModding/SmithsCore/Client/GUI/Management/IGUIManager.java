package com.smithsmodding.smithscore.client.gui.management;

import com.smithsmodding.smithscore.client.gui.components.core.*;
import net.minecraftforge.fluids.*;

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
     * Method to get the value for a progressbar. RAnged between 0 and 1.
     *
     * @param component The component to get the value for
     *
     * @return A float between 0 and 1 with 0 meaning no progress on the specific bar and 1 meaning full.
     */
    float getProgressBarValue (IGUIComponent component);

    ArrayList<FluidStack> getTankContents (IGUIComponent component);

    int getTotalTankContents (IGUIComponent component);
}
