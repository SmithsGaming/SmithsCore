/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.tileentity;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.client.gui.management.IGUIManager;
import com.smithsmodding.smithscore.common.events.TileEntityDataUpdatedEvent;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
import com.smithsmodding.smithscore.common.inventory.IContainerHost;
import com.smithsmodding.smithscore.common.inventory.IItemStorage;
import com.smithsmodding.smithscore.common.inventory.ItemStorageItemHandler;
import com.smithsmodding.smithscore.common.structures.IStructurePart;
import com.smithsmodding.smithscore.common.structures.StructureRegistry;
import com.smithsmodding.smithscore.common.tileentity.state.ITileEntityState;
import com.smithsmodding.smithscore.util.CoreReferences;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IWorldNameable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

public abstract class TileEntitySmithsCore<S extends ITileEntityState, G extends IGUIManager> extends TileEntity implements IContainerHost<G>, IWorldNameable {

    ItemStorageItemHandler invWrapper;
    private G manager;
    private S state;

    private boolean loadingFromNBT = false;

    private String name = "";

    /**
     * Constructor to create a new TileEntity for a SmithsCore Mod.
     *
     * Handles the setting of the core system values like the state, and the GUIManager.
     *
     */
    protected TileEntitySmithsCore() {
        setManager(getInitialGuiManager());
        setState(getInitialState());
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && (this instanceof IItemStorage))
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && (this instanceof IItemStorage)) {
            if (invWrapper == null)
                invWrapper = new ItemStorageItemHandler((IItemStorage) this);

            return (T) invWrapper;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        loadingFromNBT = true;
        super.readFromNBT(compound);

        if (this instanceof IStructurePart && compound.hasKey(CoreReferences.NBT.STRUCTURE)) {
            int dim;
            if (getWorld() == null)
                dim = compound.getInteger(CoreReferences.NBT.StructureData.DIMENSION);
            else
                dim = getWorld().provider.getDimension();

            ((IStructurePart) this).setStructure(StructureRegistry.getInstance().getStructure(dim, Coordinate3D.fromNBT(compound.getCompoundTag(CoreReferences.NBT.STRUCTURE))));
        }

        if (getState().requiresNBTStorage())
            this.getState().readFromNBTTagCompound(compound.getTag(CoreReferences.NBT.STATE));

        if (this instanceof IItemStorage)
            this.readInventoryFromCompound(compound.getTag(CoreReferences.NBT.INVENTORY));

        if (this instanceof IFluidContainingEntity)
            this.readFluidsFromCompound(compound.getTag(CoreReferences.NBT.FLUIDS));

        if (compound.hasKey(CoreReferences.NBT.NAME)) {
            this.name = compound.getString(CoreReferences.NBT.NAME);
        }
        loadingFromNBT = false;
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);

        if (this instanceof IStructurePart) {
            if (((IStructurePart) this).getStructure() != null) {
                compound.setInteger(CoreReferences.NBT.StructureData.DIMENSION, getWorld().provider.getDimension());
                compound.setTag(CoreReferences.NBT.STRUCTURE, ((IStructurePart) this).getStructure().getMasterLocation().toCompound());
            }
        }

        if (getState().requiresNBTStorage())
            compound.setTag(CoreReferences.NBT.STATE, this.getState().writeToNBTTagCompound());

        if (this instanceof IItemStorage)
            compound.setTag(CoreReferences.NBT.INVENTORY, this.writeInventoryToCompound());

        if (this instanceof IFluidContainingEntity)
            compound.setTag(CoreReferences.NBT.FLUIDS, this.writeFluidsToCompound());

        if (this.hasCustomName()) {
            compound.setString(CoreReferences.NBT.NAME, name);
        }

        return compound;
    }


    /**
     * Method to indicate that this TE has changed its data.
     */
    @Override
    public void markDirty () {
        if (isRemote())
            return;

        //Vanilla compatibility
        super.markDirty();
        getWorld().markChunkDirty(getPos(), this);

        //Notify the events system of a update.
        SmithsCore.getRegistry().getCommonBus().post(new TileEntityDataUpdatedEvent(this));
    }

    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound data = new NBTTagCompound();
        writeToNBT(data);

        return data;
    }

    @Override
    public void handleUpdateTag(@Nonnull NBTTagCompound tag) {
        readFromNBT(tag);
    }

    /**
     * Function used to convert the BlockPos of this TE into a Coordinate3D
     *
     * @return A Coordinate3D equivalent of the BlockPos of this TE.
     */
    @Nonnull
    public Coordinate3D getLocation() {
        return new Coordinate3D(this.pos);
    }

    @Nonnull
    protected abstract G getInitialGuiManager();

    @Override
    @Nonnull
    public G getManager() {
        return manager;
    }

    @Override
    public void setManager(@Nonnull G newManager) {
        this.manager = newManager;
    }

    @Nonnull
    protected abstract S getInitialState();

    /**
     * Getter for the current TE state.
     *
     * @return The current ITileEntityState.
     */
    @Nonnull
    public S getState() {
        return state;
    }

    /**
     * Method to set the new state on this instance of the TE
     *
     * @param state The new state.
     */
    public void setState(@Nonnull S state) {
        this.state = state;
        state.onStateCreated(this);
    }


    /**
     * Standard method to write the inventory data of this TE to the NBTCompound that stores this TE's Data.
     *
     * @return A NBTTagList with all stacks in the inventory.
     */
    @Nonnull
    protected NBTBase writeInventoryToCompound () {
        IItemStorage inventory = (IItemStorage) this;
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
    protected void readInventoryFromCompound(@Nonnull NBTBase inventoryCompound) {
        if (!( inventoryCompound instanceof NBTTagList ))
            throw new IllegalArgumentException("The given store type is not compatible with this TE!");

        IItemStorage inventory = (IItemStorage) this;
        NBTTagList inventoryList = (NBTTagList) inventoryCompound;

        ((IItemStorage) this).clearInventory();

        for (int i = 0; i < inventoryList.tagCount(); i++) {
            NBTTagCompound slotCompound = (NBTTagCompound) inventoryList.get(i);

            ItemStack newStack = new ItemStack(slotCompound.getCompoundTag(CoreReferences.NBT.InventoryData.STACKDATA));

            inventory.setInventorySlotContents(slotCompound.getInteger(CoreReferences.NBT.InventoryData.SLOTINDEX), newStack);
        }
    }


    /**
     * Standard method to write the fluid data of this TE to the NBTCompound that stores this TE's Data.
     *
     * @return A NBTTagList with all fluids in the Tile.
     */
    @Nonnull
    protected NBTBase writeFluidsToCompound () {
        IFluidContainingEntity fluidContainingEntity = (IFluidContainingEntity) this;

        NBTTagList fluidData = new NBTTagList();

        for (EnumFacing side : EnumFacing.values()) {
            IFluidTank tank = ((IFluidContainingEntity) this).getTankForSide(side);
            if (tank == null)
                continue;

            if (!(tank instanceof INBTSerializable))
                continue;

            NBTTagCompound tankCompound = new NBTTagCompound();
            tankCompound.setInteger(CoreReferences.NBT.SIDE, side.getIndex());
            tankCompound.setTag(CoreReferences.NBT.TANKCONTENTS, ((INBTSerializable) tank).serializeNBT());

            fluidData.appendTag(tankCompound);
        }

        IFluidTank tank = ((IFluidContainingEntity) this).getTankForSide(null);
        if (tank == null)
            return fluidData;

        if (!(tank instanceof INBTSerializable))
            return fluidData;

        NBTTagCompound tankCompound = new NBTTagCompound();
        tankCompound.setInteger(CoreReferences.NBT.SIDE, -1);
        tankCompound.setTag(CoreReferences.NBT.TANKCONTENTS, ((INBTSerializable) tank).serializeNBT());

        fluidData.appendTag(tankCompound);

        return fluidData;
    }

    /**
     * Standard method to read the fluids data of this TE from the NBTCompound that stores this TE's Data.
     *
     * @param inventoryCompound A NBTBase instance in the form of a TagList containing all the Data of the fluids in
     *                          this tileentity.
     */
    protected void readFluidsFromCompound(@Nonnull NBTBase inventoryCompound) {
        if (!( inventoryCompound instanceof NBTTagList ))
            throw new IllegalArgumentException("The given store type is not compatible with this TE!");

        IFluidContainingEntity fluidContainingEntity = (IFluidContainingEntity) this;

        NBTTagList inventoryList = (NBTTagList) inventoryCompound;
        for (int i = 0; i < inventoryList.tagCount(); i++) {
            NBTTagCompound tankCompound = inventoryList.getCompoundTagAt(i);

            if (tankCompound.getInteger(CoreReferences.NBT.SIDE) == -1) {
                IFluidTank tank = fluidContainingEntity.getTankForSide(null);
                if (!(tank instanceof INBTSerializable))
                    continue;

                ((INBTSerializable) tank).deserializeNBT(tankCompound.getTag(CoreReferences.NBT.TANKCONTENTS));
            } else {
                EnumFacing side = EnumFacing.values()[tankCompound.getInteger(CoreReferences.NBT.SIDE)];
                IFluidTank tank = fluidContainingEntity.getTankForSide(side);
                if (!(tank instanceof INBTSerializable))
                    continue;

                ((INBTSerializable) tank).deserializeNBT(tankCompound.getTag(CoreReferences.NBT.TANKCONTENTS));
            }
        }
    }

    /**
     * Method called by the synchronization system to send the data to the client.
     *
     * @param synchronizationCompound The NBTTagCompound to write your data to.
     *
     * @return A NBTTagCompound containing all the data required for the synchronization of this TE.
     */
    @Nonnull
    public NBTTagCompound writeToSynchronizationCompound (@Nonnull NBTTagCompound synchronizationCompound) {
        return this.writeToNBT(synchronizationCompound);
    }

    /**
     * Method called by the synchronization system to read the data from the Server.
     *
     * @param synchronizationCompound The NBTTagCompound to read your data from.
     */
    public void readFromSynchronizationCompound (@Nonnull NBTTagCompound synchronizationCompound) {
        this.readFromNBT(synchronizationCompound);
    }

    @Override
    public boolean isRemote () {
        return getWorld().isRemote;
    }

    public boolean hasCustomName() {
        return name != null && name.length() > 0;
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return new TextComponentString(name);
    }

    public void setDisplayName(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public boolean isLoadingFromNBT() {
        return loadingFromNBT;
    }
}
