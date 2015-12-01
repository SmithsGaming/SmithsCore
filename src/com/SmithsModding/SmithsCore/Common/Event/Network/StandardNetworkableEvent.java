/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.Event.Network;

import com.SmithsModding.SmithsCore.Network.Event.Messages.StandardNetworkableEventSyncMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * Standard implementation of the Networkable Event.
 * It should be used for events that only handle a limited amount of data.
 * <p/>
 * An implementing event has to have an Empty constructor as the IMessage Implementation uses Reflection to create a
 * new Instance of the implementing event and fires it on the NetworkRelayBus.
 */
public abstract class StandardNetworkableEvent extends NetworkableEvent {

    /**
     * Standard empty Constructor.
     */
    public StandardNetworkableEvent() {
    }

    /**
     * Function used by the instance created on the receiving side to reset its state from the sending side stored
     * by it in the Buffer before it is being fired on the NetworkRelayBus.
     *
     * @param pMessageBuffer The ByteBuffer from the IMessage used to Synchronize the implementing Event.
     */
    public abstract void readFromMessageBuffer(ByteBuf pMessageBuffer);

    /**
     * Function used by the instance on the sending side to write its state top the Buffer before it is send to the
     * retrieving side.
     *
     * @param pMessageBuffer The buffer from the IMessage
     */
    public abstract void writeToMessageBuffer(ByteBuf pMessageBuffer);

    /**
     * Function used by the EventHandler to retrieve an IMessage that describes this Event.
     * This IMessage is then send to the server or the client depending on the running side.
     * <p/>
     * A warning: You will have to register the IMessage and its handler to the EventNetworkManager.getInstance()
     * yourself
     *
     * @return An Instance of an IMessage class that describes this Event.
     */
    @Override
    public IMessage getCommunicationMessage() {
        return new StandardNetworkableEventSyncMessage(this);
    }

    /**
     * This function is called on the reinstated event on the receiving side.
     * This allows you to act upon the arrival of the IMessage, as long as you have the IMessageHandler call this
     * function. A good idea is the Post this event to the NetworkRelayBus from here.
     *
     * @param pMessage The instance of IMessage received by the EventNetworkManager that describes this Event.
     * @param pContext The Messages Context
     * @return A IMessage that describes the answer if need be, else null.
     */
    @Override
    public IMessage handleCommunicationMessage(IMessage pMessage, MessageContext pContext) {
        PostNetwork();

        return null;
    }
}
