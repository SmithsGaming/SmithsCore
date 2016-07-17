package com.smithsmodding.smithscore.client.model.data;

import net.minecraft.util.ResourceLocation;

import javax.vecmath.Vector2f;

/**
 * Author Orion (Created on: 17.07.2016)
 * Replacement version of the old OBJ System in MC Forge used until the ModelGroup hiding works.
 */
public class SmithsCoreOBJTexture {
    public static SmithsCoreOBJTexture WHITE = new SmithsCoreOBJTexture("builtin/white", new Vector2f(0, 0), new Vector2f(1, 1), 0);
    private String path;
    private Vector2f position;
    private Vector2f scale;
    private float rotation;

    public SmithsCoreOBJTexture(String path) {
        this(path, new Vector2f(0, 0), new Vector2f(1, 1), 0);
    }

    public SmithsCoreOBJTexture(String path, Vector2f position, Vector2f scale, float rotation) {
        this.path = path;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    public ResourceLocation getTextureLocation() {
        ResourceLocation loc = new ResourceLocation(this.path);
        return loc;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getScale() {
        return this.scale;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
