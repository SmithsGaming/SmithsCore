/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Client.Registry;

import com.SmithsModding.SmithsCore.Common.Registry.CommonRegistry;
import cpw.mods.fml.common.eventhandler.EventBus;

public class ClientRegistry extends CommonRegistry {

    //This event bus is used for client specific stuff only. It handles GUI Events.
    //All other events should be fired on the CommonBus.
    //If a NetworkSyncableEvent is fired it will automatically be synced to the Server and is there fired on the NetworkRelayBus
    private final EventBus iClientEventBus = new EventBus();

    /**
     * The client specific EventBus
     *
     * @return
     */
    @Override
    public EventBus getClientEventBus() {
        return iClientEventBus;
    }
}
