/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.client.handlers.gui;

import com.smithsmodding.smithscore.client.events.gui.*;
import com.smithsmodding.smithscore.common.inventory.*;
import net.minecraftforge.fml.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;

public class ContainerGUIClosedEventHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerClosedContainerGUIClientSide (ContainerGuiClosedEvent event) {
        if (!( FMLClientHandler.instance().getClientPlayerEntity().openContainer instanceof ContainerSmithsCore ))
            return;

        if (!event.getContainerID().equals(( (ContainerSmithsCore) FMLClientHandler.instance().getClientPlayerEntity().openContainer ).getContainerID()))
            return;

        ContainerSmithsCore container = (ContainerSmithsCore) FMLClientHandler.instance().getClientPlayerEntity().openContainer;
        container.getManager().onGuiOpened(event.getPlayerID());
    }


}
