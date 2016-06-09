package com.smithsmodding.smithscore.client.model.deserializers.definition;

import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

/**
 * @Author Marc (Created on: 28.05.2016)
 */
public class MultiComponentModelDefinition {

    final Map<String, ResourceLocation> textureLocations;

    public MultiComponentModelDefinition(Map<String, ResourceLocation> textureLocations) {
        this.textureLocations = textureLocations;

        if (!textureLocations.containsKey("default"))
            throw new IllegalArgumentException("Cannot create a MultiComponentModelDefinition without a 'default' component!");
    }

    public ImmutableMap<String, ResourceLocation> getTextureLocations() {
        return ImmutableMap.copyOf(textureLocations);
    }
}
