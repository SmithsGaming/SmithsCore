/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Client.GUI.Handlers;

import com.SmithsModding.SmithsCore.Client.GUI.Events.ContainerGuiOpenedEvent;
import com.SmithsModding.SmithsCore.Common.Inventory.ContainerSmithsCore;
import com.SmithsModding.SmithsCore.SmithsCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerGUIOpenedEventHandler {

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onPlayerOpenenedContainerGUIServerSide(ContainerGuiOpenedEvent event) throws IllegalArgumentException {
        if (!(event.getPlayer().openContainer instanceof ContainerSmithsCore))
            throw new IllegalArgumentException("The ContainerGuiOpenedEvent is fired for a player that is not watching a SmithsCore Container");

        if (!event.getContainerID().equals(((ContainerSmithsCore) event.getPlayer().openContainer).getContainerID()))
            throw new IllegalArgumentException("The ContainerGuiOpenedEvent is fired for a player that is not watching the correct Container");

        ContainerSmithsCore container = (ContainerSmithsCore) event.getPlayer().openContainer;
        container.onPlayerStartWatching(event.getPlayerID());

        SmithsCore.getRegistry().getCommonBus().post(event);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerOpenenedContainerGUIClientSide(ContainerGuiOpenedEvent event) throws IllegalArgumentException {
        if (!(event.getPlayer().openContainer instanceof ContainerSmithsCore))
            throw new IllegalArgumentException("The ContainerGuiOpenedEvent is fired for a player that is not watching a SmithsCore Container");

        if (!event.getContainerID().equals(((ContainerSmithsCore) event.getPlayer().openContainer).getContainerID()))
            throw new IllegalArgumentException("The ContainerGuiOpenedEvent is fired for a player that is not watching the correct Container");

        ContainerSmithsCore container = (ContainerSmithsCore) event.getPlayer().openContainer;
        container.onPlayerStartWatching(event.getPlayerID());

        SmithsCore.getRegistry().getCommonBus().post(event);
    }


}
