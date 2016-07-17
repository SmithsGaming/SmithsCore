package com.smithsmodding.smithscore.client.properties;

import com.smithsmodding.smithscore.client.model.states.SmithsCoreOBJState;
import net.minecraftforge.common.property.IUnlistedProperty;

/**
 * Author Orion (Created on: 17.07.2016)
 * Replacement property for the old OBJProperty in MC Forge used until the ModelGroup hiding works.
 */
public enum SmithsCoreOBJProperty implements IUnlistedProperty<SmithsCoreOBJState> {
    INSTANCE;

    public String getName() {
        return "OBJProperty";
    }

    @Override
    public boolean isValid(SmithsCoreOBJState value) {
        return value instanceof SmithsCoreOBJState;
    }

    @Override
    public Class<SmithsCoreOBJState> getType() {
        return SmithsCoreOBJState.class;
    }

    @Override
    public String valueToString(SmithsCoreOBJState value) {
        return value.toString();
    }
}
