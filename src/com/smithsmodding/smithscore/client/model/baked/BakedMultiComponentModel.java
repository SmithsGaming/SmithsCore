package com.smithsmodding.smithscore.client.model.baked;

import com.smithsmodding.smithscore.client.model.capability.CapabilityComponentModel;
import com.smithsmodding.smithscore.client.model.overrides.PreBakedItemOverride;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

/**
 * @Author Marc (Created on: 29.05.2016)
 */
public class BakedMultiComponentModel implements IBakedModel {
    final TextureAtlasSprite particleTexture;
    final ItemCameraTransforms transforms;
    final ItemOverrideList overrides;

    public BakedMultiComponentModel(TextureAtlasSprite particleTexture, ItemCameraTransforms transforms, ItemOverrideList overrides) {
        this.particleTexture = particleTexture;
        this.transforms = transforms;
        this.overrides = overrides;
    }

    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        return DummyModel.BAKED_MODEL.getQuads(state, side, rand);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
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
        return particleTexture;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return transforms;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }

    public static class BakedComponentModelItemOverride extends PreBakedItemOverride {
        private final String modelType;

        public BakedComponentModelItemOverride(IBakedModel model, String modelType) {
            super(model);
            this.modelType = modelType;
        }

        @Override
        public boolean matchedItemStack(ItemStack stack, World world, EntityLivingBase entity) {
            return stack.hasCapability(CapabilityComponentModel.COMPONENTMODEL_CAPABILITY, null) && stack.getCapability(CapabilityComponentModel.COMPONENTMODEL_CAPABILITY, null).getModelType().equals(modelType);
        }
    }
}
