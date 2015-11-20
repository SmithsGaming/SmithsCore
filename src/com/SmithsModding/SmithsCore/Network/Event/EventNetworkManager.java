/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Network.Event;

import com.SmithsModding.SmithsCore.Common.Event.Network.NetworkManagerInitializeEvent;
import com.SmithsModding.SmithsCore.Network.Event.Handlers.StandardNetworkableEventSyncMessageHandler;
import com.SmithsModding.SmithsCore.Network.Event.Messages.StandardNetworkableEventSyncMessage;
import com.SmithsModding.SmithsCore.Util.Common.Pair;
import com.SmithsModding.SmithsCore.Util.CoreReferences;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * The NetworkManager that is used to manage the Synchronising of the Events in the Busses.
 */
public class EventNetworkManager {
    //Private instance of the NetworkManager
    private static SimpleNetworkWrapper iInstance;

    /**
     * Returns the Instance of the Channel that is used for Event Synchronizing.
     *
     * @return A instance of the SimpleNetworkWrapper that describes the Channel that is used to Synchronize Events.
     */
    public static SimpleNetworkWrapper getInstance() {
        return iInstance;
    }

    /**
     * Function used to initialize the Channel.
     * Can also be used to reset the System if Need be.
     */
    public static void Init() {
        iInstance = new SimpleNetworkWrapper(CoreReferences.General.MOD_ID.toLowerCase() + ".EventSyncing");

        //Register the StandardNetworkableEvent System
        iInstance.registerMessage(StandardNetworkableEventSyncMessageHandler.class, StandardNetworkableEventSyncMessage.class, 0, Side.CLIENT);
        iInstance.registerMessage(StandardNetworkableEventSyncMessageHandler.class, StandardNetworkableEventSyncMessage.class, 1, Side.SERVER);

        //Create an Event to notify other Mods that the NetworkManager is being Instantiated.
        //This gets all the Events and gives the depending Mods a chance to store a reference to this EventBus
        NetworkManagerInitializeEvent tEvent = new NetworkManagerInitializeEvent(iInstance);
        tEvent.PostCommon();

        //Loop through all additional Events and register them
        int tDescriminator = 2;
        for (Pair<Class, Class> tMessageClassPair : tEvent.getAdditionalMessages().keySet()) {
            iInstance.registerMessage(tMessageClassPair.getKey(), tMessageClassPair.getValue(), tDescriminator, tEvent.getAdditionalMessages().get(tMessageClassPair));
            tDescriminator++;
        }
    }


}
