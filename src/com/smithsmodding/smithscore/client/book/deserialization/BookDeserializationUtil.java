package com.smithsmodding.smithscore.client.book.deserialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.IllegalFormatException;

/**
 * Created by marcf on 12/27/2016.
 */
public final class BookDeserializationUtil {

    /**
     * Method to check if a given Json representation of a GuiComponent holds a given location as key
     * @param guiObject The object to check in.
     * @param location The key to check for.
     * @return True when the key is contained in the JsobObject, false if not.
     */
    public static final boolean hasLocation(@Nonnull JsonObject guiObject, @Nonnull ResourceLocation location) {
        return guiObject.has(location.toString());
    }

    /**
     * Method to retrieve a value from a given guiObject.
     * @param guiObject The guiObject to retrieve from.
     * @param location The location key
     * @return The value of the key in the guiObject
     * @throws IllegalArgumentException is thrown when the guiObject does not have that key.
     */
    public static final JsonElement getElement(@Nonnull JsonObject guiObject, @Nonnull ResourceLocation location) throws IllegalArgumentException {
        if (!hasLocation(guiObject, location))
            throw new IllegalArgumentException("The given guiObject does not have that location");

        return guiObject.get(location.toString());
    }

    /**
     * Method to retrieve a String from a given guiObject.
     * @param guiObject The guiObject to retrieve from.
     * @param location The location key
     * @return The value of the key in the guiObject
     * @throws IllegalArgumentException is thrown when the guiObject does not have that key.
     */
    public static final String getElementAsString(@Nonnull JsonObject guiObject, @Nonnull ResourceLocation location) throws IllegalArgumentException {
        return getElement(guiObject, location).getAsString();
    }

    /**
     * Method to retrieve a int from a given guiObject.
     * @param guiObject The guiObject to retrieve from.
     * @param location The location key
     * @return The value of the key in the guiObject
     * @throws IllegalArgumentException is thrown when the guiObject does not have that key.
     */
    public static final int getElementAsInt(@Nonnull JsonObject guiObject, @Nonnull ResourceLocation location) throws IllegalArgumentException {
        return getElement(guiObject, location).getAsInt();
    }

    /**
     * Method to get a Coordinate from the JSON Representation of a GuiComponent,
     * @param guiObject The JsonObject to retrieve the coordinate from
     * @param location The key to retrieve
     * @return A Coordinate with the data stored in the guiObject at the given location
     * @throws IllegalArgumentException is thrown when the guiObject does not have that key.
     */
    @Nonnull
    public static final Coordinate2D getCoordinate(@Nonnull JsonObject guiObject, @Nonnull ResourceLocation location) throws IllegalArgumentException {
        if (!hasLocation(guiObject, location))
            throw new IllegalArgumentException("The given guiObject does not have that location");
        
        try {
            JsonElement coordinateElement = getElement(guiObject, location);
            JsonArray coordinateArray = coordinateElement.getAsJsonArray();
            
            if (coordinateArray.size() != 2) 
                throw new IllegalArgumentException("The coordinate is not formatted properly: {x,y}");
            
            return new Coordinate2D(coordinateArray.get(0).getAsInt(), coordinateArray.get(1).getAsInt());
        } catch (Exception ex) {
            throw new IllegalArgumentException("The coordinate could not be deserialized.", ex); 
        }
    }
}
