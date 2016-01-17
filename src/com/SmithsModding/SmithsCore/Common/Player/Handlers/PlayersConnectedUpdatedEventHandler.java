/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.Player.Handlers;

import com.smithsmodding.smithscore.common.Player.Event.PlayersConnectedUpdatedEvent;
import com.smithsmodding.smithscore.common.Player.Management.PlayerManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayersConnectedUpdatedEventHandler {

    /**
     * Method for handling the network event when it arrives on the client side.
     *
     * @param event
     */
    @SubscribeEvent
    public void onPlayersConnectedUpdated(PlayersConnectedUpdatedEvent event) {
        PlayerManager.getInstance().setCommonSidedJoinedMap(event.getCommonSidedJoinedMap());
    }
}
