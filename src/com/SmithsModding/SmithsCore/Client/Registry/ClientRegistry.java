/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Client.Registry;

import com.SmithsModding.SmithsCore.Common.Registry.CommonRegistry;
import net.minecraftforge.fml.common.eventhandler.EventBus;

/**
 * Used as the Central point of Data on the Client Side.
 * <p/>
 * Defines the special Client EventBus.
 */
public class ClientRegistry extends CommonRegistry {

    //This event bus is used for client specific stuff only. It handles GUI Events.
    //All other events should be fired on the CommonBus.
    //If a NetworkSyncableEvent is fired it will automatically be synced to the Server and is there fired on the NetworkRelayBus
    private final EventBus iClientEventBus = new EventBus();

    /**
     * The event bus used for client events. Under common code this is the standard Common event bus on the client side is this a special event bus
     * not related to the Common Event bus allowing Events to be handled seperatly without having to worry about SideOnly
     * instances.
     *
     * @return An EventBus that is used to handle Client specific Events.
     */
    @Override
    public EventBus getClientBus() {
        return iClientEventBus;
    }
}
