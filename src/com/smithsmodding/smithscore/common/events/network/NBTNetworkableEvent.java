package com.smithsmodding.smithscore.common.events.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public abstract class NBTNetworkableEvent extends StandardNetworkableEvent {

    protected abstract NBTTagCompound getSyncCompound();

    protected abstract void readSyncCompound(NBTTagCompound compound);

    @Override
    public void readFromMessageBuffer(ByteBuf pMessageBuffer) {
        readSyncCompound(ByteBufUtils.readTag(pMessageBuffer));
    }

    @Override
    public void writeToMessageBuffer(ByteBuf pMessageBuffer) {
        ByteBufUtils.writeTag(pMessageBuffer, getSyncCompound());
    }
}
