package com.SmithsModding.SmithsCore.Client.GUI.State;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.*;

/**
 * Created by marcf on 12/28/2015.
 */
public class LedgerComponentState implements IGUIComponentState {

    private IGUIComponent component;

    private boolean enabled;
    private boolean visible;

    private boolean openState;
    private float openProgress;

    public LedgerComponentState()
    {

    }

    @Override
    public IGUIComponent getComponent() {
        return component;
    }

    @Override
    public void setComponent(IGUIComponent component) {
        this.component = component;
    }

    /**
     * Method to retrieve the enabled state of the component, when the component is disabked it will be rendered darker
     * and it will also not retrieve any input, regardless of the fact that it had set the ForceInputRequried option to
     * true.
     *
     * @return True whem enabled, False ehn not.
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Function to set the State of tha Button if it is enabled or not.
     *
     * @param state The new enabled State.
     */
    @Override
    public void setEnabledState(boolean state) {
        this.enabled = state;
    }

    /**
     * Method to retrieve the visible state of the component, when the component is hidden (false) it will not be
     * rendered by the StandardRenderManager, but it will continue to get input. Allowing for hidden components that react to
     * input but are not visible to the user.
     *
     * @return True when visible, false when not.
     */
    @Override
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Method to set the vsibility state of a component.
     *
     * @param state Then new visibilitys state of a component.
     */
    @Override
    public void setVisibleState(boolean state) {
        this.visible = state;
    }


    /**
     * Getter to retrieve the open state of the Ledger
     *
     * @return True if open or opening, False if closed or closing
     */
    public boolean getOpenState () {
        return this.openState;
    }

    /**
     * Setter to set the open state of the Ledger
     *
     * @param state True when the ledger should start opening (or continue opening), false when the ledger should (stop
     *              opening and should) start closing, or stay closed.
     */
    public void setOpenState (boolean state) {
        this.openState = state;
    }

    /**
     * Method to toggle the open state of the Ledger.
     *
     * @return The new open state of the ledger
     */
    public boolean toggleOpenState () {
        setOpenState(!getOpenState());

        return getOpenState();
    }

    /**
     * Getter to get the opening progress.
     *
     * @return A value between 1 and 0, with 0 meaning closed and 1 meaning open.
     */
    public float getOpenProgress () {
        return this.openProgress;
    }

    /**
     * Setter to set the opening progress.
     *
     * @param progress A value between 1 and 0, with 0 meaning closed and 1 meaning open.
     */
    public void setOpenProgress (float progress) {
        this.openProgress = progress;
    }
}
