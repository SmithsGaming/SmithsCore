package com.smithsmodding.smithscore.client.book.data;

import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

/**
 * Created by marcf on 7/30/2016.
 */
public interface IBookPage {
    ResourceLocation getPageLocation();

    HashMap<String, IGUIComponent> getComponents();
}
