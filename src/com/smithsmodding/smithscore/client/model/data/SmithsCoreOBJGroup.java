package com.smithsmodding.smithscore.client.model.data;

import com.google.common.base.Optional;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Author Orion (Created on: 17.07.2016)
 */
public class SmithsCoreOBJGroup implements IModelPart {
    public static final String DEFAULT_NAME = "OBJModel.Default.Element.Name";
    public static final String ALL = "OBJModel.Group.All.Key";
    public static final String ALL_EXCEPT = "OBJModel.Group.All.Except.Key";
    @Nonnull
    public float[] minUVBounds = new float[]{0.0f, 0.0f};
    @Nonnull
    public float[] maxUVBounds = new float[]{1.0f, 1.0f};
    @Nonnull
    private String name = DEFAULT_NAME;
    private LinkedHashSet<SmithsCoreOBJFace> faces = new LinkedHashSet<SmithsCoreOBJFace>();

//        public float[] minUVBounds = new float[] {0.0f, 0.0f};
//        public float[] maxUVBounds = new float[] {1.0f, 1.0f};

    public SmithsCoreOBJGroup(@Nullable String name, @Nullable LinkedHashSet<SmithsCoreOBJFace> faces) {
        this.name = name != null ? name : DEFAULT_NAME;
        this.faces = faces == null ? new LinkedHashSet<SmithsCoreOBJFace>() : faces;
    }

    @Nonnull
    public LinkedHashSet<SmithsCoreOBJFace> applyTransform(@Nonnull Optional<TRSRTransformation> transform) {
        LinkedHashSet<SmithsCoreOBJFace> faceSet = new LinkedHashSet<SmithsCoreOBJFace>();
        for (SmithsCoreOBJFace f : this.faces) {
//                if (minUVBounds != null && maxUVBounds != null) f.normalizeUVs(minUVBounds, maxUVBounds);
            faceSet.add(f.bake(transform.or(TRSRTransformation.identity())));
        }
        return faceSet;
    }

    @Nonnull
    public String getName() {
        return this.name;
    }

    @Nonnull
    public LinkedHashSet<SmithsCoreOBJFace> getFaces() {
        return this.faces;
    }

    public void setFaces(@Nonnull LinkedHashSet<SmithsCoreOBJFace> faces) {
        this.faces = faces;
    }

    public void addFace(@Nonnull SmithsCoreOBJFace face) {
        this.faces.add(face);
    }

    public void addFaces(@Nonnull List<SmithsCoreOBJFace> faces) {
        this.faces.addAll(faces);
    }
}
