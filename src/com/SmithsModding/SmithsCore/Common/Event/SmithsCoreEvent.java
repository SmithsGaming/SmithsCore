/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.Event;

import com.SmithsModding.SmithsCore.SmithsCore;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.network.INetHandler;

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

    /**
     * Function used to get the sided NetworkingHandler.
     *
     * @param pContext The Context of the Message for which the Handler has to be retrieved.
     * @return The ClientNetHandler on the ClientSide and the ServerNetHandler on the server side.
     */
    public INetHandler getSidedPlayerHandlerFromContext(MessageContext pContext) {
        if (pContext.side == Side.SERVER) {
            return pContext.getServerHandler();
        }

        return pContext.getClientHandler();
    }
}
