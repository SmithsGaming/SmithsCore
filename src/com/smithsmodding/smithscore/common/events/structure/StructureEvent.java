package com.smithsmodding.smithscore.common.events.structure;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.events.network.NBTNetworkableEvent;
import com.smithsmodding.smithscore.common.structures.IStructure;
import com.smithsmodding.smithscore.common.structures.IStructureFactory;
import com.smithsmodding.smithscore.common.structures.StructureRegistry;
import com.smithsmodding.smithscore.util.CoreReferences;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public class StructureEvent extends NBTNetworkableEvent {

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
    protected void readSyncCompound(NBTTagCompound compound) {
        dimension = compound.getInteger(CoreReferences.NBT.StructureData.DIMENSION);

        try {
            IStructureFactory factory = StructureRegistry.getInstance().getFactory((Class<? extends IStructure>) Class.forName(compound.getString(CoreReferences.NBT.StructureData.TYPE)));
            structure = factory.loadStructureFromNBT(compound.getCompoundTag(CoreReferences.NBT.StructureData.STRUCTURE));
            structure.setMasterLocation(Coordinate3D.fromNBT(compound.getCompoundTag(CoreReferences.NBT.StructureData.MASTERLOCATION)));
        } catch (ClassNotFoundException e) {
            SmithsCore.getLogger().error(CoreReferences.LogMarkers.STRUCTURE, (Object) new Exception("Cannot retrieve Factory for synced Structure, this is supposed to be impossible!", e));
        }
    }

}
