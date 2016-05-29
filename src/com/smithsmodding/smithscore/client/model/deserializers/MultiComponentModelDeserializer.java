package com.smithsmodding.smithscore.client.model.deserializers;

import com.google.common.base.Charsets;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.smithscore.client.model.deserializers.definition.MultiComponentModelDefinition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Marc (Created on: 28.05.2016)
 */
public class MultiComponentModelDeserializer implements JsonDeserializer<Map<String, ModelResourceLocation>> {
    public static final MultiComponentModelDeserializer instance = new MultiComponentModelDeserializer();

    private static final Type mapType = new TypeToken<HashMap<String, ModelResourceLocation>>() {
    }.getType();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(mapType, instance).create();

    private MultiComponentModelDeserializer() {
    }

    /**
     * Method deserializes the given ModelLocation  into a MultiComponentModel.
     * The returned definition will hold all the SubModels in a Map.
     *
     * @param modelLocation The location to load the Definition From.
     * @return A ModelDefinition for a MultiComponentModel.
     * @throws IOException Thrown when the given ModelLocation points to nothing or not to a ModelFile.
     */
    public MultiComponentModelDefinition deserialize(ModelResourceLocation modelLocation) throws IOException {
        IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json"));
        Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);

        return new MultiComponentModelDefinition(gson.fromJson(reader, mapType));
    }

    @Override
    public Map<String, ModelResourceLocation> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject textureObject = jsonObject.get("textures").getAsJsonObject();

        HashMap<String, ModelResourceLocation> textureLocations = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : textureObject.entrySet()) {
            textureLocations.put(entry.getKey(), new ModelResourceLocation(entry.getValue().getAsString()));
        }

        return textureLocations;
    }
}
