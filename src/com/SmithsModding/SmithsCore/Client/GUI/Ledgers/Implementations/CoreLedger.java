package com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Implementations;

import com.SmithsModding.SmithsCore.Client.GUI.Animation.*;
import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Components.Implementations.*;
import com.SmithsModding.SmithsCore.Client.GUI.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Management.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import com.SmithsModding.SmithsCore.Util.Client.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.client.*;

import java.util.*;

/**
 * Created by marcf on 12/28/2015.
 */
public abstract class CoreLedger implements IGUILedger, IAnimatibleGuiComponent {

    private String uniqueID;
    private LedgerComponentState state;
    private IGUIBasedLedgerHost root;

    private LedgerConnectionSide side;
    private LinkedHashMap<String, IGUIComponent> components = new LinkedHashMap<String, IGUIComponent>();

    private CustomResource ledgerIcon;
    private String translatedLedgerHeader;
    private MinecraftColor color;

    private int headerHeight;
    private int headerWidth;

    public CoreLedger (String uniqueID, LedgerComponentState state, IGUIBasedLedgerHost root, LedgerConnectionSide side, LinkedHashMap<String, IGUIComponent> components, CustomResource ledgerIcon, String translatedLedgerHeader, MinecraftColor color) {
        this.uniqueID = uniqueID;
        this.state = state;
        this.state.setComponent(this);
        this.root = root;
        this.side = side;
        this.components = components;
        this.ledgerIcon = ledgerIcon;
        this.translatedLedgerHeader = translatedLedgerHeader;
        this.color = color;

        headerHeight = ledgerIcon.getHeight() + 16;
        headerWidth = ledgerIcon.getWidth() + 16;
    }

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
        return root;
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
        return root.getLedgerManager().getLedgerGlobalCoordinate(getPrimarySide(), getID()).getTranslatedCoordinate(getLocalCoordinate());
    }

    /**
     * Returns to location of the most top left rendered Pixel of this Component relative to its parent.
     *
     * @return A Coordinate representing the Location of the most top left Pixel relative to its parent.
     */
    @Override
    public Coordinate2D getLocalCoordinate() {
        if (getPrimarySide() == LedgerConnectionSide.LEFT) {
            return new Coordinate2D(-1 * ( getSize().getWidth() - 4 ), 0);
        } else {
            return new Coordinate2D(-4, 0);
        }
    }

    /**
     * Gets the Area Occupied by this Component, is locally oriented.
     *
     * @return A Plane detailing the the position and size of this Component.
     */
    @Override
    public Plane getAreaOccupiedByComponent() {
        return new Plane(getGlobalCoordinate(), getSize().getWidth(), getSize().getWidth());
    }

    /**
     * Method to get the size of this component. Used to determine the total UI Size of this component.
     *
     * @return The size of this component.
     */
    @Override
    public Plane getSize() {
        return new Plane(0, 0, (int) ( headerWidth + ( getMaxWidth() - headerWidth ) * state.getOpenProgress() ), (int) ( headerHeight + ( getMaxHeight() - headerHeight ) * state.getOpenProgress() ));
    }

    /**
     * Method used by the rendering and animation system to determine the max size of the Ledger.
     *
     * @return An int bigger then 24 that describes the maximum width of the Ledger when expanded.
     */
    public abstract int getMaxWidth ();

    /**
     * Method used by the rendering and animation system to determine the max size of the Ledger.
     *
     * @return An int bigger then 24 that describes the maximum height of the Ledger when expanded.
     */
    public abstract int getMaxHeight ();

    /**
     * Method gets called before the component gets rendered, allows for animations to calculate through.
     *
     * @param mouseX          The X-Coordinate of the mouse.
     * @param mouseY          The Y-Coordinate of the mouse.
     * @param partialTickTime The partial tick time, used to calculate fluent animations.
     */
    @Override
    public void update(int mouseX, int mouseY, float partialTickTime) {
        //The ledger it self has no update only an animation.
    }

    /**
     * Method called by the rendering system to perform animations. It is called before the component is rendered but
     * after the component has been updated.
     *
     * @param partialTickTime The current partial tick time.
     */
    @Override
    public void performAnimation (float partialTickTime) {
        if (state.getOpenState()) {
            float newTotalAnimationTicks = ( state.getOpenProgress() * getAnimationTime() ) + partialTickTime;
            state.setOpenProgress(newTotalAnimationTicks / getAnimationTime());
        } else {
            float newTotalAnimationTicks = ( state.getOpenProgress() * getAnimationTime() ) - partialTickTime;
            state.setOpenProgress(newTotalAnimationTicks / getAnimationTime());
        }
    }

    /**
     * Method used by the animation system of the Ledger to determine how fast the animation should go.
     * Measured in game update ticks -> 20 Ticks is 1 Second. The animation system will take the partial tick time
     * into account when it calculates the update of pixels.
     *
     * @return The amount of game ticks it should take to complete the opening and closing animation.
     */
    public abstract int getAnimationTime ();

    @Override
    public boolean shouldScissor () {
        return true;
    }

    @Override
    public Plane getGlobalScissorLocation () {
        return new Plane(getGlobalCoordinate().getTranslatedCoordinate(new Coordinate2D(4, 4)), getSize().getWidth() - 8, getSize().getHeigth() - 8);
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
        ComponentBorder componentBorder = new ComponentBorder(getID() + ".background", this, new Coordinate2D(0, 0), getSize().getWidth(), getSize().getHeigth(), color, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts);
        ComponentImage componentImage = new ComponentImage(getID() + ".header.icon", new CoreComponentState(null), this, new Coordinate2D(8, 8), ledgerIcon);
        ComponentLabel componentLabel = new ComponentLabel(getID() + ".header.label", this, new CoreComponentState(null), new Coordinate2D(headerWidth, 8), new MinecraftColor(MinecraftColor.WHITE), Minecraft.getMinecraft().fontRendererObj, translatedLedgerHeader);

        getRootGuiObject().getRenderManager().renderBackgroundComponent(componentBorder, false);
        getRootGuiObject().getRenderManager().renderBackgroundComponent(componentImage, false);
        getRootGuiObject().getRenderManager().renderBackgroundComponent(componentLabel, false);
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
        //NOOP
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
     * Method to get the Root GUI Object that this Component is part of.
     *
     * @return The GUI that this component is part of.
     */
    @Override
    public GuiContainerSmithsCore getRootGuiObject () {
        return getComponentHost().getRootGuiObject();
    }

    /**
     * Method to get the GUI Roots Manager.
     *
     * @return The Manager that is at the root for the GUI Tree.
     */
    @Override
    public IGUIManager getRootManager() {
        return root.getRootManager();
    }

    /**
     * Function to get all the Components registered to this Host.
     *
     * @return A ID to Component map that holds all the Components (but not their SubComponents) of this Host.
     */
    @Override
    public LinkedHashMap<String, IGUIComponent> getAllComponents() {
        return components;
    }

    /**
     * Method used to register a new Component to this Host.
     *
     * @param component The new component.
     */
    @Override
    public void registerNewComponent (IGUIComponent component) {
        components.put(component.getID(), component);
    }


    /**
     * Function to get the IGUIManager.
     *
     * @return Returns the current GUIManager.
     */
    @Override
    public IGUIManager getManager() {
        return root.getManager();
    }

    /**
     * Function to set the IGUIManager
     *
     * @param newManager THe new IGUIManager.
     */
    @Override
    public void setManager(IGUIManager newManager) {
        root.setManager(newManager);
    }
}
