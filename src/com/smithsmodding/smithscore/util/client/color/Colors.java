/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.client.color;
/*
*   Colors
*   Created by: Orion
*   Created on: 27-6-2014
*/

import javax.annotation.Nonnull;

/**
 * General color Dictionary for smithsmodding
 */
public final class Colors {
    //White
    @Nonnull
    public static MinecraftColor DEFAULT = new MinecraftColor(255, 255, 255, 255);

    /**
     * General colors dictionary for Omni Purpose use.
     */
    public static class General {
        @Nonnull
        public static MinecraftColor RED = new MinecraftColor(MinecraftColor.RED);
        @Nonnull
        public static MinecraftColor GREEN = new MinecraftColor(MinecraftColor.GREEN);
        @Nonnull
        public static MinecraftColor BLUE = new MinecraftColor(MinecraftColor.BLUE);
        @Nonnull
        public static MinecraftColor YELLOW = new MinecraftColor(MinecraftColor.YELLOW);
        @Nonnull
        public static MinecraftColor ORANGE = new MinecraftColor(MinecraftColor.ORANGE);
        @Nonnull
        public static MinecraftColor LIGHTBLUE = new MinecraftColor(120, 158, 255);
        @Nonnull
        public static MinecraftColor LICHTGREEN = new MinecraftColor(102, 255, 122);
        @Nonnull
        public static MinecraftColor GRAY = new MinecraftColor(MinecraftColor.GRAY);
        @Nonnull
        public static MinecraftColor BROWN = new MinecraftColor(136, 65, 0);
        @Nonnull
        public static MinecraftColor PAPERYELLOW = new MinecraftColor(231, 204, 134);

        @Nonnull
        public static MinecraftColor ELECTRICBLUE = new MinecraftColor(45, 206, 250);
    }

    /**
     * color Dictionary for Rendering Experience.
     */
    public static class Experience {
        @Nonnull
        public static MinecraftColor ORB = new MinecraftColor(189, 255, 0);
        @Nonnull
        public static MinecraftColor TEXT = new MinecraftColor(128, 255, 32);
    }
}
