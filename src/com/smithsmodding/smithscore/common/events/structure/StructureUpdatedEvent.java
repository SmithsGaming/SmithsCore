package com.smithsmodding.smithscore.common.events.structure;

import com.smithsmodding.smithscore.common.structures.IStructure;

/**
 * ------------ Class not Documented ------------
 */
public class StructureUpdatedEvent extends StructureEvent {
    public StructureUpdatedEvent() {
        super();
    }

    public StructureUpdatedEvent(IStructure structure, Integer dimension) {
        super(structure, dimension);
    }
}
