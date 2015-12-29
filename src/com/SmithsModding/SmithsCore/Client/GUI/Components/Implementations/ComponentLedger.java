package com.SmithsModding.SmithsCore.Client.GUI.Components.Implementations;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.IGUIComponent;
import com.SmithsModding.SmithsCore.Client.GUI.Host.IGUIBasedComponentHost;
import com.SmithsModding.SmithsCore.Client.GUI.Management.IGUIManager;
import com.SmithsModding.SmithsCore.Client.GUI.Management.RenderManager;
import com.SmithsModding.SmithsCore.Client.GUI.State.CoreComponentState;
import com.SmithsModding.SmithsCore.Client.GUI.State.IGUIComponentState;
import com.SmithsModding.SmithsCore.Util.Client.CustomResource;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.Coordinate2D;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.Plane;
import net.minecraft.client.gui.Gui;

import java.util.LinkedHashMap;

/**
 * Created by marcf on 12/28/2015.
 */
public abstract class ComponentLedger implements IGUIBasedComponentHost
{


    private String uniqueUIID;
    private IGUIBasedComponentHost parent;
    private IGUIComponentState state;

    private LinkedHashMap<String, IGUIComponent> componentHashMap = new LinkedHashMap<String, IGUIComponent>();

    private String header;
    private CustomResource resource;



    /**
     * Method used to register a new Component to this Host.
     *
     * @param component The new component.
     */
    @Override
    public void registerNewComponent(IGUIComponent component) {

    }

    /**
     * Method to get the Root GUI Object that this Component is part of.
     *
     * @return The GUI that this component is part of.
     */
    @Override
    public Gui getRootGuiObject() {
        return null;
    }

    /**
     * Method to get the GUI Roots Manager.
     *
     * @return The Manager that is at the root for the GUI Tree.
     */
    @Override
    public IGUIManager getRootManager() {
        return null;
    }

    /**
     * Function to get all the Components registered to this Host.
     *
     * @return A ID to Component map that holds all the Components (but not their SubComponents) of this Host.
     */
    @Override
    public LinkedHashMap<String, IGUIComponent> getAllComponents() {
        return null;
    }

    /**
     * Function used to get the ID of the Component.
     *
     * @return The ID of this Component.
     */
    @Override
    public String getID() {
        return null;
    }

    /**
     * Function used to get the StateObject of this Component.
     * A good example of what to find in the state Object is the Visibility of it.
     * But also things like the Displayed Text of a label or TextBox are stored in the Components
     * State Object.
     *
     * @return This Components State Object.
     */
    @Override
    public IGUIComponentState getState() {
        return null;
    }

    /**
     * Function to get this Components Host.
     *
     * @return This Components Host.
     */
    @Override
    public IGUIBasedComponentHost getComponentHost() {
        return null;
    }

    /**
     * Method to get the rootAnchorPixel location as seen globally by the render system of OpenGL.
     *
     * @return The location of the top left pixel of this component
     */
    @Override
    public Coordinate2D getGlobalCoordinate() {
        return null;
    }

    /**
     * Returns to location of the most top left rendered Pixel of this Component relative to its parent.
     *
     * @return A Coordinate representing the Location of the most top left Pixel relative to its parent.
     */
    @Override
    public Coordinate2D getLocalCoordinate() {
        return null;
    }

    /**
     * Gets the Area Occupied by this Component, is locally oriented.
     *
     * @return A Plane detailing the the position and size of this Component.
     */
    @Override
    public Plane getAreaOccupiedByComponent() {
        return null;
    }

    /**
     * Method to get the size of this component. Used to determine the total UI Size of this component.
     *
     * @return The size of this component.
     */
    @Override
    public Plane getSize() {
        return null;
    }

    /**
     * Method gets called before the component gets rendered, allows for animations to calculate through.
     *
     * @param mouseX          The X-Coordinate of the mouse.
     * @param mouseY          The Y-Coordinate of the mouse.
     * @param partialTickTime The partial tick time, used to calculate fluent animations.
     */
    @Override
    public void update(int mouseX, int mouseY, float partialTickTime) {

    }

    /**
     * Function used to draw this components background.
     * Usually this will incorporate all of the Components visual objects.
     * A good example of a Component that only uses the drawBackground function is the
     * BackgroundComponent.
     *
     * @param mouseX The current X-Coordinate of the Mouse
     * @param mouseY The current Y-Coordinate of the Mouse
     */
    @Override
    public void drawBackground(int mouseX, int mouseY) {

    }

    /**
     * Function used to draw this components foreground.
     * Usually this will incorporate very few of teh Components visual Objects.
     * A good example of a Component that only uses the drawForeground function is the
     * GUIDescriptionLabel (The labels that describe thins like your inventory and the TE's inventory).
     *
     * @param mouseX The current X-Coordinate of the Mouse
     * @param mouseY The current Y-Coordinate of the Mouse
     */
    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }

    /**
     * Function called when the Mouse was clicked inside of this component.
     * Either it should pass this function to its SubComponents
     * (making sure that it recalculates the location and checks if it is inside before hand,
     * handle the Click them self or both.
     * <p/>
     * When this Component or one of its SubComponents handles the Click it should return True.
     *
     * @param relativeMouseX The relative (to the Coordinate returned by @see #getLocalCoordinate) X-Coordinate of the mouseclick.
     * @param relativeMouseY The relative (to the Coordinate returned by @see #getLocalCoordinate) Y-Coordinate of the mouseclick.
     * @param mouseButton    The 0-BasedIndex of the mouse button that was pressed.
     * @return True when the click has been handled, false when it did not.
     */
    @Override
    public boolean handleMouseClickedInside(int relativeMouseX, int relativeMouseY, int mouseButton) {
        return false;
    }

    /**
     * Function called when the Mouse was clicked outside of this component.
     * It is only called when the function
     * Either it should pass this function to its SubComponents
     * (making sure that it recalculates the location and checks if it is inside before hand,
     * handle the Click them self or both.
     * <p/>
     * When this Component or one of its SubComponents handles the Click it should return True.
     *
     * @param relativeMouseX The relative (to the Coordinate returned by @see #getLocalCoordinate) X-Coordinate of the mouseclick.
     * @param relativeMouseY The relative (to the Coordinate returned by @see #getLocalCoordinate) Y-Coordinate of the mouseclick.
     * @param mouseButton    The 0-BasedIndex of the mouse button that was pressed.
     * @return True when the click has been handled, false when it did not.
     */
    @Override
    public boolean handleMouseClickedOutside(int relativeMouseX, int relativeMouseY, int mouseButton) {
        return false;
    }

    /**
     * Method to check if this function should capture all of the buttons pressed on the mouse
     * regardless of the press location was inside or outside of the Component.
     *
     * @return True when all the mouse clicks should be captured by this component.
     */
    @Override
    public boolean requiresForcedMouseInput() {
        return false;
    }

    /**
     * Function called when a Key is typed.
     *
     * @param key The key that was typed.
     */
    @Override
    public void handleKeyTyped(char key) {

    }

    /**
     * Function to get the IGUIManager.
     *
     * @return Returns the current GUIManager.
     */
    @Override
    public IGUIManager getManager() {
        return null;
    }

    /**
     * Function to set the IGUIManager
     *
     * @param newManager THe new IGUIManager.
     */
    @Override
    public void setManager(IGUIManager newManager) {

    }
}
