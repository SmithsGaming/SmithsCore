/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.player.handlers;

import com.smithsmodding.smithscore.*;
import com.smithsmodding.smithscore.common.player.event.*;
import com.smithsmodding.smithscore.common.player.management.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;

public class PlayersOnlineUpdatedEventHandler {

    //Automatically Registers this to the NetworkEventBus when the system is running on the client Side
    static {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            SmithsCore.getRegistry().getNetworkBus().register(new PlayersOnlineUpdatedEventHandler());
        }
    }

    /**
     * Method for handling the network event when it arrives on the client side.
     *
     * @param event
     */
    @SubscribeEvent
    public void onPlayersOnlineUpdated(PlayersOnlineUpdatedEvent event) {
        PlayerManager.getInstance().setCommonSidedOnlineMap(event.getCommonSidedOnlineMap());
    }

}
