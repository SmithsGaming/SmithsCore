package com.SmithsModding.SmithsCore.Util.Client.Color;

import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;

/**
 * Created by Orion
 * Created on 27.10.2015
 * 17:19
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MinecraftColor extends Color {
    /**
     * Creates an opaque sRGB color with the specified red, green,
     * and blue values in the range (0 - 255).
     * The actual color used in rendering depends
     * on finding the best match given the color space
     * available for a given output device.
     * Alpha is defaulted to 255.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>
     *                                  or <code>b</code> are outside of the range
     *                                  0 to 255, inclusive
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB
     */
    public MinecraftColor(int r, int g, int b) {
        super(r, g, b);
    }

    /**
     * Creates an sRGB color with the specified red, green, blue, and alpha
     * values in the range (0 - 255).
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>,
     *                                  <code>b</code> or <code>a</code> are outside of the range
     *                                  0 to 255, inclusive
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getAlpha
     * @see #getRGB
     */
    public MinecraftColor(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    /**
     * Creates an opaque sRGB color with the specified combined RGB value
     * consisting of the red component in bits 16-23, the green component
     * in bits 8-15, and the blue component in bits 0-7.  The actual color
     * used in rendering depends on finding the best match given the
     * color space available for a particular output device.  Alpha is
     * defaulted to 255.
     *
     * @param rgb the combined RGB components
     * @see ColorModel#getRGBdefault
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB
     */
    public MinecraftColor(int rgb) {
        super(rgb);
    }

    /**
     * Creates an sRGB color with the specified combined RGBA value consisting
     * of the alpha component in bits 24-31, the red component in bits 16-23,
     * the green component in bits 8-15, and the blue component in bits 0-7.
     * If the <code>hasalpha</code> argument is <code>false</code>, alpha
     * is defaulted to 255.
     *
     * @param rgba     the combined RGBA components
     * @param hasalpha <code>true</code> if the alpha bits are valid;
     *                 <code>false</code> otherwise
     * @see ColorModel#getRGBdefault
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getAlpha
     * @see #getRGB
     */
    public MinecraftColor(int rgba, boolean hasalpha) {
        super(rgba, hasalpha);
    }

    /**
     * Creates an opaque sRGB color with the specified red, green, and blue
     * values in the range (0.0 - 1.0).  Alpha is defaulted to 1.0.  The
     * actual color used in rendering depends on finding the best
     * match given the color space available for a particular output
     * device.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>
     *                                  or <code>b</code> are outside of the range
     *                                  0.0 to 1.0, inclusive
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB
     */
    public MinecraftColor(float r, float g, float b) {
        super(r, g, b);
    }

    /**
     * Creates an sRGB color with the specified red, green, blue, and
     * alpha values in the range (0.0 - 1.0).  The actual color
     * used in rendering depends on finding the best match given the
     * color space available for a particular output device.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>
     *                                  <code>b</code> or <code>a</code> are outside of the range
     *                                  0.0 to 1.0, inclusive
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getAlpha
     * @see #getRGB
     */
    public MinecraftColor(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    /**
     * Creates a color in the specified <code>ColorSpace</code>
     * with the color components specified in the <code>float</code>
     * array and the specified alpha.  The number of components is
     * determined by the type of the <code>ColorSpace</code>.  For
     * example, RGB requires 3 components, but CMYK requires 4
     * components.
     *
     * @param cspace     the <code>ColorSpace</code> to be used to
     *                   interpret the components
     * @param components an arbitrary number of color components
     *                   that is compatible with the <code>ColorSpace</code>
     * @param alpha      alpha value
     * @throws IllegalArgumentException if any of the values in the
     *                                  <code>components</code> array or <code>alpha</code> is
     *                                  outside of the range 0.0 to 1.0
     * @see #getComponents
     * @see #getColorComponents
     */
    public MinecraftColor(ColorSpace cspace, float[] components, float alpha) {
        super(cspace, components, alpha);
    }

    public static final void resetOpenGLColoring() {
        GL11.glColor4f(1F, 1F, 1F, 1F);
    }

    public void performOpenGLColoring() {
        GL11.glColor4f(getRed() / 255F, getGreen() / 255F, getBlue() / 255F, getAlpha() / 255F);
    }

    /**
     * Calculates the Angle of two
     *
     * @return
     */
    public double getAngleInDegrees() {
        Vector2d tRedVec = new Vector2d(getRed() * Math.cos(Math.toRadians(0)), getRed() * Math.sin(Math.toRadians(0)));
        Vector2d tGreenVec = new Vector2d(getGreen() * Math.cos(Math.toRadians(120)), getGreen() * Math.sin(Math.toRadians(120)));
        Vector2d tBlueVec = new Vector2d(getBlue() * Math.cos(Math.toRadians(240)), getBlue() * Math.sin(Math.toRadians(240)));

        Vector2d tColorVec = new Vector2d(tRedVec.x + tBlueVec.x + tGreenVec.x, tRedVec.y + tBlueVec.y + tGreenVec.y);

        if (tColorVec.y == 0) {
            if (tColorVec.x < -10) {
                return 90;
            } else if (tColorVec.x > 10) {
                return 270;
            } else {
                return 0;
            }
        }

        if (tColorVec.x == 0) {
            if (tColorVec.y < -10) {
                return 180;
            } else if (tColorVec.y > 10) {
                return 0;
            } else {
                return 0;
            }
        }

        return 360 - (Math.atan((((float) tColorVec.x) / ((float) tColorVec.y))) * (180 / Math.PI));
    }

}
