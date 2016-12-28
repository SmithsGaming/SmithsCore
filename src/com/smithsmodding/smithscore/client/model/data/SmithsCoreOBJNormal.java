package com.smithsmodding.smithscore.client.model.data;

import javax.annotation.Nonnull;
import javax.vecmath.Vector3f;

/**
 * Author Orion (Created on: 17.07.2016)
 * Replacement version of the old OBJ System in MC Forge used until the ModelGroup hiding works.
 */
public class SmithsCoreOBJNormal {
    public float x, y, z;

    public SmithsCoreOBJNormal() {
        this(0.0f, 0.0f, 0.0f);
    }

    public SmithsCoreOBJNormal(float[] data) {
        this(data[0], data[1], data[2]);
    }

    public SmithsCoreOBJNormal(@Nonnull Vector3f vector3f) {
        this(vector3f.x, vector3f.y, vector3f.z);
    }

    public SmithsCoreOBJNormal(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Nonnull
    public Vector3f getData() {
        return new Vector3f(this.x, this.y, this.z);
    }
}
