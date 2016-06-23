package com.smithsmodding.smithscore.common.events;

import com.smithsmodding.smithscore.common.events.network.StandardNetworkableEvent;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by Marc on 18.12.2015.
 */
public class TileEntityDataUpdatedEvent extends StandardNetworkableEvent {
    NBTTagCompound dataCompound;
    NetworkRegistry.TargetPoint targetPoint;

    public TileEntityDataUpdatedEvent () {
    }

    public TileEntityDataUpdatedEvent (TileEntitySmithsCore tileEntitySmithsCore) {
        this.dataCompound = tileEntitySmithsCore.writeToSynchronizationCompound(new NBTTagCompound());
        this.targetPoint = new NetworkRegistry.TargetPoint(tileEntitySmithsCore.getWorld().provider.getDimension(), tileEntitySmithsCore.getPos().getX(), tileEntitySmithsCore.getPos().getY(), tileEntitySmithsCore.getPos().getZ(), 128);
    }

    @Override
    public void readFromMessageBuffer(ByteBuf pMessageBuffer) {
        dataCompound = ByteBufUtils.readTag(pMessageBuffer);
    }

    @Override
    public void writeToMessageBuffer(ByteBuf pMessageBuffer) {
        ByteBufUtils.writeTag(pMessageBuffer, dataCompound);
    }

    public NBTTagCompound getDataCompound() {
        return dataCompound;
    }
}
