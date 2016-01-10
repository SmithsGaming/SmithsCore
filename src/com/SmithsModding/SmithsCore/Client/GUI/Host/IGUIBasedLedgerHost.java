package com.SmithsModding.SmithsCore.Client.GUI.Host;

import com.SmithsModding.SmithsCore.Client.GUI.*;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.Management.*;

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
     * Method used to register a new Component to this Host.
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
     * Method to get the Root GUI Object that this Component is part of.
     *
     * @return The GUI that this component is part of.
     */
    GuiContainerSmithsCore getRootGuiObject ();

    /**
     * Method to get the GUI Roots Manager.
     *
     * @return The Manager that is at the root for the GUI Tree.
     */
    IGUIManager getRootManager ();
}
