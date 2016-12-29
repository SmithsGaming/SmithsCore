package com.smithsmodding.smithscore.client.book.deserialization.context;

import com.smithsmodding.smithscore.client.book.data.IBookPage;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 7/31/2016.
 */
public final class BookDeserializationContext {

    @Nonnull
    private final HashMap<ResourceLocation, IBookPage> pages = new HashMap<>();

    @Nonnull
    public IBookPage getPage(@Nonnull ResourceLocation location) {
        return pages.get(location);
    }

    public void registerPage(@Nonnull IBookPage page) {
        pages.put(page.getPageLocation(), page);
    }
}
