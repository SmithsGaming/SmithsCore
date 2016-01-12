/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Client.Registry;

import com.SmithsModding.SmithsCore.Client.Handlers.GUI.*;
import com.SmithsModding.SmithsCore.Client.Handlers.Network.*;
import com.SmithsModding.SmithsCore.Client.Mouse.*;
import com.SmithsModding.SmithsCore.Client.Textures.*;
import com.SmithsModding.SmithsCore.Common.Handlers.Network.*;
import com.SmithsModding.SmithsCore.Common.Player.Handlers.*;
import com.SmithsModding.SmithsCore.Common.Registry.*;
import com.SmithsModding.SmithsCore.Util.Client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

/**
 * Used as the Central point of Data on the Client Side.
 * <p/>
 * Defines the special Client EventBus.
 */
public class ClientRegistry extends CommonRegistry {

    //This event bus is used for client specific stuff only. It handles GUI Events.
    //All other events should be fired on the CommonBus.
    //If a NetworkSyncableEvent is fired it will automatically be synced to the Server and is there fired on the NetworkRelayBus
    private final EventBus clientEventBus = new EventBus();
    private final HolographicTextureCreator holographicTextureCreator = new HolographicTextureCreator();
    private final MouseManager mouseManager = new MouseManager();
    private float partialTickTime;

    public ClientRegistry () {
    }

    /**
     * Function used to register the EventHandlers
     */
    @Override
    public void registerEventHandlers() {
        getNetworkBus().register(new ContainerGUIOpenedEventHandler());
        getNetworkBus().register(new ContainerGUIClosedEventHandler());

        getCommonBus().register(new ClientNetworkableEventHandler());
        getCommonBus().register(new CommonNetworkableEventHandler());

        getNetworkBus().register(new PlayersOnlineUpdatedEventHandler());
        getNetworkBus().register(new PlayersConnectedUpdatedEventHandler());

        getNetworkBus().register(new BlockModelUpdateEventHandler());

        MinecraftForge.EVENT_BUS.register(new Textures());
        MinecraftForge.EVENT_BUS.register(holographicTextureCreator);
        MinecraftForge.EVENT_BUS.register(mouseManager);
        MinecraftForge.EVENT_BUS.register(new ClientTickEventHandler());
        MinecraftForge.EVENT_BUS.register(new RenderGameOverlayEventHandler());
    }

    /**
     * The event bus used for client events. Under common code this is the standard Common event bus on the client side is this a special event bus
     * not related to the Common Events bus allowing Events to be handled seperatly without having to worry about SideOnly
     * instances.
     *
     * @return An EventBus that is used to handle Client specific Events.
     */
    @Override
    public EventBus getClientBus() {
        return clientEventBus;
    }

    public MouseManager getMouseManager()
    {
        return mouseManager;
    }

    public HolographicTextureCreator getHolographicTextureCreator () {
        return holographicTextureCreator;
    }

    public float getPartialTickTime() {
        return partialTickTime;
    }

    public void setPartialTickTime(float partialTickTime) {
        this.partialTickTime = partialTickTime;
    }
}
