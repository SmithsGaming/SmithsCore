package com.smithsmodding.smithscore.common.inventory.slot;

import com.smithsmodding.smithscore.common.inventory.IItemStorage;
import net.minecraft.inventory.Slot;

/**
 * Author Orion (Created on: 20.06.2016)
 */
public class SlotSmithsCore extends Slot {
    public SlotSmithsCore(IItemStorage inventoryIn, int index, int xPosition, int yPosition) {
        super(new IItemStorage.IInventoryWrapper(inventoryIn), index, xPosition, yPosition);
    }
}
