package com.smithsmodding.smithscore.client.handlers.gui;

import com.smithsmodding.smithscore.client.events.models.block.BlockModelUpdateEvent;
import com.smithsmodding.smithscore.common.tileentity.IBlockModelUpdatingTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Marc on 30.12.2015.
 */
public class BlockModelUpdateEventHandler {

    @SubscribeEvent
    public void handleUpdataEvent (BlockModelUpdateEvent event) {
        TileEntity entity = FMLClientHandler.instance().getClientPlayerEntity().worldObj.getTileEntity(event.getBlockPosition().toBlockPos());

        if (!( entity instanceof IBlockModelUpdatingTileEntity ))
            return;

        IBlockState state = entity.getWorld().getBlockState(event.getBlockPosition().toBlockPos());

        entity.getWorld().notifyBlockUpdate(event.getBlockPosition().toBlockPos(), state, state, 3);
    }
}
