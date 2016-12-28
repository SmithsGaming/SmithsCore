package com.smithsmodding.smithscore.client.book.data;

import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 7/30/2016.
 */
public interface IBookPage {
    @Nonnull
    ResourceLocation getId();

    @Nonnull
    ResourceLocation getPageLocation();

    @Nonnull
    HashMap<String, IGUIComponent> getComponents();
}
