/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.Player.Handlers;

import com.SmithsModding.SmithsCore.Common.Player.Event.PlayersConnectedUpdatedEvent;
import com.SmithsModding.SmithsCore.Common.Player.Management.PlayerManager;
import com.SmithsModding.SmithsCore.SmithsCore;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class PlayersConnectedUpdatedEventHandler {

    /**
     * Method for handling the Network event when it arrives on the client side.
     *
     * @param event
     */
    @SubscribeEvent
    public void onPlayersConnectedUpdated(PlayersConnectedUpdatedEvent event) {
        PlayerManager.getInstance().setCommonSidedJoinedMap(event.getCommonSidedJoinedMap());
    }
}
