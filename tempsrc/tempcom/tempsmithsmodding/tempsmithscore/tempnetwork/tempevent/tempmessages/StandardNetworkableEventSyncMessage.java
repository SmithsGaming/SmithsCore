/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.network.event.messages;

import com.smithsmodding.smithscore.*;
import com.smithsmodding.smithscore.common.events.network.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

import java.lang.reflect.*;

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
            EVENT = tEvent;

            tEvent.readFromMessageBuffer(buf);
        } catch (ClassNotFoundException e) {
            SmithsCore.getLogger().error("Failed to handle a events Sync for: " + tEventClassName + " The in the Message stored class for the event does not exist.", e);
        } catch (InvocationTargetException e) {
            SmithsCore.getLogger().error("Failed to handle a events Sync for: " + tEventClassName + " The creation of the events failed.", e);
        } catch (NoSuchMethodException e) {
            SmithsCore.getLogger().error("Failed to handle a events Sync for: " + tEventClassName + " The event has no empty constructor. ", e);
        } catch (InstantiationException e) {
            SmithsCore.getLogger().error("Failed to handle a events Sync for: " + tEventClassName + " Failed to retrieve a proper constructor to call.", e);
        } catch (IllegalAccessException e) {
            SmithsCore.getLogger().error("Failed to handle a events Sync for: " + tEventClassName + " The given constructor does not have public access rights.", e);
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
