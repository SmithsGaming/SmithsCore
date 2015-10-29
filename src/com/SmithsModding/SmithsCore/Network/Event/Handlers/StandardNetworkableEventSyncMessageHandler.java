/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Network.Event.Handlers;

import com.SmithsModding.SmithsCore.Network.Event.Messages.StandardNetworkableEventSyncMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class StandardNetworkableEventSyncMessageHandler implements IMessageHandler<StandardNetworkableEventSyncMessage, IMessage> {

    /**
     * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply
     * is needed.
     *
     * @param pMessage The message
     * @param pContext
     * @return an optional return message
     */
    @Override
    public IMessage onMessage(StandardNetworkableEventSyncMessage pMessage, MessageContext pContext) {
        if (pMessage.EVENT == null)
            return null;

        return pMessage.EVENT.handleCommunicationMessage(pMessage);
    }
}
