/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.events.network;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.events.SmithsCoreEvent;
import com.smithsmodding.smithscore.network.event.EventNetworkManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Classes extending this event will automatically get Synchronized over to the other side of the Game.
 * Meaning that events that happen on the client side will get synchronized to the server side.
 * And vice versa.
 *
 * A good example would be an event that triggers when the user enters something into a textbox on the client side.
 *
 * The implementing event gets catched by a events handler on the lowest priority.
 */
public class NetworkableEvent extends SmithsCoreEvent {

    /**
     * Function used by the EventHandler to retrieve an IMessage that describes this events.
     * This IMessage is then send to the server or the client depending on the running side.
     *
     * A warning: You will have to register the IMessage and its handler to the EventNetworkManager.getInstance()
     * yourself
     *
     * @param side The side this event is synced TO!
     *
     * @return An Instance of an IMessage class that describes this events.
     */
    @Nullable
    public IMessage getCommunicationMessage(Side side) {
        return null;
    }

    /**
     * This function is called on the reinstated event on the receiving side.
     * This allows you to act upon the arrival of the IMessage, as long as you have the IMessageHandler call this
     * function. A good idea is the Post this event to the NetworkRelayBus from here.
     *
     * @param pMessage The instance of IMessage received by the EventNetworkManager that describes this events.
     * @param pContext The messages context.
     */
    public void handleCommunicationMessage(@Nonnull IMessage pMessage, @Nonnull MessageContext pContext) {
    }

    /**
     * Method called by the EventHandler to indicate this event that it should sent it self from teh server to the
     * client side.
     */
    public void handleServerToClientSide () {
        if (this.getCommunicationMessage(Side.CLIENT) == null)
            return;
        EventNetworkManager.getInstance().sendToAll(this.getCommunicationMessage(Side.CLIENT));
    }

    public void handleServerToClient(@Nonnull EntityPlayerMP playerMP) {
        if (this.getCommunicationMessage(Side.CLIENT) == null)
            return;
        EventNetworkManager.getInstance().sendTo(this.getCommunicationMessage(Side.CLIENT), playerMP);
    }

    /**
     * Method called by the EventHandler to indicate this event that it should sent it self from teh client to the
     * server side.
     */
    public void handleClientToServerSide () {
        if (this.getCommunicationMessage(Side.SERVER) == null)
            return;

        EventNetworkManager.getInstance().sendToServer(this.getCommunicationMessage(Side.SERVER));
    }

    /**
     * Convenient function to post this event on the network event bus within smithscore
     */
    public void PostNetwork() {
        SmithsCore.getRegistry().getNetworkBus().post(this);
    }
}
