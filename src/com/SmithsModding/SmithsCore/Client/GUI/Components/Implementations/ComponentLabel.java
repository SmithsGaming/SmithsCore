package com.SmithsModding.SmithsCore.Client.GUI.Components.Implementations;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.client.gui.*;

import java.util.*;

/**
 * Created by marcf on 12/28/2015.
 */
public class ComponentLabel extends CoreComponent {
    private FontRenderer renderer;
    private String displayedText;
    private MinecraftColor color;

    public ComponentLabel(String uniqueID, IGUIBasedComponentHost root, IGUIComponentState state, Coordinate2D localCoordinate, MinecraftColor color, FontRenderer renderer, String displayedText) {
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
     * Usually this will incorporate all of the Components visual objects.
     * A good example of a Component that only uses the drawBackground function is the
     * BackgroundComponent.
     *
     * @param mouseX The current X-Coordinate of the Mouse
     * @param mouseY The current Y-Coordinate of the Mouse
     */
    @Override
    public void drawBackground(int mouseX, int mouseY) {
        renderer.drawStringWithShadow(displayedText, 0,0, color.getRGB());
    }

    /**
     * Function used to draw this components foreground.
     * Usually this will incorporate very few of teh Components visual Objects.
     * A good example of a Component that only uses the drawForeground function is the
     * GUIDescriptionLabel (The labels that describe thins like your inventory and the TE's inventory).
     *
     * @param mouseX The current X-Coordinate of the Mouse
     * @param mouseY The current Y-Coordinate of the Mouse
     */
    @Override
    public void drawForeground(int mouseX, int mouseY) {
        //NOOP
    }
}
