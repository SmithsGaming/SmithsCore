package com.smithsmodding.smithscore.common.events;

import com.google.common.collect.BiMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * General event for registry manipulation.
 *
 * @param <V> The type of the registry
 */
public abstract class SmithsCoreRegistryEvent<V extends IForgeRegistryEntry<V>> extends SmithsCoreEvent {

    @Nonnull
    private final Map<ResourceLocation, ?> slaveset;

    SmithsCoreRegistryEvent(@Nonnull Map<ResourceLocation, ?> slaveset) {
        this.slaveset = slaveset;
    }

    /**
     * The slaveset of the registry that is being manipulated.
     *
     * @return the slaveset of the registry that is being manipulated.
     */
    @Nonnull
    public Map<ResourceLocation, ?> getSlaveset() {
        return slaveset;
    }

    /**
     * Event fired when an Object is added to one of the registries.
     *
     * @param <V> The type of the registry
     */
    public static final class Add<V extends IForgeRegistryEntry<V>> extends SmithsCoreRegistryEvent<V> {
        @Nonnull
        private final V objectAdded;
        @Nonnull
        private final int id;

        public Add(@Nonnull V objectAdded, @Nonnull int id, @Nonnull Map<ResourceLocation, ?> slaveset) {
            super(slaveset);
            this.objectAdded = objectAdded;
            this.id = id;
        }

        /**
         * Getter for the object that has been added to the registry.
         *
         * @return The object that has been added to the registry.
         */
        @Nonnull
        public V getObjectAdded() {
            return objectAdded;
        }

        /**
         * The id of the newly registered object.
         *
         * @return the ic of the newly registered object.
         */
        @Nonnull
        public int getId() {
            return id;
        }

    }

    /**
     * Event fired when a registry is cleared.
     * For example during server to client sync.
     *
     * @param <V> The type of the registry.
     */
    public static final class Clear<V extends IForgeRegistryEntry<V>> extends SmithsCoreRegistryEvent<V> {

        @Nonnull
        private final IForgeRegistry<V> registry;

        public Clear(@Nonnull Map<ResourceLocation, ?> slaveset, @Nonnull IForgeRegistry<V> registry) {
            super(slaveset);
            this.registry = registry;
        }

        /**
         * A getter for the registry that is being cleared.
         *
         * @return The registry that is being cleared.
         */
        @Nonnull
        public IForgeRegistry<V> getRegistry() {
            return registry;
        }
    }

    /**
     * Event fired when a new Registry is created.
     *
     * @param <V> The type of the registry.
     */
    public static final class Create<V extends IForgeRegistryEntry<V>> extends SmithsCoreRegistryEvent<V> {

        @Nonnull
        private final BiMap<ResourceLocation, ? extends IForgeRegistry<?>> registries;

        public Create(@Nonnull Map<ResourceLocation, ?> slaveset, @Nonnull BiMap<ResourceLocation, ? extends IForgeRegistry<?>> registries) {
            super(slaveset);
            this.registries = registries;
        }

        /**
         * A getter for all registered registries.
         *
         * @return A BiMap containing all the registries that are registered and their names.
         */
        @Nonnull
        public BiMap<ResourceLocation, ? extends IForgeRegistry<?>> getRegistries() {
            return registries;
        }
    }

    /**
     * Event fired when a substitution happens in one the registries.
     *
     * @param <V> The type of the registry in which the substitution happens.
     */
    public static final class Substitute<V extends IForgeRegistryEntry<V>> extends SmithsCoreRegistryEvent<V> {
        @Nonnull
        private final V original;
        @Nonnull
        private final V replacement;
        @Nonnull
        private final ResourceLocation name;

        public Substitute(@Nonnull Map<ResourceLocation, ?> slaveset, @Nonnull V original, @Nonnull V replacement, @Nonnull ResourceLocation name) {
            super(slaveset);
            this.original = original;
            this.replacement = replacement;
            this.name = name;
        }

        /**
         * A getter for the originally registered object.
         *
         * @return the originally registered object.
         */
        @Nonnull
        public V getOriginal() {
            return original;
        }

        /**
         * A getter for the replacement object.
         *
         * @return the replacement object.
         */
        @Nonnull
        public V getReplacement() {
            return replacement;
        }

        /**
         * Their unique name.
         *
         * @return the unique name.
         */
        @Nonnull
        public ResourceLocation getName() {
            return name;
        }
    }
}
