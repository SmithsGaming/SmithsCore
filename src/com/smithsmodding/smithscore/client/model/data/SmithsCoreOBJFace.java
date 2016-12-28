package com.smithsmodding.smithscore.client.model.data;

import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

/**
 * Author Orion (Created on: 17.07.2016)
 * Replacement version of the old OBJ System in MC Forge used until the ModelGroup hiding works.
 */
public class SmithsCoreOBJFace {
    @Nullable
    private SmithsCoreOBJVertex[] verts = new SmithsCoreOBJVertex[4];
    //        private Normal[] norms = new Normal[4];
//        private TextureCoordinate[] texCoords = new TextureCoordinate[4];
    @Nullable
    private String materialName = SmithsCoreOBJMaterial.DEFAULT_NAME;
    private boolean isTri = false;

    public SmithsCoreOBJFace(SmithsCoreOBJVertex[] verts) {
        this(verts, SmithsCoreOBJMaterial.DEFAULT_NAME);
    }

    public SmithsCoreOBJFace(@Nullable SmithsCoreOBJVertex[] verts, String materialName) {
        this.verts = verts != null && verts.length > 2 ? verts : null;
        setMaterialName(materialName);
        checkData();
    }

//        public Face(Vertex[] verts, Normal[] norms)
//        {
//            this(verts, norms, null);
//        }

//        public Face(Vertex[] verts, TextureCoordinate[] texCoords)
//        {
//            this(verts, null, texCoords);
//        }

//        public Face(Vertex[] verts, Normal[] norms, TextureCoordinate[] texCoords)
//        {
//            this(verts, norms, texCoords, Material.DEFAULT_NAME);
//        }

//        public Face(Vertex[] verts, Normal[] norms, TextureCoordinate[] texCoords, String materialName)
//        {
//            this.verts = verts != null && verts.length > 2 ? verts : null;
//            this.norms = norms != null && norms.length > 2 ? norms : null;
//            this.texCoords = texCoords != null && texCoords.length > 2 ? texCoords : null;
//            setMaterialName(materialName);
//            checkData();
//        }

    private void checkData() {
        if (this.verts != null && this.verts.length == 3) {
            this.isTri = true;
            this.verts = new SmithsCoreOBJVertex[]{this.verts[0], this.verts[1], this.verts[2], this.verts[2]};
        }
    }

    @Nullable
    public String getMaterialName() {
        return this.materialName;
    }

    public void setMaterialName(@Nullable String materialName) {
        this.materialName = materialName != null && !materialName.isEmpty() ? materialName : this.materialName;
    }

    public boolean isTriangles() {
        return isTri;
    }

    public boolean setVertices(@Nullable SmithsCoreOBJVertex[] verts) {
        if (verts == null) return false;
        else this.verts = verts;
        checkData();
        return true;
    }

    @Nullable
    public SmithsCoreOBJVertex[] getVertices() {
        return this.verts;
    }

//        public boolean areUVsNormalized()
//        {
//            for (Vertex v : this.verts)
//                if (!v.hasNormalizedUVs())
//                    return false;
//            return true;
//        }

//        public void normalizeUVs(float[] min, float[] max)
//        {
//            if (!this.areUVsNormalized())
//            {
//                for (int i = 0; i < this.verts.length; i++) {
//                    TextureCoordinate texCoord = this.verts[i].getTextureCoordinate();
//                    min[0] = texCoord.u < min[0] ? texCoord.u : min[0];
//                    max[0] = texCoord.u > max[0] ? texCoord.u : max[0];
//                    min[1] = texCoord.v < min[1] ? texCoord.v : min[1];
//                    max[1] = texCoord.v > max[1] ? texCoord.v : max[1];
//                }
//
//                for (Vertex v : this.verts) {
//                    v.texCoord.u = (v.texCoord.u - min[0]) / (max[0] - min[0]);
//                    v.texCoord.v = (v.texCoord.v - min[1]) / (max[1] - max[1]);
//                }
//            }
//        }

    @Nonnull
    public SmithsCoreOBJFace bake(@Nonnull TRSRTransformation transform) {
        Matrix4f m = transform.getMatrix();
        Matrix3f mn = null;
        SmithsCoreOBJVertex[] vertices = new SmithsCoreOBJVertex[verts.length];
//            Normal[] normals = norms != null ? new Normal[norms.length] : null;
//            TextureCoordinate[] textureCoords = texCoords != null ? new TextureCoordinate[texCoords.length] : null;

        for (int i = 0; i < verts.length; i++) {
            SmithsCoreOBJVertex v = verts[i];
//                Normal n = norms != null ? norms[i] : null;
//                TextureCoordinate t = texCoords != null ? texCoords[i] : null;

            Vector4f pos = new Vector4f(v.getPos()), newPos = new Vector4f();
            pos.w = 1;
            m.transform(pos, newPos);
            vertices[i] = new SmithsCoreOBJVertex(newPos, v.getMaterial());

            if (v.hasNormal()) {
                if (mn == null) {
                    mn = new Matrix3f();
                    m.getRotationScale(mn);
                    mn.invert();
                    mn.transpose();
                }
                Vector3f normal = new Vector3f(v.getNormal().getData()), newNormal = new Vector3f();
                mn.transform(normal, newNormal);
                newNormal.normalize();
                vertices[i].setNormal(new SmithsCoreOBJNormal(newNormal));
            }

            if (v.hasTextureCoordinate()) vertices[i].setTextureCoordinate(v.getTextureCoordinate());
            else v.setTextureCoordinate(SmithsCoreOBJTextureCoordinate.getDefaultUVs()[i]);

            //texCoords TODO
//                if (t != null) textureCoords[i] = t;
        }
        return new SmithsCoreOBJFace(vertices, this.materialName);
    }

    @Nonnull
    public SmithsCoreOBJNormal getNormal() {
        Vector3f a = this.verts[2].getPos3();
        a.sub(this.verts[0].getPos3());
        Vector3f b = this.verts[3].getPos3();
        b.sub(this.verts[1].getPos3());
        a.cross(a, b);
        a.normalize();
        return new SmithsCoreOBJNormal(a);
    }
}
