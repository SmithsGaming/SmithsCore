package com.SmithsModding.SmithsCore.Client.Block;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.*;

/**
 * Created by Marc on 03.01.2016.
 */
public interface ICustomDebugInformationBlock {
    /**
     * Method to handle displaying or removing of additional information on the F3 Screen.
     *
     * @param event   The event with the displayed data.
     * @param worldIn The world
     * @param pos     Position of the block the player is looking at.
     */
    void handleDebugInformation (RenderGameOverlayEvent.Text event, World worldIn, BlockPos pos);
}
