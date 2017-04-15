package com.smithsmodding.smithscore.common.events.structure;

import com.smithsmodding.smithscore.common.structures.IStructure;

/**
 * ------------ Class not Documented ------------
 */
public class StructureDestroyedEvent extends StructureEvent {
    public StructureDestroyedEvent() {
        super();
    }

    public StructureDestroyedEvent(IStructure structure, Integer dimension) {
        super(structure, dimension);
    }
}
