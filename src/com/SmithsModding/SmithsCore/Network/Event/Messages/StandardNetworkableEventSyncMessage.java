/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Network.Event.Messages;

import com.SmithsModding.SmithsCore.Common.Event.StandardNetworkableEvent;
import com.SmithsModding.SmithsCore.SmithsCore;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.InvocationTargetException;

public class StandardNetworkableEventSyncMessage implements IMessage {

    //Contains the event that this message represents;
    public StandardNetworkableEvent EVENT;

    public StandardNetworkableEventSyncMessage() {
    }

    public StandardNetworkableEventSyncMessage(StandardNetworkableEvent pEvent) {
        EVENT = pEvent;
    }


    /**
     * Convert from the supplied buffer into your specific message type
     *
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf) {
        String tEventClassName = ByteBufUtils.readUTF8String(buf);

        try {
            Class tEventClass = Class.forName(tEventClassName);

            StandardNetworkableEvent tEvent = (StandardNetworkableEvent) tEventClass.getConstructor().newInstance();

            tEvent.readFromMessageBuffer(buf);
        } catch (ClassNotFoundException e) {
            SmithsCore.getLogger().error("Failed to handle a Event Sync for: " + tEventClassName + " The in the Message stored class for the event does not exist.", e);
        } catch (InvocationTargetException e) {
            SmithsCore.getLogger().error("Failed to handle a Event Sync for: " + tEventClassName + " The creation of the Event failed.", e);
        } catch (NoSuchMethodException e) {
            SmithsCore.getLogger().error("Failed to handle a Event Sync for: " + tEventClassName + " The event has no empty constructor. ", e);
        } catch (InstantiationException e) {
            SmithsCore.getLogger().error("Failed to handle a Event Sync for: " + tEventClassName + " Failed to retrieve a proper constructor to call.", e);
        } catch (IllegalAccessException e) {
            SmithsCore.getLogger().error("Failed to handle a Event Sync for: " + tEventClassName + " The given constructor does not have public access rights.", e);
        }
    }

    /**
     * Deconstruct your message into the supplied byte buffer
     *
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, EVENT.getClass().getName());

        EVENT.writeToMessageBuffer(buf);
    }
}
