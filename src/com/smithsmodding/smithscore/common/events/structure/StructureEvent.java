package com.smithsmodding.smithscore.common.events.structure;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.events.network.NBTNetworkableEvent;
import com.smithsmodding.smithscore.common.structures.IStructure;
import com.smithsmodding.smithscore.common.structures.IStructureFactory;
import com.smithsmodding.smithscore.common.structures.StructureRegistry;
import com.smithsmodding.smithscore.util.CoreReferences;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public abstract class StructureEvent extends NBTNetworkableEvent {

    private IStructure structure;
    private Integer dimension;

    public StructureEvent() {
    }

    public StructureEvent(IStructure structure, Integer dimension) {
        this.structure = structure;
        this.dimension = dimension;
    }

    public IStructure getStructure() {
        return structure;
    }

    public Integer getDimension() {
        return dimension;
    }

    @Override
    protected NBTTagCompound getSyncCompound() {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setInteger(CoreReferences.NBT.StructureData.DIMENSION, dimension);
        compound.setString(CoreReferences.NBT.StructureData.TYPE, structure.getClass().getName());
        compound.setTag(CoreReferences.NBT.StructureData.MASTERLOCATION, structure.getMasterLocation().toCompound());
        compound.setTag(CoreReferences.NBT.StructureData.STRUCTURE, StructureRegistry.getInstance().getFactory(structure).generateNBTFromStructure(structure));

        return compound;
    }

    @Override
    protected void readSyncCompound(@Nonnull NBTTagCompound compound) {
        dimension = compound.getInteger(CoreReferences.NBT.StructureData.DIMENSION);

        try {
            IStructureFactory factory = StructureRegistry.getInstance().getFactory((Class<? extends IStructure>) Class.forName(compound.getString(CoreReferences.NBT.StructureData.TYPE)));
            structure = factory.loadStructureFromNBT(compound.getCompoundTag(CoreReferences.NBT.StructureData.STRUCTURE));
            structure.setMasterLocation(Coordinate3D.fromNBT(compound.getCompoundTag(CoreReferences.NBT.StructureData.MASTERLOCATION)));
        } catch (ClassNotFoundException e) {
            SmithsCore.getLogger().error(CoreReferences.LogMarkers.STRUCTURE, (Object) new Exception("Cannot retrieve Factory for synced Structure, this is supposed to be impossible!", e));
        }
    }

    public static class Create extends StructureEvent {
        public Create() {
            super();
        }

        public Create(IStructure structure, Integer dimension) {
            super(structure, dimension);
        }
    }

    public static class Destroyed extends StructureEvent {
        public Destroyed() {
            super();
        }

        public Destroyed(IStructure structure, Integer dimension) {
            super(structure, dimension);
        }
    }

    public static class Updated extends StructureEvent {
        public Updated() {
            super();
        }

        public Updated(IStructure structure, Integer dimension) {
            super(structure, dimension);
        }
    }

    public static class MasterBlockChanged extends StructureEvent {
        private Coordinate3D oldMaster;

        public MasterBlockChanged() {
            super();
        }

        public MasterBlockChanged(IStructure structure, Coordinate3D oldMaster, Integer dimension) {
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
        protected void readSyncCompound(@Nonnull NBTTagCompound compound) {
            super.readSyncCompound(compound);

            oldMaster = Coordinate3D.fromNBT(compound.getCompoundTag(CoreReferences.NBT.StructureData.OLDMASTER));
        }
    }
}
