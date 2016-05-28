package com.smithsmodding.smithscore.client.gui.components.implementations;

import com.smithsmodding.smithscore.client.gui.components.core.ComponentOrientation;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.management.StandardRenderManager;
import com.smithsmodding.smithscore.client.gui.state.IGUIComponentState;
import com.smithsmodding.smithscore.util.client.Textures;
import com.smithsmodding.smithscore.util.client.TranslationKeys;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.client.gui.GuiHelper;
import com.smithsmodding.smithscore.util.client.gui.TextureComponent;
import com.smithsmodding.smithscore.util.client.gui.UIRotation;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import com.smithsmodding.smithscore.util.common.positioning.Plane;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Marc on 18.01.2016.
 */
public class ComponentFluidTank extends CoreComponent {

    ArrayList<FluidStack> fluidStacks = new ArrayList<FluidStack>();
    int totalTankContents = 1600;

    ComponentOrientation orientation;

    MinecraftColor foreGround;
    MinecraftColor backGround;

    public ComponentFluidTank (String uniqueID, IGUIBasedComponentHost parent, IGUIComponentState state, Coordinate2D rootAnchorPixel, int width, int height, ComponentOrientation orientation) {
        this(uniqueID, parent, state, rootAnchorPixel, width, height, orientation, new MinecraftColor(Color.RED), new MinecraftColor(Color.WHITE));
    }

    public ComponentFluidTank(String uniqueID, IGUIBasedComponentHost parent, IGUIComponentState state, Coordinate2D rootAnchorPixel, int width, int height, ComponentOrientation orientation, MinecraftColor foreGround, MinecraftColor backGround) {
        super(uniqueID, parent, state, rootAnchorPixel, width, height);

        this.orientation = orientation;
        this.foreGround = foreGround;
        this.backGround = backGround;
    }

    @Override
    public ArrayList<String> getToolTipContent () {
        int fluidContent = 0;

        for (FluidStack stack :
                fluidStacks) {
            fluidContent += stack.amount;
        }

        ArrayList<String> toolTip = new ArrayList<String>();

        toolTip.add(I18n.translateToLocal(TranslationKeys.GUI.TANKAMOUNT) + " " + fluidContent + " mB / " + totalTankContents + " mB");

        if (fluidContent == 0)
            return toolTip;

        toolTip.add("");
        toolTip.add(I18n.translateToLocal(TranslationKeys.GUI.TANKCONTENTS));

        toolTip.addAll(fluidStacks.stream().map(stack -> "  * " + stack.getFluid().getLocalizedName(stack) + ": " + stack.amount + " mB").collect(Collectors.toList()));

        return toolTip;
    }

    @Override
    public void update (int mouseX, int mouseY, float partialTickTime) {
        fluidStacks = getComponentHost().getRootManager().getTankContents(this);
        totalTankContents = getComponentHost().getRootManager().getTotalTankContents(this);
    }

    @Override
    public void drawBackground (int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        StandardRenderManager.pushColorOnRenderStack(backGround);

        TextureComponent tCenterComponent = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 1, 16, 16, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));

        TextureComponent[] tCornerComponents = new TextureComponent[4];
        tCornerComponents[0] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 0, 0, 1, 1, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
        tCornerComponents[1] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 17, 0, 1, 1, new UIRotation(false, false, true, 90), new Coordinate2D(0, 0));
        tCornerComponents[2] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 17, 17, 1, 1, new UIRotation(false, false, true, 180), new Coordinate2D(0, 0));
        tCornerComponents[3] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 0, 17, 1, 1, new UIRotation(false, false, true, 270), new Coordinate2D(0, 0));

        TextureComponent[] tSideComponents = new TextureComponent[4];
        tSideComponents[0] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 0, 16, 1, new UIRotation(false, false, false, 0), new Coordinate2D(0, 0));
        tSideComponents[1] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 17, 1, 16, new UIRotation(false, false, true, -90), new Coordinate2D(15, 0));
        tSideComponents[2] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 17, 1, 16, new UIRotation(false, false, false, 0), new Coordinate2D(0, 15));
        tSideComponents[3] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 0, 16, 1, new UIRotation(false, false, true, -90), new Coordinate2D(0, 0));

        GuiHelper.drawRectangleStretched(tCenterComponent, tSideComponents, tCornerComponents, getSize().getWidth(), getSize().getHeigth(), new Coordinate2D(0, 0));

        StandardRenderManager.popColorFromRenderStack();
        GlStateManager.popMatrix();


        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 1);

        if (orientation == ComponentOrientation.HORIZONTALLEFTTORIGHT || orientation == ComponentOrientation.HORIZONTALRIGHTTOLEFT) {
            int blockDistance = ( getSize().getWidth() - 2 ) / 9;

            for (int iter = blockDistance; iter < ( getSize().getWidth() - 2 ); iter += blockDistance) {
                int barHeight = (int) ( getSize().getHeigth() * 0.3 );
                if (iter == 5 * blockDistance) {
                    barHeight *= 2;
                }

                GuiHelper.drawColoredRect(new Plane(new Coordinate2D(iter, 1), 1, barHeight), 0, new MinecraftColor(255, 0, 0));
            }
        } else {
            int blockDistance = (getSize().getHeigth() - 2) / 10;

            for (int iter = blockDistance + 1; iter < ( getSize().getHeigth()); iter += (blockDistance + 1)) {
                int barWidth = (int) ( getSize().getWidth() * 0.3 );
                if (iter == 5 * (blockDistance + 1)) {
                    barWidth *= 2;
                }

                GuiHelper.drawColoredRect(new Plane(new Coordinate2D(1, iter), barWidth, 1), 0, new MinecraftColor(MinecraftColor.RED));
            }
        }

        GlStateManager.translate(0, 0, -1);
        GlStateManager.popMatrix();


        if (fluidStacks == null || fluidStacks.size() == 0)
            return;

        GlStateManager.pushMatrix();

        switch (orientation) {
            case HORIZONTALLEFTTORIGHT:
                renderHorizontalLeftToRight();
                break;
            case HORIZONTALRIGHTTOLEFT:
                renderHorizontalRightToLeft();
                break;
            case VERTICALBOTTOMTOTOP:
                renderVerticalBottomToTop();
                break;
            case VERTICALTOPTOBOTTOM:
                renderVerticalTopToBottom();
                break;
        }

        GlStateManager.popMatrix();
    }

    @Override
    public void drawForeground (int mouseX, int mouseY) {

    }

    private void renderHorizontalLeftToRight () {
        int fluidX = 1;

        for (FluidStack stack : fluidStacks) {
            int fluidWidth = (int) ( ( (float) stack.amount / (float) totalTankContents ) * getSize().getWidth() );

            if (fluidX + fluidWidth > ( getSize().getWidth() - 2 ))
                fluidWidth = getSize().getWidth() - fluidX - 2;

            Plane fluidArea = new Plane(getGlobalCoordinate().getTranslatedCoordinate(new Coordinate2D(fluidX, 1)), fluidWidth, getSize().getHeigth() - 2);
            getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().setScissorRegionTo(fluidArea);

            GuiHelper.drawFluid(stack, 1, 1, 0, getSize().getWidth() - 2, getSize().getHeigth() - 2);

            getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().popCurrentScissorRegion();

            fluidX += fluidWidth;
        }
    }

    private void renderHorizontalRightToLeft () {
        int fluidX = getSize().getWidth() - 1;

        for (FluidStack stack : fluidStacks) {
            int fluidWidth = (int) ( ( (float) stack.amount / (float) totalTankContents ) * getSize().getWidth() );

            if (fluidX - fluidWidth < 2)
                fluidWidth = fluidX - 1;

            fluidX -= fluidWidth;

            Plane fluidArea = new Plane(getGlobalCoordinate().getTranslatedCoordinate(new Coordinate2D(fluidX, 1)), fluidWidth, getSize().getHeigth() - 2);
            getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().setScissorRegionTo(fluidArea);

            GuiHelper.drawFluid(stack, 1, 1, 0, getSize().getWidth() - 2, getSize().getHeigth() - 2);

            getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().popCurrentScissorRegion();
        }
    }

    private void renderVerticalBottomToTop () {
        int fluidY = getSize().getHeigth() - 1;

        for (FluidStack stack : fluidStacks) {
            int fluidHeight = (int) ( ( (float) stack.amount / (float) totalTankContents ) * getSize().getHeigth() );

            if (fluidY - fluidHeight < 2)
                fluidHeight = fluidY - 1;

            fluidY -= fluidHeight;

            Plane fluidArea = new Plane(getGlobalCoordinate().getTranslatedCoordinate(new Coordinate2D(1, fluidY)), getSize().getWidth() - 2, fluidHeight);
            getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().setScissorRegionTo(fluidArea);

            GuiHelper.drawFluid(stack, 1, 1, 0, getSize().getWidth() - 2, getSize().getHeigth() - 2);

            getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().popCurrentScissorRegion();
        }
    }

    private void renderVerticalTopToBottom () {
        int fluidY = 1;

        for (FluidStack stack : fluidStacks) {
            int fluidHeight = (int) ( ( (float) stack.amount / (float) totalTankContents ) * getSize().getHeigth() );

            if (fluidY + fluidHeight > ( getSize().getHeigth() - 2 ))
                fluidHeight = getSize().getHeigth() - fluidY - 2;

            Plane fluidArea = new Plane(getGlobalCoordinate().getTranslatedCoordinate(new Coordinate2D(1, fluidY)), getSize().getWidth() - 2, fluidHeight);
            getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().setScissorRegionTo(fluidArea);

            GuiHelper.drawFluid(stack, 1, 1, 0, getSize().getWidth() - 2, getSize().getHeigth() - 2);

            getComponentHost().getRootGuiObject().getRenderManager().getScissorRegionManager().popCurrentScissorRegion();

            fluidY += fluidHeight;
        }
    }

    @Override
    public boolean handleMouseClickedInside (int relativeMouseX, int relativeMouseY, int mouseButton) {
        return false;
    }
}
