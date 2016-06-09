package com.smithsmodding.smithscore.client.model.capability;

import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.concurrent.Callable;

/**
 * @Author Marc (Created on: 04.06.2016)
 */
public class CapabilityComponentModel {

    @CapabilityInject(IComponentModelProvider.class)
    public static Capability<IComponentModelProvider> COMPONENTMODEL_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IComponentModelProvider.class, new Capability.IStorage<IComponentModelProvider>() {
            @Override
            public NBTBase writeNBT(Capability<IComponentModelProvider> capability, IComponentModelProvider instance, EnumFacing side) {
                NBTTagCompound dataCompound = new NBTTagCompound();
                dataCompound.setString(CoreReferences.NBT.Capabilities.COMPONENTMODELTYPE, instance.getModelType());

                return dataCompound;
            }

            @Override
            public void readNBT(Capability<IComponentModelProvider> capability, IComponentModelProvider instance, EnumFacing side, NBTBase nbt) {
                NBTTagCompound dataCompound = (NBTTagCompound) nbt;
                instance.setModelType(dataCompound.getString(CoreReferences.NBT.Capabilities.COMPONENTMODELTYPE));
            }
        }, new Callable<IComponentModelProvider>() {
            @Override
            public IComponentModelProvider call() throws Exception {
                return MissingComponentModelTypeProvider.INSTANCE;
            }
        });
    }

    public static class MissingComponentModelTypeProvider implements IComponentModelProvider {

        public static final MissingComponentModelTypeProvider INSTANCE = new MissingComponentModelTypeProvider();

        private MissingComponentModelTypeProvider() {
        }

        @Override
        public String getModelType() {
            return (new ModelResourceLocation("builtin/missing", "missing")).toString();
        }

        @Override
        public void setModelType(String modelType) {
            return;
        }
    }

    public static class DefaultComponentModelTypeProvider implements IComponentModelProvider {

        private String modelType;

        public DefaultComponentModelTypeProvider() {
            modelType = MissingComponentModelTypeProvider.INSTANCE.getModelType();
        }

        public DefaultComponentModelTypeProvider(String modelType) {
            this.modelType = modelType;
        }

        @Override
        public String getModelType() {
            return modelType;
        }

        @Override
        public void setModelType(String modelType) {
            this.modelType = modelType;
        }
    }

    public static class DefaultComponentModelProviderCapabilityProvider implements ICapabilityProvider {

        private final IComponentModelProvider provider;

        public DefaultComponentModelProviderCapabilityProvider(IComponentModelProvider provider) {
            this.provider = provider;
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return capability == COMPONENTMODEL_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            if (capability == COMPONENTMODEL_CAPABILITY)
                return COMPONENTMODEL_CAPABILITY.cast(provider);

            return null;
        }
    }
}
