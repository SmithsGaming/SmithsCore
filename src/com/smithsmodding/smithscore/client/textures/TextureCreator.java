package com.smithsmodding.smithscore.client.textures;

import com.google.common.collect.Maps;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.util.client.ResourceHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by Marc on 06.12.2015.
 *
 * TextureManager used to handle grayscale textures and color them for each material. Modelled after parts of the
 * TinkersConstruct CustomTextureCreator.
 */
public class TextureCreator implements IResourceManagerReloadListener {
    //Variable containing all the mappings for
    @Nonnull
    private static Set<ITextureController> controllers = new HashSet<>();
    //Variable containing the location of all grayscale base textures.
    @Nonnull
    private static Set<ResourceLocation> baseTextures = new HashSet<>();
    //Variable that holds the colored end textures when the Creator has reloaded
    @Nonnull
    private static Map<String, HashMap<String, TextureAtlasSprite>> buildSprites = Maps.newHashMap();

    static {
        registerController(new HolographicTexture.HolographicTextureController());
    }

    /**
     * Static method to register a new GrayScale texture to the Creator.
     *
     * @param location The location of the Texture.
     */
    public static void registerBaseTexture (ResourceLocation location) {
        baseTextures.add(location);
    }

    /**
     * Static method to register more then one GrayScale texture to the Creator.
     *
     * @param locations The location of the textures to register.
     */
    public static void registerBaseTexture (@Nonnull Collection<ResourceLocation> locations) {
        baseTextures.addAll(locations);
    }

    /**
     * Method to register a new TextureController.
     * @param controller The new controller to register.
     */
    public static void registerController(ITextureController controller) {
        controllers.add(controller);
    }

    /**
     * Method to unregister a TextureController.
     *
     * @param controller The controller to unregister.
     */
    public static void unregisterController(ITextureController controller) {
        controllers.remove(controller);
    }

    /**
     * Static method to get the builded textures.
     *
     * @return A map containing all the colored textures using the base texture and the materialname as keys.
     */
    @Nonnull
    public static Map<String, HashMap<String, TextureAtlasSprite>> getBuildSprites() {
        return buildSprites;
    }

    /**
     * Actual construction method is called from the ForgeEvent system. This method kicks the creation of the textures
     * of and provided a map to put the textures in.
     *
     * @param event The events fired before the TextureSheet is stitched. TextureStitchEvent.Pre instance.
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void createCustomTextures (@Nonnull TextureStitchEvent.Pre event) {
        //Only run the creation once, after all mods have been loaded.
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return;
        }

        //Function is called so that all textures can be created.
        createMaterialTextures(event.getMap());
    }

    /**
     * Method used to create textures that are material dependend based on the given baseTextures list.
     *
     * @param map The map to register the textures to.
     */
    public void createMaterialTextures (@Nonnull TextureMap map) {
        for (ResourceLocation baseTexture : baseTextures) {
            buildSprites.put(baseTexture.toString(), new HashMap<>());
            for (ITextureController controller : controllers) {
                if (baseTexture.toString().equals("minecraft:missingno")) {
                    //A missing texture does not need coloring. Skipping.
                    continue;
                }

                //Grabbing the base texture so that we can color a copy.
                TextureAtlasSprite base = map.getTextureExtry(baseTexture.toString());
                if (base == null) {
                    //A missing texture does not need coloring. Skipping.
                    SmithsCore.getLogger().error("Missing base texture: " + baseTexture.toString());
                    continue;
                }

                buildSprites.get(baseTexture.toString()).put(controller.getCreationIdentifier(), createTexture(controller, baseTexture, base, map));
            }
        }
    }

    @Nullable
    private TextureAtlasSprite createTexture(@Nonnull ITextureController controller, @Nonnull ResourceLocation baseTexture, TextureAtlasSprite base, @Nonnull TextureMap map) {
        String location = baseTexture.toString() + "_" + controller.getCreationIdentifier();
        TextureAtlasSprite sprite = null;

        if (ResourceHelper.exists(location)) {
            sprite = map.registerSprite(new ResourceLocation(location));
        } else {
            TextureAtlasSprite matBase = base;

            // different base texture?
            if (controller.getTextureSuffix() != null) {
                String loc2 = baseTexture.toString() + "_" + controller.getTextureSuffix();
                TextureAtlasSprite base2 = map.getTextureExtry(loc2);
                // can we manually load it?
                if (base2 == null && ResourceHelper.exists(loc2)) {
                    base2 = new AbstractColoredTexture(loc2, loc2) {
                        @Override
                        protected int colorPixel(int pixel, int mipmap, int pxCoord) {
                            return pixel;
                        }
                    };

                    // save in the map so it's getting reused by the others and is available
                    map.setTextureEntry(base2);
                }
                if (base2 != null) {
                    matBase = base2;
                }
                sprite = controller.getTexture(matBase, location);
            }
        }

        // stitch new textures
        if (sprite != null) {
            map.setTextureEntry(sprite);
        }

        return sprite;
    }

    /**
     * Method called when the resource manager reloads. Clears all the sprites.
     *
     * @param resourceManager The resource manager that reloaded.
     */
    @Override
    public void onResourceManagerReload (IResourceManager resourceManager) {
        baseTextures.clear();
        buildSprites.clear();
    }
}
