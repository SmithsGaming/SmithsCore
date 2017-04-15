package com.smithsmodding.smithscore.common.events.structure;

import com.smithsmodding.smithscore.common.structures.IStructure;
import com.smithsmodding.smithscore.util.CoreReferences;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.nbt.NBTTagCompound;

/**
 * ------------ Class not Documented ------------
 */
public class StructureMasterBlockChangedEvent extends StructureEvent {
    private Coordinate3D oldMaster;

    public StructureMasterBlockChangedEvent() {
        super();
    }

    public StructureMasterBlockChangedEvent(IStructure structure, Coordinate3D oldMaster, Integer dimension) {
        super(structure, dimension);
        this.oldMaster = oldMaster;
    }

    public Coordinate3D getOldMaster() {
        return oldMaster;
    }

    @Override
    protected NBTTagCompound getSyncCompound() {
        NBTTagCompound compound = super.getSyncCompound();
        compound.setTag(CoreReferences.NBT.StructureData.OLDMASTER, oldMaster.toCompound());

        return compound;
    }

    @Override
    protected void readSyncCompound(NBTTagCompound compound) {
        super.readSyncCompound(compound);

        oldMaster = Coordinate3D.fromNBT(compound.getCompoundTag(CoreReferences.NBT.StructureData.OLDMASTER));
    }
}
