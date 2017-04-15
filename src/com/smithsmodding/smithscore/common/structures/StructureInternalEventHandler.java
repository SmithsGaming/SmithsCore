package com.smithsmodding.smithscore.common.structures;

import com.smithsmodding.smithscore.common.events.structure.StructureCreateEvent;
import com.smithsmodding.smithscore.common.events.structure.StructureDestroyedEvent;
import com.smithsmodding.smithscore.common.events.structure.StructureMasterBlockChangedEvent;
import com.smithsmodding.smithscore.common.events.structure.StructureUpdatedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedHashMap;

/**
 * ------------ Class not Documented ------------
 */
public class StructureInternalEventHandler {

    private final StructureRegistry registry;

    public StructureInternalEventHandler(StructureRegistry registry) {
        this.registry = registry;
    }

    @SubscribeEvent
    public void onStructureCreation(StructureCreateEvent event) {
        synchronized (registry.structures) {
            if (!registry.structures.containsKey(event.getDimension()))
                registry.structures.put(event.getDimension(), new LinkedHashMap<>());

            registry.structures.get(event.getDimension()).put(event.getStructure().getMasterLocation(), event.getStructure());
        }
    }

    @SubscribeEvent
    public void onStructureDestruction(StructureDestroyedEvent event) {
        synchronized (registry.structures) {
            if (!registry.structures.containsKey(event.getDimension()))
                return;

            if (!registry.structures.get(event.getDimension()).containsKey(event.getStructure().getMasterLocation()))
                return;

            registry.structures.get(event.getDimension()).remove(event.getStructure().getMasterLocation());

            if (registry.structures.get(event.getDimension()).size() == 0)
                registry.structures.remove(event.getDimension());
        }
    }

    @SubscribeEvent
    public void onStructureMasterUpdated(StructureMasterBlockChangedEvent event) {
        synchronized (registry.structures) {
            if (!registry.structures.containsKey(event.getDimension()))
                return;

            if (!registry.structures.get(event.getDimension()).containsKey(event.getOldMaster()))
                return;

            registry.structures.get(event.getDimension()).remove(event.getOldMaster());
            registry.structures.get(event.getDimension()).put(event.getStructure().getMasterLocation(), event.getStructure());
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onStructureUpdated(StructureUpdatedEvent event) {
        synchronized (registry.structures) {
            if (!registry.structures.containsKey(event.getDimension()))
                return;

            if (!registry.structures.get(event.getDimension()).containsKey(event.getStructure().getMasterLocation()))
                return;

            registry.structures.get(event.getDimension()).remove(event.getStructure().getMasterLocation());
            registry.structures.get(event.getDimension()).put(event.getStructure().getMasterLocation(), event.getStructure());
        }
    }
}
