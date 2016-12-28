package com.smithsmodding.smithscore.client.handlers.gui;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.client.events.gui.GuiInputEvent;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 26.01.2016.
 */
public class GuiInputEventHandler {

    @SubscribeEvent
    public void onInputOnClient (@Nonnull GuiInputEvent event) {
        if (!( event.getPlayer().openContainer instanceof ContainerSmithsCore )) {
            SmithsCore.getLogger().error("Cannot process input as the open container on player: " + event.getPlayer().getDisplayName().toString() + " is not a SmithsCore compatible Container!");
            return;
        }

        if (event.getTypes() == GuiInputEvent.InputTypes.TABCHANGED) {
            ( (ContainerSmithsCore) event.getPlayer().openContainer ).onTabChanged(event.getInput());
            return;
        }

        ((ContainerSmithsCore) event.getPlayer().openContainer).onInput(event.getTypes(), event.getComponentID(), event.getInput());
    }
}

