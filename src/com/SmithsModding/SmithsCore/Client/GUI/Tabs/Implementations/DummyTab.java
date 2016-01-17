package com.SmithsModding.SmithsCore.Client.GUI.Tabs.Implementations;

import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.*;
import net.minecraft.item.*;

/**
 * Created by Marc on 17.01.2016.
 */
public class DummyTab extends CoreTab {

    public DummyTab (String uniqueID, IGUIBasedTabHost root, IGUIComponentState state, ItemStack displayStack, MinecraftColor tabColor, String toolTipString) {
        super(uniqueID, root, state, displayStack, tabColor, toolTipString);
    }

    /**
     * Method used by the Tab system to make sure that the tabs register their components properly.
     *
     * @param host The host for this Tabs components.
     */
    @Override
    protected void registerTabComponents (IGUIBasedComponentHost host) {
        getRootGuiObject().registerComponents(this);
    }
}
