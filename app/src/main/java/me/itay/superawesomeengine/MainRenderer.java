package me.itay.superawesomeengine;

import android.opengl.GLSurfaceView;
import android.util.Log;

import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import me.itay.superawesomeengine.engine.entities.Camera;
import me.itay.superawesomeengine.engine.entities.EntityBlueprint;
import me.itay.superawesomeengine.engine.entities.EntityInstance;
import me.itay.superawesomeengine.engine.entities.EntityManager;
import me.itay.superawesomeengine.engine.entities.Light;
import me.itay.superawesomeengine.engine.entities.builtin.CModelTexture;
import me.itay.superawesomeengine.engine.entities.builtin.CRawModel;
import me.itay.superawesomeengine.engine.entities.builtin.CTexturedModel;
import me.itay.superawesomeengine.engine.entities.builtin.CTransformation;
import me.itay.superawesomeengine.engine.renderEngine.EntityRenderer;
import me.itay.superawesomeengine.engine.renderEngine.Loader;
import me.itay.superawesomeengine.engine.renderEngine.MasterRenderer;
import me.itay.superawesomeengine.engine.shaders.StaticShader;
import me.itay.superawesomeengine.engine.terrains.Terrain;
import me.itay.superawesomeengine.engine.textures.ModelTexture;
import me.itay.superawesomeengine.engine.toolbox.Test;

/**
 * Created by Itay on 4/19/2017.
 */

public class MainRenderer implements GLSurfaceView.Renderer {

    MasterRenderer renderer;
    Camera camera;
    Light light;

    Terrain terrain1;
    Terrain terrain2;

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        renderer = new MasterRenderer();

        light = new Light(new Vector3f(2000, 2000, 2000), new Vector3f(1, 1, 1));

        camera = new Camera();
        EntityManager.addEntity(camera.getInstance());

        EntityBlueprint treeBlueprint = new EntityBlueprint();
        treeBlueprint.addComponent(new CTransformation());
        treeBlueprint.addComponent(new CRawModel("tree"));
        treeBlueprint.addComponent(new CModelTexture("tree"));
        treeBlueprint.addComponent(new CTexturedModel());
        treeBlueprint.build();

        EntityBlueprint fernBlueprint = new EntityBlueprint();
        fernBlueprint.addComponent(new CTransformation());
        fernBlueprint.addComponent(new CRawModel("fern"));
        fernBlueprint.addComponent(new CModelTexture("fern"));
        fernBlueprint.getComponent(CModelTexture.class).getTexture().setFakeLighting(true).setTransparent(true);
        fernBlueprint.addComponent(new CTexturedModel());
        fernBlueprint.build();

        EntityBlueprint grassBlueprint = new EntityBlueprint();
        grassBlueprint.addComponent(new CTransformation());
        grassBlueprint.addComponent(new CRawModel("grassModel"));
        grassBlueprint.addComponent(new CModelTexture("grassTexture"));
        grassBlueprint.addComponent(new CTexturedModel());
        grassBlueprint.getComponent(CModelTexture.class).getTexture().setFakeLighting(true).setTransparent(true);
        grassBlueprint.build();


        Random random = new Random();
        for(int i = 0; i < 500; i++) {
            {
                EntityInstance instance = treeBlueprint.createInstance();
                CTransformation transformation = instance.getComponent(CTransformation.class);
                transformation.setPosition(new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600));
                transformation.setScale(3);
                EntityManager.addEntity(instance);
            }
            {
                EntityInstance instance = grassBlueprint.createInstance();
                CTransformation transformation = instance.getComponent(CTransformation.class);
                transformation.setPosition(new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600));
                transformation.setScale(1);
                EntityManager.addEntity(instance);
            }
            {
                EntityInstance instance = fernBlueprint.createInstance();
                CTransformation transformation = instance.getComponent(CTransformation.class);
                transformation.setPosition(new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600));
                transformation.setScale(0.6f);
                EntityManager.addEntity(instance);
            }
        }

        terrain1 = new Terrain(0, -1, new ModelTexture(Loader.loadTexture("grass")));
        terrain2 = new Terrain(-1, -1, new ModelTexture(Loader.loadTexture("grass")));
    }

    long lastTime = System.nanoTime();
    long timer = System.currentTimeMillis();
    final double ns = 1000000000.0 / 60.0;
    double delta = 0;
    int frames = 0;
    int updates = 0;

    @Override
    public void onDrawFrame(GL10 gl) {
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;

        while(delta >= 1) {
            update();
            updates++;
            delta--;
        }
        render();
        frames++;

        if(System.currentTimeMillis() - timer > 1000) {
            EntityManager.tick();
            timer += 1000;
            Log.d("FPSCounter", "fps: " + frames + " | ups: " + updates);
            frames = 0;
            updates = 0;
        }
    }

    public void update() {
        EntityManager.update();
    }

    public void render() {
        renderer.prepare(camera);

        renderer.processTerrain(terrain1);
        renderer.processTerrain(terrain2);

        EntityManager.render(renderer);

        renderer.render(light, camera);
    }

    public void cleanUp() {
        renderer.cleanUp();
    }
}
