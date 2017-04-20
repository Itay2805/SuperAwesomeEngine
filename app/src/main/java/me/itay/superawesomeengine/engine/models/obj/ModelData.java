package me.itay.superawesomeengine.engine.models.obj;

import org.lwjgl.util.vector.Vector3f;

import java.util.List;

/**
 * Created by Itay on 4/20/2017.
 */

public class ModelData {

    private float[] vertices;
    private float[] textureCoords;
    private float[] normals;
    private int[] indices;
    private float furthestPoint;
    private List<Vector3f> bounds;

    public ModelData(float[] vertices, float[] textureCoords, float[] normals, int[] indices,
                     float furthestPoint, List<Vector3f> bounds) {
        this.vertices = vertices;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;
        this.furthestPoint = furthestPoint;
        this.bounds = bounds;
    }

    public List<Vector3f> getBounds() {
        return bounds;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public float[] getNormals() {
        return normals;
    }

    public int[] getIndices() {
        return indices;
    }

    public float getFurthestPoint() {
        return furthestPoint;
    }

}
