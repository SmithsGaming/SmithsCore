package com.smithsmodding.smithscore.client.book.deserialization.implementations;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.smithsmodding.smithscore.client.book.deserialization.BookDeserializationContext;
import com.smithsmodding.smithscore.client.book.deserialization.IPageComponentDeserializer;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentBorder;
import com.smithsmodding.smithscore.util.CoreReferences;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import net.minecraft.util.ResourceLocation;

/**
 * Created by marcf on 8/3/2016.
 */
public class BorderDeserializer implements IPageComponentDeserializer<ComponentBorder> {
    @Override
    public ComponentBorder deserialize(JsonElement element, BookDeserializationContext context) {
        JsonObject borderObject = element.getAsJsonObject();

        MinecraftColor backGroundColor = (MinecraftColor) context.getContextData(CoreReferences.BookData.PAGEBACKGROUND);
        return null;
    }

    private ComponentBorder.CornerTypes getCornerTypeFromString(String cornerType) {
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
