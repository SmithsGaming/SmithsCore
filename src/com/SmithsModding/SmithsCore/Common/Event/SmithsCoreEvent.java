/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.Event;

import com.SmithsModding.SmithsCore.SmithsCore;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * Root class for all SmithsCore Events.
 */
public class SmithsCoreEvent extends Event {
    /**
     * Convenient function to post this event on the Common event bus within SmithsCore
     */
    public void PostCommon() {
        SmithsCore.getRegistry().getCommonBus().post(this);
    }

    /**
     * Convenient function to post this event on the Client event bus within SmithsCore
     */
    public void PostClient() {
        SmithsCore.getRegistry().getClientBus().post(this);
    }


}
