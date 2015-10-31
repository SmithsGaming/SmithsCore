package com.SmithsModding.SmithsCore.Client.Proxy;

import com.SmithsModding.SmithsCore.Client.Handlers.Network.ClientNetworkableEventHandler;
import com.SmithsModding.SmithsCore.Common.Proxy.CoreCommonProxy;
import com.SmithsModding.SmithsCore.SmithsCore;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.io.File;

/**
 * Specific proxy class used to initialize Client only sides of this Mod
 * It is the common point of entry after the Modclass receives notice of a Init-state update, on the Client (and the internal server) side,
 * through one of his eventhandlers.
 * <p/>
 * Created by Orion
 * Created on 26.10.2015
 * 12:49
 * <p/>
 * Copyrighted according to Project specific license
 */
public class CoreClientProxy extends CoreCommonProxy {
    /**
     * Function used to prepare mods and plugins for the Init-Phase
     * Also initializes most of the Network code for the Client- and Serverside.
     * <p/>
     * <p/>
     * The configuration handler is initialized by a different function.
     */
    @Override
    public void preInit() {
        super.preInit();
    }

    /**
     * Function used to initialize this mod.
     * It sets parameters used in most of its functions as common mod for SmithsModding mods.
     */
    @Override
    public void Init() {
        super.Init();
    }

    /**
     * Function used to change behaviour of this mod based on loaded mods.
     */
    @Override
    public void postInit() {
        super.postInit();
    }

    /**
     * Function used to initialize the configuration classes that are common between client and server
     *
     * @param pSuggestedConfigFile The file (or directory given that Java makes no difference between the two) that is suggested to contain configuration options for this mod.
     *                             This parameter is in normal situations populated with the suggested configuration File from the PreInit event.
     * @see File
     * @see FMLPreInitializationEvent
     */
    @Override
    public void configInit(File pSuggestedConfigFile) {
        super.configInit(pSuggestedConfigFile);
    }

    /**
     * Function used to get the effective running side.
     * Basically indicates if elements marked with SideOnly(Side.Client) or SideOnly(Side.SERVER) are available to the JVM
     * As the Client side of this Proxy inherits from this Common one it overrides this function and returns Side.Client instead of value returned from here.
     * <p/>
     * The value returned here does not indicate if the user is running a dedicated or a internal server. It only indicated that the instance of minecraft has GUI-Capabilities or not.
     *
     * @return The effective running Side of this Proxy
     * @see SideOnly
     * @see CoreCommonProxy
     */
    @Override
    public Side getEffectiveSide() {
        return Side.CLIENT;
    }

    /**
     * Function called from preInit() to register all of the Eventhandlers used by Common code.
     */
    @Override
    protected void registerEventHandlers() {
        SmithsCore.getRegistry().getClientBus().register(new ClientNetworkableEventHandler());
        SmithsCore.getRegistry().getCommonBus().register(new ClientNetworkableEventHandler());
    }
}
