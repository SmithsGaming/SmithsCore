package com.smithsmodding.smithscore.client.model.data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Vector4f;

/**
 * Author Orion (Created on: 17.07.2016)
 * Replacement version of the old OBJ System in MC Forge used until the ModelGroup hiding works.
 */
public class SmithsCoreOBJMaterial {
    public static final String WHITE_NAME = "OBJModel.White.Texture.Name";
    public static final String DEFAULT_NAME = "OBJModel.Default.Texture.Name";
    private Vector4f color;
    private SmithsCoreOBJTexture texture = SmithsCoreOBJTexture.WHITE;
    @Nonnull
    private String name = DEFAULT_NAME;

    public SmithsCoreOBJMaterial() {
        this(new Vector4f(1f, 1f, 1f, 1f));
    }

    public SmithsCoreOBJMaterial(Vector4f color) {
        this(color, SmithsCoreOBJTexture.WHITE, WHITE_NAME);
    }

    public SmithsCoreOBJMaterial(SmithsCoreOBJTexture texture) {
        this(new Vector4f(1f, 1f, 1f, 1f), texture, DEFAULT_NAME);
    }

    public SmithsCoreOBJMaterial(Vector4f color, SmithsCoreOBJTexture texture, @Nullable String name) {
        this.color = color;
        this.texture = texture;
        this.name = name != null ? name : DEFAULT_NAME;
    }

    @Nonnull
    public String getName() {
        return this.name;
    }

    public void setName(@Nullable String name) {
        this.name = name != null ? name : DEFAULT_NAME;
    }

    public Vector4f getColor() {
        return this.color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public SmithsCoreOBJTexture getTexture() {
        return this.texture;
    }

    public void setTexture(SmithsCoreOBJTexture texture) {
        this.texture = texture;
    }

    public boolean isWhite() {
        return this.texture.equals(SmithsCoreOBJTexture.WHITE);
    }

    @Nonnull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(String.format("%nMaterial:%n"));
        builder.append(String.format("    Name: %s%n", this.name));
        builder.append(String.format("    Color: %s%n", this.color.toString()));
        builder.append(String.format("    Is White: %b%n", this.isWhite()));
        return builder.toString();
    }
}
