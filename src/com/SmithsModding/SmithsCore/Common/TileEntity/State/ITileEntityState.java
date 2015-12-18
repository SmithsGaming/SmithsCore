package com.SmithsModding.SmithsCore.Common.TileEntity.State;

import com.SmithsModding.SmithsCore.Common.TileEntity.*;
import net.minecraft.nbt.*;

/**
 * Created by Marc on 18.12.2015.
 */
public interface ITileEntityState {

    /**
     * Method called when this State get attached to a TE. Allows it to store a reference or modify values of the TE.
     *
     * @param tileEntitySmithsCore The TE this state got attached to.
     */
    void onStateCreated (TileEntitySmithsCore tileEntitySmithsCore);

    /**
     * Called to indicate this TE that some of its values may have been updated. Use it to perform additional
     * calculation on this data.
     */
    void onStateUpdated ();

    /**
     * Method called by the Attached TE to indicate that it is being detached and discarded by its TE. Allows you to
     * handle the disconnect from the State gracefully.
     */
    void onStateDestroyed ();

    /**
     * Method to let the attached TE know that this State needs to store data in the TE's NBTTagCompound that gets
     * written to disk.
     *
     * @return True when the state needs storing, false when not.
     */
    boolean requiresNBTStorage ();

    /**
     * Method that allows this State to read its data from Disk, when the attached TE gets loaded.
     *
     * @param stateData The stored data of this State.
     */
    void readFromNBTTagCompound (NBTBase stateData);

    /**
     * Method that allows this state to writes its data to Disk, when the attached TE writes its data to disk.
     *
     * @return A NBTBase that describes this State.
     */
    NBTBase writeToNBTTagCompound ();

    /**
     * Method to let the attached TE know that this State needs to store data in the TE's NBTTagCompound that gets used
     * to synchronise the TE.
     *
     * @return True when the state needs storing, false when not.
     */
    boolean requiresSynchronization ();

    /**
     * Method that allows this State to read its data from the Network, when the attached TE gets synchronized.
     *
     * @param stateData The stored data of this State.
     */
    void readFromSynchronizationCompound (NBTBase stateData);

    /**
     * Method that allows this state to writes its data to the Network, when the attached TE gets synchronized.
     *
     * @return A NBTBase that describes this State.
     */
    NBTBase writeToSynchronizationCompound ();

}
