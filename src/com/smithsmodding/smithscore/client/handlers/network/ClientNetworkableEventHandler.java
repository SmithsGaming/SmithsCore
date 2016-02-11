/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.client.handlers.network;

import com.smithsmodding.smithscore.common.events.network.*;
import com.smithsmodding.smithscore.common.handlers.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;

public class ClientNetworkableEventHandler extends CommonNetworkableEventHandler {

    @Override
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEvent(NetworkableEvent pEvent) {
        if (pEvent.getCommunicationMessage(Side.SERVER) == null)
            return;

        if (pEvent.isCanceled())
            return;

        pEvent.handleClientToServerSide();
    }
}
