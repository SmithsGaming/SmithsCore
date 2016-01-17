package com.smithsmodding.smithscore.client.gui.management;

import com.smithsmodding.smithscore.client.gui.components.core.*;
import net.minecraft.client.gui.*;

/**
 * Created by marcf on 12/3/2015.
 */
public interface IRenderManager
{

    /**
     * Method to get the Gui this StandardRenderManager renders on.
     *
     * @return The current active gui
     */
    Gui getRootGuiObject();

    /**
     * Methd to get the root GuiManager this StandardRenderManager belongs to.
     *
     * @return The GuiManager of the Root gui object.
     */
    IGUIManager getRootGuiManager();

    /**
     * Method to render the BackGround of a Component
     *
     * @param component The Component to render.
     */
    void renderBackgroundComponent (IGUIComponent component, boolean parentEnabled);

    /**
     * Method to render the ForeGround of a Component
     *
     * @param component The Component to render
     */
    void renderForegroundComponent (IGUIComponent component, boolean parentEnabled);

    /**
     * Method to render the ToolTip of the Component
     *
     * @param component The Component to render the tooltip from.
     */
    void renderToolTipComponent (IGUIComponent component, int mouseX, int mouseY);
}
