package com.SmithsModding.SmithsCore.Util.Common;

import net.minecraftforge.fluids.*;

/**
 * Created by Marc on 30.12.2015.
 */
public class FluidStackHelper {

    public static boolean equalsIgnoreStackSize (FluidStack fluidStack1, FluidStack fluidStack2) {
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

    public static boolean areFluidStackTagsEqual (FluidStack stackA, FluidStack stackB) {
        return stackA == null && stackB == null ? true : ( stackA != null && stackB != null ? ( stackA.tag == null && stackB.tag != null ? false : stackA.tag == null || stackA.tag.equals(stackB.tag) ) : false );
    }
}
