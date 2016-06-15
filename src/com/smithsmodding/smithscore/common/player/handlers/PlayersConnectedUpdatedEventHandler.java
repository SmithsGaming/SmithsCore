/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.player.handlers;

import com.smithsmodding.smithscore.common.player.event.*;
import com.smithsmodding.smithscore.common.player.management.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class PlayersConnectedUpdatedEventHandler {

    /**
     * Method for handling the network event when it arrives on the client side.
     *
     * @param event The Event indicating that the ConnectedPlayers updated.
     */
    @SubscribeEvent
    public void onPlayersConnectedUpdated(PlayersConnectedUpdatedEvent event) {
        PlayerManager.getInstance().setCommonSidedJoinedMap(event.getCommonSidedJoinedMap());
    }
}
