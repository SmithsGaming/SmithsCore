package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.components.core.*;
import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.util.client.*;
import com.smithsmodding.smithscore.util.client.gui.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.util.*;

import java.util.*;

/**
 * Created by Marc on 25.12.2015.
 */
public class ComponentProgressBar extends CoreComponent {

    private ComponentDirection renderDirection;

    private CustomResource emptyResource;
    private CustomResource fullResource;

    public ComponentProgressBar (String uniqueID, IGUIComponentState state, IGUIBasedComponentHost root, Coordinate2D localCoordinate, ComponentDirection componentDirection, CustomResource emptyResource, CustomResource fullResource) {
        super(uniqueID, root, state, localCoordinate, 0,0);

        this.renderDirection = componentDirection;
        this.emptyResource = emptyResource;
        this.fullResource = fullResource;
    }

    @Override
    public Plane getAreaOccupiedByComponent() {
        return new Plane(getGlobalCoordinate(), getWidth(), getHeight());
    }

    @Override
    public Plane getSize() {
        return new Plane(0,0, getWidth(), getHeight());
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
    public ArrayList<String> getToolTipContent () {
        ArrayList<String> tips = new ArrayList<String>();
        tips.add(StatCollector.translateToLocal(TranslationKeys.GUI.PROGRESS) + ": " + Math.round(getComponentHost().getRootManager().getProgressBarValue(this) * 100) + "%");

        return tips;
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
        Plane renderBox = new Plane(getGlobalCoordinate(), (int) ( getWidth() * getComponentHost().getRootManager().getProgressBarValue(this) ), getHeight());

        if (renderBox.getWidth() == 0)
            return;

        GuiHelper.enableScissor(renderBox);

        GuiHelper.drawResource(fullResource,0,0);

        GuiHelper.disableScissor();
    }

    public void drawTopLayerFromRightToLeft () {
        int fullWidth = (int) ( getWidth() * getComponentHost().getRootManager().getProgressBarValue(this) );

        if (fullWidth == 0)
            return;

        Plane renderBox = new Plane(getGlobalCoordinate().getTranslatedCoordinate(new Coordinate2D(getWidth() - fullWidth, 0)), fullWidth, getHeight());

        GuiHelper.enableScissor(renderBox);

        GuiHelper.drawResource(fullResource, 0,0);

        GuiHelper.disableScissor();
    }

    public void drawTopLayerBottomToTop () {
        int fullHeight = (int) ( getHeight() * getComponentHost().getRootManager().getProgressBarValue(this) );

        if (fullHeight == 0)
            return;

        Plane renderBox = new Plane(getGlobalCoordinate().getTranslatedCoordinate(new Coordinate2D(0, getHeight() - fullHeight)), getWidth(), fullHeight);



        GuiHelper.enableScissor(renderBox);

        GuiHelper.drawResource(fullResource, 0,0);

        GuiHelper.disableScissor();
    }

    public void drawTopLayerTopToBottom () {
        Plane renderBox = new Plane(getGlobalCoordinate(), getWidth(), (int) ( getHeight() * getComponentHost().getRootManager().getProgressBarValue(this) ));

        if (renderBox.getHeigth() == 0)
            return;

        GuiHelper.enableScissor(renderBox);

        GuiHelper.drawResource(fullResource, 0,0);

        GuiHelper.disableScissor();
    }
}
