package com.smithsmodding.smithscore.client.gui.management;

import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import net.minecraft.client.gui.Gui;

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
     * Method to get this RenderManagers ScissorRegionManager.
     *
     * @return This RenderManagers ScissorRegionManager.
     */
    IScissorRegionManager getScissorRegionManager();

    /**
     * Method to render the BackGround of a Component
     *
     * @param component The Component to render.
     * @param parentEnabled Indicates if the parent is enabled.
     */
    void renderBackgroundComponent (IGUIComponent component, boolean parentEnabled);

    /**
     * Method to render the ForeGround of a Component
     *
     * @param component The Component to render
     * @param parentEnabled Indicates if the parent is enabled.
     */
    void renderForegroundComponent (IGUIComponent component, boolean parentEnabled);

    /**
     * Method to render the ToolTip of the Component
     *
     * @param component The Component to render the tooltip from.
     * @param mouseX The absolute X-Coordinate of the Mouse
     * @param mouseY The absolute Y-Coordinate of the Mouse
     */
    void renderToolTipComponent (IGUIComponent component, int mouseX, int mouseY);
}
