package com.smithsmodding.smithscore.client.model.unbaked;

import com.google.common.base.Function;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

import java.util.Collection;
import java.util.Collections;

/**
 * Dummy model to be returned on the initial load to silence the missing model messages.
 * It's never actually used and gets replaced with the real models when the resource manager reloads.
 * <p/>
 * Thanks to TinkersConstruct as a resource of how to load multilayered objects.
 */
public class DummyModel implements IModel {

    public static final DummyModel INSTANCE = new DummyModel();
    public static IBakedModel BAKED_MODEL;

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format,
                            Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        if (BAKED_MODEL != null)
            return BAKED_MODEL;

        BAKED_MODEL = ModelLoaderRegistry.getMissingModel().bake(ModelLoaderRegistry.getMissingModel().getDefaultState(), format, bakedTextureGetter);

        return BAKED_MODEL;
    }

    @Override
    public IModelState getDefaultState() {
        return ModelLoaderRegistry.getMissingModel().getDefaultState();
    }
}
