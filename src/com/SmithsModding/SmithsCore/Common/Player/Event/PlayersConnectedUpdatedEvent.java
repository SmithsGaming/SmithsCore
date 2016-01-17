/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.Player.Event;

import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;

import java.util.*;

public class PlayersConnectedUpdatedEvent extends StandardNetworkableEvent {

    private HashMap<UUID, String> commonSidedJoinedMap = new HashMap<UUID, String>();

    public PlayersConnectedUpdatedEvent(PlayerManager manager) {
        commonSidedJoinedMap = manager.getCommonSidedJoinedMap();
    }

    /**
     * Getter for the map that contais a UUID to Username mapping for all the player that ever connected to this
     * Server based on the world save.
     *
     * @return The map that contais a UUID to Username mapping for all the player that ever connected to this server based on the world save.
     */
    public HashMap<UUID, String> getCommonSidedJoinedMap() {
        return commonSidedJoinedMap;
    }

    /**
     * Function used by the instance created on the receiving side to reset its state from the sending side stored
     * by it in the Buffer before it is being fired on the NetworkRelayBus.
     *
     * @param pMessageBuffer The ByteBuffer from the IMessage used to Synchronize the implementing events.
     */
    @Override
    public void readFromMessageBuffer(ByteBuf pMessageBuffer) {
        int pairCount = pMessageBuffer.readInt();

        for (int pairIndex = 0; pairIndex < pairCount; pairIndex++) {
            commonSidedJoinedMap.put(NetworkHelper.readUUID(pMessageBuffer), ByteBufUtils.readUTF8String(pMessageBuffer));
        }
    }

    /**
     * Function used by the instance on the sending side to write its state top the Buffer before it is send to the
     * retrieving side.
     *
     * @param pMessageBuffer The buffer from the IMessage
     */
    @Override
    public void writeToMessageBuffer(ByteBuf pMessageBuffer) {
        pMessageBuffer.writeInt(commonSidedJoinedMap.size());

        for (UUID id : commonSidedJoinedMap.keySet()) {
            NetworkHelper.writeUUID(pMessageBuffer, id);
            ByteBufUtils.writeUTF8String(pMessageBuffer, commonSidedJoinedMap.get(id));
        }
    }
}
