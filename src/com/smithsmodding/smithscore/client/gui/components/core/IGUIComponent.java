package com.smithsmodding.smithscore.client.gui.components.core;

import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.state.IGUIComponentState;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import com.smithsmodding.smithscore.util.common.positioning.Plane;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 02.12.2015
 * 10:44
 *
 * Copyrighted according to Project specific license
 */
public interface IGUIComponent
{

    /**
     * Function used to get the ID of the Component.
     *
     * @return The ID of this Component.
     */
    @Nonnull
    String getID();

    /**
     * Function used to get the StateObject of this Component.
     * A good example of what to find in the state Object is the Visibility of it.
     * But also things like the Displayed Text of a label or TextBox are stored in the components
     * state Object.
     *
     * @return This components state Object.
     */
    @Nonnull
    IGUIComponentState getState();

    /**
     * Function to get this components host.
     *
     * @return This components host.
     */
    @Nonnull
    IGUIBasedComponentHost getComponentHost();

    /**
     * Method to get the rootAnchorPixel location as seen globally by the render system of OpenGL.
     *
     * @return The location of the top left pixel of this component
     */
    @Nonnull
    Coordinate2D getGlobalCoordinate ();

    /**
     * Returns to location of the most top left rendered Pixel of this Component relative to its parent.
     *
     * @return A Coordinate representing the Location of the most top left Pixel relative to its parent.
     */
    @Nonnull
    Coordinate2D getLocalCoordinate ();

    /**
     * Gets the Area Occupied by this Component, is locally oriented.
     *
     * @return A Plane detailing the the position and size of this Component.
     */
    @Nonnull
    Plane getAreaOccupiedByComponent();

    /**
     * Method to get the size of this component. Used to determine the total UI Size of this component.
     *
     * @return The size of this component.
     */
    @Nonnull
    Plane getSize ();

    /**
     * Method gets called before the component gets rendered, allows for animations to calculate through.
     *
     * @param mouseX The X-Coordinate of the mouse.
     * @param mouseY The Y-Coordinate of the mouse.
     * @param partialTickTime The partial tick time, used to calculate fluent animations.
     */
    @Nonnull
    void update(@Nonnull int mouseX, @Nonnull int mouseY, @Nonnull float partialTickTime);

    /**
     * Function used to draw this components background.
     * Usually this will incorporate all of the components visual objects.
     * A good example of a Component that only uses the drawBackground function is the
     * BackgroundComponent.
     *
     * @param mouseX The current X-Coordinate of the mouse
     * @param mouseY The current Y-Coordinate of the mouse
     */
    void drawBackground(@Nonnull int mouseX, @Nonnull int mouseY);

    /**
     * Function used to draw this components foreground.
     * Usually this will incorporate very few of teh components visual Objects.
     * A good example of a Component that only uses the drawForeground function is the
     * GUIDescriptionLabel (The labels that describe thins like your inventory and the TE's inventory).
     *
     * @param mouseX The current X-Coordinate of the mouse
     * @param mouseY The current Y-Coordinate of the mouse
     */
    void drawForeground(@Nonnull int mouseX, @Nonnull int mouseY);

    /**
     * Function called when the mouse was clicked inside of this component.
     * Either it should pass this function to its SubComponents
     * (making sure that it recalculates the location and checks if it is inside before hand,
     * handle the Click them self or both.
     *
     * When this Component or one of its SubComponents handles the Click it should return True.
     *
     * @param relativeMouseX The relative (to the Coordinate returned by @see #getLocalCoordinate) X-Coordinate of the mouseclick.
     * @param relativeMouseY The relative (to the Coordinate returned by @see #getLocalCoordinate) Y-Coordinate of the mouseclick.
     * @param mouseButton The 0-BasedIndex of the mouse button that was pressed.
     *
     * @return True when the click has been handled, false when it did not.
     */
    @Nonnull
    boolean handleMouseClickedInside (@Nonnull int relativeMouseX, @Nonnull int relativeMouseY, @Nonnull int mouseButton);

    /**
     * Function called when the mouse was clicked outside of this component.
     * It is only called when the function
     * Either it should pass this function to its SubComponents
     * (making sure that it recalculates the location and checks if it is inside before hand,
     * handle the Click them self or both.
     *
     * When this Component or one of its SubComponents handles the Click it should return True.
     *
     * @param relativeMouseX The relative (to the Coordinate returned by @see #getLocalCoordinate) X-Coordinate of the mouseclick.
     * @param relativeMouseY The relative (to the Coordinate returned by @see #getLocalCoordinate) Y-Coordinate of the mouseclick.
     * @param mouseButton The 0-BasedIndex of the mouse button that was pressed.
     *
     * @return True when the click has been handled, false when it did not.
     */
    @Nonnull
    boolean handleMouseClickedOutside (@Nonnull int relativeMouseX, @Nonnull int relativeMouseY, @Nonnull int mouseButton);

    /**
     * Method to check if this function should capture all of the buttons pressed on the mouse
     * regardless of the press location was inside or outside of the Component.
     *
     * @return True when all the mouse clicks should be captured by this component.
     */
    @Nonnull
    boolean requiresForcedMouseInput();

    /**
     * Function called when a Key is typed.
     *
     * @param key The key that was typed.
     * @param keyCode The vanilla Minecraft keycode.
     * @return True when handled, false when not.
     */
    @Nonnull
    boolean handleKeyTyped(@Nonnull char key, @Nonnull int keyCode);

    @Nullable
    ArrayList<String> getToolTipContent ();
}
