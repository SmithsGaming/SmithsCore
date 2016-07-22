package com.smithsmodding.smithscore.common.fluid;

import com.google.common.collect.Lists;
import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Author Orion (Created on: 22.07.2016)
 */
public class MultiFluidTank implements IFluidTank, net.minecraftforge.fluids.capability.IFluidHandler, INBTSerializable<NBTTagCompound> {

    private List<FluidStack> fluidStacks;
    private int capacity;

    public MultiFluidTank(int capacity) {
        this.fluidStacks = Lists.newArrayList();
        this.capacity = capacity;
    }

    public MultiFluidTank(int capacity, FluidStack... fluidStacks) {
        this(capacity, Arrays.asList(fluidStacks));
    }

    public MultiFluidTank(int capacity, List<FluidStack> fluidStacks) {
        this.fluidStacks = fluidStacks;
        this.capacity = capacity;

        if (capacity < getFluidAmount()) {
            int count = 0;

            Iterator<FluidStack> iterator = this.fluidStacks.iterator();
            while (iterator.hasNext()) {
                FluidStack stack = iterator.next();
                if (count + stack.amount > capacity) {
                    iterator.remove();
                    continue;
                }

                count += stack.amount;
            }
        }
    }

    @Nullable
    @Override
    public FluidStack getFluid() {
        if (fluidStacks.size() == 0)
            return null;

        return fluidStacks.get(0);
    }

    @Override
    public int getFluidAmount() {
        int count = 0;

        for (FluidStack stack : fluidStacks)
            count += stack.amount;

        return count;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public FluidTankInfo getInfo() {
        return null;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[0];
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        int usage = Math.min(getFluidAmount(), resource.amount);

        if (!doFill)
            return usage;

        for (FluidStack stack : fluidStacks) {
            if (stack.isFluidEqual(resource)) {
                stack.amount += usage;
                return usage;
            }
        }

        FluidStack usedFluid = new FluidStack(resource, usage);
        fluidStacks.add(usedFluid);

        return usage;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        Iterator<FluidStack> iterator = fluidStacks.iterator();
        while (iterator.hasNext()) {
            FluidStack stack = iterator.next();

            if (stack.isFluidEqual(resource)) {
                FluidStack usage = new FluidStack(resource, Math.min(stack.amount, resource.amount));

                if (doDrain) {
                    stack.amount -= usage.amount;
                    if (stack.amount == 0)
                        iterator.remove();
                }

                return usage;
            }
        }

        return null;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (fluidStacks.size() == 0)
            return null;

        FluidStack stack = fluidStacks.get(0);
        FluidStack usedStack = new FluidStack(stack, Math.min(stack.amount, maxDrain));

        if (doDrain) {
            stack.amount -= usedStack.amount;
            if (stack.amount == 0) {
                fluidStacks.remove(0);
            }
        }

        return usedStack;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList contents = new NBTTagList();

        for (FluidStack stack : fluidStacks) {
            contents.appendTag(stack.writeToNBT(new NBTTagCompound()));
        }

        compound.setTag(CoreReferences.NBT.MultiFluidTank.CONTENTS, contents);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        fluidStacks = new ArrayList<>();

        NBTTagList contents = nbt.getTagList(CoreReferences.NBT.MultiFluidTank.CONTENTS, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < contents.tagCount(); i++) {
            fluidStacks.add(FluidStack.loadFluidStackFromNBT(contents.getCompoundTagAt(i)));
        }

        if (capacity < getFluidAmount()) {
            int count = 0;

            Iterator<FluidStack> iterator = this.fluidStacks.iterator();
            while (iterator.hasNext()) {
                FluidStack stack = iterator.next();
                if (count + stack.amount > capacity) {
                    iterator.remove();
                    continue;
                }

                count += stack.amount;
            }
        }
    }

    public List<FluidStack> getFluidStacks() {
        return fluidStacks;
    }
}
