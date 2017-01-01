package com.smithsmodding.smithscore.client.model.deserializers.definition;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Author Marc (Created on: 28.05.2016)
 */
public class MultiComponentModelDefinition {

    @Nonnull
    final Map<String, ResourceLocation> textureLocations;
    final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public MultiComponentModelDefinition(@Nonnull Map<String, ResourceLocation> textureLocations, @Nonnull Map<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        this.textureLocations = textureLocations;
        this.transforms = ImmutableMap.copyOf(transforms);

        if (!textureLocations.containsKey("default"))
            throw new IllegalArgumentException("Cannot create a MultiComponentModelDefinition without a 'default' component!");
    }

    @Nonnull
    public ImmutableMap<String, ResourceLocation> getTextureLocations() {
        return ImmutableMap.copyOf(textureLocations);
    }

    @Nonnull
    public ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms() {
        return transforms;
    }
}
