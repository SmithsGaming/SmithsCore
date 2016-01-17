package com.smithsmodding.smithscore.client.Mouse;

import com.smithsmodding.smithscore.util.Common.Postioning.Coordinate2D;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


/**
 * Created by marcf on 12/3/2015.
 */
public class MouseManager
{

    private Coordinate2D location;

    /**
     * Method to get the last known location of the mouse.
     *
     * @return The last known location of the mouse.
     */
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
    public void onMouseEvent(MouseEvent event)
    {
        location = new Coordinate2D(event.x, event.y);
    }
}
