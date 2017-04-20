package me.itay.superawesomeengine.engine.models;

import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

import me.itay.superawesomeengine.engine.toolbox.AABB;

/**
 * Created by Itay on 4/19/2017.
 */

public class RawModel {

    private int vaoID;
    private int vertexCount;
    private AABB boundingBox;
    private List<Vector3f> bounds;

    public RawModel(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public AABB getBoundingBox() {
        return boundingBox;
    }

    public List<Vector3f> getBounds() {
        return bounds;
    }

    public void setBounds(List<Vector3f> bounds) {
        this.bounds = bounds;
    }

    public void setBoundingBox(AABB bounds) {
        this.boundingBox = bounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawModel rawModel = (RawModel) o;

        if (vaoID != rawModel.vaoID) return false;
        return vertexCount == rawModel.vertexCount;
    }

    @Override
    public int hashCode() {
        int result = vaoID;
        result = 31 * result + vertexCount;
        return result;
    }
}
