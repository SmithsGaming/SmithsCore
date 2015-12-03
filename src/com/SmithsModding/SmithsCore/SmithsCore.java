package com.SmithsModding.SmithsCore;

import com.SmithsModding.SmithsCore.Common.Proxy.CoreCommonProxy;
import com.SmithsModding.SmithsCore.Common.Registry.CommonRegistry;
import com.SmithsModding.SmithsCore.Util.CoreReferences;
import com.google.common.base.Stopwatch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by Orion
 * Created on 25.10.2015
 * 21:46
 * <p/>
 * Copyrighted according to Project specific license
 */

@Mod(modid = CoreReferences.General.MOD_ID, name = "SmithsCore", version = CoreReferences.General.VERSION,
        dependencies = "required-after:Forge@[10.13,)")
public class SmithsCore {

    // Instance of this mod use for internal and Forge references
    @Mod.Instance(CoreReferences.General.MOD_ID)
    public static SmithsCore instance;

    // Logger used to output log messages from SmithsCore
    private static Logger iLogger = LogManager.getLogger("SmithsCore");

    // Private variable for the Sided Registry
    @SidedProxy(clientSide = "com.SmithsModding.SmithsCore.Client.Registry.ClientRegistry", serverSide = "com.SmithsModding.SmithsCore.Common.Registry.CommonRegistry")
    private static CommonRegistry iRegistry;

    // Proxies used to register stuff client and server side.
    @SidedProxy(clientSide = "com.SmithsModding.SmithsCore.Client.Proxy.CoreClientProxy", serverSide = "com.SmithsModding.SmithsCore.Common.Proxy.CoreCommonProxy")
    private static CoreCommonProxy iProxy;

    public static final Logger getLogger() {
        return iLogger;
    }

    public static final CommonRegistry getRegistry() {
        return iRegistry;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();

        getLogger().info(CoreReferences.LogMarkers.PREINIT, "Initializing event handlers.");
        getRegistry().registerEventHandlers();
        getRegistry().initializeNetwork();

        watch.stop();

        Long milliseconds = watch.elapsed(TimeUnit.MILLISECONDS);
        getLogger().info(CoreReferences.LogMarkers.PREINIT, "SmithsCore Pre-Init completed after: " + milliseconds + " mS.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();

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
}
