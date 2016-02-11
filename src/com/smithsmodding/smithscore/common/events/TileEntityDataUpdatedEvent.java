package com.smithsmodding.smithscore.common.events;

import com.smithsmodding.smithscore.*;
import com.smithsmodding.smithscore.common.events.network.*;
import com.smithsmodding.smithscore.common.tileentity.*;
import com.smithsmodding.smithscore.network.event.*;
import com.smithsmodding.smithscore.util.*;
import io.netty.buffer.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.client.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.*;

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
        EventNetworkManager.getInstance().sendToAllAround(this.getCommunicationMessage(Side.CLIENT), new NetworkRegistry.TargetPoint(tileEntitySmithsCore.getWorld().provider.getDimensionId(), tileEntitySmithsCore.getPos().getX(), tileEntitySmithsCore.getPos().getY(), tileEntitySmithsCore.getPos().getZ(), 128));
    }
}
