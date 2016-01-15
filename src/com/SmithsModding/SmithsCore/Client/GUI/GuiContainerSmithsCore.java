/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Client.GUI;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Management.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Common.Inventory.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;

import java.io.*;
import java.util.*;

public abstract class GuiContainerSmithsCore extends GuiContainer implements IGUIBasedComponentHost, IGUIBasedLedgerHost{

    private boolean isInitialized = false;
    private StandardRenderManager renderer = new StandardRenderManager(this);
    private StandardLedgerManager ledgers = new StandardLedgerManager(this);
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
            registerLedgers(this);
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
        renderer.renderBackgroundComponent(this, false);
        renderer.renderToolTipComponent(this, mouseX - getLocalCoordinate().getXComponent(), mouseY - getLocalCoordinate().getYComponent());
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

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     *
     * @param mouseX
     * @param mouseY
     * @param mouseButton
     */
    @Override
    protected void mouseClicked (int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (requiresForcedMouseInput())
            this.handleMouseClickedOutside(mouseX - getLocalCoordinate().getXComponent(), mouseY - getLocalCoordinate().getYComponent(), mouseButton);

        this.handleMouseClickedInside(mouseX - getLocalCoordinate().getXComponent(), mouseY - getLocalCoordinate().getYComponent(), mouseButton);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     *
     * @param typedChar
     * @param keyCode
     */
    @Override
    protected void keyTyped (char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        this.handleKeyTyped(typedChar);
    }

    /**
     * Function called when a Key is typed.
     *
     * @param key The key that was typed.
     */
    @Override
    public void handleKeyTyped (char key) {
        for (IGUIComponent component : getLedgerManager().getLeftLedgers().values()) {
            component.handleKeyTyped(key);
        }

        for (IGUIComponent component : getLedgerManager().getRightLedgers().values()) {
            component.handleKeyTyped(key);
        }

        for (IGUIComponent component : componentHashMap.values()) {
            component.handleKeyTyped(key);
        }
    }

    @Override
    public ArrayList<String> getToolTipContent () {
        return new ArrayList<String>();
    }

    /**
     * Method to check if this function should capture all of the buttons pressed on the mouse regardless of the press
     * location was inside or outside of the Component.
     *
     * @return True when all the mouse clicks should be captured by this component.
     */
    @Override
    public boolean requiresForcedMouseInput () {
        for (IGUIComponent component : getLedgerManager().getLeftLedgers().values()) {
            if (component.requiresForcedMouseInput())
                return true;
        }

        for (IGUIComponent component : getLedgerManager().getRightLedgers().values()) {
            if (component.requiresForcedMouseInput())
                return true;
        }

        for (IGUIComponent component : componentHashMap.values()) {
            if (component.requiresForcedMouseInput())
                return true;
        }

        return false;
    }

    /**
     * Function called when the Mouse was clicked outside of this component. It is only called when the function
     * requiresForcedMouseInput() return true Either it should pass this function to its SubComponents (making sure that
     * it recalculates the location and checks if it is inside before hand, handle the Click them self or both.
     * <p/>
     * When this Component or one of its SubComponents handles the Click it should return True.
     *
     * @param relativeMouseX The relative (to the Coordinate returned by @see #getLocalCoordinate) X-Coordinate of the
     *                       mouseclick.
     * @param relativeMouseY The relative (to the Coordinate returned by @see #getLocalCoordinate) Y-Coordinate of the
     *                       mouseclick.
     * @param mouseButton    The 0-BasedIndex of the mouse button that was pressed.
     *
     * @return True when the click has been handled, false when it did not.
     */
    @Override
    public boolean handleMouseClickedOutside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        for (IGUIComponent component : getLedgerManager().getLeftLedgers().values()) {
            if (component.requiresForcedMouseInput()) {
                Coordinate2D location = component.getLocalCoordinate();
                component.handleMouseClickedOutside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton);
            }
        }

        for (IGUIComponent component : getLedgerManager().getRightLedgers().values()) {
            if (component.requiresForcedMouseInput()) {
                Coordinate2D location = component.getLocalCoordinate();
                component.handleMouseClickedOutside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton);
            }
        }

        for (IGUIComponent component : componentHashMap.values()) {
            if (component.requiresForcedMouseInput()) {
                Coordinate2D location = component.getLocalCoordinate();
                component.handleMouseClickedOutside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton);
            }
        }

        return false;
    }

    /**
     * Function called when the Mouse was clicked inside of this component. Either it should pass this function to its
     * SubComponents (making sure that it recalculates the location and checks if it is inside before hand, handle the
     * Click them self or both.
     * <p/>
     * When this Component or one of its SubComponents handles the Click it should return True.
     *
     * @param relativeMouseX The relative (to the Coordinate returned by @see #getLocalCoordinate) X-Coordinate of the
     *                       mouseclick.
     * @param relativeMouseY The relative (to the Coordinate returned by @see #getLocalCoordinate) Y-Coordinate of the
     *                       mouseclick.
     * @param mouseButton    The 0-BasedIndex of the mouse button that was pressed.
     *
     * @return True when the click has been handled, false when it did not.
     */
    @Override
    public boolean handleMouseClickedInside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        for (IGUIComponent component : componentHashMap.values()) {
            Coordinate2D location = component.getLocalCoordinate();
            Plane localOccupiedArea = component.getSize().Move(location.getXComponent(), location.getYComponent());

            if (!localOccupiedArea.ContainsCoordinate(relativeMouseX, relativeMouseY))
                continue;

            if (component.handleMouseClickedInside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton))
                return true;

        }

        for (IGUIComponent component : getLedgerManager().getLeftLedgers().values()) {
            Coordinate2D location = component.getLocalCoordinate();
            Plane localOccupiedArea = component.getSize().Move(location.getXComponent(), location.getYComponent());

            if (!localOccupiedArea.ContainsCoordinate(relativeMouseX, relativeMouseY))
                continue;

            if (component.handleMouseClickedInside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton))
                return true;
        }

        for (IGUIComponent component : getLedgerManager().getRightLedgers().values()) {
            Coordinate2D location = component.getLocalCoordinate();
            Plane localOccupiedArea = component.getSize().Move(location.getXComponent(), location.getYComponent());

            if (!localOccupiedArea.ContainsCoordinate(relativeMouseX, relativeMouseY))
                continue;

            if (component.handleMouseClickedInside(relativeMouseX - location.getXComponent(), relativeMouseY - location.getYComponent(), mouseButton))
                return true;
        }


        return true;
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
            area.IncludeCoordinate(new Plane(component.getLocalCoordinate(), component.getSize().getWidth(), component.getSize().getHeigth()));
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

    /**
     * A number bigger then 0 that describes the offset of the right side ledgers with the top left corner as origin
     *
     * @return THe right side ledger offset
     */
    @Override
    public int getRightLedgerOffSet() {
        return getSize().getWidth();
    }

    /**
     * A number bigger then 0 that describes the offset of the left side ledgers with the top left corner as origin
     *
     * @return THe right left ledger offset
     */
    @Override
    public int getLeftLedgerOffSet() {
        return 0;
    }

    @Override
    public GuiContainerSmithsCore getRootGuiObject () {
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

    @Override
    public void drawHoveringText (List<String> textLines, int x, int y, FontRenderer font) {
        super.drawHoveringText(textLines, x, y, font);
    }

    /**
     * Method used to retrieve the rendermanager of this GUI,
     *
     * @return The currently used StandardRenderManager.
     */
    public IRenderManager getRenderManager () {
        return renderer;
    }

    /**
     * Method used to register a new Component to this Host.
     *
     * @param ledger The new component.
     */
    @Override
    public void registerNewLedger (IGUILedger ledger) {
        if (ledger.getPrimarySide() == LedgerConnectionSide.LEFT) {
            getLedgerManager().registerLedgerLeftSide(ledger);
            return;
        }

        getLedgerManager().registerLedgerRightSide(ledger);
    }

    /**
     * Method to get the LedgerManager for this host.
     *
     * @return The LedgerManager of this host.
     */
    @Override
    public ILedgerManager getLedgerManager () {
        return ledgers;
    }
}
