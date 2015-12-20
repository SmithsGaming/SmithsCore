package com.SmithsModding.SmithsCore.Network.Structure.Handlers;

import com.SmithsModding.SmithsCore.Common.Structures.*;
import com.SmithsModding.SmithsCore.Network.Structure.Messages.*;
import net.minecraft.client.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.fml.client.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

/**
 * Created by Orion Created on 04.07.2015 15:54
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerOnCreateMasterEntity implements IMessageHandler<MessageOnCreateMasterEntity, IMessage> {

    @Override
    public IMessage onMessage (final MessageOnCreateMasterEntity message, final MessageContext ctx) {

        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
            @Override
            public void run () {
                TileEntity tEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.iTECoordinate.toBlockPos());
                if (tEntity instanceof IStructureComponent) {
                    ( (IStructureComponent) tEntity ).initiateAsMasterEntity();
                }

            }
        });

        return null;
    }
}
