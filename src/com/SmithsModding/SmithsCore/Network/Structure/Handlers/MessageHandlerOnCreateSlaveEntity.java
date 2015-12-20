package com.SmithsModding.SmithsCore.Network.Structure.Handlers;

import com.SmithsModding.SmithsCore.Common.Structures.*;
import com.SmithsModding.SmithsCore.Network.Structure.Messages.*;
import net.minecraft.client.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.fml.client.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

/**
 * Created by Orion Created on 04.07.2015 16:57
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerOnCreateSlaveEntity implements IMessageHandler<MessageOnCreateSlaveEntity, IMessage> {

    @Override
    public IMessage onMessage (final MessageOnCreateSlaveEntity message, final MessageContext ctx) {

        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
            @Override
            public void run () {
                TileEntity tSlaveEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.iTECoordinate.toBlockPos());
                if (tSlaveEntity instanceof IStructureComponent) {
                    ( (IStructureComponent) tSlaveEntity ).initiateAsSlaveEntity((IStructureComponent) FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.iMasterCoorinate.toBlockPos()));
                }
            }
        });

        return null;
    }
}
