package com.SmithsModding.SmithsCore.Common.Structures;


import com.SmithsModding.SmithsCore.Common.PathFinding.*;
import com.SmithsModding.SmithsCore.Network.Structure.Messages.*;
import com.SmithsModding.SmithsCore.Network.Structure.*;
import com.SmithsModding.SmithsCore.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.network.*;

import java.util.*;

/**
 * Created by Orion Created on 03.07.2015 12:49
 * <p/>
 * Copyrighted according to Project specific license
 */
public final class StructureManager {

    public static void joinSructure (IStructureComponent pStructureMember, IStructureComponent pNewComponent) {
        if (!( pStructureMember.isSlaved() )) {
            pNewComponent.initiateAsSlaveEntity(pStructureMember);
            StructureNetworkManager.getInstance().sendToAllAround(new MessageOnCreateSlaveEntity(pNewComponent, pStructureMember), new NetworkRegistry.TargetPoint(( (TileEntity) pNewComponent ).getWorld().provider.getDimensionId(), (double) pNewComponent.getLocation().getXComponent(), (double) pNewComponent.getLocation().getYComponent(), (double) pNewComponent.getLocation().getZComponent(), 128));
            try {
                ( pStructureMember ).registerNewSlave((TileEntity) pNewComponent);
                StructureNetworkManager.getInstance().sendToAllAround(new MessageOnUpdateMasterData(pStructureMember), new NetworkRegistry.TargetPoint(( (TileEntity) pStructureMember ).getWorld().provider.getDimensionId(), (double) pStructureMember.getLocation().getXComponent(), (double) pStructureMember.getLocation().getYComponent(), (double) pStructureMember.getLocation().getZComponent(), 128));
            } catch (Exception IAEx) {
                SmithsCore.getLogger().error("Failed to register a TE", IAEx);
            }

            return;
        }

        pNewComponent.initiateAsSlaveEntity(pStructureMember.getMasterEntity());
        StructureNetworkManager.getInstance().sendToAllAround(new MessageOnCreateSlaveEntity(pNewComponent, pStructureMember.getMasterEntity()), new NetworkRegistry.TargetPoint(( (TileEntity) pNewComponent ).getWorld().provider.getDimensionId(), (double) pNewComponent.getLocation().getXComponent(), (double) pNewComponent.getLocation().getYComponent(), (double) pNewComponent.getLocation().getZComponent(), 128));
        try {
            pStructureMember.getMasterEntity().registerNewSlave((TileEntity) pNewComponent);
            StructureNetworkManager.getInstance().sendToAllAround(new MessageOnUpdateMasterData(pStructureMember.getMasterEntity()), new NetworkRegistry.TargetPoint(( (TileEntity) pStructureMember.getMasterEntity() ).getWorld().provider.getDimensionId(), (double) pStructureMember.getMasterEntity().getLocation().getXComponent(), (double) pStructureMember.getMasterEntity().getLocation().getYComponent(), (double) pStructureMember.getMasterEntity().getLocation().getZComponent(), 128));
        } catch (Exception IAEx) {
            SmithsCore.getLogger().error("Failed to register a TE on a remote master", IAEx);
        }

        return;
    }

    public static void mergeStructures (IStructureComponent pNewStructureMaster, IStructureComponent pComponentStructureMaster, IStructureComponent pCombiningStructureComponent) {
        //Add the individual slaves
        for (IStructureComponent tNewSlaveComponent : pComponentStructureMaster.getSlaveEntities().values()) {
            joinSructure(pNewStructureMaster, tNewSlaveComponent);
        }

        //Add the structures Master, destroying the old Structures
        joinSructure(pNewStructureMaster, pComponentStructureMaster);
    }

    public static IStructureComponent splitStructure (IStructureComponent pOldMasterStructure, ArrayList<IStructureComponent> pSplittedComponents) {
        //Create the new Structures master Entity
        IStructureComponent tNewMasterComponent = pSplittedComponents.remove(0);
        tNewMasterComponent.initiateAsMasterEntity();
        StructureNetworkManager.getInstance().sendToAllAround(new MessageOnCreateMasterEntity(tNewMasterComponent), new NetworkRegistry.TargetPoint(( (TileEntity) tNewMasterComponent ).getWorld().provider.getDimensionId(), (double) tNewMasterComponent.getLocation().getXComponent(), (double) tNewMasterComponent.getLocation().getYComponent(), (double) tNewMasterComponent.getLocation().getZComponent(), 128));

        //Let all the Slaves join the new Handlers
        for (IStructureComponent tNewSlave : pSplittedComponents) {
            joinSructure(tNewMasterComponent, tNewSlave);

            pOldMasterStructure.removeSlave(tNewSlave.getLocation());
        }

        StructureNetworkManager.getInstance().sendToAllAround(new MessageOnUpdateMasterData(pOldMasterStructure), new NetworkRegistry.TargetPoint(( (TileEntity) pOldMasterStructure ).getWorld().provider.getDimensionId(), (double) pOldMasterStructure.getLocation().getXComponent(), (double) pOldMasterStructure.getLocation().getYComponent(), (double) pOldMasterStructure.getLocation().getZComponent(), 128));
        StructureNetworkManager.getInstance().sendToAllAround(new MessageOnUpdateMasterData(tNewMasterComponent), new NetworkRegistry.TargetPoint(( (TileEntity) tNewMasterComponent ).getWorld().provider.getDimensionId(), (double) tNewMasterComponent.getLocation().getXComponent(), (double) tNewMasterComponent.getLocation().getYComponent(), (double) tNewMasterComponent.getLocation().getZComponent(), 128));

        return tNewMasterComponent;
    }

    public static void createStructureComponent (IStructureComponent tNewComponent) {
        IStructureComponent tTargetStructure = null;

        tTargetStructure = checkNewComponentSide(tNewComponent.getLocation().moveCoordinate(EnumFacing.EAST, 1), tTargetStructure, tNewComponent);
        tTargetStructure = checkNewComponentSide(tNewComponent.getLocation().moveCoordinate(EnumFacing.WEST, 1), tTargetStructure, tNewComponent);
        tTargetStructure = checkNewComponentSide(tNewComponent.getLocation().moveCoordinate(EnumFacing.SOUTH, 1), tTargetStructure, tNewComponent);
        tTargetStructure = checkNewComponentSide(tNewComponent.getLocation().moveCoordinate(EnumFacing.NORTH, 1), tTargetStructure, tNewComponent);

        if (tTargetStructure == null) {
            tNewComponent.initiateAsMasterEntity();
            StructureNetworkManager.getInstance().sendToAllAround(new MessageOnCreateMasterEntity(tNewComponent), new NetworkRegistry.TargetPoint(( (TileEntity) tNewComponent ).getWorld().provider.getDimensionId(), (double) tNewComponent.getLocation().getXComponent(), (double) tNewComponent.getLocation().getYComponent(), (double) tNewComponent.getLocation().getZComponent(), 128));
        }
    }

    private static IStructureComponent checkNewComponentSide (Coordinate3D pTargetCoordinate, IStructureComponent pTargetStructure, IStructureComponent pNewComponent) {
        TileEntity tEntity = getWorld(pNewComponent).getTileEntity(pTargetCoordinate.toBlockPos());
        if (tEntity == null)
            return pTargetStructure;

        if (!( tEntity instanceof IStructureComponent ))
            return pTargetStructure;

        if (!( (IStructureComponent) tEntity ).getStructureType().equals(pNewComponent.getStructureType()))
            return pTargetStructure;

        if (pTargetStructure == null) {
            joinSructure((IStructureComponent) tEntity, pNewComponent);
            return (IStructureComponent) tEntity;
        }

        if (( (IStructureComponent) tEntity ).getMasterEntity().getLocation().equals(pNewComponent.getMasterEntity().getLocation()))
            return pTargetStructure;

        if (( (IStructureComponent) tEntity ).isSlaved()) {
            mergeStructures(pTargetStructure, ( (IStructureComponent) tEntity ).getMasterEntity(), pNewComponent);
        } else {
            mergeStructures(pTargetStructure, ( (IStructureComponent) tEntity ), pNewComponent);
        }

        return pTargetStructure;
    }

    public static void destroyStructureComponent (IStructureComponent tToBeDestroyedComponent) {
        IStructureComponent tMasterComponent = null;

        if (tToBeDestroyedComponent.isSlaved()) {
            tToBeDestroyedComponent.getMasterEntity().removeSlave(tToBeDestroyedComponent.getLocation());
            StructureNetworkManager.getInstance().sendToAllAround(new MessageOnUpdateMasterData(tToBeDestroyedComponent.getMasterEntity()), new NetworkRegistry.TargetPoint(( (TileEntity) tToBeDestroyedComponent.getMasterEntity() ).getWorld().provider.getDimensionId(), (double) tToBeDestroyedComponent.getMasterEntity().getLocation().getXComponent(), (double) tToBeDestroyedComponent.getMasterEntity().getLocation().getYComponent(), (double) tToBeDestroyedComponent.getMasterEntity().getLocation().getZComponent(), 128));

            tMasterComponent = tToBeDestroyedComponent.getMasterEntity();
        } else {
            if (tToBeDestroyedComponent.getSlaveEntities().size() == 1) {
                tMasterComponent = ( new ArrayList<IStructureComponent>(tToBeDestroyedComponent.getSlaveEntities().values()) ).get(0);
                tMasterComponent.initiateAsMasterEntity();
                StructureNetworkManager.getInstance().sendToAllAround(new MessageOnCreateMasterEntity(tMasterComponent), new NetworkRegistry.TargetPoint(( (TileEntity) tMasterComponent ).getWorld().provider.getDimensionId(), (double) tMasterComponent.getLocation().getXComponent(), (double) tMasterComponent.getLocation().getYComponent(), (double) tMasterComponent.getLocation().getZComponent(), 128));
            } else if (tToBeDestroyedComponent.getSlaveEntities().size() > 1) {
                ArrayList<IStructureComponent> tComponentList = new ArrayList<IStructureComponent>(tToBeDestroyedComponent.getSlaveEntities().values());

                tMasterComponent = tComponentList.get(0);
                tMasterComponent.initiateAsMasterEntity();
                StructureNetworkManager.getInstance().sendToAllAround(new MessageOnCreateMasterEntity(tMasterComponent), new NetworkRegistry.TargetPoint(( (TileEntity) tMasterComponent ).getWorld().provider.getDimensionId(), (double) tMasterComponent.getLocation().getXComponent(), (double) tMasterComponent.getLocation().getYComponent(), (double) tMasterComponent.getLocation().getZComponent(), 128));

                for (int tIndex = 1; tIndex < tComponentList.size(); tIndex++) {
                    joinSructure(tMasterComponent, tComponentList.get(tIndex));
                }
            }
        }

        if (tMasterComponent != null) {
            ArrayList<IStructureComponent> tNotConnectedComponents = validateStructureIntegrity(tMasterComponent, tToBeDestroyedComponent);
            while (!tNotConnectedComponents.isEmpty()) {
                tNotConnectedComponents = validateStructureIntegrity(splitStructure(tMasterComponent, tNotConnectedComponents), tToBeDestroyedComponent);
            }
        }
    }

    public static ArrayList<IStructureComponent> validateStructureIntegrity (IStructureComponent pMasterComponent, IPathComponent pSeperatingComponent) {
        ArrayList<IStructureComponent> tNotConnectedComponents = new ArrayList<IStructureComponent>();

        for (IStructureComponent tSlave : pMasterComponent.getSlaveEntities().values()) {
            if (!checkIfComponentStillConnected(pMasterComponent, tSlave, pSeperatingComponent))
                tNotConnectedComponents.add(tSlave);
        }

        for (IStructureComponent tSlave : tNotConnectedComponents) {
            SmithsCore.getLogger().info("Removing " + tSlave.getLocation().toString() + " from structure.");
            pMasterComponent.getSlaveEntities().remove(tSlave.getLocation());
        }

        return tNotConnectedComponents;
    }

    private static World getWorld (IStructureComponent pComponent) {
        return ( (TileEntity) pComponent ).getWorld();
    }

    private static boolean checkIfComponentStillConnected (IStructureComponent pMasterComponent, IStructureComponent pTargetComponent, IPathComponent pSplittingComponent) {
        SmithsCore.getLogger().info("Starting connection search between: " + pMasterComponent.getLocation().toString() + " to " + pTargetComponent.getLocation().toString());

        PathFinder tConnectionChecker = new PathFinder(pMasterComponent, pTargetComponent, pSplittingComponent);
        return tConnectionChecker.isConnected();
    }


}
