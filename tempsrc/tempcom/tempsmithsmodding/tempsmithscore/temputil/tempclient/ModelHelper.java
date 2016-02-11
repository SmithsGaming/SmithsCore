package com.smithsmodding.smithscore.util.client;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import com.smithsmodding.smithscore.util.client.color.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.pipeline.*;

import javax.vecmath.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by Marc on 06.12.2015.
 * <p/>
 * The original ModelHelper comes from TinkersConstruct.
 * Added some special Case Objects for smithsmodding.
 */
public class ModelHelper {

    public static final IModelState DEFAULT_ITEM_STATE;
    public static final IModelState DEFAULT_TOOL_STATE;
    static final Type maptype = new TypeToken<Map<String, String>>() {
    }.getType();
    private static final Gson
            GSON =
            new GsonBuilder().registerTypeAdapter(maptype, ModelTextureDeserializer.INSTANCE).create();

    static {
        {
            // equals forge:default-item
            TRSRTransformation thirdperson = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
                    new Vector3f(0, 1f / 16, -3f / 16),
                    TRSRTransformation.quatFromYXZDegrees(new Vector3f(-90, 0, 0)),
                    new Vector3f(0.55f, 0.55f, 0.55f),
                    null));
            TRSRTransformation firstperson = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
                    new Vector3f(0, 4f / 16, 2f / 16),
                    TRSRTransformation.quatFromYXZDegrees(new Vector3f(0, -135, 25)),
                    new Vector3f(1.7f, 1.7f, 1.7f),
                    null));
            DEFAULT_ITEM_STATE = new SimpleModelState(ImmutableMap.of(ItemCameraTransforms.TransformType.THIRD_PERSON, thirdperson, ItemCameraTransforms.TransformType.FIRST_PERSON, firstperson));
        }
        {
            TRSRTransformation thirdperson = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
                    new Vector3f(0, 1.25f / 16, -3.5f / 16),
                    TRSRTransformation.quatFromYXZDegrees(new Vector3f(0, 90, -35)),
                    new Vector3f(0.85f, 0.85f, 0.85f),
                    null));
            TRSRTransformation firstperson = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
                    new Vector3f(0, 4f / 16, 2f / 16),
                    TRSRTransformation.quatFromYXZDegrees(new Vector3f(0, -135, 25)),
                    new Vector3f(1.7f, 1.7f, 1.7f),
                    null));
            DEFAULT_TOOL_STATE = new SimpleModelState(ImmutableMap.of(ItemCameraTransforms.TransformType.THIRD_PERSON, thirdperson, ItemCameraTransforms.TransformType.FIRST_PERSON, firstperson));
        }
    }


    public static TextureAtlasSprite getTextureFromBlock (Block block, int meta) {
        IBlockState state = block.getStateFromMeta(meta);
        return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
    }

    public static TextureAtlasSprite getTextureFromBlockstate (IBlockState state) {
        return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
    }

    public static BakedQuad colorQuad (MinecraftColor color, BakedQuad quad) {
        int[] data = quad.getVertexData();

        int a = (color.getRGB() >> 24);
        if (a == 0) {
            a = 255;
        }
        int r = (color.getRGB() >> 16) & 0xFF;
        int g = (color.getRGB() >> 8) & 0xFF;
        int b = (color.getRGB() >> 0) & 0xFF;

        int c = r | g << 8 | b << 16 | a << 24;

        // update color in the data. all 4 Vertices.
        for (int i = 0; i < 4; i++) {
            data[i * 7 + 3] = c;
        }

        return new IColoredBakedQuad.ColoredBakedQuad(data, -1, quad.getFace());
    }

    public static Map<String, String> loadTexturesFromJson (ResourceLocation location) throws IOException {
        // get the json
        IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(location.getResourceDomain(), location.getResourcePath() + ".json"));
        Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

        return GSON.fromJson(reader, maptype);
    }

    public static ImmutableList<ResourceLocation> loadTextureListFromJson (ResourceLocation location) throws IOException {
        ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
        for (String s : loadTexturesFromJson(location).values()) {
            builder.add(new ResourceLocation(s));
        }

        return builder.build();
    }

    public static ResourceLocation getModelLocation (ResourceLocation location) {
        return new ResourceLocation(location.getResourceDomain(), "models/" + location.getResourcePath() + ".json");
    }

    public static float[][][] getUnpackedQuadData (UnpackedBakedQuad quad, VertexFormat format) {
        int[] vertexData = quad.getVertexData();
        float[][][] unpackedData = new float[4][format.getElementCount()][4];

        for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++) {
            for (int vertexElementTypeIndex = 0; vertexElementTypeIndex < format.getElementCount(); vertexElementTypeIndex++) {
                LightUtil.unpack(vertexData, unpackedData[vertexIndex][vertexElementTypeIndex], format, vertexIndex, vertexElementTypeIndex);
            }
        }

        return unpackedData;
    }

    public static int[] getPackedQuadData (float[][][] unpackedData, VertexFormat format) {
        int[] vertexData = new int[format.getNextOffset()];

        for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++) {
            for (int vertexElementTypeIndex = 0; vertexElementTypeIndex < format.getElementCount(); vertexElementTypeIndex++) {
                LightUtil.pack(unpackedData[vertexIndex][vertexElementTypeIndex], vertexData, format, vertexIndex, vertexElementTypeIndex);
            }
        }

        return vertexData;
    }

    public static void setNormalsToIgnoreLightingOnItem (float[][][] unpackedData) {
        if (unpackedData.length != 4)
            throw new IllegalArgumentException("The given data is not in UnpackedBakedQuad format!");

        for (int vertexIndex = 0; vertexIndex < 4; vertexIndex++) {
            if (unpackedData[vertexIndex].length != 5)
                throw new IllegalArgumentException("The given unpacked data contains a vertex that is not in Item format!");

            float[] normalData = unpackedData[vertexIndex][3];

            if (normalData.length != 4)
                throw new IllegalArgumentException("The given unpacked data contains a vertex that is not in Item format!");

            normalData[0] = 0f;
            normalData[1] = 1f;
            normalData[2] = 0f;
            normalData[3] = 0f;
        }
    }

    public static void setNormalsToIgnoreLightingOnItemModel (IBakedModel model) {
        for (BakedQuad quad : model.getGeneralQuads()) {
            float[][][] unmodifiedUnpackedQuadData = ModelHelper.getUnpackedQuadData((UnpackedBakedQuad) quad, DefaultVertexFormats.ITEM);
            ModelHelper.setNormalsToIgnoreLightingOnItem(unmodifiedUnpackedQuadData);
            int[] packedData = ModelHelper.getPackedQuadData(unmodifiedUnpackedQuadData, DefaultVertexFormats.ITEM);

            for (int dataIndex = 0; dataIndex < quad.getVertexData().length; dataIndex++) {
                quad.getVertexData()[dataIndex] = packedData[dataIndex];
            }
        }
    }

    /**
     * Deseralizes a json in the format of { "textures": { "foo": "texture",... }}
     * Ignores all invalid json
     */
    public static class ModelTextureDeserializer implements JsonDeserializer<Map<String, String>> {

        public static final ModelTextureDeserializer INSTANCE = new ModelTextureDeserializer();

        private static final Gson GSON = new Gson();

        @Override
        public Map<String, String> deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject obj = json.getAsJsonObject();
            JsonElement texElem = obj.get("textures");

            if (texElem == null) {
                throw new JsonParseException("Missing textures entry in json");
            }

            return GSON.fromJson(texElem, maptype);
        }
    }


}
