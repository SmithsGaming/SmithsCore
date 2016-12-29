package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.events.gui.GuiInputEvent;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.state.IGUIComponentState;
import com.smithsmodding.smithscore.client.gui.state.TextboxComponentState;
import com.smithsmodding.smithscore.util.client.CustomResource;
import com.smithsmodding.smithscore.util.client.gui.GuiHelper;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import com.smithsmodding.smithscore.util.common.positioning.Plane;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class ComponentTextbox extends GuiTextField implements IGUIComponent {
    protected String uniqueID;
    protected TextboxComponentState state;
    protected IGUIBasedComponentHost parent;
    protected Coordinate2D rootAnchorPixel;
    protected int width;
    protected int height;

    protected CustomResource secondaryBackground;

    public ComponentTextbox(@Nonnull String uniqueID, @Nonnull TextboxComponentState state, @Nonnull IGUIBasedComponentHost parent, @Nonnull Coordinate2D rootAnchorPixel, int width, int height) {
        super(state.getId(), state.getFontRendererInstance(), 0, 0, width, height);
        this.uniqueID = uniqueID;
        this.state = state;
        this.parent = parent;
        this.rootAnchorPixel = rootAnchorPixel;
        this.width = width;
        this.height = height;

        getState().setComponent(this);
        setText(state.getText());
    }

    @Nonnull
    @Override
    public String getID() {
        return uniqueID;
    }

    @Nonnull
    @Override
    public IGUIComponentState getState() {
        return state;
    }

    @Nonnull
    @Override
    public IGUIBasedComponentHost getComponentHost() {
        return parent;
    }

    @Nonnull
    @Override
    public Coordinate2D getGlobalCoordinate() {
        return parent.getGlobalCoordinate().getTranslatedCoordinate(getLocalCoordinate());
    }

    @Nonnull
    @Override
    public Coordinate2D getLocalCoordinate() {
        return rootAnchorPixel;
    }

    @Nonnull
    @Override
    public Plane getAreaOccupiedByComponent() {
        return new Plane(getGlobalCoordinate(), width, height);
    }

    @Nonnull
    @Override
    public Plane getSize() {
        return new Plane(0, 0, width, height);
    }

    @Override
    public void update(int mouseX, int mouseY, float partialTickTime) {
        //NOOP
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        this.drawTextBox();
        GlStateManager.enableLighting();
        GlStateManager.enableBlend();

        if (secondaryBackground == null) return;

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GuiHelper.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GuiHelper.drawTexturedModelRectFromIcon(0, 0, 0, secondaryBackground.getIcon(), secondaryBackground.getIcon().getIconWidth(), secondaryBackground.getIcon().getIconHeight());
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        //NOOP
    }

    @Override
    public boolean handleMouseClickedInside(int relativeMouseX, int relativeMouseY, int mouseButton) {
        this.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
        return getSize().ContainsCoordinate(relativeMouseX, relativeMouseY);
    }

    @Override
    public boolean handleMouseClickedOutside(int relativeMouseX, int relativeMouseY, int mouseButton) {
        this.mouseClicked(relativeMouseX, relativeMouseY, mouseButton);
        return !getSize().ContainsCoordinate(relativeMouseX, relativeMouseY);
    }

    @Override
    public boolean requiresForcedMouseInput() {
        return true;
    }

    @Override
    public boolean handleKeyTyped(char key, int keyCode) {
        boolean result = this.textboxKeyTyped(key, keyCode);

        if (!state.getText().equals(getText())) {
            state.setText(getText());
            GuiInputEvent event = new GuiInputEvent(GuiInputEvent.InputTypes.TEXTCHANGED, uniqueID, getText());
            event.PostClient();
        }

        return result;
    }

    @Nonnull
    @Override
    public ArrayList<String> getToolTipContent() {
        return new ArrayList<>();
    }
}
