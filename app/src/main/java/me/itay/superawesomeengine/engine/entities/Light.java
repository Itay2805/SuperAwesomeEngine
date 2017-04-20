package me.itay.superawesomeengine.engine.entities;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Itay on 4/19/2017.
 */

public class Light {

    private Vector3f position;
    private Vector3f color;

    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }
    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
