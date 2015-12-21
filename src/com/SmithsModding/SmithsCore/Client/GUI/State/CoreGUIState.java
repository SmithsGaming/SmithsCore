package com.SmithsModding.SmithsCore.Client.GUI.State;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.*;

/**
 * Created by Marc on 21.12.2015.
 */
public class CoreGUIState implements IGUIComponentState {

    GuiContainerSmithsCore guiContainerSmithsCore;

    public CoreGUIState (GuiContainerSmithsCore guiContainerSmithsCore) {
        this.guiContainerSmithsCore = guiContainerSmithsCore;
    }

    @Override
    public IGUIComponent getComponent () {
        return guiContainerSmithsCore;
    }

    @Override
    public boolean isEnabled () {
        return true;
    }

    @Override
    public void setEnabledState (boolean state) {
        //NOOP
    }

    @Override
    public boolean isVisible () {
        return true;
    }

    @Override
    public void setVisibleState (boolean state) {
        //NOOP
    }
}
