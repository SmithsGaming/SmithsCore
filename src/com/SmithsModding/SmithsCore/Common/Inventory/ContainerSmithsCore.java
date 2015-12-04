package com.SmithsModding.SmithsCore.Common.Inventory;

import com.SmithsModding.SmithsCore.Client.GUI.Host.IGUIBasedComponentHost;
import com.SmithsModding.SmithsCore.Client.GUI.Management.IGUIManager;
import com.SmithsModding.SmithsCore.Client.GUI.Management.RelayBasedGUIManager;
import net.minecraft.inventory.Container;

/**
 * Created by Orion
 * Created on 22.11.2015
 * 22:46
 * <p/>
 * Copyrighted according to Project specific license
 */
public abstract class ContainerSmithsCore extends Container implements IContainerHost {

    IGUIManager manager;
    IContainerHost host;

    String containerID;

    public ContainerSmithsCore(String containerID, IContainerHost host) {
        this.containerID = containerID;
        this.host = host;
        this.manager = new RelayBasedGUIManager(host);
    }


    /**
     * Getter for the Containers ID.
     * Used to identify the container over the Network.
     * If this relates to TileEntities, it should contain a ID and a location based ID so that multiple instances
     * of this container matched up to different TileEntities can be separated.
     *
     * @return The ID of this Container Instance.
     */
    @Override
    public String getContainerID() {
        return containerID;
    }

    /**
     * Function to get the IGUIManager.
     *
     * @return Returns the current GUIManager.
     */
    @Override
    public IGUIManager getManager() {
        return manager;
    }

    /**
     * Function to set the IGUIManager
     *
     * @param newManager THe new IGUIManager.
     */
    @Override
    public void setManager(IGUIManager newManager) {
        manager = newManager;
    }
}
