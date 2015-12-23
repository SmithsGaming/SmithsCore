package com.SmithsModding.SmithsCore.Client.GUI.Components.Implementations;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Management.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import com.SmithsModding.SmithsCore.Util.Client.*;
import com.SmithsModding.SmithsCore.Util.Client.GUI.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;

/**
 * Created by Marc on 21.12.2015.
 */
public class ComponentBorder implements IGUIComponent {

    private String uniqueID;
    private IGUIComponentState state;
    private IGUIBasedComponentHost parent;

    private Coordinate2D rootAnchorPixel;
    private int width;
    private int height;

    private MinecraftColor color;

    private CornerTypes topLeftCorner;
    private CornerTypes topRightCorner;
    private CornerTypes lowerRightCorner;
    private CornerTypes lowerLeftCorner;

    public ComponentBorder (String uniqueID, IGUIBasedComponentHost parent, Coordinate2D rootAnchorPixel, int width, int height, MinecraftColor color, CornerTypes topLeftType, CornerTypes topRightType, CornerTypes lowerRightCorner, CornerTypes lowerLeftCorner) {
        this(uniqueID, null, parent, rootAnchorPixel, width, height, color, topLeftType, topRightType, lowerRightCorner, lowerLeftCorner);
        state = new CoreComponentState(this);
    }

    public ComponentBorder (String uniqueID, IGUIComponentState state, IGUIBasedComponentHost parent, Coordinate2D rootAnchorPixel, int width, int height, MinecraftColor color, CornerTypes topLeftType, CornerTypes topRightType, CornerTypes lowerRightType, CornerTypes lowerLeftType) {
        this.uniqueID = uniqueID;
        this.state = state;
        this.parent = parent;

        this.rootAnchorPixel = rootAnchorPixel;
        this.width = width;
        this.height = height;

        this.color = color;

        this.topLeftCorner = topLeftType;
        this.topRightCorner = topRightType;
        this.lowerRightCorner = lowerRightType;
        this.lowerLeftCorner = lowerLeftType;
    }

    @Override
    public String getID () {
        return uniqueID;
    }

    @Override
    public IGUIComponentState getState () {
        return state;
    }

    @Override
    public IGUIBasedComponentHost getRootComponent () {
        return parent.getRootComponent();
    }

    @Override
    public Coordinate2D getGlobalCoordinate () {
        return parent.getGlobalCoordinate().getTranslatedCoordinate(getLocalCoordinate());
    }

    @Override
    public Coordinate2D getLocalCoordinate () {
        return rootAnchorPixel;
    }

    @Override
    public Plane getAreaOccupiedByComponent () {
        return new Plane(getGlobalCoordinate(), width, height);
    }

    @Override
    public Plane getSize () {
        return new Plane(0, 0, width, height);
    }

    @Override
    public void drawBackground (int mouseX, int mouseY) {
        RenderManager.pushColorOnRenderStack(color);

        renderWithDependentCorner(mouseX, mouseY);

        RenderManager.popColorFromRenderStack();
    }

    @Override
    public void drawForeground (int mouseX, int mouseY) {
        //NOOP
    }

    @Override
    public boolean handleMouseClickedInside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        return false;
    }

    @Override
    public boolean handleMouseClickedOutside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        return false;
    }

    @Override
    public boolean requiresForcedMouseInput () {
        return false;
    }

    @Override
    public void handleKeyTyped (char key) {
        return;
    }

    private void renderWithDependentCorner (int mouseX, int mouseY) {
        TextureComponent tCenterComponent = new TextureComponent(Textures.Gui.Basic.Border.CENTER, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));

        TextureComponent[] tCornerComponents = new TextureComponent[4];


        tCornerComponents[0] = getTopLeftComponent();
        tCornerComponents[1] = getTopRightComponent();
        //tCornerComponents[2] = getLowerRightCorner();
        //tCornerComponents[3] = getLowerLeftCorner();


        //tCornerComponents[0] = new TextureComponent(Textures.Gui.Basic.Border.INWARTSCORNERLIGHT, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
        //tCornerComponents[1] = new TextureComponent(Textures.Gui.Basic.Border.INWARTSCORNERLIGHT, new UIRotation(false, false, true, 90), new Coordinate2D(0, 0));
        tCornerComponents[2] = new TextureComponent(Textures.Gui.Basic.Border.INWARTSCORNERDARK, new UIRotation(false, false, false, 0), new Coordinate2D(-4, -4));
        tCornerComponents[3] = new TextureComponent(Textures.Gui.Basic.Border.INWARTSCORNERLIGHT, new UIRotation(false, false, true, 270), new Coordinate2D(0, 0));


        TextureComponent[] tSideComponents = new TextureComponent[4];
        tSideComponents[0] = new TextureComponent(Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
        tSideComponents[1] = new TextureComponent(Textures.Gui.Basic.Border.STRAIGHTBORDERDARK, new UIRotation(false, false, true, -90), new Coordinate2D(0, 0));
        tSideComponents[2] = new TextureComponent(Textures.Gui.Basic.Border.STRAIGHTBORDERDARK, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
        tSideComponents[3] = new TextureComponent(Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT, new UIRotation(false, false, true, -90), new Coordinate2D(0, 0));

        GuiHelper.drawRectangleStretched(tCenterComponent, tSideComponents, tCornerComponents, width, height, rootAnchorPixel);
    }

    private TextureComponent getTopLeftComponent () {
        if (topLeftCorner == CornerTypes.Inwarts)
            return new TextureComponent(Textures.Gui.Basic.Border.INWARTSCORNERLIGHT, new UIRotation(false, false, true, 0), new Coordinate2D(0, 0));

        if (topLeftCorner == CornerTypes.Outwarts) {
            UIRotation rotation = new UIRotation(false, false, true, 0);
            Coordinate2D correctionVector = new Coordinate2D(0, 0);

            if (topRightCorner != CornerTypes.Outwarts && lowerLeftCorner == CornerTypes.Outwarts && lowerRightCorner != CornerTypes.Outwarts) {
                rotation = new UIRotation(false, false, true, 180);
                correctionVector = new Coordinate2D(3, -3);
            }

            return new TextureComponent(Textures.Gui.Basic.Border.OUTWARTSCORNER, rotation, correctionVector);
        }

        if (topLeftCorner == CornerTypes.StraightVertical)
            return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Light.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT.getPrimaryLocation(), Colors.DEFAULT, 3, 0, 4, 4), new UIRotation(false, false, true, -90), new Coordinate2D(0, 0));

        return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Light.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT.getPrimaryLocation(), Colors.DEFAULT, 3, 0, 4, 4), new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
    }

    private TextureComponent getTopRightComponent () {
        if (topRightCorner == CornerTypes.Inwarts)
            return new TextureComponent(Textures.Gui.Basic.Border.INWARTSCORNERLIGHT, new UIRotation(false, false, false, 90), new Coordinate2D(10, 10));

        if (topRightCorner == CornerTypes.Outwarts) {
            UIRotation rotation = new UIRotation(false, false, true, 270);
            Coordinate2D correctionVector = new Coordinate2D(0, -3);

            if (topLeftCorner != CornerTypes.Outwarts && lowerRightCorner == CornerTypes.Outwarts && lowerLeftCorner != CornerTypes.Outwarts) {
                rotation = new UIRotation(false, false, true, 90);
                correctionVector = new Coordinate2D(-3, 0);
            }

            return new TextureComponent(Textures.Gui.Basic.Border.OUTWARTSCORNER, rotation, correctionVector);
        }

        if (topRightCorner == CornerTypes.StraightVertical)
            return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Light.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT.getPrimaryLocation(), Colors.DEFAULT, 3, 0, 4, 4), new UIRotation(false, false, true, 90), new Coordinate2D(0, 0));

        return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Light.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT.getPrimaryLocation(), Colors.DEFAULT, 3, 0, 4, 4), new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
    }

    private TextureComponent getLowerRightCorner () {
        if (topLeftCorner == CornerTypes.Inwarts)
            return new TextureComponent(Textures.Gui.Basic.Border.INWARTSCORNERLIGHT, new UIRotation(false, false, true, 270), new Coordinate2D(0, 0));

        if (topLeftCorner == CornerTypes.Outwarts) {
            UIRotation rotation = new UIRotation(false, false, true, 180);
            Coordinate2D correctionVector = new Coordinate2D(3, -3);

            if (lowerLeftCorner != CornerTypes.Outwarts && topRightCorner == CornerTypes.Outwarts && topLeftCorner != CornerTypes.Outwarts) {
                rotation = new UIRotation(false, false, true, 0);
                correctionVector = new Coordinate2D(0, 0);
            }

            return new TextureComponent(Textures.Gui.Basic.Border.OUTWARTSCORNER, rotation, correctionVector);
        }

        if (topLeftCorner == CornerTypes.StraightVertical)
            return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Dark.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERDARK.getPrimaryLocation(), Colors.DEFAULT, 3, 0, 4, 4), new UIRotation(false, false, true, -90), new Coordinate2D(0, 0));

        return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Dark.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERDARK.getPrimaryLocation(), Colors.DEFAULT, 3, 0, 4, 4), new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
    }

    private TextureComponent getLowerLeftCorner () {
        if (lowerLeftCorner == CornerTypes.Inwarts)
            return new TextureComponent(Textures.Gui.Basic.Border.INWARTSCORNERDARK, new UIRotation(false, false, false, 0), new Coordinate2D(-4, -4));

        if (lowerLeftCorner == CornerTypes.Outwarts) {
            UIRotation rotation = new UIRotation(false, false, true, 90);
            Coordinate2D correctionVector = new Coordinate2D(-3, 0);

            if (lowerLeftCorner != CornerTypes.Outwarts && topLeftCorner == CornerTypes.Outwarts && topRightCorner != CornerTypes.Outwarts) {
                rotation = new UIRotation(false, false, true, 270);
                correctionVector = new Coordinate2D(0, -3);
            }

            return new TextureComponent(Textures.Gui.Basic.Border.OUTWARTSCORNER, rotation, correctionVector);
        }

        if (lowerLeftCorner == CornerTypes.StraightVertical)
            return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Dark.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERDARK.getPrimaryLocation(), Colors.DEFAULT, 3, 0, 4, 4), new UIRotation(false, false, true, 90), new Coordinate2D(0, 0));

        return new TextureComponent(new CustomResource("Gui.Basic.Border.Border.Dark.Small", Textures.Gui.Basic.Border.STRAIGHTBORDERDARK.getPrimaryLocation(), Colors.DEFAULT, 3, 0, 4, 4), new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
    }


    public enum CornerTypes {
        Inwarts,
        Outwarts,
        StraightVertical,
        StraightHorizontal
    }
}
