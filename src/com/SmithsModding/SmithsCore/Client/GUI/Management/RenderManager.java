package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Client.Registry.*;
import com.SmithsModding.SmithsCore.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import com.SmithsModding.SmithsCore.Util.Client.GUI.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

import java.util.*;

/**
 * Created by marcf on 12/3/2015.
 */
public class RenderManager implements IRenderManager {

    private static ArrayList<MinecraftColor> colorStack = new ArrayList<MinecraftColor>();

    Gui root;

    public RenderManager(Gui root)
    {
        if (!(root instanceof IGUIBasedComponentHost))
            throw new IllegalArgumentException("The given Root for this manager is not a IGUIBasedComponentHost!");

        this.root = root;
    }

    /**
     * Method should be used when rendering UI Components. It is used to set the color to a new one and remember the old
     * one.
     * <p/>
     * Allows for the Enabled color to be put on the known stack, or any other color set by a component.
     */
    public static void pushColorOnRenderStack (MinecraftColor color) {
        colorStack.add(0, color);
        color.performOpenGLColoring();
    }

    /**
     * Method should be used when rendering UI Components. It is used to reset the color to the previous state instead
     * of to white.
     * <p/>
     * Allows for the Enabled color to be put on the known stack, or any other color set by a component.
     */
    public static void popColorFromRenderStack () {
        if (colorStack.size() == 0) {
            SmithsCore.getLogger().error("The color Stack is empty!");
            return;
        }

        colorStack.remove(0);

        if (colorStack.size() == 0) {
            //SmithsCore.getLogger().error("The color stack under flowed.");

            new MinecraftColor(MinecraftColor.WHITE).performOpenGLColoring();
            return;
        }

        colorStack.get(0).performOpenGLColoring();
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
        if (!component.getState().isVisible())
            return;

        ClientRegistry registry = (ClientRegistry) SmithsCore.getRegistry();

        if (component instanceof IGUIBasedComponentHost)
        {
            GlStateManager.pushMatrix();

            GlStateManager.translate(component.getLocalCoordinate().getXComponent(), component.getLocalCoordinate().getYComponent(), 0F);

            component.update(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent(), registry.getPartialTickTime());

            for(IGUIComponent subComponent : ((IGUIBasedComponentHost) component).getAllComponents().values())
            {
                this.renderSubBackgroundComponent(subComponent, component.getState().isEnabled());
            }

            GlStateManager.popMatrix();
        }
        else
        {
            GlStateManager.pushMatrix();

            GlStateManager.translate(component.getLocalCoordinate().getXComponent(), component.getLocalCoordinate().getYComponent(), 0F);

            component.update(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent(), registry.getPartialTickTime());

            GuiHelper.enableScissor(component.getAreaOccupiedByComponent());

            IGUIComponentState state = component.getState();

            if (!state.isEnabled())
            {
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                pushColorOnRenderStack(new MinecraftColor(MinecraftColor.darkGray));
            }

            component.drawBackground(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent());

            if (SmithsCore.isInDevenvironment()) {
                //GuiHelper.renderScissorDebugOverlay();
            }

            if (!state.isEnabled()) {
                popColorFromRenderStack();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
            }

            GuiHelper.disableScissor();

            GlStateManager.popMatrix();
        }

    }

    /**
     * This method is used when rendering SubComponents of a IGUIComponentHost, it takes the enabled state of its parent into account.
     *
     * @param component The subcomponent to render.
     * @param parentEnabled The enabled state of its parent.
     */
    private void renderSubBackgroundComponent(IGUIComponent component, boolean parentEnabled)
    {
        if (!component.getState().isVisible())
            return;

        ClientRegistry registry = (ClientRegistry) SmithsCore.getRegistry();

        if (component instanceof IGUIBasedComponentHost)
        {
            GlStateManager.pushMatrix();

            GlStateManager.translate(component.getLocalCoordinate().getXComponent(), component.getLocalCoordinate().getYComponent(), 0F);

            component.update(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent(), registry.getPartialTickTime());

            for(IGUIComponent subComponent : ((IGUIBasedComponentHost) component).getAllComponents().values())
            {
                this.renderSubBackgroundComponent(subComponent, !parentEnabled?false:subComponent.getState().isEnabled());
            }

            GlStateManager.popMatrix();
        }
        else
        {


            GlStateManager.pushMatrix();

            GlStateManager.translate(component.getLocalCoordinate().getXComponent(), component.getLocalCoordinate().getYComponent(), 0F);

            component.update(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent(), registry.getPartialTickTime());

            GuiHelper.enableScissor(component.getAreaOccupiedByComponent());

            if (SmithsCore.isInDevenvironment()) {
                //GuiHelper.renderScissorDebugOverlay();
            }

            IGUIComponentState state = component.getState();

            if (!state.isEnabled() || !parentEnabled)
            {
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                ((MinecraftColor) MinecraftColor.darkGray).performOpenGLColoring();
            }

            component.drawBackground(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent());

            if (!state.isEnabled() || !parentEnabled)
            {
                MinecraftColor.resetOpenGLColoring();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
            }

            GuiHelper.disableScissor();

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
        if (!component.getState().isVisible())
            return;

        if (component instanceof IGUIBasedComponentHost) {
            GlStateManager.pushMatrix();

            GlStateManager.translate(component.getLocalCoordinate().getXComponent(), component.getLocalCoordinate().getYComponent(), 0F);

            for (IGUIComponent subComponent : ( (IGUIBasedComponentHost) component ).getAllComponents().values()) {
                GlStateManager.pushMatrix();

                this.renderForegroundComponent(subComponent);

                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
        } else {
            ClientRegistry registry = (ClientRegistry) SmithsCore.getRegistry();

            GlStateManager.pushMatrix();

            GlStateManager.translate(component.getLocalCoordinate().getXComponent(), component.getLocalCoordinate().getYComponent(), 0F);

            GuiHelper.enableScissor(component.getAreaOccupiedByComponent());

            IGUIComponentState state = component.getState();

            component.drawForeground(registry.getMouseManager().getLocation().getXComponent(), registry.getMouseManager().getLocation().getYComponent());

            if (!state.isEnabled()) {
                MinecraftColor.resetOpenGLColoring();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
            }
            GlStateManager.popMatrix();
        }
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
