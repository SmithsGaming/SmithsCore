package com.smithsmodding.smithscore.client.block.statemap;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Author Orion (Created on: 11.10.2016)
 */
public class ExtendedStateMap implements IStateMapper {

    private final char[] camelCase;
    private final StateMap map;
    private final String prefix;

    private Map<IBlockState, ModelResourceLocation> cache;

    public ExtendedStateMap(StateMap map, String prefix, char[] camelCase) {
        this.camelCase = camelCase;
        this.map = map;
        this.prefix = prefix;
    }

    @Override
    public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
        if (cache == null) {
            cache = Maps.newLinkedHashMap();
            cache = buildCache(blockIn);
        }

        return cache;
    }

    private Map<IBlockState, ModelResourceLocation> buildCache(@Nonnull Block block) {
        Map<IBlockState, ModelResourceLocation> originalMap = map.putStateModelLocations(block);

        for (Map.Entry<IBlockState, ModelResourceLocation> entry : originalMap.entrySet()) {
            String domain = entry.getValue().getResourceDomain();
            String path = entry.getValue().getResourcePath();
            String variant = entry.getValue().getVariant();

            if (prefix != "") {
                path = prefix + path;
            }

            if (camelCase != null) {
                path = WordUtils.capitalizeFully(path, camelCase);
            }

            ModelResourceLocation value = new ModelResourceLocation(new ResourceLocation(domain, path), variant);
            cache.put(entry.getKey(), value);
        }

        return cache;
    }

    public static class Builder {

        @Nonnull
        private final StateMap.Builder stateBuilder;

        @Nullable
        private char[] camelCase;
        private String preFix;

        public Builder() {
            stateBuilder = new StateMap.Builder();
            camelCase = null;
            preFix = "";
        }

        @Nonnull
        public ExtendedStateMap.Builder withName(@Nonnull IProperty<?> builderProperty) {
            stateBuilder.withName(builderProperty);
            return this;
        }

        @Nonnull
        public ExtendedStateMap.Builder withSuffix(@Nonnull String builderSuffix) {
            stateBuilder.withSuffix(builderSuffix);
            return this;
        }

        @Nonnull
        public ExtendedStateMap.Builder withPrefix(String builderPrefix) {
            this.preFix = builderPrefix;
            return this;
        }

        @Nonnull
        public ExtendedStateMap.Builder withCamelCase(char[] delimeters) {
            this.camelCase = delimeters;
            return this;
        }

        @Nonnull
        public ExtendedStateMap.Builder ignore(IProperty<?>... ignorable) {
            stateBuilder.ignore(ignorable);
            return this;
        }

        @Nullable
        public ExtendedStateMap build() {
            return new ExtendedStateMap(stateBuilder.build(), preFix, camelCase);
        }
    }
}
