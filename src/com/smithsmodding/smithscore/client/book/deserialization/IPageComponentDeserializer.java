package com.smithsmodding.smithscore.client.book.deserialization;

import com.google.gson.JsonElement;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;

/**
 * Created by marcf on 7/31/2016.
 */
public interface IPageComponentDeserializer<C extends IGUIComponent> {
    C deserialize(JsonElement element, BookDeserializationContext context);
}
