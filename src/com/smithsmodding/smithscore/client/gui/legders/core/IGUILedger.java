package com.smithsmodding.smithscore.client.gui.legders.core;

import com.smithsmodding.smithscore.client.gui.hosts.*;
import com.smithsmodding.smithscore.client.gui.scissoring.*;

import javax.annotation.Nonnull;
import java.util.*;

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
    @Nonnull
    ArrayList<String> getIconToolTipText();
}