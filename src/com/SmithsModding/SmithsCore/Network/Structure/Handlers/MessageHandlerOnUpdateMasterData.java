package com.SmithsModding.SmithsCore.Network.Structure.Handlers;

import com.SmithsModding.SmithsCore.Common.Structures.*;
import com.SmithsModding.SmithsCore.Network.Structure.Messages.*;
import com.SmithsModding.SmithsCore.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.client.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.fml.client.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

/**
 * Created by Orion Created on 04.07.2015 16:01
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerOnUpdateMasterData implements IMessageHandler<MessageOnUpdateMasterData, IMessage> {

    @Override
    public IMessage onMessage (final MessageOnUpdateMasterData message, final MessageContext ctx) {

        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
            @Override
            public void run () {
                TileEntity tMasterEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.iTECoordinate.toBlockPos());

                if (tMasterEntity instanceof IStructureComponent) {
                    ( (IStructureComponent) tMasterEntity ).initiateAsMasterEntity();

                    for (Coordinate3D tSlaveCoord : message.iSlaveCoords) {
                        try {
                            ( (IStructureComponent) tMasterEntity ).registerNewSlave(FMLClientHandler.instance().getClient().theWorld.getTileEntity(tSlaveCoord.toBlockPos()));
                        } catch (Exception Ex) {
                            SmithsCore.getLogger().info("Failed to handle a Coordinate while synchronising structure data: " + tSlaveCoord.toString());
                        }
                    }
                }
            }
        });

        return null;
    }
}
