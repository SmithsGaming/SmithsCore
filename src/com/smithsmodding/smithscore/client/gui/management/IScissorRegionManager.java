package com.smithsmodding.smithscore.client.gui.management;

import com.smithsmodding.smithscore.util.common.positioning.*;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by Marc on 12.02.2016.
 */
public interface IScissorRegionManager
{

    /**
     * Method to set a new scissor region. The standard manager will do inheritance Checks to make sure that the new region
     * intersects somewhere with the current region before applying.
     *
     * @param scissorRegion The new scissor region
     *
     * @return True when either completely or partially applied, false when not.
     */
    boolean setScissorRegionTo(@Nonnull Plane scissorRegion);

    /**
     * Method called to indicate that the currently used scissor region should be reset.
     */
    void popCurrentScissorRegion();
}
