/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.common.helper;

import io.netty.buffer.ByteBuf;

import javax.annotation.Nonnull;
import java.util.UUID;

public class NetworkHelper {
    /**
     * Method to easily write UUIDs to a MessageBuffer.
     *
     * @param buffer The buffer to write to.
     * @param id     The UUID to write.
     */
    public static void writeUUID(@Nonnull ByteBuf buffer, @Nonnull UUID id) {
        buffer.writeLong(id.getMostSignificantBits());
        buffer.writeLong(id.getLeastSignificantBits());
    }

    /**
     * Method used to easily read a UUID from a MessageBuffer
     *
     * @param buffer The buffer to read the UUID from.
     * @return The next UUID stored in the buffer.
     */
    @Nonnull
    public static UUID readUUID(@Nonnull ByteBuf buffer) {
        return new UUID(buffer.readLong(), buffer.readLong());
    }

}
