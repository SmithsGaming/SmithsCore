package com.smithsmodding.smithscore.client.handlers.gui;

import com.smithsmodding.smithscore.*;
import com.smithsmodding.smithscore.client.events.gui.*;
import com.smithsmodding.smithscore.common.inventory.*;
import net.minecraftforge.fml.common.eventhandler.*;

/**
 * Created by Marc on 26.01.2016.
 */
public class GuiInputEventHandler {

    @SubscribeEvent
    public void onInputOnClient (GuiInputEvent event) {
        if (!( event.getPlayer().openContainer instanceof ContainerSmithsCore )) {
            SmithsCore.getLogger().error("Cannot process input as the open container on player: " + event.getPlayer().getDisplayName().toString() + " is not a SmithsCore compatible Container!");
            return;
        }

        if (event.getTypes() == GuiInputEvent.InputTypes.TABCHANGED) {
            ( (ContainerSmithsCore) event.getPlayer().openContainer ).onTabChanged(event.getInput());
        }
    }
}

