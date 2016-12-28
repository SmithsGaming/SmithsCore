package com.smithsmodding.smithscore.client.handlers;

import com.smithsmodding.smithscore.common.events.TileEntityDataUpdatedEvent;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public class TileEntityDataUpdatedEventHandler {

    @SubscribeEvent
    public void onDataUpdated(@Nonnull TileEntityDataUpdatedEvent event) {
        TileEntity tileEntity = FMLClientHandler.instance().getClientPlayerEntity().getEntityWorld().getTileEntity(new BlockPos(event.getDataCompound().getInteger("x"), event.getDataCompound().getInteger("y"), event.getDataCompound().getInteger("z")));

        if (tileEntity == null || !(tileEntity instanceof TileEntitySmithsCore)) {
            return;
        }

        ((TileEntitySmithsCore) tileEntity).readFromSynchronizationCompound(event.getDataCompound());
    }
}
