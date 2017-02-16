package com.smithsmodding.smithscore.common.fluid;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Marc on 20.12.2015.
 */
public interface IFluidContainingEntity {

    default boolean requiresNBTStorage(EnumFacing side) {
        return true;
    }

    @Nonnull
    IFluidTank getTankForSide(@Nullable EnumFacing side);

    int getTotalTankSizeOnSide(@Nullable EnumFacing side);

    int getTankContentsVolumeOnSide(@Nullable EnumFacing side);
}
