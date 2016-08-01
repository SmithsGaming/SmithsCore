package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.hosts.IContentAreaHost;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.management.IGUIManager;
import com.smithsmodding.smithscore.client.gui.management.IRenderManager;
import com.smithsmodding.smithscore.client.gui.state.IGUIComponentState;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import com.smithsmodding.smithscore.util.common.positioning.Plane;
import net.minecraft.client.gui.FontRenderer;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Marc on 11.02.2016.
 */
public class ComponentContentArea extends CoreComponent implements IGUIBasedComponentHost {

    private IContentAreaHost contentHost;
    private LinkedHashMap<String, IGUIComponent> componentHashMap = new LinkedHashMap<String, IGUIComponent>();

    public ComponentContentArea (String uniqueID, IContentAreaHost parent, IGUIComponentState state, Coordinate2D rootAnchorPixel, int width, int height) {
        super(uniqueID, parent, state, rootAnchorPixel, width, height);

        this.contentHost = (IContentAreaHost) this.parent;
    }

    @Override
    public Plane getSize () {
        Plane area = new Plane(0, 0, 0, 0);

        for (IGUIComponent component : getAllComponents().values()) {
            area.IncludeCoordinate(new Plane(component.getLocalCoordinate(), component.getSize().getWidth(), component.getSize().getHeigth()));
        }

        return area;
    }

    @Override
    public void update (int mouseX, int mouseY, float partialTickTime) {
        //NOOP
    }

    @Override
    public void drawBackground (int mouseX, int mouseY) {
        //NOOP
    }

    @Override
    public void drawForeground (int mouseX, int mouseY) {
        //NOOP
    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents (IGUIBasedComponentHost host) {
        contentHost.registerContentComponents(this);
    }

    /**
     * Method used to register a new Component to this host.
     *
     * @param component The new component.
     */
    @Override
    public void registerNewComponent (IGUIComponent component) {
        componentHashMap.put(component.getID(), component);
    }

    /**
     * Method to get the Root gui Object that this Component is part of.
     *
     * @return The gui that this component is part of.
     */
    @Override
    public IGUIBasedComponentHost getRootGuiObject() {
        return parent.getRootGuiObject();
    }

    /**
     * Method to get the gui Roots Manager.
     *
     * @return The Manager that is at the root for the gui Tree.
     */
    @Override
    public IGUIManager getRootManager () {
        return parent.getRootManager();
    }

    /**
     * Function to get all the components registered to this host.
     *
     * @return A ID to Component map that holds all the components (but not their SubComponents) of this host.
     */
    @Override
    public LinkedHashMap<String, IGUIComponent> getAllComponents () {
        return componentHashMap;
    }

    /**
     * Method for outside systems to retrieve a UI Component based of its ID.
     *
     * @param uniqueUIID The uniqueUIID that is being searched for.
     *
     * @return A IGUIComponent with then given ID or null if no child components exists with that ID.
     */
    @Override
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
    public void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font) {
        getComponentHost().drawHoveringText(textLines, x, y, font);
    }

    @Override
    public IRenderManager getRenderManager() {
        return getComponentHost().getRenderManager();
    }

    @Override
    public int getDefaultDisplayVerticalOffset() {
        return getComponentHost().getDefaultDisplayVerticalOffset();
    }

    /**
     * Function to get the IGUIManager.
     *
     * @return Returns the current GUIManager.
     */
    @Override
    public IGUIManager getManager () {
        return parent.getManager();
    }

    /**
     * Function to set the IGUIManager
     *
     * @param newManager THe new IGUIManager.
     */
    @Override
    public void setManager (IGUIManager newManager) {
        parent.setManager(newManager);
    }
}
