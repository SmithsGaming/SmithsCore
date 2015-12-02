/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Client.GUI;

import com.SmithsModding.SmithsCore.Client.GUI.Events.ContainerGuiClosedEvent;
import com.SmithsModding.SmithsCore.Client.GUI.Events.ContainerGuiOpenedEvent;
import com.SmithsModding.SmithsCore.Client.GUI.Host.IGUIBasedComponentHost;
import com.SmithsModding.SmithsCore.Common.Inventory.ContainerSmithsCore;
import com.SmithsModding.SmithsCore.Common.Inventory.IContainerHost;
import com.SmithsModding.SmithsCore.SmithsCore;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.client.FMLClientHandler;

public abstract class GuiContainerSmithsCore extends GuiContainer implements IGUIBasedComponentHost{

    private boolean isInitialized = false;

    public GuiContainerSmithsCore(ContainerSmithsCore container) {
        super(container);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    @Override
    public void initGui()
    {
        super.initGui();

        if (!isInitialized)
        {
            SmithsCore.getRegistry().getCommonBus().post(new ContainerGuiOpenedEvent(FMLClientHandler.instance().getClientPlayerEntity(), (ContainerSmithsCore) this.inventorySlots));
            registerComponents(this);
        }

        setIsInitialized(true);
    }



    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    @Override
    public void onGuiClosed() {
        SmithsCore.getRegistry().getCommonBus().post(new ContainerGuiClosedEvent(FMLClientHandler.instance().getClientPlayerEntity(), (ContainerSmithsCore) this.inventorySlots));

        super.onGuiClosed();
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    private void setIsInitialized(boolean isInitialized) {
        this.isInitialized = isInitialized;
    }
}
