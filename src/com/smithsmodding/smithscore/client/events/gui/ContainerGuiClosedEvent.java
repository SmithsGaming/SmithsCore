/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.client.events.gui;

import com.smithsmodding.smithscore.common.events.network.StandardNetworkableEvent;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * Eevnt fired on the ClientSide to signal that a User opened a UI.
 */
public class ContainerGuiClosedEvent extends StandardNetworkableEvent {

    EntityPlayer player;
    UUID playerID;
    String containerID;

    public ContainerGuiClosedEvent() {
    }

    public ContainerGuiClosedEvent(EntityPlayer pPlayer, @Nonnull ContainerSmithsCore containerSmithsCore) {
        this.player = pPlayer;
        this.playerID = player.getUniqueID();
        this.containerID = containerSmithsCore.getContainerID();
    }

    /**
     * Function to get the player Opening the UI
     *
     * @return The entity opening the UI.
     */
    public EntityPlayer getPlayer() {
        return player;
    }

    /**
     * Getter for the ID of the player opening the UI.
     * Should be used when this event is received on the Networkbus of the ClientSide.
     *
     * @return The UUID of the player opening the UI.
     */
    public UUID getPlayerID() {
        return playerID;
    }

    /**
     * The ID of the Container that the player Opened.
     * Used to keep track of which UI was opened when this event is received on the NetworkBus on the client side.
     *
     * @return THe containers ID.
     */
    public String getContainerID() {
        return containerID;
    }

    /**
     * Function used by the instance created on the receiving side to reset its state from the sending side stored
     * by it in the Buffer before it is being fired on the NetworkRelayBus.
     *
     * @param pMessageBuffer The ByteBuffer from the IMessage used to Synchronize the implementing events.
     */
    @Override
    public void readFromMessageBuffer(@Nonnull ByteBuf pMessageBuffer) {
        playerID = new UUID(pMessageBuffer.readLong(), pMessageBuffer.readLong());
        containerID = ByteBufUtils.readUTF8String(pMessageBuffer);
    }

    /**
     * Function used by the instance on the sending side to write its state top the Buffer before it is send to the
     * retrieving side.
     *
     * @param pMessageBuffer The buffer from the IMessage
     */
    @Override
    public void writeToMessageBuffer(@Nonnull ByteBuf pMessageBuffer) {
        pMessageBuffer.writeLong(playerID.getMostSignificantBits());
        pMessageBuffer.writeLong(playerID.getLeastSignificantBits());
        ByteBufUtils.writeUTF8String(pMessageBuffer, containerID);
    }

    /**
     * This function is called on the reinstated event on the receiving side.
     * This allows you to act upon the arrival of the IMessage, as long as you have the IMessageHandler call this
     * function. A good idea is the Post this event to the NetworkRelayBus from here.
     *
     * In this case, some additional values are reconstructed based of the Side this Message is received on.
     *
     * @param pMessage The instance of IMessage received by the EventNetworkManager that describes this events.
     * @param pContext The messages Context.
     */
    @Override
    public void handleCommunicationMessage (IMessage pMessage, @Nonnull MessageContext pContext) {
        //Retrieve the player from the Context.
        if (pContext.side == Side.SERVER) {
            player = pContext.getServerHandler().playerEntity;

            super.handleCommunicationMessage(pMessage, pContext);
        } else {
            super.handleCommunicationMessage(pMessage, pContext);
        }
    }
}
