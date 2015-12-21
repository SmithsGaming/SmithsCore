/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Client.GUI;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Events.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Management.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Common.Inventory.*;
import com.SmithsModding.SmithsCore.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.client.*;

import java.util.*;

public abstract class GuiContainerSmithsCore extends GuiContainer implements IGUIBasedComponentHost{

    private boolean isInitialized = false;
    private RenderManager renderer = new RenderManager(this);
    private CoreGUIState state = new CoreGUIState(this);

    private String uniqueUIID;
    private HashMap<String, IGUIComponent> componentHashMap = new HashMap<String, IGUIComponent>();

    public GuiContainerSmithsCore(ContainerSmithsCore container) {
        super(container);

        uniqueUIID = container.getContainerID() + "-GUI";
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
        getManager().clearAllRegisteredComponents();
        SmithsCore.getRegistry().getCommonBus().post(new ContainerGuiClosedEvent(FMLClientHandler.instance().getClientPlayerEntity(), (ContainerSmithsCore) this.inventorySlots));

        super.onGuiClosed();
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    private void setIsInitialized(boolean isInitialized) {
        this.isInitialized = isInitialized;
    }

    @Override
    public void drawBackground (int mouseX, int mouseY) {
        renderer.renderBackgroundComponent(this);
    }

    @Override
    public void drawForeground (int mouseX, int mouseY) {
        renderer.renderForegroundComponent(this);
    }

    @Override
    public IGUIManager getManager () {
        return ( (ContainerSmithsCore) inventorySlots ).getManager();
    }

    @Override
    public void setManager (IGUIManager newManager) {
        ( (ContainerSmithsCore) inventorySlots ).setManager(newManager);
    }

    @Override
    public String getID () {
        return uniqueUIID;
    }

    @Override
    public IGUIComponentState getState () {
        return state;
    }

    @Override
    public IGUIBasedComponentHost getRootComponent () {
        return this;
    }

    @Override
    public void handleKeyTyped (char key) {
        SmithsCore.getLogger().error("Still to implement. KeyTyped: " + key);
    }

    @Override
    public boolean requiresForcedMouseInput () {
        for (IGUIComponent component : componentHashMap.values())
            if (component.requiresForcedMouseInput())
                return true;

        return false;
    }

    @Override
    public boolean handlMouseClickedOutside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        for (IGUIComponent component : componentHashMap.values())
            if (component.requiresForcedMouseInput())
                component.handlMouseClickedOutside(relativeMouseX, relativeMouseY, mouseButton);

        return true;
    }

    @Override
    public boolean handlMouseClickedInside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        for (IGUIComponent component : componentHashMap.values())
            if (component.handlMouseClickedInside(relativeMouseX, relativeMouseY, mouseButton))
                return true;

        return false;
    }

    @Override
    public Plane getAreaOccupiedByComponent () {
        return new Plane(guiLeft, guiTop, width, height);
    }

    @Override
    public Coordinate2D getComponentRootAnchorPixel () {
        return new Coordinate2D(guiLeft, guiTop);
    }

    @Override
    public HashMap<String, IGUIComponent> getAllComponents () {
        return componentHashMap;
    }

    @Override
    public IGUIManager getRootManager () {
        return ( (ContainerSmithsCore) inventorySlots ).getManager();
    }

    @Override
    public Gui getRootGuiObject () {
        return this;
    }
}
