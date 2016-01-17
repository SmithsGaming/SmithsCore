package com.smithsmodding.smithscore.util;

import org.apache.logging.log4j.*;

/**
 * Created by Orion
 * Created on 26.10.2015
 * 12:32
 * <p/>
 * Copyrighted according to Project specific license
 */
public class CoreReferences {

    public static final class General {
        public static final String MOD_ID = "smithscore";
        public static final String VERSION = "@VERSION@";
        public static final String MC_VERSION = "@MCVERSION@";
        public static final String API_VERSION = "@APIVERSION@";
    }

    public static final class NBT {
        public static final String OWNER = "owner";
        public static final String UUID_MOST_SIG = "UUIDMostSig";
        public static final String UUID_LEAST_SIG = "UUIDLeastSig";
        public static final String OWNER_UUID_MOST_SIG = "ownerUUIDMostSig";
        public static final String OWNER_UUID_LEAST_SIG = "ownerUUIDLeastSig";

        public static final String FLUIDS = "smithscore.Fluids";
        public static final String INVENTORY = "smithscore.inventory";
        public static final String STATE = "smithscore.state";
        public static final String STRUCTURE = "smithscore.Structure";

        public static class InventoryData {
            public static final String SLOTINDEX = "smithscore.inventory.SlotIndex";
            public static final String STACKDATA = "smithscore.inventory.ItemStackData";
        }

        public static class StructureData {
            public static final String ISSLAVED = "smithscore.Structure.Slaved";

            public static final String MASTERLOCATION = "smithscore.Structure.MasterLocation";

            public static final String SLAVELOCATIONS = "smithscore.Structure.SlaveLocations";
        }

        public static class Coordinates {
            public static final String X = "smithscore.Coordaintes.X";
            public static final String Y = "smithscore.Coordaintes.Y";
            public static final String Z = "smithscore.Coordaintes.Z";
        }
    }

    public static final class LogMarkers {
        public static final Marker CLIENT = MarkerManager.getMarker("[client]");
        public static final Marker COMMON = MarkerManager.getMarker("[common]");

        public static final Marker PREINIT = MarkerManager.getMarker("[Pre-Init]", COMMON);
        public static final Marker INIT = MarkerManager.getMarker("[Init]", COMMON);
        public static final Marker POSTINIT = MarkerManager.getMarker("[Post-Init]", COMMON);

        public static final Marker RENDER = MarkerManager.getMarker("[Render]", CLIENT);

        public static final Marker TESYNC = MarkerManager.getMarker("[TE-Sync]", COMMON);
    }
}
