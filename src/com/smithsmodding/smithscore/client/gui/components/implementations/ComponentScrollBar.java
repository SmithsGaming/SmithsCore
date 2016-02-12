package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.*;
import com.smithsmodding.smithscore.client.gui.animation.*;
import com.smithsmodding.smithscore.client.gui.components.core.*;
import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.management.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.util.client.*;
import com.smithsmodding.smithscore.util.client.color.*;
import com.smithsmodding.smithscore.util.client.gui.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.client.renderer.*;

import java.util.*;

/**
 * Created by Marc on 08.02.2016.
 */
public class ComponentScrollBar implements IGUIComponent, IGUIBasedComponentHost, IAnimatibleGuiComponent {

    public static int WIDTH = 7;

    private String uniqueID;
    private LinkedHashMap<String, IGUIComponent> componentHashMap = new LinkedHashMap<String, IGUIComponent>();

    private IGUIBasedComponentHost parent;
    private ScrollBarComponentState state;

    private Coordinate2D rootAnchorPixel;
    private int height;

    Plane innerArea;

    public ComponentScrollBar (String uniqueID, ScrollBarComponentState state, IGUIBasedComponentHost parent, Coordinate2D rootAnchorPixel, int height) {
        this.uniqueID = uniqueID;

        this.parent = parent;
        this.rootAnchorPixel = rootAnchorPixel;
        this.height = height;

        this.state = state;
        this.state.setComponent(this);

        innerArea = new Plane(0,10,WIDTH,height - 20);
    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents (IGUIBasedComponentHost host) {
        registerNewComponent(new ComponentButton(getID() + ".Buttons.Up", this, new Coordinate2D(0,0), WIDTH, 10, false, Textures.Gui.Basic.Components.Button.UPARROW));
        registerNewComponent(new ComponentButton(getID() + ".Buttons.ScrollDrag", this, new Coordinate2D(0, 10), WIDTH, 10, true, Textures.Gui.Basic.Components.Button.SCROLLBAR));
        registerNewComponent(new ComponentButton(getID() + ".Buttons.Down", this, new Coordinate2D(0, height - 10), WIDTH, 10, false, Textures.Gui.Basic.Components.Button.DOWNARROW));
    }

    /**
     * Method used to register a new Component to this host.
     *
     * @param component The new component.
     */
    @Override
    public void registerNewComponent (IGUIComponent component) {
        componentHashMap.put(component.getID(), component);

        if (component instanceof IGUIBasedComponentHost)
            ((IGUIBasedComponentHost) component ).registerComponents((IGUIBasedComponentHost) component);
    }

    /**
     * Method to get the Root gui Object that this Component is part of.
     *
     * @return The gui that this component is part of.
     */
    @Override
    public GuiContainerSmithsCore getRootGuiObject () {
        return parent.getRootGuiObject();
    }

    /**
     * Method to get the gui Roots Manager.
     *
     * @return The Manager that is at the root for the gui Tree.
     */
    @Override
    public IGUIManager getRootManager () {
        return parent.getRootManager();
    }

    /**
     * Function to get all the components registered to this host.
     *
     * @return A ID to Component map that holds all the components (but not their SubComponents) of this host.
     */
    @Override
    public LinkedHashMap<String, IGUIComponent> getAllComponents () {
        return componentHashMap;
    }

    public IGUIComponent getComponentByID(String uniqueUIID)
    {
        if (getID().equals(uniqueUIID))
            return this;

        if (getAllComponents().get(uniqueUIID) != null)
            return getAllComponents().get(uniqueUIID);

        for(IGUIComponent childComponent : getAllComponents().values())
        {
            if (childComponent instanceof IGUIBasedComponentHost)
            {
                IGUIComponent foundComponent = ((IGUIBasedComponentHost) childComponent).getComponentByID(uniqueUIID);

                if (foundComponent != null)
                    return foundComponent;
            }
        }

        return null;
    }

    /**
     * Function used to get the ID of the Component.
     *
     * @return The ID of this Component.
     */
    @Override
    public String getID () {
        return uniqueID;
    }

    /**
     * Function used to get the StateObject of this Component. A good example of what to find in the state Object is the
     * Visibility of it. But also things like the Displayed Text of a label or TextBox are stored in the components
     * state Object.
     *
     * @return This components state Object.
     */
    @Override
    public IGUIComponentState getState () {
        return state;
    }

    /**
     * Function to get this components host.
     *
     * @return This components host.
     */
    @Override
    public IGUIBasedComponentHost getComponentHost () {
        return parent;
    }

    /**
     * Method to get the rootAnchorPixel location as seen globally by the render system of OpenGL.
     *
     * @return The location of the top left pixel of this component
     */
    @Override
    public Coordinate2D getGlobalCoordinate () {
        return parent.getGlobalCoordinate().getTranslatedCoordinate(rootAnchorPixel);
    }

    /**
     * Returns to location of the most top left rendered Pixel of this Component relative to its parent.
     *
     * @return A Coordinate representing the Location of the most top left Pixel relative to its parent.
     */
    @Override
    public Coordinate2D getLocalCoordinate () {
        return rootAnchorPixel;
    }

    /**
     * Gets the Area Occupied by this Component, is locally oriented.
     *
     * @return A Plane detailing the the position and size of this Component.
     */
    @Override
    public Plane getAreaOccupiedByComponent () {
        return new Plane(getGlobalCoordinate(), WIDTH, height);
    }

    /**
     * Method to get the size of this component. Used to determine the total UI Size of this component.
     *
     * @return The size of this component.
     */
    @Override
    public Plane getSize () {
        return new Plane(0,0,WIDTH, height);
    }

    /**
     * Method gets called before the component gets rendered, allows for animations to calculate through.
     *
     * @param mouseX          The X-Coordinate of the mouse.
     * @param mouseY          The Y-Coordinate of the mouse.
     * @param partialTickTime The partial tick time, used to calculate fluent animations.
     */
    @Override
    public void update (int mouseX, int mouseY, float partialTickTime) {

    }

    /**
     * Function used to draw this components background. Usually this will incorporate all of the components visual
     * objects. A good example of a Component that only uses the drawBackground function is the BackgroundComponent.
     *
     * @param mouseX The current X-Coordinate of the mouse
     * @param mouseY The current Y-Coordinate of the mouse
     */
    @Override
    public void drawBackground (int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GuiHelper.drawColoredRect(innerArea, 0, new MinecraftColor(Colors.General.GRAY));
        GlStateManager.popMatrix();
    }

    /**
     * Function used to draw this components foreground. Usually this will incorporate very few of teh components visual
     * Objects. A good example of a Component that only uses the drawForeground function is the GUIDescriptionLabel (The
     * labels that describe thins like your inventory and the TE's inventory).
     *
     * @param mouseX The current X-Coordinate of the mouse
     * @param mouseY The current Y-Coordinate of the mouse
     */
    @Override
    public void drawForeground (int mouseX, int mouseY) {
        //NOOP
    }

    /**
     * Function called when the mouse was clicked inside of this component. Either it should pass this function to its
     * SubComponents (making sure that it recalculates the location and checks if it is inside before hand, handle the
     * Click them self or both.
     * <p/>
     * When this Component or one of its SubComponents handles the Click it should return True.
     *
     * @param relativeMouseX The relative (to the Coordinate returned by @see #getLocalCoordinate) X-Coordinate of the
     *                       mouseclick.
     * @param relativeMouseY The relative (to the Coordinate returned by @see #getLocalCoordinate) Y-Coordinate of the
     *                       mouseclick.
     * @param mouseButton    The 0-BasedIndex of the mouse button that was pressed.
     *
     * @return True when the click has been handled, false when it did not.
     */
    @Override
    public boolean handleMouseClickedInside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        for(IGUIComponent component : getAllComponents().values())
        {
            Coordinate2D location = component.getLocalCoordinate();
            Plane localOccupiedArea = component.getSize().Move(location.getXComponent(), location.getYComponent());

            if (!localOccupiedArea.ContainsCoordinate(relativeMouseX, relativeMouseY))
                continue;

            if (component.handleMouseClickedInside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton))
                return true;
        }

        if (innerArea.ContainsCoordinate(relativeMouseX, relativeMouseY))
        {
            state.setTarget(state.getMaximum() * (relativeMouseY - 10) / (float) (height - 20));

            return true;
        }

        return false;
    }

    /**
     * Function called when the mouse was clicked outside of this component. It is only called when the function Either
     * it should pass this function to its SubComponents (making sure that it recalculates the location and checks if it
     * is inside before hand, handle the Click them self or both.
     * <p/>
     * When this Component or one of its SubComponents handles the Click it should return True.
     *
     * @param relativeMouseX The relative (to the Coordinate returned by @see #getLocalCoordinate) X-Coordinate of the
     *                       mouseclick.
     * @param relativeMouseY The relative (to the Coordinate returned by @see #getLocalCoordinate) Y-Coordinate of the
     *                       mouseclick.
     * @param mouseButton    The 0-BasedIndex of the mouse button that was pressed.
     *
     * @return True when the click has been handled, false when it did not.
     */
    @Override
    public boolean handleMouseClickedOutside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        for (IGUIComponent component : getAllComponents().values()) {
            if (component.requiresForcedMouseInput()) {
                Coordinate2D location = component.getLocalCoordinate();
                component.handleMouseClickedOutside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton);
            }
        }

        return false;
    }

    /**
     * Method to check if this function should capture all of the buttons pressed on the mouse regardless of the press
     * location was inside or outside of the Component.
     *
     * @return True when all the mouse clicks should be captured by this component.
     */
    @Override
    public boolean requiresForcedMouseInput () {
        return false;
    }

    /**
     * Function called when a Key is typed.
     *
     * @param key The key that was typed.
     */
    @Override
    public void handleKeyTyped (char key) {

    }

    @Override
    public ArrayList<String> getToolTipContent () {
        return null;
    }

    /**
     * Function to get the IGUIManager.
     *
     * @return Returns the current GUIManager.
     */
    @Override
    public IGUIManager getManager () {
        return parent.getManager();
    }

    /**
     * Function to set the IGUIManager
     *
     * @param newManager THe new IGUIManager.
     */
    @Override
    public void setManager (IGUIManager newManager) {
        parent.setManager(newManager);
    }

    public void onInternalButtonClick(IGUIComponent component)
    {
        if (!(component instanceof ComponentButton))
            throw new IllegalArgumentException("The given component is not a Button.");

        ScrollBarComponentState state = (ScrollBarComponentState) getState();

        if (component.getID().endsWith(".Up"))
        {
            state.onUpClick();
        }
        else if(component.getID().endsWith(".Down"))
        {
            state.onDownClick();
        }
        else if(component.getID().endsWith(".ScrollDrag"))
        {
            state.onDragClick();
        }


    }

    /**
     * Method called by the rendering system to perform animations. It is called before the component is rendered but
     * after the component has been updated.
     *
     * @param partialTickTime The current partial tick time.
     */
    @Override
    public void performAnimation (float partialTickTime) {
        state.onAnimationClick(partialTickTime);
    }
}
