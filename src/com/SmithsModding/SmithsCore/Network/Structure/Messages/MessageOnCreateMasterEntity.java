package com.SmithsModding.SmithsCore.Network.Structure.Messages;

import com.SmithsModding.SmithsCore.Common.Structures.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

/**
 * Created by Orion Created on 04.07.2015 15:24
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageOnCreateMasterEntity implements IMessage {

    public Coordinate3D iTECoordinate;

    public MessageOnCreateMasterEntity (IStructureComponent pComponentToSync) {
        iTECoordinate = pComponentToSync.getLocation();
    }

    public MessageOnCreateMasterEntity () {

    }

    @Override
    public void fromBytes (ByteBuf buf) {
        iTECoordinate = Coordinate3D.fromBytes(buf);
    }

    @Override
    public void toBytes (ByteBuf buf) {
        iTECoordinate.toBytes(buf);
    }
}
