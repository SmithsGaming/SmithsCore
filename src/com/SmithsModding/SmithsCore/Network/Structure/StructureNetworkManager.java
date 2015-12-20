package com.SmithsModding.SmithsCore.Network.Structure;


import com.SmithsModding.SmithsCore.Network.Structure.Handlers.*;
import com.SmithsModding.SmithsCore.Network.Structure.Messages.*;
import com.SmithsModding.SmithsCore.Util.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by Orion Created on 04.07.2015 16:59
 * <p/>
 * Copyrighted according to Project specific license
 */
public class StructureNetworkManager {
    private static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CoreReferences.General.MOD_ID.toLowerCase() + "-structures");

    public static SimpleNetworkWrapper getInstance () {
        return INSTANCE;
    }

    public static void Init () {
        INSTANCE.registerMessage(MessageHandlerOnCreateMasterEntity.class, MessageOnCreateMasterEntity.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(MessageHandlerOnCreateSlaveEntity.class, MessageOnCreateSlaveEntity.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MessageHandlerOnUpdateMasterData.class, MessageOnUpdateMasterData.class, 2, Side.CLIENT);
    }


}
