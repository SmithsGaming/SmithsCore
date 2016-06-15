/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.network.event.handlers;

import com.smithsmodding.smithscore.network.event.messages.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.*;

public class StandardNetworkableEventSyncMessageHandler implements IMessageHandler<StandardNetworkableEventSyncMessage, IMessage> {

    /**
     * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply
     * is needed.
     *
     * @param pMessage The message
     * @param pContext The messages context.
     * @return an optional return message
     */
    @Override
    public IMessage onMessage (final StandardNetworkableEventSyncMessage pMessage, final MessageContext pContext) {
        if (pMessage.EVENT == null)
            return null;

        IThreadListener runnable;

        if (pContext.side == Side.CLIENT) {
            runnable = Minecraft.getMinecraft();
        } else {
            runnable = (IThreadListener) pContext.getServerHandler().playerEntity.worldObj;
        }

        runnable.addScheduledTask(new Runnable() {
            @Override
            public void run () {
                pMessage.EVENT.handleCommunicationMessage(pMessage, pContext);
            }
        });

        return null;
    }
}
