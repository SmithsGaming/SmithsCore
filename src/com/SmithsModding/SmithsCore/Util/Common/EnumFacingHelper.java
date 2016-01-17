/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.Common;
/*
 *   EnumFacingHelper
 *   Created by: Orion
 *   Created on: 13-1-2015
 */

import net.minecraft.util.EnumFacing;

public class EnumFacingHelper {
    //{DOWN, UP, NORTH, SOUTH, WEST, EAST}
    public static int ConvertToInt(EnumFacing pDirection) {
        switch (pDirection) {
            case DOWN:
                return 0;
            case UP:
                return 1;
            case NORTH:
                return 2;
            case SOUTH:
                return 3;
            case WEST:
                return 4;
            case EAST:
                return 5;
            default:
                return -1;
        }
    }
}
