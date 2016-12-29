package com.smithsmodding.smithscore.client.book.deserialization.context;

import com.smithsmodding.smithscore.client.book.GuiBookSmithsCore;
import com.smithsmodding.smithscore.client.book.data.IBookPage;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 7/31/2016.
 */
public final class PageDeserializationContext {

    private final GuiBookSmithsCore gui;
    private final IBookPage page;
    private final HashMap<ResourceLocation, Object> contextData = new HashMap<>();

    private int componentIndex = 0;

    public PageDeserializationContext(@Nonnull GuiBookSmithsCore gui,@Nonnull IBookPage page) {
        this.gui = gui;
        this.page = page;
    }

    @Nonnull
    public GuiBookSmithsCore getGui() {
        return gui;
    }

    @Nonnull
    public IBookPage getPage() {
        return page;
    }

    @Nonnull
    public Object getContextData(@Nonnull ResourceLocation key) {
        return contextData.get(key);
    }

    public void registerContextData(ResourceLocation key, Object obj) {
        contextData.put(key, obj);
    }

    @Nonnull
    public int getNextComponentIndex() {
        return componentIndex++;
    }
}
