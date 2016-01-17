package com.smithsmodding.smithscore.client.Handlers.GUI;

import net.minecraft.tileentity.*;
import net.minecraftforge.fml.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

/**
 * Created by Marc on 30.12.2015.
 */
public class BlockModelUpdateEventHandler {

    @SubscribeEvent
    public void handleUpdataEvent (BlockModelUpdateEvent event) {
        TileEntity entity = FMLClientHandler.instance().getClientPlayerEntity().worldObj.getTileEntity(event.getBlockPosition().toBlockPos());

        if (!( entity instanceof IBlockModelUpdatingTileEntity ))
            return;

        entity.getWorld().markBlockForUpdate(entity.getPos());
    }
}
