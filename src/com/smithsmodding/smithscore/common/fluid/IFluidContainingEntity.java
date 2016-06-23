package com.smithsmodding.smithscore.common.fluid;

import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

/**
 * Created by Marc on 20.12.2015.
 */
public interface IFluidContainingEntity {
    ArrayList<FluidStack> getAllFluids ();

    void setAllFluids (ArrayList<FluidStack> stacks);

    FluidStack removeFirstFluid ();

    FluidStack removeLastFluid ();

    void addFluidToTheBottom (FluidStack stack);

    void addFluidToTheTop (FluidStack stack);

    void addFluid(FluidStack stack);

    int getTankSize ();

    int getTankContentsVolume();

}
