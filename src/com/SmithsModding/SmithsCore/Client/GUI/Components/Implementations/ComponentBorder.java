package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.management.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.util.client.*;
import com.smithsmodding.smithscore.util.client.color.*;
import com.smithsmodding.smithscore.util.client.gui.*;
import com.smithsmodding.smithscore.util.common.positioning.*;

/**
 * Created by Marc on 21.12.2015.
 */
public class ComponentBorder extends CoreComponent {

    private MinecraftColor color;

    private CornerTypes topLeftCorner;
    private CornerTypes topRightCorner;
    private CornerTypes lowerRightCorner;
    private CornerTypes lowerLeftCorner;

    public ComponentBorder (String uniqueID, IGUIBasedComponentHost parent, Coordinate2D rootAnchorPixel, int width, int height, MinecraftColor color, CornerTypes topLeftType, CornerTypes topRightType, CornerTypes lowerRightCorner, CornerTypes lowerLeftCorner) {
        this(uniqueID, new CoreComponentState(null), parent, rootAnchorPixel, width, height, color, topLeftType, topRightType, lowerRightCorner, lowerLeftCorner);
    }

    public ComponentBorder (String uniqueID, IGUIComponentState state, IGUIBasedComponentHost parent, Coordinate2D rootAnchorPixel, int width, int height, MinecraftColor color, CornerTypes topLeftType, CornerTypes topRightType, CornerTypes lowerRightType, CornerTypes lowerLeftType) {
        super(uniqueID, parent, state, rootAnchorPixel, height, width);

        this.state.setComponent(this);

        this.color = color;

        this.topLeftCorner = topLeftType;
        this.topRightCorner = topRightType;
        this.lowerRightCorner = lowerRightType;
        this.lowerLeftCorner = lowerLeftType;
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
        StandardRenderManager.pushColorOnRenderStack(getColor());

        renderWithDependentCorner(mouseX, mouseY);

        StandardRenderManager.popColorFromRenderStack();
    }

    @Override
    public void drawForeground (int mouseX, int mouseY) {
        //NOOP
    }

    public MinecraftColor getColor(){
        return color;
    }

    private void renderWithDependentCorner (int mouseX, int mouseY) {
        TextureComponent tCenterComponent = new TextureComponent(Textures.Gui.Basic.Border.CENTER, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));

        TextureComponent[] tCornerComponents = new TextureComponent[4];


        tCornerComponents[0] = getTopLeftComponent();
        tCornerComponents[1] = getTopRightComponent();
        tCornerComponents[2] = getLowerRightCorner();
        tCornerComponents[3] = getLowerLeftCorner();

        TextureComponent[] tSideComponents = new TextureComponent[4];
        tSideComponents[0] = getTopBorder();
        tSideComponents[1] = getRightBorder();
        tSideComponents[2] = getLowerBorder();
        tSideComponents[3] = getLeftBorder();

        GuiHelper.drawRectangleStretched(tCenterComponent, tSideComponents, tCornerComponents, width, height, rootAnchorPixel);
    }

    private TextureComponent getTopLeftComponent () {
        if (topLeftCorner == CornerTypes.Inwarts)
            return new TextureComponent(Textures.Gui.Basic.Border.INWARTSCORNERLIGHT, new UIRotation(false, false, true, 0), new Coordinate2D(0, 0));

        if (topLeftCorner == CornerTypes.Outwarts) {
            CustomResource cornerTexture = Textures.Gui.Basic.Border.OUTWARTSCORNERDARKLIGHT;
            UIRotation rotation = new UIRotation(false, false, true, 0);
            Coordinate2D correctionVector = new Coordinate2D(0, 0);

            if (topRightCorner != CornerTypes.Outwarts && lowerLeftCorner == CornerTypes.Outwarts && lowerRightCorner != CornerTypes.Outwarts) {
                cornerTexture = Textures.Gui.Basic.Border.OUTWARTSCORNERLIGHTDARK;
                rotation = new UIRotation(false, false, true, 180);
                correctionVector = new Coordinate2D(3, 3);
            }

            return new TextureComponent(cornerTexture, rotation, correctionVector);
        }

        if (topLeftCorner == CornerTypes.StraightVertical)
            return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Light.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT.getPrimaryLocation(), Colors.DEFAULT, 3, 0, 3, 3), new UIRotation(false, false, true, -90), new Coordinate2D(0, 3));

        return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Light.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT.getPrimaryLocation(), Colors.DEFAULT, 3, 0, 3, 3), new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
    }

    private TextureComponent getTopRightComponent () {
        if (topRightCorner == CornerTypes.Inwarts)
            return new TextureComponent(Textures.Gui.Basic.Border.INWARTSCORNERLIGHT, new UIRotation(false, false, true, 90), new Coordinate2D(0, 0));

        if (topRightCorner == CornerTypes.Outwarts) {
            CustomResource cornerTexture = Textures.Gui.Basic.Border.OUTWARTSCORNERDARKDARK;
            UIRotation rotation = new UIRotation(false, false, true, 270);
            Coordinate2D correctionVector = new Coordinate2D(-3, 3);

            if (topLeftCorner != CornerTypes.Outwarts && lowerRightCorner == CornerTypes.Outwarts && lowerLeftCorner != CornerTypes.Outwarts) {
                cornerTexture = Textures.Gui.Basic.Border.OUTWARTSCORNERLIGHTLIGHT;
                rotation = new UIRotation(false, false, true, 90);
                correctionVector = new Coordinate2D(0, 0);
            }

            return new TextureComponent(cornerTexture, rotation, correctionVector);
        }

        if (topRightCorner == CornerTypes.StraightVertical)
            return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Dark.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERDARK.getPrimaryLocation(), Colors.DEFAULT, 253, 3, 3, 3), new UIRotation(false, false, true, 0), new Coordinate2D(-3, 0));

        return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Light.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT.getPrimaryLocation(), Colors.DEFAULT, 3, 0, 3, 3), new UIRotation(false, false, true, 0), new Coordinate2D(-3, 0));
    }

    private TextureComponent getLowerRightCorner () {
        if (lowerRightCorner == CornerTypes.Inwarts)
            return new TextureComponent(Textures.Gui.Basic.Border.INWARTSCORNERDARK, new UIRotation(false, false, true, 0), new Coordinate2D(-4, -4));

        if (lowerRightCorner == CornerTypes.Outwarts) {
            CustomResource cornerTexture = Textures.Gui.Basic.Border.OUTWARTSCORNERLIGHTDARK;
            UIRotation rotation = new UIRotation(false, false, true, 180);
            Coordinate2D correctionVector = new Coordinate2D(0, 0);

            if (lowerLeftCorner != CornerTypes.Outwarts && topRightCorner == CornerTypes.Outwarts && topLeftCorner != CornerTypes.Outwarts) {
                cornerTexture = Textures.Gui.Basic.Border.OUTWARTSCORNERDARKDARK;
                rotation = new UIRotation(false, false, true, 0);
                correctionVector = new Coordinate2D(-3, -3);
            }

            return new TextureComponent(cornerTexture, rotation, correctionVector);
        }

        if (lowerRightCorner == CornerTypes.StraightVertical)
            return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Dark.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERDARK.getPrimaryLocation(), Colors.DEFAULT, 253, 3, 3, 3), new UIRotation(false, false, true, 0), new Coordinate2D(-3, -3));

        return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Dark.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERDARK.getPrimaryLocation(), Colors.DEFAULT, 3, 253, 3, 3), new UIRotation(false, false, false, 0), new Coordinate2D(-3, -3));
    }

    private TextureComponent getLowerLeftCorner () {
        if (lowerLeftCorner == CornerTypes.Inwarts)
            return new TextureComponent(Textures.Gui.Basic.Border.INWARTSCORNERLIGHTINVERTED, new UIRotation(false, false, true, 0), new Coordinate2D(0, -3));

        if (lowerLeftCorner == CornerTypes.Outwarts) {
            CustomResource cornerTexture = Textures.Gui.Basic.Border.OUTWARTSCORNERLIGHTLIGHT;
            UIRotation rotation = new UIRotation(false, false, true, 90);
            Coordinate2D correctionVector = new Coordinate2D(3, -3);

            if (lowerRightCorner != CornerTypes.Outwarts && topLeftCorner == CornerTypes.Outwarts && topRightCorner != CornerTypes.Outwarts) {
                cornerTexture = Textures.Gui.Basic.Border.OUTWARTSCORNERDARKDARK;
                rotation = new UIRotation(false, false, true, 270);
                correctionVector = new Coordinate2D(0, 0);
            }

            return new TextureComponent(cornerTexture, rotation, correctionVector);
        }

        if (lowerLeftCorner == CornerTypes.StraightVertical)
            return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Light.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT.getPrimaryLocation(), Colors.DEFAULT, 3, 0, 3, 3), new UIRotation(false, false, true, -90), new Coordinate2D(0, 0));

        return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Dark.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERDARK.getPrimaryLocation(), Colors.DEFAULT, 3, 253, 3, 3), new UIRotation(false, false, true, 0), new Coordinate2D(0, -3));
    }

    public TextureComponent getTopBorder () {
        if (( topLeftCorner == CornerTypes.Outwarts && topRightCorner == CornerTypes.Outwarts ) || ( topLeftCorner == CornerTypes.StraightVertical && topRightCorner == CornerTypes.StraightVertical )) {
            return new TextureComponent(new CustomResource("Gui.Basic.Border.Center", Textures.Gui.Basic.Border.CENTER.getPrimaryLocation(), Colors.DEFAULT, 4, 4, 248, 4), new UIRotation(false, false, true, 0), new Coordinate2D(0, 0));
        }

        return new TextureComponent(Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
    }

    public TextureComponent getRightBorder () {
        if (( topRightCorner == CornerTypes.Outwarts && lowerRightCorner == CornerTypes.Outwarts && topLeftCorner != CornerTypes.Outwarts && lowerLeftCorner != CornerTypes.Outwarts ) || ( topRightCorner == CornerTypes.StraightHorizontal && lowerRightCorner == CornerTypes.StraightHorizontal )) {
            return new TextureComponent(new CustomResource("Gui.Basic.Border.Center", Textures.Gui.Basic.Border.CENTER.getPrimaryLocation(), Colors.DEFAULT, 4, 248, 4, 4), new UIRotation(false, false, true, -90), new Coordinate2D(0, 0));
        }

        return new TextureComponent(Textures.Gui.Basic.Border.STRAIGHTBORDERDARK, new UIRotation(false, false, true, -90), new Coordinate2D(0, 0));
    }

    public TextureComponent getLowerBorder () {
        if (( lowerRightCorner == CornerTypes.Outwarts && lowerLeftCorner == CornerTypes.Outwarts ) || ( lowerLeftCorner == CornerTypes.StraightVertical && lowerRightCorner == CornerTypes.StraightVertical )) {
            return new TextureComponent(new CustomResource("Gui.Basic.Border.Center", Textures.Gui.Basic.Border.CENTER.getPrimaryLocation(), Colors.DEFAULT, 4, 4, 248, 4), new UIRotation(false, false, true, 0), new Coordinate2D(0, 0));
        }

        return new TextureComponent(Textures.Gui.Basic.Border.STRAIGHTBORDERDARK, new UIRotation(false, false, true, 0), new Coordinate2D(0, 0));
    }

    public TextureComponent getLeftBorder () {
        if (( topLeftCorner == CornerTypes.Outwarts && lowerLeftCorner == CornerTypes.Outwarts && topRightCorner != CornerTypes.Outwarts && lowerRightCorner != CornerTypes.Outwarts ) || ( topLeftCorner == CornerTypes.StraightHorizontal && lowerLeftCorner == CornerTypes.StraightHorizontal )) {
            return new TextureComponent(new CustomResource("Gui.Basic.Border.Center", Textures.Gui.Basic.Border.CENTER.getPrimaryLocation(), Colors.DEFAULT, 4, 248, 4, 4), new UIRotation(false, false, true, -90), new Coordinate2D(0, 0));
        }

        return new TextureComponent(Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT, new UIRotation(false, false, true, -90), new Coordinate2D(0, 0));
    }

    public enum CornerTypes {
        Inwarts,
        Outwarts,
        StraightVertical,
        StraightHorizontal
    }
}
