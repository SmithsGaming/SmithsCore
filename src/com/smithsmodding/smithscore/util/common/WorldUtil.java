package com.smithsmodding.smithscore.util.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author Orion (Created on: 04.07.2016)
 */
public class WorldUtil {

    public static IBlockState getBlockStateForSideAndFacing(World world, BlockPos blockPos, EnumFacing hitFacing, EnumFacing direction) {
        return world.getBlockState(getBlockPosForPerspective(blockPos, hitFacing, direction));
    }

    public static BlockPos getBlockPosForPerspective(BlockPos pos, EnumFacing hitFacing, EnumFacing direction) {
        switch (hitFacing) {
            case NORTH:
                return getBlockPosForNorthHit(pos, direction);
            case SOUTH:
                return getBlockPosForSouthHit(pos, direction);
            case WEST:
                return getBlockPosForWestHit(pos, direction);
            case EAST:
                return getBlockPosForEastHit(pos, direction);
            default:
                return pos;
        }
    }

    private static BlockPos getBlockPosForNorthHit(BlockPos pos, EnumFacing direction) {
        switch (direction) {
            case NORTH:
                return pos.offset(EnumFacing.SOUTH, 1);
            case SOUTH:
                return pos.offset(EnumFacing.NORTH, 1);
            case WEST:
                return pos.offset(EnumFacing.EAST, 1);
            case EAST:
                return pos.offset(EnumFacing.WEST, 1);
            default:
                return pos.offset(direction, 1);
        }
    }

    private static BlockPos getBlockPosForSouthHit(BlockPos pos, EnumFacing direction) {
        return pos.offset(direction, 1);
    }

    private static BlockPos getBlockPosForEastHit(BlockPos pos, EnumFacing direction) {
        switch (direction) {
            case NORTH:
                return pos.offset(EnumFacing.WEST, 1);
            case SOUTH:
                return pos.offset(EnumFacing.EAST, 1);
            case WEST:
                return pos.offset(EnumFacing.NORTH, 1);
            case EAST:
                return pos.offset(EnumFacing.SOUTH, 1);
            default:
                return pos.offset(direction, 1);
        }
    }

    private static BlockPos getBlockPosForWestHit(BlockPos pos, EnumFacing direction) {
        switch (direction) {
            case NORTH:
                return pos.offset(EnumFacing.EAST, 1);
            case SOUTH:
                return pos.offset(EnumFacing.WEST, 1);
            case WEST:
                return pos.offset(EnumFacing.NORTH, 1);
            case EAST:
                return pos.offset(EnumFacing.SOUTH, 1);
            default:
                return pos.offset(direction, 1);
        }
    }
}
