/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.Event;

import com.SmithsModding.SmithsCore.Network.Event.Messages.StandardNetworkableEventSyncMessage;
import com.SmithsModding.SmithsCore.SmithsCore;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public abstract class StandardNetworkableEvent extends NetworkableEvent {

    @Override
    public IMessage handleCommunicationMessage(IMessage pMessage) {
        SmithsCore.getRegistry().getNetworkBus().post(((StandardNetworkableEventSyncMessage) pMessage).EVENT);

        return null;
    }

    @Override
    public IMessage getCommunicationMessage() {
        return new StandardNetworkableEventSyncMessage(this);
    }

    public abstract void readFromMessageBuffer(ByteBuf pMessageBuffer);

    public abstract void writeToMessageBuffer(ByteBuf pMessageBuffer);
}
