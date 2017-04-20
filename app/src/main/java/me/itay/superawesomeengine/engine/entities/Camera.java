package me.itay.superawesomeengine.engine.entities;

import org.lwjgl.util.vector.Vector3f;

import me.itay.superawesomeengine.engine.entities.builtin.CMovement;
import me.itay.superawesomeengine.engine.entities.builtin.CTransformation;

/**
 * Created by Itay on 4/19/2017.
 */

public class Camera {

    private static final EntityBlueprint CAMERA;

    static {
        CAMERA = new EntityBlueprint();
        CAMERA.addComponent(new CTransformation());
        CAMERA.addComponent(new CMovement());
        CAMERA.build();
    }

    private EntityInstance instance;
    private CTransformation transformation;

    public Camera() {
        instance = CAMERA.createInstance();
        transformation = instance.getComponent(CTransformation.class);
        transformation.increasePosition(0, 5, 0);
        transformation.increaseRotation(0, 0, 10);
    }

    public Vector3f getPosition() {
        return transformation.getPosition();
    }

    public float getYaw() {
        return transformation.getRotZ();
    }

    public float getRoll() {
        return transformation.getRotX();
    }

    public float getPitch() {
        return transformation.getRotZ();
    }

    public EntityInstance getInstance() {
        return instance;
    }
}
