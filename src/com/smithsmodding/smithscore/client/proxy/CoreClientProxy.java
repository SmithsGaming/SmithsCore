package com.smithsmodding.smithscore.client.proxy;

import com.smithsmodding.smithscore.*;
import com.smithsmodding.smithscore.client.handlers.gui.*;
import com.smithsmodding.smithscore.client.handlers.network.*;
import com.smithsmodding.smithscore.client.registry.*;
import com.smithsmodding.smithscore.common.handlers.network.*;
import com.smithsmodding.smithscore.common.player.handlers.*;
import com.smithsmodding.smithscore.common.proxy.*;
import com.smithsmodding.smithscore.util.client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.*;

import java.io.*;

/**
 * Specific proxy class used to initialize client only sides of this Mod
 * It is the common point of entry after the Modclass receives notice of a Init-state update, on the client (and the internal server) side,
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
     * Also initializes most of the network code for the client- and Serverside.
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
     * It sets parameters used in most of its functions as common mod for smithsmodding mods.
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
     * Basically indicates if elements marked with SideOnly(Side.client) or SideOnly(Side.SERVER) are available to the JVM
     * As the client side of this proxy inherits from this common one it overrides this function and returns Side.client instead of value returned from here.
     * <p/>
     * The value returned here does not indicate if the user is running a dedicated or a internal server. It only indicated that the instance of minecraft has gui-Capabilities or not.
     *
     * @return The effective running Side of this proxy
     * @see SideOnly
     * @see CoreCommonProxy
     */
    @Override
    public Side getEffectiveSide() {
        return Side.CLIENT;
    }

    /**
     * Function called from preInit() to register all of the Eventhandlers used by common code.
     */
    @Override
    protected void registerEventHandlers() {
        SmithsCore.getRegistry().getClientBus().register(new ClientNetworkableEventHandler());
        SmithsCore.getRegistry().getCommonBus().register(new ClientNetworkableEventHandler());
        SmithsCore.getRegistry().getCommonBus().register(new CommonNetworkableEventHandler());

        SmithsCore.getRegistry().getNetworkBus().register(new GuiInputEventHandler());

        SmithsCore.getRegistry().getNetworkBus().register(new ContainerGUIOpenedEventHandler());
        SmithsCore.getRegistry().getNetworkBus().register(new ContainerGUIClosedEventHandler());

        SmithsCore.getRegistry().getNetworkBus().register(new PlayersOnlineUpdatedEventHandler());
        SmithsCore.getRegistry().getNetworkBus().register(new PlayersConnectedUpdatedEventHandler());

        SmithsCore.getRegistry().getNetworkBus().register(new BlockModelUpdateEventHandler());

        MinecraftForge.EVENT_BUS.register(new Textures());
        MinecraftForge.EVENT_BUS.register(( (ClientRegistry) SmithsCore.getRegistry() ).getHolographicTextureCreator());
        MinecraftForge.EVENT_BUS.register(( (ClientRegistry) SmithsCore.getRegistry() ).getMouseManager());
        MinecraftForge.EVENT_BUS.register(new ClientTickEventHandler());
        MinecraftForge.EVENT_BUS.register(new RenderGameOverlayEventHandler());
    }
}
