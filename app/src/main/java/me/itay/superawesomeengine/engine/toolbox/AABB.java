package me.itay.superawesomeengine.engine.toolbox;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Itay on 4/20/2017.
 */

public class AABB {

    public float minX = Float.POSITIVE_INFINITY;
    public float maxX = Float.NEGATIVE_INFINITY;
    public float minY = Float.POSITIVE_INFINITY;
    public float maxY = Float.NEGATIVE_INFINITY;
    public float minZ = Float.POSITIVE_INFINITY;
    public float maxZ = Float.NEGATIVE_INFINITY;
    private Vector3f pos;
    private Vector3f size;

    public AABB(Vector3f pos, Vector3f size) {
        this.pos = pos;
        this.size = size;
        this.updateCoordinates();
    }

    private void updateCoordinates() {
        this.minX = pos.getX() - size.getX() / 2;
        this.maxX = pos.getX() + size.getX() / 2;

        this.minZ = pos.getZ() - size.getZ() / 2;
        this.maxZ = pos.getZ() + size.getZ() / 2;

        this.minY = pos.getY();
        this.maxY = pos.getY() + size.getY();
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
        this.updateCoordinates();
    }

    public void setSize(Vector3f size) {
        this.size = size;
        this.updateCoordinates();
    }

    public Vector3f getPos() {
        return this.pos;
    }

    public Vector3f getSize() {
        return this.size;
    }

    public Vector3f[] getVertices() {
        return  new Vector3f[]{
                new Vector3f(minX, maxY, 0.0f),
                new Vector3f(minX, minY, 0.0f),
                new Vector3f(maxX, minY, 0.0f),
                new Vector3f(maxX, maxY, 0.0f),
        };
    }

    public boolean isColliding(AABB other) {
        return !(
                other.maxX <= this.minX ||
                other.minX >= this.maxX ||
                other.maxY <= this.minY ||
                other.minY >= this.maxY ||
                other.maxZ <= this.minZ ||
                other.minZ >= this.maxZ
        );
    }

}
