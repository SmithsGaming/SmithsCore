package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.util.client.color.*;
import com.smithsmodding.smithscore.util.common.positioning.*;
import net.minecraft.client.gui.*;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 12/28/2015.
 */
public class ComponentLabel extends CoreComponent {
    protected String displayedText;
    protected MinecraftColor color;
    private FontRenderer renderer;

    public ComponentLabel(@Nonnull String uniqueID, @Nonnull IGUIBasedComponentHost root, @Nonnull IGUIComponentState state, @Nonnull Coordinate2D localCoordinate, @Nonnull MinecraftColor color, @Nonnull FontRenderer renderer, @Nonnull String displayedText) {
        super(uniqueID, root, state, localCoordinate, renderer.getStringWidth(displayedText), renderer.FONT_HEIGHT);

        this.displayedText = displayedText;
        this.color = color;
        this.renderer = renderer;
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

    /**
     * Function used to draw this components background.
     * Usually this will incorporate all of the components visual objects.
     * A good example of a Component that only uses the drawBackground function is the
     * BackgroundComponent.
     *
     * @param mouseX The current X-Coordinate of the mouse
     * @param mouseY The current Y-Coordinate of the mouse
     */
    @Override
    public void drawBackground(int mouseX, int mouseY) {
        renderer.drawStringWithShadow(displayedText, 0,0, color.getRGB());
    }

    /**
     * Function used to draw this components foreground.
     * Usually this will incorporate very few of teh components visual Objects.
     * A good example of a Component that only uses the drawForeground function is the
     * GUIDescriptionLabel (The labels that describe thins like your inventory and the TE's inventory).
     *
     * @param mouseX The current X-Coordinate of the mouse
     * @param mouseY The current Y-Coordinate of the mouse
     */
    @Override
    public void drawForeground(int mouseX, int mouseY) {
        //NOOP
    }
}
