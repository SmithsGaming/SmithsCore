/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.Event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public abstract class NetworkableEvent extends Event {

    public abstract IMessage getCommunicationMessage();

    public abstract IMessage handleCommunicationMessage(IMessage pMessage);
}
