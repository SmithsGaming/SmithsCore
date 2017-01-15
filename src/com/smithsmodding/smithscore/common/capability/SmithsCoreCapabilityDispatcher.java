package com.smithsmodding.smithscore.common.capability;

import com.smithsmodding.smithscore.common.events.IEventHandler;
import com.smithsmodding.smithscore.util.CoreReferences;
import com.smithsmodding.smithscore.util.common.capabilities.NullFactory;
import com.smithsmodding.smithscore.util.common.capabilities.NullStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * Created by marcf on 1/9/2017.
 */
public final class SmithsCoreCapabilityDispatcher implements ICapabilitySerializable<NBTTagCompound> {

    @CapabilityInject(IInstanceCap.class)
    public static Capability<IInstanceCap> INSTANCE_CAPABILITY;
    private HashMap<Capability<?>, Object> capInstanceMap = new HashMap<>();

    public SmithsCoreCapabilityDispatcher() {
        this.registerCapability(INSTANCE_CAPABILITY, new IInstanceCap.Impl(this));
    }

    public static void initialize() {
        MinecraftForge.EVENT_BUS.register(RegistrationController.getInstance());
        CapabilityManager.INSTANCE.register(IInstanceCap.class, new NullStorage<>(), new NullFactory<>());
    }

    public static void attach(AttachCapabilitiesEvent event) {
        event.addCapability(new ResourceLocation(CoreReferences.General.MOD_ID.toLowerCase(), CoreReferences.CapabilityManager.DEFAULT), new SmithsCoreCapabilityDispatcher());
    }

    /**
     * Method to register a new Instance of a Capability.
     *
     * @param cap The capability to register it.
     * @param <T> The type of capability to register.
     */
    public <T> void registerNewInstance(@Nonnull Capability<T> cap) {
        this.registerCapability(cap, cap.getDefaultInstance());
    }

    /**
     * Method to register a specific Instance of a Capability.
     *
     * @param cap      The capability to register it.
     * @param instance The instance of the capability to register.
     * @param <T>      The type of the capability to register.
     */
    public <T> void registerCapability(Capability<T> cap, T instance) {
        this.capInstanceMap.put(cap, instance);
    }

    /**
     * Method to remove a Capability from the Dispatcher.
     *
     * @param cap The capability to remove.
     * @param <T> The type of the capability.
     * @return The instance of the capability that was registered if any. Null if none was registered or was stored under that key.
     */
    @Nullable
    public <T> T removeCapability(Capability<T> cap) {
        return (T) capInstanceMap.remove(cap);
    }

    /**
     * Determines if this object has support for the capability in question on the specific side.
     * The return value of this MIGHT change during runtime if this object gains or looses support
     * for a capability.
     * <p>
     * Example:
     * A Pipe getting a cover placed on one side causing it loose the Inventory attachment function for that side.
     * <p>
     * This is a light weight version of getCapability, intended for metadata uses.
     *
     * @param capability The capability to check
     * @param facing     The Side to check from:
     *                   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @return True if this object supports the capability.
     */
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capInstanceMap.containsKey(capability);
    }

    /**
     * Retrieves the handler for the capability requested on the specific side.
     * The return value CAN be null if the object does not support the capability.
     * The return value CAN be the same for multiple faces.
     *
     * @param capability The capability to check
     * @param facing     The Side to check from:
     *                   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @return The requested capability. Returns null when {@link #hasCapability(Capability, EnumFacing)} would return false.
     */
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return (T) capInstanceMap.get(capability);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound managerCompound = new NBTTagCompound();

        capInstanceMap.forEach(new SerializationBiConsumer(managerCompound));

        return managerCompound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        capInstanceMap.forEach(new DeSerializationBiConsumer(nbt));
    }

    public interface IInstanceCap {

        /**
         * Method to get the Dispatcher to register and remove Capabilities.
         *
         * @return The dispatcher.
         */
        SmithsCoreCapabilityDispatcher getDispatcher();

        class Impl implements IInstanceCap {

            private final SmithsCoreCapabilityDispatcher dispatcher;

            public Impl(SmithsCoreCapabilityDispatcher dispatcher) {
                this.dispatcher = dispatcher;
            }

            /**
             * Method to get the Dispatcher to register and remove Capabilities.
             *
             * @return The dispatcher.
             */
            @Override
            public SmithsCoreCapabilityDispatcher getDispatcher() {
                return dispatcher;
            }
        }
    }

    public static class RegistrationController implements IEventHandler<AttachCapabilitiesEvent.Item> {

        private static final RegistrationController INSTANCE = new RegistrationController();

        private RegistrationController() {
        }

        public static RegistrationController getInstance() {
            return INSTANCE;
        }

        @SubscribeEvent
        @Override
        public void handle(AttachCapabilitiesEvent.Item event) {
            SmithsCoreCapabilityDispatcher.attach(event);
        }
    }

    private class SerializationBiConsumer<T> implements BiConsumer<Capability<T>, T> {

        private final NBTTagCompound managerCompound;

        public SerializationBiConsumer(NBTTagCompound managerCompound) {
            this.managerCompound = managerCompound;
        }

        /**
         * Performs this operation on the given arguments.
         *
         * @param tCapability the first input argument
         * @param t           the first input argument
         */
        @Override
        public void accept(Capability<T> tCapability, T t) {
            managerCompound.setTag(tCapability.getName(), tCapability.writeNBT(t, null));
        }
    }

    private class DeSerializationBiConsumer<T> implements BiConsumer<Capability<T>, T> {

        private final NBTTagCompound managerCompound;

        public DeSerializationBiConsumer(NBTTagCompound managerCompound) {
            this.managerCompound = managerCompound;
        }

        /**
         * Performs this operation on the given arguments.
         *
         * @param tCapability the first input argument
         * @param t           the first input argument
         */
        @Override
        public void accept(Capability<T> tCapability, T t) {
            if (managerCompound.hasKey(tCapability.getName())) {
                tCapability.readNBT(t, null, managerCompound.getTag(tCapability.getName()));
            }
        }
    }
}
