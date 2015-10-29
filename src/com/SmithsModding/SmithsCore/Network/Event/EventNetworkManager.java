/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Network.Event;

import com.SmithsModding.SmithsCore.Util.CoreReferences;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class EventNetworkManager {
    private SimpleNetworkWrapper iInstance;

    public SimpleNetworkWrapper getInstance() {
        return iInstance;
    }

    public void Init() {
        iInstance = new SimpleNetworkWrapper(CoreReferences.General.MOD_ID.toLowerCase() + ".EventSyncing");
    }


}
