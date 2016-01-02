package com.SmithsModding.SmithsCore.Common.Inventory;

import com.SmithsModding.SmithsCore.Client.Events.GUI.*;
import com.SmithsModding.SmithsCore.Client.GUI.Management.*;
import com.SmithsModding.SmithsCore.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

/**
 * Created by Orion
 * Created on 22.11.2015
 * 22:46
 * <p/>
 * Copyrighted according to Project specific license
 */
public abstract class ContainerSmithsCore extends Container implements IContainerHost {

    public static final int PLAYER_INVENTORY_ROWS = 3;
    public static final int PLAYER_INVENTORY_COLUMNS = 9;

    private IGUIManager manager;
    private IContainerHost host;

    private String containerID;

    private IInventory containerInventory;
    private IInventory playerInventory;

    public ContainerSmithsCore (String containerID, IContainerHost host, IInventory containerInventory, EntityPlayer playerMP) {
        this.containerID = containerID;
        this.host = host;
        this.manager = new RelayBasedGUIManager(host);
        this.containerInventory = containerInventory;
        this.playerInventory = playerMP.inventory;

        this.manager.onGuiOpened(playerMP.getUniqueID());

        if (this.host.isRemote())
            return;

        SmithsCore.getRegistry().getCommonBus().post(new ContainerGuiOpenedEvent(playerMP, this));
    }

    /**
     * Called when the container is closed.
     *
     * @param playerIn
     */
    @Override
    public void onContainerClosed (EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);

        this.manager.onGUIClosed(playerIn.getUniqueID());

        if (this.host.isRemote())
            return;

        SmithsCore.getRegistry().getCommonBus().post(new ContainerGuiClosedEvent(playerIn, this));
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

    @Override
    public boolean isRemote () {
        return host.isRemote();
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

    public IInventory getContainerInventory () {
        return containerInventory;
    }

    public IInventory getPlayerInventory () {
        return playerInventory;
    }

}
