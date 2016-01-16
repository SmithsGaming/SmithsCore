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

import java.util.*;

/**
 * Created by Marc on 22.12.2015.
 */
public class ComponentSlot extends CoreComponent {
    private MinecraftColor color;

    public ComponentSlot (String uniqueID, SlotComponentState state, IGUIBasedComponentHost parent, Slot connectedSlot, MinecraftColor color) {
        this(uniqueID, state, parent, new Coordinate2D(connectedSlot.xDisplayPosition - 1, connectedSlot.yDisplayPosition - 1), color);
    }

    public ComponentSlot (String uniqueID, SlotComponentState state, IGUIBasedComponentHost parent, Coordinate2D rootAnchorPixel, MinecraftColor color) {
        super(uniqueID, parent, state, rootAnchorPixel, 18, 18);

        this.color = color;
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
        GlStateManager.pushMatrix();

        StandardRenderManager.pushColorOnRenderStack(color);

        GuiHelper.drawRectangleStretched(new MultiComponentTexture(Textures.Gui.Basic.Slots.DEFAULT, Textures.Gui.Basic.Slots.DEFAULT.getWidth(), Textures.Gui.Basic.Slots.DEFAULT.getHeight(), 1, 1), 18, 18, new Coordinate2D(0, 0));

        SlotComponentState state = (SlotComponentState) getState();

        if (state.requiresHoloRendering() && state.getHolographicSprite() != null) {
            GuiHelper.drawTexturedModelRectFromIcon(1, 1, 0, state.getHolographicSprite(), 16, 16);
        }

        StandardRenderManager.popColorFromRenderStack();

        GlStateManager.popMatrix();
    }

    @Override
    public void drawForeground (int mouseX, int mouseY) {
        //NOOP
    }
}
