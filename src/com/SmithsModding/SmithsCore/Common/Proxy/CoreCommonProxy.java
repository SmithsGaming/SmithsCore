package com.smithsmodding.smithscore.common.proxy;


import com.smithsmodding.smithscore.*;
import com.smithsmodding.smithscore.common.handlers.network.*;
import net.minecraftforge.fml.relauncher.*;

import java.io.*;

/**
 * common class used to manage code that runs on both sides of Minecraft.
 * It is the common point of entry after the Modclass receives notice of a Init-state update, on the dedicated server side,
 * through one of his eventhandlers.
 * <p/>
 * Created by Orion
 * Created on 26.10.2015
 * 12:48
 * <p/>
 * Copyrighted according to Project specific license
 */
public class CoreCommonProxy {

    /**
     * Function used to prepare mods and plugins for the Init-Phase
     * <p/>
     * The configuration handler is initialized by a different function.
     */
    public void preInit() {
        registerEventHandlers();
    }

    /**
     * Function used to initialize this mod.
     * It sets parameters used in most of its functions as common mod for smithsmodding mods.
     * Also initializes most of the network code for the Server.
     */
    public void Init() {

    }

    /**
     * Function used to change behaviour of this mod based on loaded mods.
     */
    public void postInit() {

    }

    /**
     * Function used to initialize the configuration classes that are common between client and server
     *
     * @param pSuggestedConfigFile The file (or directory given that Java makes no difference between the two) that is suggested to contain configuration options for this mod.
     *                             This parameter is in normal situations populated with the suggested configuration File from the PreInit event.
     * @see File
     * @see net.minecraftforge.fml.common.event.FMLPreInitializationEvent
     */
    public void configInit(File pSuggestedConfigFile) {

    }

    /**
     * Function used to get the effective running side.
     * Basically indicates if elements marked with SideOnly(Side.client) or SideOnly(Side.SERVER) are available to the JVM
     * As the client side of this proxy inherits from this common one it overrides this function and returns Side.client instead of value returned from here.
     * <p/>
     * The value returned here does not indicate if the user is running a dedicated or a internal server. It only indicated that the instance of minecraft has gui-Capabilities or not.
     *
     * @return The effective running Side of this proxy
     * @see net.minecraftforge.fml.relauncher.SideOnly
     * @see com.smithsmodding.smithscore.client.proxy.CoreClientProxy
     */
    public Side getEffectiveSide() {
        return Side.SERVER;
    }

    /**
     * Function called from preInit() to register all of the Eventhandlers used by common code.
     */
    protected void registerEventHandlers() {
        SmithsCore.getRegistry().getCommonBus().register(new CommonNetworkableEventHandler());
    }
}
