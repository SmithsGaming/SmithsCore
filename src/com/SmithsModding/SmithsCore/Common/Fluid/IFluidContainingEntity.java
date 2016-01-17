package com.smithsmodding.smithscore.common.fluid;

import net.minecraftforge.fluids.*;

import java.util.*;

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
}
