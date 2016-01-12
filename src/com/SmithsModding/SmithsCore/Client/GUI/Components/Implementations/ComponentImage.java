package com.SmithsModding.SmithsCore.Client.GUI.Components.Implementations;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Util.Client.*;
import com.SmithsModding.SmithsCore.Util.Client.GUI.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;

/**
 * Created by Marc on 09.01.2016.
 */
public class ComponentImage implements IGUIComponent {

    private String uniqueID;
    private IGUIComponentState state;
    private IGUIBasedComponentHost root;

    private Coordinate2D rootAnchorPixel;

    private CustomResource image;

    public ComponentImage (String uniqueID, IGUIComponentState state, IGUIBasedComponentHost root, Coordinate2D rootAnchorPixel, CustomResource image) {
        this.uniqueID = uniqueID;

        this.state = state;
        this.state.setComponent(this);

        this.root = root;
        this.rootAnchorPixel = rootAnchorPixel;
        this.image = image;
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
     * Visibility of it. But also things like the Displayed Text of a label or TextBox are stored in the Components
     * State Object.
     *
     * @return This Components State Object.
     */
    @Override
    public IGUIComponentState getState () {
        return state;
    }

    /**
     * Function to get this Components Host.
     *
     * @return This Components Host.
     */
    @Override
    public IGUIBasedComponentHost getComponentHost () {
        return root;
    }

    /**
     * Method to get the rootAnchorPixel location as seen globally by the render system of OpenGL.
     *
     * @return The location of the top left pixel of this component
     */
    @Override
    public Coordinate2D getGlobalCoordinate () {
        return root.getGlobalCoordinate().getTranslatedCoordinate(getLocalCoordinate());
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
        return new Plane(getGlobalCoordinate(), image.getWidth(), image.getHeight());
    }

    /**
     * Method to get the size of this component. Used to determine the total UI Size of this component.
     *
     * @return The size of this component.
     */
    @Override
    public Plane getSize () {
        return new Plane(0, 0, image.getWidth(), image.getHeight());
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
        //NOOP
    }

    /**
     * Function used to draw this components background. Usually this will incorporate all of the Components visual
     * objects. A good example of a Component that only uses the drawBackground function is the BackgroundComponent.
     *
     * @param mouseX The current X-Coordinate of the Mouse
     * @param mouseY The current Y-Coordinate of the Mouse
     */
    @Override
    public void drawBackground (int mouseX, int mouseY) {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GuiHelper.bindTexture(TextureMap.locationBlocksTexture);
        GuiHelper.drawTexturedModelRectFromIcon(0, 0, 0, image.getIcon(), image.getIcon().getIconWidth(), image.getIcon().getIconHeight());
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

    /**
     * Function used to draw this components foreground. Usually this will incorporate very few of teh Components visual
     * Objects. A good example of a Component that only uses the drawForeground function is the GUIDescriptionLabel (The
     * labels that describe thins like your inventory and the TE's inventory).
     *
     * @param mouseX The current X-Coordinate of the Mouse
     * @param mouseY The current Y-Coordinate of the Mouse
     */
    @Override
    public void drawForeground (int mouseX, int mouseY) {
        //NOOP
    }

    /**
     * Function called when the Mouse was clicked inside of this component. Either it should pass this function to its
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
        return false;
    }

    /**
     * Function called when the Mouse was clicked outside of this component. It is only called when the function Either
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
}
