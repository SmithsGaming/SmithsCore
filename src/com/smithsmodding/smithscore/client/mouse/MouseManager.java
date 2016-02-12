package com.smithsmodding.smithscore.client.mouse;

import com.smithsmodding.smithscore.util.client.gui.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.java.games.input.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import org.lwjgl.input.Mouse;


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
    public void onClientTickEvent(TickEvent.ClientTickEvent event)
    {
        if (GuiHelper.GUISCALE == 0)
            GuiHelper.calcScaleFactor();

        location = new Coordinate2D(org.lwjgl.input.Mouse.getEventX() / GuiHelper.GUISCALE, GuiHelper.DISPLAYHEIGHT - (Mouse.getEventY() / GuiHelper.GUISCALE));
    }
}
