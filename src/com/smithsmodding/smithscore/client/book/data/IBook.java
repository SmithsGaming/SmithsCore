package com.smithsmodding.smithscore.client.book.data;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 7/30/2016.
 */
public interface IBook {

    @Nonnull
    ResourceLocation getBookLocation();

    @Nonnull
    HashMap<ResourceLocation, IBookPage> getPages();

    @Nonnull
    IBookPage getOpenPage();
}
