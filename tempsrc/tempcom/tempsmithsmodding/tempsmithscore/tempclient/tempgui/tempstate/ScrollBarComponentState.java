package com.smithsmodding.smithscore.client.gui.state;

import com.smithsmodding.smithscore.*;
import com.smithsmodding.smithscore.client.events.gui.*;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.registry.*;
import com.smithsmodding.smithscore.util.common.positioning.*;

/**
 * Created by Marc on 10.02.2016.
 */
public class ScrollBarComponentState extends CoreComponentState
{

    int moveDelta = 4;
    int totalStepCount;

    int minimum = 0;
    int maximum = 100;

    int current = 0;

    public ScrollBarComponentState (int moveDelta, int minimum, int maximum) {
        super();

        this.moveDelta = moveDelta;
        this.minimum = minimum;
        this.maximum = maximum;

        current = minimum;

        recalculateMoveDelta(25);
    }

    public int getMinimum () {
        return minimum;
    }

    public void setMinimum (int minimum) {
        this.minimum = minimum;
        recalculateMoveDelta(totalStepCount);
    }

    public int getMaximum () {
        return maximum;
    }

    public void setMaximum (int maximum) {
        this.maximum = maximum;
        recalculateMoveDelta(totalStepCount);
    }

    public int getCurrent () {
        return current;
    }

    public void setCurrent (int current) {
        this.current = current;

        SmithsCore.getRegistry().getClientBus().post(new ScrollBarValueChangedEvent(getComponent().getID(), current, maximum, minimum));

        updateDragButtonPosition();
    }

    public void updateCurrent(int delta)
    {
        setCurrent(current - delta);
    }

    public void recalculateMoveDelta(int totalStepCount)
    {
        moveDelta = (maximum - minimum) / totalStepCount;

        this.totalStepCount = totalStepCount;

        updateDragButtonPosition();
    }

    public void onUpClick()
    {
        if (current == minimum)
            return;

        if (current - moveDelta <= minimum)
        {
            updateCurrent(-1 * (current - minimum));
            updateDragButtonPosition();
            return;
        }

        updateCurrent(-moveDelta);
        updateDragButtonPosition();
    }

    public void onDownClick()
    {
        if (current == maximum)
            return;

        if (current + moveDelta >= maximum)
        {
            updateCurrent(maximum - current);
            updateDragButtonPosition();
            return;
        }

        updateCurrent(moveDelta);
        updateDragButtonPosition();
    }

    public void onDragClick()
    {
        ComponentScrollBar host = (ComponentScrollBar) this.getComponent();
        ComponentButton dragButton = (ComponentButton) host.getAllComponents().get(host.getID() + ".Buttons.ScrollDrag");

        int totalScrollableHeight = host.getSize().getHeigth() - 30;

        if (totalScrollableHeight <= 0)
            throw new IllegalStateException("The ScrollDragButton should not be visible for such a small ScrollBar.");

        int pixelPerMove = totalScrollableHeight / totalStepCount;

        if (pixelPerMove <= 0)
            throw new IllegalStateException("There are to many total steps in the ScrollBar to be able to move the drag button properly.");

        Coordinate2D mousePosition = (( ClientRegistry) SmithsCore.getRegistry()).getMouseManager().getLocation();
        Plane dragButtonPosition = dragButton.getAreaOccupiedByComponent();

        int delta = 0;

        if (mousePosition.getYComponent() > dragButtonPosition.LowerRightCoord().getYComponent())
        {
            delta = (mousePosition.getYComponent() - dragButtonPosition.LowerRightCoord().getYComponent()) / pixelPerMove;
        }
        else if (mousePosition.getYComponent() < dragButtonPosition.TopLeftCoord().getYComponent())
        {
            delta = (mousePosition.getYComponent() - dragButtonPosition.TopLeftCoord().getYComponent()) / pixelPerMove;
        }

        if (delta == 0)
            return;

        if (delta > 0 && current + delta >= maximum)
        {
            delta = maximum - current;
        }

        if (delta < 0 && current + delta <= minimum)
        {
            delta = -1 * (current - minimum);
        }

        updateCurrent(delta);
    }

    public void updateDragButtonPosition()
    {
        ComponentScrollBar host = (ComponentScrollBar) this.getComponent();
        ComponentButton dragButton = (ComponentButton) host.getAllComponents().get(host.getID() + ".Buttons.ScrollDrag");

        int yOffset = current * moveDelta;

        dragButton.getLocalCoordinate().setYComponent(yOffset);
    }
}
