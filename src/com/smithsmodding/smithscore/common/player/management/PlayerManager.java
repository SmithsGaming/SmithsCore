/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.common.player.management;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.player.event.PlayersConnectedUpdatedEvent;
import com.smithsmodding.smithscore.common.player.event.PlayersOnlineUpdatedEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerManager {

    //Instance of the Manager
    private static final PlayerManager INSTANCE = new PlayerManager();
    //Variable that gets filled with the list of players that have ever played on the server.
    //Gets synchronised over to the client.
    private HashMap<UUID, String> commonSidedJoinedMap = new HashMap<UUID, String>();
    //Variable gets filled with the list of players that are currently connected to the server. Prevents tempering with the global list.
    //Gets synchronised over to the server.
    private List<UUID> commonSidedOnlineMap = new ArrayList<UUID>();
    //Server side only lookup list for UUID -> EntityPlayer;
    private HashMap<UUID, EntityPlayer> serverSidedJoinedMap = new HashMap<UUID, EntityPlayer>();

    /**
     * Method to get the current Instance of the PlayerManager
     *
     * @return The current Instance of the Manager.
     */
    public static PlayerManager getInstance() {
        return INSTANCE;
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
     * Setter for the map that contais a UUID to Username mapping for all the player that ever connected to this
     * Server based on the world save.
     *
     * @param commonSidedJoinedMap The map that contais a UUID to Username mapping for all the player that ever connected to this Server based on the world save.
     */
    public void setCommonSidedJoinedMap(HashMap<UUID, String> commonSidedJoinedMap) {
        this.commonSidedJoinedMap = commonSidedJoinedMap;
    }

    /**
     * Getter for the list of online Players
     *
     * @return The list of online Players
     */
    public List<UUID> getCommonSidedOnlineMap() {
        return commonSidedOnlineMap;
    }

    /**
     * Setter for the list of online Players
     *
     * @param commonSidedOnlineMap The list of online Players
     */
    public void setCommonSidedOnlineMap(List<UUID> commonSidedOnlineMap) {
        this.commonSidedOnlineMap = commonSidedOnlineMap;
    }

    /**
     * Server side only getter for a LookUp map that allows UUID to Entity Conversion without iteration.
     *
     * @return A LookUp map that allows UUID to Entity Conversion without iteration.
     */
    @SideOnly(Side.SERVER)
    public HashMap<UUID, EntityPlayer> getServerSidedJoinedMap() {
        return serverSidedJoinedMap;
    }


    /**
     * Method that handles the event for the Server Starting
     *
     * @param event FMLServerStarting event.
     */
    public void onServerStart(FMLServerStartingEvent event) {
        refreshPlayerUUIDList();

        SmithsCore.getRegistry().getCommonBus().post(new PlayersConnectedUpdatedEvent(this));
        SmithsCore.getRegistry().getCommonBus().post(new PlayersOnlineUpdatedEvent(this));
    }


    /**
     * Method used to handle new players logging into the Server
     *
     * @param event The event fired when a player logs in.
     */
    @SubscribeEvent
    public void onPlayerJoinServer(PlayerEvent.PlayerLoggedInEvent event) {
        SmithsCore.getLogger().info("Updating player UUID list");
        EntityPlayer player = event.player;

        if (!commonSidedJoinedMap.containsKey(player.getGameProfile().getId())) {
            commonSidedJoinedMap.put(player.getGameProfile().getId(), UsernameCache.getLastKnownUsername(player.getGameProfile().getId()));
            SmithsCore.getRegistry().getCommonBus().post(new PlayersConnectedUpdatedEvent(this));
        }

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            serverSidedJoinedMap.put(player.getGameProfile().getId(), player);
        }
        else {
            commonSidedOnlineMap.add(player.getGameProfile().getId());
        }

        SmithsCore.getRegistry().getCommonBus().post(new PlayersOnlineUpdatedEvent(this));
    }

    /**
     * Method used to handle a player logging of. Is used to keep track of who is online.
     *
     * @param event The event fired when a player logs of.
     */
    @SubscribeEvent
    public void onPlayerLeaveServer(PlayerEvent.PlayerLoggedOutEvent event) {
        commonSidedOnlineMap.remove(event.player.getGameProfile().getId());
        SmithsCore.getRegistry().getCommonBus().post(new PlayersOnlineUpdatedEvent(this));
    }


    /**
     * client side only method to handle a client disconnecting from a Server.
     * Resets the connected player list on the client Side.
     *
     * @param event The event indicating that the player Disconnected from the Server.
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientDisconnectServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        SmithsCore.getLogger().info("Disconnect: Clearing cached connected player list.");
        commonSidedJoinedMap.clear();
        commonSidedOnlineMap.clear();
    }


    /**
     * Method used to create a list of all players that connected to this server before smithscore was installed.
     */
    private void refreshPlayerUUIDList() {
        File file = new File(FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getSaveHandler().getWorldDirectory(), "playerdata");

        commonSidedJoinedMap = new HashMap<UUID, String>();
        serverSidedJoinedMap = new HashMap<UUID, EntityPlayer>();

        if (file.isDirectory()) {
            for (File search : file.listFiles()) {
                if (search.getName().contains(".dat")) {
                    try {
                        SmithsCore.getLogger().info("Adding player UUID to list");

                        UUID id = UUID.fromString(search.getName().replaceFirst("[.][^.]+$", ""));

                        commonSidedJoinedMap.put(id, UsernameCache.getLastKnownUsername(UUID.fromString(search.getName().replaceFirst("[.][^.]+$", ""))));
                        serverSidedJoinedMap.put(id, FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(id));
                    } catch (Exception e) {
                        SmithsCore.getLogger().error(e.getStackTrace());
                    }
                }
            }
        }
    }
}
