package com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Implementations;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.IGUIComponent;
import com.SmithsModding.SmithsCore.Client.GUI.Host.IGUIBasedComponentHost;
import com.SmithsModding.SmithsCore.Client.GUI.Host.IGUIBasedLedgerHost;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.IGUILedger;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.LedgerConnectionSide;
import com.SmithsModding.SmithsCore.Client.GUI.Management.IGUIManager;
import com.SmithsModding.SmithsCore.Client.GUI.State.IGUIComponentState;
import com.SmithsModding.SmithsCore.Client.GUI.State.LedgerComponentState;
import com.SmithsModding.SmithsCore.Util.Client.CustomResource;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.Coordinate2D;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.Plane;
import net.minecraft.client.gui.Gui;

import java.util.LinkedHashMap;

/**
 * Created by marcf on 12/28/2015.
 */
public abstract class CoreLedger implements IGUILedger {

    private String uniqueID;
    private LedgerComponentState state;
    private IGUIBasedLedgerHost root;

    private LedgerConnectionSide side;
    private LinkedHashMap<String, IGUIComponent> components = new LinkedHashMap<String, IGUIComponent>();

    private CustomResource ledgerIcon;
    private String translatedLedgerHeader;



    /**
     * Function used to get the ID of the Component.
     *
     * @return The ID of this Component.
     */
    @Override
    public String getID() {
        return uniqueID;
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
        return state;
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
     * Function to get this Components Host.
     *
     * @return This Components Host.
     */
    @Override
    public IGUIBasedLedgerHost getLedgerHost() {
        return root;
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
     * Method to get the primary rendered side of the Ledger.
     *
     * @return Left when the Ledger is rendered on the left side, right when rendered on the right side.
     */
    @Override
    public LedgerConnectionSide getPrimarySide() {
        return side;
    }

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
