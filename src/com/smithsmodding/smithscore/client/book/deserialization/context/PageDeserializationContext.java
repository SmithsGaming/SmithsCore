package com.smithsmodding.smithscore.client.book.deserialization.context;

import com.smithsmodding.smithscore.client.book.GuiBookSmithsCore;
import com.smithsmodding.smithscore.client.book.data.IBookPage;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

/**
 * Created by marcf on 7/31/2016.
 */
public final class PageDeserializationContext {

    private final GuiBookSmithsCore gui;
    private final IBookPage page;
    private final HashMap<ResourceLocation, Object> contextData = new HashMap<>();

    private int componentIndex = 0;

    public PageDeserializationContext(GuiBookSmithsCore gui, IBookPage page) {
        this.gui = gui;
        this.page = page;
    }

    public GuiBookSmithsCore getGui() {
        return gui;
    }

    public IBookPage getPage() {
        return page;
    }

    public Object getContextData(ResourceLocation key) {
        return contextData.get(key);
    }

    public void registerContextData(ResourceLocation key, Object obj) {
        contextData.put(key, obj);
    }

    public int getNextComponentIndex() {
        return componentIndex++;
    }
}
