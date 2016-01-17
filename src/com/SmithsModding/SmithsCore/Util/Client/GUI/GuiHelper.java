/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.Client.GUI;


import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;
import org.lwjgl.opengl.*;

/**
 * Helper class to perform several functions while rendering
 */
public final class GuiHelper {
    public static int DISPLAYHEIGHT;
    public static int DISPLAYWIDTH;
    public static int GUISCALE;
    protected static RenderItem ITEMRENDERER = Minecraft.getMinecraft().getRenderItem();

    /**
     * Draws a CustomResource on the Screen in the given Position relative to current GL Buffer Matrix Origin.
     *
     * @param pResource The resource to Draw
     * @param pX        The X Offset from the current Origin
     * @param pY        The Y Offset from the current Origin
     */
    public static void drawResource(CustomResource pResource, int pX, int pY) {
        GL11.glPushMatrix();
        pResource.getColor().performOpenGLColoring();
        GuiHelper.bindTexture(pResource.getPrimaryLocation());
        GuiHelper.drawTexturedModalRect(pX, pY, 0, pResource.getU(), pResource.getV(), pResource.getWidth(), pResource.getHeight());
        MinecraftColor.resetOpenGLColoring();
        GL11.glPopMatrix();
    }

    /**
     * Draws the given TextureComponent Stretched (repeatedly) in the given Area offset from the current origin with the ElementCoordinate
     *
     * @param pCenterComponent   The Texture to render.
     * @param pWidth             The Total width
     * @param pHeight            The Total Height
     * @param pElementCoordinate The Offset.
     */
    public static void drawRectangleStretched(TextureComponent pCenterComponent, int pWidth, int pHeight, Coordinate2D pElementCoordinate) {
        renderCenter(pCenterComponent, pWidth, pHeight, pElementCoordinate);
    }

    /**
     * Draws the Multicomponent on the screen in the Given size
     *
     * @param pComponents        The MultiComponent to Render.
     * @param pWidth             The Total Width
     * @param pHeight            The Total Height
     * @param pElementCoordinate The Offset
     */
    public static void drawRectangleStretched(MultiComponentTexture pComponents, int pWidth, int pHeight, Coordinate2D pElementCoordinate) {
        renderCenter(pComponents.iCenterComponent, pWidth - pComponents.iCornerComponents[0].iWidth - pComponents.iCornerComponents[1].iWidth, pHeight - pComponents.iCornerComponents[0].iHeight - pComponents.iCornerComponents[3].iHeight, new Coordinate2D(pElementCoordinate.getXComponent() + pComponents.iCornerComponents[0].iWidth, pElementCoordinate.getYComponent() + pComponents.iCornerComponents[0].iHeight));

        renderCorner(pComponents.iCornerComponents[0], pElementCoordinate);
        renderCorner(pComponents.iCornerComponents[1], new Coordinate2D(pElementCoordinate.getXComponent() + pWidth - pComponents.iCornerComponents[1].iWidth, pElementCoordinate.getYComponent()));
        renderCorner(pComponents.iCornerComponents[2], new Coordinate2D(pElementCoordinate.getXComponent() + pWidth - pComponents.iCornerComponents[2].iWidth, pElementCoordinate.getYComponent() + pHeight - pComponents.iCornerComponents[2].iHeight));
        renderCorner(pComponents.iCornerComponents[3], new Coordinate2D(pElementCoordinate.getXComponent(), pElementCoordinate.getYComponent() + pHeight - pComponents.iCornerComponents[3].iHeight));

        renderBorder(pComponents.iSideComponents[0], pWidth - (pComponents.iCornerComponents[0].iWidth * 2), pComponents.iSideComponents[0].iHeight, new Coordinate2D(pElementCoordinate.getXComponent() + pComponents.iCornerComponents[0].iWidth, pElementCoordinate.getYComponent()));
        renderBorder(pComponents.iSideComponents[1], pComponents.iSideComponents[1].iWidth, pHeight - pComponents.iCornerComponents[0].iHeight - pComponents.iCornerComponents[2].iHeight, new Coordinate2D(pElementCoordinate.getXComponent() + pWidth - pComponents.iSideComponents[1].iWidth, pElementCoordinate.getYComponent() + pComponents.iCornerComponents[1].iHeight));
        renderBorder(pComponents.iSideComponents[2], pWidth - pComponents.iCornerComponents[2].iWidth - pComponents.iCornerComponents[3].iWidth, pComponents.iSideComponents[2].iHeight, new Coordinate2D(pElementCoordinate.getXComponent() + pComponents.iCornerComponents[0].iWidth, pElementCoordinate.getYComponent() + (pHeight - pComponents.iSideComponents[2].iHeight)));
        renderBorder(pComponents.iSideComponents[3], pComponents.iSideComponents[3].iWidth, pHeight - (pComponents.iCornerComponents[0].iHeight * 2), new Coordinate2D(pElementCoordinate.getXComponent(), pElementCoordinate.getYComponent() + pComponents.iCornerComponents[0].iHeight));
    }

    /**
     * The unwrapped variant of drawRectangleStretched using the individual components.
     *
     * @param pCenterComponent   A TextureComponent that describes the center of this Texture.
     * @param pCornerComponents  An Array with the components for the Corners in this order: TopLeft, TopRight, BottomRight, BottomLeft.
     * @param pSideComponents    An Array with the components for the Sides in this order: Top, Right, Bottom, Left.
     * @param pWidth             The total width
     * @param pHeight            The total Height
     * @param pElementCoordinate The Offset
     */
    public static void drawRectangleStretched(TextureComponent pCenterComponent, TextureComponent[] pSideComponents, TextureComponent[] pCornerComponents, int pWidth, int pHeight, Coordinate2D pElementCoordinate) {
        renderCenter(pCenterComponent, pWidth - pCornerComponents[0].iWidth - pCornerComponents[1].iWidth, pHeight - pCornerComponents[0].iHeight - pCornerComponents[3].iHeight, new Coordinate2D(pElementCoordinate.getXComponent() + pCornerComponents[0].iWidth, pElementCoordinate.getYComponent() + pCornerComponents[0].iHeight));

        renderCorner(pCornerComponents[0], pElementCoordinate);
        renderCorner(pCornerComponents[1], new Coordinate2D(pElementCoordinate.getXComponent() + pWidth, pElementCoordinate.getYComponent()));
        renderCorner(pCornerComponents[2], new Coordinate2D(pElementCoordinate.getXComponent() + pWidth, pElementCoordinate.getYComponent() + pHeight));
        renderCorner(pCornerComponents[3], new Coordinate2D(pElementCoordinate.getXComponent(), pElementCoordinate.getYComponent() + pHeight));

        renderBorder(pSideComponents[0], pWidth - pCornerComponents[0].iWidth - pCornerComponents[1].iWidth, pSideComponents[0].iHeight, new Coordinate2D(pElementCoordinate.getXComponent() + pCornerComponents[0].iWidth, pElementCoordinate.getYComponent()));
        renderBorder(pSideComponents[1], pHeight - pCornerComponents[1].iHeight - pCornerComponents[2].iHeight, pSideComponents[1].iHeight, new Coordinate2D(pElementCoordinate.getXComponent() + pWidth - pSideComponents[1].iHeight, pElementCoordinate.getYComponent() + pHeight - pCornerComponents[2].iHeight));
        renderBorder(pSideComponents[2], pWidth - pCornerComponents[2].iWidth - pCornerComponents[3].iWidth, pSideComponents[2].iHeight, new Coordinate2D(pElementCoordinate.getXComponent() + pCornerComponents[3].iWidth, pElementCoordinate.getYComponent() + pHeight - pSideComponents[2].iHeight));
        renderBorder(pSideComponents[3], pHeight - pCornerComponents[3].iHeight - pCornerComponents[0].iHeight, pSideComponents[3].iHeight, new Coordinate2D(pElementCoordinate.getXComponent(), pElementCoordinate.getYComponent() + pHeight - pCornerComponents[3].iHeight));
    }

    /**
     * Draws a given FluidStack on the Screen.
     * <p/>
     * This function comes with regards to the BuildCraft Team
     *
     * @param pFluidStack The Stack to render
     * @param pX          The x offset
     * @param pY          The y offset
     * @param pZ          The z offset
     * @param pWidth      The total Width
     * @param pHeight     The total Height
     */
    public static void drawFluid(FluidStack pFluidStack, int pX, int pY, int pZ, int pWidth, int pHeight) {
        if (pFluidStack == null || pFluidStack.getFluid() == null) {
            return;
        }

        TextureAtlasSprite texture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(pFluidStack.getFluid().getStill(pFluidStack).toString());

        if (texture == null) {
            texture = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }

        MinecraftColor fluidColor = new MinecraftColor(pFluidStack.getFluid().getColor(pFluidStack));

        bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.color(fluidColor.getRedFloat(), fluidColor.getGreenFloat(), fluidColor.getBlueFloat(), fluidColor.getAlphaFloat());

        GlStateManager.enableColorLogic();

        int tFullX = pWidth / 16 + 1;
        int tFullY = pHeight / 16 + 1;
        for (int i = 0; i < tFullX; i++) {
            for (int j = 0; j < tFullY; j++) {
                drawCutIcon(texture, pX + i * 16, pY + j * 16, pZ, 16, 16, 0);
            }
        }

        GlStateManager.disableColorLogic();
    }

    /**
     * Draws a cut IIcon on the Screen
     * <p/>
     * This function comes with regards to the BuildCraft Team
     *
     * @param pIcon
     * @param pX              The x offset
     * @param pY              The y offset
     * @param pZ              The z offset
     * @param pWidth          The total Width
     * @param pHeight         The total Height
     * @param pCutOffVertical The vertical distance to cut of.
     */
    private static void drawCutIcon(TextureAtlasSprite pIcon, int pX, int pY, int pZ, int pWidth, int pHeight, int pCutOffVertical) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);

        worldrenderer.pos((double) (pX + 0), (double) (pY + pHeight), (double) pZ).tex((double) pIcon.getMinU(), (double) pIcon.getInterpolatedV(pHeight)).endVertex();
        worldrenderer.pos((double) (pX + pWidth), (double) (pY + pHeight), (double) pZ).tex((double) pIcon.getInterpolatedU(pWidth), (double) pIcon.getInterpolatedV(pHeight)).endVertex();
        worldrenderer.pos((double) (pX + pWidth), (double) (pY + 0), (double) pZ).tex((double) pIcon.getInterpolatedU(pWidth), (double) pIcon.getInterpolatedV(pCutOffVertical)).endVertex();
        worldrenderer.pos((double) (pX + 0), (double) (pY + 0), (double) pZ).tex((double) pIcon.getMinU(), (double) pIcon.getInterpolatedV(pCutOffVertical)).endVertex();

        tessellator.draw();
    }

    /**
     * Renders a texture as if it would be the center of a MultiComponentTexture.
     *
     * @param pComponent         The Component to Render
     * @param pWidth             The total width of the Component in the End
     * @param pHeight            The total height of the Component in the End
     * @param pElementCoordinate The offset of the Component from the current GL Buffer Matric Origin.
     */
    private static void renderCenter(TextureComponent pComponent, int pWidth, int pHeight, Coordinate2D pElementCoordinate) {
        GL11.glPushMatrix();
        GL11.glTranslatef(pElementCoordinate.getXComponent() + pComponent.iRelativeTranslation.getXComponent(), pElementCoordinate.getYComponent() + pComponent.iRelativeTranslation.getYComponent(), 0F);
        pComponent.iRotation.performGLRotation();

        bindTexture(pComponent.iAddress);
        if (pWidth <= pComponent.iWidth && pHeight <= pComponent.iHeight) {
            drawTexturedModalRect(0, 0, 0, pComponent.iU, pComponent.iV, pWidth, pHeight);
        } else {
            int tDrawnHeight = 0;
            int tDrawnWidth = 0;
            while (tDrawnHeight < (pHeight)) {
                int tHeightToRender = pHeight - tDrawnHeight;
                if (tHeightToRender >= pComponent.iHeight)
                    tHeightToRender = pComponent.iHeight;

                if (pWidth <= pComponent.iWidth) {
                    drawTexturedModalRect(0, tDrawnHeight, 0, pComponent.iU, pComponent.iV, pWidth, tHeightToRender);
                    tDrawnHeight += tHeightToRender;
                } else {
                    while (tDrawnWidth < (pWidth)) {
                        int tWidthToRender = pWidth - tDrawnWidth;
                        if (tWidthToRender >= pComponent.iWidth)
                            tWidthToRender = pComponent.iWidth;

                        if (pHeight <= pComponent.iHeight) {
                            drawTexturedModalRect(tDrawnWidth, 0, 0, pComponent.iU, pComponent.iV, tWidthToRender, tHeightToRender);
                            tDrawnWidth += tWidthToRender;
                        } else {
                            drawTexturedModalRect(tDrawnWidth, tDrawnHeight, 0, pComponent.iU, pComponent.iV, tWidthToRender, tHeightToRender);
                            tDrawnWidth += pComponent.iWidth;
                        }
                    }
                    tDrawnWidth = 0;
                    tDrawnHeight += tHeightToRender;
                }
            }
        }

        GL11.glPopMatrix();
    }

    /**
     * Renders a texture as if it would be the corner of a MultiComponentTexture.
     *
     * @param pComponent         The Component to Render
     * @param pElementCoordinate The offset of the Component from the current GL Buffer Matric Origin.
     */
    private static void renderCorner(TextureComponent pComponent, Coordinate2D pElementCoordinate) {
        GL11.glPushMatrix();
        GL11.glTranslatef(pElementCoordinate.getXComponent() + pComponent.iRelativeTranslation.getXComponent(), pElementCoordinate.getYComponent() + pComponent.iRelativeTranslation.getYComponent(), 0F);
        pComponent.iRotation.performGLRotation();

        bindTexture(pComponent.iAddress);
        drawTexturedModalRect(0, 0, 0, pComponent.iU, pComponent.iV, pComponent.iWidth, pComponent.iHeight);

        GL11.glPopMatrix();
    }

    /**
     * Renders a texture as if it would be the side of a MultiComponentTexture.
     *
     * @param pComponent         The Component to Render
     * @param pWidth             The total width of the Component in the End
     * @param pHeight            The total height of the Component in the End
     * @param pElementCoordinate The offset of the Component from the current GL Buffer Matric Origin.
     */
    private static void renderBorder(TextureComponent pComponent, int pWidth, int pHeight, Coordinate2D pElementCoordinate) {
        GL11.glPushMatrix();
        GL11.glTranslatef(pElementCoordinate.getXComponent() + pComponent.iRelativeTranslation.getXComponent(), pElementCoordinate.getYComponent() + pComponent.iRelativeTranslation.getYComponent(), 0F);
        pComponent.iRotation.performGLRotation();

        bindTexture(pComponent.iAddress);

        if (pWidth <= pComponent.iWidth && pHeight <= pComponent.iHeight) {
            drawTexturedModalRect(0, 0, 0, pComponent.iU, pComponent.iV, pWidth, pHeight);
        } else {
            int tDrawnHeigth = 0;
            int tDrawnWidth = 0;
            if (pWidth <= pComponent.iWidth) {
                while (pHeight > tDrawnHeigth) {
                    int tDrawableHeight = pHeight - tDrawnHeigth;
                    if (tDrawableHeight > pComponent.iHeight)
                        tDrawableHeight = pComponent.iHeight;

                    drawTexturedModalRect(0, tDrawnHeigth, 0, pComponent.iU, pComponent.iV, pWidth, tDrawableHeight);

                    tDrawnHeigth += tDrawableHeight;
                }
            } else {
                while (tDrawnWidth < (pWidth)) {
                    int tWidthToRender = pWidth - tDrawnWidth;
                    if (tWidthToRender >= pComponent.iWidth)
                        tWidthToRender = pComponent.iWidth;

                    drawTexturedModalRect(tDrawnWidth, 0, 0, pComponent.iU, pComponent.iV, tWidthToRender, pComponent.iHeight);
                    tDrawnWidth += tWidthToRender;
                }
            }
        }
        GL11.glPopMatrix();
    }

    /**
     * Helper function copied from the gui class to make it possible to use it outside of a gui class.
     * <p/>
     * TRhe function comes with regards to the Minecraft Team
     *
     * @param pXKoord The x offset
     * @param pYKoord The y offset
     * @param pZKoord The z offset
     * @param pWidth  The total Width
     * @param pHeight The total Height
     * @param pU      The X Offset in the currently loaded GL Image.
     * @param pV      The Y Offset in the currently loaded GL Iamge
     */
    public static void drawTexturedModalRect(int pXKoord, int pYKoord, int pZKoord, int pU, int pV, int pWidth, int pHeight) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double) (pXKoord + 0), (double) (pYKoord + pHeight), (double) pZKoord).tex((double) ((float) (pU + 0) * f), (double) ((float) (pV + pHeight) * f1)).endVertex();
        worldrenderer.pos((double) (pXKoord + pWidth), (double) (pYKoord + pHeight), (double) pZKoord).tex((double) ((float) (pU + pWidth) * f), (double) ((float) (pV + pHeight) * f1)).endVertex();
        worldrenderer.pos((double) (pXKoord + pWidth), (double) (pYKoord + 0), (double) pZKoord).tex((double) ((float) (pU + pWidth) * f), (double) ((float) (pV + 0) * f1)).endVertex();
        worldrenderer.pos((double) (pXKoord + 0), (double) (pYKoord + 0), (double) pZKoord).tex((double) ((float) (pU + 0) * f), (double) ((float) (pV + 0) * f1)).endVertex();
        tessellator.draw();
    }

    /**
     * Helper function copied from the gui class to make it possible to use it outside of a gui class.
     * <p/>
     * TRhe function comes with regards to the Minecraft Team
     *
     * @param pXCoord The x offset
     * @param pYCoord The y offset
     * @param pZCoord The z offset
     * @param pWidth  The total Width
     * @param pHeight The total Height
     * @param pIIcon  The IIcon describing the Texture
     */
    public static void drawTexturedModelRectFromIcon(int pXCoord, int pYCoord, int pZCoord, TextureAtlasSprite pIIcon, int pWidth, int pHeight) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double) (pXCoord + 0), (double) (pYCoord + pHeight), (double) pZCoord).tex((double) pIIcon.getMinU(), (double) pIIcon.getMaxV()).endVertex();
        worldrenderer.pos((double) (pXCoord + pWidth), (double) (pYCoord + pHeight), (double) pZCoord).tex((double) pIIcon.getMaxU(), (double) pIIcon.getMaxV()).endVertex();
        worldrenderer.pos((double) (pXCoord + pWidth), (double) (pYCoord + 0), (double) pZCoord).tex((double) pIIcon.getMaxU(), (double) pIIcon.getMinV()).endVertex();
        worldrenderer.pos((double) (pXCoord + 0), (double) (pYCoord + 0), (double) pZCoord).tex((double) pIIcon.getMinU(), (double) pIIcon.getMinV()).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a colored rectangle over the given plane.
     *
     * @param pPlane  The plane to cover in the Color on the Screen
     * @param pZKoord The Z-Level to render on.
     * @param pColor  The color to render.
     */
    public static void drawColoredRect(Plane pPlane, int pZKoord, MinecraftColor pColor) {
        drawGradiendColoredRect(pPlane, pZKoord, pColor, pColor);
    }

    /**
     * Draws a vertical gradient rectangle in the given position.
     *
     * @param pPlane      The plane to fill on the screen
     * @param pZKoord     The Z-Level to render on.
     * @param pColorStart The left color
     * @param pColorEnd   The right color.
     */
    public static void drawGradiendColoredRect(Plane pPlane, int pZKoord, MinecraftColor pColorStart, MinecraftColor pColorEnd) {
        float f = pColorStart.getAlphaFloat();
        float f1 = pColorStart.getBlueFloat();
        float f2 = pColorStart.getGreenFloat();
        float f3 = pColorStart.getRedFloat();
        float f4 = pColorEnd.getAlphaFloat();
        float f5 = pColorEnd.getBlueFloat();
        float f6 = pColorEnd.getGreenFloat();
        float f7 = pColorEnd.getRedFloat();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((double) pPlane.LowerRightCoord().getXComponent(), (double) pPlane.TopLeftCoord().getYComponent(), (double) pZKoord).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos((double) pPlane.TopLeftCoord().getXComponent(), (double) pPlane.TopLeftCoord().getYComponent(), (double) pZKoord).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos((double) pPlane.TopLeftCoord().getXComponent(), (double) pPlane.LowerRightCoord().getYComponent(), (double) pZKoord).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos((double) pPlane.LowerRightCoord().getXComponent(), (double) pPlane.LowerRightCoord().getYComponent(), (double) pZKoord).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    /**
     * Helper function to draw an ItemStack on the given location
     *
     * @param stack The Stack to render
     * @param x     The X Coordinate to render on
     * @param y     The Y Coordinate to render on
     */
    public static void drawItemStack(ItemStack stack, int x, int y) {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        FontRenderer font = null;
        if (stack != null) {
            font = stack.getItem().getFontRenderer(stack);
        }

        if (font == null) {
            font = Minecraft.getMinecraft().fontRendererObj;
        }

        ITEMRENDERER.renderItemAndEffectIntoGUI(stack, x, y);
        ITEMRENDERER.renderItemOverlayIntoGUI(font, stack, x, y, "");
    }

    /**
     * Helper function to draw an ItemStack with an Overlaytext
     *
     * @param stack   The Stack to Render
     * @param x       The X Coordinate to render on
     * @param y       The Y Coordinate to render on
     * @param altText The overlay text to render.
     */
    private static void drawItemStack(ItemStack stack, int x, int y, String altText) {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        FontRenderer font = null;
        if (stack != null) {
            font = stack.getItem().getFontRenderer(stack);
        }

        if (font == null) {
            font = Minecraft.getMinecraft().fontRendererObj;
        }

        ITEMRENDERER.renderItemAndEffectIntoGUI(stack, x, y);
        ITEMRENDERER.renderItemOverlayIntoGUI(font, stack, x, y - 8, altText);
    }

    /**
     * Convenient helper function to bind the texture to a String adress
     *
     * @param pTextureAddress
     */
    public static void bindTexture(String pTextureAddress) {
        bindTexture(new ResourceLocation(pTextureAddress));
    }

    /**
     * Convenient helper function to bind the texture using a ResourceLocation
     *
     * @param pTextureLocation
     */
    public static void bindTexture(ResourceLocation pTextureLocation) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(pTextureLocation);
    }

    /**
     * Calculates the ScaleFactor that Minecraft uses to scale its drawable area to the selected window size by the user
     * and sets it in the internal storage of the GuiHelper. Technically only a single call to this function is needed,
     * yet when the user changes the window size this has to be called again to make it work.
     * As there is no way to detect that, we currently call it before we need a function that uses the data provided
     * by this function.
     */
    public static void calcScaleFactor() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sc = new ScaledResolution(mc);
        DISPLAYWIDTH = sc.getScaledWidth();
        DISPLAYHEIGHT = sc.getScaledHeight();
        GUISCALE = sc.getScaleFactor();
    }

    /**
     * Enables the scissor box for a given Plan in the UI.
     * Keep the weird drawing origin for the Scissor box in mind.
     *
     * @param pTargetPlane The plane that should be scissored.
     */
    public static void enableScissor(Plane pTargetPlane) {

        calcScaleFactor();

        GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(pTargetPlane.TopLeftCoord().getXComponent() * GUISCALE, ( ( DISPLAYHEIGHT - pTargetPlane.LowerRightCoord().getYComponent() ) * GUISCALE ), ( pTargetPlane.getWidth() ) * GUISCALE, ( pTargetPlane.getHeigth() ) * GUISCALE);

    }

    /**
     * Disables all Scissors currently active
     */
    public static void disableScissor() {

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopAttrib();

    }

    /**
     * Renders the debug overlay for the Scissor box
     */
    public static void renderScissorDebugOverlay() {
        bindTexture(TextureMap.locationBlocksTexture);
        drawTexturedModalRect(-10, -10, 10, 0, 0, DISPLAYWIDTH, DISPLAYHEIGHT);
    }
}
