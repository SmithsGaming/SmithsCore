package com.smithsmodding.smithscore.common.tileentity;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.UUID;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public interface IWatchableTileEntity {
    //TODO: FIX ME!
    @Nonnull
    Collection<UUID> getWatchingPlayers();

    //TODO: FIX ME!
    int getConnectedPlayerCount();
}
