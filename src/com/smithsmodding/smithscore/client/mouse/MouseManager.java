package com.smithsmodding.smithscore.client.mouse;

import com.smithsmodding.smithscore.util.client.gui.GuiHelper;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import javax.annotation.Nonnull;


/**
 * Created by marcf on 12/3/2015.
 */
public class MouseManager
{

    private Coordinate2D location = new Coordinate2D(0, 0);

    /**
     * Method to get the last known location of the mouse.
     *
     * @return The last known location of the mouse.
     */
    @Nonnull
    public Coordinate2D getLocation() {
        return location;
    }

    /**
     * Function called when the ForgeMouseEvent is fired.
     * Updates the location of the mouse.
     *
     * @param event The MouseEvent containing the data of the mouse at the current tick.
     */
    @SubscribeEvent
    public void onClientTickEvent(@Nonnull TickEvent.ClientTickEvent event)
    {
        if (GuiHelper.GUISCALE == 0)
            GuiHelper.calcScaleFactor();

        location = new Coordinate2D(org.lwjgl.input.Mouse.getEventX() / GuiHelper.GUISCALE, GuiHelper.DISPLAYHEIGHT - (Mouse.getEventY() / GuiHelper.GUISCALE));
    }
}
