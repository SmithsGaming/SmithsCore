package com.SmithsModding.SmithsCore.Common.Inventory;

import com.SmithsModding.SmithsCore.SmithsCore;
import net.minecraft.inventory.Container;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Orion
 * Created on 22.11.2015
 * 22:46
 * <p/>
 * Copyrighted according to Project specific license
 */
public abstract class ContainerSmithsCore extends Container {

    ArrayList<UUID> iWatchingPlayers = new ArrayList<UUID>();

    public void onPlayerStartWatching(UUID pPlayerID) {
        if (iWatchingPlayers.contains(pPlayerID)) {
            SmithsCore.getLogger().warn("A player is already watching this Container!");
            return;
        }

        iWatchingPlayers.add(pPlayerID);
    }

    public void onPlayerStopWatching(UUID pPlayerID) {
        if (!iWatchingPlayers.contains(pPlayerID)) {
            SmithsCore.getLogger().warn("A player already stopped watching this Container!");
            return;
        }

        iWatchingPlayers.remove(pPlayerID);
    }

}
