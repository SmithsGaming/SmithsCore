package com.smithsmodding.smithscore.common.structures;

import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.events.structure.StructureEvent;
import com.smithsmodding.smithscore.util.CoreReferences;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public final class StructureRegistry {

    private static final StructureRegistry instance = new StructureRegistry();

    private final LinkedHashMap<Class<? extends IStructure>, IStructureFactory> factories = new LinkedHashMap<>();
    private final LinkedHashMap<Integer, LinkedHashMap<Coordinate3D, IStructure>> structures = new LinkedHashMap<>();

    private StructureRegistry() {
    }

    public static StructureRegistry getInstance() {
        return instance;
    }

    public void registerStructureFactory(IStructureFactory factory) {
        factories.put(factory.getStructureType(), factory);
    }

    public IStructureFactory getFactory(Class<? extends IStructure> clazz) {
        return factories.get(clazz);
    }

    public IStructureFactory getFactory(IStructure structure) {
        return getFactory(structure.getClass());
    }

    public IStructure getStructure(Integer dimension, Coordinate3D masterLocation) {
        if (!structures.containsKey(dimension))
            return null;

        return structures.get(dimension).get(masterLocation);
    }

    public void onStructurePartPlaced(IStructurePart part) {
        IStructureFactory factory = getFactory(part.getStructureType());
        IStructure newInitialStructure = factory.generateNewStructure(part);

        new StructureEvent.Create(newInitialStructure, part.getWorld().provider.getDimension()).PostCommon();

        part.setStructure(newInitialStructure);
        part.getStructure().getController().onPartPlaced(part);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        File structureDirectory = new File(FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getSaveHandler().getWorldDirectory(), "armory/structures");

        structures.clear();

        if (!structureDirectory.exists())
            return;

        if (structureDirectory.isDirectory()) {
            for (File search : structureDirectory.listFiles()) {
                if (search.getName().contains(".dat")) {
                    try {
                        if (SmithsCore.isInDevenvironment())
                            SmithsCore.getLogger().info("Loading structures from: " + search.getName());

                        FileInputStream inputStream = new FileInputStream(search);
                        NBTTagCompound compound = CompressedStreamTools.readCompressed(inputStream);

                        NBTTagList structuresList = compound.getTagList(CoreReferences.NBT.StructureData.STORE, Constants.NBT.TAG_COMPOUND);
                        Integer dimensionId = compound.getInteger(CoreReferences.NBT.StructureData.DIMENSION);

                        for (int i = 0; i < structuresList.tagCount(); i++) {
                            NBTTagCompound structureCompound = structuresList.getCompoundTagAt(i);
                            Class<? extends IStructure> structureClass = (Class<? extends IStructure>) Class.forName(structureCompound.getString(CoreReferences.NBT.StructureData.TYPE));
                            if (!factories.containsKey(structureClass)) {
                                SmithsCore.getLogger().warn(CoreReferences.LogMarkers.STRUCTURE, "Found a structure of type: " + structureClass.getName() + " in file: " + search.getName() + " that has no associated factory, it will not persist!");
                                continue;
                            }

                            IStructureFactory factory = factories.get(structureClass);
                            IStructure structure = factory.loadStructureFromNBT(structureCompound.getCompoundTag(CoreReferences.NBT.StructureData.STRUCTURE));
                            structure.setMasterLocation(Coordinate3D.fromNBT(structureCompound.getCompoundTag(CoreReferences.NBT.StructureData.MASTERLOCATION)));

                            if (!structures.containsKey(dimensionId))
                                structures.put(dimensionId, new LinkedHashMap<>());

                            structures.get(dimensionId).put(structure.getMasterLocation(), structure);
                        }
                    } catch (Exception ex) {
                        SmithsCore.getLogger().error(CoreReferences.LogMarkers.STRUCTURE, (Object) new Exception("Failed to load the structures data from Disk! They will not persist!", ex));
                    }
                }
            }
        }


    }

    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event) {
        File structureDirectory = new File(FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getSaveHandler().getWorldDirectory(), "armory/structures");

        try {
            if (!structureDirectory.exists())
                structureDirectory.mkdirs();

            for (File file : structureDirectory.listFiles())
                file.delete();

            for (Integer dimensionId : structures.keySet()) {
                LinkedHashMap<Coordinate3D, IStructure> structuresInDimension = structures.get(dimensionId);

                NBTTagCompound dimensionCompound = new NBTTagCompound();
                dimensionCompound.setInteger(CoreReferences.NBT.StructureData.DIMENSION, dimensionId);
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

                File dimensionFile = new File(structureDirectory, "dim_" + dimensionId + ".dat");
                dimensionFile.createNewFile();

                FileOutputStream outputStream = new FileOutputStream(dimensionFile);
                CompressedStreamTools.writeCompressed(dimensionCompound, outputStream);
            }
        } catch (Exception ex) {
            SmithsCore.getLogger().error(CoreReferences.LogMarkers.STRUCTURE, (Object) new Exception("Failed to write the structure data to Disk! It will not persist!", ex));
        }
    }

    @SubscribeEvent
    public void onStructureCreation(StructureEvent.Create event) {
        if (!structures.containsKey(event.getDimension()))
            structures.put(event.getDimension(), new LinkedHashMap<>());

        structures.get(event.getDimension()).put(event.getStructure().getMasterLocation(), event.getStructure());

        /*if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
            return;

        World world = FMLClientHandler.instance().getWorldClient();

        for(Object obj : event.getStructure().getPartLocations()) {
            Coordinate3D location = (Coordinate3D) obj;
            IStructurePart part = (IStructurePart) world.getTileEntity(location.toBlockPos());
            part.setStructure(event.getStructure());
        }*/
    }

    @SubscribeEvent
    public void onStructureDestruction(StructureEvent.Destroyed event) {
        if (!structures.containsKey(event.getDimension()))
            return;

        if (!structures.get(event.getDimension()).containsKey(event.getStructure().getMasterLocation()))
            return;

        structures.get(event.getDimension()).remove(event.getStructure().getMasterLocation());

        if (structures.get(event.getDimension()).size() == 0)
            structures.remove(event.getDimension());
    }

    @SubscribeEvent
    public void onStructureMasterUpdated(StructureEvent.MasterBlockChanged event) {
        if (!structures.containsKey(event.getDimension()))
            return;

        if (!structures.get(event.getDimension()).containsKey(event.getOldMaster()))
            return;

        structures.get(event.getDimension()).remove(event.getOldMaster());
        structures.get(event.getDimension()).put(event.getStructure().getMasterLocation(), event.getStructure());
    }

    @SubscribeEvent
    public void onStructureUpdated(StructureEvent.Updated event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
            return;

        if (!structures.containsKey(event.getDimension()))
            return;

        if (!structures.get(event.getDimension()).containsKey(event.getStructure().getMasterLocation()))
            return;

        structures.get(event.getDimension()).remove(event.getStructure().getMasterLocation());
        structures.get(event.getDimension()).put(event.getStructure().getMasterLocation(), event.getStructure());

        /*World world = FMLClientHandler.instance().getWorldClient();

        for(Object obj : event.getStructure().getPartLocations()) {
            Coordinate3D location = (Coordinate3D) obj;
            IStructurePart part = (IStructurePart) world.getTileEntity(location.toBlockPos());
            part.setStructure(event.getStructure());
            IStructurePart part1 = (IStructurePart) world.getTileEntity(location.toBlockPos());

            assert part.getStructure().getMasterLocation().equals(part1.getStructure().getMasterLocation());
        }*/
    }
}
