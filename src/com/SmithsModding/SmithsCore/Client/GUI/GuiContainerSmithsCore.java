/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Client.GUI;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Management.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Common.Inventory.*;
import com.SmithsModding.SmithsCore.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;

import java.util.*;

public abstract class GuiContainerSmithsCore extends GuiContainer implements IGUIBasedComponentHost, IGUIBasedLedgerHost{

    private boolean isInitialized = false;
    private RenderManager renderer = new RenderManager(this);
    private CoreComponentState state = new CoreComponentState(this);

    private String uniqueUIID;
    private LinkedHashMap<String, IGUIComponent> componentHashMap = new LinkedHashMap<String, IGUIComponent>();

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
        if (!isInitialized)
        {
            registerComponents(this);
        }

        Plane areaWithComponents = getSize();
        this.xSize = areaWithComponents.getWidth();
        this.ySize = areaWithComponents.getHeigth();

        super.initGui();

        setIsInitialized(true);
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
        //renderer.renderForegroundComponent(this);
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
    public IGUIBasedComponentHost getComponentHost() {
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
    public boolean handleMouseClickedOutside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        for (IGUIComponent component : componentHashMap.values())
            if (component.requiresForcedMouseInput())
                component.handleMouseClickedOutside(relativeMouseX, relativeMouseY, mouseButton);

        return true;
    }

    @Override
    public boolean handleMouseClickedInside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        for (IGUIComponent component : componentHashMap.values())
            if (component.handleMouseClickedInside(relativeMouseX, relativeMouseY, mouseButton))
                return true;

        return false;
    }

    @Override
    public Coordinate2D getLocalCoordinate () {
        return new Coordinate2D(guiLeft, guiTop);
    }

    @Override
    public Plane getAreaOccupiedByComponent () {
        Plane size = getSize();
        return new Plane(getGlobalCoordinate(), size.getWidth(), size.getHeigth());
    }

    @Override
    public Plane getSize () {
        Plane area = new Plane(0, 0, 0, 0);

        for (IGUIComponent component : getAllComponents().values()) {
            area.IncludeCoordinate(component.getSize());
        }

        return area;
    }

    /**
     * Method gets called before the component gets rendered, allows for animations to calculate through.
     *
     * @param mouseX          The X-Coordinate of the mouse.
     * @param mouseY          The Y-Coordinate of the mouse.
     * @param partialTickTime The partial tick time, used to calculate fluent animations.
     */
    @Override
    public void update(int mouseX, int mouseY, float partialTickTime) {
        //NOOP
    }

    @Override
    public LinkedHashMap<String, IGUIComponent> getAllComponents () {
        return componentHashMap;
    }

    @Override
    public Coordinate2D getGlobalCoordinate () {
        return getLocalCoordinate();
    }

    @Override
    public void registerNewComponent (IGUIComponent component) {
        if (component instanceof IGUIBasedComponentHost)
            ( (IGUIBasedComponentHost) component ).registerComponents((IGUIBasedComponentHost) component);

        componentHashMap.put(component.getID(), component);
    }

    @Override
    public IGUIManager getRootManager () {
        return ( (ContainerSmithsCore) inventorySlots ).getManager();
    }

    @Override
    public Gui getRootGuiObject () {
        return this;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer (float partialTicks, int mouseX, int mouseY) {
        this.drawBackground(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer (int mouseX, int mouseY) {
        this.drawForeground(mouseX, mouseY);
    }
}
