package com.smithsmodding.smithscore.client.GUI.Host;

/**
 * Created by marcf on 12/28/2015.
 */
public interface IGUIBasedLedgerHost extends IGUIBasedComponentHost
{
    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param parent This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    void registerLedgers(IGUIBasedLedgerHost parent);

    /**
     * Method used to register a new Component to this host.
     *
     * @param ledger The new component.
     */
    void registerNewLedger (IGUILedger ledger);

    /**
     * Method to get the LedgerManager for this host.
     *
     * @return The LedgerManager of this host.
     */
    ILedgerManager getLedgerManager ();


    /**
     * Method to get the Root gui Object that this Component is part of.
     *
     * @return The gui that this component is part of.
     */
    GuiContainerSmithsCore getRootGuiObject ();

    /**
     * Method to get the gui Roots Manager.
     *
     * @return The Manager that is at the root for the gui Tree.
     */
    IGUIManager getRootManager ();

    /**
     * A number bigger then 0 that describes the offset of the right side ledgers with the top left corner as origin
     *
     * @return THe right side ledger offset
     */
    int getRightLedgerOffSet();

    /**
     * A number bigger then 0 that describes the offset of the left side ledgers with the top left corner as origin
     *
     * @return THe right left ledger offset
     */
    int getLeftLedgerOffSet();
}
