/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.Events;

import com.smithsmodding.smithscore.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Root class for all smithscore events.
 */
public class SmithsCoreEvent extends Event {
    /**
     * Convenient function to post this event on the common event bus within smithscore
     */
    public void PostCommon() {
        SmithsCore.getRegistry().getCommonBus().post(this);
    }

    /**
     * Convenient function to post this event on the client event bus within smithscore
     */
    public void PostClient() {
        SmithsCore.getRegistry().getClientBus().post(this);
    }

    /**
     * Function used to get the sided NetworkingHandler.
     *
     * @param pContext The Context of the Message for which the handlers has to be retrieved.
     * @return The ClientNetHandler on the ClientSide and the ServerNetHandler on the server side.
     */
    public INetHandler getSidedPlayerHandlerFromContext(MessageContext pContext) {
        if (pContext.side == Side.SERVER) {
            return pContext.getServerHandler();
        }

        return pContext.getClientHandler();
    }
}
