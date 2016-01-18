package com.smithsmodding.smithscore.client.gui.tabs.implementations;

import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.state.*;
import com.smithsmodding.smithscore.util.client.color.*;
import net.minecraft.item.*;

/**
 * Created by Marc on 17.01.2016.
 */
public class DummyTab extends CoreTab {

    public DummyTab (String uniqueID, IGUIBasedTabHost root, IGUIComponentState state, ItemStack displayStack, MinecraftColor tabColor, String toolTipString) {
        super(uniqueID, root, state, displayStack, tabColor, toolTipString);
    }

    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents (IGUIBasedComponentHost host) {
        getRootGuiObject().registerComponents(this);
    }
}
