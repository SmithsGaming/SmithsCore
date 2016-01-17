package com.SmithsModding.SmithsCore.Client.GUI.Tabs.Core;

import com.SmithsModding.SmithsCore.Client.GUI.Host.IGUIBasedComponentHost;
import com.SmithsModding.SmithsCore.Client.GUI.Host.IGUIBasedTabHost;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by marcf on 1/15/2016.
 */
public interface IGUITab extends IGUIBasedComponentHost
{

    /**
     * Method to get the Host of this Tab
     *
     * @return The current tabs host.
     */
    IGUIBasedTabHost getTabHost();

    ItemStack getDisplayStack();

    /**
     * Function to get the tooltiptext of the GUI System.
     *
     * @return
     */
    ArrayList<String> getIconToolTipText();

}
