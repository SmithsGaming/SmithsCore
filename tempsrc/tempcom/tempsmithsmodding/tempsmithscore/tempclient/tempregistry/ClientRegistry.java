/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.client.registry;

import com.smithsmodding.smithscore.client.mouse.*;
import com.smithsmodding.smithscore.client.textures.*;
import com.smithsmodding.smithscore.common.registry.*;
import net.minecraftforge.fml.common.eventhandler.*;

/**
 * Used as the Central point of Data on the client Side.
 * <p/>
 * Defines the special client EventBus.
 */
public class ClientRegistry extends CommonRegistry {

    //This event bus is used for client specific stuff only. It handles gui events.
    //All other events should be fired on the CommonBus.
    //If a NetworkSyncableEvent is fired it will automatically be synced to the Server and is there fired on the NetworkRelayBus
    private final EventBus clientEventBus = new EventBus();
    private final HolographicTextureCreator holographicTextureCreator = new HolographicTextureCreator();
    private final MouseManager mouseManager = new MouseManager();
    private float partialTickTime;

    public ClientRegistry () {
    }

    /**
     * The event bus used for client events. Under common code this is the standard common event bus on the client side is this a special event bus
     * not related to the common events bus allowing events to be handled seperatly without having to worry about SideOnly
     * instances.
     *
     * @return An EventBus that is used to handle client specific events.
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
