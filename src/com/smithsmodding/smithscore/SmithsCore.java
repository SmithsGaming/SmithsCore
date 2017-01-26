package com.smithsmodding.smithscore;

import com.google.common.base.Stopwatch;
import com.smithsmodding.smithscore.common.player.management.PlayerManager;
import com.smithsmodding.smithscore.common.proxy.CoreCommonProxy;
import com.smithsmodding.smithscore.common.registry.CommonRegistry;
import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by Orion
 * Created on 25.10.2015
 * 21:46
 *
 * Copyrighted according to Project specific license
 */
@Mod(modid = CoreReferences.General.MOD_ID, name = "smithscore", version = CoreReferences.General.VERSION,
        dependencies = "required-after:forge@[13.19,)")
public class SmithsCore {

    // Instance of this mod use for internal and Forge references
    @Mod.Instance(CoreReferences.General.MOD_ID)
    private static SmithsCore instance;

    // Logger used to output log messages from smithscore
    private static Logger iLogger = LogManager.getLogger(CoreReferences.General.MOD_ID);

    private static boolean isInDevEnvironment = Boolean.parseBoolean(System.getProperties().getProperty("SmithsCore.Dev", "false"));

    // Private variable for the Sided registry
    @SidedProxy(clientSide = "com.smithsmodding.smithscore.client.registry.ClientRegistry", serverSide = "com.smithsmodding.smithscore.common.registry.CommonRegistry")
    private static CommonRegistry iRegistry;

    // Proxies used to register stuff client and server side.
    @SidedProxy(clientSide = "com.smithsmodding.smithscore.client.proxy.CoreClientProxy", serverSide = "com.smithsmodding.smithscore.common.proxy.CoreCommonProxy")
    private static CoreCommonProxy proxy;

    public static final SmithsCore getInstance(){ return instance; }

    public static final Logger getLogger() { return iLogger; }

    public static final CoreCommonProxy getProxy() {
        return proxy;
    }

    public static final CommonRegistry getRegistry() {
        return iRegistry;
    }

    public static final boolean isInDevenvironment() {return isInDevEnvironment; }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();

        if (isInDevenvironment()){
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "SmithsCore starting in Dev mode. Current active Features:");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "  > Additional log output.");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "  > Debug rendering of structures.");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "  > Debug overlay rendering of UI components,");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "");
        }
        else
        {
            getLogger().info(CoreReferences.LogMarkers.PREINIT, "SmithsCore starting in Normal mode.");
        }

        proxy.preInit();

        //AutomaticEventBusSubcriptionInjector.inject(event.getModMetadata().parentMod, event.getAsmData());

        watch.stop();

        Long milliseconds = watch.elapsed(TimeUnit.MILLISECONDS);
        getLogger().info(CoreReferences.LogMarkers.PREINIT, "SmithsCore Pre-Init completed after: " + milliseconds + " mS.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();

        proxy.Init();

        watch.stop();

        Long milliseconds = watch.elapsed(TimeUnit.MILLISECONDS);
        getLogger().info(CoreReferences.LogMarkers.INIT, "SmithsCore Init completed after: " + milliseconds + " ms.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();

        watch.stop();

        Long milliseconds = watch.elapsed(TimeUnit.MILLISECONDS);
        getLogger().info(CoreReferences.LogMarkers.POSTINIT, "SmithsCore Post-Init completed after: " + milliseconds + " ms.");
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        PlayerManager.getInstance().onServerStart(event);
    }
}
