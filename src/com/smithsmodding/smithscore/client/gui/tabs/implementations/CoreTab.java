package com.smithsmodding.smithscore.client.gui.tabs.implementations;

import com.smithsmodding.smithscore.client.gui.*;
import com.smithsmodding.smithscore.client.gui.components.core.*;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.management.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.client.gui.tabs.core.*;
import com.smithsmodding.smithscore.util.client.color.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import scala.actors.threadpool.Arrays;

import java.util.*;

/**
 * Created by marcf on 1/17/2016.
 */
public abstract class CoreTab implements IGUITab {

    String uniqueID;
    IGUIBasedTabHost root;
    IGUIComponentState state;

    LinkedHashMap<String, IGUIComponent> components = new LinkedHashMap<String, IGUIComponent>();

    ItemStack displayStack;
    MinecraftColor tabColor;
    String toolTipString;

    public CoreTab (String uniqueID, IGUIBasedTabHost root, IGUIComponentState state, ItemStack displayStack, MinecraftColor tabColor, String toolTipString) {
        this.uniqueID = uniqueID;
        this.root = root;
        this.state = state;
        this.displayStack = displayStack;
        this.tabColor = tabColor;
        this.toolTipString = toolTipString;

        this.state.setComponent(this);
    }

    /**
     * Method to get the host of this Tab
     *
     * @return The current tabs host.
     */
    @Override
    public IGUIBasedTabHost getTabHost() {
        return root;
    }

    /**
     * Method to get this tabs TabManger.
     *
     * @return The TabManager of this Tab.
     */
    @Override
    public ITabManager getTabManager () {
        return getTabHost().getTabManager();
    }

    @Override
    public ItemStack getDisplayStack() {
        return displayStack;
    }

    /**
     * Method to get the tabs color.
     *
     * @return The tabs color.
     */
    @Override
    public MinecraftColor getTabColor () {
        return tabColor;
    }

    /**
     * Function to get the tooltiptext of the gui System.
     *
     * @return The tooltip contents.
     */
    @Override
    public ArrayList<String> getIconToolTipText() {
        return (ArrayList<String>) Arrays.asList(new String[] {toolTipString});
    }

    /**
     * Method used to register a new Component to this host.
     *
     * @param component The new component.
     */
    @Override
    public void registerNewComponent(IGUIComponent component) {
        if (component instanceof IGUIBasedComponentHost)
            ((IGUIBasedComponentHost) component).registerComponents((IGUIBasedComponentHost) component);

        components.put(component.getID(), component);
    }

    /**
     * Method to get the Root gui Object that this Component is part of.
     *
     * @return The gui that this component is part of.
     */
    @Override
    public GuiContainerSmithsCore getRootGuiObject() {
        return root.getRootGuiObject();
    }

    /**
     * Method to get the gui Roots Manager.
     *
     * @return The Manager that is at the root for the gui Tree.
     */
    @Override
    public IGUIManager getRootManager() {
        return root.getRootManager();
    }

    /**
     * Function to get all the components registered to this host.
     *
     * @return A ID to Component map that holds all the components (but not their SubComponents) of this host.
     */
    @Override
    public LinkedHashMap<String, IGUIComponent> getAllComponents() {
        return components;
    }

    public IGUIComponent getComponentByID (String uniqueUIID) {
        if (getID().equals(uniqueUIID))
            return this;

        if (getAllComponents().get(uniqueUIID) != null)
            return getAllComponents().get(uniqueUIID);

        for (IGUIComponent childComponent : getAllComponents().values()) {
            if (childComponent instanceof IGUIBasedComponentHost) {
                IGUIComponent foundComponent = ( (IGUIBasedComponentHost) childComponent ).getComponentByID(uniqueUIID);

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
    public String getID() {
        return uniqueID;
    }

    /**
     * Function used to get the StateObject of this Component.
     * A good example of what to find in the state Object is the Visibility of it.
     * But also things like the Displayed Text of a label or TextBox are stored in the components
     * state Object.
     *
     * @return This components state Object.
     */
    @Override
    public IGUIComponentState getState() {
        return state;
    }

    /**
     * Function to get this components host.
     *
     * @return This components host.
     */
    @Override
    public IGUIBasedComponentHost getComponentHost() {
        return root;
    }

    /**
     * Method to get the rootAnchorPixel location as seen globally by the render system of OpenGL.
     *
     * @return The location of the top left pixel of this component
     */
    @Override
    public Coordinate2D getGlobalCoordinate() {
        return root.getGlobalCoordinate().getTranslatedCoordinate(getLocalCoordinate());
    }

    /**
     * Returns to location of the most top left rendered Pixel of this Component relative to its parent.
     *
     * @return A Coordinate representing the Location of the most top left Pixel relative to its parent.
     */
    @Override
    public Coordinate2D getLocalCoordinate() {
        ITabManager manager = root.getTabManager();

        if (manager.getTabs().size() > 2)
            return new Coordinate2D(0,0);

        else return new Coordinate2D(0, manager.getDisplayAreaVerticalOffset());
    }

    /**
     * Gets the Area Occupied by this Component, is locally oriented.
     *
     * @return A Plane detailing the the position and size of this Component.
     */
    @Override
    public Plane getAreaOccupiedByComponent() {
        Plane size = getSize();

        return new Plane(getGlobalCoordinate(), size.getWidth(), size.getHeigth());
    }

    /**
     * Method to get the size of this component. Used to determine the total UI Size of this component.
     *
     * @return The size of this component.
     */
    @Override
    public Plane getSize() {
        Plane area = new Plane(0, 0, 0, 0);

        for (IGUIComponent component : getAllComponents().values()) {
            area.IncludeCoordinate(new Plane(component.getLocalCoordinate(), component.getSize().getWidth(), component.getSize().getHeigth()));
        }

        return area;
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
        //NOOP
    }

    /**
     * Function used to draw this components background.
     * Usually this will incorporate all of the components visual objects.
     * A good example of a Component that only uses the drawBackground function is the
     * BackgroundComponent.
     *
     * @param mouseX The current X-Coordinate of the mouse
     * @param mouseY The current Y-Coordinate of the mouse
     */
    @Override
    public void drawBackground(int mouseX, int mouseY) {
        ITabManager manager = root.getTabManager();

        int tabIndex = manager.getCurrentTabIndex();
        int selectorIndex = tabIndex % manager.getTabSelectorCount();

        for (int i = tabIndex - selectorIndex; i < tabIndex - selectorIndex + manager.getTabSelectorCount(); i++) {
            if (i != selectorIndex) {
                final IGUITab tab = manager.getTabFromSelectorIndex(i);

                Coordinate2D selectorRootCoord = new Coordinate2D(manager.getSelectorsHorizontalOffset() + manager.getTabSelectorWidth() * i, manager.getInActiveSelectorVerticalOffset()).getTranslatedCoordinate(new Coordinate2D(0, -1 * ( manager.getTabSelectorHeight() + 1 ))).getTranslatedCoordinate(new Coordinate2D(0, manager.getInActiveSelectorVerticalOffset()));
                ComponentBorder inActiveBorder = new ComponentBorder(this.uniqueID + ".TabSelectors." + i + ".Background", this, selectorRootCoord, manager.getTabSelectorWidth(), manager.getTabSelectorHeight(), new MinecraftColor(new MinecraftColor(tab.getTabColor()).darker()), ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.StraightVertical, ComponentBorder.CornerTypes.StraightVertical);

                int displayOffset = ( manager.getTabSelectorWidth() - 16 ) / 2;
                ComponentItemStackDisplay selectorDisplay = new ComponentItemStackDisplay(this.uniqueID + ".TabSelectors." + i + ".Display", this, new CoreComponentState(), selectorRootCoord.getTranslatedCoordinate(new Coordinate2D(displayOffset, displayOffset)), tab.getDisplayStack()) {
                    @Override
                    public ArrayList<String> getToolTipContent () {
                        return tab.getToolTipContent();
                    }
                };

                getRootGuiObject().getRenderManager().renderBackgroundComponent(inActiveBorder, getState().isEnabled());
                getRootGuiObject().getRenderManager().renderBackgroundComponent(selectorDisplay, getState().isEnabled());

                if (selectorDisplay.getAreaOccupiedByComponent().ContainsCoordinate(mouseX, mouseY)) {
                    GlStateManager.translate(0, 0, 5);
                    getRootGuiObject().drawHoveringText(tab.getToolTipContent(), mouseX + 4, mouseY + 4, Minecraft.getMinecraft().fontRendererObj);
                    GlStateManager.translate(0, 0, -5);
                }
            }
        }

        final IGUITab tab = manager.getTabFromSelectorIndex(selectorIndex);

        Coordinate2D selectorRootCoord = new Coordinate2D(manager.getSelectorsHorizontalOffset() + manager.getTabSelectorWidth() * selectorIndex, manager.getInActiveSelectorVerticalOffset()).getTranslatedCoordinate(new Coordinate2D(0, -1 * ( manager.getTabSelectorHeight() + 1 )));
        ComponentBorder activeBorder = new ComponentBorder(this.uniqueID + ".TabSelectors." + selectorIndex + ".Background", this, selectorRootCoord, manager.getTabSelectorWidth(), manager.getTabSelectorHeight(), tab.getTabColor(), ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Outwarts, ComponentBorder.CornerTypes.Outwarts);

        int displayOffset = ( activeBorder.getSize().getWidth() - 16 ) / 2;
        ComponentItemStackDisplay selectorDisplay = new ComponentItemStackDisplay(this.uniqueID + ".TabSelectors." + selectorIndex + ".Display", this, new CoreComponentState(), selectorRootCoord.getTranslatedCoordinate(new Coordinate2D(displayOffset, displayOffset)), tab.getDisplayStack()) {
            @Override
            public ArrayList<String> getToolTipContent () {
                return tab.getToolTipContent();
            }
        };

        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 2);

        getRootGuiObject().getRenderManager().renderBackgroundComponent(activeBorder, getState().isEnabled());
        getRootGuiObject().getRenderManager().renderBackgroundComponent(selectorDisplay, getState().isEnabled());

        GlStateManager.translate(0, 0, -2);
        GlStateManager.popMatrix();

        /*
        TODO: Fix tooltip drawing on TabSelectors.

        if (selectorDisplay.getAreaOccupiedByComponent().ContainsCoordinate(mouseX, mouseY))
        {
            GlStateManager.translate(0,0,5);
            getRootGuiObject().drawHoveringText(tab.getToolTipContent(), mouseX + 4, mouseY + 4, Minecraft.getMinecraft().fontRendererObj);
            GlStateManager.translate(0,0,-5);
        }
        */
    }

    /**
     * Function used to draw this components foreground.
     * Usually this will incorporate very few of teh components visual Objects.
     * A good example of a Component that only uses the drawForeground function is the
     * GUIDescriptionLabel (The labels that describe thins like your inventory and the TE's inventory).
     *
     * @param mouseX The current X-Coordinate of the mouse
     * @param mouseY The current Y-Coordinate of the mouse
     */
    @Override
    public void drawForeground(int mouseX, int mouseY) {
        //NOOP
    }

    /**
     * Method to check if this function should capture all of the buttons pressed on the mouse regardless of the press
     * location was inside or outside of the Component.
     *
     * @return True when all the mouse clicks should be captured by this component.
     */
    @Override
    public boolean requiresForcedMouseInput () {
        for (IGUIComponent component : components.values()) {
            if (component.requiresForcedMouseInput())
                return true;
        }

        return false;
    }

    /**
     * Function called when the mouse was clicked outside of this component. It is only called when the function
     * requiresForcedMouseInput() return true Either it should pass this function to its SubComponents (making sure that
     * it recalculates the location and checks if it is inside before hand, handle the Click them self or both.
     *
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
        for (IGUIComponent component : components.values()) {
            if (component.requiresForcedMouseInput()) {
                Coordinate2D location = component.getLocalCoordinate();
                component.handleMouseClickedOutside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton);
            }
        }

        return false;
    }

    /**
     * Function called when the mouse was clicked inside of this component. Either it should pass this function to its
     * SubComponents (making sure that it recalculates the location and checks if it is inside before hand, handle the
     * Click them self or both.
     *
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
        if (relativeMouseY < getTabManager().getDisplayAreaVerticalOffset() && relativeMouseX > getTabManager().getSelectorsHorizontalOffset() && relativeMouseX < getSize().getWidth() - getTabManager().getSelectorsHorizontalOffset() && relativeMouseX < getTabManager().getSelectorsHorizontalOffset() + ( getTabManager().getTabSelectorWidth() * getTabManager().getTabSelectorCount() ) && relativeMouseY > 0) {
            int selectorMouseX = relativeMouseX - getTabManager().getSelectorsHorizontalOffset();
            int selectorIndex = selectorMouseX / getTabManager().getTabSelectorWidth();

            getTabManager().setActiveTab(getTabManager().getTabFromSelectorIndex(selectorIndex));

            return false;
        }


        for (IGUIComponent component : components.values()) {
            Coordinate2D location = component.getLocalCoordinate().getTranslatedCoordinate(new Coordinate2D(0, getTabManager().getDisplayAreaVerticalOffset()));
            Plane localOccupiedArea = component.getSize().Move(location.getXComponent(), location.getYComponent());

            if (!localOccupiedArea.ContainsCoordinate(relativeMouseX, relativeMouseY))
                continue;

            if (component.handleMouseClickedInside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton))
                return true;

        }

        return false;
    }

    /**
     * Function called when a Key is typed.
     *
     * @param key The key that was typed.
     */
    @Override
    public void handleKeyTyped (char key) {
        for (IGUIComponent component : components.values()) {
            component.handleKeyTyped(key);
        }
    }

    @Override
    public ArrayList<String> getToolTipContent() {
        return new ArrayList<String>();
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

    /*




     */
}
