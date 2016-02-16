package com.smithsmodding.smithscore.common.slots;

import com.smithsmodding.smithscore.common.tileentity.Capabilities.*;
import net.minecraft.inventory.*;

/**
 * Created by Marc on 15.02.2016.
 */
public class SlotSmithsCore extends Slot {

    public SlotSmithsCore (ISmithsCoreItemHandler inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn.getIInventoryWrapper(), index, xPosition, yPosition);
    }
}
