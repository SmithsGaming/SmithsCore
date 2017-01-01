package com.smithsmodding.smithscore.client.model.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.client.model.baked.BakedSmithsCoreOBJModel;
import com.smithsmodding.smithscore.client.model.data.SmithsCoreOBJCustomData;
import com.smithsmodding.smithscore.client.model.data.SmithsCoreOBJMaterial;
import com.smithsmodding.smithscore.client.model.data.SmithsCoreOBJMaterialLibrary;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IModelCustomData;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import java.util.*;

/*
 * Modified version of the MC Forge OBJLoader used until hiding of groups is supported.
 * Licensed under its License.
 */
public class SmithsCoreOBJModel implements IRetexturableModel, IModelCustomData {
    private final ResourceLocation modelLocation;
    //private Gson GSON = new GsonBuilder().create();
    private SmithsCoreOBJMaterialLibrary matLib;
    private SmithsCoreOBJCustomData customData;

    public SmithsCoreOBJModel(@Nonnull SmithsCoreOBJMaterialLibrary matLib, @Nonnull ResourceLocation modelLocation) {
        this(matLib, modelLocation, new SmithsCoreOBJCustomData());
    }

    public SmithsCoreOBJModel(@Nonnull SmithsCoreOBJMaterialLibrary matLib, @Nonnull ResourceLocation modelLocation, @Nonnull SmithsCoreOBJCustomData customData) {
        this.matLib = matLib;
        this.modelLocation = modelLocation;
        this.customData = customData;
    }

    @Nonnull
    public ResourceLocation getModelLocation() {
        return modelLocation;
    }

    @Nonnull
    public SmithsCoreOBJCustomData getCustomData() {
        return customData;
    }

    @Nonnull
    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptyList();
    }

    @Nonnull
    @Override
    public Collection<ResourceLocation> getTextures() {
        Iterator<SmithsCoreOBJMaterial> materialIterator = this.matLib.getMaterials().values().iterator();
        List<ResourceLocation> textures = Lists.newArrayList();
        while (materialIterator.hasNext()) {
            SmithsCoreOBJMaterial mat = materialIterator.next();
            ResourceLocation textureLoc = new ResourceLocation(mat.getTexture().getPath());
            if (!textures.contains(textureLoc) && !mat.isWhite())
                textures.add(textureLoc);
        }
        return textures;
    }

    @Nonnull
    @Override
    public IBakedModel bake(@Nonnull IModelState state, @Nonnull VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        ImmutableMap.Builder<String, TextureAtlasSprite> builder = ImmutableMap.builder();
        builder.put(ModelLoader.White.LOCATION.toString(), ModelLoader.White.INSTANCE);
        TextureAtlasSprite missing = bakedTextureGetter.apply(new ResourceLocation("missingno"));
        for (Map.Entry<String, SmithsCoreOBJMaterial> e : matLib.getMaterials().entrySet()) {
            if (e.getValue().getTexture().getTextureLocation().getResourcePath().startsWith("#")) {
                SmithsCore.getLogger().error("OBJLoader: Unresolved texture '%s' for obj model '%s'", e.getValue().getTexture().getTextureLocation().getResourcePath(), modelLocation);
                builder.put(e.getKey(), missing);
            } else {
                builder.put(e.getKey(), bakedTextureGetter.apply(e.getValue().getTexture().getTextureLocation()));
            }
        }
        builder.put("missingno", missing);
        return new BakedSmithsCoreOBJModel(this, state, format, builder.build());
    }

    public SmithsCoreOBJMaterialLibrary getMatLib() {
        return this.matLib;
    }

    @Nonnull
    @Override
    public IModel process(@Nonnull ImmutableMap<String, String> customData) {
        SmithsCoreOBJModel ret = new SmithsCoreOBJModel(this.matLib, this.modelLocation, new SmithsCoreOBJCustomData(this.customData, customData));
        return ret;
    }

    @Nonnull
    @Override
    public IModel retexture(@Nonnull ImmutableMap<String, String> textures) {
        SmithsCoreOBJModel ret = new SmithsCoreOBJModel(this.matLib.makeLibWithReplacements(textures), this.modelLocation, this.customData);
        return ret;
    }

    @Override
    @Nonnull
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }

    @SuppressWarnings("serial")
    public static class UVsOutOfBoundsException extends RuntimeException {
        public ResourceLocation modelLocation;

        public UVsOutOfBoundsException(@Nonnull ResourceLocation modelLocation) {
            super(String.format("Model '%s' has UVs ('vt') out of bounds 0-1! The missing model will be used instead. Support for UV processing will be added to the OBJ loader in the future.", modelLocation));
            this.modelLocation = modelLocation;
        }
    }
}