package com.smithsmodding.smithscore.client.gui.hosts;

import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.management.IGUIManager;
import com.smithsmodding.smithscore.client.gui.management.IGUIManagerProvider;
import com.smithsmodding.smithscore.client.gui.management.IRenderManager;
import net.minecraft.client.gui.FontRenderer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Orion Created on 01.12.2015 18:13
 *
 * Copyrighted according to Project specific license
 */
public interface IGUIBasedComponentHost extends IGUIManagerProvider, IGUIComponent {

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    void registerComponents (@Nonnull IGUIBasedComponentHost host);

    /**
     * Method used to register a new Component to this host.
     *
     * @param component The new component.
     */
    void registerNewComponent (@Nonnull IGUIComponent component);

    /**
     * Method to get the Root gui Object that this Component is part of.
     *
     * @return The gui that this component is part of.
     */
    @Nonnull
    IGUIBasedComponentHost getRootGuiObject();

    /**
     * Method to get the gui Roots Manager.
     *
     * @return The Manager that is at the root for the gui Tree.
     */
    @Nonnull
    IGUIManager getRootManager ();

    /**
     * Function to get all the components registered to this host.
     *
     * @return A ID to Component map that holds all the components (but not their SubComponents) of this host.
     */
    @Nonnull
    LinkedHashMap<String, IGUIComponent> getAllComponents ();

    /**
     * Method for outside systems to retrieve a UI Component based of its ID.
     *
     * @param uniqueUIID The uniqueUIID that is being searched for.
     *
     * @return A IGUIComponent with then given ID or null if no child components exists with that ID.
     */
    @Nullable
    IGUIComponent getComponentByID (@Nonnull String uniqueUIID);

    /**
     * Method to draw a tooltip for a component inside this Host.
     *
     * @param textLines The contents of the Tooltip
     * @param x         The X Coord of the tooltip
     * @param y         The y Coord of the tooltip
     * @param font      The font of the tooltip
     */
    void drawHoveringText(@Nullable List<String> textLines, int x, int y, FontRenderer font);

    /**
     * Get this hosts render manager;
     *
     * @return The render manager of this hosts.
     */
    @Nonnull
    IRenderManager getRenderManager();

    /**
     * Get the default offset for this GUI host.
     *
     * @return The default offset.
     */
    int getDefaultDisplayVerticalOffset();
}
