package com.smithsmodding.smithscore.client.block.statemap;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

/**
 * Author Orion (Created on: 11.10.2016)
 */
public class PrefixableStateMap implements IStateMapper {

    private final StateMap map;
    private final String prefix;

    private Map<IBlockState, ModelResourceLocation> cache;

    public PrefixableStateMap(StateMap map, String prefix) {
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

    private Map<IBlockState, ModelResourceLocation> buildCache(Block block) {
        Map<IBlockState, ModelResourceLocation> originalMap = map.putStateModelLocations(block);

        for (Map.Entry<IBlockState, ModelResourceLocation> entry : originalMap.entrySet()) {
            ModelResourceLocation value = new ModelResourceLocation(new ResourceLocation(entry.getValue().getResourceDomain(), prefix + entry.getValue().getResourcePath()), entry.getValue().getVariant());
            cache.put(entry.getKey(), value);
        }

        return cache;
    }
}
