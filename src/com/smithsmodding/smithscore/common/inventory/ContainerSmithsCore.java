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

import javax.annotation.Nonnull;

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

    public ContainerSmithsCore(@Nonnull String containerID, @Nonnull IContainerHost host, @Nonnull IItemStorage containerInventory, @Nonnull EntityPlayer playerMP) {
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
    public void onContainerClosed(@Nonnull EntityPlayer playerIn) {
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
    @Nonnull
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
    @Nonnull
    public IGUIManager getManager() {
        return manager;
    }

    /**
     * Function to set the IGUIManager
     *
     * @param newManager THe new IGUIManager.
     */
    @Override
    public void setManager(@Nonnull IGUIManager newManager) {
        manager = newManager;
    }

    @Nonnull
    public IItemStorage getContainerInventory() {
        return containerInventory;
    }

    @Nonnull
    public IInventory getPlayerInventory() {
        return playerInventory;
    }

    public void onTabChanged(@Nonnull String newActiveTabID) {
    }

    public void onInput(GuiInputEvent.InputTypes types, @Nonnull String componentID, @Nonnull String input) {
        if (types == GuiInputEvent.InputTypes.TABCHANGED) {
            this.onTabChanged(input);
            return;
        }

        getManager().onInput(types, componentID, input);
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer entityPlayer, int slotIndex) {
        ItemStack newItemStack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();
            if (slotIndex < containerInventory.getSizeInventory()) {
                if (!this.mergeItemStack(itemStack, containerInventory.getSizeInventory(), inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemStack, 0, containerInventory.getSizeInventory(), false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack.getCount() == 0 || itemStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return newItemStack;
    }

    @Override
    protected boolean mergeItemStack(@Nonnull ItemStack itemStack, int slotMin, int slotMax, boolean ascending) {
        boolean slotFound = false;
        int currentSlotIndex = ascending ? slotMax - 1 : slotMin;
        Slot slot;
        ItemStack stackInSlot;
        if (itemStack.isStackable()) {
            while (itemStack.getCount() > 0 && (!ascending && currentSlotIndex < slotMax || ascending && currentSlotIndex >= slotMin)) {
                slot = this.inventorySlots.get(currentSlotIndex);
                stackInSlot = slot.getStack();
                if (slot.isItemValid(itemStack) && ItemStackHelper.equalsIgnoreStackSize(itemStack, stackInSlot)) {
                    int combinedStackSize = stackInSlot.getCount() + itemStack.getCount();
                    int slotStackSizeLimit = Math.min(stackInSlot.getMaxStackSize(), slot.getSlotStackLimit());
                    if (combinedStackSize <= slotStackSizeLimit) {
                        itemStack.setCount(0);
                        stackInSlot.setCount(combinedStackSize);
                        slot.onSlotChanged();
                        slotFound = true;
                    } else if (stackInSlot.getCount() < slotStackSizeLimit) {
                        itemStack.shrink(slotStackSizeLimit - stackInSlot.getCount());
                        stackInSlot.setCount(slotStackSizeLimit);
                        slot.onSlotChanged();
                        slotFound = true;
                    }
                }
                currentSlotIndex += ascending ? -1 : 1;
            }
        }
        if (itemStack.getCount() > 0) {
            currentSlotIndex = ascending ? slotMax - 1 : slotMin;
            while (!ascending && currentSlotIndex < slotMax || ascending && currentSlotIndex >= slotMin) {
                slot = this.inventorySlots.get(currentSlotIndex);
                stackInSlot = slot.getStack();
                if (slot.isItemValid(itemStack) && stackInSlot.isEmpty()) {
                    slot.putStack(ItemStackHelper.cloneItemStack(itemStack, Math.min(itemStack.getCount(), slot.getSlotStackLimit())));
                    slot.onSlotChanged();
                    if (!slot.getStack().isEmpty()) {
                        itemStack.shrink(slot.getStack().getCount());
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
