package com.smithsmodding.smithscore.common;

import com.smithsmodding.smithscore.client.model.capability.CapabilityComponentModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * @Author Marc (Created on: 05.06.2016)
 */
public abstract class ItemComponentModel extends Item {

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new CapabilityComponentModel.DefaultComponentModelProviderCapabilityProvider(new CapabilityComponentModel.DefaultComponentModelTypeProvider(getModelType()));
    }

    public abstract String getModelType();
}
