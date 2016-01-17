package com.smithsmodding.smithscore.util.client;

import com.smithsmodding.smithscore.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.registry.*;

import java.io.*;

/**
 * Created by Marc on 06.12.2015.
 */
public class ResourceHelper {

    /**
     * Function to check if a given ResourceLocation for a texture actually exists.
     *
     * @param resource The ResourceLocation to check.
     * @return True when the file exists, false when not
     */
    public static boolean exists (ResourceLocation resource) {
        try {
            ResourceLocation loc = new ResourceLocation(resource.getResourceDomain(), "textures/" + resource.getResourcePath() + ".png");
            Minecraft.getMinecraft().getResourceManager().getAllResources(loc);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Function to check if a given location for a texture actually exists.
     *
     * @param location The location to check.
     * @return True when the file exists, false when not.
     */
    public static boolean exists (String location) {
        return exists(new ResourceLocation(location));
    }

    /**
     * Function to convert a item into a ResourceLocation.
     *
     * @param item The item to convert.
     * @return The uniform ResourceLocation for the given Item.
     */
    public static ResourceLocation getItemLocation (Item item) {
        // get the registered name for the object
        Object o = GameData.getItemRegistry().getNameForObject(item);

        // are you trying to add an unregistered item...?
        if (o == null) {
            SmithsCore.getLogger().error("Item %s is not registered!" + item.getUnlocalizedName());
            // bad boi
            return null;
        }

        return (ResourceLocation) o;
    }
}
