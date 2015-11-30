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

    ArrayList<UUID> watchingPlayers = new ArrayList<UUID>();
    String containerID;

    public ContainerSmithsCore(String containerID) {
        this.containerID = containerID;
    }


    /**
     * Getter for the Containers ID.
     * Used to identify the container over the Network.
     * If this relates to TileEntities, it should contain a ID and a location based ID so that multiple instances
     * of this container matched up to different TileEntities can be separated.
     *
     * @return The ID of this Container Instance.
     */
    public String getContainerID() {
        return containerID;
    }



    public void onPlayerStartWatching(UUID pPlayerID) {
        if (watchingPlayers.contains(pPlayerID)) {
            SmithsCore.getLogger().warn("A player is already watching this Container!");
            return;
        }

        watchingPlayers.add(pPlayerID);
    }

    public void onPlayerStopWatching(UUID pPlayerID) {
        if (!watchingPlayers.contains(pPlayerID)) {
            SmithsCore.getLogger().warn("A player already stopped watching this Container!");
            return;
        }

        watchingPlayers.remove(pPlayerID);
    }

}
