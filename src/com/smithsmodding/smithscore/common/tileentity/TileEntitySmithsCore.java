/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.tileentity;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.client.gui.management.IGUIManager;
import com.smithsmodding.smithscore.client.gui.management.TileStorageBasedGUIManager;
import com.smithsmodding.smithscore.common.events.TileEntityDataUpdatedEvent;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
import com.smithsmodding.smithscore.common.inventory.IContainerHost;
import com.smithsmodding.smithscore.common.structures.IStructureComponent;
import com.smithsmodding.smithscore.common.tileentity.state.ITileEntityState;
import com.smithsmodding.smithscore.util.CoreReferences;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class TileEntitySmithsCore extends TileEntity implements IContainerHost {

    InvWrapper invWrapper;
    private IGUIManager manager = new TileStorageBasedGUIManager();
    private ITileEntityState state;

    /**
     * Constructor to create a new tileentity for a smithscore Mod.
     *
     * Handles the setting of the core system values like the state, and the GUIManager.
     *
     * @param initialState The TE state that gets set on default when a new Instance is created.
     * @param manager The GUIManager that handles interactins with events comming from UI's
     */
    protected TileEntitySmithsCore (ITileEntityState initialState, IGUIManager manager) {
        setManager(manager);
        setState(initialState);
    }

    @Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && (this instanceof IInventory))
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability (Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && (this instanceof IInventory)){
            if (invWrapper == null)
                invWrapper = new InvWrapper((IInventory) this);

            return (T) invWrapper;
        }

        return super.getCapability(capability, facing);
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

        if (this instanceof IStructureComponent)
            this.readStructureComponentFromNBT((NBTTagCompound) compound.getTag(CoreReferences.NBT.STRUCTURE));

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

        if (this instanceof IStructureComponent)
            compound.setTag(CoreReferences.NBT.STRUCTURE, this.writeStructureComponentDataToNBT(new NBTTagCompound()));
    }

    /**
     * Method to indicate that this TE has changed its data.
     */
    @Override
    public void markDirty () {
        getState().onStateUpdated();

        //Vanilla compatibility
        super.markDirty();
        getWorld().markChunkDirty(getPos(), this);

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
     * Getter for the current TE state.
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
     * Standard method to write the inventory data of this TE to the NBTCompound that stores this TE's Data.
     *
     * @return A NBTTagList with all stacks in the inventory.
     */
    protected NBTBase writeInventoryToCompound () {
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
     * Standard method to read the inventory data of this TE from the NBTCompound that stores this TE's Data.
     *
     * @param inventoryCompound A NBTBase instance in the form of a TagList containing all the Data of the Slots in this
     *                          inventory.
     */
    protected void readInventoryFromCompound (NBTBase inventoryCompound) {
        if (inventoryCompound == null)
            return;

        if (!( inventoryCompound instanceof NBTTagList ))
            throw new IllegalArgumentException("The given store type is not compatible with this TE!");

        IInventory inventory = (IInventory) this;
        NBTTagList inventoryList = (NBTTagList) inventoryCompound;

        for (int i = 0; i < inventoryList.tagCount(); i++) {
            NBTTagCompound slotCompound = (NBTTagCompound) inventoryList.get(i);

            ItemStack newStack = ItemStack.loadItemStackFromNBT(slotCompound.getCompoundTag(CoreReferences.NBT.InventoryData.STACKDATA));

            inventory.setInventorySlotContents(slotCompound.getInteger(CoreReferences.NBT.InventoryData.SLOTINDEX), newStack);
        }
    }


    /**
     * Standard method to write the fluid data of this TE to the NBTCompound that stores this TE's Data.
     *
     * @return A NBTTagList with all fluids in the Tile.
     */
    protected NBTBase writeFluidsToCompound () {
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
     *                          this tileentity.
     */
    protected void readFluidsFromCompound (NBTBase inventoryCompound) {
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
     * Method used to write the structure data to NBT
     *
     * @param structureCompound The NBTTagCompound to write the structure data to.
     *
     * @return The given structureCompound with the structure data appended.
     */
    protected NBTTagCompound writeStructureComponentDataToNBT (NBTTagCompound structureCompound) {
        IStructureComponent component = (IStructureComponent) this;

        structureCompound.setBoolean(CoreReferences.NBT.StructureData.ISSLAVED, component.isSlaved());

        if (component.isSlaved()) {
            structureCompound.setTag(CoreReferences.NBT.StructureData.MASTERLOCATION, component.getMasterLocation().toCompound());
            structureCompound.setTag(CoreReferences.NBT.StructureData.SLAVELOCATIONS, new NBTTagList());
        } else {
            structureCompound.setTag(CoreReferences.NBT.StructureData.MASTERLOCATION, getLocation().toCompound());

            NBTTagList coordinateList = new NBTTagList();

            Iterator<Coordinate3D> coordinate3DIterator = component.getSlaveCoordinates().iterator();
            while (coordinate3DIterator.hasNext()) {
                coordinateList.appendTag(coordinate3DIterator.next().toCompound());
            }

            structureCompound.setTag(CoreReferences.NBT.StructureData.SLAVELOCATIONS, coordinateList);
        }

        return structureCompound;
    }

    /**
     * Function to read the structure data from a NBT
     *
     * @param structureCompound The compound with the Structure Data.
     */
    protected void readStructureComponentFromNBT (NBTTagCompound structureCompound) {
        IStructureComponent component = (IStructureComponent) this;

        boolean isSlaved = structureCompound.getBoolean(CoreReferences.NBT.StructureData.ISSLAVED);

        Coordinate3D masterLocation = Coordinate3D.fromNBT(structureCompound.getCompoundTag(CoreReferences.NBT.StructureData.MASTERLOCATION));
        NBTTagList slaveCoordinateTagList = structureCompound.getTagList(CoreReferences.NBT.StructureData.SLAVELOCATIONS, Constants.NBT.TAG_COMPOUND);

        if (isSlaved) {
            component.setMasterLocation(masterLocation);
            component.setSlaveCoordinates(new ArrayList<Coordinate3D>());
        } else {
            component.setMasterLocation(getLocation());

            ArrayList<Coordinate3D> slaveCoordinateList = new ArrayList<Coordinate3D>();
            for (int i = 0; i < slaveCoordinateTagList.tagCount(); i++) {
                NBTTagCompound coordinateCompound = (NBTTagCompound) slaveCoordinateTagList.get(i);
                slaveCoordinateList.add(Coordinate3D.fromNBT(coordinateCompound));
            }

            component.setSlaveCoordinates(slaveCoordinateList);
        }

    }


    /**
     * Method called by the synchronization system to send the data to the client.
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

        if (this instanceof IStructureComponent)
            synchronizationCompound.setTag(CoreReferences.NBT.STRUCTURE, this.writeStructureComponentDataToNBT(new NBTTagCompound()));

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

        if (this instanceof IStructureComponent)
            this.readStructureComponentFromNBT((NBTTagCompound) synchronizationCompound.getTag(CoreReferences.NBT.STRUCTURE));
    }

    @Override
    public boolean isRemote () {
        return getWorld().isRemote;
    }
}
