package com.smithsmodding.smithscore.common.events;

import static com.smithsmodding.smithscore.common.events.AutomaticEventBusSubscriber.BusType.*;

/**
 * Created by marcf on 1/20/2017.
 */
public @interface AutomaticEventBusSubscriber {

    BusType[] types() default {CLIENT, SERVER, NETWORK};

    /**
     * Optional value, only nessasary if tis annotation is not on the same class that has a @Mod annotation.
     * Needed to prevent early classloading of classes not owned by your mod.
     *
     * @return
     */
    String modid() default "";

    enum BusType {
        CLIENT,
        SERVER,
        NETWORK
    }
}
