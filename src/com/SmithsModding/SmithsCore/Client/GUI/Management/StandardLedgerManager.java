package com.SmithsModding.SmithsCore.Client.GUI.Management;

import com.SmithsModding.SmithsCore.Client.GUI.Host.*;
import com.SmithsModding.SmithsCore.Client.GUI.Ledgers.Core.*;
import com.SmithsModding.SmithsCore.Client.GUI.State.*;
import com.SmithsModding.SmithsCore.*;
import com.SmithsModding.SmithsCore.Util.Common.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import com.SmithsModding.SmithsCore.Util.*;

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
        ledger.getAllComponents().clear();
        ledger.registerComponents(ledger);

        ledgersRight.put(ledger.getID(), ledger);
    }

    @Override
    public void registerLedgerLeftSide (IGUILedger ledger) {
        ledger.getAllComponents().clear();
        ledger.registerComponents(ledger);

        ledgersLeft.put(ledger.getID(), ledger);
    }

    @Override
    public int getLedgerIndex (LedgerConnectionSide side, String uniqueID) {
        if (side == LedgerConnectionSide.LEFT) {
            return ( ( new ArrayList<String>(ledgersLeft.keySet()) ) ).indexOf(uniqueID);
        } else {
            return ( ( new ArrayList<String>(ledgersRight.keySet()) ) ).indexOf(uniqueID);
        }
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
            horizontalOffset = getHost().getLeftLedgerOffSet();
        } else {
            ledgers = ledgersRight;
            horizontalOffset = getHost().getRightLedgerOffSet();
        }

        Iterator<String> iterator = ledgers.keySet().iterator();
        Coordinate2D root = new Coordinate2D(horizontalOffset, 4);

        String key, last;

        key = "";
        last = "";

        while (iterator.hasNext()) {
            last = key;
            key = iterator.next();

            if (last != "")
                root = root.getTranslatedCoordinate(new Coordinate2D(0, ledgers.get(last).getSize().getHeigth()));

            if (key.equals(uniqueID)) {
                return root;
            }
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
        IGUILedger ledger;

        if (side == LedgerConnectionSide.LEFT) {
            ledger = ledgersLeft.get(uniqueID);
        }
        else
        {
            ledger = ledgersRight.get(uniqueID);
        }

        return ledger.getGlobalCoordinate();
    }

    /**
     * Method called by a ledger to indicate that the opened ledger should be changed.
     *
     * @param ledger The ledger in which was clicked, either to indicate that this one should be opened (and a other one
     *               closed) or this one should be closed.
     */
    @Override
    public void onLedgerClickedInside (IGUILedger ledger) {
        String openLedgerID = (String) InstanceVariableManager.getVariable(this.getHost().getID() + ".LastOpenLedger");

        if (openLedgerID == null)
            openLedgerID = "";

        if (openLedgerID != "" && openLedgerID != ledger.getID()) {
            //Closing the old Ledger
            int i = getLedgerIndex(LedgerConnectionSide.LEFT, openLedgerID);
            if (i > -1) {
                //Closing a left ledger
                IGUILedger ledger1 = getLeftLedgers().get(openLedgerID);
                ( (LedgerComponentState) ledger1.getState() ).toggleOpenState();
            } else {
                i = getLedgerIndex(LedgerConnectionSide.RIGHT, openLedgerID);
                if (i > -1) {
                    //Closing a right ledger
                    IGUILedger ledger1 = getRightLedgers().get(openLedgerID);
                    ( (LedgerComponentState) ledger1.getState() ).toggleOpenState();
                } else {
                    SmithsCore.getLogger().error(CoreReferences.LogMarkers.RENDER, "A saved ledger cannot be found!");
                }
            }
        }

        ( (LedgerComponentState) ledger.getState() ).toggleOpenState();

        if (!((LedgerComponentState) ledger.getState()).getOpenState()) {
            InstanceVariableManager.setVariable(this.getHost().getID() + ".LastOpenLedger", "");
        }
        else {
            InstanceVariableManager.setVariable(this.getHost().getID() + ".LastOpenLedger", ledger.getID());
        }

    }
}
