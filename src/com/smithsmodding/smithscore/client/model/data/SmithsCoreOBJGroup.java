package com.smithsmodding.smithscore.client.model.data;

import com.google.common.base.Optional;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Author Orion (Created on: 17.07.2016)
 */
public class SmithsCoreOBJGroup implements IModelPart {
    public static final String DEFAULT_NAME = "OBJModel.Default.Element.Name";
    public static final String ALL = "OBJModel.Group.All.Key";
    public static final String ALL_EXCEPT = "OBJModel.Group.All.Except.Key";
    public float[] minUVBounds = new float[]{0.0f, 0.0f};
    public float[] maxUVBounds = new float[]{1.0f, 1.0f};
    private String name = DEFAULT_NAME;
    private LinkedHashSet<SmithsCoreOBJFace> faces = new LinkedHashSet<SmithsCoreOBJFace>();

//        public float[] minUVBounds = new float[] {0.0f, 0.0f};
//        public float[] maxUVBounds = new float[] {1.0f, 1.0f};

    public SmithsCoreOBJGroup(String name, LinkedHashSet<SmithsCoreOBJFace> faces) {
        this.name = name != null ? name : DEFAULT_NAME;
        this.faces = faces == null ? new LinkedHashSet<SmithsCoreOBJFace>() : faces;
    }

    public LinkedHashSet<SmithsCoreOBJFace> applyTransform(Optional<TRSRTransformation> transform) {
        LinkedHashSet<SmithsCoreOBJFace> faceSet = new LinkedHashSet<SmithsCoreOBJFace>();
        for (SmithsCoreOBJFace f : this.faces) {
//                if (minUVBounds != null && maxUVBounds != null) f.normalizeUVs(minUVBounds, maxUVBounds);
            faceSet.add(f.bake(transform.or(TRSRTransformation.identity())));
        }
        return faceSet;
    }

    public String getName() {
        return this.name;
    }

    public LinkedHashSet<SmithsCoreOBJFace> getFaces() {
        return this.faces;
    }

    public void setFaces(LinkedHashSet<SmithsCoreOBJFace> faces) {
        this.faces = faces;
    }

    public void addFace(SmithsCoreOBJFace face) {
        this.faces.add(face);
    }

    public void addFaces(List<SmithsCoreOBJFace> faces) {
        this.faces.addAll(faces);
    }
}
