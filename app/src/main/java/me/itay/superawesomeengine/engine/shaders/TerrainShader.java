package me.itay.superawesomeengine.engine.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import me.itay.superawesomeengine.engine.entities.Camera;
import me.itay.superawesomeengine.engine.entities.Light;
import me.itay.superawesomeengine.engine.renderEngine.Loader;
import me.itay.superawesomeengine.engine.toolbox.Maths;

/**
 * Created by Itay on 4/19/2017.
 */

public class TerrainShader extends ShaderProgram {

    private static final String VERTEX_FILE = "shaders/terrainVertexShader.glsl";
    private static final String FRAGMENT_FILE = "shaders/terrainFragmentShader.glsl";

    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_inverseViewMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColor;

    @Override
    public void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_inverseViewMatrix = super.getUniformLocation("inverseViewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_skyColor = super.getUniformLocation("skyColor");
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(Loader.POSITIONS_INDEX, "position");
        bindAttribute(Loader.TEXTURE_COORDS_INDEX, "textureCoords");
        bindAttribute(Loader.NORMALS_INDEX, "normal");
    }

    public void loadSkyColor(float r, float g, float b) {
        loadVector(location_skyColor, new Vector3f(r, g, b));
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        loadMatrix(location_viewMatrix, viewMatrix);
        loadMatrix(location_inverseViewMatrix, Matrix4f.invert(viewMatrix, null));
    }

    public void loadLight(Light light) {
        loadVector(location_lightColour, light.getColor());
        loadVector(location_lightPosition, light.getPosition());
    }

    public void loadShineVariables(float damper, float reflectivity) {
        loadFloat(location_shineDamper, damper);
        loadFloat(location_reflectivity, reflectivity);
    }

}
