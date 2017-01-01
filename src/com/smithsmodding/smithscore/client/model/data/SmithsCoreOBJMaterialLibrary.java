package com.smithsmodding.smithscore.client.model.data;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.smithsmodding.smithscore.SmithsCore;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.vecmath.Vector4f;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Author Orion (Created on: 17.07.2016)
 * Replacement version of the old OBJ System in MC Forge used until the ModelGroup hiding works.
 */
public class SmithsCoreOBJMaterialLibrary {
    private static final Pattern WHITE_SPACE = Pattern.compile("\\s+");
    private Set<String> unknownMaterialCommands = new HashSet<String>();
    @Nonnull
    private Map<String, SmithsCoreOBJMaterial> materials = new HashMap<String, SmithsCoreOBJMaterial>();
    private Map<String, SmithsCoreOBJGroup> groups = new HashMap<String, SmithsCoreOBJGroup>();
    private InputStreamReader mtlStream;
    private BufferedReader mtlReader;

//        private float[] minUVBounds = new float[] {0.0f, 0.0f};
//        private float[] maxUVBounds = new float[] {1.0f, 1.0f};

    public SmithsCoreOBJMaterialLibrary() {
        this.groups.put(SmithsCoreOBJGroup.DEFAULT_NAME, new SmithsCoreOBJGroup(SmithsCoreOBJGroup.DEFAULT_NAME, null));
        SmithsCoreOBJMaterial def = new SmithsCoreOBJMaterial();
        def.setName(SmithsCoreOBJMaterial.DEFAULT_NAME);
        this.materials.put(SmithsCoreOBJMaterial.DEFAULT_NAME, def);
    }

    @Nonnull
    public SmithsCoreOBJMaterialLibrary makeLibWithReplacements(@Nonnull ImmutableMap<String, String> replacements) {
        Map<String, SmithsCoreOBJMaterial> mats = new HashMap<String, SmithsCoreOBJMaterial>();
        for (Map.Entry<String, SmithsCoreOBJMaterial> e : this.materials.entrySet()) {
            // key for the material name, with # added if missing
            String keyMat = e.getKey();
            if (!keyMat.startsWith("#")) keyMat = "#" + keyMat;
            // key for the texture name, with ".png" stripped and # added if missing
            String keyTex = e.getValue().getTexture().getPath();
            if (keyTex.endsWith(".png")) keyTex = keyTex.substring(0, keyTex.length() - ".png".length());
            if (!keyTex.startsWith("#")) keyTex = "#" + keyTex;
            if (replacements.containsKey(keyMat)) {
                SmithsCoreOBJTexture currentTexture = e.getValue().getTexture();
                SmithsCoreOBJTexture replacementTexture = new SmithsCoreOBJTexture(replacements.get(keyMat), currentTexture.getPosition(), currentTexture.getScale(), currentTexture.getRotation());
                SmithsCoreOBJMaterial replacementMaterial = new SmithsCoreOBJMaterial(e.getValue().getColor(), replacementTexture, e.getValue().getName());
                mats.put(e.getKey(), replacementMaterial);
            } else if (replacements.containsKey(keyTex)) {
                SmithsCoreOBJTexture currentTexture = e.getValue().getTexture();
                SmithsCoreOBJTexture replacementTexture = new SmithsCoreOBJTexture(replacements.get(keyTex), currentTexture.getPosition(), currentTexture.getScale(), currentTexture.getRotation());
                SmithsCoreOBJMaterial replacementMaterial = new SmithsCoreOBJMaterial(e.getValue().getColor(), replacementTexture, e.getValue().getName());
                mats.put(e.getKey(), replacementMaterial);
            } else {
                mats.put(e.getKey(), e.getValue());
            }
        }
        SmithsCoreOBJMaterialLibrary ret = new SmithsCoreOBJMaterialLibrary();
        ret.unknownMaterialCommands = this.unknownMaterialCommands;
        ret.materials = mats;
        ret.groups = this.groups;
        ret.mtlStream = this.mtlStream;
        ret.mtlReader = this.mtlReader;
//            ret.minUVBounds = this.minUVBounds;
//            ret.maxUVBounds = this.maxUVBounds;
        return ret;
    }

//        public float[] getMinUVBounds()
//        {
//            return this.minUVBounds;
//        }

//        public float[] getMaxUVBounds()
//        {
//            return this.maxUVBounds;
//        }

//        public void setUVBounds(float minU, float maxU, float minV, float maxV)
//        {
//            this.minUVBounds[0] = minU;
//            this.maxUVBounds[0] = maxU;
//            this.minUVBounds[1] = minV;
//            this.maxUVBounds[1] = maxV;
//        }

    @Nonnull
    public Map<String, SmithsCoreOBJGroup> getGroups() {
        return this.groups;
    }

    @Nonnull
    public Set<String> getUnknownMaterialCommands() {
        return unknownMaterialCommands;
    }

    @Nonnull
    public Map<String, SmithsCoreOBJMaterial> getMaterials() {
        return materials;
    }

    @Nonnull
    public InputStreamReader getMtlStream() {
        return mtlStream;
    }

    @Nonnull
    public BufferedReader getMtlReader() {
        return mtlReader;
    }

    @Nonnull
    public List<SmithsCoreOBJGroup> getGroupsContainingFace(@Nonnull SmithsCoreOBJFace f) {
        List<SmithsCoreOBJGroup> groupList = Lists.newArrayList();
        for (SmithsCoreOBJGroup g : this.groups.values()) {
            if (g.getFaces().contains(f)) groupList.add(g);
        }
        return groupList;
    }

    public void changeMaterialColor(@Nonnull String name, int color) {
        Vector4f colorVec = new Vector4f();
        colorVec.w = (color >> 24 & 255) / 255;
        colorVec.x = (color >> 16 & 255) / 255;
        colorVec.y = (color >> 8 & 255) / 255;
        colorVec.z = (color & 255) / 255;
        this.materials.get(name).setColor(colorVec);
    }

    @Nonnull
    public SmithsCoreOBJMaterial getMaterial(@Nonnull String name) {
        return this.materials.get(name);
    }

    @Nonnull
    public ImmutableList<String> getMaterialNames() {
        return ImmutableList.copyOf(this.materials.keySet());
    }

    public void parseMaterials(@Nonnull IResourceManager manager, @Nonnull String path, @Nonnull ResourceLocation from) throws IOException {
        this.materials.clear();
        boolean hasSetTexture = false;
        boolean hasSetColor = false;
        String domain = from.getResourceDomain();
        if (!path.contains("/"))
            path = from.getResourcePath().substring(0, from.getResourcePath().lastIndexOf("/") + 1) + path;
        mtlStream = new InputStreamReader(manager.getResource(new ResourceLocation(domain, path)).getInputStream(), Charsets.UTF_8);
        mtlReader = new BufferedReader(mtlStream);

        String currentLine = "";
        SmithsCoreOBJMaterial material = new SmithsCoreOBJMaterial();
        material.setName(SmithsCoreOBJMaterial.WHITE_NAME);
        material.setTexture(SmithsCoreOBJTexture.WHITE);
        this.materials.put(SmithsCoreOBJMaterial.WHITE_NAME, material);
        this.materials.put(SmithsCoreOBJMaterial.DEFAULT_NAME, new SmithsCoreOBJMaterial(SmithsCoreOBJTexture.WHITE));

        for (; ; ) {
            currentLine = mtlReader.readLine();
            if (currentLine == null) break;
            currentLine.trim();
            if (currentLine.isEmpty() || currentLine.startsWith("#")) continue;

            String[] fields = WHITE_SPACE.split(currentLine, 2);
            String key = fields[0];
            String data = fields[1];

            if (key.equalsIgnoreCase("newmtl")) {
                hasSetColor = false;
                hasSetTexture = false;
                material = new SmithsCoreOBJMaterial();
                material.setName(data);
                this.materials.put(data, material);
            } else if (key.equalsIgnoreCase("Ka") || key.equalsIgnoreCase("Kd") || key.equalsIgnoreCase("Ks")) {
                if (key.equalsIgnoreCase("Kd") || !hasSetColor) {
                    String[] rgbStrings = WHITE_SPACE.split(data, 3);
                    Vector4f color = new Vector4f(Float.parseFloat(rgbStrings[0]), Float.parseFloat(rgbStrings[1]), Float.parseFloat(rgbStrings[2]), 1.0f);
                    hasSetColor = true;
                    material.setColor(color);
                } else {
                    SmithsCore.getLogger().info("OBJModel: A color has already been defined for material '%s' in '%s'. The color defined by key '%s' will not be applied!", material.getName(), new ResourceLocation(domain, path).toString(), key);
                }
            } else if (key.equalsIgnoreCase("map_Ka") || key.equalsIgnoreCase("map_Kd") || key.equalsIgnoreCase("map_Ks")) {
                if (key.equalsIgnoreCase("map_Kd") || !hasSetTexture) {
                    if (data.contains(" ")) {
                        String[] mapStrings = WHITE_SPACE.split(data);
                        String texturePath = mapStrings[mapStrings.length - 1];
                        SmithsCoreOBJTexture texture = new SmithsCoreOBJTexture(texturePath);
                        hasSetTexture = true;
                        material.setTexture(texture);
                    } else {
                        SmithsCoreOBJTexture texture = new SmithsCoreOBJTexture(data);
                        hasSetTexture = true;
                        material.setTexture(texture);
                    }
                } else {
                    SmithsCore.getLogger().info("OBJModel: A texture has already been defined for material '%s' in '%s'. The texture defined by key '%s' will not be applied!", material.getName(), new ResourceLocation(domain, path).toString(), key);
                }
            } else if (key.equalsIgnoreCase("d") || key.equalsIgnoreCase("Tr")) {
                //d <-optional key here> float[0.0:1.0, 1.0]
                //Tr r g b OR Tr spectral map file OR Tr xyz r g b (CIEXYZ colorspace)
                String[] splitData = WHITE_SPACE.split(data);
                float alpha = Float.parseFloat(splitData[splitData.length - 1]);
                material.getColor().setW(alpha);
            } else {
                if (!unknownMaterialCommands.contains(key)) {
                    unknownMaterialCommands.add(key);
                    SmithsCore.getLogger().info("OBJLoader.MaterialLibrary: key '%s' (model: '%s') is not currently supported, skipping", key, new ResourceLocation(domain, path));
                }
            }
        }
    }
}
