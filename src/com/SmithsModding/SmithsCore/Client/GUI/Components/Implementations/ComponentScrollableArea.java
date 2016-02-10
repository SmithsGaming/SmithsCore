package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.util.common.positioning.*;

/**
 * Created by Marc on 08.02.2016.
 */
public class ComponentScrollableArea extends CoreComponent {

    public ComponentScrollableArea (String uniqueID, IGUIBasedComponentHost parent, IGUIComponentState state, Coordinate2D rootAnchorPixel, int width, int height) {
        super(uniqueID, parent, state, rootAnchorPixel, width, height);
    }

    @Override
    public void update (int mouseX, int mouseY, float partialTickTime) {

    }

    @Override
    public void drawBackground (int mouseX, int mouseY) {

    }

    @Override
    public void drawForeground (int mouseX, int mouseY) {

    }
}
