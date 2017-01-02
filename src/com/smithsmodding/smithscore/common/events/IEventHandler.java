package com.smithsmodding.smithscore.common.events;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Simple Interface declaring an Eventhandler for SC's and Forges event system.
 * <p>
 * Created by marcf on 1/2/2017.
 */
public interface IEventHandler<T extends Event> {

    @SubscribeEvent
    void handle(T event);
}
