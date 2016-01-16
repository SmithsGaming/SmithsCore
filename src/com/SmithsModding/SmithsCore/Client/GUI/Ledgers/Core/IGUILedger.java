package com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core;

import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Scissoring.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;

import java.util.ArrayList;

/**
 * Created by marcf on 12/28/2015.
 */
public interface IGUILedger extends IGUIBasedComponentHost, IScissoredGuiComponent {

    /**
     * Function to get this Components Host.
     *
     * @return This Components Host.
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