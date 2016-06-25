package com.smithsmodding.smithscore.common.structures;


import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.pathfinding.IPathComponent;
import com.smithsmodding.smithscore.common.pathfinding.PathFinder;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Orion Created on 03.07.2015 12:49
 *
 * Copyrighted according to Project specific license
 */
public final class StructureManager {

    public static void joinSructure(IStructureComponent<?> structureMaster, IStructureComponent<?> newSlave) {
        newSlave.initiateAsSlaveEntity(structureMaster.getMasterLocation());
        try {
            structureMaster.registerNewSlave(newSlave.getLocation());
        } catch (Exception IAEx) {
            SmithsCore.getLogger().error("Failed to register a TE on a remote master", IAEx);
        }

        return;
    }

    public static void mergeStructures(IStructureComponent<?> newStructureMaster, IStructureComponent<?> merginComponentMaster, IStructureComponent<?> combiningComponent) {
        //Add the individual slaves
        for (Coordinate3D slaveCoordinate : merginComponentMaster.getSlaveCoordinates()) {
            IStructureComponent slaveComponent = (IStructureComponent) ( (TileEntity) merginComponentMaster ).getWorld().getTileEntity(slaveCoordinate.toBlockPos());

            joinSructure(newStructureMaster, slaveComponent);
        }

        //Add the structures Master, destroying the old structures
        newStructureMaster.getStructureData().onDataMergeInto(merginComponentMaster.getStructureData());
        joinSructure(newStructureMaster, merginComponentMaster);
    }

    public static IStructureComponent splitStructure(IStructureComponent<?> oldMaster, ArrayList<IStructureComponent<?>> components) {
        //Create the new structures master Entity
        IStructureComponent newMaster = components.remove(0);
        newMaster.initiateAsMasterEntity();

        //Somehow this has no Effect??
        getWorld(newMaster).setTileEntity(newMaster.getLocation().toBlockPos(), (TileEntity) newMaster);

        //Let all the Slaves join the new handlers
        for (IStructureComponent newSlave : components) {
            joinSructure(newMaster, newSlave);

            oldMaster.removeSlave(newSlave.getLocation());
        }

        return newMaster;
    }

    public static void createStructureComponent(IStructureComponent<?> newComponent) {
        IStructureComponent tTargetStructure = null;

        tTargetStructure = checkNewComponentSide(newComponent.getLocation().moveCoordinate(EnumFacing.EAST, 1), tTargetStructure, newComponent);
        tTargetStructure = checkNewComponentSide(newComponent.getLocation().moveCoordinate(EnumFacing.WEST, 1), tTargetStructure, newComponent);
        tTargetStructure = checkNewComponentSide(newComponent.getLocation().moveCoordinate(EnumFacing.SOUTH, 1), tTargetStructure, newComponent);
        tTargetStructure = checkNewComponentSide(newComponent.getLocation().moveCoordinate(EnumFacing.NORTH, 1), tTargetStructure, newComponent);

        if (tTargetStructure == null) {
            newComponent.initiateAsMasterEntity();
        }
    }

    private static IStructureComponent checkNewComponentSide(Coordinate3D target, IStructureComponent<?> structure, IStructureComponent<?> component) {
        TileEntity tEntity = getWorld(component).getTileEntity(target.toBlockPos());
        if (tEntity == null)
            return structure;

        if (!( tEntity instanceof IStructureComponent ))
            return structure;

        if (!((IStructureComponent) tEntity).getStructureTypeUniqueID().equals(component.getStructureTypeUniqueID()))
            return structure;

        if (structure == null) {
            joinSructure((IStructureComponent) tEntity, component);
            return (IStructureComponent) tEntity;
        }

        if (((IStructureComponent) tEntity).getMasterLocation().equals(component.getMasterLocation()))
            return structure;

        if (( (IStructureComponent) tEntity ).isSlaved()) {
            mergeStructures(structure, (IStructureComponent) tEntity.getWorld().getTileEntity(((IStructureComponent) tEntity).getMasterLocation().toBlockPos()), component);
        } else {
            mergeStructures(structure, ((IStructureComponent) tEntity), component);
        }

        return structure;
    }

    public static void destroyStructureComponent(IStructureComponent<?> component) {
        IStructureComponent<?> master = null;

        if (component.isSlaved()) {
            ((IStructureComponent) ((TileEntity) component).getWorld().getTileEntity(component.getMasterLocation().toBlockPos())).removeSlave(component.getLocation());

            master = (IStructureComponent) ((TileEntity) component).getWorld().getTileEntity(component.getMasterLocation().toBlockPos());
        } else {
            if (component.getSlaveCoordinates().size() == 1) {
                master = (IStructureComponent) ((TileEntity) component).getWorld().getTileEntity(component.getSlaveCoordinates().iterator().next().toBlockPos());
                master.initiateAsMasterEntity();
                master.getStructureData().onDataMergeInto(component.getStructureData());
            } else if (component.getSlaveCoordinates().size() > 1) {
                Iterator<Coordinate3D> iterator = component.getSlaveCoordinates().iterator();

                master = (IStructureComponent) ((TileEntity) component).getWorld().getTileEntity(iterator.next().toBlockPos());
                master.initiateAsMasterEntity();
                master.getStructureData().onDataMergeInto(component.getStructureData());

                while (iterator.hasNext()) {
                    joinSructure(master, (IStructureComponent) ((TileEntity) component).getWorld().getTileEntity(iterator.next().toBlockPos()));
                }
            }
        }

        if (master != null) {
            ArrayList<IStructureComponent<?>> notConnectedComponents = validateStructureIntegrity(master, component);
            while (!notConnectedComponents.isEmpty()) {
                notConnectedComponents = validateStructureIntegrity(splitStructure(master, notConnectedComponents), component);
            }
        }
    }

    public static ArrayList<IStructureComponent<?>> validateStructureIntegrity(IStructureComponent<?> master, IPathComponent splitter) {
        ArrayList<IStructureComponent<?>> notConnectedComponents = new ArrayList<>();

        for (Coordinate3D slaveCoordinate : master.getSlaveCoordinates()) {
            IStructureComponent slaveComponent = (IStructureComponent) ((TileEntity) master).getWorld().getTileEntity(slaveCoordinate.toBlockPos());

            if (!checkIfComponentStillConnected(master, slaveComponent, splitter))
                notConnectedComponents.add(slaveComponent);
        }

        for (IStructureComponent tSlave : notConnectedComponents) {
            SmithsCore.getLogger().info("Removing " + tSlave.getLocation().toString() + " from structure.");
            master.removeSlave(tSlave.getLocation());
        }

        return notConnectedComponents;
    }

    private static World getWorld(IStructureComponent<?> component) {
        return ((TileEntity) component).getWorld();
    }

    private static boolean checkIfComponentStillConnected(IStructureComponent<?> master, IStructureComponent<?> target, IPathComponent splitter) {
        SmithsCore.getLogger().info("Starting connection search between: " + master.getLocation().toString() + " to " + target.getLocation().toString());

        PathFinder tConnectionChecker = new PathFinder(master, target, splitter);
        return tConnectionChecker.isConnected();
    }


}
