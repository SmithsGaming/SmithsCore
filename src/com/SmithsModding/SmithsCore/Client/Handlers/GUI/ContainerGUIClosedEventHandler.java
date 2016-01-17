/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.client.Handlers.GUI;

import net.minecraftforge.fml.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;

public class ContainerGUIClosedEventHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerOpenenedContainerGUIClientSide (ContainerGuiClosedEvent event) {
        if (!( FMLClientHandler.instance().getClientPlayerEntity().openContainer instanceof ContainerSmithsCore ))
            return;

        if (!event.getContainerID().equals(( (ContainerSmithsCore) FMLClientHandler.instance().getClientPlayerEntity().openContainer ).getContainerID()))
            return;

        ContainerSmithsCore container = (ContainerSmithsCore) event.getPlayer().openContainer;
        container.getManager().onGuiOpened(event.getPlayerID());
    }


}
