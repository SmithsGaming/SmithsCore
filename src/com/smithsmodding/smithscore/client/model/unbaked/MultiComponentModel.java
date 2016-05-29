package com.smithsmodding.smithscore.client.model.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

import java.util.Collection;
import java.util.Collections;

/**
 * @Author Marc (Created on: 29.05.2016)
 */
public class MultiComponentModel implements IModel {

    final ImmutableList<IModel> components;

    public MultiComponentModel(ImmutableList<IModel> components) {
        this.components = components;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        ImmutableList.Builder<ResourceLocation> builder = new ImmutableList.Builder<ResourceLocation>();
        for (IModel component : components)
            builder.addAll(component.getTextures());

        return builder.build();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return null;
    }

    @Override
    public IModelState getDefaultState() {
        return ModelHelper.DEFAULT_ITEM_STATE;
    }
}
