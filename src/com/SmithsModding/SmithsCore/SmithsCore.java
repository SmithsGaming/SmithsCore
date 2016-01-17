package com.smithsmodding.smithscore;

import com.google.common.base.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.*;

import java.util.concurrent.*;

/**
 * Created by Orion
 * Created on 25.10.2015
 * 21:46
 * <p/>
 * Copyrighted according to Project specific license
 */

@Mod(modid = CoreReferences.General.MOD_ID, name = "smithscore", version = CoreReferences.General.VERSION,
        dependencies = "required-after:Forge@[10.13,)")
public class SmithsCore {

    // Instance of this mod use for internal and Forge references
    @Mod.Instance(CoreReferences.General.MOD_ID)
    private static SmithsCore instance;

    // Logger used to output log messages from smithscore
    private static Logger iLogger = LogManager.getLogger(CoreReferences.General.MOD_ID);

    private static boolean isInDevEnvironment = Boolean.parseBoolean(System.getProperties().getProperty("smithscore.Dev", "false"));

    // Private variable for the Sided registry
    @SidedProxy(clientSide = "com.smithsmodding.smithscore.client.registry.ClientRegistry", serverSide = "com.smithsmodding.smithscore.common.registry.CommonRegistry")
    private static CommonRegistry iRegistry;

    // Proxies used to register stuff client and server side.
    @SidedProxy(clientSide = "com.smithsmodding.smithscore.client.proxy.CoreClientProxy", serverSide = "com.smithsmodding.smithscore.common.proxy.CoreCommonProxy")
    private static CoreCommonProxy proxy;

    public static final SmithsCore getInstance(){ return instance; }

    public static final Logger getLogger() { return iLogger; }

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
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "smithscore starting in Dev mode. Current active Features:");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "  > Additional log output.");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "  > Debug rendering of structures.");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "  > Debug overlay rendering of UI components,");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            getLogger().warn(CoreReferences.LogMarkers.PREINIT, "");
        }
        else
        {
            getLogger().info(CoreReferences.LogMarkers.PREINIT, "smithscore starting in Normal mode.");
        }

        getLogger().info(CoreReferences.LogMarkers.PREINIT, "Initializing event handlers.");
        getRegistry().registerEventHandlers();
        getRegistry().initializeNetwork();

        watch.stop();

        Long milliseconds = watch.elapsed(TimeUnit.MILLISECONDS);
        getLogger().info(CoreReferences.LogMarkers.PREINIT, "smithscore Pre-Init completed after: " + milliseconds + " mS.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();

        proxy.Init();

        watch.stop();

        Long milliseconds = watch.elapsed(TimeUnit.MILLISECONDS);
        getLogger().info(CoreReferences.LogMarkers.INIT, "smithscore Init completed after: " + milliseconds + " ms.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();

        watch.stop();

        Long milliseconds = watch.elapsed(TimeUnit.MILLISECONDS);
        getLogger().info(CoreReferences.LogMarkers.POSTINIT, "smithscore Post-Init completed after: " + milliseconds + " ms.");
    }
}
