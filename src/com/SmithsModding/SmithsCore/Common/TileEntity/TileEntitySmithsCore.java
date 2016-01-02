/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.TileEntity;

import com.SmithsModding.SmithsCore.Client.GUI.Management.*;
import com.SmithsModding.SmithsCore.Common.Events.*;
import com.SmithsModding.SmithsCore.Common.Fluid.*;
import com.SmithsModding.SmithsCore.Common.Inventory.*;
import com.SmithsModding.SmithsCore.Common.TileEntity.State.*;
import com.SmithsModding.SmithsCore.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import com.SmithsModding.SmithsCore.Util.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.fluids.*;

import java.util.*;

public abstract class TileEntitySmithsCore extends TileEntity implements IContainerHost {

    private IGUIManager manager = new TileStorageBasedGUIManager();
    private ITileEntityState state;

    /**
     * Constructor to create a new TileEntity for a SmithsCore Mod.
     *
     * Handles the setting of the core system values like the state, and the GUIManager.
     *
     * @param initialState The TE State that gets set on default when a new Instance is created.
     * @param manager The GUIManager that handles interactins with events comming from UI's
     */
    protected TileEntitySmithsCore (ITileEntityState initialState, IGUIManager manager) {
        setManager(manager);
        setState(initialState);
    }


    @Override
    public void readFromNBT (NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (this instanceof IInventory)
            this.readInventoryFromCompound(compound.getTag(CoreReferences.NBT.INVENTORY));

        if (this instanceof IFluidContainingEntity)
            this.readFluidsFromCompound(compound.getTag(CoreReferences.NBT.FLUIDS));

        if (getState().requiresNBTStorage())
            this.getState().readFromNBTTagCompound(compound.getTag(CoreReferences.NBT.STATE));
    }

    @Override
    public void writeToNBT (NBTTagCompound compound) {
        super.writeToNBT(compound);

        if (this instanceof IInventory)
            compound.setTag(CoreReferences.NBT.INVENTORY, this.writeInventoryToCompound());

        if (this instanceof IFluidContainingEntity)
            compound.setTag(CoreReferences.NBT.FLUIDS, this.writeFluidsToCompound());

        if (getState().requiresNBTStorage())
            compound.setTag(CoreReferences.NBT.STATE, this.getState().writeToNBTTagCompound());
    }

    /**
     * Method to indicate that this TE has changed its data.
     */
    @Override
    public void markDirty () {
        getState().onStateUpdated();

        //Vanilla compatibility
        super.markDirty();
        getWorld().markBlockForUpdate(getPos());

        //Notify the events system of a update.
        SmithsCore.getRegistry().getCommonBus().post(new TileEntityDataUpdatedEvent(this));
    }

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


    /**
     * Getter for the current TE State.
     *
     * @return The current ITileEntityState.
     */
    public ITileEntityState getState () {
        return state;
    }

    /**
     * Method to set the new state on this instance of the TE
     *
     * @param state The new state.
     */
    public void setState (ITileEntityState state) {
        this.state = state;
        state.onStateCreated(this);
    }


    /**
     * Standard method to write the Inventory data of this TE to the NBTCompound that stores this TE's Data.
     *
     * @return A NBTTagList with all stacks in the Inventory.
     */
    public NBTBase writeInventoryToCompound () {
        IInventory inventory = (IInventory) this;
        NBTTagList inventoryList = new NBTTagList();

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);

            if (stack == null)
                continue;

            NBTTagCompound slotCompound = new NBTTagCompound();
            slotCompound.setInteger(CoreReferences.NBT.InventoryData.SLOTINDEX, i);
            slotCompound.setTag(CoreReferences.NBT.InventoryData.STACKDATA, stack.writeToNBT(new NBTTagCompound()));

            inventoryList.appendTag(slotCompound);
        }

        return inventoryList;
    }

    /**
     * Standard method to read the Inventory data of this TE from the NBTCompound that stores this TE's Data.
     *
     * @param inventoryCompound A NBTBase instance in the form of a TagList containing all the Data of the Slots in this
     *                          inventory.
     */
    public void readInventoryFromCompound (NBTBase inventoryCompound) {
        if (!( inventoryCompound instanceof NBTTagList ))
            throw new IllegalArgumentException("The given store type is not compatible with this TE!");

        IInventory inventory = (IInventory) this;
        NBTTagList inventoryList = (NBTTagList) inventoryCompound;

        for (int i = 0; i < inventoryList.tagCount(); i++) {
            NBTTagCompound slotCompound = (NBTTagCompound) inventoryList.get(i);

            inventory.setInventorySlotContents(slotCompound.getInteger(CoreReferences.NBT.InventoryData.SLOTINDEX), ItemStack.loadItemStackFromNBT(slotCompound.getCompoundTag(CoreReferences.NBT.InventoryData.STACKDATA)));
        }
    }


    /**
     * Standard method to write the fluid data of this TE to the NBTCompound that stores this TE's Data.
     *
     * @return A NBTTagList with all fluids in the Tile.
     */
    public NBTBase writeFluidsToCompound () {
        IFluidContainingEntity fluidContainingEntity = (IFluidContainingEntity) this;

        NBTTagList fluidData = new NBTTagList();

        for (FluidStack stack : fluidContainingEntity.getAllFluids()) {
            fluidData.appendTag(stack.writeToNBT(new NBTTagCompound()));
        }

        return fluidData;
    }

    /**
     * Standard method to read the fluids data of this TE from the NBTCompound that stores this TE's Data.
     *
     * @param inventoryCompound A NBTBase instance in the form of a TagList containing all the Data of the fluids in
     *                          this TileEntity.
     */
    public void readFluidsFromCompound (NBTBase inventoryCompound) {
        if (!( inventoryCompound instanceof NBTTagList ))
            throw new IllegalArgumentException("The given store type is not compatible with this TE!");

        IFluidContainingEntity fluidContainingEntity = (IFluidContainingEntity) this;

        NBTTagList inventoryList = (NBTTagList) inventoryCompound;
        ArrayList<FluidStack> fluidStacks = new ArrayList<FluidStack>();

        for (int i = 0; i < inventoryList.tagCount(); i++) {
            NBTTagCompound fluidCompound = (NBTTagCompound) inventoryList.get(i);

            fluidStacks.add(FluidStack.loadFluidStackFromNBT(fluidCompound));
        }

        fluidContainingEntity.setAllFluids(fluidStacks);
    }


    /**
     * Method called by the synchronization system to send the data to the Client.
     *
     * @param synchronizationCompound The NBTTagCompound to write your data to.
     *
     * @return A NBTTagCompound containing all the data required for the synchronization of this TE.
     */
    public NBTTagCompound writeToSynchronizationCompound (NBTTagCompound synchronizationCompound) {
        super.writeToNBT(synchronizationCompound);

        if (this instanceof IInventory)
            synchronizationCompound.setTag(CoreReferences.NBT.INVENTORY, this.writeInventoryToCompound());

        if (this instanceof IFluidContainingEntity)
            synchronizationCompound.setTag(CoreReferences.NBT.FLUIDS, this.writeFluidsToCompound());

        if (getState().requiresSynchronization())
            synchronizationCompound.setTag(CoreReferences.NBT.STATE, this.getState().writeToSynchronizationCompound());

        return synchronizationCompound;
    }

    /**
     * Method called by the synchronization system to read the data from the Server.
     *
     * @param synchronizationCompound The NBTTagCompound to read your data from.
     */
    public void readFromSynchronizationCompound (NBTTagCompound synchronizationCompound) {
        if (this instanceof IInventory)
            this.readInventoryFromCompound(synchronizationCompound.getTag(CoreReferences.NBT.INVENTORY));

        if (this instanceof IFluidContainingEntity)
            this.readFluidsFromCompound(synchronizationCompound.getTag(CoreReferences.NBT.FLUIDS));

        if (getState().requiresSynchronization())
            this.getState().readFromNBTTagCompound(synchronizationCompound.getTag(CoreReferences.NBT.STATE));
    }

    @Override
    public boolean isRemote () {
        return getWorld().isRemote;
    }
}
