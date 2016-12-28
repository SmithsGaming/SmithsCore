package com.smithsmodding.smithscore.client.model.baked;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.smithsmodding.smithscore.client.model.data.*;
import com.smithsmodding.smithscore.client.model.states.SmithsCoreOBJState;
import com.smithsmodding.smithscore.client.model.unbaked.SmithsCoreOBJModel;
import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.Models;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author Orion (Created on: 17.07.2016)
 */
public class BakedSmithsCoreOBJModel implements IPerspectiveAwareModel {
    private final SmithsCoreOBJModel model;
    private final VertexFormat format;
    private IModelState state;
    private ImmutableList<BakedQuad> quads;
    private ImmutableMap<String, TextureAtlasSprite> textures;
    private final LoadingCache<IModelState, BakedSmithsCoreOBJModel> cache = CacheBuilder.newBuilder().maximumSize(20).build(new CacheLoader<IModelState, BakedSmithsCoreOBJModel>() {
        @Nonnull
        public BakedSmithsCoreOBJModel load(IModelState state) throws Exception {
            return new BakedSmithsCoreOBJModel(model, state, format, textures);
        }
    });
    private TextureAtlasSprite sprite = ModelLoader.White.INSTANCE;

    public BakedSmithsCoreOBJModel(SmithsCoreOBJModel model, IModelState state, VertexFormat format, ImmutableMap<String, TextureAtlasSprite> textures) {
        this.model = model;
        this.state = state;
        if (this.state instanceof SmithsCoreOBJState) this.updateStateVisibilityMap((SmithsCoreOBJState) this.state);
        this.format = format;
        this.textures = textures;
    }

    public void scheduleRebake() {
    }

    @Override
    public List<BakedQuad> getQuads(IBlockState blockState, @Nullable EnumFacing side, long rand) {
        if (side != null) return ImmutableList.of();
        if (quads == null) {
            quads = buildQuads(this.state);
        }
        if (blockState instanceof IExtendedBlockState) {
            IExtendedBlockState exState = (IExtendedBlockState) blockState;
            if (exState.getUnlistedNames().contains(CoreReferences.BlockStateProperties.Unlisted.OBJSTATE)) {

                SmithsCoreOBJState newState = exState.getValue(CoreReferences.BlockStateProperties.Unlisted.OBJSTATE);
                if (newState != null) {
                    return buildQuads(newState);
                }
            }
        }
        return quads;
    }

    private ImmutableList<BakedQuad> buildQuads(@Nonnull IModelState modelState) {
        List<BakedQuad> quads = Lists.newArrayList();
        Collections.synchronizedSet(new LinkedHashSet<BakedQuad>());
        Set<SmithsCoreOBJFace> faces = Collections.synchronizedSet(new LinkedHashSet<SmithsCoreOBJFace>());
        Optional<TRSRTransformation> transform = Optional.absent();
        for (SmithsCoreOBJGroup g : this.model.getMatLib().getGroups().values()) {
//                g.minUVBounds = this.model.getMatLib().minUVBounds;
//                g.maxUVBounds = this.model.getMatLib().maxUVBounds;
//                FMLLog.info("Group: %s u: [%f, %f] v: [%f, %f]", g.name, g.minUVBounds[0], g.maxUVBounds[0], g.minUVBounds[1], g.maxUVBounds[1]);

            if (modelState.apply(Optional.of(Models.getHiddenModelPart(ImmutableList.of(g.getName())))).isPresent()) {
                continue;
            }
            if (modelState instanceof SmithsCoreOBJState) {
                SmithsCoreOBJState state = (SmithsCoreOBJState) modelState;
                if (state.getParent() != null) {
                    transform = state.getParent().apply(Optional.absent());
                }
                //TODO: can this be replaced by updateStateVisibilityMap(OBJState)?
                if (state.getGroupNamesFromMap().contains(SmithsCoreOBJGroup.ALL)) {
                    state.getVisibilityMap().clear();
                    for (String s : this.model.getMatLib().getGroups().keySet()) {
                        state.getVisibilityMap().put(s, state.getOperation().performOperation(true));
                    }
                } else if (state.getGroupNamesFromMap().contains(SmithsCoreOBJGroup.ALL_EXCEPT)) {
                    List<String> exceptList = state.getGroupNamesFromMap().subList(1, state.getGroupNamesFromMap().size());
                    state.getVisibilityMap().clear();
                    this.model.getMatLib().getGroups().keySet().stream().filter(s -> !exceptList.contains(s)).forEachOrdered(s -> {
                        state.getVisibilityMap().put(s, state.getOperation().performOperation(true));
                    });
                } else {
                    for (String s : state.getVisibilityMap().keySet()) {
                        state.getVisibilityMap().put(s, state.getOperation().performOperation(state.getVisibilityMap().get(s)));
                    }
                }
                if (state.getGroupsWithVisibility(true).contains(g.getName())) {
                    faces.addAll(g.applyTransform(transform));
                }
            } else {
                transform = modelState.apply(Optional.absent());
                faces.addAll(g.applyTransform(transform));
            }
        }
        for (SmithsCoreOBJFace f : faces) {
            if (this.model.getMatLib().getMaterials().get(f.getMaterialName()).isWhite()) {
                for (SmithsCoreOBJVertex v : f.getVertices()) {//update material in each vertex
                    if (!v.getMaterial().equals(this.model.getMatLib().getMaterial(v.getMaterial().getName()))) {
                        v.setMaterial(this.model.getMatLib().getMaterial(v.getMaterial().getName()));
                    }
                }
                sprite = ModelLoader.White.INSTANCE;
            } else sprite = this.textures.get(f.getMaterialName());
            UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
            builder.setContractUVs(true);
            builder.setQuadOrientation(EnumFacing.getFacingFromVector(f.getNormal().x, f.getNormal().y, f.getNormal().z));
            builder.setTexture(sprite);
            SmithsCoreOBJNormal faceNormal = f.getNormal();
            putVertexData(builder, f.getVertices()[0], faceNormal, SmithsCoreOBJTextureCoordinate.getDefaultUVs()[0], sprite);
            putVertexData(builder, f.getVertices()[1], faceNormal, SmithsCoreOBJTextureCoordinate.getDefaultUVs()[1], sprite);
            putVertexData(builder, f.getVertices()[2], faceNormal, SmithsCoreOBJTextureCoordinate.getDefaultUVs()[2], sprite);
            putVertexData(builder, f.getVertices()[3], faceNormal, SmithsCoreOBJTextureCoordinate.getDefaultUVs()[3], sprite);
            quads.add(builder.build());
        }
        return ImmutableList.copyOf(quads);
    }

    private final void putVertexData(@Nonnull UnpackedBakedQuad.Builder builder, @Nonnull SmithsCoreOBJVertex v, @Nonnull SmithsCoreOBJNormal faceNormal, @Nonnull SmithsCoreOBJTextureCoordinate defUV, @Nonnull TextureAtlasSprite sprite) {
        for (int e = 0; e < format.getElementCount(); e++) {
            switch (format.getElement(e).getUsage()) {
                case POSITION:
                    builder.put(e, v.getPos().x, v.getPos().y, v.getPos().z, v.getPos().w);
                    break;
                case COLOR:
                    if (v.getMaterial() != null)
                        builder.put(e,
                                v.getMaterial().getColor().x,
                                v.getMaterial().getColor().y,
                                v.getMaterial().getColor().z,
                                v.getMaterial().getColor().w);
                    else
                        builder.put(e, 1, 1, 1, 1);
                    break;
                case UV:
                    if (!v.hasTextureCoordinate())
                        builder.put(e,
                                sprite.getInterpolatedU(defUV.u * 16),
                                sprite.getInterpolatedV((model.getCustomData().flipV ? 1 - defUV.v : defUV.v) * 16),
                                0, 1);
                    else
                        builder.put(e,
                                sprite.getInterpolatedU(v.getTextureCoordinate().u * 16),
                                sprite.getInterpolatedV((model.getCustomData().flipV ? 1 - v.getTextureCoordinate().v : v.getTextureCoordinate().v) * 16),
                                0, 1);
                    break;
                case NORMAL:
                    if (!v.hasNormal())
                        builder.put(e, faceNormal.x, faceNormal.y, faceNormal.z, 0);
                    else
                        builder.put(e, v.getNormal().x, v.getNormal().y, v.getNormal().z, 0);
                    break;
                default:
                    builder.put(e);
            }
        }
    }

    @Override
    public boolean isAmbientOcclusion() {
        return model == null || model.getCustomData().ambientOcclusion;
    }

    @Override
    public boolean isGui3d() {
        return model == null || model.getCustomData().gui3d;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.sprite;
    }

    // FIXME: merge with getQuads
        /* @Override
        public OBJBakedModel handleBlockState(IBlockState state)
        {
            if (state instanceof IExtendedBlockState)
            {
                IExtendedBlockState exState = (IExtendedBlockState) state;
                if (exState.getUnlistedNames().contains(OBJProperty.instance))
                {
                    OBJState s = exState.getValue(OBJProperty.instance);
                    if (s != null)
                    {
                        if (s.visibilityMap.containsKey(Group.ALL) || s.visibilityMap.containsKey(Group.ALL_EXCEPT))
                        {
                            this.updateStateVisibilityMap(s);
                        }
                        return getCachedModel(s);
                    }
                }
            }
            return this;
        }*/

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    private void updateStateVisibilityMap(@Nonnull SmithsCoreOBJState state) {
        if (state.getVisibilityMap().containsKey(SmithsCoreOBJGroup.ALL)) {
            boolean operation = state.getVisibilityMap().get(SmithsCoreOBJGroup.ALL);
            state.getVisibilityMap().clear();
            for (String s : this.model.getMatLib().getGroups().keySet()) {
                state.getVisibilityMap().put(s, state.getOperation().performOperation(operation));
            }
        } else if (state.getVisibilityMap().containsKey(SmithsCoreOBJGroup.ALL_EXCEPT)) {
            List<String> exceptList = state.getGroupNamesFromMap().subList(1, state.getGroupNamesFromMap().size());
            state.getVisibilityMap().remove(SmithsCoreOBJGroup.ALL_EXCEPT);
            this.model.getMatLib().getGroups().keySet().stream().filter(s -> !exceptList.contains(s)).forEachOrdered(s -> {
                state.getVisibilityMap().put(s, state.getOperation().performOperation(state.getVisibilityMap().get(s)));
            });
        } else {
            for (String s : state.getVisibilityMap().keySet()) {
                state.getVisibilityMap().put(s, state.getOperation().performOperation(state.getVisibilityMap().get(s)));
            }
        }
    }

    public BakedSmithsCoreOBJModel getCachedModel(@Nonnull IModelState state) {
        return cache.getUnchecked(state);
    }

    public SmithsCoreOBJModel getModel() {
        return this.model;
    }

    public IModelState getState() {
        return this.state;
    }

    @Nonnull
    public BakedSmithsCoreOBJModel getBakedModel() {
        return new BakedSmithsCoreOBJModel(this.model, this.state, this.format, this.textures);
    }

    @Nonnull
    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(@Nonnull ItemCameraTransforms.TransformType cameraTransformType) {
        return IPerspectiveAwareModel.MapWrapper.handlePerspective(this, state, cameraTransformType);
    }

    @Nonnull
    @Override
    public String toString() {
        return this.model.getModelLocation().toString();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }
}