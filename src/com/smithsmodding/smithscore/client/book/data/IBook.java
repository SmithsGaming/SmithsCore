package com.smithsmodding.smithscore.client.book.data;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

/**
 * Created by marcf on 7/30/2016.
 */
public interface IBook {

    ResourceLocation getBookLocation();

    HashMap<ResourceLocation, IBookPage> getPages();

    IBookPage getOpenPage();
}
