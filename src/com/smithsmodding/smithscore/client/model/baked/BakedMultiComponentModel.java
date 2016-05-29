package com.smithsmodding.smithscore.client.model.baked;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

import java.util.List;

/**
 * @Author Marc (Created on: 29.05.2016)
 */
public class BakedMultiComponentModel implements IBakedModel {

    final ImmutableList<IBakedModel> bakedComponents;
    final ImmutableList<BakedQuad> quads;
    final TextureAtlasSprite particleTexture;
    final ItemCameraTransforms transforms;
    final ItemOverrideList overrides;

    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        return quads;
    }

    @Override
    public boolean isAmbientOcclusion() {

    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return null;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }
}
