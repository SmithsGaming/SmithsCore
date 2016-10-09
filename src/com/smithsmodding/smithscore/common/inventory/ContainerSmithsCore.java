package com.smithsmodding.smithscore.common.inventory;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.client.events.gui.ContainerGuiClosedEvent;
import com.smithsmodding.smithscore.client.events.gui.ContainerGuiOpenedEvent;
import com.smithsmodding.smithscore.client.events.gui.GuiInputEvent;
import com.smithsmodding.smithscore.client.gui.management.IGUIManager;
import com.smithsmodding.smithscore.client.gui.management.RelayBasedGUIManager;
import com.smithsmodding.smithscore.util.common.helper.ItemStackHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 22.11.2015
 * 22:46
 *
 * Copyrighted according to Project specific license
 */
public abstract class ContainerSmithsCore extends Container implements IContainerHost {

    public static final int PLAYER_INVENTORY_ROWS = 3;
    public static final int PLAYER_INVENTORY_COLUMNS = 9;

    private IGUIManager manager;
    private IContainerHost host;

    private String containerID;

    private IItemStorage containerInventory;
    private IInventory playerInventory;

    public ContainerSmithsCore(String containerID, IContainerHost host, IItemStorage containerInventory, EntityPlayer playerMP) {
        this.containerID = containerID;
        this.host = host;
        this.manager = new RelayBasedGUIManager(host, this);
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
     * @param playerIn The player that closed the Container.
     */
    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);

        this.manager.onGUIClosed(playerIn.getUniqueID());

        if (this.host.isRemote())
            return;

        SmithsCore.getRegistry().getCommonBus().post(new ContainerGuiClosedEvent(playerIn, this));
    }

    /**
     * Getter for the Containers ID.
     * Used to identify the container over the network.
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
    public boolean isRemote() {
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

    public IItemStorage getContainerInventory() {
        return containerInventory;
    }

    public IInventory getPlayerInventory() {
        return playerInventory;
    }

    public void onTabChanged(String newActiveTabID) {
    }

    public void onInput(GuiInputEvent.InputTypes types, String componentID, String input) {
        if (types == GuiInputEvent.InputTypes.TABCHANGED) {
            this.onTabChanged(input);
            return;
        }

        getManager().onInput(types, componentID, input);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex) {
        ItemStack newItemStack = null;
        Slot slot = inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();
            if (slotIndex < containerInventory.getSizeInventory()) {
                if (!this.mergeItemStack(itemStack, containerInventory.getSizeInventory(), inventorySlots.size(), false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemStack, 0, containerInventory.getSizeInventory(), false)) {
                return null;
            }
            if (itemStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return newItemStack;
    }

    @Override
    protected boolean mergeItemStack(ItemStack itemStack, int slotMin, int slotMax, boolean ascending) {
        boolean slotFound = false;
        int currentSlotIndex = ascending ? slotMax - 1 : slotMin;
        Slot slot;
        ItemStack stackInSlot;
        if (itemStack.isStackable()) {
            while (itemStack.stackSize > 0 && (!ascending && currentSlotIndex < slotMax || ascending && currentSlotIndex >= slotMin)) {
                slot = this.inventorySlots.get(currentSlotIndex);
                stackInSlot = slot.getStack();
                if (slot.isItemValid(itemStack) && ItemStackHelper.equalsIgnoreStackSize(itemStack, stackInSlot)) {
                    int combinedStackSize = stackInSlot.stackSize + itemStack.stackSize;
                    int slotStackSizeLimit = Math.min(stackInSlot.getMaxStackSize(), slot.getSlotStackLimit());
                    if (combinedStackSize <= slotStackSizeLimit) {
                        itemStack.stackSize = 0;
                        stackInSlot.stackSize = combinedStackSize;
                        slot.onSlotChanged();
                        slotFound = true;
                    } else if (stackInSlot.stackSize < slotStackSizeLimit) {
                        itemStack.stackSize -= slotStackSizeLimit - stackInSlot.stackSize;
                        stackInSlot.stackSize = slotStackSizeLimit;
                        slot.onSlotChanged();
                        slotFound = true;
                    }
                }
                currentSlotIndex += ascending ? -1 : 1;
            }
        }
        if (itemStack.stackSize > 0) {
            currentSlotIndex = ascending ? slotMax - 1 : slotMin;
            while (!ascending && currentSlotIndex < slotMax || ascending && currentSlotIndex >= slotMin) {
                slot = this.inventorySlots.get(currentSlotIndex);
                stackInSlot = slot.getStack();
                if (slot.isItemValid(itemStack) && stackInSlot == null) {
                    slot.putStack(ItemStackHelper.cloneItemStack(itemStack, Math.min(itemStack.stackSize, slot.getSlotStackLimit())));
                    slot.onSlotChanged();
                    if (slot.getStack() != null) {
                        itemStack.stackSize -= slot.getStack().stackSize;
                        slotFound = true;
                    }
                    break;
                }
                currentSlotIndex += ascending ? -1 : 1;
            }
        }
        return slotFound;
    }
}
