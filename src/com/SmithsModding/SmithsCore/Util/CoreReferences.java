package com.SmithsModding.SmithsCore.Util;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * Created by Orion
 * Created on 26.10.2015
 * 12:32
 * <p/>
 * Copyrighted according to Project specific license
 */
public class CoreReferences {

    public static final class General {
        public static final String MOD_ID = "SmithsCore";
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
    }

    public static final class LogMarkers {
        public static final Marker CLIENT = MarkerManager.getMarker("[Client]");
        public static final Marker COMMON = MarkerManager.getMarker("[Common]");

        public static final Marker PREINIT = MarkerManager.getMarker("[Pre-Init]", COMMON);
        public static final Marker INIT = MarkerManager.getMarker("[Init]", COMMON);
        public static final Marker POSTINIT = MarkerManager.getMarker("[Post-Init]", COMMON);

        public static final Marker RENDER = MarkerManager.getMarker("[Render]", CLIENT);
    }
}
