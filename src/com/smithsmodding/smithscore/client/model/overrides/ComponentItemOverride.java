package com.smithsmodding.smithscore.client.model.overrides;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

/**
 * @Author Marc (Created on: 04.06.2016)
 */
public class ComponentItemOverride extends ItemOverride {

    private final String modelType;
    private final IBakedModel model;

    public ComponentItemOverride(String modelType, IBakedModel model) {
        super(new ResourceLocation(""), new HashMap<>());

        this.modelType = modelType;
        this.model = model;
    }

    public String getModelType() {
        return modelType;
    }

    public IBakedModel getModel() {
        return model;
    }
}
