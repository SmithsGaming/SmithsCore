package com.SmithsModding.SmithsCore.Client.GUI.State;

import com.SmithsModding.SmithsCore.Client.GUI.Components.IGUIComponent;

/**
 * Created by Orion
 * Created on 02.12.2015
 * 10:59
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IGUIComponentState
{

    IGUIComponent getComponent();

    /**
     * Function to
     *
     * @return
     */
    boolean isEnabled();

    /**
     * Function to set the State of tha Button if it is enabled or not.
     *
     * @param state The new enabled State.
     */
    void setEnabledState(boolean state);
}
