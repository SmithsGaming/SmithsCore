package com.smithsmodding.smithscore.client.model.overrides;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Author Marc (Created on: 04.06.2016)
 */
public class PreBakedComponentOverrideList extends ItemOverrideList {

    private final ImmutableList<PreBakedItemOverride> overrides;

    public PreBakedComponentOverrideList(@Nonnull ImmutableList<PreBakedItemOverride> overrides) {
        super(new ArrayList<>());

        this.overrides = overrides;
    }

    @Override
    @Nonnull
    public ImmutableList<ItemOverride> getOverrides() {
        ImmutableList.Builder<ItemOverride> overrideBuilder = new ImmutableList.Builder<>();
        overrideBuilder.addAll(overrides);

        return overrideBuilder.build();
    }

    @Override
    @Nonnull
    public IBakedModel handleItemState(@Nonnull IBakedModel originalModel, @Nonnull ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
        for (PreBakedItemOverride override : overrides)
            if (override.matchedItemStack(stack, world, entity))
                return override.getModel();

        return originalModel;
    }
}
