package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Components.IGUIComponent;
import com.SmithsModding.SmithsCore.Client.GUI.Host.IGUIBasedComponentHost;
import com.SmithsModding.SmithsCore.Client.Registry.ClientRegistry;
import com.SmithsModding.SmithsCore.SmithsCore;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

/**
 * Created by marcf on 12/3/2015.
 */
public class RenderManager implements IRenderManager {

    Gui root;

    public RenderManager(Gui root)
    {
        if (!(root instanceof IGUIBasedComponentHost))
            throw new IllegalArgumentException("The given Root for this manager is not a IGUIBasedComponentHost!");

        this.root = root;
    }

    /**
     * Method to get the Gui this RenderManager renders on.
     *
     * @return The current active GUI
     */
    @Override
    public Gui getRootGuiObject() {
        return root;
    }

    /**
     * Methd to get the root GuiManager this RenderManager belongs to.
     *
     * @return The GuiManager of the Root GUI object.
     */
    @Override
    public IGUIManager getRootGuiManager() {
        return ((IGUIBasedComponentHost)root).getRootManager();
    }

    /**
     * Method to render the BackGround of a Component
     *
     * @param component The Component to render.
     */
    @Override
    public void renderBackgroundComponent(IGUIComponent component) {
        if (component instanceof IGUIBasedComponentHost)
        {
            GlStateManager.pushMatrix();

            GlStateManager.translate(component.getComponentRootAnchorPixel().getXComponent(), component.getComponentRootAnchorPixel().getYComponent(), 0F);

            for(IGUIComponent subComponent : ((IGUIBasedComponentHost) component).getAllComponents().values())
            {
                GlStateManager.pushMatrix();

                this.renderBackgroundComponent(subComponent);

                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
        }
        else
        {
            ClientRegistry registry = (ClientRegistry) SmithsCore.getRegistry();

            GlStateManager.pushMatrix();

            GlStateManager.translate(component.getComponentRootAnchorPixel().getXComponent(), component.getComponentRootAnchorPixel().getYComponent(), 0F);

            component.drawBackground(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent());

            GlStateManager.popMatrix();
        }

    }

    /**
     * Method to render the ForeGround of a Component
     *
     * @param component The Component to render
     */
    @Override
    public void renderForegroundComponent(IGUIComponent component) {

    }

    /**
     * Method to render the ToolTip of the Component
     *
     * @param component The Component to render the tooltip from.
     */
    @Override
    public void renderToolTipComponent(IGUIComponent component) {

    }
}
