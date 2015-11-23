/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Client.GUI.Events;

import com.SmithsModding.SmithsCore.Common.Event.Network.StandardNetworkableEvent;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class ContainerGuiOpenedEvent extends StandardNetworkableEvent {

    EntityPlayer iPlayer;
    UUID iPlayerID;

    public ContainerGuiOpenedEvent() {
    }

    public ContainerGuiOpenedEvent(EntityPlayer pPlayer) {
        this.iPlayer = pPlayer;
        this.iPlayerID = iPlayer.getUniqueID();
    }

    /**
     * Function to get the Player Opening the UI
     *
     * @return The entity opening the UI.
     */
    public EntityPlayer getPlayer() {
        return iPlayer;
    }

    /**
     * Function used by the instance created on the receiving side to reset its state from the sending side stored
     * by it in the Buffer before it is being fired on the NetworkRelayBus.
     *
     * @param pMessageBuffer The ByteBuffer from the IMessage used to Synchronize the implementing Event.
     */
    @Override
    public void readFromMessageBuffer(ByteBuf pMessageBuffer) {
        iPlayerID = new UUID(pMessageBuffer.readLong(), pMessageBuffer.readLong());
    }

    /**
     * Function used by the instance on the sending side to write its state top the Buffer before it is send to the
     * retrieving side.
     *
     * @param pMessageBuffer The buffer from the IMessage
     */
    @Override
    public void writeToMessageBuffer(ByteBuf pMessageBuffer) {
        pMessageBuffer.writeLong(iPlayerID.getMostSignificantBits());
        pMessageBuffer.writeLong(iPlayerID.getLeastSignificantBits());
    }

    /**
     * This function is called on the reinstated event on the receiving side.
     * This allows you to act upon the arrival of the IMessage, as long as you have the IMessageHandler call this
     * function. A good idea is the Post this event to the NetworkRelayBus from here.
     * <p/>
     * In this case, some additional values are reconstructed based of the Side this Message is received on.
     *
     * @param pMessage The instance of IMessage received by the EventNetworkManager that describes this Event.
     * @param pContext The Messages Context.
     * @return A IMessage that describes the answer if need be, else null.
     */
    @Override
    public IMessage handleCommunicationMessage(IMessage pMessage, MessageContext pContext) {
        //Retrieve the Player from the Context.
        if (pContext.side == Side.SERVER) {
            iPlayer = pContext.getServerHandler().playerEntity;

            return super.handleCommunicationMessage(pMessage, pContext);
        } else {
            iPlayer = FMLClientHandler.instance().getClientPlayerEntity().getEntityWorld().func_152378_a(iPlayerID);
            return super.handleCommunicationMessage(pMessage, pContext);
        }
    }
}
