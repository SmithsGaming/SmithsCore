/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.network.event.messages;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.events.network.StandardNetworkableEvent;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;

public class StandardNetworkableEventSyncMessage implements IMessage {

    //Contains the event that this message represents;
    public StandardNetworkableEvent EVENT;

    public StandardNetworkableEventSyncMessage() {
    }

    public StandardNetworkableEventSyncMessage(@Nonnull StandardNetworkableEvent pEvent) {
        EVENT = pEvent;
    }


    /**
     * Convert from the supplied buffer into your specific message type
     *
     * @param buf The buffer to write to.
     */
    @Override
    public void fromBytes(@Nonnull ByteBuf buf) {
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
     * @param buf The buffer to read from.
     */
    @Override
    public void toBytes(@Nonnull ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, EVENT.getClass().getName());

        EVENT.writeToMessageBuffer(buf);
    }
}
