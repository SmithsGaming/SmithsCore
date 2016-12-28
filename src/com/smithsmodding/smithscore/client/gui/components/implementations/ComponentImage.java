package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.state.IGUIComponentState;
import com.smithsmodding.smithscore.util.client.CustomResource;
import com.smithsmodding.smithscore.util.client.gui.GuiHelper;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 09.01.2016.
 */
public class ComponentImage extends CoreComponent {

    private CustomResource image;

    public ComponentImage (String uniqueID, IGUIComponentState state, IGUIBasedComponentHost root, Coordinate2D rootAnchorPixel, @Nonnull CustomResource image) {
        super(uniqueID, root, state, rootAnchorPixel, image.getWidth(), image.getHeight());

        this.image = image;
    }

    /**
     * Method gets called before the component gets rendered, allows for animations to calculate through.
     *
     * @param mouseX          The X-Coordinate of the mouse.
     * @param mouseY          The Y-Coordinate of the mouse.
     * @param partialTickTime The partial tick time, used to calculate fluent animations.
     */
    @Override
    public void update (int mouseX, int mouseY, float partialTickTime) {
        //NOOP
    }

    /**
     * Function used to draw this components background. Usually this will incorporate all of the components visual
     * objects. A good example of a Component that only uses the drawBackground function is the BackgroundComponent.
     *
     * @param mouseX The current X-Coordinate of the mouse
     * @param mouseY The current Y-Coordinate of the mouse
     */
    @Override
    public void drawBackground (int mouseX, int mouseY) {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GuiHelper.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GuiHelper.drawTexturedModelRectFromIcon(0, 0, 0, image.getIcon(), image.getIcon().getIconWidth(), image.getIcon().getIconHeight());
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

    /**
     * Function used to draw this components foreground. Usually this will incorporate very few of teh components visual
     * Objects. A good example of a Component that only uses the drawForeground function is the GUIDescriptionLabel (The
     * labels that describe thins like your inventory and the TE's inventory).
     *
     * @param mouseX The current X-Coordinate of the mouse
     * @param mouseY The current Y-Coordinate of the mouse
     */
    @Override
    public void drawForeground (int mouseX, int mouseY) {
        //NOOP
    }
}
