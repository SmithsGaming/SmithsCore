package com.smithsmodding.smithscore.client.model.overrides;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.HashMap;

/**
 * Author Marc (Created on: 04.06.2016)
 */
public abstract class PreBakedItemOverride extends ItemOverride {

    private final IBakedModel model;

    public PreBakedItemOverride(IBakedModel model) {
        super(new ResourceLocation(""), new HashMap<>());

        this.model = model;
    }

    public IBakedModel getModel() {
        return model;
    }

    public abstract boolean matchedItemStack(ItemStack stack, World world, EntityLivingBase entity);
}
