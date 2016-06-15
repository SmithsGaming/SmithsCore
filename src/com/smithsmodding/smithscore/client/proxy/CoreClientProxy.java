package com.smithsmodding.smithscore.client.proxy;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.client.handlers.gui.*;
import com.smithsmodding.smithscore.client.handlers.network.ClientNetworkableEventHandler;
import com.smithsmodding.smithscore.client.model.loader.MultiComponentModelLoader;
import com.smithsmodding.smithscore.client.registry.ClientRegistry;
import com.smithsmodding.smithscore.common.handlers.network.CommonNetworkableEventHandler;
import com.smithsmodding.smithscore.common.player.handlers.PlayersConnectedUpdatedEventHandler;
import com.smithsmodding.smithscore.common.player.handlers.PlayersOnlineUpdatedEventHandler;
import com.smithsmodding.smithscore.common.proxy.CoreCommonProxy;
import com.smithsmodding.smithscore.util.client.ResourceHelper;
import com.smithsmodding.smithscore.util.client.Textures;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

/**
 * Specific proxy class used to initialize client only sides of this Mod
 * It is the common point of entry after the Modclass receives notice of a Init-state update, on the client (and the internal server) side,
 * through one of his eventhandlers.
 *
 * Created by Orion
 * Created on 26.10.2015
 * 12:49
 *
 * Copyrighted according to Project specific license
 */
public class CoreClientProxy extends CoreCommonProxy {

    MultiComponentModelLoader multiComponentModelLoader = MultiComponentModelLoader.instance;

    public static ResourceLocation registerMultiComponentItemModel(Item item) {
        ResourceLocation itemLocation = ResourceHelper.getItemLocation(item);
        if (itemLocation == null) {
            return null;
        }

        String path = "component/" + itemLocation.getResourcePath() + MultiComponentModelLoader.EXTENSION;

        return registerMultiComponentItemModel(item, new ResourceLocation(itemLocation.getResourceDomain(), path));
    }

    public static ResourceLocation registerMultiComponentItemModel(Item item, final ResourceLocation location) {
        if (!location.getResourcePath().endsWith(MultiComponentModelLoader.EXTENSION)) {
            SmithsCore.getLogger().error("The MultiComponent-model " + location.toString() + " does not end with '"
                    + MultiComponentModelLoader.EXTENSION
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        return registerItemModelDefinition(item, location, MultiComponentModelLoader.EXTENSION);
    }

    public static ResourceLocation registerItemModelDefinition(Item item, final ResourceLocation location, String requiredExtension) {
        if (!location.getResourcePath().endsWith(requiredExtension)) {
            SmithsCore.getLogger().error("The item-model " + location.toString() + " does not end with '"
                    + requiredExtension
                    + "' and will therefore not be loaded by the custom model loader!");
        }

        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(location, "inventory");
            }
        });

        // We have to read the default variant if we have custom variants, since it wont be added otherwise and therefore not loaded
        ModelBakery.registerItemVariants(item, location);

        SmithsCore.getLogger().info("Added model definition for: " + item.getUnlocalizedName() + " add: " + location.getResourcePath() + " in the Domain: " + location.getResourceDomain());

        return location;
    }

    /**
     * Function used to prepare mods and plugins for the Init-Phase
     * Also initializes most of the network code for the client- and Serverside.
     *
     * The configuration handler is initialized by a different function.
     */
    @Override
    public void preInit() {
        super.preInit();

        ModelLoaderRegistry.registerLoader(multiComponentModelLoader);
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
     *
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

        SmithsCore.getRegistry().getClientBus().register(new ButtonInputEventHandler());

        MinecraftForge.EVENT_BUS.register(new Textures());
        MinecraftForge.EVENT_BUS.register(((ClientRegistry) SmithsCore.getRegistry()).getTextureCreator());
        MinecraftForge.EVENT_BUS.register(( (ClientRegistry) SmithsCore.getRegistry() ).getMouseManager());
        MinecraftForge.EVENT_BUS.register(new ClientTickEventHandler());
        MinecraftForge.EVENT_BUS.register(new RenderGameOverlayEventHandler());
    }

}
