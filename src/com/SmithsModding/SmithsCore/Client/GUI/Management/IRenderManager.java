package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Components.IGUIComponent;
import net.minecraft.client.gui.Gui;

/**
 * Created by marcf on 12/3/2015.
 */
public interface IRenderManager
{

    /**
     * Method to get the Gui this RenderManager renders on.
     *
     * @return The current active GUI
     */
    Gui getRootGuiObject();

    /**
     * Methd to get the root GuiManager this RenderManager belongs to.
     *
     * @return The GuiManager of the Root GUI object.
     */
    IGUIManager getRootGuiManager();

    /**
     * Method to render the BackGround of a Component
     *
     * @param component The Component to render.
     */
    void renderBackgroundComponent(IGUIComponent component);

    /**
     * Method to render the ForeGround of a Component
     *
     * @param component The Component to render
     */
    void renderForegroundComponent(IGUIComponent component);

    /**
     * Method to render the ToolTip of the Component
     *
     * @param component The Component to render the tooltip from.
     */
    void renderToolTipComponent(IGUIComponent component);
}
