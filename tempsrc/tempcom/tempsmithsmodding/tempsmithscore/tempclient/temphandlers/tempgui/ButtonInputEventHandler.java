package com.smithsmodding.smithscore.client.handlers.gui;

import com.smithsmodding.smithscore.client.events.gui.*;
import com.smithsmodding.smithscore.client.gui.*;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

/**
 * Created by Marc on 10.02.2016.
 */
public class ButtonInputEventHandler
{

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGuiInputEvent(GuiInputEvent event)
    {
        if (!(event.getTypes() == GuiInputEvent.InputTypes.BUTTONCLICKED))
            return;

        GuiContainerSmithsCore guiContainerSmithsCore = (GuiContainerSmithsCore) Minecraft.getMinecraft().currentScreen;
        ComponentButton button = (ComponentButton) guiContainerSmithsCore.getComponentByID(event.getComponentID());

        if (!(button.getComponentHost() instanceof ComponentScrollBar))
            return;

        if (!button.getID().endsWith(".Buttons.Up") || !button.getID().endsWith(".Buttons.ScrollDrag") || !button.getID().endsWith(".Buttons.Down"))
            return;

        ((ComponentScrollBar) button.getComponentHost()).onInternalButtonClick(button);

        event.setCanceled(true);
    }
}
