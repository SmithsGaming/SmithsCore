package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.components.core.ComponentConnectionType;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.management.IGUIManager;
import com.smithsmodding.smithscore.client.gui.management.IRenderManager;
import com.smithsmodding.smithscore.client.gui.state.CoreComponentState;
import com.smithsmodding.smithscore.client.gui.state.IGUIComponentState;
import com.smithsmodding.smithscore.client.gui.state.SlotComponentState;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import com.smithsmodding.smithscore.util.common.positioning.Plane;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.IInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Marc on 22.12.2015.
 */
public class ComponentPlayerInventory implements IGUIBasedComponentHost {

    public static int WIDTH = ContainerSmithsCore.PLAYER_INVENTORY_COLUMNS * 18 + 2 * 7;
    public static int HEIGHT = ( ContainerSmithsCore.PLAYER_INVENTORY_ROWS + 1 ) * 18 + 5 + 2 * 7;

    private String uniqueID;
    @Nonnull
    private LinkedHashMap<String, IGUIComponent> componentHashMap = new LinkedHashMap<String, IGUIComponent>();

    private IGUIBasedComponentHost parent;
    @Nonnull
    private CoreComponentState state = new CoreComponentState(this);

    private Coordinate2D rootAnchorPixel;
    private int width;
    private int height;

    private MinecraftColor color;

    private IInventory playerInventory;

    private ComponentConnectionType connectionType;

    public ComponentPlayerInventory (@Nonnull String uniqueID, @Nonnull IGUIBasedComponentHost parent, @Nonnull Coordinate2D rootAnchorPixel, @Nonnull MinecraftColor color, @Nonnull IInventory playerInventory, ComponentConnectionType connectionType) {
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
    public void registerNewComponent (@Nonnull IGUIComponent component) {
        if (component instanceof IGUIBasedComponentHost)
            ( (IGUIBasedComponentHost) component ).registerNewComponent(component);

        componentHashMap.put(component.getID(), component);
    }

    @Override
    @Nonnull
    public IGUIBasedComponentHost getRootGuiObject() {
        return parent.getRootGuiObject();
    }

    @Override
    @Nonnull
    public IGUIManager getRootManager () {
        return parent.getManager();
    }

    @Nonnull
    @Override
    public LinkedHashMap<String, IGUIComponent> getAllComponents () {
        return componentHashMap;
    }

    @Nullable
    public IGUIComponent getComponentByID (@Nonnull String uniqueUIID) {
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
    public void drawHoveringText(@Nullable List<String> textLines, int x, int y, @Nonnull  FontRenderer font) {
        getComponentHost().drawHoveringText(textLines, x, y, font);
    }

    @Override
    @Nonnull
    public IRenderManager getRenderManager() {
        return getComponentHost().getRenderManager();
    }

    @Override
    public int getDefaultDisplayVerticalOffset() {
        return getComponentHost().getDefaultDisplayVerticalOffset();
    }


    @Nonnull
    @Override
    public String getID () {
        return uniqueID;
    }

    @Nonnull
    @Override
    public IGUIComponentState getState () {
        return state;
    }

    @Nonnull
    @Override
    public IGUIBasedComponentHost getComponentHost() {
        return parent.getComponentHost();
    }

    @Nonnull
    @Override
    public Coordinate2D getGlobalCoordinate () {
        return parent.getGlobalCoordinate().getTranslatedCoordinate(getLocalCoordinate());
    }

    @Nonnull
    @Override
    public Coordinate2D getLocalCoordinate () {
        return rootAnchorPixel;
    }

    @Nonnull
    @Override
    public Plane getAreaOccupiedByComponent () {
        return new Plane(getGlobalCoordinate(), width, height);
    }

    @Nonnull
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

    @Nonnull
    @Override
    public ArrayList<String> getToolTipContent () {
        return new ArrayList<String>();
    }

    @Override
    @Nonnull
    public IGUIManager getManager () {
        return parent.getManager();
    }

    @Override
    public void setManager (@Nonnull IGUIManager newManager) {
        parent.setManager(newManager);
    }
}
