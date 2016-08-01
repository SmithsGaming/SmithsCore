package com.smithsmodding.smithscore.client.book.deserialization;

import com.smithsmodding.smithscore.client.book.data.IBookPage;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

/**
 * Created by marcf on 7/31/2016.
 */
public final class BookDeserializationContext {

    private final HashMap<ResourceLocation, IBookPage> pages = new HashMap<>();
    private final HashMap<ResourceLocation, Object> contextData = new HashMap<>();

    public IBookPage getPage(ResourceLocation location) {
        return pages.get(location);
    }

    public void registerPage(IBookPage page) {
        pages.put(page.getPageLocation(), page);
    }

    public Object getContextData(ResourceLocation key) {
        return contextData.get(key);
    }

    public void registerContextData(ResourceLocation key, Object obj) {
        contextData.put(key, obj);
    }
}
