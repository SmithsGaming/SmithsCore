package com.smithsmodding.smithscore.common.events;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.events.network.StandardNetworkableEvent;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.network.event.EventNetworkManager;
import com.smithsmodding.smithscore.util.CoreReferences;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

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

    public NBTTagCompound getDataCompound() {
        return dataCompound;
    }

    @Override
    public void readFromMessageBuffer (ByteBuf pMessageBuffer) {
        dataCompound = ByteBufUtils.readTag(pMessageBuffer);
    }

    @Override
    public void writeToMessageBuffer (ByteBuf pMessageBuffer) {
        ByteBufUtils.writeTag(pMessageBuffer, dataCompound);
    }

    @Override
    public IMessage getCommunicationMessage (Side side) {
        if (side == Side.SERVER)
            return null;

        return super.getCommunicationMessage(side);
    }

    @Override
    public void handleServerToClientSide () {
        EventNetworkManager.getInstance().sendToAllAround(this.getCommunicationMessage(Side.CLIENT), targetPoint);
    }
}
