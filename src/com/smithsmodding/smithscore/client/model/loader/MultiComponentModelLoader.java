package com.smithsmodding.smithscore.client.model.loader;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

import java.util.ArrayList;

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
        return null;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
