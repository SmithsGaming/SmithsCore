/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.Player.Handlers;

import com.SmithsModding.SmithsCore.Common.Player.Event.PlayersOnlineUpdatedEvent;
import com.SmithsModding.SmithsCore.Common.Player.Management.PlayerManager;
import com.SmithsModding.SmithsCore.SmithsCore;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class PlayersOnlineUpdatedEventHandler {

    //Automatically Registers this to the NetworkEventBus when the system is running on the Client Side
    static {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            SmithsCore.getRegistry().getNetworkBus().register(new PlayersOnlineUpdatedEventHandler());
        }
    }

    /**
     * Method for handling the Network event when it arrives on the client side.
     *
     * @param event
     */
    @SubscribeEvent
    public void onPlayersOnlineUpdated(PlayersOnlineUpdatedEvent event) {
        PlayerManager.getInstance().setCommonSidedOnlineMap(event.getCommonSidedOnlineMap());
    }

}
