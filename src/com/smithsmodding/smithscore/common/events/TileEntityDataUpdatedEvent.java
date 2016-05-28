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
    TileEntitySmithsCore tileEntitySmithsCore;

    public TileEntityDataUpdatedEvent () {
    }

    public TileEntityDataUpdatedEvent (TileEntitySmithsCore tileEntitySmithsCore) {
        this.tileEntitySmithsCore = tileEntitySmithsCore;
    }

    public TileEntitySmithsCore getTileEntitySmithsCore () {
        return tileEntitySmithsCore;
    }

    @Override
    public void readFromMessageBuffer (ByteBuf pMessageBuffer) {
        NBTTagCompound messageCompound = ByteBufUtils.readTag(pMessageBuffer);

        TileEntity tileEntity = FMLClientHandler.instance().getWorldClient().getTileEntity(new BlockPos(messageCompound.getInteger("x"), messageCompound.getInteger("y"), messageCompound.getInteger("z")));

        if (tileEntity == null)
            return;

        if (!( tileEntity instanceof TileEntitySmithsCore )) {
            SmithsCore.getLogger().error(CoreReferences.LogMarkers.TESYNC, "While trying to sync a TE a instance mismatch occurred. This should be impossible and is a BUG!");
            return;
        }

        this.tileEntitySmithsCore = (TileEntitySmithsCore) tileEntity;

        tileEntitySmithsCore.readFromSynchronizationCompound(messageCompound);
    }

    @Override
    public void writeToMessageBuffer (ByteBuf pMessageBuffer) {
        NBTTagCompound messageCompound = new NBTTagCompound();

        tileEntitySmithsCore.writeToSynchronizationCompound(messageCompound);

        ByteBufUtils.writeTag(pMessageBuffer, messageCompound);
    }

    @Override
    public IMessage getCommunicationMessage (Side side) {
        if (side == Side.SERVER)
            return null;

        return super.getCommunicationMessage(side);
    }

    @Override
    public void handleServerToClientSide () {
        EventNetworkManager.getInstance().sendToAllAround(this.getCommunicationMessage(Side.CLIENT), new NetworkRegistry.TargetPoint(tileEntitySmithsCore.getWorld().provider.getDimension(), tileEntitySmithsCore.getPos().getX(), tileEntitySmithsCore.getPos().getY(), tileEntitySmithsCore.getPos().getZ(), 128));
    }
}
