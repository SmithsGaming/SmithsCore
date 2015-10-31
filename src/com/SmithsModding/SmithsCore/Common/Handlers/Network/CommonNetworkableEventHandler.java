/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.Handlers.Network;

import com.SmithsModding.SmithsCore.Common.Event.Network.NetworkableEvent;
import com.SmithsModding.SmithsCore.Network.Event.EventNetworkManager;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Eventhandler used to catch Networkable Events on the Server Side.
 */
public class CommonNetworkableEventHandler {

    /**
     * Event handler for Events on the ServerSide that gets fired by the CommonBus to send a Message to All clients.
     *
     * @param pEvent The event that should be Synchronized
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEvent(NetworkableEvent pEvent) {
        EventNetworkManager.getInstance().sendToAll(pEvent.getCommunicationMessage());
    }
}
