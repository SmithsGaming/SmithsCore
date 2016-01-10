package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;

import java.util.*;

/**
 * Created by Marc on 09.01.2016.
 */
public class StandardLedgerManager implements ILedgerManager {

    private IGUIBasedLedgerHost host;
    private LinkedHashMap<String, IGUILedger> ledgersLeft = new LinkedHashMap<String, IGUILedger>();
    private LinkedHashMap<String, IGUILedger> ledgersRight = new LinkedHashMap<String, IGUILedger>();


    public StandardLedgerManager (IGUIBasedLedgerHost host) {
        this.host = host;
    }

    @Override
    public IGUIBasedLedgerHost getHost () {
        return host;
    }

    @Override
    public void registerLedgerRightSide (IGUILedger ledger) {
        ledgersRight.put(ledger.getID(), ledger);
    }

    @Override
    public void registerLedgerLeftSide (IGUILedger ledger) {
        ledgersLeft.put(ledger.getID(), ledger);
    }

    @Override
    public int getLedgerIndex (LedgerConnectionSide side, String uniqueID) {
        if (side == LedgerConnectionSide.LEFT) {
            ( ( new ArrayList<String>(ledgersLeft.keySet()) ) ).indexOf(uniqueID);
        } else {
            ( ( new ArrayList<String>(ledgersRight.keySet()) ) ).indexOf(uniqueID);
        }

        return -1;
    }

    @Override
    public LinkedHashMap<String, IGUILedger> getRightLedgers () {
        return ledgersRight;
    }

    @Override
    public LinkedHashMap<String, IGUILedger> getLeftLedgers () {
        return ledgersLeft;
    }

    @Override
    public Coordinate2D getLedgerLocalCoordinate (LedgerConnectionSide side, String uniqueID) {
        LinkedHashMap<String, IGUILedger> ledgers;
        int horizontalOffset;
        if (side == LedgerConnectionSide.LEFT) {
            ledgers = ledgersLeft;
            horizontalOffset = 0;
        } else {
            ledgers = ledgersRight;
            horizontalOffset = getHost().getSize().getWidth();
        }

        Iterator<String> iterator = ledgers.keySet().iterator();
        Coordinate2D root = new Coordinate2D(horizontalOffset, 0);

        while (iterator.hasNext()) {
            String key = iterator.next();

            root = root.getTranslatedCoordinate(new Coordinate2D(0, ledgers.get(key).getSize().getHeigth()));

            if (key.equals(uniqueID))
                return root;
        }

        return new Coordinate2D(-1000, -1000);
    }

    /**
     * Method returns the Global coorindate of the RootAnchor pixel of the given Ledger (if it exists on the given side
     * else null)
     * <p/>
     * For a Ledger on the left side of the GUI it will return the top most right pixel that is visible. For a Ledher on
     * the right side of the GUI it will return the top most left pixel that is visible.
     *
     * @param side     The side you want a Ledger coordinate for.
     * @param uniqueID The unique ID of the ledger.
     *
     * @return See method description.
     */
    @Override
    public Coordinate2D getLedgerGlobalCoordinate (LedgerConnectionSide side, String uniqueID) {
        return getHost().getGlobalCoordinate().getTranslatedCoordinate(getLedgerLocalCoordinate(side, uniqueID));
    }
}
