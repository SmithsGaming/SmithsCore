package com.SmithsModding.SmithsCore.Network.Structure.Messages;

import com.SmithsModding.SmithsCore.Common.Structures.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

import java.util.*;

/**
 * Created by Orion Created on 04.07.2015 15:57
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageOnUpdateMasterData implements IMessage {

    public Coordinate3D iTECoordinate;
    public ArrayList<Coordinate3D> iSlaveCoords = new ArrayList<Coordinate3D>();

    public MessageOnUpdateMasterData (IStructureComponent pMasterComponent) {
        iTECoordinate = pMasterComponent.getMasterEntity().getLocation();

        for (Coordinate3D tSlave : pMasterComponent.getMasterEntity().getSlaveEntities().keySet())
            iSlaveCoords.add(tSlave);
    }

    public MessageOnUpdateMasterData () {

    }

    @Override
    public void fromBytes (ByteBuf buf) {
        iTECoordinate = Coordinate3D.fromBytes(buf);

        int tSlaveCount = buf.readInt();

        for (int tSlave = 0; tSlave < tSlaveCount; tSlave++)
            iSlaveCoords.add(Coordinate3D.fromBytes(buf));
    }

    @Override
    public void toBytes (ByteBuf buf) {
        iTECoordinate.toBytes(buf);

        buf.writeInt(iSlaveCoords.size());

        for (Coordinate3D tSlave : iSlaveCoords)
            tSlave.toBytes(buf);
    }
}

