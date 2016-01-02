package com.SmithsModding.SmithsCore.Common.Inventory;

import com.SmithsModding.SmithsCore.Client.GUI.Management.*;

/**
 * Created by Orion
 * Created on 01.12.2015
 * 18:20
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IContainerHost extends IGUIManagerProvider
{
    /**
     * Getter for the Containers ID.
     * Used to identify the container over the Network.
     * If this relates to TileEntities, it should contain a ID and a location based ID so that multiple instances
     * of this container matched up to different TileEntities can be separated.
     *
     * @return The ID of this Container Instance.
     */
    String getContainerID();

    boolean isRemote ();
}
