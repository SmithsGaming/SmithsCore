package com.smithsmodding.smithscore.client.gui.state;

import com.smithsmodding.smithscore.*;
import com.smithsmodding.smithscore.client.events.gui.*;
import com.smithsmodding.smithscore.client.gui.components.core.*;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.registry.*;
import com.smithsmodding.smithscore.util.common.positioning.*;

import javax.annotation.Nonnull;
import java.lang.*;

/**
 * Created by Marc on 10.02.2016.
 */
public class ScrollBarComponentState extends CoreComponentState
{

    float moveDelta = 4;
    int totalStepCount;

    int minimum = 0;
    int maximum = 100;

    float current = 0;
    float target = current;

    public ScrollBarComponentState (int moveDelta, int minimum, int maximum) {
        super();

        this.moveDelta = moveDelta;
        this.minimum = minimum;
        this.maximum = maximum;

        this.current = maximum;
        this.target = maximum;

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

    public float getCurrent () {
        return current;
    }

    public void setCurrent (float current) {

        if (this.current == this.target)
        {
            this.target = current;
        }

        this.current = current;

        SmithsCore.getRegistry().getClientBus().post(new ScrollBarValueChangedEvent(getComponent().getID(), current, maximum, minimum));

        updateDragButtonPosition();
    }

    public void updateCurrent(float delta)
    {
        setCurrent(current + delta);
    }

    public void setTarget (float target) {
        this.target = target;
    }

    public void recalculateMoveDelta(int totalStepCount)
    {
        moveDelta = (maximum - minimum) / (float) totalStepCount;

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

        updateCurrent(-1 * moveDelta);
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

    @Override
    public void setComponent (@Nonnull IGUIComponent component) {
        super.setComponent(component);
    }

    public void onDragClick()
    {
        ComponentScrollBar host = (ComponentScrollBar) this.getComponent();
        ComponentButton dragButton = (ComponentButton) host.getAllComponents().get(host.getID() + ".Buttons.ScrollDrag");

        int totalScrollableHeight = host.getSize().getHeigth() - 20;

        if (totalScrollableHeight <= 0)
            throw new IllegalStateException("The ScrollDragButton should not be visible for such a small ScrollBar.");

        float pixelPerMove = totalScrollableHeight / (float) totalStepCount;

        if (pixelPerMove <= 0)
            throw new IllegalStateException("There are to many total steps in the ScrollBar to be able to move the drag button properly.");

        Coordinate2D mousePosition = (( ClientRegistry) SmithsCore.getRegistry()).getMouseManager().getLocation();
        Plane dragButtonPosition = dragButton.getAreaOccupiedByComponent();

        float delta = 0;

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

    public void onAnimationClick(float partialTickTime)
    {
        if (target == current)
            return;

        ComponentScrollBar host = (ComponentScrollBar) this.getComponent();

        int totalScrollableHeight = host.getSize().getHeigth() - 20;

        if (totalScrollableHeight <= 0)
            throw new IllegalStateException("The ScrollDragButton should not be visible for such a small ScrollBar.");

        float pixelPerMove = totalScrollableHeight / (float) totalStepCount;

        if (pixelPerMove <= 0)
            throw new IllegalStateException("There are to many total steps in the ScrollBar to be able to move the drag button properly.");

        if (target < current)
        {
            float delta =  - 1 * (moveDelta / pixelPerMove);

            if (current + delta < target)
                delta = (current - target) * -1;

            updateCurrent(delta);
        }
        else
        {
            float delta = (moveDelta / pixelPerMove);

            if (current + delta > target)
                delta = (target - current);

            updateCurrent(delta);
        }
    }

    public void updateDragButtonPosition()
    {
        ComponentScrollBar host = (ComponentScrollBar) this.getComponent();

        if (host == null)
            return;

        ComponentButton dragButton = (ComponentButton) host.getAllComponents().get(host.getID() + ".Buttons.ScrollDrag");
        if (dragButton == null)
            return;

        int yOffset = 10 + (int) (current / moveDelta);

        dragButton.getLocalCoordinate().setYComponent(yOffset);
    }
}
