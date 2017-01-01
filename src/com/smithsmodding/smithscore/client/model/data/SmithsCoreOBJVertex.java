package com.smithsmodding.smithscore.client.model.data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

/**
 * Author Orion (Created on: 17.07.2016)
 * Replacement version of the old OBJ System in MC Forge used until the ModelGroup hiding works.
 */
public class SmithsCoreOBJVertex {
    private Vector4f position;
    private SmithsCoreOBJNormal normal;
    private SmithsCoreOBJTextureCoordinate texCoord;
    private SmithsCoreOBJMaterial material = new SmithsCoreOBJMaterial();

    public SmithsCoreOBJVertex(@Nonnull Vector4f position, @Nonnull SmithsCoreOBJMaterial material) {
        this.position = position;
        this.material = material;
    }

    @Nonnull
    public Vector4f getPos() {
        return this.position;
    }

    public void setPos(@Nonnull Vector4f position) {
        this.position = position;
    }

    @Nonnull
    public Vector3f getPos3() {
        return new Vector3f(this.position.x, this.position.y, this.position.z);
    }

    public boolean hasNormal() {
        return this.normal != null;
    }

    @Nullable
    public SmithsCoreOBJNormal getNormal() {
        return this.normal;
    }

    public void setNormal(@Nullable SmithsCoreOBJNormal normal) {
        this.normal = normal;
    }

    public boolean hasTextureCoordinate() {
        return this.texCoord != null;
    }

    @Nonnull
    public SmithsCoreOBJTextureCoordinate getTextureCoordinate() {
        return this.texCoord;
    }

    public void setTextureCoordinate(@Nonnull SmithsCoreOBJTextureCoordinate texCoord) {
        this.texCoord = texCoord;
    }

//        public boolean hasNormalizedUVs()
//        {
//            return this.texCoord.u >= 0.0f && this.texCoord.u <= 1.0f && this.texCoord.v >= 0.0f && this.texCoord.v <= 1.0f;
//        }

    @Nonnull
    public SmithsCoreOBJMaterial getMaterial() {
        return this.material;
    }

    public void setMaterial(@Nonnull SmithsCoreOBJMaterial material) {
        this.material = material;
    }

    @Nonnull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("v:%n"));
        builder.append(String.format("    position: %s %s %s%n", position.x, position.y, position.z));
        builder.append(String.format("    material: %s %s %s %s %s%n", material.getName(), material.getColor().x, material.getColor().y, material.getColor().z, material.getColor().w));
        return builder.toString();
    }
}
