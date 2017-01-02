package com.smithsmodding.smithscore.util.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 04.07.2016)
 */
public class WorldUtil {

    @Nonnull
    public static IBlockState getBlockStateForSideAndFacing(@Nonnull World world, @Nonnull BlockPos blockPos, @Nonnull EnumFacing hitFacing, @Nonnull EnumFacing direction) {
        return world.getBlockState(getBlockPosForPerspective(blockPos, hitFacing, direction));
    }

    @Nonnull
    public static BlockPos getBlockPosForPerspective(@Nonnull BlockPos pos, @Nonnull EnumFacing hitFacing, @Nonnull EnumFacing direction) {
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

    @Nonnull
    private static BlockPos getBlockPosForNorthHit(@Nonnull BlockPos pos, @Nonnull EnumFacing direction) {
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

    @Nonnull
    private static BlockPos getBlockPosForSouthHit(@Nonnull BlockPos pos, @Nonnull EnumFacing direction) {
        return pos.offset(direction, 1);
    }

    @Nonnull
    private static BlockPos getBlockPosForEastHit(@Nonnull BlockPos pos, @Nonnull EnumFacing direction) {
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

    @Nonnull
    private static BlockPos getBlockPosForWestHit(@Nonnull BlockPos pos, @Nonnull EnumFacing direction) {
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
