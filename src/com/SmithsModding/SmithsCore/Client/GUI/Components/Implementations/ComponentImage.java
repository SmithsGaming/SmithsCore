package com.SmithsModding.SmithsCore.Client.GUI.Components.Implementations;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Util.Client.*;
import com.SmithsModding.SmithsCore.Util.Client.GUI.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;

import java.util.*;

/**
 * Created by Marc on 09.01.2016.
 */
public class ComponentImage extends CoreComponent {

    private CustomResource image;

    public ComponentImage (String uniqueID, IGUIComponentState state, IGUIBasedComponentHost root, Coordinate2D rootAnchorPixel, CustomResource image) {
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
     * Function used to draw this components background. Usually this will incorporate all of the Components visual
     * objects. A good example of a Component that only uses the drawBackground function is the BackgroundComponent.
     *
     * @param mouseX The current X-Coordinate of the Mouse
     * @param mouseY The current Y-Coordinate of the Mouse
     */
    @Override
    public void drawBackground (int mouseX, int mouseY) {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GuiHelper.bindTexture(TextureMap.locationBlocksTexture);
        GuiHelper.drawTexturedModelRectFromIcon(0, 0, 0, image.getIcon(), image.getIcon().getIconWidth(), image.getIcon().getIconHeight());
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

    /**
     * Function used to draw this components foreground. Usually this will incorporate very few of teh Components visual
     * Objects. A good example of a Component that only uses the drawForeground function is the GUIDescriptionLabel (The
     * labels that describe thins like your inventory and the TE's inventory).
     *
     * @param mouseX The current X-Coordinate of the Mouse
     * @param mouseY The current Y-Coordinate of the Mouse
     */
    @Override
    public void drawForeground (int mouseX, int mouseY) {
        //NOOP
    }
}
