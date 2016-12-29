package com.smithsmodding.smithscore.client.gui.tabs.core;

import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.management.*;
import com.smithsmodding.smithscore.util.client.color.*;
import net.minecraft.item.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
    @Nonnull
    IGUIBasedTabHost getTabHost();

    /**
     * Method to get this tabs TabManger.
     *
     * @return The TabManager of this Tab.
     */
    @Nonnull
    ITabManager getTabManager ();

    /**
     * Method to get the display stack.
     *
     * @return THe displayed stack.
     */
    @Nonnull
    ItemStack getDisplayStack();

    /**
     * Method to get the tabs color.
     *
     * @return The tabs color.
     */
    @Nonnull
    MinecraftColor getTabColor ();

    /**
     * Function to get the tooltiptext of the gui System.
     *
     * @return The tooltip contents
     */
    @Nullable
    ArrayList<String> getIconToolTipText();

}
