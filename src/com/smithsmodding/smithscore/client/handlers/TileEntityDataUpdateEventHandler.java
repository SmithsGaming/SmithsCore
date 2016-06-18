package com.smithsmodding.smithscore.client.handlers;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.events.TileEntityDataUpdatedEvent;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Author Marc (Created on: 18.06.2016)
 */
public class TileEntityDataUpdateEventHandler
{

    @SubscribeEvent
    void onTileEntityUpdated(TileEntityDataUpdatedEvent event) {
        NBTTagCompound dataCompound = event.getDataCompound();

        TileEntity tileEntity = FMLClientHandler.instance().getClientPlayerEntity().getEntityWorld().getTileEntity(new BlockPos(dataCompound.getInteger("x"), dataCompound.getInteger("y"), dataCompound.getInteger("z")));
        if (tileEntity == null || !(tileEntity instanceof TileEntitySmithsCore)) {
            SmithsCore.getLogger().error(CoreReferences.LogMarkers.TESYNC, "Received a TESync-Event for a not syncable TE. This is supposed to be impossible and is a BUG!");
        }

        TileEntitySmithsCore tileEntitySmithsCore = (TileEntitySmithsCore) tileEntity;
        tileEntitySmithsCore.readFromSynchronizationCompound(dataCompound);
    }
}
