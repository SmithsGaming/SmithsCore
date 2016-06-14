package com.smithsmodding.smithscore.client.model.loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.smithscore.client.model.deserializers.MultiComponentModelDeserializer;
import com.smithsmodding.smithscore.client.model.deserializers.definition.MultiComponentModelDefinition;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import com.smithsmodding.smithscore.client.model.unbaked.MultiComponentModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Author Marc (Created on: 28.05.2016)
 */
public class MultiComponentModelLoader implements ICustomModelLoader {

    public static final MultiComponentModelLoader instance = new MultiComponentModelLoader();
    public static final String EXTENSION = "MCM-smithscore";

    private ArrayList<String> acceptedDomains = new ArrayList<>();

    private MultiComponentModelLoader() {
    }

    public void registerDomain(String domain) {
        acceptedDomains.add(domain);
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        if (!modelLocation.getResourcePath().endsWith(EXTENSION)) return false;

        for (String domain : acceptedDomains)
            if (modelLocation.getResourceDomain().equals(domain))
                return true;

        return false;
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        modelLocation = ModelHelper.getModelLocation(modelLocation);

        MultiComponentModelDefinition definition = MultiComponentModelDeserializer.instance.deserialize(modelLocation);

        ImmutableMap.Builder<String, IModel> builder = new ImmutableMap.Builder<>();
        for (Map.Entry<String, ResourceLocation> component : definition.getTextureLocations().entrySet()) {
            builder.put(component.getKey(), new ItemLayerModel(ImmutableList.of(component.getValue())));
        }

        return new MultiComponentModel(builder.build(), definition.getTransforms());
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        ///NOOP
    }
}
