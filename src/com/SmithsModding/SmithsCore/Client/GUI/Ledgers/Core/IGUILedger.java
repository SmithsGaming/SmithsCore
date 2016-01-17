package com.smithsmodding.smithscore.client.GUI.Ledgers.Core;

import java.util.ArrayList;

/**
 * Created by marcf on 12/28/2015.
 */
public interface IGUILedger extends IGUIBasedComponentHost, IScissoredGuiComponent {

    /**
     * Function to get this components host.
     *
     * @return This components host.
     */
    IGUIBasedLedgerHost getLedgerHost ();

    /**
     * Method to get the primary rendered side of the Ledger.
     *
     * @return Left when the Ledger is rendered on the left side, right when rendered on the right side.
     */
    LedgerConnectionSide getPrimarySide ();

    /**
     * Method used by the ToolTip system to dynamically get the ToolTip that is displayed when hovered over the Icon.
     *
     * @return The Icons ToolTip.
     */
    ArrayList<String> getIconToolTipText();
}