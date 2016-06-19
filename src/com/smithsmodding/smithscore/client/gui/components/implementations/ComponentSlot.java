package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.management.StandardRenderManager;
import com.smithsmodding.smithscore.client.gui.state.SlotComponentState;
import com.smithsmodding.smithscore.util.client.Textures;
import com.smithsmodding.smithscore.util.client.color.Colors;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.client.gui.GuiHelper;
import com.smithsmodding.smithscore.util.client.gui.MultiComponentTexture;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import com.smithsmodding.smithscore.util.common.positioning.Plane;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;

/**
 * Created by Marc on 22.12.2015.
 */
public class ComponentSlot extends CoreComponent {
    private MinecraftColor color;

    public ComponentSlot (String uniqueID, SlotComponentState state, IGUIBasedComponentHost parent, Slot connectedSlot, MinecraftColor color) {
        this(uniqueID, state, parent, new Coordinate2D(connectedSlot.xDisplayPosition - 1, connectedSlot.yDisplayPosition - 1 - parent.getRootGuiObject().getTabManager().getDisplayAreaVerticalOffset()), color);
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
            GuiHelper.drawColoredRect(new Plane(1,1,16,16), 0, Colors.General.ELECTRICBLUE);
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
