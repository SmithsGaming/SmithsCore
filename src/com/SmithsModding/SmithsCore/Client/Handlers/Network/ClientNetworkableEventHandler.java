/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Client.Handlers.Network;

import com.SmithsModding.SmithsCore.Common.Event.Network.NetworkableEvent;
import com.SmithsModding.SmithsCore.Common.Handlers.Network.CommonNetworkableEventHandler;
import com.SmithsModding.SmithsCore.Network.Event.EventNetworkManager;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ClientNetworkableEventHandler extends CommonNetworkableEventHandler {

    @Override
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEvent(NetworkableEvent pEvent) {
        EventNetworkManager.getInstance().sendToServer(pEvent.getCommunicationMessage());
    }
}
