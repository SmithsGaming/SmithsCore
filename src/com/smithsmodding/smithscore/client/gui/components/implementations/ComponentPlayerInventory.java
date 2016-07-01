package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.GuiContainerSmithsCore;
import com.smithsmodding.smithscore.client.gui.components.core.ComponentConnectionType;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.management.IGUIManager;
import com.smithsmodding.smithscore.client.gui.state.CoreComponentState;
import com.smithsmodding.smithscore.client.gui.state.IGUIComponentState;
import com.smithsmodding.smithscore.client.gui.state.SlotComponentState;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import com.smithsmodding.smithscore.util.common.positioning.Plane;
import net.minecraft.inventory.IInventory;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Marc on 22.12.2015.
 */
public class ComponentPlayerInventory implements IGUIBasedComponentHost {

    public static int WIDTH = ContainerSmithsCore.PLAYER_INVENTORY_COLUMNS * 18 + 2 * 7;
    public static int HEIGHT = ( ContainerSmithsCore.PLAYER_INVENTORY_ROWS + 1 ) * 18 + 5 + 2 * 7;

    private String uniqueID;
    private LinkedHashMap<String, IGUIComponent> componentHashMap = new LinkedHashMap<String, IGUIComponent>();

    private IGUIBasedComponentHost parent;
    private CoreComponentState state = new CoreComponentState(this);

    private Coordinate2D rootAnchorPixel;
    private int width;
    private int height;

    private MinecraftColor color;

    private IInventory playerInventory;

    private ComponentConnectionType connectionType;

    public ComponentPlayerInventory (String uniqueID, IGUIBasedComponentHost parent, Coordinate2D rootAnchorPixel, MinecraftColor color, IInventory playerInventory, ComponentConnectionType connectionType) {
        this.uniqueID = uniqueID;
        this.parent = parent;
        this.rootAnchorPixel = rootAnchorPixel;
        this.color = color;
        this.playerInventory = playerInventory;

        this.width = WIDTH;
        this.height = HEIGHT;

        this.connectionType = connectionType;
    }

    @Override
    public void registerComponents (IGUIBasedComponentHost host) {

        ComponentBorder.CornerTypes topLeft = ComponentBorder.CornerTypes.Inwards;
        ComponentBorder.CornerTypes topRight = ComponentBorder.CornerTypes.Inwards;
        ComponentBorder.CornerTypes lowerRight = ComponentBorder.CornerTypes.Inwards;
        ComponentBorder.CornerTypes lowerLeft = ComponentBorder.CornerTypes.Inwards;

        switch (connectionType) {
            case BELOWDIRECTCONNECT:
                topLeft = ComponentBorder.CornerTypes.StraightVertical;
                topRight = ComponentBorder.CornerTypes.StraightVertical;
                break;
            case BELOWSMALLER:
                topLeft = ComponentBorder.CornerTypes.Outwards;
                topRight = ComponentBorder.CornerTypes.Outwards;
                break;
            case BELOWBIGGER:
                topLeft = ComponentBorder.CornerTypes.Inwards;
                topRight = ComponentBorder.CornerTypes.Inwards;
                break;
            case ABOVEDIRECTCONNECT:
                lowerLeft = ComponentBorder.CornerTypes.StraightVertical;
                lowerRight = ComponentBorder.CornerTypes.StraightVertical;
                break;
            case ABOVESMALLER:
                lowerLeft = ComponentBorder.CornerTypes.Outwards;
                lowerRight = ComponentBorder.CornerTypes.Outwards;
                break;
            case ABOVEBIGGER:
                lowerLeft = ComponentBorder.CornerTypes.Inwards;
                lowerRight = ComponentBorder.CornerTypes.Inwards;
                break;
            case RIGHTDIRECTCONNECT:
                topLeft = ComponentBorder.CornerTypes.StraightHorizontal;
                lowerLeft = ComponentBorder.CornerTypes.StraightHorizontal;
                break;
            case RIGHTSMALLER:
                topLeft = ComponentBorder.CornerTypes.Outwards;
                lowerLeft = ComponentBorder.CornerTypes.Outwards;
                break;
            case RIGHTBIGGER:
                topLeft = ComponentBorder.CornerTypes.Inwards;
                lowerLeft = ComponentBorder.CornerTypes.Inwards;
                break;
            case LEFTDIRECTCONNECT:
                topRight = ComponentBorder.CornerTypes.StraightHorizontal;
                lowerRight = ComponentBorder.CornerTypes.StraightHorizontal;
                break;
            case LEFTSMALLER:
                topRight = ComponentBorder.CornerTypes.Outwards;
                lowerRight = ComponentBorder.CornerTypes.Outwards;
                break;
            case LEFTBIGGER:
                topRight = ComponentBorder.CornerTypes.Inwards;
                lowerRight = ComponentBorder.CornerTypes.Inwards;
                break;
        }

        registerNewComponent(new ComponentBorder(uniqueID + ".Background", this, new Coordinate2D(0, 0), width, height, color, topLeft, topRight, lowerRight, lowerLeft));

        for (int r = 0; r < ContainerSmithsCore.PLAYER_INVENTORY_ROWS; r++) {
            for (int c = 0; c < ContainerSmithsCore.PLAYER_INVENTORY_COLUMNS; c++) {
                registerNewComponent(new ComponentSlot(uniqueID + ".Slot.inventory." + ( r * 9 + c ), new SlotComponentState(null, ( r * 9 + c ), playerInventory, null), this, new Coordinate2D(c * 18 + 7, r * 18 + 7), color));
            }
        }

        for (int c = 0; c < ContainerSmithsCore.PLAYER_INVENTORY_COLUMNS; c++) {
            registerNewComponent(new ComponentSlot(uniqueID + ".Slot.Hotbar." + ( c ), new SlotComponentState(null, ( 3 * 9 + c ), playerInventory, null), this, new Coordinate2D(c * 18 + 7, 3 * 18 + 5 + 7), color));
        }
    }

    @Override
    public void registerNewComponent (IGUIComponent component) {
        if (component instanceof IGUIBasedComponentHost)
            ( (IGUIBasedComponentHost) component ).registerNewComponent(component);

        componentHashMap.put(component.getID(), component);
    }

    @Override
    public GuiContainerSmithsCore getRootGuiObject () {
        return parent.getRootGuiObject();
    }

    @Override
    public IGUIManager getRootManager () {
        return parent.getManager();
    }

    @Override
    public LinkedHashMap<String, IGUIComponent> getAllComponents () {
        return componentHashMap;
    }

    public IGUIComponent getComponentByID (String uniqueUIID) {
        if (getID().equals(uniqueUIID))
            return this;

        if (getAllComponents().get(uniqueUIID) != null)
            return getAllComponents().get(uniqueUIID);

        for (IGUIComponent childComponent : getAllComponents().values()) {
            if (childComponent instanceof IGUIBasedComponentHost) {
                IGUIComponent foundComponent = ( (IGUIBasedComponentHost) childComponent ).getComponentByID(uniqueUIID);

                if (foundComponent != null)
                    return foundComponent;
            }
        }

        return null;
    }


    @Override
    public String getID () {
        return uniqueID;
    }

    @Override
    public IGUIComponentState getState () {
        return state;
    }

    @Override
    public IGUIBasedComponentHost getComponentHost() {
        return parent.getComponentHost();
    }

    @Override
    public Coordinate2D getGlobalCoordinate () {
        return parent.getGlobalCoordinate().getTranslatedCoordinate(getLocalCoordinate());
    }

    @Override
    public Coordinate2D getLocalCoordinate () {
        return rootAnchorPixel;
    }

    @Override
    public Plane getAreaOccupiedByComponent () {
        return new Plane(getGlobalCoordinate(), width, height);
    }

    @Override
    public Plane getSize () {
        return new Plane(0, 0, width, height);
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
    public void drawBackground (int mouseX, int mouseY) {
        // Under normal use this method is not called as the rendermanager takes care of SubComponents and their rendering
    }

    @Override
    public void drawForeground (int mouseX, int mouseY) {
        // Under normal use this method is not called as the rendermanager takes care of SubComponents and their rendering
    }

    @Override
    public boolean handleMouseClickedInside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        return false;
    }

    @Override
    public boolean handleMouseClickedOutside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        return false;
    }

    @Override
    public boolean requiresForcedMouseInput () {
        return false;
    }

    @Override
    public boolean handleKeyTyped(char key, int keyCode) {
        return false;
    }

    @Override
    public ArrayList<String> getToolTipContent () {
        return new ArrayList<String>();
    }

    @Override
    public IGUIManager getManager () {
        return parent.getManager();
    }

    @Override
    public void setManager (IGUIManager newManager) {
        parent.setManager(newManager);
    }
}
