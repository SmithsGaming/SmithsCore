package com.smithsmodding.smithscore.client.model.data;

import javax.annotation.Nonnull;
import javax.vecmath.Vector3f;

/**
 * Author Orion (Created on: 17.07.2016)
 * Replacement version of the old OBJ System in MC Forge used until the ModelGroup hiding works.
 */
public class SmithsCoreOBJTextureCoordinate {
    public float u, v, w;

    public SmithsCoreOBJTextureCoordinate() {
        this(0.0f, 0.0f, 1.0f);
    }

    public SmithsCoreOBJTextureCoordinate(float[] data) {
        this(data[0], data[1], data[2]);
    }

    public SmithsCoreOBJTextureCoordinate(@Nonnull Vector3f data) {
        this(data.x, data.y, data.z);
    }

    public SmithsCoreOBJTextureCoordinate(float u, float v, float w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    @Nonnull
    public static SmithsCoreOBJTextureCoordinate[] getDefaultUVs() {
        SmithsCoreOBJTextureCoordinate[] texCoords = new SmithsCoreOBJTextureCoordinate[4];
        texCoords[0] = new SmithsCoreOBJTextureCoordinate(0.0f, 0.0f, 1.0f);
        texCoords[1] = new SmithsCoreOBJTextureCoordinate(1.0f, 0.0f, 1.0f);
        texCoords[2] = new SmithsCoreOBJTextureCoordinate(1.0f, 1.0f, 1.0f);
        texCoords[3] = new SmithsCoreOBJTextureCoordinate(0.0f, 1.0f, 1.0f);
        return texCoords;
    }

    @Nonnull
    public Vector3f getData() {
        return new Vector3f(this.u, this.v, this.w);
    }
}
