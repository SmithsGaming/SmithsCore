package com.SmithsModding.SmithsCore.Network.Structure.Messages;

import com.SmithsModding.SmithsCore.Common.Structures.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

/**
 * Created by Orion Created on 04.07.2015 16:04
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageOnCreateSlaveEntity implements IMessage {


    public Coordinate3D iTECoordinate;
    public Coordinate3D iMasterCoorinate;

    public MessageOnCreateSlaveEntity (IStructureComponent pComponentToSync, IStructureComponent pMasterComponent) {
        iTECoordinate = pComponentToSync.getLocation();
        iMasterCoorinate = pMasterComponent.getLocation();
    }

    public MessageOnCreateSlaveEntity () {

    }

    @Override
    public void fromBytes (ByteBuf buf) {
        iTECoordinate = Coordinate3D.fromBytes(buf);
        iMasterCoorinate = Coordinate3D.fromBytes(buf);
    }

    @Override
    public void toBytes (ByteBuf buf) {
        iTECoordinate.toBytes(buf);
        iMasterCoorinate.toBytes(buf);
    }
}
