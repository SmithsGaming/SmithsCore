package com.SmithsModding.SmithsCore.Client.GUI.Components.Implementations;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Management.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import com.SmithsModding.SmithsCore.Util.Client.GUI.*;
import com.SmithsModding.SmithsCore.Util.Client.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.client.renderer.*;
import net.minecraft.inventory.*;

/**
 * Created by Marc on 22.12.2015.
 */
public class ComponentSlot implements IGUIComponent {
    private String uniqueID;
    private SlotComponentState state;
    private IGUIBasedComponentHost parent;

    private Coordinate2D rootAnchorPixel;
    private int width;
    private int height;

    private MinecraftColor color;

    public ComponentSlot (String uniqueID, SlotComponentState state, IGUIBasedComponentHost parent, Slot connectedSlot, MinecraftColor color) {
        this(uniqueID, state, parent, new Coordinate2D(connectedSlot.xDisplayPosition - 1, connectedSlot.yDisplayPosition - 1), color);
    }

    public ComponentSlot (String uniqueID, SlotComponentState state, IGUIBasedComponentHost parent, Coordinate2D rootAnchorPixel, MinecraftColor color) {
        this.uniqueID = uniqueID;

        this.state = state;
        this.state.setComponent(this);

        this.parent = parent;

        this.rootAnchorPixel = rootAnchorPixel;
        this.width = 18;
        this.height = 18;

        this.color = color;
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
        GlStateManager.pushMatrix();

        RenderManager.pushColorOnRenderStack(color);

        GuiHelper.drawRectangleStretched(new MultiComponentTexture(Textures.Gui.Basic.Slots.DEFAULT, Textures.Gui.Basic.Slots.DEFAULT.getWidth(), Textures.Gui.Basic.Slots.DEFAULT.getHeight(), 1, 1), 18, 18, new Coordinate2D(0, 0));

        if (state.requiresHoloRendering() && state.getHolographicSprite() != null) {
            GuiHelper.drawTexturedModelRectFromIcon(1, 1, 0, state.getHolographicSprite(), 16, 16);
        }

        RenderManager.popColorFromRenderStack();

        GlStateManager.popMatrix();
    }

    @Override
    public void drawForeground (int mouseX, int mouseY) {
        //NOOP
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
        return;
    }
}
