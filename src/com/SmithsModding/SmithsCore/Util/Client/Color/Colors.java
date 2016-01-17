/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.Client.Color;
/*
*   Colors
*   Created by: Orion
*   Created on: 27-6-2014
*/

/**
 * General Color Dictionary for smithsmodding
 */
public final class Colors {
    //White
    public static MinecraftColor DEFAULT = new MinecraftColor(255, 255, 255, 255);

    /**
     * General colors dictionary for Omni Purpose use.
     */
    public static class General {
        public static MinecraftColor RED = (MinecraftColor) MinecraftColor.RED;
        public static MinecraftColor GREEN = (MinecraftColor) MinecraftColor.GREEN;
        public static MinecraftColor BLUE = (MinecraftColor) MinecraftColor.BLUE;
        public static MinecraftColor YELLOW = (MinecraftColor) MinecraftColor.YELLOW;
        public static MinecraftColor ORANGE = (MinecraftColor) MinecraftColor.ORANGE;
        public static MinecraftColor LIGHTBLUE = new MinecraftColor(120, 158, 255);
        public static MinecraftColor LICHTGREEN = new MinecraftColor(102, 255, 122);
        public static MinecraftColor GRAY = (MinecraftColor) MinecraftColor.GRAY;
        public static MinecraftColor BROWN = new MinecraftColor(136, 65, 0);
        public static MinecraftColor PAPERYELLOW = new MinecraftColor(231, 204, 134);

        public static MinecraftColor ELECTRICBLUE = new MinecraftColor(45, 206, 250);
    }

    /**
     * Color Dictionary for Rendering Experience.
     */
    public static class Experience {
        public static MinecraftColor ORB = new MinecraftColor(189, 255, 0);
        public static MinecraftColor TEXT = new MinecraftColor(128, 255, 32);
    }
}
