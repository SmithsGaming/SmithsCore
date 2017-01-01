/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.player.handlers;

import com.smithsmodding.smithscore.common.player.event.PlayersOnlineUpdatedEvent;
import com.smithsmodding.smithscore.common.player.management.PlayerManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

public class PlayersOnlineUpdatedEventHandler {
    /**
     * Method for handling the network event when it arrives on the client side.
     *
     * @param event The event indicating that the amount of online Players updated.
     */
    @SubscribeEvent
    public void onPlayersOnlineUpdated(@Nonnull PlayersOnlineUpdatedEvent event) {
        PlayerManager.getInstance().setCommonSidedOnlineMap(event.getCommonSidedOnlineMap());
    }

}
