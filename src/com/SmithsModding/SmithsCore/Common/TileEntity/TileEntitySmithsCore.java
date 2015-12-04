/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.TileEntity;

import com.SmithsModding.SmithsCore.Client.GUI.Management.IGUIManager;
import com.SmithsModding.SmithsCore.Client.GUI.Management.TileStorageBasedGUIManager;
import com.SmithsModding.SmithsCore.Common.Inventory.IContainerHost;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.Coordinate3D;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntitySmithsCore extends TileEntity implements IContainerHost {

    private IGUIManager manager = new TileStorageBasedGUIManager();

    /**
     * Function used to convert the BlockPos of this TE into a Coordinate3D
     *
     * @return A Coordinate3D equivalent of the BlockPos of this TE.
     */
    public Coordinate3D getLocation() {
        return new Coordinate3D(this.pos);
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

    public abstract String getGlobalTileEntityId();

    public abstract boolean shouldAutoSaveInventoryToNBT();
}
