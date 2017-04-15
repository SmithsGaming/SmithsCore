package com.smithsmodding.smithscore.common.events.structure;

import com.smithsmodding.smithscore.common.structures.IStructure;

/**
 * ------------ Class not Documented ------------
 */
public class StructureCreateEvent extends StructureEvent {
    public StructureCreateEvent() {
        super();
    }

    public StructureCreateEvent(IStructure structure, Integer dimension) {
        super(structure, dimension);
    }
}
