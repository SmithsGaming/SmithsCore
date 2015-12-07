/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.Event.Network;

import com.SmithsModding.SmithsCore.Common.Event.SmithsCoreEvent;
import com.SmithsModding.SmithsCore.SmithsCore;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Classes extending this event will automatically get Synchronized over to the other side of the Game.
 * Meaning that events that happen on the client side will get synchronized to the server side.
 * And vice versa.
 * <p/>
 * A good example would be an event that triggers when the user enters something into a textbox on the client side.
 * <p/>
 * The implementing event gets catched by a Event handler on the lowest priority.
 */
public class NetworkableEvent extends SmithsCoreEvent {

    /**
     * Function used by the EventHandler to retrieve an IMessage that describes this Event.
     * This IMessage is then send to the server or the client depending on the running side.
     * <p/>
     * A warning: You will have to register the IMessage and its handler to the EventNetworkManager.getInstance()
     * yourself
     *
     * @return An Instance of an IMessage class that describes this Event.
     */
    public IMessage getCommunicationMessage () {
        return null;
    }

    /**
     * This function is called on the reinstated event on the receiving side.
     * This allows you to act upon the arrival of the IMessage, as long as you have the IMessageHandler call this
     * function. A good idea is the Post this event to the NetworkRelayBus from here.
     *
     * @param pMessage The instance of IMessage received by the EventNetworkManager that describes this Event.
     * @param pContext The Messages context.
     * @return A IMessage that describes the answer if need be, else null.
     */
    public IMessage handleCommunicationMessage (IMessage pMessage, MessageContext pContext) {
        return null;
    }

    /**
     * Convenient function to post this event on the Network event bus within SmithsCore
     */
    public void PostNetwork() {
        SmithsCore.getRegistry().getNetworkBus().post(this);
    }
}
