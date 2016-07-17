package com.smithsmodding.smithscore.client.model.loader;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.client.model.deserializers.SmithsCoreOBJParser;
import com.smithsmodding.smithscore.client.model.unbaked.SmithsCoreOBJModel;
import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/*
 * Modified version of the MC Forge OBJLoader used until hiding of groups is supported.
 * Licensed under its License.
 */
public enum SmithsCoreOBJLoader implements ICustomModelLoader {
    INSTANCE;

    private final HashSet<String> enabledDomains = new HashSet<String>();
    private final Map<ResourceLocation, SmithsCoreOBJModel> cache = new HashMap<>();
    private final Map<ResourceLocation, Exception> errors = new HashMap<>();
    private IResourceManager manager;

    public void addDomain(String domain) {
        enabledDomains.add(domain.toLowerCase());
        SmithsCore.getLogger().log(Level.INFO, CoreReferences.LogMarkers.CLIENT, "Added: " + domain.toLowerCase() + " to the SmitshCore OBJ Loader.");
    }

    public void onResourceManagerReload(IResourceManager resourceManager) {
        this.manager = resourceManager;
        cache.clear();
        errors.clear();
    }

    public boolean accepts(ResourceLocation modelLocation) {
        return enabledDomains.contains(modelLocation.getResourceDomain()) && modelLocation.getResourcePath().endsWith(".obj");
    }

    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        ResourceLocation file = new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath());
        if (!cache.containsKey(file)) {
            IResource resource = null;
            try {
                resource = manager.getResource(file);
            } catch (Exception e) {
                return ModelLoaderRegistry.getMissingModel();
            }
            SmithsCoreOBJParser parser = new SmithsCoreOBJParser(resource, manager);
            SmithsCoreOBJModel model = null;
            try {
                model = parser.parse();
            } catch (Exception e) {
                errors.put(modelLocation, e);
            } finally {
                cache.put(modelLocation, model);
            }
        }
        SmithsCoreOBJModel model = cache.get(file);
        if (model == null)
            throw new ModelLoaderRegistry.LoaderException("Error loading model previously: " + file, errors.get(modelLocation));
        return model;
    }
}