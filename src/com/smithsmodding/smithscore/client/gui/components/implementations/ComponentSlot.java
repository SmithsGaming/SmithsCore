package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.management.StandardRenderManager;
import com.smithsmodding.smithscore.client.gui.state.SlotComponentState;
import com.smithsmodding.smithscore.util.client.Textures;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.client.gui.GuiHelper;
import com.smithsmodding.smithscore.util.client.gui.MultiComponentTexture;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Slot;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 22.12.2015.
 */
public class ComponentSlot extends CoreComponent {
    private MinecraftColor color;

    public ComponentSlot (@Nonnull String uniqueID, @Nonnull SlotComponentState state, @Nonnull IGUIBasedComponentHost parent, @Nonnull Slot connectedSlot, @Nonnull MinecraftColor color) {
        this(uniqueID, state, parent, new Coordinate2D(connectedSlot.xPos - 1, connectedSlot.yPos - 1 - parent.getRootGuiObject().getDefaultDisplayVerticalOffset()), color);
    }

    public ComponentSlot (@Nonnull String uniqueID, @Nonnull SlotComponentState state, @Nonnull IGUIBasedComponentHost parent, @Nonnull Coordinate2D rootAnchorPixel, @Nonnull MinecraftColor color) {
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
            GuiHelper.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
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
