package com.smithsmodding.smithscore.client.GUI.Management;

import java.util.*;

/**
 * Created by marcf on 12/29/2015.
 */
public interface ILedgerManager
{

    IGUIBasedLedgerHost getHost();

    void registerLedgerRightSide(IGUILedger ledger);

    void registerLedgerLeftSide(IGUILedger ledger);

    int getLedgerIndex(LedgerConnectionSide side, String uniqueID);

    LinkedHashMap<String, IGUILedger> getRightLedgers();

    LinkedHashMap<String, IGUILedger> getLeftLedgers();

    Coordinate2D getLedgerLocalCoordinate(LedgerConnectionSide side, String uniqueID);

    /**
     * Method returns the Global coorindate of the RootAnchor pixel of the given Ledger (if it exists on the given side
     * else null)
     *
     * For a Ledger on the left side of the gui it will return the top most right pixel that is visible.
     * For a Ledher on the right side of the gui it will return the top most left pixel that is visible.
     *
     * @param side The side you want a Ledger coordinate for.
     * @param uniqueID The unique ID of the ledger.
     *
     * @return See method description.
     */
    Coordinate2D getLedgerGlobalCoordinate(LedgerConnectionSide side, String uniqueID);

    /**
     * Method called by a ledger to indicate that the opened ledger should be changed.
     *
     * @param ledger The ledger in which was clicked, either to indicate that this one should be opened (and a other one
     *               closed) or this one should be closed.
     */
    void onLedgerClickedInside (IGUILedger ledger);
}
