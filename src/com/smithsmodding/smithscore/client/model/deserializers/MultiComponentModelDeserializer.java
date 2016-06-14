package com.smithsmodding.smithscore.client.model.deserializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.smithsmodding.smithscore.client.model.deserializers.definition.MultiComponentModelDefinition;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Marc (Created on: 28.05.2016)
 */
public class MultiComponentModelDeserializer implements JsonDeserializer<Map<String, ResourceLocation>> {
    public static final MultiComponentModelDeserializer instance = new MultiComponentModelDeserializer();

    private static final Type mapType = new TypeToken<HashMap<String, ResourceLocation>>() {
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
    public MultiComponentModelDefinition deserialize(ResourceLocation modelLocation) throws IOException {
        return new MultiComponentModelDefinition(gson.fromJson(ModelHelper.getReaderForResource(modelLocation), mapType), ModelHelper.loadTransformFromJson(modelLocation));
    }

    @Override
    public Map<String, ResourceLocation> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject textureObject = jsonObject.get("components").getAsJsonObject();

        HashMap<String, ResourceLocation> textureLocations = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : textureObject.entrySet()) {
            textureLocations.put(entry.getKey(), new ResourceLocation(entry.getValue().getAsString()));
        }

        return textureLocations;
    }
}
