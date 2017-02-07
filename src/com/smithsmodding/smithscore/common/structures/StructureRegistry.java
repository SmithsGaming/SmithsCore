package com.smithsmodding.smithscore.common.structures;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.events.structure.StructureEvent;
import com.smithsmodding.smithscore.util.CoreReferences;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public final class StructureRegistry {

    private static final StructureRegistry clientInstance = new StructureRegistry();
    private static final StructureRegistry serverInstance = new StructureRegistry();

    private final LinkedHashMap<Class<? extends IStructure>, IStructureFactory> factories = new LinkedHashMap<>();
    private final LinkedHashMap<Integer, LinkedHashMap<Coordinate3D, IStructure>> structures = new LinkedHashMap<>();

    private StructureRegistry() {
    }

    @Nonnull
    public static StructureRegistry getInstance() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            return clientInstance;

        return serverInstance;
    }

    @Nonnull
    public static StructureRegistry getClientInstance() {
        return clientInstance;
    }

    @Nonnull
    public static StructureRegistry getServerInstance() {
        return serverInstance;
    }

    public void registerStructureFactory(@Nonnull IStructureFactory factory) {
        factories.put(factory.getStructureType(), factory);
    }

    public IStructureFactory getFactory(@Nonnull Class<? extends IStructure> clazz) {
        return factories.get(clazz);
    }

    public IStructureFactory getFactory(@Nonnull IStructure structure) {
        return getFactory(structure.getClass());
    }

    public IStructure getStructure(@Nonnull Integer dimension, @Nonnull Coordinate3D masterLocation) {
        synchronized (structures) {
            if (!structures.containsKey(dimension))
                return null;

            return structures.get(dimension).get(masterLocation);
        }
    }

    public void onStructurePartPlaced(@Nonnull IStructurePart part) {
        IStructureFactory factory = getFactory(part.getStructureType());
        IStructure newInitialStructure = factory.generateNewStructure(part);

        new StructureEvent.Create(newInitialStructure, part.getEnvironment().provider.getDimension()).PostCommon();

        part.setStructure(newInitialStructure);
        part.getStructure().getController().onPartPlaced(part);
    }

    @SubscribeEvent
    public void onWorldLoad(@Nonnull WorldEvent.Load event) {
        int dimensionId = event.getWorld().provider.getDimension();
        File dimensionFile = new File(FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getSaveHandler().getWorldDirectory(), "armory/structures/dim_" + dimensionId + ".dat");

        if (structures.containsKey(dimensionId))
            structures.get(dimensionId).clear();

        if (!dimensionFile.exists())
            return;

        if (!structures.containsKey(dimensionId))
            structures.put(dimensionId, new LinkedHashMap<>());

        try {
            if (SmithsCore.isInDevenvironment())
                SmithsCore.getLogger().info("Loading structures from: " + dimensionFile.getName());

            FileInputStream inputStream = new FileInputStream(dimensionFile);
            NBTTagCompound compound = CompressedStreamTools.readCompressed(inputStream);

            NBTTagList structuresList = compound.getTagList(CoreReferences.NBT.StructureData.STORE, Constants.NBT.TAG_COMPOUND);

            for (int i = 0; i < structuresList.tagCount(); i++) {
                NBTTagCompound structureCompound = structuresList.getCompoundTagAt(i);
                Class<? extends IStructure> structureClass = (Class<? extends IStructure>) Class.forName(structureCompound.getString(CoreReferences.NBT.StructureData.TYPE));
                if (!factories.containsKey(structureClass)) {
                    SmithsCore.getLogger().warn(CoreReferences.LogMarkers.STRUCTURE, "Found a structure of type: " + structureClass.getName() + " in file: " + dimensionFile.getName() + " that has no associated factory, it will not persist!");
                    continue;
                }

                IStructureFactory factory = factories.get(structureClass);
                IStructure structure = factory.loadStructureFromNBT(structureCompound.getCompoundTag(CoreReferences.NBT.StructureData.STRUCTURE));
                structure.setMasterLocation(Coordinate3D.fromNBT(structureCompound.getCompoundTag(CoreReferences.NBT.StructureData.MASTERLOCATION)));

                structures.get(dimensionId).put(structure.getMasterLocation(), structure);
            }
        } catch (Exception ex) {
            SmithsCore.getLogger().error(CoreReferences.LogMarkers.STRUCTURE, (Object) new Exception("Failed to load the structures data from Disk! They will not persist!", ex));
        }
    }

    @SubscribeEvent
    public void onWorldSave(@Nonnull WorldEvent.Save event) {
        saveStructureDataForWorld(event.getWorld().provider.getDimension());
    }

    private void saveStructureDataForWorld(int dimensionId) {
        File dimensionFile = new File(FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getSaveHandler().getWorldDirectory(), "armory/structures/dim_" + dimensionId + ".dat");

        try {
            if (!dimensionFile.exists())
                dimensionFile.mkdirs();

            if (dimensionFile.exists())
                dimensionFile.delete();

            if (!structures.containsKey(dimensionId))
                return;

            NBTTagCompound dimensionCompound = new NBTTagCompound();
            NBTTagList structureList = new NBTTagList();

            for (Coordinate3D masterCoordinate : structures.get(dimensionId).keySet()) {
                NBTTagCompound structureCompound = new NBTTagCompound();
                structureCompound.setTag(CoreReferences.NBT.StructureData.MASTERLOCATION, masterCoordinate.toCompound());

                IStructure structure = structures.get(dimensionId).get(masterCoordinate);
                structureCompound.setString(CoreReferences.NBT.StructureData.TYPE, structure.getClass().getName());

                IStructureFactory factory = factories.get(structure.getClass());
                structureCompound.setTag(CoreReferences.NBT.StructureData.STRUCTURE, factory.generateNBTFromStructure(structure));

                structureList.appendTag(structureCompound);
            }

            dimensionCompound.setTag(CoreReferences.NBT.StructureData.STORE, structureList);

            dimensionFile.createNewFile();

            FileOutputStream outputStream = new FileOutputStream(dimensionFile);
            CompressedStreamTools.writeCompressed(dimensionCompound, outputStream);
        } catch (Exception ex) {
            SmithsCore.getLogger().error(CoreReferences.LogMarkers.STRUCTURE, (Object) new Exception("Failed to write the structure data to Disk! It will not persist!", ex));
        }
    }

    private void deleteStructureDataOnDisk(int dimensionId) {
        File dimensionFile = new File(FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getSaveHandler().getWorldDirectory(), "armory/structures/dim_" + dimensionId + ".dat");

        try {
            if (!dimensionFile.exists())
                return;

            dimensionFile.delete();
        } catch (Exception ex) {
            SmithsCore.getLogger().error(CoreReferences.LogMarkers.STRUCTURE, (Object) new Exception("Failed to delete the structure data to Disk! It will still persist!", ex));
        }
    }

    @SubscribeEvent
    public void onPlayerJoinServer(@Nonnull PlayerEvent.PlayerLoggedInEvent event) {
        for (Map.Entry<Integer, LinkedHashMap<Coordinate3D, IStructure>> dimensionEntry : structures.entrySet()) {
            for (IStructure structure : dimensionEntry.getValue().values()) {
                new StructureEvent.Create(structure, dimensionEntry.getKey()).handleServerToClient((EntityPlayerMP) event.player);
            }
        }
    }

    @SubscribeEvent
    public void onStructureCreation(@Nonnull StructureEvent.Create event) {
        synchronized (structures) {
            if (!structures.containsKey(event.getDimension()))
                structures.put(event.getDimension(), new LinkedHashMap<>());

            structures.get(event.getDimension()).put(event.getStructure().getMasterLocation(), event.getStructure());

            saveStructureDataForWorld(event.getDimension());
        }
    }

    @SubscribeEvent
    public void onStructureDestruction(@Nonnull StructureEvent.Destroyed event) {
        synchronized (structures) {
            if (!structures.containsKey(event.getDimension()))
                return;

            if (!structures.get(event.getDimension()).containsKey(event.getStructure().getMasterLocation()))
                return;

            structures.get(event.getDimension()).remove(event.getStructure().getMasterLocation());

            if (structures.get(event.getDimension()).size() == 0) {
                structures.remove(event.getDimension());
                deleteStructureDataOnDisk(event.getDimension());
                return;
            }

            saveStructureDataForWorld(event.getDimension());
        }
    }

    @SubscribeEvent
    public void onStructureMasterUpdated(@Nonnull StructureEvent.MasterBlockChanged event) {
        synchronized (structures) {
            if (!structures.containsKey(event.getDimension()))
                return;

            if (!structures.get(event.getDimension()).containsKey(event.getOldMaster()))
                return;

            structures.get(event.getDimension()).remove(event.getOldMaster());
            structures.get(event.getDimension()).put(event.getStructure().getMasterLocation(), event.getStructure());

            saveStructureDataForWorld(event.getDimension());
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onStructureUpdated(@Nonnull StructureEvent.Updated event) {
        synchronized (structures) {
            if (!structures.containsKey(event.getDimension()))
                return;

            if (!structures.get(event.getDimension()).containsKey(event.getStructure().getMasterLocation()))
                return;

            structures.get(event.getDimension()).remove(event.getStructure().getMasterLocation());
            structures.get(event.getDimension()).put(event.getStructure().getMasterLocation(), event.getStructure());

            saveStructureDataForWorld(event.getDimension());
        }
    }


}
