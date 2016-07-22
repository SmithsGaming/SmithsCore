package com.smithsmodding.smithscore.common.fluid;

import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

/**
 * Created by Marc on 20.12.2015.
 */
public interface IFluidContainingEntity {
    IFluidTank getTankForSide(@Nullable Side side);

    int getTotalTankSizeOnSide(@Nullable Side side);

    int getTankContentsVolumeOnSide(@Nullable Side side);
}
