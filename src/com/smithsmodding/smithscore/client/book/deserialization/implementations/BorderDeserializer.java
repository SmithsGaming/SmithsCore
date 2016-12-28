package com.smithsmodding.smithscore.client.book.deserialization.implementations;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.smithsmodding.smithscore.client.book.deserialization.BookDeserializationUtil;
import com.smithsmodding.smithscore.client.book.deserialization.context.BookDeserializationContext;
import com.smithsmodding.smithscore.client.book.deserialization.IPageComponentDeserializer;
import com.smithsmodding.smithscore.client.book.deserialization.context.PageDeserializationContext;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentBorder;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;

import javax.annotation.Nonnull;

import static com.smithsmodding.smithscore.util.CoreReferences.BookData.Border.*;
import static com.smithsmodding.smithscore.util.CoreReferences.BookData.General.*;

/**
 * Created by marcf on 8/3/2016.
 */
public class BorderDeserializer implements IPageComponentDeserializer<ComponentBorder> {

    @Nonnull
    @Override
    public ComponentBorder deserialize(@Nonnull JsonElement element, @Nonnull PageDeserializationContext context) {
        JsonObject borderObject = element.getAsJsonObject();

        MinecraftColor backGroundColor = (MinecraftColor) context.getContextData(BACKGROUND);

        ComponentBorder.CornerTypes generalCorner = getCornerTypeFromString(BookDeserializationUtil.getElementAsString(borderObject, CORNERS));
        ComponentBorder.CornerTypes topLeft = (BookDeserializationUtil.hasLocation(borderObject, CORNERTOPLEFT) ? getCornerTypeFromString(BookDeserializationUtil.getElementAsString(borderObject, CORNERTOPLEFT)) : generalCorner);
        ComponentBorder.CornerTypes topRight = (BookDeserializationUtil.hasLocation(borderObject, CORNERTOPRIGHT) ? getCornerTypeFromString(BookDeserializationUtil.getElementAsString(borderObject, CORNERTOPRIGHT)) : generalCorner);
        ComponentBorder.CornerTypes bottomLeft = (BookDeserializationUtil.hasLocation(borderObject, CORNERBOTTOMLEFT) ? getCornerTypeFromString(BookDeserializationUtil.getElementAsString(borderObject, CORNERBOTTOMLEFT)) : generalCorner);
        ComponentBorder.CornerTypes bottomRight = (BookDeserializationUtil.hasLocation(borderObject, CORNERBOTTOMRIGHT) ? getCornerTypeFromString(BookDeserializationUtil.getElementAsString(borderObject, CORNERBOTTOMRIGHT)) : generalCorner);

        Coordinate2D location = BookDeserializationUtil.getCoordinate(borderObject, LOCATION);

        int width = BookDeserializationUtil.getElementAsInt(borderObject, WIDTH);
        int height = BookDeserializationUtil.getElementAsInt(borderObject, HEIGHT);

        String uniqueId = context.getPage().getId().toString() + "-" + context.getNextComponentIndex();

        return new ComponentBorder(uniqueId, context.getGui(), location, width, height, backGroundColor, topLeft, topRight, bottomRight, bottomLeft);
    }

    @Nonnull
    private ComponentBorder.CornerTypes getCornerTypeFromString(@Nonnull String cornerType) {
        switch (cornerType.toLowerCase()){
            case "inwards":
                return ComponentBorder.CornerTypes.Inwards;
            case "outwards":
                return ComponentBorder.CornerTypes.Outwards;
            case "horizontal":
                return ComponentBorder.CornerTypes.StraightHorizontal;
            case "vertical":
                return ComponentBorder.CornerTypes.StraightVertical;
            default:
                return ComponentBorder.CornerTypes.Inwards;
        }
    }
}
