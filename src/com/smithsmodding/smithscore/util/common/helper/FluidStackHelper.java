package com.smithsmodding.smithscore.util.common.helper;

import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

/**
 * Created by Marc on 30.12.2015.
 */
public class FluidStackHelper {

    public static boolean equalsIgnoreStackSize (@Nullable FluidStack fluidStack1, @Nullable FluidStack fluidStack2) {
        if (fluidStack1 != null && fluidStack2 != null) {
            // Sort on itemID
            if (fluidStack1.getFluid().hashCode() == fluidStack2.getFluid().hashCode()) {
                // Then sort on NBT
                if (( fluidStack1.tag != null ) && fluidStack2.tag != null) {
                    // Then sort on stack size
                    if (FluidStackHelper.areFluidStackTagsEqual(fluidStack1, fluidStack2)) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean areFluidStackTagsEqual (@Nullable FluidStack stackA, @Nullable FluidStack stackB) {
        return stackA == null && stackB == null || ((stackA != null && stackB != null) && (!(stackA.tag == null && stackB.tag != null) && (stackA.tag == null || stackA.tag.equals(stackB.tag))));
    }
}
