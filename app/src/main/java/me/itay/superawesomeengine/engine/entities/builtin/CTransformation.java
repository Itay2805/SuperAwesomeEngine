package me.itay.superawesomeengine.engine.entities.builtin;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import me.itay.superawesomeengine.engine.entities.Component;
import me.itay.superawesomeengine.engine.toolbox.Maths;

/**
 * Created by Itay on 4/19/2017.
 */

public class CTransformation extends Component {

    private Vector3f position = new Vector3f();
    private float rotX = 0.0f, rotY = 0.0f, rotZ = 0.0f;
    private float scale = 1.0f;

    private Matrix4f transformationMatrix;

    public CTransformation() {
        super(false, false);
    }

    public CTransformation(Vector3f position) { this(position, 0.0f, 0.0f, 0.0f); }
    public CTransformation(Vector3f position, float rotX, float rotY, float rotZ) { this(position, rotX, rotY, rotZ, 1.0f); }
    public CTransformation(Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(false, false);
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public CTransformation(CTransformation transformation) {
        super(false, false);
        this.scale = transformation.getScale();
        this.rotX = transformation.getRotX();
        this.rotY = transformation.getRotY();
        this.rotZ = transformation.getRotZ();
        this.position = new Vector3f(transformation.getPosition());
    }

    @Override
    public void init() {
        super.init();
        createTransformationMatrix();
    }

    public void increasePosition(float dx, float dy, float dz) {
        position.x += dx;
        position.y += dy;
        position.z += dz;
        createTransformationMatrix();
    }

    public void increaseRotation(float dx, float dy, float dz) {
        rotX += dx;
        rotY += dy;
        rotZ += dz;
        createTransformationMatrix();
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public float getScale() {
        return scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public CTransformation setPosition(Vector3f position) {
        this.position = position;
        createTransformationMatrix();
        return this;
    }

    public CTransformation setRotX(float rotX) {
        this.rotX = rotX;
        createTransformationMatrix();
        return this;
    }

    public CTransformation setRotY(float rotY) {
        this.rotY = rotY;
        createTransformationMatrix();
        return this;
    }

    public CTransformation setRotZ(float rotZ) {
        this.rotZ = rotZ;
        createTransformationMatrix();
        return this;
    }

    public CTransformation setScale(float scale) {
        this.scale = scale;
        createTransformationMatrix();
        return this;
    }

    private void createTransformationMatrix() {
        transformationMatrix = Maths.createTransformationMatrix(getPosition(), getRotX(), getRotY(), getRotZ(), getScale());
    }

    public Matrix4f getTransformationMatrix() {
        return transformationMatrix;
    }

    @Override
    public Component clone() {
        return new CTransformation(this);
    }
}
