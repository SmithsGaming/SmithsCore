package com.SmithsModding.SmithsCore.Client.GUI.Components.Implementations;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Management.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Common.Inventory.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.client.gui.*;
import net.minecraft.inventory.*;

import java.util.*;

/**
 * Created by Marc on 22.12.2015.
 */
public class ComponentPlayerInventory implements IGUIBasedComponentHost {
    private String uniqueID;
    private HashMap<String, IGUIComponent> componentHashMap = new HashMap<String, IGUIComponent>();

    private IGUIBasedComponentHost parent;
    private CoreComponentState state = new CoreComponentState(this);

    private Coordinate2D rootAnchorPixel;
    private int width;
    private int height;

    private MinecraftColor color;

    private IInventory playerInventory;

    public ComponentPlayerInventory (String uniqueID, IGUIBasedComponentHost parent, Coordinate2D rootAnchorPixel, MinecraftColor color, IInventory playerInventory) {
        this.uniqueID = uniqueID;
        this.parent = parent;
        this.rootAnchorPixel = rootAnchorPixel;
        this.color = color;
        this.playerInventory = playerInventory;

        this.width = ContainerSmithsCore.PLAYER_INVENTORY_COLUMNS * 18 + 2 * 7;
        this.height = ( ContainerSmithsCore.PLAYER_INVENTORY_ROWS + 1 ) * 18 + 5 + 2 * 7;
    }

    @Override
    public void registerComponents (IGUIBasedComponentHost host) {
        registerNewComponent(new ComponentBorder(uniqueID + ".Background", parent, rootAnchorPixel, width, height, color, ComponentBorder.CornerTypes.Outwarts, ComponentBorder.CornerTypes.Outwarts, ComponentBorder.CornerTypes.Inwarts, ComponentBorder.CornerTypes.Inwarts));

        for (int r = 0; r < ContainerSmithsCore.PLAYER_INVENTORY_ROWS; r++) {
            for (int c = 0; c < ContainerSmithsCore.PLAYER_INVENTORY_COLUMNS; c++) {
                registerNewComponent(new ComponentSlot(uniqueID + ".Slot.Inventory." + ( r * 9 + c ), new SlotComponentState(null, ( r * 9 + c ), playerInventory, null), parent, new Coordinate2D(c * 18 + 7, r * 18 + 7), color));
            }
        }

        for (int c = 0; c < ContainerSmithsCore.PLAYER_INVENTORY_COLUMNS; c++) {
            registerNewComponent(new ComponentSlot(uniqueID + ".Slot.Hotbar." + ( c ), new SlotComponentState(null, ( 3 * 9 + c ), playerInventory, null), parent, new Coordinate2D(c * 18 + 7, 3 * 18 + 5 + 7), color));
        }
    }

    @Override
    public void registerNewComponent (IGUIComponent component) {
        if (component instanceof IGUIBasedComponentHost)
            ( (IGUIBasedComponentHost) component ).registerNewComponent(component);

        componentHashMap.put(component.getID(), component);
    }

    @Override
    public Gui getRootGuiObject () {
        return parent.getRootGuiObject();
    }

    @Override
    public IGUIManager getRootManager () {
        return parent.getManager();
    }

    @Override
    public HashMap<String, IGUIComponent> getAllComponents () {
        return componentHashMap;
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
    public IGUIBasedComponentHost getRootComponent () {
        return parent.getRootComponent();
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
    public void handleKeyTyped (char key) {

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
