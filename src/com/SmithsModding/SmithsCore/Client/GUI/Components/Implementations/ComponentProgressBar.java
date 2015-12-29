package com.SmithsModding.SmithsCore.Client.GUI.Components.Implementations;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Util.Client.*;
import com.SmithsModding.SmithsCore.Util.Client.GUI.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;

/**
 * Created by Marc on 25.12.2015.
 */
public class ComponentProgressBar implements IGUIComponent {

    private String uniqueID;
    private IGUIComponentState state;
    private IGUIBasedComponentHost root;

    private Coordinate2D localCoordinate;

    private ComponentDirection renderDirection;

    private CustomResource emptyResource;
    private CustomResource fullResource;

    public ComponentProgressBar (String uniqueID, IGUIComponentState state, IGUIBasedComponentHost root, Coordinate2D localCoordinate, ComponentDirection componentDirection, CustomResource emptyResource, CustomResource fullResource) {
        this.uniqueID = uniqueID;

        this.state = state;
        this.state.setComponent(this);

        this.root = root;
        this.localCoordinate = localCoordinate;
        this.renderDirection = componentDirection;
        this.emptyResource = emptyResource;
        this.fullResource = fullResource;
    }

    @Override
    public String getID () {
        return uniqueID;
    }

    @Override
    public IGUIComponentState getState () {
        return state;
    }

    @Override
    public IGUIBasedComponentHost getComponentHost() {
        return root;
    }

    @Override
    public Coordinate2D getGlobalCoordinate () {
        return root.getGlobalCoordinate().getTranslatedCoordinate(getLocalCoordinate());
    }

    @Override
    public Coordinate2D getLocalCoordinate () {
        return localCoordinate;
    }

    @Override
    public Plane getAreaOccupiedByComponent () {
        return new Plane(getLocalCoordinate(), getWidth(), getHeight());
    }

    @Override
    public Plane getSize () {
        return new Plane(0, 0, getWidth(), getHeight());
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

    @Override
    public void drawBackground (int mouseX, int mouseY) {
        GuiHelper.drawResource(emptyResource, 0,0);

        switch (renderDirection) {
            case HORIZONTALLEFTTORIGHT:
                drawTopLayerFromLeftToRight();
                break;
            case HORIZONTALRIGHTTOLEFT:
                drawTopLayerFromRightToLeft();
                break;
            case VERTICALBOTTOMTOTOP:
                drawTopLayerBottomToTop();
                break;
            case VERTICALTOPTOBOTTOM:
                drawTopLayerTopToBottom();
                break;
        }
    }

    @Override
    public void drawForeground (int mouseX, int mouseY) {
        //NOOP
    }

    @Override
    public boolean handleMouseClickedInside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        return false;
    }

    @Override
    public boolean handleMouseClickedOutside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        return false;
    }

    @Override
    public boolean requiresForcedMouseInput () {
        return false;
    }

    @Override
    public void handleKeyTyped (char key) {

    }

    private int getWidth () {
        if (emptyResource.getWidth() > fullResource.getWidth())
            return emptyResource.getWidth();

        return fullResource.getWidth();
    }

    private int getHeight () {
        if (emptyResource.getHeight() > fullResource.getHeight())
            return emptyResource.getHeight();

        return fullResource.getHeight();
    }

    public void drawTopLayerFromLeftToRight () {
        Plane renderBox = new Plane(getGlobalCoordinate(), (int) ( getWidth() * root.getRootManager().getProgressBarValue(this) ), getHeight());

        GuiHelper.enableScissor(renderBox);

        GuiHelper.drawResource(fullResource,0,0);

        GuiHelper.disableScissor();
    }

    public void drawTopLayerFromRightToLeft () {
        int fullWidth = (int) ( getWidth() * root.getRootManager().getProgressBarValue(this) );

        Plane renderBox = new Plane(getGlobalCoordinate().getTranslatedCoordinate(new Coordinate2D(getWidth() - fullWidth, 0)), fullWidth, getHeight());

        GuiHelper.enableScissor(renderBox);

        GuiHelper.drawResource(fullResource, 0,0);

        GuiHelper.disableScissor();
    }

    public void drawTopLayerBottomToTop () {
        Plane renderBox = new Plane(getGlobalCoordinate(), getWidth(), (int) ( getHeight() * root.getRootManager().getProgressBarValue(this) ));

        GuiHelper.enableScissor(renderBox);

        GuiHelper.drawResource(fullResource, 0,0);

        GuiHelper.disableScissor();
    }

    public void drawTopLayerTopToBottom () {
        int fullHeight = (int) ( getHeight() * root.getRootManager().getProgressBarValue(this) );

        Plane renderBox = new Plane(getGlobalCoordinate().getTranslatedCoordinate(new Coordinate2D(0, getHeight() - fullHeight)), getWidth(), fullHeight);

        GuiHelper.enableScissor(renderBox);

        GuiHelper.drawResource(fullResource, 0,0);

        GuiHelper.disableScissor();
    }
}
