package com.smithsmodding.smithscore.common.tileentity.state;

import com.smithsmodding.smithscore.common.tileentity.*;
import net.minecraft.nbt.*;

/**
 * Created by Marc on 18.12.2015.
 */
public interface ITileEntityState {

    /**
     * Method called when this state get attached to a TE. Allows it to store a reference or modify values of the TE.
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
     * handle the disconnect from the state gracefully.
     */
    void onStateDestroyed ();

    /**
     * Method to let the attached TE know that this state needs to store data in the TE's NBTTagCompound that gets
     * written to disk.
     *
     * @return True when the state needs storing, false when not.
     */
    boolean requiresNBTStorage ();

    /**
     * Method that allows this state to read its data from Disk, when the attached TE gets loaded.
     *
     * @param stateData The stored data of this state.
     */
    void readFromNBTTagCompound (NBTBase stateData);

    /**
     * Method that allows this state to writes its data to Disk, when the attached TE writes its data to disk.
     *
     * @return A NBTBase that describes this state.
     */
    NBTBase writeToNBTTagCompound ();

    /**
     * Method to let the attached TE know that this state needs to store data in the TE's NBTTagCompound that gets used
     * to synchronise the TE.
     *
     * @return True when the state needs storing, false when not.
     */
    boolean requiresSynchronization ();

    /**
     * Method that allows this state to read its data from the network, when the attached TE gets synchronized.
     *
     * @param stateData The stored data of this state.
     */
    void readFromSynchronizationCompound (NBTBase stateData);

    /**
     * Method that allows this state to writes its data to the network, when the attached TE gets synchronized.
     *
     * @return A NBTBase that describes this state.
     */
    NBTBase writeToSynchronizationCompound ();

}
