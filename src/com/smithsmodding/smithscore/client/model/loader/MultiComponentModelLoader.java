package com.smithsmodding.smithscore.client.model.loader;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.smithscore.client.model.deserializers.MultiComponentModelDeserializer;
import com.smithsmodding.smithscore.client.model.deserializers.definition.MultiComponentModelDefinition;
import com.smithsmodding.smithscore.client.model.unbaked.MultiComponentModel;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Author Marc (Created on: 28.05.2016)
 */
public class MultiComponentModelLoader implements ICustomModelLoader {

    public static final MultiComponentModelLoader instance = new MultiComponentModelLoader();

    private ArrayList<String> acceptedDomains = new ArrayList<>();

    private MultiComponentModelLoader() {
    }

    public void registerDomain(String domain) {
        acceptedDomains.add(domain);
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        for (String domain : acceptedDomains)
            if (modelLocation.getResourcePath().endsWith(domain))
                return true;

        return false;
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        MultiComponentModelDefinition definition = MultiComponentModelDeserializer.instance.deserialize(modelLocation);

        ImmutableMap.Builder<String, IModel> builder = new ImmutableMap.Builder<>();
        for (Map.Entry<String, ResourceLocation> component : definition.getTextureLocations().entrySet()) {
            builder.put(component.getKey(), ModelLoaderRegistry.getModel(component.getValue()));
        }

        return new MultiComponentModel(builder.build());
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        ///NOOP
    }
}
