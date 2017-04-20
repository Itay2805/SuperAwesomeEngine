package me.itay.superawesomeengine.engine.renderEngine;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.itay.superawesomeengine.MainActivity;
import me.itay.superawesomeengine.engine.entities.Camera;
import me.itay.superawesomeengine.engine.entities.EntityInstance;
import me.itay.superawesomeengine.engine.entities.Light;
import me.itay.superawesomeengine.engine.entities.builtin.CTexturedModel;
import me.itay.superawesomeengine.engine.entities.builtin.CTransformation;
import me.itay.superawesomeengine.engine.models.TexturedModel;
import me.itay.superawesomeengine.engine.shaders.StaticShader;
import me.itay.superawesomeengine.engine.shaders.TerrainShader;
import me.itay.superawesomeengine.engine.terrains.Terrain;
import me.itay.superawesomeengine.engine.toolbox.Bounds;
import me.itay.superawesomeengine.engine.toolbox.Maths;

import static android.opengl.GLES30.*;

/**
 * Created by Itay on 4/20/2017.
 */

public class MasterRenderer {

    private static final float FOV = 55;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 250;

    private static final float RED = 0.49f;
    private static final float GREEN = 0.89f;
    private static final float BLUE = 0.98f;


    private StaticShader entityShader = new StaticShader();
    private EntityRenderer entityRenderer;

    private TerrainShader terrainShader = new TerrainShader();
    private TerrainRenderer terrainRenderer;

    private Matrix4f viewMatrix;
    private Matrix4f inverseViewMatrix;
    private Matrix4f projection;

    private Matrix4f projectionMatrix;

    private Map<TexturedModel, List<EntityInstance>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();

    public MasterRenderer() {
        enableCulling();

        createProjectionMatrix();

        entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }


    public void prepare(Camera camera) {
        viewMatrix = Maths.createViewMatrix(camera);
        inverseViewMatrix = Matrix4f.invert(viewMatrix, null);
        projection = Matrix4f.mul(projectionMatrix, viewMatrix, null);
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getInverseViewMatrix() {
        return inverseViewMatrix;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public void render(Light sun, Camera camera) {
        prepare();

        // render entities
        entityShader.start();
        entityShader.loadSkyColor(RED, GREEN, BLUE);
        entityShader.loadLight(sun);
        entityShader.loadViewMatrix(camera);
        entityRenderer.render(entities);
        entityShader.stop();
        entities.clear();

        // render terrains
        terrainShader.start();
        terrainShader.loadSkyColor(RED, GREEN, BLUE);
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        terrains.clear();
    }

    public static void enableCulling() {
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    }


    public static void disableCulling() {
        glDisable(GL_CULL_FACE);
    }

    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }

    public void processEntity(EntityInstance instance) {
        CTexturedModel model = instance.getComponent(CTexturedModel.class);

        CTransformation transformation = instance.getComponent(CTransformation.class);
        Matrix4f transformationMatrix = transformation.getTransformationMatrix();
        if(!Bounds.inBounds(projection, transformationMatrix, model.getModel().getModel().getBounds())) {
            return;
        }

        TexturedModel entityModel = model.getModel();
        List<EntityInstance> batch = entities.get(entityModel);
        if(batch != null) {
            batch.add(instance);
        }else {
            batch = new ArrayList<>();
            batch.add(instance);
            entities.put(entityModel, batch);
        }
    }

    public void cleanUp() {
        entityShader.cleanUp();
        terrainShader.cleanUp();
    }

    public void prepare() {
        glEnable(GL_DEPTH_TEST);
        glClearColor(RED, GREEN, BLUE, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public EntityRenderer getEntityRenderer() {
        return entityRenderer;
    }

    public StaticShader getEntityShader() {
        return entityShader;
    }

    private void createProjectionMatrix(){
        DisplayMetrics display = new DisplayMetrics();
        ((Activity) MainActivity.getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(display);

        float aspectRatio = (float) display.widthPixels / (float) display.heightPixels;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
