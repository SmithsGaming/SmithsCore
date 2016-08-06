package com.smithsmodding.smithscore.util;

import com.smithsmodding.smithscore.client.model.states.SmithsCoreOBJState;
import com.smithsmodding.smithscore.client.properties.SmithsCoreOBJProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.property.IUnlistedProperty;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * Created by Orion
 * Created on 26.10.2015
 * 12:32
 *
 * Copyrighted according to Project specific license
 */
public class CoreReferences {

    public static final class General {
        public static final String MOD_ID = "SmithsCore";
        public static final String VERSION = "@VERSION@";
        public static final String MC_VERSION = "@MCVERSION@";
        public static final String API_VERSION = "@APIVERSION@";
    }

    public static final class BookData {
        public static final ResourceLocation PAGEBACKGROUND = new ResourceLocation(General.MOD_ID.toLowerCase(), "Book.Background");
    }

    public static final class NBT {
        public static final String OWNER = "owner";
        public static final String UUID_MOST_SIG = "UUIDMostSig";
        public static final String UUID_LEAST_SIG = "UUIDLeastSig";
        public static final String OWNER_UUID_MOST_SIG = "ownerUUIDMostSig";
        public static final String OWNER_UUID_LEAST_SIG = "ownerUUIDLeastSig";

        public static final String NAME = "Name";

        public static final String FLUIDS = "SmithsCore.Fluids";
        public static final String SIDE = "SmithsCore.Side";
        public static final String TANKCONTENTS = "SmitshCore.TankContents";
        public static final String INVENTORY = "SmithsCore.Inventory";
        public static final String STATE = "SmithsCore.State";
        public static final String STRUCTURE = "SmithsCore.Structure";

        public static class InventoryData {
            public static final String SLOTINDEX = "SmithsCore.Inventory.SlotIndex";
            public static final String STACKDATA = "SmithsCore.Inventory.ItemStackData";
        }

        public static class StructureData {
            public static final String TYPE = "SmithsCore.Structure.Type";
            public static final String STRUCTURE = "SmithsCore.Structure.Structures";
            public static final String STORE = "SmithsCore.Structure.Store";
            public static final String DIMENSION = "SmithsCore.Structure.Dimension";
            public static final String MASTERLOCATION = "SmithsCore.Structure.MasterLocation";
            public static final String OLDMASTER = "SmithsCore.Structure.OldMaster";


            public static final String ISSLAVED = "SmithsCore.Structure.Slaved";

            public static final String SLAVELOCATIONS = "SmithsCore.Structure.SlaveLocations";
        }

        public static class Coordinates {
            public static final String X = "SmithsCore.Coordinates.X";
            public static final String Y = "SmithsCore.Coordinates.Y";
            public static final String Z = "SmithsCore.Coordinates.Z";
        }

        public static final class IItemProperties {
            public static final String TARGET = "SmithsCore.ModelTypeTargetRequest";
        }

        public static final class MultiFluidTank {
            public static final String CONTENTS = "SmitshCore.Fluid.MultiFluidTank.Contents";
        }
    }

    public static final class IItemProperties {
        public static final ResourceLocation MODELTYPE = new ResourceLocation(General.MOD_ID.toLowerCase(), "ModelType");
    }

    public static final class BlockStateProperties {

        public static final class Unlisted {
            public static final IUnlistedProperty<SmithsCoreOBJState> OBJSTATE = SmithsCoreOBJProperty.INSTANCE;
        }
    }

    public static final class LogMarkers {
        public static final Marker CLIENT = MarkerManager.getMarker("[client]");
        public static final Marker COMMON = MarkerManager.getMarker("[common]");

        public static final Marker PREINIT = MarkerManager.getMarker("[Pre-Init]", COMMON);
        public static final Marker INIT = MarkerManager.getMarker("[Init]", COMMON);
        public static final Marker POSTINIT = MarkerManager.getMarker("[Post-Init]", COMMON);

        public static final Marker RENDER = MarkerManager.getMarker("[Render]", CLIENT);

        public static final Marker STRUCTURE = MarkerManager.getMarker("[Structure]", COMMON);
        public static final Marker SERIALIZATION = MarkerManager.getMarker("[Serialization]", COMMON);
    }
}
