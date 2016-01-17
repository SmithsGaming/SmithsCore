package com.smithsmodding.smithscore.client.GUI.Components.Implementations;

import com.smithsmodding.smithscore.client.GUI.Components.Core.IGUIComponent;
import com.smithsmodding.smithscore.client.GUI.Host.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.GUI.State.IGUIComponentState;
import com.smithsmodding.smithscore.util.Common.Postioning.Coordinate2D;
import com.smithsmodding.smithscore.util.Common.Postioning.Plane;

import java.util.ArrayList;

/**
 * Created by marcf on 1/16/2016.
 */
public abstract class CoreComponent implements IGUIComponent {
    protected String uniqueID;
    protected IGUIComponentState state;
    protected IGUIBasedComponentHost parent;
    protected Coordinate2D rootAnchorPixel;
    protected int width;
    protected int height;

    public CoreComponent(String uniqueID, IGUIBasedComponentHost parent, IGUIComponentState state, Coordinate2D rootAnchorPixel, int height, int width) {
        this.rootAnchorPixel = rootAnchorPixel;
        this.height = height;
        this.uniqueID = uniqueID;
        this.state = state;
        this.width = width;
        this.parent = parent;

        this.state.setComponent(this);
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
        return parent.getComponentHost();
    }

    @Override
    public Coordinate2D getGlobalCoordinate () {
        return parent.getGlobalCoordinate().getTranslatedCoordinate(getLocalCoordinate());
    }

    @Override
    public Coordinate2D getLocalCoordinate () {
        return rootAnchorPixel;
    }

    @Override
    public Plane getAreaOccupiedByComponent () {
        return new Plane(getGlobalCoordinate(), width, height);
    }

    @Override
    public Plane getSize () {
        return new Plane(0, 0, width, height);
    }

    @Override
    public abstract void update(int mouseX, int mouseY, float partialTickTime);

    @Override
    public abstract void drawBackground(int mouseX, int mouseY);

    @Override
    public abstract void drawForeground(int mouseX, int mouseY);

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
        return;
    }

    @Override
    public ArrayList<String> getToolTipContent () {
        return new ArrayList<String>();
    }
}
