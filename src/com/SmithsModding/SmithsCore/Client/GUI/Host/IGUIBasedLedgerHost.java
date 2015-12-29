package com.SmithsModding.SmithsCore.Client.GUI.Host;

import com.SmithsModding.SmithsCore.Client.GUI.Components.Core.IGUIComponent;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.IGUILedger;
import com.SmithsModding.SmithsCore.Client.GUI.Management.IGUIManager;
import net.minecraft.client.gui.Gui;

import java.util.LinkedHashMap;

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
     * Method to get the Root GUI Object that this Component is part of.
     *
     * @return The GUI that this component is part of.
     */
    Gui getRootGuiObject();



    /**
     * Method to get the GUI Roots Manager.
     *
     * @return The Manager that is at the root for the GUI Tree.
     */
    IGUIManager getRootManager();

    /**
     * Function to get all the Components registered to this Host.
     *
     * @return A ID to ledger map that holds all the Components (but not their SubComponents) of this Host.
     */
    LinkedHashMap<String, IGUILedger> getAllLedgers ();
}
