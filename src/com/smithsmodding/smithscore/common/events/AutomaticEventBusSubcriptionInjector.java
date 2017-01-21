package com.smithsmodding.smithscore.common.events;

import com.google.common.base.Strings;
import com.google.common.collect.SetMultimap;
import com.smithsmodding.smithscore.SmithsCore;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderException;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.asm.ModAnnotation;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Created by marcf on 1/20/2017.
 */
public final class AutomaticEventBusSubcriptionInjector {

    private static final EnumSet<AutomaticEventBusSubscriber.BusType> DEFAULT = EnumSet.allOf(AutomaticEventBusSubscriber.BusType.class);

    public static void inject(ModContainer mod, ASMDataTable data) {
        SmithsCore.getLogger().info("Attempting to inject @AutomaticEventBusSubscriber classes into the eventbus for %s", mod.getModId());
        SetMultimap<String, ASMDataTable.ASMData> modData = data.getAnnotationsFor(mod);
        Set<ASMDataTable.ASMData> mods = modData.get(Mod.class.getName());
        Set<ASMDataTable.ASMData> targets = modData.get(AutomaticEventBusSubscriber.class.getName());
        ClassLoader mcl = Loader.instance().getModClassLoader();

        for (ASMDataTable.ASMData targ : targets) {
            try {
                //noinspection unchecked
                List<ModAnnotation.EnumHolder> sidesEnum = (List<ModAnnotation.EnumHolder>) targ.getAnnotationInfo().get("types");
                EnumSet<AutomaticEventBusSubscriber.BusType> types = DEFAULT;

                if (sidesEnum != null) {
                    types = EnumSet.noneOf(AutomaticEventBusSubscriber.BusType.class);
                    for (ModAnnotation.EnumHolder h : sidesEnum) {
                        types.add(AutomaticEventBusSubscriber.BusType.valueOf(h.getValue()));
                    }
                }

                if ((types == DEFAULT || types.contains(AutomaticEventBusSubscriber.BusType.CLIENT)) && SmithsCore.getProxy().getEffectiveSide() == Side.CLIENT) {
                    SmithsCore.getLogger().info("Found @AutomaticEventBusSubscriber class %s ready for registration on the Client Event bus", targ.getClassName());
                    String amodid = (String) targ.getAnnotationInfo().get("modid");
                    if (Strings.isNullOrEmpty(amodid)) {
                        amodid = ASMDataTable.getOwnerModID(mods, targ);
                        if (Strings.isNullOrEmpty(amodid)) {
                            SmithsCore.getLogger().warn("Could not determine owning mod for @AutomaticEventBusSubscriber on %s for mod %s", targ.getClassName(), mod.getModId());
                            continue;
                        }
                    }
                    if (!mod.getModId().equals(amodid)) {
                        SmithsCore.getLogger().info("Skipping @AutomaticEventBusSubscriber injection for %s since it is not for mod %s", targ.getClassName(), mod.getModId());
                        continue; //We're not injecting this guy
                    }
                    Class<?> subscriptionTarget = Class.forName(targ.getClassName(), true, mcl);
                    SmithsCore.getRegistry().getClientBus().register(subscriptionTarget);
                    SmithsCore.getLogger().info("Injected @AutomaticEventBusSubscriber class %s in the Client Event bus", targ.getClassName());
                }

                if ((types == DEFAULT || types.contains(AutomaticEventBusSubscriber.BusType.SERVER))) {
                    SmithsCore.getLogger().info("Found @AutomaticEventBusSubscriber class %s ready for registration on the Common Event bus", targ.getClassName());
                    String amodid = (String) targ.getAnnotationInfo().get("modid");
                    if (Strings.isNullOrEmpty(amodid)) {
                        amodid = ASMDataTable.getOwnerModID(mods, targ);
                        if (Strings.isNullOrEmpty(amodid)) {
                            SmithsCore.getLogger().warn("Could not determine owning mod for @AutomaticEventBusSubscriber on %s for mod %s", targ.getClassName(), mod.getModId());
                            continue;
                        }
                    }
                    if (!mod.getModId().equals(amodid)) {
                        SmithsCore.getLogger().info("Skipping @AutomaticEventBusSubscriber injection for %s since it is not for mod %s", targ.getClassName(), mod.getModId());
                        continue; //We're not injecting this guy
                    }
                    Class<?> subscriptionTarget = Class.forName(targ.getClassName(), true, mcl);
                    SmithsCore.getRegistry().getCommonBus().register(subscriptionTarget);
                    SmithsCore.getLogger().info("Injected @AutomaticEventBusSubscriber class %s in the Common Event bus", targ.getClassName());
                }

                if ((types == DEFAULT || types.contains(AutomaticEventBusSubscriber.BusType.NETWORK))) {
                    SmithsCore.getLogger().info("Found @AutomaticEventBusSubscriber class %s ready for registration on the Network Event bus", targ.getClassName());
                    String amodid = (String) targ.getAnnotationInfo().get("modid");
                    if (Strings.isNullOrEmpty(amodid)) {
                        amodid = ASMDataTable.getOwnerModID(mods, targ);
                        if (Strings.isNullOrEmpty(amodid)) {
                            SmithsCore.getLogger().warn("Could not determine owning mod for @AutomaticEventBusSubscriber on %s for mod %s", targ.getClassName(), mod.getModId());
                            continue;
                        }
                    }
                    if (!mod.getModId().equals(amodid)) {
                        SmithsCore.getLogger().info("Skipping @AutomaticEventBusSubscriber injection for %s since it is not for mod %s", targ.getClassName(), mod.getModId());
                        continue; //We're not injecting this guy
                    }
                    Class<?> subscriptionTarget = Class.forName(targ.getClassName(), true, mcl);
                    SmithsCore.getRegistry().getNetworkBus().register(subscriptionTarget);
                    SmithsCore.getLogger().info("Injected @AutomaticEventBusSubscriber class %s in the Network Event bus", targ.getClassName());
                }
            } catch (Exception e) {
                SmithsCore.getLogger().error(new Exception(String.format("An error occurred trying to load an AutomaticEventBusSubscriber %s for modid %s", targ.getClassName(), mod.getModId()), e));
                throw new LoaderException(e);
            }
        }
    }
}
