package com.smithsmodding.smithscore.common.events.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public class NBTNetworkableEvent extends StandardNetworkableEvent {

    protected NBTTagCompound getSyncCompound() {
        return new NBTTagCompound();
    }

    protected void readSyncCompound(NBTTagCompound compound) {
        return;
    }

    @Override
    public void readFromMessageBuffer(ByteBuf pMessageBuffer) {
        readSyncCompound(ByteBufUtils.readTag(pMessageBuffer));
    }

    @Override
    public void writeToMessageBuffer(ByteBuf pMessageBuffer) {
        ByteBufUtils.writeTag(pMessageBuffer, getSyncCompound());
    }
}
