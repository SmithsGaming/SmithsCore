package com.smithsmodding.smithscore.client.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import javax.annotation.Nonnull;

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
    void handleDebugInformation (@Nonnull RenderGameOverlayEvent.Text event, @Nonnull World worldIn, @Nonnull BlockPos pos);
}
