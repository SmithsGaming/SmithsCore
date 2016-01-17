package com.smithsmodding.smithscore.client.GUI.Tabs.Core;

import net.minecraft.item.*;

import java.util.*;

/**
 * Created by marcf on 1/15/2016.
 */
public interface IGUITab extends IGUIBasedComponentHost
{

    /**
     * Method to get the host of this Tab
     *
     * @return The current tabs host.
     */
    IGUIBasedTabHost getTabHost();

    /**
     * Method to get the display stack.
     *
     * @return THe displayed stack.
     */
    ItemStack getDisplayStack();

    /**
     * Method to get the tabs color.
     *
     * @return The tabs color.
     */
    MinecraftColor getTabColor ();

    /**
     * Function to get the tooltiptext of the gui System.
     *
     * @return
     */
    ArrayList<String> getIconToolTipText();

}
