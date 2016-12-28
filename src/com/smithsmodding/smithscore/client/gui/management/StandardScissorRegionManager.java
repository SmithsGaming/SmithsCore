package com.smithsmodding.smithscore.client.gui.management;

import com.smithsmodding.smithscore.*;
import com.smithsmodding.smithscore.util.client.gui.*;
import com.smithsmodding.smithscore.util.common.positioning.*;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by Marc on 12.02.2016.
 */
public class StandardScissorRegionManager implements IScissorRegionManager {

    @Nonnull
    private ArrayList<Plane> scissorRegionStack = new ArrayList<Plane>();

    @Override
    public boolean setScissorRegionTo (@Nonnull Plane scissorRegion) {
        if (scissorRegionStack.size() == 0)
        {
            GuiHelper.enableScissor(scissorRegion);
            scissorRegionStack.add(0, scissorRegion);

            return true;
        }

        Plane intersectionRegion = scissorRegionStack.get(0).getInstersection(scissorRegion);

        if (intersectionRegion == null || intersectionRegion.Contents() == 0)
            return false;

        GuiHelper.disableScissor();

        GuiHelper.enableScissor(intersectionRegion);
        scissorRegionStack.add(0, scissorRegion);

        return true;
    }

    @Override
    public void popCurrentScissorRegion () {
        if (scissorRegionStack.size() == 0)
            return;

        scissorRegionStack.remove(0);
        GuiHelper.disableScissor();

        if (scissorRegionStack.size() == 0) {
            return;
        }

        GuiHelper.enableScissor(scissorRegionStack.get(0));
    }
}
