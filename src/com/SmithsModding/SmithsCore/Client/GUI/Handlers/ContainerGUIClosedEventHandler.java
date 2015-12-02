/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Client.GUI.Handlers;

import com.SmithsModding.SmithsCore.Client.GUI.Events.ContainerGuiClosedEvent;
import com.SmithsModding.SmithsCore.Client.GUI.Events.ContainerGuiOpenedEvent;
import com.SmithsModding.SmithsCore.Common.Inventory.ContainerSmithsCore;
import com.SmithsModding.SmithsCore.SmithsCore;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerGUIClosedEventHandler {

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onPlayerOpenenedContainerGUIServerSide(ContainerGuiClosedEvent event) throws IllegalArgumentException {
        if (!(event.getPlayer().openContainer instanceof ContainerSmithsCore))
            throw new IllegalArgumentException("The ContainerGuiClosedEvent is fired for a player that is not watching a SmithsCore Container");

        if (!event.getContainerID().equals(((ContainerSmithsCore) event.getPlayer().openContainer).getContainerID()))
            throw new IllegalArgumentException("The ContainerGuiClosedEvent is fired for a player that is not watching the correct Container");

        ContainerSmithsCore container = (ContainerSmithsCore) event.getPlayer().openContainer;
        container.getManager().onGuiOpened(event.getPlayerID());

        SmithsCore.getRegistry().getCommonBus().post(event);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerOpenenedContainerGUIClientSide(ContainerGuiClosedEvent event) {
        if (!(FMLClientHandler.instance().getClientPlayerEntity().openContainer instanceof ContainerSmithsCore))
            return;

        if (!event.getContainerID().equals(((ContainerSmithsCore) FMLClientHandler.instance().getClientPlayerEntity().openContainer).getContainerID()))
            return;

        ContainerSmithsCore container = (ContainerSmithsCore) event.getPlayer().openContainer;
        container.getManager().onGuiOpened(event.getPlayerID());
    }



}
