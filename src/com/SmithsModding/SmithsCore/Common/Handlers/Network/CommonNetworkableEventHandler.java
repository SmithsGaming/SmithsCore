/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.Handlers.Network;

import com.SmithsModding.SmithsCore.Common.Event.Network.NetworkableEvent;
import com.SmithsModding.SmithsCore.Network.Event.EventNetworkManager;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Eventhandler used to catch Networkable Events on the Server Side.
 */
public class CommonNetworkableEventHandler {

    /**
     * Event handler for Events on one Side that gets fired by the CommonBus to send a Message to All clients or from one Client to the Server.
     *
     * @param pEvent The event that should be Synchronized
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEvent(NetworkableEvent pEvent) {
        EventNetworkManager.getInstance().sendToAll(pEvent.getCommunicationMessage());
    }
}
