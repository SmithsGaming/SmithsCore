package com.SmithsModding.SmithsCore.Client.Handlers.GUI;

import com.SmithsModding.SmithsCore.Client.Registry.*;
import com.SmithsModding.SmithsCore.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;

/**
 * Created by marcf on 12/28/2015.
 */
public class ClientTickEventHandler
{

    /**
     * Method event handler for the render tick event. Is only fired on the Client side, so this code can safely cast the
     * current Registry to a ClientRegistry.
     *
     * @param event The RenderTickEvent fired by MinecraftForge.
     */
    @SubscribeEvent
    public void handleEvent(TickEvent.RenderTickEvent event)
    {
        ((ClientRegistry) SmithsCore.getRegistry()).setPartialTickTime(event.renderTickTime);
    }
}
