package com.smithsmodding.smithscore.client.gui.state;

import com.smithsmodding.smithscore.client.gui.components.core.*;

/**
 * Created by Orion
 * Created on 02.12.2015
 * 10:59
 *
 * Copyrighted according to Project specific license
 */
public interface IGUIComponentState
{

    IGUIComponent getComponent();

    void setComponent (IGUIComponent component);

    /**
     * Method to retrieve the enabled state of the component, when the component is disabked it will be rendered darker
     * and it will also not retrieve any input, regardless of the fact that it had set the ForceInputRequried option to
     * true.
     *
     * @return True whem enabled, False ehn not.
     */
    boolean isEnabled();

    /**
     * Function to set the state of tha Button if it is enabled or not.
     *
     * @param state The new enabled state.
     */
    void setEnabledState(boolean state);

    /**
     * Method to retrieve the visible state of the component, when the component is hidden (false) it will not be
     * rendered by the StandardRenderManager, but it will continue to get input. Allowing for hidden components that react to
     * input but are not visible to the user.
     *
     * @return True when visible, false when not.
     */
    boolean isVisible();

    /**
     * Method to set the vsibility state of a component.
     *
     * @param state Then new visibilitys state of a component.
     */
    void setVisibleState(boolean state);
}
