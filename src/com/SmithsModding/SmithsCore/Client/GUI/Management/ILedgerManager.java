package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Host.IGUIBasedLedgerHost;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.IGUILedger;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.LedgerConnectionSide;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.Coordinate2D;

/**
 * Created by marcf on 12/29/2015.
 */
public interface ILedgerManager
{

    IGUIBasedLedgerHost getHost();

    void registerLedgerRightSide(IGUILedger ledger);

    void registerLedgerLeftSide(IGUILedger ledger);

    int getLedgerIndex(LedgerConnectionSide side, String uniqueID);

    /**
     * Method returns the Global coorindate of the RootAnchor pixel of the given Ledger (if it exists on the given side
     * else null)
     *
     * For a Ledger on the left side of the GUI it will return the top most right pixel that is visible.
     * For a Ledher on the right side of the GUI it will return the top most left pixel that is visible.
     *
     * @param side The side you want a Ledger coordinate for.
     * @param uniqueID The unique ID of the ledger.
     *
     * @return See method description.
     */
    Coordinate2D getLedgerGlobalCoordinate(LedgerConnectionSide side, String uniqueID);


}
